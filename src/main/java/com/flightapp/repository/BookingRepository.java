package com.flightapp.repository;

import com.flightapp.entity.Booking;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.*;

public interface BookingRepository extends JpaRepository<Booking, Long> {
    Optional<Booking> findByPnr(String pnr);
    List<Booking> findByEmailOrderByBookingTimeDesc(String email);
}
