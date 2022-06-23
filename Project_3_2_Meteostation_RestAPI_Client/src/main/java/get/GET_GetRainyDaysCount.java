package get;

import org.springframework.web.client.RestTemplate;

// делаем get запрос и получаем количество дождливых дней из БД в виде числа
public class GET_GetRainyDaysCount {

    public static void main(String[] args) {

        RestTemplate restTemplate = new RestTemplate();

        String url = "http://localhost:8087/measurements/rainyDaysCount";

        // отправляем запрос, получаем ответ в виде строки
        String response = restTemplate.getForObject(url, String.class);

        System.out.println(response);

    }

}
