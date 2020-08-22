package com.ps.proj1.flightschedule;

import static org.assertj.core.api.Assertions.assertThat;

import com.ps.proj1.flightschedule.entity.Flight;
import com.ps.proj1.flightschedule.repository.FlightRepository;
import java.time.LocalDateTime;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;


@DataJpaTest
@AutoConfigureTestDatabase(replace = Replace.NONE)
public class DerivedQueryTests {

  @Autowired
  private FlightRepository flightRepository;

  @BeforeEach
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

    final List<Flight> flightFromLondon = flightRepository.findByOrigin("London");

    assertThat(flightFromLondon).hasSize(2);
    assertThat(flightFromLondon.get(0)).isEqualToComparingFieldByField(flight1);
    assertThat(flightFromLondon.get(1)).isEqualToComparingFieldByField(flight2);

  }

  @Test
  public void shouldFindFlightsFromOriginAndDestination(){
    Flight flight1 = createFlight("London","Paris");
    Flight flight2 = createFlight("London","New York");
    Flight flight3 = createFlight("London","Paris");

    flightRepository.save(flight1);
    flightRepository.save(flight2);
    flightRepository.save(flight3);

    final List<Flight> flightsFromLondonToParis = flightRepository.findByOriginAndDestination("London","Paris");

    assertThat(flightsFromLondonToParis).hasSize(2);

    assertThat(flightsFromLondonToParis.get(0)).isEqualToComparingFieldByField(flight1);
    assertThat(flightsFromLondonToParis.get(1)).isEqualToComparingFieldByField(flight3);
  }

  @Test
  public void ShouldFindFlightsFromOriginOrDestination(){
    Flight flight1 = createFlight("London");
    Flight flight2 = createFlight("Paris");
    Flight flight3 = createFlight("Madrid","New York");

    flightRepository.save(flight1);
    flightRepository.save(flight2);
    flightRepository.save(flight3);

    final List<Flight> flightFromLondonOrNewYork = flightRepository.findByOriginOrDestination("London","New York");

    assertThat(flightFromLondonOrNewYork).hasSize(2);

  }

  @Test
  public void ShouldFindFlightsFromOrigins(){

    Flight flight1 = createFlight("London");
    Flight flight2 = createFlight("Paris");
    Flight flight3 = createFlight("Madrid","New York");

    flightRepository.save(flight1);
    flightRepository.save(flight2);
    flightRepository.save(flight3);

    final List<Flight> flightsFromOriginLondonParis = flightRepository.findByOriginIn("London","Paris");

    assertThat(flightsFromOriginLondonParis).hasSize(2);

  }


  @Test
  public void ShouldFindFlightsFromOriginIgnoringCase(){
    Flight flight1 = createFlight("LONDON");


    flightRepository.save(flight1);

    final List<Flight> flightsFromLondon = flightRepository.findByOriginIgnoreCase("London");

    assertThat(flightsFromLondon).hasSize(1).first().isEqualToComparingFieldByField(flight1);


  }

  private Flight createFlight(String origin, String destination) {
    Flight flight = new Flight();
    flight.setOrigin(origin);
    flight.setDestination(destination);
    flight.setScheduledAt(LocalDateTime.parse("2020-08-21T12:00:00"));
    return flight;
  }

  private Flight createFlight(String origin){
      return createFlight(origin,"Madrid");
  }

}
