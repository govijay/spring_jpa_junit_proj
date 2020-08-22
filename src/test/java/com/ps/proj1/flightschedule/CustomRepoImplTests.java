package com.ps.proj1.flightschedule;

import static org.assertj.core.api.Assertions.*;

import com.ps.proj1.flightschedule.entity.Flight;
import com.ps.proj1.flightschedule.repository.FlightRepository;
import java.time.LocalDateTime;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

@DataJpaTest
@AutoConfigureTestDatabase(replace = Replace.NONE)
public class CustomRepoImplTests {

  @Autowired
  FlightRepository flightRepository;

  @Test
  public void shouldSaveCustomImpl(){

   Flight toDelete = createFlight("London");

   Flight toKeep = createFlight("Paris");

   flightRepository.save(toDelete);
   flightRepository.save(toKeep);

   flightRepository.deleteByOrigin("London");

   assertThat(flightRepository.findAll()).hasSize(1).first().isEqualTo(toKeep);


  }

  private Flight createFlight(String origin) {
    Flight flight = new Flight();
    flight.setOrigin(origin);
    flight.setDestination("Madrid");
    flight.setScheduledAt(LocalDateTime.now());

    return flight;
  }

}
