package com.ps.proj1.flightschedule.repository;

import java.io.Serializable;
import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.ps.proj1.flightschedule.entity.Flight;

@Repository
public interface FlightRepository<T,Long extends Serializable> extends JpaRepository<Flight,Long>,DeleteByOriginRepository {


  List<Flight> findByOrigin(String origin);

  List<Flight> findByOriginAndDestination(String origin, String destination);

  List<Flight> findByOriginOrDestination(String origin, String destination);

  List<Flight> findByOriginIn(String ... origins);

  List<Flight> findByOriginIgnoreCase(String origin);


  Page<Flight> findByOrigin(String origin,
      Pageable pageRequest);
}
