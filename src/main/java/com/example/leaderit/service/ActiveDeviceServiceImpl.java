package com.example.leaderit.service;

import com.example.leaderit.dto.ActiveDeviceDto;
import com.example.leaderit.entity.ActiveDevice;
import com.example.leaderit.entity.IotDevice;
import com.example.leaderit.repository.ActiveDeviceRepository;
import com.example.leaderit.util.dtomapper.ActiveDeviceMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ActiveDeviceServiceImpl implements ActiveDeviceService {

    private final ActiveDeviceRepository activeDeviceRepository;
    private final ActiveDeviceMapper activeDeviceMapper;

    @Autowired
    public ActiveDeviceServiceImpl(ActiveDeviceRepository activeDeviceRepository, ActiveDeviceMapper activeDeviceMapper) {
        this.activeDeviceRepository = activeDeviceRepository;
        this.activeDeviceMapper = activeDeviceMapper;
    }

    @Override
    public ActiveDevice setDeviceActive(IotDevice iotDevice) {
        Optional<ActiveDevice> currentActivity = activeDeviceRepository.findByIotDeviceId(iotDevice.getId());
        if (currentActivity.isPresent()) {
            currentActivity.get().setDateLastActivity(LocalDateTime.now());
            return activeDeviceRepository.save(currentActivity.get());
        }
        else {
            return activeDeviceRepository.save(
                    new ActiveDevice(null, iotDevice, LocalDateTime.now(), LocalDateTime.now())
            );
        }
    }

    @Override
    public List<ActiveDeviceDto> getAllActiveDevices() {
        activeDeviceRepository.deleteInactiveDevices(LocalDateTime.now().minusMinutes(30));
        return activeDeviceRepository.findAll().stream().map(activeDeviceMapper::toDTO).collect(Collectors.toList());
    }
}
