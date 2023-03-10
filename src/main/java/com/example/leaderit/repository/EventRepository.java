package com.example.leaderit.repository;

import com.example.leaderit.entity.Event;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;

@Repository
public interface EventRepository extends JpaRepository<Event, Long> {
    Page<Event> findAll(Pageable pageable);
    Page<Event> findByIotDeviceSerialNumberAndDateCreatedBetween(String serialNumber, LocalDateTime fromDate, LocalDateTime toDate, Pageable pageable);
}
