package com.example.leaderit.util.dto_mapper;

import com.example.leaderit.dto.IotDeviceDto;
import com.example.leaderit.entity.IotDevice;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class IotDeviceMapper {
    private final ModelMapper modelMapper;

    public IotDeviceDto toDTO(IotDevice iotDevice) {
        IotDeviceDto iotDeviceDto = modelMapper.map(iotDevice, IotDeviceDto.class);
        iotDeviceDto.setSecretKey(null);
        return iotDeviceDto;
    }

    public IotDeviceDto toDtoOnCreate(IotDevice iotDevice) {
        return modelMapper.map(iotDevice, IotDeviceDto.class);
    }

    public IotDevice toEntity(IotDeviceDto iotDeviceDto) {
        return modelMapper.map(iotDeviceDto, IotDevice.class);
    }
}