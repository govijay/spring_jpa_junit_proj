package com.ps.proj1.flightschedule.service;

import com.ps.proj1.flightschedule.entity.Flight;
import com.ps.proj1.flightschedule.repository.FlightRepository;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
public class FlightService {

  private final FlightRepository flightRepository;

  public FlightService(FlightRepository flightRepository){
    this.flightRepository = flightRepository;
  }

  public void saveFlight(Flight flight) {
    flightRepository.save(flight);
    // other queries..
    throw new RuntimeException("Failed");
  }

  @Transactional
  public void saveFlightByTransaction(Flight flight) {
    flightRepository.save(flight);
    // other queries..
    throw new RuntimeException("Failed");
  }


}
