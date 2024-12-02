package ru.shuman.Project_Aibolit_Server.controllers;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.shuman.Project_Aibolit_Server.dto.GenderDTO;
import ru.shuman.Project_Aibolit_Server.models.Gender;
import ru.shuman.Project_Aibolit_Server.services.GenderService;
import ru.shuman.Project_Aibolit_Server.util.validators.GenderIdValidator;
import ru.shuman.Project_Aibolit_Server.util.validators.GenderValidator;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/genders")
public class GenderController {

    private final GenderService genderService;
    private final GenderIdValidator genderIdValidator;
    private final GenderValidator genderValidator;
    private final ModelMapper modelMapper;

    @Autowired
    public GenderController(GenderService genderService, GenderIdValidator genderIdValidator,
                            GenderValidator genderValidator, ModelMapper modelMapper) {
        this.genderService = genderService;
        this.genderIdValidator = genderIdValidator;
        this.genderValidator = genderValidator;
        this.modelMapper = modelMapper;
    }

    @GetMapping
    public ResponseEntity<List<GenderDTO>> sendListGenders() {

        List<Gender> genders = genderService.findAll();

        List<GenderDTO> genderDTOList = genders.stream().map(this::convertToGenderDTO).collect(Collectors.toList());

        return new ResponseEntity<>(genderDTOList, HttpStatus.OK);
    }

//    @GetMapping("/{id}")
//    public ResponseEntity<PriceDTO> sendOnePrice(@PathVariable(value = "id") int priceId,
//                                                 @ModelAttribute(value = "price") Price price,
//                                                 BindingResult bindingResult) {
//
//        price.setId(priceId);
//
//        priceIdValidator.validate(price, bindingResult);
//
//        StandardMethods.collectStringAboutErrors(bindingResult, PriceNotFound.class);
//
//        PriceDTO priceDTO = convertToPriceDTO(priceService.findById(priceId).get());
//
//        return new ResponseEntity<>(priceDTO, HttpStatus.OK);
//
//    }
//
//    @PostMapping
//    public ResponseEntity<HttpStatus> create(@RequestBody @Valid PriceDTO priceDTO,
//                                             BindingResult bindingResult) {
//
//        Price price = convertToPrice(priceDTO);
//
//        priceValidator.validate(price, bindingResult);
//
//        StandardMethods.collectStringAboutErrors(bindingResult, PriceNotCreatedOrUpdatedException.class);
//
//        priceService.create(price);
//
//        return new ResponseEntity<>(HttpStatus.OK);
//    }
//
//    @PatchMapping("/{id}")
//    public ResponseEntity<HttpStatus> update(@PathVariable(value = "id") int priceId,
//                                             @RequestBody @Valid PriceDTO priceDTO,
//                                             BindingResult bindingResult) {
//
//        Price price = convertToPrice(priceDTO);
//
//        priceIdValidator.validate(price, bindingResult);
//        priceValidator.validate(price, bindingResult);
//
//        StandardMethods.collectStringAboutErrors(bindingResult, PriceNotCreatedOrUpdatedException.class);
//
//        priceService.update(price);
//
//        return new ResponseEntity<>(HttpStatus.OK);
//    }
//
//    // Метод обработчик исключения PriceNotFound
//    @ExceptionHandler
//    private ResponseEntity<PriceErrorResponse> handleExceptionPriceNotFound(PriceNotFound e) {
//        PriceErrorResponse response = new PriceErrorResponse(
//                e.getMessage(),
//                System.currentTimeMillis()
//        );
//
//        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
//    }
//
//    // Метод обработчик исключения PriceNotCreatedOrUpdatedException
//    @ExceptionHandler
//    private ResponseEntity<PriceErrorResponse> handleExceptionPriceNotCreated(PriceNotCreatedOrUpdatedException e) {
//        PriceErrorResponse response = new PriceErrorResponse(
//                e.getMessage(),
//                System.currentTimeMillis()
//        );
//
//        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
//    }

    // Метод конверсии из DTO в модель
    private Gender convertToGender(GenderDTO genderDTO) {
        return this.modelMapper.map(genderDTO, Gender.class);
    }

    // Метод конверсии из модели в DTO
    private GenderDTO convertToGenderDTO(Gender gender) {
        return this.modelMapper.map(gender, GenderDTO.class);
    }
}
