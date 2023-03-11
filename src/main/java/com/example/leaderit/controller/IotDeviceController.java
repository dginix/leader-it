package com.example.leaderit.controller;

import com.example.leaderit.dto.ActiveDeviceDto;
import com.example.leaderit.dto.EventDto;
import com.example.leaderit.dto.IotDeviceDto;
import com.example.leaderit.dto.StatisticsResponse;
import com.example.leaderit.service.IotDeviceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;

@RestController
@RequestMapping("/api/devices")
public class IotDeviceController {

    private final IotDeviceService iotDeviceService;

    @Autowired
    public IotDeviceController(IotDeviceService iotDeviceService) {
        this.iotDeviceService = iotDeviceService;
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
    public EventDto addEvent(@RequestBody EventDto eventDto) {
        return eventDto;
    }

    // 3) Получить информацию об активных устройствах
    // - Устройство которое было не активно более 30 минут должно быть убрано из таблицы "Перечень
    // активных устройств"
    // TODO мб. добавить в ответе серийный номер устройства
    @GetMapping("/active")
    public List<ActiveDeviceDto> getActiveDevices() {
        return Collections.emptyList();
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
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime dateAdded,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {

        return Collections.emptyList();
    }

    // 7) Получить статистику по указанному периоду времени о количестве полученных событий,
    // сгруппированном по типам устройств
    @GetMapping("/statistics")
    public List<StatisticsResponse> getStatistics(
        @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime startDate,
        @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime endDate) {

            return Collections.emptyList();
    }
}
