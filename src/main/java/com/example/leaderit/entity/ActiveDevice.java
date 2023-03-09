package com.example.leaderit.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "active_devices")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class ActiveDevice {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "device_id", nullable = false)
    private IotDevice iotDevice;

    @Column(name = "date_first_activity", nullable = false)
    private LocalDateTime dateFirstActivity;

    @Column(name = "date_last_activity", nullable = false)
    private LocalDateTime dateLastActivity;
}
