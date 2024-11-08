package post;

import dto.SensorDTO;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.web.client.RestTemplate;

// отправляем post запрос и имя сенсора для его регистрации
public class POST_SensorRegistration {

    private static final String SENSOR_NAME = "Sensor_16";      // название сенсора

    public static void main(String[] args) {

        RestTemplate restTemplate = new RestTemplate();

        SensorDTO sensor = new SensorDTO();
        sensor.setName(SENSOR_NAME);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<SensorDTO> request = new HttpEntity<>(sensor, headers);

        String url = "http://localhost:8087/sensors/registration";

        String response = restTemplate.postForObject(url, request, String.class);

        System.out.println(response);

    }

}
