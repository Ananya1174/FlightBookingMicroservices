package com.flightservice.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;
import java.util.List;
import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

@Entity
@Table(name = "flights")
@Getter
@Setter
@NoArgsConstructor   // JPA requires a default constructor
@AllArgsConstructor  // Optional: allows creating full objects easily
public class Flight {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String flightNumber;

    private String airlineName;

    private String airlineLogoUrl;

    private String origin;

    private String destination;

    private LocalDateTime departureTime;

    private LocalDateTime arrivalTime;

    private Double price;

    private String tripType; // ONEWAY or ROUNDTRIP

    private Integer totalSeats;

    @OneToMany(mappedBy = "flight",
            cascade = CascadeType.ALL,
            orphanRemoval = true,
            fetch = FetchType.LAZY)
    private List<FlightSeat> seats;
}