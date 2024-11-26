package com.example.Project_2_Library_Spring.Boot_Lesson_74.controllers;

import com.example.Project_2_Library_Spring.Boot_Lesson_74.models.Person;
import com.example.Project_2_Library_Spring.Boot_Lesson_74.services.PersonService;
import com.example.Project_2_Library_Spring.Boot_Lesson_74.util.PersonValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Controller
@RequestMapping("/people")
public class PersonController {

    private final PersonService personService;
    private final PersonValidator personValidator;

    /*
    Внедрение зависимостей
     */
    @Autowired
    public PersonController(PersonService personService, PersonValidator personValidator) {
        this.personService = personService;
        this.personValidator = personValidator;
    }

    /*
    Метод возвращает страницу всех читателей
     */
    @GetMapping()
    public String index(Model model) {
        model.addAttribute("people", personService.findAll());
        return "people/index";
    }

    /*
    Метод возвращает страницу создания новых читателей
     */
    @GetMapping("/new")
    public String newPerson(@ModelAttribute("person") Person person) {
        return "people/new";
    }

    /*
    Метод принимает пост запрос с данными для создания нового читателя, все поля валидируются согласно аннотациям
    в модели и валидатору, после сохранения читателя редиректит на страницу всех читателей
     */
    @PostMapping()
    public String create(@ModelAttribute("person") @Valid Person person,
                         BindingResult bindingResult) {
        personValidator.validate(person, bindingResult);

        if (bindingResult.hasErrors()) {
            return "people/new";
        }
        personService.save(person);
        return "redirect:/people";
    }

    /*
    Метод возвращает страницу выбранного читателя
     */
    @GetMapping("/{id}")    // динамический id
    public String show(@PathVariable("id") int id, Model model) {
        model.addAttribute("person", personService.findById(id));
        model.addAttribute("books", personService.getBooksByPersonId(id));
        return "people/show";
    }

    /*
    Метод возвращает страницу для изменения данных выбранного читателя
     */
    @GetMapping("/{id}/edit")
    public String edit(@PathVariable("id") int id, Model model) {
        model.addAttribute("person", personService.findById(id));
        return "people/edit";
    }

    /*
    Метод принимает патч запрос для изменения выбранного читателя, на вход принимает объект читателя с новыми данными,
    после сохрангения читателя редиректит на страницу всех читателей
     */
    @PatchMapping("/{id}")
    public String update(@ModelAttribute("person") @Valid Person person,
                         BindingResult bindingResult,
                         @PathVariable("id") int id) {
        personValidator.validate(person, bindingResult);

        if (bindingResult.hasErrors()) {
            return "people/edit";
        }
        personService.update(id, person);
        return "redirect:/people";
    }

    /*
    Метод принимает делит запрос на удаление выбранного читателя, после удаления читателя редиректит
    на страницу всех читателей
     */
    @DeleteMapping("/{id}")
    public String delete(@PathVariable("id") int id) {
        personService.delete(id);
        return "redirect:/people";
    }

}
