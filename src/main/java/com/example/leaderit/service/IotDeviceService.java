package com.example.leaderit.service;

import com.example.leaderit.dto.IotDeviceDto;

import java.time.LocalDateTime;
import java.util.List;

public interface IotDeviceService {
    IotDeviceDto addDevice(IotDeviceDto deviceDto);
    IotDeviceDto getDeviceBySerialNumber(String serialNumber);
    List<IotDeviceDto> getAllDevices( List<String> deviceType,
                                             LocalDateTime startDateAdded,
                                             LocalDateTime endDateAdded,
                                             int page,
                                             int size);

}
