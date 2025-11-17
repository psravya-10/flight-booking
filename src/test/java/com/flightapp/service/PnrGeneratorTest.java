package com.flightapp.service;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class PnrGeneratorTest {

    @Test
    void testPnrLength() {
        String pnr = PnrGenerator.generate();
        assertEquals(8, pnr.length());
    }

    @Test
    void testPnrNotNull() {
        String pnr = PnrGenerator.generate();
        assertNotNull(pnr);
    }

    @Test
    void testPnrIsUppercase() {
        String pnr = PnrGenerator.generate();
        assertTrue(pnr.matches("[A-Z0-9]+"));
    }
}
