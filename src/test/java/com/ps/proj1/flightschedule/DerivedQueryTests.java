package com.ps.proj1.flightschedule;

import static org.assertj.core.api.Assertions.assertThat;

import com.ps.proj1.flightschedule.entity.Flight;
import com.ps.proj1.flightschedule.repository.FlightRepository;
import java.time.LocalDateTime;
import java.util.List;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@DataJpaTest
@AutoConfigureTestDatabase(replace = Replace.NONE)
public class DerivedQueryTests {

  @Autowired
  private FlightRepository flightRepository;

  @Before
  public void setUp(){
    flightRepository.deleteAll();
  }

  @Test
  public void shouldFindFlightsFromLondon(){
    Flight flight1 = createFlight("London");
    Flight flight2 = createFlight("London");
    Flight flight3 = createFlight("New York");

    flightRepository.save(flight1);
    flightRepository.save(flight2);
    flightRepository.save(flight3);

    assertThat(flightRepository.count()).isEqualTo(3);

    List<Flight> flightFromLondon = flightRepository.findByOrigin("London");

    assertThat(flightFromLondon).hasSize(2);
    assertThat(flightFromLondon.get(0)).isEqualToComparingFieldByField(flight1);
    assertThat(flightFromLondon.get(1)).isEqualToComparingFieldByField(flight2);

  }

  private Flight createFlight(String origin){
    Flight flight = new Flight();
    flight.setOrigin(origin);
    flight.setDestination("Paris");
    flight.setScheduledAt(LocalDateTime.parse("2020-08-21T12:00:00"));
    return flight;
  }

}
