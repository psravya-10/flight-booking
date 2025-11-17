package com.flightapp.dto;

import lombok.Data;
import java.time.LocalDateTime;

@Data
public class FlightSearchResponse {

    private Long flightId;
    private LocalDateTime departureTime;
    private LocalDateTime arrivalTime;
    private String airlineName;
    private String airlineLogoUrl;
    private double priceOneWay;
    private Double priceRoundTrip;
    private String tripType;
}
