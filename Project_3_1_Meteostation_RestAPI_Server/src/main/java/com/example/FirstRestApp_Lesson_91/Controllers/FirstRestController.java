package com.example.FirstRestApp_Lesson_91.Controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

// @RestController - все методы возвращают данные а не представления, это тоже самое что и @Controller + @ResponseBody в каждом методе
@RestController
@RequestMapping("/api")
public class FirstRestController {

    // @ResponseBody - говорит о том, что мы уже в контроллере не возвращаем шаблон (представление), а возвращаем данные, например Java объекты в виде Json,
    // т.е. String это строка данных
    // когда применяем у класса аннотацию @RestController, то у каждого метода не нужно ставить @ResponseBody
    // @ResponseBody
    @GetMapping("/sayHello")
    public String sayHello() {
        // т.е. на сей раз спринг вернет строку с данным текстом и не будет искать такое представление
        return "Hello World";
    }

}
