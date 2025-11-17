package com.flightapp.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;

@Entity
@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
public class Booking {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String pnr;

    private String customerName;
    private String email;

    private int numberOfSeats;
    private String seatNumbers;
    private String mealPreference;

    private boolean cancelled;

    private LocalDate journeyDate;
    private LocalDateTime bookingTime;

    @ManyToOne
    private Flight flight;

    @OneToMany(mappedBy = "booking", cascade = CascadeType.ALL)
    private List<Passenger> passengers = new ArrayList<>();
}
