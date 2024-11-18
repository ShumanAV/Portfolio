package org.example.controllers;

import jakarta.validation.Valid;
import org.example.dao.BookDAO;
import org.example.models.Book;
import org.example.models.Person;
import org.example.services.BookService;
import org.example.services.PersonService;
import org.example.util.BookValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@Controller
@RequestMapping("/books")
public class BooksController {
    private final BookDAO bookDAO;
    private final BookService bookService;
    private final BookValidator bookValidator;
    private final PersonService personService;

    /*
    Внедрение зависимостей через конструктор
     */
    @Autowired
    public BooksController(BookDAO bookDAO, BookService bookService, BookValidator bookValidator, PersonService personService) {
        this.bookDAO = bookDAO;
        this.bookService = bookService;
        this.bookValidator = bookValidator;
        this.personService = personService;
    }

    /*
    Метод формирует весь список книг, передает этот список книг в модель, возвращает страницу со списком всех книг.
    На вход может принимать параметры запроса для сортировки и пагинации списка книг, в зависимости от того
    какие параметры запроса присутствуют такой перегруженный метод findAll() из bookService'а и используется
     */
    @GetMapping()
    public String index(@RequestParam(value = "page", required = false) Integer page,
                        @RequestParam(value = "books_per_page", required = false) Integer booksPerPage,
                        @RequestParam(value = "sort_by_year", required = false) Boolean sortByYear,
                        Model model) {
        if ((page == null || booksPerPage == null) & (sortByYear == null || !sortByYear)) {
            model.addAttribute("books", bookService.findAll());

        } else if ((page != null & booksPerPage != null) & (sortByYear == null || !sortByYear)) {
            model.addAttribute("books", bookService.findAll(page, booksPerPage));

        } else if ((page == null & booksPerPage == null) & sortByYear) {
            model.addAttribute("books", bookService.findAll(sortByYear));

        } else if ((page != null & booksPerPage != null) & sortByYear) {
            model.addAttribute("books", bookService.findAll(page, booksPerPage, sortByYear));
        }

        return "books/index";
    }

    /*
    Метод возвращает страницу для отображения информации о выбранной книге, передает в модель выбранную книгу по id,
    читателя книги если он есть у данной книги, если нет то null, также передает список всех читателей для заполнения
    списка в отображении для закрепления книги, а также при помощи @ModelAttribute создается пустой объект Person
    и передается для закрепления за данным объектом списка с читателями, при выборе читателя из данного списка
    в пустой объект Person кладется id выбранного человека, далее для закрепления книги за читателем используется
    данный объект Person, но уже с id
     */
    @GetMapping("/{id}")
    public String show(@PathVariable("id") int bookId, Model model, @ModelAttribute("person") Person person) {
        model.addAttribute("book", bookService.show(bookId).get());

        Optional<Person> reader = bookService.getBookReader(bookId);
        if (reader.isPresent()) {
            model.addAttribute("reader", reader.get());
        } else {
            model.addAttribute("people", personService.index());
        }
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
        bookService.save(book);
        return "redirect:/books";
    }

    /*
    Метод возвращает страницу для изменения данных книги, в модель передается выбранная книга
     */
    @GetMapping("/{id}/edit")
    public String edit(@PathVariable("id") int bookId, Model model) {
        model.addAttribute("book", bookService.show(bookId).get());
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
        bookService.update(bookId, book);
        return "redirect:/books";
    }

    /*
    Метод удаления выбранной книги
     */
    @DeleteMapping("/{id}")
    public String delete(@PathVariable("id") int bookId) {
        bookService.delete(bookId);
        return "redirect:/books";
    }

    /*
    Метод прикрепления книги к выбранному читателю, на вход принимает id книги и выбранного человека из списка select
    в виде объекта Person с заполненным только personId
     */
    @PatchMapping("/{id}/assign")
    public String assign(@PathVariable("id") int bookId, @ModelAttribute("person") Person selectedPerson) {
        bookService.assign(bookId, selectedPerson.getPersonId());
        return "redirect:/books/{id}";
    }

    /*
    Метод освобождения книги от читателя, с этого момента книга считается не закрепленной ни за кем
     */
    @PatchMapping("/{id}/release")
    public String release(@PathVariable("id") int bookId) {
        bookService.release(bookId);
        return "redirect:/books/{id}";
    }

    /*
    Метод возвращает страницу для поиска книг
     */
    @GetMapping("/search")
    public String searchPage(){
        return "books/search";
    }

    /*
    Метод запрашивает список книг, названия которых начинается со строки title из поля поиска,
    и передает данный список в модель, также в модель передаем и текущее значение строки поиска для отображения
     */
    @PostMapping("/search")
    public String search(@RequestParam("title") String title, Model model){
        model.addAttribute("title", title);
        model.addAttribute("books", bookService.searchByTitle(title));
        return "books/search";
    }
}
