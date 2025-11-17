package com.flightapp.dto;

import jakarta.validation.constraints.*;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class AirlineInventoryRequest {

    @NotBlank
    private String airlineName;


    @NotBlank
    private String fromPlace;

    @NotBlank
    private String toPlace;

    @NotNull
    private LocalDateTime departureTime;

    @NotNull
    private LocalDateTime arrivalTime;

    @NotBlank
    private String tripType;

    @Min(value=1)
    private double priceOneWay;

    private Double priceRoundTrip;

    @Min(value=1)
    private int totalSeats;
}
