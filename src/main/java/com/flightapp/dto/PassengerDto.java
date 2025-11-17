package com.flightapp.dto;

import jakarta.validation.constraints.*;
import lombok.Data;

@Data
public class PassengerDto {

    @NotBlank
    private String name;

    @NotBlank
    private String gender;

    @Min(value=1)
    private int age;
}
