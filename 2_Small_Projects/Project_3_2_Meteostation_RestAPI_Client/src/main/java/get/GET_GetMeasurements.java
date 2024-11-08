package get;

import util.MeasurementsResponse;
import org.springframework.web.client.RestTemplate;
import xchart.XchartDraw;

// делаем get запрос и получаем все измерения из БД в виде Json файла, затем рисуем диаграмму температур
public class GET_GetMeasurements {

    public static void main(String[] args) {

        RestTemplate restTemplate = new RestTemplate();

        String url = "http://localhost:8087/measurements";

        // отправляем запрос, получаем ответ в виде строки
        MeasurementsResponse response = restTemplate.getForObject(url, MeasurementsResponse.class);

        if (response != null && response.getMeasurements() != null) {
            XchartDraw.draw(response.getMeasurements());
        }

    }

}
