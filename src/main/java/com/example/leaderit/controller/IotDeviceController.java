package com.example.leaderit.controller;

import com.example.leaderit.dto.*;
import com.example.leaderit.service.ActiveDeviceService;
import com.example.leaderit.service.EventService;
import com.example.leaderit.service.IotDeviceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/api/devices")
public class IotDeviceController {

    private final IotDeviceService iotDeviceService;
    private final EventService eventService;
    private final ActiveDeviceService activeDeviceService;

    @Autowired
    public IotDeviceController(IotDeviceService iotDeviceService, EventService eventService, ActiveDeviceService activeDeviceService) {
        this.iotDeviceService = iotDeviceService;
        this.eventService = eventService;
        this.activeDeviceService = activeDeviceService;
    }

    // 1) Добавление нового устройства
    // - В ответ возвращается секретный ключ
    // TODO валидация ключа в сервисе, ключ возвращается только здесь
    @PostMapping
    public IotDeviceDto addDevice(@RequestBody IotDeviceDto iotDeviceDto) {
        return iotDeviceService.addDevice(iotDeviceDto);
    }

    // 2) Сохранение события от IoT устройства
    // - При обработке запроса, требуется выполнять валидацию секретного ключа
    // - Контракт тела запроса обязательно должен принимать тип события
    // - Требуется предусмотреть что объект с полезной нагрузкой для каждого устройства может
    // содержать уникальный набор полей
    // - При обработке события считаем, что устройство активно и добавляем/изменяем запись в
    // таблице "Перечень активных устройств"
    // TODO @Valid, добавить условия в EventDto
    @PostMapping("/{deviceSerialNumber}/events")
    public EventDto addEvent(@PathVariable String deviceSerialNumber, @RequestBody @Valid EventRequest eventRequest) {
        return eventService.addEventBySerialNumber(deviceSerialNumber, eventRequest.getSecretKey(), eventRequest.getEventDto());
    }

    // 3) Получить информацию об активных устройствах
    // - Устройство которое было не активно более 30 минут должно быть убрано из таблицы "Перечень
    // активных устройств"
    // TODO мб. добавить в ответе серийный номер устройства
    @GetMapping("/active")
    public List<ActiveDeviceDto> getActiveDevices() {
        return activeDeviceService.getAllActiveDevices();
    }

    // 4) Получить информацию о конкретном устройстве по серийному номеру
    @GetMapping("/{deviceSerialNumber}")
    public IotDeviceDto getDevice(@PathVariable String deviceSerialNumber) {
        return iotDeviceService.getDeviceBySerialNumber(deviceSerialNumber);
    }

    // 5) Получить информацию о всех добавленных в систему устройств
    // - Фильтр по типу устройства
    // - Фильтр по дате добавления
    // - Пагинация
    // TODO не возвращать secret key, только при добавлении устройства
    @GetMapping()
    public List<IotDeviceDto> getAllDevices(
            @RequestParam(required = false) List<String> deviceType,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime startDateAdded,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime endDateAdded,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {

        return iotDeviceService.getAllDevices(deviceType, startDateAdded, endDateAdded, page, size);
    }

    // 6) Получить информацию о событиях конкретного устройства по серийному номеру
    // - Фильтр по дате добавления
    // - Пагинация
    @GetMapping("/{deviceSerialNumber}/events")
    public List<EventDto> getEventsBySerialNumber(
            @PathVariable String deviceSerialNumber,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime startDateCreated,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime endDateCreated,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {

        return eventService.getEventsBySerialNumber(deviceSerialNumber, startDateCreated, endDateCreated, page, size);
    }

    // 7) Получить статистику по указанному периоду времени о количестве полученных событий,
    // сгруппированном по типам устройств
    @GetMapping("/statistics")
    public List<StatisticsResponse> getStatistics(
        @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime startDate,
        @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime endDate) {

            return eventService.getStatistics(startDate, endDate);
    }
}
