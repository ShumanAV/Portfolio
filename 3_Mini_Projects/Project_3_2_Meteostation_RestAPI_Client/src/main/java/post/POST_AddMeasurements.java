package post;

import dto.MeasurementDTO;
import dto.SensorDTO;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.web.client.RestTemplate;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Random;

// отправляем в виде post запроса результаты измерений, а именно: температуру и наличие дождя, отправляем в виде Json файла
public class POST_AddMeasurements {

    private static final String SENSOR_NAME = "Sensor_16";   // название сенсора
    private static final int NUMBER_MEASUREMENT = 10;     // кол-во измерений, которое будет отправлено на сервер в виде post запроса
    private static final int MIN_TEMPERATURE = -100;        // мин температура
    private static final int MAX_TEMPERATURE = 100;         // макс температура

    public static void main(String[] args) {

        RestTemplate restTemplate = new RestTemplate();

        Random random = new Random();

        for (int i = 0; i < NUMBER_MEASUREMENT; i++) {

            // сделаем небольшую задержку
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }

            // вычисляем температуру в пределах от -100 до 100
            float temperature = random.nextFloat() * MAX_TEMPERATURE * (random.nextBoolean() ? 1: -1);
            temperature = new BigDecimal(temperature).setScale(2, RoundingMode.UP).floatValue();

            // вычисляем наличие дождя true/false
            boolean raining = random.nextBoolean();

            // создаем сенсор
            SensorDTO sensor = new SensorDTO();
            sensor.setName(SENSOR_NAME);

            // создаем измерение
            MeasurementDTO measurement = new MeasurementDTO();
            measurement.setValue(temperature);
            measurement.setRaining(raining);
            measurement.setSensor(sensor);

            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);

            // преобразуем измерение в json
            HttpEntity<MeasurementDTO> request = new HttpEntity<>(measurement, headers);

            String url = "http://localhost:8087/measurements/add";

            // отправляем запрос, получаем ответ в виде строки
            String response = restTemplate.postForObject(url, request, String.class);

            System.out.println("Запрос №" + (i + 1) + ", ответ: " + response);
        }

    }

}
