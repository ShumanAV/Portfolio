package ru.shuman.Project_Aibolit_Server.controllers;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ru.shuman.Project_Aibolit_Server.dto.PriceDTO;
import ru.shuman.Project_Aibolit_Server.models.Price;
import ru.shuman.Project_Aibolit_Server.services.PriceService;
import ru.shuman.Project_Aibolit_Server.util.errors.PriceErrorResponse;
import ru.shuman.Project_Aibolit_Server.util.exceptions.PriceNotCreatedOrUpdatedException;
import ru.shuman.Project_Aibolit_Server.util.exceptions.PriceNotFoundException;
import ru.shuman.Project_Aibolit_Server.util.validators.PriceIdValidator;
import ru.shuman.Project_Aibolit_Server.util.validators.PriceValidator;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

import static ru.shuman.Project_Aibolit_Server.util.GeneralMethods.checkingForErrorsAndThrowsException;

@RestController
@RequestMapping("/prices")
public class PriceController {

    private final PriceService priceService;
    private final PriceIdValidator priceIdValidator;
    private final PriceValidator priceValidator;
    private final ModelMapper modelMapper;

    /*
    Внедрение зависимостей
     */
    @Autowired
    public PriceController(PriceService priceService, PriceIdValidator priceIdValidator, PriceValidator priceValidator,
                           ModelMapper modelMapper) {
        this.priceService = priceService;
        this.priceIdValidator = priceIdValidator;
        this.priceValidator = priceValidator;
        this.modelMapper = modelMapper;
    }

    /*
    Метод формирует и возвращает список прайсов в обертке ResponseEntity, в URL может быть параметр запроса published,
    тогда возвращает список с учетом этого
     */
    @GetMapping
    public ResponseEntity<List<PriceDTO>> sendListPrices(@RequestParam(value = "published", required = false) Boolean published) {

        List<Price> prices;
        if (published == null) {
            prices = priceService.findAll();
        } else {
            prices = priceService.findAllByPublished(published);
        }

        List<PriceDTO> priceDTOList = prices.stream().map(this::convertToPriceDTO).collect(Collectors.toList());

        return new ResponseEntity<>(priceDTOList, HttpStatus.OK);
    }

    /*
    Метод возвращает один прайс по id в обертке ResponseEntity, id берем из url,
    при помощи @ModelAttribute создаем пустой объект типа Price, устанавливаем в нем переданный id, далее валидируем id,
    находим прайс и возвращаем его
     */
    @GetMapping("/{id}")
    public ResponseEntity<PriceDTO> sendOnePrice(@PathVariable(value = "id") int priceId,
                                                 @ModelAttribute(value = "price") Price price,
                                                 BindingResult bindingResult) {

        price.setId(priceId);

        priceIdValidator.validate(price, bindingResult);

        checkingForErrorsAndThrowsException(bindingResult, PriceNotFoundException.class);

        PriceDTO priceDTO = convertToPriceDTO(priceService.findById(priceId).get());

        return new ResponseEntity<>(priceDTO, HttpStatus.OK);

    }

    /*
    Метод создает новый прайс, на вход поступает объект PriceDTO в виде json, принимаем его, валидируем его,
    в случае отсутствия ошибок при валидации создаем новый прайс, возвращаем код 200 в обертке ResponseEntity
     */
    @PostMapping
    public ResponseEntity<HttpStatus> create(@RequestBody @Valid PriceDTO priceDTO,
                                             BindingResult bindingResult) {

        Price price = convertToPrice(priceDTO);

        priceValidator.validate(price, bindingResult);

        checkingForErrorsAndThrowsException(bindingResult, PriceNotCreatedOrUpdatedException.class);

        priceService.create(price);

        return new ResponseEntity<>(HttpStatus.OK);
    }

    /*
    Метод изменяет существующий прайс, в URL передается id и в виде json объект PriceDTO с новыми данными
    для изменения, валидируем его, при отсутствии ошибок сохраняем изменения, возвращаем код 200 в обертке ResponseEntity
     */
    @PatchMapping("/{id}")
    public ResponseEntity<HttpStatus> update(@PathVariable(value = "id") int priceId,
                                             @RequestBody @Valid PriceDTO priceDTO,
                                             BindingResult bindingResult) {
        priceDTO.setId(priceId);
        Price price = convertToPrice(priceDTO);

        priceIdValidator.validate(price, bindingResult);
        priceValidator.validate(price, bindingResult);

        checkingForErrorsAndThrowsException(bindingResult, PriceNotCreatedOrUpdatedException.class);

        priceService.update(price);

        return new ResponseEntity<>(HttpStatus.OK);
    }

    /*
    Метод обработчик исключения PriceNotFoundException
     */
    @ExceptionHandler
    private ResponseEntity<PriceErrorResponse> handleException(PriceNotFoundException e) {
        PriceErrorResponse response = new PriceErrorResponse(
                e.getMessage(),
                System.currentTimeMillis()
        );

        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    /*
    Метод обработчик исключения PriceNotCreatedOrUpdatedException
     */
    @ExceptionHandler
    private ResponseEntity<PriceErrorResponse> handleException(PriceNotCreatedOrUpdatedException e) {
        PriceErrorResponse response = new PriceErrorResponse(
                e.getMessage(),
                System.currentTimeMillis()
        );

        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    /*
    Метод конверсии из DTO в модель
     */
    private Price convertToPrice(PriceDTO priceDTO) {
        return this.modelMapper.map(priceDTO, Price.class);
    }

    /*
    Метод конверсии из модели в DTO
     */
    private PriceDTO convertToPriceDTO(Price price) {
        return this.modelMapper.map(price, PriceDTO.class);
    }
}
