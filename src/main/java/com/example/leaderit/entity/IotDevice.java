package com.example.leaderit.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "iot_device")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class IotDevice {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "serial_number", unique = true, nullable = false)
    private String serialNumber;

    @Column(name = "device_name", nullable = false)
    private String deviceName;

    @Column(name = "device_type", nullable = false)
    private String deviceType;

    @Column(name = "secret_key", nullable = false)
    private String secretKey;

    @Column(name = "date_added", nullable = false)
    private LocalDateTime dateAdded;

    public IotDevice(Long id) {
        this.id = id;
    }
}
