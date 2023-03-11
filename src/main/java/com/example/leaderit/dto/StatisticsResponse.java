package com.example.leaderit.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Setter
@Getter
public class StatisticsResponse {
    private String deviceType;
    private Long eventCount;
}
