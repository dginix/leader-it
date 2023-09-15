package com.example.leaderit.service;

import com.example.leaderit.dto.ActiveDeviceDto;
import com.example.leaderit.entity.ActiveDevice;
import com.example.leaderit.entity.IotDevice;
import com.example.leaderit.repository.ActiveDeviceRepository;
import com.example.leaderit.util.dto_mapper.ActiveDeviceMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class ActiveDeviceServiceImpl implements ActiveDeviceService {

    private final ActiveDeviceRepository activeDeviceRepository;
    private final ActiveDeviceMapper activeDeviceMapper;

    @Override
    @Transactional
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
    @Transactional
    public List<ActiveDeviceDto> getAllActiveDevices() {
        return activeDeviceRepository.findAll().stream().map(activeDeviceMapper::toDTO).collect(Collectors.toList());
    }

    @Override
    @Transactional
    public void deleteInactiveDevices() {
        log.info("Start deleting inactive devices");
        activeDeviceRepository.deleteInactiveDevices(LocalDateTime.now().minusMinutes(30));
    }
}
