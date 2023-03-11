package com.example.leaderit.service;

import com.example.leaderit.dto.EventDto;
import com.example.leaderit.dto.StatisticsResponse;
import java.time.LocalDateTime;
import java.util.List;

public interface EventService {
    EventDto addEventBySerialNumber(String serialNumber, String secretKey, EventDto eventDto);
    List<EventDto> getEventsBySerialNumber(String serialNumber, LocalDateTime startDateCreated, LocalDateTime endDateCreated, int page, int size);
    List<StatisticsResponse> getStatistics(LocalDateTime startDate, LocalDateTime endDate);
}
