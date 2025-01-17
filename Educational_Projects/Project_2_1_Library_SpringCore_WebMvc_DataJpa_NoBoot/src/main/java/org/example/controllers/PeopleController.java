package org.example.controllers;

import jakarta.validation.Valid;
import org.example.dao.PersonDAO;
import org.example.models.Person;
import org.example.services.PersonService;
import org.example.util.PersonValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/people")
public class PeopleController {

    private final PersonDAO personDAO;
    private final PersonService personService;
    private final PersonValidator personValidator;

    /*
    Внедрение зависимостей через конструктор
     */
    @Autowired
    public PeopleController(PersonDAO personDAO, PersonService personService, PersonValidator personValidator) {
        this.personDAO = personDAO;
        this.personService = personService;
        this.personValidator = personValidator;
    }

    /*
    Метод возвращает страницу со списком всех читателей, для этого в модель передается сформированный данный список
     */
    @GetMapping()
    public String index(Model model) {
        model.addAttribute("people", personService.index());
        return "people/index";
    }

    /*
    Метод возвращает страницу с подробной информацией о выбранном человеке и список книг, которые за ним закреплены при наличии,
    для этого в модель передается выбранный человек и список книг если они есть
     */
    @GetMapping("/{id}")
    public String show(@PathVariable(value = "id") int personId, Model model) {
        model.addAttribute("person", personService.show(personId).get());
        model.addAttribute("books", personService.getBooks(personId));
        return "people/show";
    }

    /*
    Метод возвращает страницу создания нового человека
     */
    @GetMapping("/new")
    public String newPerson(@ModelAttribute("person") Person person) {
        return "people/new";
    }

    /*
    Метод создания нового человека, на вход принимает объект Person с заполненными данными со страницы,
    все поля валидируются согласно модели и валидатора.
    В BindingResult попадают простейшие ошибки из валидации полей, но также и ошибки из валидатора
     */
    @PostMapping()
    public String create(@ModelAttribute("person") @Valid Person person, BindingResult bindingResult) {
        personValidator.validate(person, bindingResult);
        if (bindingResult.hasErrors())
            return "people/new";

        personService.save(person);
        return "redirect: /people";
    }

    /*
    Метод возвращает страницу изменения данных выбранного человека
     */
    @GetMapping("/{id}/edit")
    public String edit(@PathVariable("id") int personId, Model model) {
        model.addAttribute("person", personService.show(personId).get());
        return "people/edit";
    }

    /*
    Метод изменения данных выбранного человека, на вход принимает объект Person с заполненными данными со страницы,
    все поля валидируются согласно модели и валидатора
     */
    @PatchMapping("/{id}")
    public String update(@ModelAttribute("person") @Valid Person person, BindingResult bindingResult,
                         @PathVariable("id") int personId) {
        person.setPersonId(personId);
        personValidator.validate(person, bindingResult);
        if (bindingResult.hasErrors())
            return "people/edit";

        personService.update(personId, person);
        return "redirect:/people";
    }

    /*
    Метод удаления выбранного человека
     */
    @DeleteMapping("/{id}")
    public String delete(@PathVariable("id") int personId) {
        personService.delete(personId);
        return "redirect:/people";
    }
}
