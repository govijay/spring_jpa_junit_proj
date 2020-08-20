package com.ps.proj1.flightschedule.service;

import com.ps.proj1.flightschedule.entity.Flight;
import com.ps.proj1.flightschedule.repository.FlightRepository;
import java.time.LocalDateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class FlightService {

  @Autowired
  FlightRepository flightRepository;

  public void createFlight(){
    final Flight flight = new Flight();
    flight.setOrigin("Amstredam");
    flight.setDestination("New York");
    flight.setScheduledAt(LocalDateTime.parse("2020-08-19T12:00:00"));
    flightRepository.save(flight);
  }

}
