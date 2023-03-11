package com.example.leaderit.dto;

import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class EventRequest {
    @NotNull(message = "Must provide event")
    EventDto eventDto;
    @NotNull(message = "Must provide secret key")
    String secretKey;
}
