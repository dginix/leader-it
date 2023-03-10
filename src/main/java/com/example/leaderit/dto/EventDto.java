package com.example.leaderit.dto;

import lombok.*;
import java.time.LocalDateTime;
import java.util.Map;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class EventDto {
    private Long id;
    private Long iotDeviceId;
    private String eventType;
    private Map<String, Object> payload;
    private LocalDateTime dateCreated;
}