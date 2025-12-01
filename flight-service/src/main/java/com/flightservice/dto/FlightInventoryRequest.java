package com.flightservice.dto;

import java.time.LocalDateTime;
import java.util.List;

import jakarta.validation.constraints.*;
import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class FlightInventoryRequest {

    private String flightNumber;

    @NotBlank
    private String airlineName;

    private String airlineLogoUrl;

    @NotBlank
    private String origin;

    @NotBlank
    private String destination;

    @NotNull
    private LocalDateTime departureTime;

    @NotNull
    private LocalDateTime arrivalTime;

    @NotNull
    @PositiveOrZero
    private Double price;

    @NotBlank
    private String tripType; 

    @Positive
    private Integer totalSeats;

    private List<String> seatNumbers;
}