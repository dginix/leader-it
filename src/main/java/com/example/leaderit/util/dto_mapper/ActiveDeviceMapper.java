package com.example.leaderit.util.dto_mapper;

import com.example.leaderit.dto.ActiveDeviceDto;
import com.example.leaderit.entity.ActiveDevice;
import com.example.leaderit.entity.IotDevice;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ActiveDeviceMapper {
    private final ModelMapper modelMapper;

    @Autowired
    public ActiveDeviceMapper(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    public ActiveDeviceDto toDTO(ActiveDevice activeDevice) {
        ActiveDeviceDto activeDeviceDTO = modelMapper.map(activeDevice, ActiveDeviceDto.class);
        activeDeviceDTO.setIotDeviceId(activeDevice.getIotDevice().getId());
        return activeDeviceDTO;
    }

    public ActiveDevice toEntity(ActiveDeviceDto activeDeviceDTO, IotDevice iotDevice) {
        ActiveDevice activeDevice = modelMapper.map(activeDeviceDTO, ActiveDevice.class);
        activeDevice.setIotDevice(iotDevice);
        return activeDevice;
    }
}