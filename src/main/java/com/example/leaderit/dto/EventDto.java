package com.example.leaderit.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.Map;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class EventDto {
    private Long id;
    private Long iotDeviceId;
    private String iotDeviceSerialNumber;
    @NotNull(message = "Must provide event type")
    private String eventType;
    @NotNull(message = "Must provide payload")
    private Map<String, Object> payload;
    private LocalDateTime dateCreated;
}