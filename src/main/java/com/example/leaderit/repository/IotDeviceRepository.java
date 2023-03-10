package com.example.leaderit.repository;

import com.example.leaderit.entity.IotDevice;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;


@Repository
public interface IotDeviceRepository extends JpaRepository<IotDevice, Long> {
    IotDevice findBySerialNumber(String serialNumber);
    Page<IotDevice> findAll(Pageable pageable);
    Page<IotDevice> findByDeviceTypeIn(List<String> deviceType, Pageable pageable);
    Page<IotDevice> findByDateAddedBetween(LocalDateTime fromDate, LocalDateTime toDate, Pageable pageable);
}
