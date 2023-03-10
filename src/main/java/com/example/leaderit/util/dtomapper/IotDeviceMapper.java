package com.example.leaderit.util.dtomapper;

import com.example.leaderit.dto.IotDeviceDto;
import com.example.leaderit.entity.IotDevice;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class IotDeviceMapper {
    private final ModelMapper modelMapper;

    @Autowired
    public IotDeviceMapper(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    public IotDeviceDto toDto(IotDevice iotDevice) {
        return modelMapper.map(iotDevice, IotDeviceDto.class);
    }

    public IotDevice toEntity(IotDeviceDto iotDeviceDto) {
        return modelMapper.map(iotDeviceDto, IotDevice.class);
    }
}