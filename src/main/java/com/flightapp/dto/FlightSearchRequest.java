package com.flightapp.dto;

import jakarta.validation.constraints.*;
import lombok.Data;

import java.time.LocalDate;

@Data
public class FlightSearchRequest {
    @NotBlank
    private String fromPlace;
    @NotBlank
    private String toPlace;

    @NotNull
    private LocalDate journeyDate;

    @NotBlank
    private String tripType;
}
