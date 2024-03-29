# leader-it
Тестовое задание для компании [Лидер-ИТ](https://leader-it.com/)

## Требования
Чтобы собрать и запустить приложение необходимо:

- [JDK 8](https://www.oracle.com/cis/java/technologies/javase/javase8-archive-downloads.html)
- [Maven 3](https://maven.apache.org)
- [Spring Boot 2.7.9](https://start.spring.io/) со следующими стартерами:
    - Liquibase
    - PostgreSQL
    - Spring Data JPA
    - Validation
    - Spring Web
    - Lombok
- [ModelMapper 3.1.1](https://mvnrepository.com/artifact/org.modelmapper/modelmapper)

## Запуск приложения локально
- Через терминал `java -jar leader-it-0.0.1-SNAPSHOT.jar`
- Запустить в IDE `main` из класса `com.example.leaderit.LeaderItApplication`
- Используя [Spring Boot Maven plugin](https://mvnrepository.com/artifact/org.springframework.boot/spring-boot-maven-plugin) `mvn spring-boot:run`

## TODO's
- Unit Testing
- Метод добавления устройства сохраняет секретный ключ в
НЕ зашифрованном виде
- Таблица "Перечень активных устройств" не
очищается от старых записей

---------------------------------------------------

# Описание задания:
Необходимо создать REST-сервис для сбора метрик полученных от IoT оборудования и
составления статистики.

Предполагается что при регистрации в системе IoT устройство получит некий секретный ключ, с
которым будем выполнять передачу информации и событиях.

**Структура данных:**

IoT устройство:
- Идентификатор (порядковый номер)
- Серийный номер (уникальное значение)
- Наименование устройства
- Тип устройства
- Секретный ключ для подписи запросов (формируется при создании записи, хранить в открытом виду запрещено)
- Дата добавления в систему

Событие:
- Идентификатор
- Идентификатор устройства
- Тип события
- Полезная нагрузка (json)
- Дата создания

Перечень активных устройств:
- Идентификатор
- Идентификатор устройства
- Дата первой активности
- Дата последней активности

REST-сервис должен предоставлять следующие возможности:

Добавление нового устройства:
- В ответ возвращается секретный ключ

Сохранение события от IoT устройства:
- При обработке запроса, требуется выполнять валидацию секретного ключа
- Контракт тела запроса обязательно должен принимать тип события
- Требуется предусмотреть что объект с полезной нагрузкой для каждого устройства может
содержать уникальный набор полей
- При обработке события считаем, что устройство активно и добавляем/изменяем запись в
таблице "Перечень активных устройств"

Получить информацию об активных устройствах:
- Устройство которое было не активно более 30 минут должно быть убрано из таблицы "Перечень
активных устройств"

Получить информацию о конкретном устройстве по серийному номеру

Получить информацию о всех добавленных в систему устройств:
- Фильтр по типу устройства
- Фильтр по дате добавления
- Пагинация

Получить информацию о событиях конкретного устройства по серийному номеру:
- Фильтр по дате добавления
- Пагинация

Получить статистику по указанному периоду времени о количестве полученных событий,
сгруппированном по типам устройств

Дополнительно:
Сервис при первом запуске должен самостоятельно создавать необходимые объекты в БД с
помощью Liquibase.
Необходимо использовать PostgreSQL.
Для реализации необходимо использовать Java 8, Maven, Spring Boot, Hibernate, PostgreSQL,
Liquibase.
Исходный код загрузить в GitHub и в описании указать краткую инструкцию по запуску
приложения и описание реализованных методов API.
Обязательное условие: отображение истории разработки в виде коммитов. Решенные задания
без истории разработки приниматься не будут

-----------------------------------------

# Описание API:

Этот API содержит семь endpoint'ов, которые управляют устройствами IoT и их событиями.
Ниже приведена подробная информация о каждой конечной точке, а также ее параметры и ответы.

## POST /api/devices
Этот endpoint добавляет новое IoT-устройство в систему.

Параметры:
- IotDeviceDto (Request Body): Json, содержащий информацию о новом устройстве.
-
Ответ:
- IotDeviceDto (Response Body): Json, содержащий информацию о новом добавленном устройстве.

IotDeviceDto

Этот DTO содержит информацию об устройстве IoT, зарегистрированном в системе. Он имеет следующие атрибуты:
- id: Уникальный идентификатор IoT-устройства (необязательный для запроса).
- serialNumber: Серийный номер IoT-устройства.
- deviceName: Имя устройства IoT.
- deviceType: Тип устройства IoT.
- secretKey: Секретный ключ, необходимый IoT-устройству для связи с системой (необязательный для запроса).
- dateAdded: Дата и время, когда данное IoT-устройство было добавлено в систему (необязательный для запроса).

Пример
```json
{
  "id": 456,
  "serialNumber": "ABC123",
  "deviceName": "MySensor",
  "deviceType": "Temperature Sensor",
  "secretKey": "mySecretKey",
  "dateAdded": "2022-01-01T00:00:00"
}
```

## POST /api/devices/{deviceSerialNumber}/events
Этот endpoint сохраняет событие от IoT-устройства в системе.

Параметры:
- deviceSerialNumber (Path): Серийный номер устройства, отправившего событие.
- eventRequest (Request Body): Json, содержащий данные о событии.

Ответ:
- EventDto (Response Body): Json, содержащий информацию о сохраненном событии.

EventDto

Этот DTO содержит информацию о событии, вызванном IoT-устройством. Он имеет следующие атрибуты:
- id: Уникальный идентификатор события (необязательный для запроса).
- iotDeviceId: Уникальный идентификатор IoT-устройства, вызвавшего это событие (необязательный для запроса).
- iotDeviceSerialNumber: Серийный номер IoT-устройства, вызвавшего это событие (необязательный для запроса).
- eventType: Тип события.
- payload: json, содержащий полезную нагрузку события (необязательный для запроса).
- dateCreated: Дата и время, когда было создано это событие (необязательный для запроса).

Пример формата JSON:
```json
{
  "id": 789,
  "iotDeviceId": 456,
  "iotDeviceSerialNumber": "ABC123",
  "eventType": "temperature",
  "payload": {
    "value": 25,
    "unit": "Celsius"
  },
  "dateCreated": "2022-01-05T12:34:56"
}
```

## GET /api/devices/active
Этот endpoint возвращает список всех активных устройств в системе.

Параметры:
- Нет

Ответ:
- List (ActiveDeviceDto) (Response Body): Массив Json, содержащий информацию обо всех активных устройствах.

ActiveDeviceDto

Этот DTO содержит информацию об активном IoT-устройстве в системе. Он имеет следующие атрибуты:
- id: Уникальный идентификатор активного устройства.
- iotDeviceId: Уникальный идентификатор устройства IoT, связанного с этим активным устройством.
- dateFirstActivity: Дата и время, когда это активное устройство было впервые обнаружено в системе.
- dateLastActivity: Дата и время, когда это активное устройство было в последний раз активно в системе.

Пример формата JSON:
```json
{
  "id": 123,
  "iotDeviceId": 456,
  "dateFirstActivity": "2022-01-01T00:00:00",
  "dateLastActivity": "2022-01-05T12:34:56"
}
```

## GET /api/devices/{deviceSerialNumber}
Этот endpoint возвращает информацию о конкретном устройстве в системе.

Параметры:
- deviceSerialNumber (Path): Серийный номер устройства, о котором необходимо получить информацию.
-
Ответ:
- IotDeviceDto (Response Body): Json, содержащий информацию об устройстве.

## GET /api/devices
Этот endpoint возвращает информацию обо всех устройствах в системе.

Параметры:
- deviceType (Query Parameter, необязательный): Тип устройства (список устройств), о котором нужно получить информацию.
- startDateAdded (Query Parameter, необязательный): Дата начала временного диапазона для получения информации об устройствах, добавленных в него.
- endDateAdded (Query Parameter, необязательный): Дата окончания временного диапазона для получения данных об устройствах, добавленных в него.
- page (Query Parameter, необязательный, по умолчанию = 0): Номер страницы результатов, которые необходимо получить.
- size (Query Parameter, необязательный, по умолчанию = 10): Количество результатов для получения на каждой странице.

- Ответ:
- List (IotDeviceDto) (Response Body): Массив Json, содержащий информацию обо всех устройствах, соответствующих заданным параметрам.

## GET /api/devices/{deviceSerialNumber}/events
Этот endpoint возвращает информацию обо всех событиях конкретного устройства в системе.

Параметры:
- deviceSerialNumber (Path): Серийный номер устройства для получения событий.
- startDateCreated (Query Parameter, необязательный): Дата начала временного диапазона для получения событий, созданных в нем.
- endDateCreated (Query Parameter, необязательный): Дата окончания временного диапазона для получения событий, созданных в нем.
- page (Query Parameter, необязательный, по умолчанию = 0): Номер страницы результатов, которые необходимо получить.
- size (Query Parameter, необязательный, по умолчанию = 10): Количество результатов, которые нужно получить на каждой странице.

Ответ:
- List (EventDto) (Response Body): Массив Json, содержащий информацию обо всех событиях, соответствующих заданным параметрам.

## GET /api/devices/events/statistics
Этот endpoint возвращает статистику о событиях всех устройств в системе в указанном временном диапазоне.

Параметры:
- startDate (Query Parameter): Дата начала временного диапазона для получения статистики событий.
- endDate (Query Parameter): Дата окончания временного диапазона для получения статистики событий.

Ответ:
- List<StatisticsResponse> (Response Body): Список объектов DTO, содержащих статистику о событиях всех устройств в указанном временном диапазоне.

StatisticsResponse

Этот DTO содержит подсчет событий для определенного типа устройства. Он имеет следующие атрибуты:
- deviceType: Тип устройства IoT, для которого ведется подсчет событий.
- eventCount: Общее количество событий для указанного типа устройства.

Пример формата JSON:
```json
{
  "deviceType": "Temperature Sensor",
  "eventCount": 1000
}
```
