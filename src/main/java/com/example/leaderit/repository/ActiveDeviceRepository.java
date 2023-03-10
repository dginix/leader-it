package com.example.leaderit.repository;

import com.example.leaderit.entity.ActiveDevice;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.time.LocalDateTime;

@Repository
public interface ActiveDeviceRepository extends JpaRepository<ActiveDevice, Long> {
    ActiveDevice findByIotDeviceSerialNumber(String serialNumber);

    Page<ActiveDevice> findAll(Pageable page);

    @Modifying
    @Query("DELETE FROM ActiveDevice a WHERE a.dateOfLastActivity < :inactiveTime")
    void deleteInactiveDevices(@Param("inactiveTime") LocalDateTime inactiveTime);
}
