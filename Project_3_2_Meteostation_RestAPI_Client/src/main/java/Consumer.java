import org.springframework.http.HttpEntity;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;

// в данном классе будем делать запросы
public class Consumer {

    public static void main(String[] args) {

        // выберем сервис, к которому сделаем запрос и получим от него json
        RestTemplate restTemplate = new RestTemplate();

        // для post запроса в качестве json создаем map
        Map<String,String> jsonToSend = new HashMap<>();
        jsonToSend.put("name", "Test name");
        jsonToSend.put("job", "Test job");

        // упаковываем map в HttpEntity, для преобразования ее в json, просто так map мы не можем передать
        HttpEntity<Map<String,String>> request = new HttpEntity<>(jsonToSend);

        // выбрали сервис https://vivazzi.pro
         String url = "https://vivazzi.pro/test-request/?json=true&par_1=foo&par_2=bar";
        // т.к. мы делаем get запрос поэтому getForObject, если бы делали post запрос то было бы postForObject, обратно мы должны получить
        // json файл, примем его в виде строки, поэтому String.class
//        String response = restTemplate.getForObject(url, String.class);
        String response = restTemplate.postForObject(url, request, String.class);
        System.out.println(response);


    }

}
