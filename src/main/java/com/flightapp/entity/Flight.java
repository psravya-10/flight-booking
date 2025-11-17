package com.flightapp.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
public class Flight {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String fromPlace;
    private String toPlace;

    private LocalDateTime departureTime;
    private LocalDateTime arrivalTime;

    private String tripType;

    private double priceOneWay;
    private Double priceRoundTrip;

    private int totalSeats;
    private int availableSeats;

    @ManyToOne
    private Airline airline;
}
