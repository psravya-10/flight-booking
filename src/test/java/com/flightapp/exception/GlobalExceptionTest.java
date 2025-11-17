package com.flightapp.exception;

import org.junit.jupiter.api.Test;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.*;

class GlobalExceptionHandlerTest {

    private final GlobalExceptionHandler handler = new GlobalExceptionHandler();

    @Test
    void testHandleNotFound() {
        ResponseEntity<?> res = handler.handleNotFound(new NotFoundException("Not found"));
        assertEquals(404, res.getStatusCode().value());
    }

    @Test
    void testHandleBadRequest() {
        ResponseEntity<?> res = handler.handleBadRequest(new BadRequestException("Bad request"));
        assertEquals(400, res.getStatusCode().value());
    }
}
