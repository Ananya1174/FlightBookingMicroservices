package com.flightservice.repository;

import com.flightservice.model.FlightSeat;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FlightSeatRepository extends JpaRepository<FlightSeat, Long> {
    List<FlightSeat> findByFlightIdAndStatus(Long flightId, String status);
}