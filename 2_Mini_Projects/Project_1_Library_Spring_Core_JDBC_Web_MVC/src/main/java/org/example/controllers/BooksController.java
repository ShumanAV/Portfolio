package org.example.controllers;

import jakarta.validation.Valid;
import org.example.DAO.BookDAO;
import org.example.DAO.PersonDAO;
import org.example.Models.Book;
import org.example.Models.Person;
import org.example.util.BookValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/books")
public class BooksController {
    private final BookDAO bookDAO;
    private final BookValidator bookValidator;
    private final PersonDAO personDAO;

    /*
    Внедрение зависимостей через конструктор
     */
    @Autowired
    public BooksController(BookDAO bookDAO, BookValidator bookValidator, PersonDAO personDAO) {
        this.bookDAO = bookDAO;
        this.bookValidator = bookValidator;
        this.personDAO = personDAO;
    }

    /*
    Метод формирует весь список книг, передает этот список книг в модель, возвращает страницу со списком всех книг
     */
    @GetMapping()
    public String index(Model model) {
        model.addAttribute("books", bookDAO.index());
        return "books/index";
    }

    /*
    Метод возвращает страницу для отображения информации о выбранной книге, передает в модель выбранную книгу по id,
    читателя книги если он есть у данной книги, если нет то null, также передает список всех читателей для заполнения
    списка в отображении
     */
    @GetMapping("/{id}")
    public String show(@PathVariable("id") int bookId, Model model, @ModelAttribute("person") Person person) {
        model.addAttribute("book", bookDAO.show(bookId).get());
        model.addAttribute("reader", bookDAO.searchWhoTookThisBook(bookId));
        model.addAttribute("people", personDAO.index());
        return "books/show";
    }

    /*
    Метод возвращает страницу для создания новой книги, пустой объект книга создается и передается в модель
    для привязки полей на странице к объекту Book, с целью последующей передачи заполненных данных в виде объекта Person
     */
    @GetMapping("/new")
    public String newBook(@ModelAttribute("book") Book book) {
        return "books/new";
    }

    /*
    Метод создает новую книгу, на вход приходит объект Book с заполненными полями на странице создания книги,
    все поля валидируются согласно модели и валидатору
     */
    @PostMapping()
    public String create(@ModelAttribute("book") @Valid Book book, BindingResult bindingResult) {
        bookValidator.validate(book, bindingResult);
        if (bindingResult.hasErrors())
            return "books/new";
        bookDAO.save(book);
        return "redirect:/books";
    }

    /*
    Метод возвращает страницу для изменения данных книги, в модель передается выбранная книга
     */
    @GetMapping("/{id}/edit")
    public String edit(@PathVariable("id") int bookId, Model model) {
        model.addAttribute("book", bookDAO.show(bookId).get());
        return "books/edit";
    }

    /*
    Метод изменения книги, на вход принимает объект книги с внесенными изменениями на странице,
    все поля валидируются согласно моделям и валидатору
     */
    @PatchMapping("/{id}")
    public String update(@ModelAttribute("book") @Valid Book book, BindingResult bindingResult,
                         @PathVariable("id") int bookId) {
        book.setBookId(bookId);
        bookValidator.validate(book, bindingResult);
        if (bindingResult.hasErrors())
            return "books/edit";
        bookDAO.update(bookId, book);
        return "redirect:/books";
    }

    /*
    Метод удаления выбранной книги
     */
    @DeleteMapping("/{id}")
    public String delete(@PathVariable("id") int bookId) {
        bookDAO.delete(bookId);
        return "redirect:/books";
    }

    /*
    Метод прикрепления книги к выбранному читателю, на вход принимает id книги и выбранного человека в виде объекта Person
     с заполненным только personId из списка select
     */
    @PatchMapping("/{id}/assign")
    public String assignBook(@PathVariable("id") int bookId, @ModelAttribute("person") Person person) {
        bookDAO.assignBook(bookId, person.getPersonId());
        return "redirect:/books/{id}";
    }

    /*
    Метод освобождения книги от читателя, с этого момента книга считается не закрепленной ни за кем
     */
    @PatchMapping("/{id}/release")
    public String releaseBook(@PathVariable("id") int bookId) {
        bookDAO.releaseBook(bookId);
        return "redirect:/books/{id}";
    }
}
