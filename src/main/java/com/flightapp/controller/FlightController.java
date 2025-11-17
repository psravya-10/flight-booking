package com.flightapp.controller;

import com.flightapp.dto.*;
import com.flightapp.service.FlightService;
import jakarta.validation.Valid;

import lombok.extern.slf4j.Slf4j;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@Slf4j
@RequestMapping("/api/v1.0/flight")
public class FlightController {

    private final FlightService service;

    public FlightController(FlightService service) {
        this.service = service;
    }

    @PostMapping("/airline/inventory/add")
    public ResponseEntity<Long> addInventory(@Valid @RequestBody AirlineInventoryRequest req) {
        log.debug("Add inventory");
        return ResponseEntity.ok(service.addInventory(req));
    }

    @PostMapping("/search")
    public ResponseEntity<List<FlightSearchResponse>> search(@Valid @RequestBody FlightSearchRequest req) {
        log.debug("Search flights");
        return ResponseEntity.ok(service.searchFlights(req));
    }

    @PostMapping("/booking/{flightId}")
    public ResponseEntity<TicketResponse> book(
            @PathVariable Long flightId,
            @Valid @RequestBody BookingRequest req
    ) {
        log.debug("Book ticket");
        return ResponseEntity.ok(service.bookTicket(flightId, req));
    }

    @GetMapping("/ticket/{pnr}")
    public ResponseEntity<TicketResponse> ticket(@PathVariable String pnr) {
        log.debug("Get Ticket by PNR");
        return ResponseEntity.ok(service.getTicketByPnr(pnr));
    }

    @GetMapping("/booking/history/{email}")
    public ResponseEntity<List<TicketResponse>> history(@PathVariable String email) {
        log.debug("Get Booking History");
        return ResponseEntity.ok(service.getHistory(email));
    }

    @DeleteMapping("/booking/cancel/{pnr}")
    public ResponseEntity<Void> cancel(@PathVariable String pnr) {
        log.debug("Cancel ticket");
        service.cancelTicket(pnr);
        return ResponseEntity.noContent().build();
    }
}
