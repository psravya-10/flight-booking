package com.flightapp.controller;

import com.flightapp.dto.*;
import com.flightapp.service.FlightService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Collections;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(FlightController.class)
class FlightControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private FlightService service;

    @Test
    void testSearchFlights() throws Exception {
        Mockito.when(service.searchFlights(Mockito.any()))
                .thenReturn(Collections.emptyList());

        mockMvc.perform(post("/api/v1.0/flight/search")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"fromPlace\":\"HYD\",\"toPlace\":\"DEL\",\"journeyDate\":\"2025-11-20\",\"tripType\":\"ONE_WAY\"}"))
                .andExpect(status().isOk());
    }
}
