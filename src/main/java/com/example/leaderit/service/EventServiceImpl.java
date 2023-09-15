package com.example.leaderit.service;

import com.example.leaderit.dto.EventDto;
import com.example.leaderit.dto.StatisticsResponse;
import com.example.leaderit.entity.Event;
import com.example.leaderit.entity.IotDevice;
import com.example.leaderit.exception.NoSuchDeviceFoundException;
import com.example.leaderit.exception.WrongSecretKeyException;
import com.example.leaderit.repository.EventRepository;
import com.example.leaderit.repository.IotDeviceRepository;
import com.example.leaderit.util.dto_mapper.EventMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.validation.Valid;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class EventServiceImpl implements EventService {

    private final EventRepository eventRepository;
    private final EventMapper eventMapper;
    private final IotDeviceRepository iotDeviceRepository;
    private final ActiveDeviceService activeDeviceService;

    @Autowired
    public EventServiceImpl(EventRepository eventRepository,
                            EventMapper eventMapper,
                            IotDeviceRepository iotDeviceRepository,
                            ActiveDeviceService activeDeviceService) {

        this.eventRepository = eventRepository;
        this.eventMapper = eventMapper;
        this.iotDeviceRepository = iotDeviceRepository;
        this.activeDeviceService = activeDeviceService;
    }

    @Transactional
    @Override
    public EventDto addEventBySerialNumber(String serialNumber, String secretKey, @Valid EventDto eventDto) {
        IotDevice iotDevice = iotDeviceRepository.findBySerialNumber(serialNumber).orElseThrow(
                () -> new NoSuchDeviceFoundException("Device with serial number " + serialNumber + " not found")
        );

        if (!iotDevice.getSecretKey().equals(secretKey)) {
            throw new WrongSecretKeyException("Wrong secret key");
        }

        activeDeviceService.setDeviceActive(iotDevice);

        Event eventToSave = eventMapper.toEntity(eventDto);
        eventToSave.setDateCreated(LocalDateTime.now());
        eventToSave.setIotDevice(iotDevice);

        return eventMapper.toDTO(eventRepository.save(eventToSave));
    }

    @Override
    public List<EventDto> getEventsBySerialNumber(String serialNumber, LocalDateTime startDateCreated, LocalDateTime endDateCreated, int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        return eventRepository.findEventsBySerialNumber(serialNumber, startDateCreated, endDateCreated, pageable).stream()
                .map(eventMapper::toDTO)
                .collect(Collectors.toList());
    }

    public List<StatisticsResponse> getStatistics(LocalDateTime startDate, LocalDateTime endDate) {
        return eventRepository.getStatistics(startDate, endDate);
    }
}
