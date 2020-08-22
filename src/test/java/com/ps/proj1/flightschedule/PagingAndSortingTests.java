package com.ps.proj1.flightschedule;

import static org.assertj.core.api.Assertions.assertThat;

import com.ps.proj1.flightschedule.entity.Flight;
import com.ps.proj1.flightschedule.repository.FlightRepository;
import java.time.LocalDateTime;
import java.util.Iterator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;

@DataJpaTest
@AutoConfigureTestDatabase(replace = Replace.NONE)
public class PagingAndSortingTests {

  @Autowired
  FlightRepository flightRepository;

  @BeforeEach
  public void setUp(){
    flightRepository.deleteAll();
  }

  @Test
  public void shouldSortFlightsByDestination(){
    final Flight madrid = createFlight("Madrid");
    final Flight london = createFlight("London");
    final Flight paris = createFlight("Paris");

    flightRepository.save(madrid);
    flightRepository.save(london);
    flightRepository.save(paris);

    final Iterable<Flight> flights = flightRepository.findAll(Sort.by("destination"));

    assertThat(flights).hasSize(3);

    final Iterator<Flight> iterator = flights.iterator();

    assertThat(iterator.next().getDestination()).isEqualTo("London");
    assertThat(iterator.next().getDestination()).isEqualTo("Madrid");
    assertThat(iterator.next().getDestination()).isEqualTo("Paris");
  }

  @Test
  public void ShouldSortFlightsByScheduledAndThenName(){
    final LocalDateTime now = LocalDateTime.now();
    final Flight paris1 = createFlight("Paris",now);
    final Flight paris2 = createFlight("Paris",now.plusHours(2));
    final Flight paris3 = createFlight("Paris", now.minusHours(1));

    final Flight london1 = createFlight("London",now.plusHours(1));
    final Flight london2 = createFlight("London",now);

    flightRepository.save(paris1);
    flightRepository.save(paris2);
    flightRepository.save(paris3);
    flightRepository.save(london1);
    flightRepository.save(london2);

    final Iterable<Flight> flights = flightRepository.findAll(Sort.by("destination","scheduledAt"));

    assertThat(flights).hasSize(5);

    final Iterator<Flight> iterator = flights.iterator();

    assertThat(iterator.next()).isEqualToComparingFieldByField(london2);
    assertThat(iterator.next()).isEqualToComparingFieldByField(london1);
    assertThat(iterator.next()).isEqualToComparingFieldByField(paris3);
    assertThat(iterator.next()).isEqualToComparingFieldByField(paris1);
    assertThat(iterator.next()).isEqualToComparingFieldByField(paris2);
  }


  @Test
  public void shouldPageResults(){

    for (int i = 0; i < 50; i++) {
      flightRepository.save(createFlight(String.valueOf(i)));
    }
    final Page<Flight> page = flightRepository.findAll(PageRequest.of(2,5));

    assertThat(page.getTotalElements()).isEqualTo(50);
    assertThat(page.getNumberOfElements()).isEqualTo(5);
    assertThat(page.getTotalPages()).isEqualTo(10);

    assertThat(page.getContent()).extracting(Flight::getDestination).containsExactly("10","11","12","13","14");
  }

  @Test
  public void shouldPageOrSortResults(){

    for (int i = 0; i < 50; i++) {
      flightRepository.save(createFlight(String.valueOf(i)));
    }
    final Page<Flight> page = flightRepository.findAll(PageRequest.of(2,5,Sort.by(Direction.DESC,"destination")));

    assertThat(page.getTotalElements()).isEqualTo(50);
    assertThat(page.getNumberOfElements()).isEqualTo(5);
    assertThat(page.getTotalPages()).isEqualTo(10);

    assertThat(page.getContent()).extracting(Flight::getDestination).containsExactly("44","43","42","41","40");
  }

  @Test
  public void shouldPageOrSortByDerivedQueryResults(){

    for (int i = 0; i < 10; i++) {
      final Flight flight = createFlight(String.valueOf(i));
      flight.setOrigin("London");
      flightRepository.save(flight);
    }

    for (int i = 0; i < 10; i++) {
      final Flight flight = createFlight(String.valueOf(i));
      flight.setOrigin("Paris");
      flightRepository.save(flight);
    }

    final Page<Flight> page = flightRepository.findByOrigin("London",PageRequest.of(0,5,Sort.by(Direction.DESC,"destination")));

    assertThat(page.getTotalElements()).isEqualTo(10);
    assertThat(page.getNumberOfElements()).isEqualTo(5);
    assertThat(page.getTotalPages()).isEqualTo(2);

    assertThat(page.getContent()).extracting(Flight::getDestination).containsExactly("9","8","7","6","5");
  }

  private Flight createFlight(String destination, LocalDateTime scheduledAt) {
    Flight flight = new Flight();
    flight.setDestination(destination);
    flight.setOrigin("London");
    flight.setScheduledAt(scheduledAt);
    return flight;
  }

  private Flight createFlight(String destination) {
    Flight flight = new Flight();
    flight.setOrigin("London");
    flight.setDestination(destination);
    flight.setScheduledAt(LocalDateTime.parse("2020-08-21T12:00:00"));
    return flight;
  }

}
