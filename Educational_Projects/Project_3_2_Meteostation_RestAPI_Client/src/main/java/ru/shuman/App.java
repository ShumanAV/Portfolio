package ru.shuman;

import org.knowm.xchart.*;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.web.client.RestTemplate;
import ru.shuman.dto.MeasurementDTO;
import ru.shuman.dto.MeasurementResponseDTO;
import ru.shuman.dto.SensorDTO;

import java.util.*;

public class App {

    private static final int TEMPERATURE_MIN = -100;
    private static final int TEMPERATURE_MAX = 100;
    private static final String SENSOR_NAME = "sensor_6";
    private static final int QUANTITY_MEASUREMENTS = 1000;
    private static final RestTemplate REST_TEMPLATE = new RestTemplate();


    /*
    В методе main каждый выполняемый метод отдельный и автономный от других, при каждом запуске можно не используемые
    методы в данный момент закоментировать
     */
    public static void main(String[] args) {
        createNewSensor();
        getAllSensors();
        makeMeasurements(QUANTITY_MEASUREMENTS);
        getAllMeasurements();
        getRainyDaysCount();
    }

    /*
    Метод делает гет запрос на сервер по получению количества дождливых дней за все время измерений
     */
    private static void getRainyDaysCount() {
        final String url = "http://localhost:8080/measurements/rainyDaysCount";

        String response = REST_TEMPLATE.getForObject(url, String.class);

        System.out.println(response);
    }

    /*
    Метод делает гет запрос на сервер для получения всех измерений за все время, на вход получает json с
    объектом - оберткой с массивом измерений, парсит его и распечатывает
     */
    private static void getAllMeasurements() {
        final String url = "http://localhost:8080/measurements";

        MeasurementResponseDTO response = REST_TEMPLATE.getForObject(url, MeasurementResponseDTO.class);

        for (MeasurementDTO measurementDTO: response.getMeasurements()) {
            System.out.println(measurementDTO);
        }

        printChart(response.getMeasurements());
    }

    /*
    Метод строит xchart диаграмму из данных количества точек и замеров температуры, полученных из БД
     */
    private static void printChart(List<MeasurementDTO> response) {
        double[] xData = new double[response.size()];
        double[] yData = new double[response.size()];

        //наполняем данными массивы, по оси Х - кол-во точек, по оси Y - значение температуры
        for (int i = 0; i < response.size(); i++) {
            xData[i] = i;
            yData[i] = response.get(i).getValue();
        }

        // Создаём график
        XYChart chart = QuickChart.getChart("Диаграмма температур", "X - Кол-во", "Y - Температура", "y(x)", xData, yData);

        // Отображаем его
        new SwingWrapper(chart).displayChart();
    }

    /*
    Метод эмулирует работу датчика погоды и делает заданное количество измерений температуры и дождливых дней,
    которое принимается на входе, делает пост запрос на сервер для сохранения измерений в БД
     */
    private static void makeMeasurements(int quantityMeasurements) {
        HttpEntity<MeasurementDTO> request;

        final String url = "http://localhost:8080/measurements/add";

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        String response;

        Random random = new Random();
        for (int i = 0; i < quantityMeasurements; i++) {
            MeasurementDTO measurement = new MeasurementDTO(random.nextFloat(TEMPERATURE_MIN, TEMPERATURE_MAX), random.nextBoolean(),
                    new SensorDTO(SENSOR_NAME));

            request = new HttpEntity<>(measurement, headers);

            response = REST_TEMPLATE.postForObject(url, request, String.class);

            System.out.println(response);
        }
    }

    /*
    Метод делает гет запрос на сервер для получения всех зарегистрированных датчиков погоды за все время в БД
     */
    private static void getAllSensors() {
        final String url = "http://localhost:8080/sensors";

        SensorDTO[] response = REST_TEMPLATE.getForObject(url, SensorDTO[].class);

        System.out.println(Arrays.toString(response));
    }

    /*
    Метод создает новый датчик погоды и делает пост запрос на сервер для сохранения данного датчика в БД
     */
    private static void createNewSensor() {
        SensorDTO sensor = new SensorDTO(SENSOR_NAME);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<SensorDTO> request = new HttpEntity<>(sensor, headers);

        final String url = "http://localhost:8080/sensors/registration";

        String response = REST_TEMPLATE.postForObject(url, request, String.class);

        System.out.println(response);
    }
}
