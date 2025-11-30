package com.bookingservice.service;

import com.bookingservice.client.FlightClient;
import com.bookingservice.client.dto.FlightDto;
import com.bookingservice.dto.BookingRequest;
import com.bookingservice.dto.BookingResponseDto;
import com.bookingservice.model.Booking;
import com.bookingservice.model.Passenger;
import com.bookingservice.repository.BookingRepository;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.time.Instant;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class BookingService {

    private static final Logger log = LoggerFactory.getLogger(BookingService.class);

    private final BookingRepository bookingRepository;
    private final FlightClient flightClient;

    public BookingService(BookingRepository bookingRepository,
                          FlightClient flightClient) {
        this.bookingRepository = bookingRepository;
        this.flightClient = flightClient;
    }

    @Transactional
    @CircuitBreaker(name = "flightClient", fallbackMethod = "createBookingFallback")
    public BookingResponseDto createBooking(BookingRequest request, String headerEmail) {
        // If request doesn't contain userEmail, use header as canonical identity.
        if (request.getUserEmail() == null || request.getUserEmail().isBlank()) {
            request.setUserEmail(headerEmail);
        } else {
            // If request contains userEmail, require it to match header (case-insensitive)
            if (!headerEmail.equalsIgnoreCase(request.getUserEmail())) {
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
                        "Header user email must match request userEmail");
            }
        }

        // Get flight details from flight-service
        FlightDto flight = flightClient.getFlightById(request.getFlightId());
        if (flight == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND,
                    "Flight not found: " + request.getFlightId());
        }

        // check seat availability. simple check: count available seats >= numSeats
        long availableSeats = Optional.ofNullable(flight.getSeats()).orElse(Collections.emptyList())
                .stream().filter(s -> "AVAILABLE".equalsIgnoreCase(s.getStatus())).count();

        if (availableSeats < request.getNumSeats()) {
            throw new ResponseStatusException(HttpStatus.CONFLICT,
                    "Not enough seats available");
        }

        // calculate total price (simple: price * numSeats)
        double totalPrice = flight.getPrice() * request.getNumSeats();

        Booking booking = new Booking();
        booking.setPnr(generatePnr());
        booking.setFlightId(request.getFlightId());
        booking.setUserEmail(request.getUserEmail());
        booking.setNumSeats(request.getNumSeats());
        booking.setTotalPrice(totalPrice);
        booking.setStatus("ACTIVE");
        booking.setCreatedAt(Instant.now());

        // map passengers
        List<Passenger> passengers = Optional.ofNullable(request.getPassengers()).orElse(Collections.emptyList())
                .stream().map(pdto -> {
                    Passenger p = new Passenger();
                    p.setPassengerName(pdto.getName());
                    p.setGender(pdto.getGender());
                    p.setAge(pdto.getAge());
                    p.setSeatNumber(pdto.getSeatNumber());
                    p.setMealPreference(pdto.getMealPreference());
                    p.setBooking(booking);
                    return p;
                }).collect(Collectors.toList());

        booking.setPassengers(passengers);
        Booking saved = bookingRepository.save(booking);

        // NOTE: Kafka/events removed — do not fail booking if events are not available.
        log.info("Booking saved: pnr={}, flightId={}, user={}", saved.getPnr(), saved.getFlightId(), saved.getUserEmail());

        return convertToDto(saved);
    }

    // Fallback when flight service call fails: return 503 Service Unavailable
    public BookingResponseDto createBookingFallback(BookingRequest request, String headerEmail, Throwable t) {
        throw new ResponseStatusException(HttpStatus.SERVICE_UNAVAILABLE,
                "Flight service unavailable. Try again later.", t);
    }

    @Transactional(readOnly = true)
    public BookingResponseDto getByPnr(String pnr) {
        Booking booking = bookingRepository.findByPnr(pnr)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "PNR not found"));
        return convertToDto(booking);
    }

    @Transactional(readOnly = true)
    public List<BookingResponseDto> getHistoryByEmail(String email) {
        List<Booking> list = bookingRepository.findByUserEmailOrderByCreatedAtDesc(email);
        return list.stream().map(this::convertToDto).collect(Collectors.toList());
    }

    @Transactional
    public BookingResponseDto cancelBooking(String pnr, String headerEmail) {
        Booking booking = bookingRepository.findByPnr(pnr)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "PNR not found"));

        if (!booking.getUserEmail().equalsIgnoreCase(headerEmail)) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Only the booking owner can cancel this booking");
        }

        if ("CANCELLED".equalsIgnoreCase(booking.getStatus())) {
            return convertToDto(booking);
        }

        booking.setStatus("CANCELLED");
        booking.setCancelledAt(Instant.now());
        Booking saved = bookingRepository.save(booking);

        log.info("Booking cancelled: pnr={}, flightId={}, user={}", saved.getPnr(), saved.getFlightId(), saved.getUserEmail());

        return convertToDto(saved);
    }

    private BookingResponseDto convertToDto(Booking b) {
        BookingResponseDto dto = new BookingResponseDto();
        dto.setPnr(b.getPnr());
        dto.setFlightId(b.getFlightId());
        dto.setUserEmail(b.getUserEmail());
        dto.setNumSeats(b.getNumSeats());
        dto.setTotalPrice(b.getTotalPrice());
        dto.setStatus(b.getStatus());
        dto.setCreatedAt(b.getCreatedAt());
        List<BookingResponseDto.PassengerInfo> pinfos = Optional.ofNullable(b.getPassengers()).orElse(Collections.emptyList())
                .stream().map(p -> {
                    BookingResponseDto.PassengerInfo pi = new BookingResponseDto.PassengerInfo();
                    pi.setName(p.getPassengerName());
                    pi.setGender(p.getGender());
                    pi.setAge(p.getAge());
                    pi.setSeatNumber(p.getSeatNumber());
                    pi.setMealPreference(p.getMealPreference());
                    return pi;
                }).collect(Collectors.toList());
        dto.setPassengers(pinfos);
        return dto;
    }

    private String generatePnr() {
        // generate short unique PNR: 8 uppercase alphanumeric chars
        return UUID.randomUUID().toString().replaceAll("-", "").substring(0, 8).toUpperCase();
    }
}