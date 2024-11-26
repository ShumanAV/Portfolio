package com.example.Project_2_Library_Spring.Boot_Lesson_74.controllers;

import com.example.Project_2_Library_Spring.Boot_Lesson_74.models.Book;
import com.example.Project_2_Library_Spring.Boot_Lesson_74.models.Person;
import com.example.Project_2_Library_Spring.Boot_Lesson_74.services.BookService;
import com.example.Project_2_Library_Spring.Boot_Lesson_74.services.PersonService;
import com.example.Project_2_Library_Spring.Boot_Lesson_74.util.BookValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Optional;

@Controller
@RequestMapping("/books")
public class BookController {

    private final BookService bookService;
    private final PersonService personService;
    private final BookValidator bookValidator;

    /*
    Внедрение зависимостей
     */
    @Autowired
    public BookController(BookService bookService, PersonService personService, BookValidator bookValidator) {
        this.bookService = bookService;
        this.personService = personService;
        this.bookValidator = bookValidator;
    }

    /*
    Метод возвращает страницу всех книг, на вход принимает параметры для пагинации и сортировки книг
     */
    @GetMapping()
    public String index(
            Model model,
            @RequestParam(value = "page", required = false) Integer page,
            @RequestParam(value = "books_per_page", required = false) Integer booksPerPage,
            @RequestParam(value = "sort_by_year", required = false) Boolean sortByYear) {

        if (page == null && booksPerPage == null) {
            model.addAttribute("books", bookService.findAll(sortByYear));
        } else {
            model.addAttribute("books", bookService.findAllWithPagination(page, booksPerPage, sortByYear));
        }
        return "book/index";
    }

    /*
    Метод возвращает страницу для создания новой книги
     */
    @GetMapping("/new")
    public String newBook(@ModelAttribute("book") Book book) {
        return "book/new";
    }

    /*
    Метод создает новую книгу, на вход принимает объект book со страницы создания новой книги, все поля валидируются
    согласно аннотациям в модели и валидатору, после сохранения книги, редиректит на страницу всех книг
     */
    @PostMapping()
    public String create(@ModelAttribute("book") @Valid Book book,
                         BindingResult bindingResult) {
        bookValidator.validate(book, bindingResult);

        if (bindingResult.hasErrors()) {
            return "book/new";
        }
        bookService.save(book);
        return "redirect:/books";
    }

    /*
    Метод возвращает страницу просмотра выбранной книги
     */
    @GetMapping("/{id}")    // динамический id
    public String show(@PathVariable("id") int id, Model model, @ModelAttribute("person") Person person) {
        model.addAttribute("book", bookService.findById(id));

        Optional<Person> bookOwner = bookService.getBookOwner(id);

        if (bookOwner.isPresent()) {
            model.addAttribute("owner", bookOwner.get());
        } else {
            model.addAttribute("people", personService.findAll());
        }
        return "book/show";
    }

    /*
    Метод возвращает страницу для изменения выбранной книги
     */
    @GetMapping("/{id}/edit")
    public String edit(@PathVariable("id") int id, Model model) {
        model.addAttribute("book", bookService.findById(id));
        return "book/edit";
    }

    /*
    Метод принимает патч запрос с измененными данными книги, после сохранения книги редиректит на страницу всех книг
     */
    @PatchMapping("/{id}")
    public String update(@ModelAttribute("book") @Valid Book book,
                         BindingResult bindingResult,
                         @PathVariable("id") int id) {
        bookValidator.validate(book, bindingResult);

        if (bindingResult.hasErrors()) {
            return "book/edit";
        }
        bookService.update(id, book);
        return "redirect:/books";
    }

    /*
    Метод принимает делит запрос для удаления выбранной книги, после удаления редиректит на страницу всех книг
     */
    @DeleteMapping("/{id}")
    public String delete(@PathVariable("id") int id) {
        bookService.delete(id);
        return "redirect:/books";
    }

    /*
    Метод освобождает выбранную книгу от читателя, после редиректит на страницу выбранной книги
     */
    @PatchMapping("/{id}/release")
    public String release(@PathVariable("id") int id) {
        bookService.release(id);
        return "redirect:/books/" + id;
    }

    /*
    Метод закрепляет выбранную книгу за выбранным читателем, выбранный читатель принимается на вход с id,
    после редиректит на страницу выбранной книги
    */
    @PatchMapping("/{id}/assign")
    public String assign(@PathVariable("id") int id, @ModelAttribute("person") Person selectedPerson) {
        // у selectedPerson назначено только поле id остальные null
        bookService.assign(id, selectedPerson);
        return "redirect:/books/" + id;
    }

    /*
    Метод возвращает страницу поиска книг
     */
    @GetMapping("/search")
    public String indexSearch() {
        return "/book/search";
    }

    /*
    Метод делает поиск книги по названию, возвращает страницу поиска книги
     */
    @PostMapping("/search")
    public String search(@RequestParam(value = "title", required = false) String title, Model model) {
        model.addAttribute("title", title);
        model.addAttribute("books", bookService.findByTitleStartingWith(title));
        return "/book/search";
    }

}
