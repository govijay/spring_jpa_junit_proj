package com.ps.proj1.flightschedule.service;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

@DataJpaTest
class FlightServiceTest {

  @Autowired
  FlightService flightService;

  @Test
  void createFlight() {
    flightService.createFlight();

  }
}