package com.flightapp.repository;

import com.flightapp.entity.Airline;
import com.flightapp.entity.Flight;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface FlightRepository extends JpaRepository<Flight, Long> {

    List<Flight> findByFromPlaceIgnoreCaseAndToPlaceIgnoreCaseAndDepartureTimeBetweenAndTripTypeIgnoreCase(
            String fromPlace,
            String toPlace,
            LocalDateTime start,
            LocalDateTime end,
            String tripType
    );
    Optional<Flight> findByAirlineAndFromPlaceIgnoreCaseAndToPlaceIgnoreCaseAndDepartureTime(
            Airline airline,
            String fromPlace,
            String toPlace,
            LocalDateTime departureTime
    );
}
