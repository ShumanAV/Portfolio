package com.example.FirstRestApp_Lesson_91.Controllers;

import com.example.FirstRestApp_Lesson_91.dto.PersonDTO;
import com.example.FirstRestApp_Lesson_91.models.Person;
import com.example.FirstRestApp_Lesson_91.services.PeopleService;
import com.example.FirstRestApp_Lesson_91.util.PersonErrorResponse;
import com.example.FirstRestApp_Lesson_91.util.PersonNotCreatedException;
import com.example.FirstRestApp_Lesson_91.util.PersonNotFoundException;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/people")
public class PeopleController {

    private final ModelMapper modelMapper;
    private final PeopleService peopleService;

    @Autowired
    public PeopleController(ModelMapper modelMapper, PeopleService peopleService) {
        this.modelMapper = modelMapper;
        this.peopleService = peopleService;
    }

    // переделали в то, что возвращаем клиенту DTO, а не модель
    @GetMapping()
    public List<PersonDTO> getPeople() {
        // здесь Jackson автоматически конвертирует объекты в json и пересылаем его по сети при запросе
        return peopleService.findAll().stream().map(this::convertToPersonDTO).collect(Collectors.toList());
    }

    // переделали в то, что возвращаем клиенту DTO, а не модель
    @GetMapping("/{id}")
    public PersonDTO getPerson(@PathVariable("id") int id) {
        // здесь Jackson автоматически конвертирует объект Person в json, если объект найдет по id, то будет статус 200
        return convertToPersonDTO(peopleService.findOne(id));
    }

    // @RequestBody - получаем от клиента json и автоматически преобразовывает json в java объекты
    // меняем принимаемый Person на DTO с последующим преобразованием DTO в модель Person
    @PostMapping
    public ResponseEntity<HttpStatus> create(@RequestBody @Valid PersonDTO personDTO, BindingResult bindingResult) {
        // выкинем ошибку и отправим клиенту в виде json
        if (bindingResult.hasErrors()) {
            StringBuilder errorMsg = new StringBuilder();

            // создадим лист ошибок
            List<FieldError> errors = bindingResult.getFieldErrors();
            for (FieldError error: errors ) {
                // преобразуем все ошибки сделанные клиентом в большую строку, error.getField() - добавим поле, в котором есть ошибка
                // и добавим error.getDefaultMessage() какая ошибка была в этом поле, и чтобы ошибки не склеились конкатенируем ";"
                errorMsg.append(error.getField())
                        .append(" - ").append(error.getDefaultMessage()).append(";");
            }
            // выбросим нашу ошибку и передадим в нее текст ошибки валидации данных, нам остается ее поймать в другом методе ниже
            throw new PersonNotCreatedException(errorMsg.toString());
        }

        peopleService.save(convertToPerson(personDTO));
        // вернется клиенту, что все прошло ок, т.е. отправляем HTTP ответ с пустым телом и статусом 200
        return ResponseEntity.ok(HttpStatus.OK);
    }

    // @ExceptionHandler - этой аннотацией помечаем тот метод, который в себя ловит exception, и который возвращает необходимый json
    // данный метод будет принимать нашу ошибку, если бы мы не сделали нашу ошибку и он бы принимал просто Exception, в этом случае он бы принимал все ошибки
    @ExceptionHandler
    private ResponseEntity<PersonErrorResponse> handleException(PersonNotFoundException e) {
        // создаем наш объект PersonErrorResponse с ошибкой
        PersonErrorResponse response = new PersonErrorResponse(
                "Person with this id wasn't found!",
                System.currentTimeMillis()
        );
        // возвращаем преобразованную в json тело нашего ответа - ошибку и статус NOT_FOUND - 404 статус, клиент будет понимать, что такого человека
        // не найдено и поэтому возникла ошибка, ResponseEntity - обертка нашего response
        // в HTTP ответе будет - тело ответа - наш response, который с помощью Jackson будет преобразован в json, и статус в заголовке ответа
        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler
    private ResponseEntity<PersonErrorResponse> handleException(PersonNotCreatedException e) {
        // создаем наш объект PersonErrorResponse с ошибкой
        PersonErrorResponse response = new PersonErrorResponse(
                e.getMessage(),
                System.currentTimeMillis()
        );
        // возвращаем преобразованную в json тело нашего ответа - ошибку и статус BAD_REQUEST - статус, клиент будет понимать, что у него в данных ошибка,
        // ResponseEntity - обертка нашего response
        // в HTTP ответе будет - тело ответа - наш response, который с помощью Jackson будет преобразован в json, и статус в заголовке ответа
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    // метод конвертации из DTO в модель, кладем в модель только те поля, которые есть в DTO
    // такое решение не оптимальное, т.к. много одинакового кода, если полей много, придется писать много кода, для автоматизации используется
    // зависимость modelmapper
    private Person convertToPerson(PersonDTO personDTO) {

        // вместо этого
//        Person person = new Person();
//        person.setName(personDTO.getName());
//        person.setAge(personDTO.getAge());
//        person.setEmail(personDTO.getEmail());
//        return person;

        // делаем так
        // пока создаем ModelMapper вручную, но в больших приложениях мы будем его использовать повсеместно, поэтому чтобы каждый раз не создавать новый
        // объект этого класса, создадим его бин в FirstRestAppLesson91Application, чтобы спринг автоматически внедрял его там гед нужно, а это убираем
//        ModelMapper modelMapper = new ModelMapper();
        // мапим из DTO в класс модели и возвращаем ее объект, он сам найдет поля, которые совпадают и их смапит
        return modelMapper.map(personDTO, Person.class);
    }

    private PersonDTO convertToPersonDTO(Person person) {
        return modelMapper.map(person, PersonDTO.class);
    }

}
