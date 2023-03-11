package com.example.leaderit.dto;

import java.time.LocalDateTime;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class IotDeviceDto {
    private Long id;
    private String serialNumber;
    private String deviceName;
    private String deviceType;
    private String secretKey;
    private LocalDateTime dateAdded;
}