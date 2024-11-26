package com.example.Project_2_Library_Spring.Boot_Lesson_74.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class MainController {

    /*
    Метод возвращает главную страницу
     */
    @GetMapping("/index")
    public String index() {
        return "index";
    }

}
