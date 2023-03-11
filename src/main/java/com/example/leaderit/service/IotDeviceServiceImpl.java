package com.example.leaderit.service;

import com.example.leaderit.dto.IotDeviceDto;
import com.example.leaderit.entity.IotDevice;
import com.example.leaderit.exception.NoSuchDeviceFoundException;
import com.example.leaderit.repository.IotDeviceRepository;
import com.example.leaderit.security.SecretKeyGenerator;
import com.example.leaderit.util.dtomapper.IotDeviceMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;


@Service
public class IotDeviceServiceImpl implements IotDeviceService {

    private final IotDeviceRepository iotDeviceRepository;
    private final IotDeviceMapper iotDeviceMapper;
    private final SecretKeyGenerator secretKeyGenerator;

    @Autowired
    public IotDeviceServiceImpl(IotDeviceRepository iotDeviceRepository, IotDeviceMapper iotDeviceMapper, SecretKeyGenerator secretKeyGenerator) {
        this.iotDeviceRepository = iotDeviceRepository;
        this.iotDeviceMapper = iotDeviceMapper;
        this.secretKeyGenerator = secretKeyGenerator;
    }

    @Override
    public IotDeviceDto addDevice(IotDeviceDto deviceDto) {
        IotDevice newIotDevice = iotDeviceMapper.toEntity(deviceDto);
        newIotDevice.setSecretKey(secretKeyGenerator.generateSecretKey());
        newIotDevice.setDateAdded(LocalDateTime.now());
        return iotDeviceMapper.toDtoOnCreate(
                iotDeviceRepository.save(newIotDevice)
        );
    }

    @Override
    public IotDeviceDto getDeviceBySerialNumber(String serialNumber) {
        IotDevice iotDevice = iotDeviceRepository.findBySerialNumber(serialNumber).orElseThrow(
                () -> new NoSuchDeviceFoundException("Device with serial number " + serialNumber + " not found")
        );

        return iotDeviceMapper.toDTO(iotDevice);
    }

    @Override
    public List<IotDeviceDto> getAllDevices(List<String> deviceType, LocalDateTime startDateAdded, LocalDateTime endDateAdded, int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<IotDevice> res = iotDeviceRepository.getAllDevicesFiltered(deviceType, startDateAdded, endDateAdded, pageable);
        return res.stream().map(iotDeviceMapper::toDTO).collect(Collectors.toList());
    }
}
