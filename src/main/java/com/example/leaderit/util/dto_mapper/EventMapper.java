package com.example.leaderit.util.dto_mapper;

import com.example.leaderit.dto.EventDto;
import com.example.leaderit.entity.Event;
import com.example.leaderit.entity.IotDevice;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class EventMapper {
    private final ModelMapper modelMapper;

    @Autowired
    public EventMapper(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    public EventDto toDTO(Event event) {
        EventDto eventDTO = modelMapper.map(event, EventDto.class);
        eventDTO.setIotDeviceId(event.getIotDevice().getId());
        eventDTO.setIotDeviceSerialNumber(event.getIotDevice().getSerialNumber());
        return eventDTO;
    }

    public Event toEntity(EventDto eventDTO) {
        Event event = modelMapper.map(eventDTO, Event.class);
        event.setIotDevice(new IotDevice(eventDTO.getIotDeviceId()));
        return event;
    }
}