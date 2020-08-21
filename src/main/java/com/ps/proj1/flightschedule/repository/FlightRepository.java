package com.ps.proj1.flightschedule.repository;

import java.io.Serializable;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.ps.proj1.flightschedule.entity.Flight;

@Repository
public interface FlightRepository<T,Long extends Serializable> extends JpaRepository<Flight,Long> {


  List<Flight> findByOrigin(String origin);
}
