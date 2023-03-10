package com.example.leaderit.dto;

import java.time.LocalDateTime;
import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class IotDeviceDto {
    private Long id;
    private String serialNumber;
    private String deviceName;
    private String deviceType;
    private String secretKey;
    private LocalDateTime dateAdded;
}