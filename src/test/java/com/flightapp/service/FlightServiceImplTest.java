package com.flightapp.service;

import com.flightapp.dto.AirlineInventoryRequest;
import com.flightapp.entity.Airline;
import com.flightapp.entity.Flight;
import com.flightapp.repository.AirlineRepository;
import com.flightapp.repository.BookingRepository;
import com.flightapp.repository.FlightRepository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.mockito.Mockito.*;

import java.time.LocalDateTime;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

class FlightServiceImplTest {

    private AirlineRepository airlineRepo;
    private FlightRepository flightRepo;
    private BookingRepository bookingRepo;

    private FlightServiceImpl service;

    @BeforeEach
    void setup() {
        airlineRepo = mock(AirlineRepository.class);
        flightRepo = mock(FlightRepository.class);
        bookingRepo = mock(BookingRepository.class);
        service = new FlightServiceImpl(airlineRepo, flightRepo, bookingRepo);
    }

    @Test
    void testAddInventory() {

        AirlineInventoryRequest req = new AirlineInventoryRequest();
        req.setAirlineName("Indigo");
        
        req.setFromPlace("Hyd");
        req.setToPlace("Delhi");
        req.setDepartureTime(LocalDateTime.now());
        req.setArrivalTime(LocalDateTime.now().plusHours(2));
        req.setTripType("ONE_WAY");
        req.setTotalSeats(100);
        req.setPriceOneWay(5000.0);

        Airline airline = new Airline(1L, "Indigo");

        when(airlineRepo.findByNameIgnoreCase("Indigo"))
                .thenReturn(Optional.of(airline));

        when(flightRepo.save(any(Flight.class))).thenAnswer(invocation -> {
            Flight f = invocation.getArgument(0);
            f.setId(10L);   
            return f;
        });

        Long id = service.addInventory(req);

        assertEquals(10L, id);
    }

}
