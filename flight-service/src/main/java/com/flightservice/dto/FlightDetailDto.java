package com.flightservice.dto;

import java.time.LocalDateTime;
import java.util.List;
import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class FlightDetailDto {

    private Long id;
    private String flightNumber;
    private String airlineName;
    private String airlineLogoUrl;
    private String origin;
    private String destination;
    private LocalDateTime departureTime;
    private LocalDateTime arrivalTime;
    private Double price;
    private String tripType;
    private Integer totalSeats;
    private List<SeatDto> seats;

    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class SeatDto {
        private String seatNumber;
        private String status; // AVAILABLE / BOOKED
    }
}