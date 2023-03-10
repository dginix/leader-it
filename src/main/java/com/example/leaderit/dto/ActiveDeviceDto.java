package com.example.leaderit.dto;

import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class ActiveDeviceDto {
    private Long id;
    private Long iotDeviceId;
    private LocalDateTime dateFirstActivity;
    private LocalDateTime dateLastActivity;
}