package com.flightapp.dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.*;
import lombok.Data;

import java.time.LocalDate;
import java.util.List;

@Data
public class BookingRequest {

    @NotBlank
    private String customerName;

    @Email
    @NotBlank
    private String email;

    @Min(value=1)
    private int numberOfSeats;

    @NotNull
    private LocalDate journeyDate;
    
    @Valid
    @NotEmpty
    private List<PassengerDto> passengers;

    @NotBlank
    private String seatNumbers;

    @NotBlank
    private String mealPreference;
}
