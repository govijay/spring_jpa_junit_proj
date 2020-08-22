package com.ps.proj1.flightschedule;

import static org.assertj.core.api.Assertions.*;

import com.ps.proj1.flightschedule.entity.Flight;
import com.ps.proj1.flightschedule.repository.FlightRepository;
import com.ps.proj1.flightschedule.service.FlightService;
import java.time.LocalDateTime;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class TransactionalTests {

  @Autowired
  FlightRepository flightRepository;

  @Autowired
  FlightService flightService;


  @BeforeEach
  public void SetUp(){
    flightRepository.deleteAll();
  }

  @Test
  public void ShouldNotRollBackWhenTheresNoTransaction(){

    final Flight flight = createFlight("London");

    try{
      flightService.saveFlight(flight);
    }catch (Exception e){
    // do nothing
    }finally{
      assertThat(flightRepository.findAll()).isNotEmpty();
    }
  }

  @Test
  public void ShouldRollBackWhenTheresTransaction(){

    final Flight flight = createFlight("London");

    try{
      flightService.saveFlightByTransaction(flight);
    }catch (Exception e){
      // do nothing
    }finally{
      assertThat(flightRepository.findAll()).isEmpty();
    }
  }

  private Flight createFlight(String origin) {

    Flight flight = new Flight();
    flight.setOrigin(origin);
    flight.setDestination("Paris");
    flight.setScheduledAt(LocalDateTime.now());
    return flight;
  }

}
