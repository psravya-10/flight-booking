package com.flightapp.service;

import com.flightapp.dto.*;
import com.flightapp.entity.*;
import com.flightapp.exception.*;
import com.flightapp.repository.*;

import lombok.extern.slf4j.Slf4j;

import org.springframework.stereotype.Service;

import java.time.*;
import java.util.*;

import java.util.stream.Collectors;

@Service
@Slf4j
public class FlightServiceImpl implements FlightService {

    private final AirlineRepository airlineRepo;
    private final FlightRepository flightRepo;
    private final BookingRepository bookingRepo;

    public FlightServiceImpl(
            AirlineRepository airlineRepo,
            FlightRepository flightRepo,
            BookingRepository bookingRepo
    ) {
        this.airlineRepo = airlineRepo;
        this.flightRepo = flightRepo;
        this.bookingRepo = bookingRepo;
    }

    @Override
    public Long addInventory(AirlineInventoryRequest req) {

        Airline airline = airlineRepo.findByNameIgnoreCase(req.getAirlineName())
                .orElseGet(() -> airlineRepo.save(
                		new Airline(null, req.getAirlineName())

                ));

        Flight flight = new Flight();
        flight.setAirline(airline);
        flight.setFromPlace(req.getFromPlace());
        flight.setToPlace(req.getToPlace());
        flight.setDepartureTime(req.getDepartureTime());
        flight.setArrivalTime(req.getArrivalTime());
        flight.setTripType(req.getTripType());
        flight.setTotalSeats(req.getTotalSeats());
        flight.setAvailableSeats(req.getTotalSeats());
        flight.setPriceOneWay(req.getPriceOneWay());
        flight.setPriceRoundTrip(req.getPriceRoundTrip());

        flightRepo.save(flight);
        return flight.getId();
    }

    @Override
    public List<FlightSearchResponse> searchFlights(FlightSearchRequest req) {

        LocalDateTime start = req.getJourneyDate().atStartOfDay();
        LocalDateTime end = req.getJourneyDate().atTime(23, 59);

        List<Flight> flights = flightRepo
                .findByFromPlaceIgnoreCaseAndToPlaceIgnoreCaseAndDepartureTimeBetweenAndTripTypeIgnoreCase(
                        req.getFromPlace(), req.getToPlace(),
                        start, end,
                        req.getTripType()
                );

        return flights.stream().map(f -> {
            FlightSearchResponse r = new FlightSearchResponse();
            r.setFlightId(f.getId());
            r.setDepartureTime(f.getDepartureTime());
            r.setArrivalTime(f.getArrivalTime());
            r.setTripType(f.getTripType());
            r.setAirlineName(f.getAirline().getName());
            r.setPriceOneWay(f.getPriceOneWay());
            r.setPriceRoundTrip(f.getPriceRoundTrip());
            return r;
        }).collect(Collectors.toList());
    }

    @Override
    public TicketResponse bookTicket(Long flightId, BookingRequest req) {

        Flight flight = flightRepo.findById(flightId)
                .orElseThrow(() -> new NotFoundException("Flight not found"));

        if (req.getNumberOfSeats() != req.getPassengers().size())
            throw new BadRequestException("Passenger count must match seat count");

        if (req.getNumberOfSeats() > flight.getAvailableSeats())
            throw new BadRequestException("Seats not available");

        LocalDate flightDate = flight.getDepartureTime().toLocalDate();
        if (!flightDate.equals(req.getJourneyDate()))
            throw new BadRequestException("Wrong journey date");

        Booking booking = new Booking();
        booking.setCustomerName(req.getCustomerName());
        booking.setEmail(req.getEmail());
        booking.setSeatNumbers(req.getSeatNumbers());
        booking.setMealPreference(req.getMealPreference());
        booking.setJourneyDate(req.getJourneyDate());
        booking.setCancelled(false);
        booking.setNumberOfSeats(req.getNumberOfSeats());
        booking.setBookingTime(LocalDateTime.now());
        booking.setFlight(flight);
        booking.setPnr(PnrGenerator.generate());

        List<Passenger> passengers = new ArrayList<>();
        req.getPassengers().forEach(p -> {
            passengers.add(new Passenger(null, p.getName(), p.getGender(), p.getAge(), booking));
        });
        booking.setPassengers(passengers);

        flight.setAvailableSeats(flight.getAvailableSeats() - req.getNumberOfSeats());

        bookingRepo.save(booking);
        flightRepo.save(flight);

        return toResponse(booking);
    }

    @Override
    public TicketResponse getTicketByPnr(String pnr) {
        Booking b = bookingRepo.findByPnr(pnr)
                .orElseThrow(() -> new NotFoundException("PNR not found"));
        return toResponse(b);
    }

    @Override
    public List<TicketResponse> getHistory(String emailId) {
        return bookingRepo.findByEmailOrderByBookingTimeDesc(emailId)
                .stream()
                .map(this::toResponse)
                .collect(Collectors.toList());
    }

    @Override
    public void cancelTicket(String pnr) {

        Booking booking = bookingRepo.findByPnr(pnr)
                .orElseThrow(() -> new NotFoundException("Booking not found"));

        LocalDateTime now = LocalDateTime.now();
        LocalDateTime dep = booking.getJourneyDate().atStartOfDay();

        if (Duration.between(now, dep).toHours() < 24)
            throw new BadRequestException("Cannot cancel within 24 hours");

        booking.setCancelled(true);

        Flight f = booking.getFlight();
        f.setAvailableSeats(f.getAvailableSeats() + booking.getNumberOfSeats());

        bookingRepo.save(booking);
        flightRepo.save(f);
    }

    private TicketResponse toResponse(Booking b) {
        TicketResponse r = new TicketResponse();
        r.setPnr(b.getPnr());
        r.setCustomerName(b.getCustomerName());
        r.setEmail(b.getEmail());
        r.setJourneyDate(b.getJourneyDate());
        r.setSeatNumbers(b.getSeatNumbers());
        r.setMealPreference(b.getMealPreference());
        r.setNumberOfSeats(b.getNumberOfSeats());
        r.setCancelled(b.isCancelled());
        r.setBookingTime(b.getBookingTime());

        Flight f = b.getFlight();
        r.setFromPlace(f.getFromPlace());
        r.setToPlace(f.getToPlace());
        r.setDepartureTime(f.getDepartureTime());
        r.setArrivalTime(f.getArrivalTime());
        r.setAirlineName(f.getAirline().getName());

        List<PassengerDto> pList = b.getPassengers().stream()
                .map(px -> {
                    PassengerDto dto = new PassengerDto();
                    dto.setName(px.getName());
                    dto.setGender(px.getGender());
                    dto.setAge(px.getAge());
                    return dto;
                })
                .collect(Collectors.toList());
        r.setPassengers(pList);

        return r;
    }
}
