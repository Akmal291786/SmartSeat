package com.akmal.smartseat.repository;
import com.akmal.smartseat.entity.Reservation; import org.springframework.data.jpa.repository.JpaRepository;
public interface ReservationRepository extends JpaRepository<Reservation,Long>{}
