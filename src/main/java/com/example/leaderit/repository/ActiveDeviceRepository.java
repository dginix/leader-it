package com.example.leaderit.repository;

import com.example.leaderit.entity.ActiveDevice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Optional;

@Repository
public interface ActiveDeviceRepository extends JpaRepository<ActiveDevice, Long> {
    Optional<ActiveDevice> findByIotDeviceId(Long Long);

    @Modifying
    @Transactional
    @Query("DELETE FROM ActiveDevice a WHERE a.dateLastActivity < :inactiveTime")
    void deleteInactiveDevices(@Param("inactiveTime") LocalDateTime inactiveTime);
}
