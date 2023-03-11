package com.example.leaderit.service;

import com.example.leaderit.dto.ActiveDeviceDto;
import com.example.leaderit.entity.ActiveDevice;
import com.example.leaderit.entity.IotDevice;

import java.util.List;

public interface ActiveDeviceService {
    ActiveDevice setDeviceActive(IotDevice iotDevice);
    List<ActiveDeviceDto> getAllActiveDevices();
}
