package com.flightservice.controller;

import com.flightservice.dto.*;
import com.flightservice.service.FlightService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/flights")
public class FlightController {

    private final FlightService flightService;

    public FlightController(FlightService flightService) {
        this.flightService = flightService;
    }

    /**
     * Add flight inventory
     * POST /api/flights/inventory
     */
    @PostMapping("/inventory")
    public ResponseEntity<FlightResponseDto> addInventory(@Valid @RequestBody FlightInventoryRequest request) {
        FlightResponseDto dto = flightService.addInventory(request);
        return ResponseEntity.ok(dto);
    }

    /**
     * Search flights
     * POST /api/flights/search
     */
    @PostMapping("/search")
    public ResponseEntity<List<SearchResultDto>> searchFlights(@Valid @RequestBody SearchRequest req) {
        List<SearchResultDto> results = flightService.searchFlights(req);
        return ResponseEntity.ok(results);
    }
}