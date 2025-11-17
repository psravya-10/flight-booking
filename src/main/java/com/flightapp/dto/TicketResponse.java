package com.flightapp.dto;

import lombok.Data;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Data
public class TicketResponse {

    private String pnr;
    private String customerName;
    private String email;

    private String fromPlace;
    private String toPlace;

    private LocalDate journeyDate;
    private LocalDateTime departureTime;
    private LocalDateTime arrivalTime;

    private String airlineName;

    private int numberOfSeats;
    private String seatNumbers;
    private String mealPreference;

    private boolean cancelled;
    private LocalDateTime bookingTime;

    private List<PassengerDto> passengers;
}
