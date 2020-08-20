package com.ps.proj1.flightschedule;

import static org.assertj.core.api.Assertions.assertThat;

import com.ps.proj1.flightschedule.entity.Flight;
import com.ps.proj1.flightschedule.repository.FlightRepository;
import java.time.LocalDateTime;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import org.assertj.core.api.Assertions;
import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;


@DataJpaTest
@AutoConfigureTestDatabase(replace = Replace.NONE)
class FlightScheduleRepoTests {

  @Autowired
  EntityManager entityManager;

  @Autowired
  private FlightRepository flightRepository;

  @Test
  void injectedComponentsAreNotNull(){
    assertThat(flightRepository).isNotNull();
  }

 @Test
 public void verifyFlightCanBeSaved(){
   final Flight flight = new Flight();
   flight.setOrigin("Amstredam");
   flight.setDestination("New York");
   flight.setScheduledAt(LocalDateTime.parse("2020-08-19T12:00:00"));
   flightRepository.save(flight);

   final TypedQuery<Flight> results = entityManager.createQuery("SELECT f FROM Flight f",Flight.class);
   final List<Flight> resultList = results.getResultList();

   assertThat(resultList).hasSize(1).first().isEqualTo(flight);
 }

 @Test
 public void shouldPerformCRUDOperation(){
    final Flight flight = new Flight();
    flight.setOrigin("Paris");
    flight.setDestination("New York");
    flight.setScheduledAt(LocalDateTime.parse("2020-08-19T12:00:01"));
    flightRepository.save(flight);

   assertThat(flightRepository.findAll()).hasSize(1).first().isEqualTo(flight);

   flightRepository.deleteById(flight.getId());

   assertThat(flightRepository.count()).isZero();
    

    
 }



}
