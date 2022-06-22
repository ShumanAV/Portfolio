import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class YandexTranslator {

    public static void main(String[] args) throws JsonProcessingException {

        System.out.println("Введите предложение на русском языке");
        Scanner scanner = new Scanner(System.in);
        String sentenceToTranslate = scanner.nextLine();

        RestTemplate restTemplate = new RestTemplate();

        String url = "https://translate.api.cloud.yandex.net/translate/v2/translate";

        // неоьходимо отправить хедеры, для этого создаем объект хедеров
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(MediaType.APPLICATION_JSON);
        // тут нужно получить свой токен в яндексе
        httpHeaders.add("Authorization", "Bearer" + "токен");

        // создадим map для передачи туда json файла
        Map<String,String> jsonData = new HashMap<>();
        // тут вместо id нужно получить свой id папки после регистрации
        jsonData.put("folderId", "id");
        // ниже выбираем целевой язык для перевода
        jsonData.put("targetLanguageCode", "en");
        // ниже передаем текст для перевода в виде массива с одним элементом
        jsonData.put("texts", "[" + sentenceToTranslate + "]");

        // преобразуем map и хедеры в json
        HttpEntity<Map<String,String>> request = new HttpEntity<>(jsonData, httpHeaders);

//        String response = restTemplate.postForObject(url, httpHeaders, String.class);
        // теперь jackson спарсит файл в объекты YandexResponse
        YandexResponse response = restTemplate.postForObject(url, httpHeaders, YandexResponse.class);

        // парсим полученный json файл с помощью Jackson, чтобы каждый раз не распарсивать этот файл, создадим класс и jackson автоматически будет парсить
//        // в этот объект, ниже строки нам больше не нужны
//        ObjectMapper mapper = new ObjectMapper();
//        JsonNode obj = mapper.readTree(response);
//        System.out.println("Перевод: " + obj.get("translations").get(0).get("text"));
        System.out.println("Перевод: " + response.getTranslations().get(0).getText());

    }

}
