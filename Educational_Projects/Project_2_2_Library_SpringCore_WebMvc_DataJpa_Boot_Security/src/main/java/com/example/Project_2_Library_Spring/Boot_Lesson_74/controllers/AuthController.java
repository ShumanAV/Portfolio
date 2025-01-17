package com.example.Project_2_Library_Spring.Boot_Lesson_74.controllers;

import com.example.Project_2_Library_Spring.Boot_Lesson_74.models.Person;
import com.example.Project_2_Library_Spring.Boot_Lesson_74.services.RegistrationService;
import com.example.Project_2_Library_Spring.Boot_Lesson_74.util.PersonValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Controller
@RequestMapping("/auth")
public class AuthController {

    private final RegistrationService registrationService;
    private final PersonValidator personValidator;

    /*
    Внедрение зависимостей
     */
    @Autowired
    public AuthController(RegistrationService registrationService, PersonValidator personValidator) {
        this.registrationService = registrationService;
        this.personValidator = personValidator;
    }

    /*
    Метод возвращает страницу для аутентификации
     */
    @GetMapping("/login")
    public String loginPage() {
        return "auth/login";
    }

    /*
    Метод возвращает страницу для регистрации новых пользователей
     */
    @GetMapping("/registration")
    public String registrationPage(@ModelAttribute("person") Person person) {
        // с помощью @ModelAttribute создадим пустого человека и передадим его в модель
        return ("auth/registration");
    }

    /*
    Метод принимает пост запрос со страницы регистрации новых пользователей, все поля валидируются согласно аннотациям
    и валидатору, после регистрации пользователя редиректит на страницу аутентификации
     */
    @PostMapping("registration")
    public String performRegistration(@ModelAttribute("person") @Valid Person person, BindingResult bindingResult) {
        personValidator.validate(person, bindingResult);

        if (bindingResult.hasErrors()) {
            return "/auth/registration";
        }

        registrationService.register(person);
        return "redirect:/auth/login";
    }

}
