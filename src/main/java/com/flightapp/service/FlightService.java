package com.flightapp.service;

import com.flightapp.dto.*;
import java.util.List;

public interface FlightService {

    Long addInventory(AirlineInventoryRequest req);

    List<FlightSearchResponse> searchFlights(FlightSearchRequest req);

    TicketResponse bookTicket(Long flightId, BookingRequest req);

    TicketResponse getTicketByPnr(String pnr);

    List<TicketResponse> getHistory(String emailId);

    void cancelTicket(String pnr);
}
