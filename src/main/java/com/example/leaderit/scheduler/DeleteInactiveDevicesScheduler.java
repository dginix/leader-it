package com.example.leaderit.scheduler;

import com.example.leaderit.service.ActiveDeviceService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class DeleteInactiveDevicesScheduler {

    private final ActiveDeviceService activeDeviceService;

    @Scheduled(cron = "${scheduler.delete-inactive-devices}")
    public void deleteInactiveDevices() {
        activeDeviceService.deleteInactiveDevices();
    }
}
