package ru.shuman.Project_Aibolit_Server.controllers;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.shuman.Project_Aibolit_Server.dto.BloodDTO;
import ru.shuman.Project_Aibolit_Server.dto.RoleDTO;
import ru.shuman.Project_Aibolit_Server.models.Blood;
import ru.shuman.Project_Aibolit_Server.models.Role;
import ru.shuman.Project_Aibolit_Server.services.BloodService;
import ru.shuman.Project_Aibolit_Server.util.validators.BloodIdValidator;
import ru.shuman.Project_Aibolit_Server.util.validators.BloodValidator;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/bloods")
public class BloodController {

    private final BloodService bloodService;
    private final BloodIdValidator bloodIdValidator;
    private final BloodValidator bloodValidator;
    private final ModelMapper modelMapper;

    @Autowired
    public BloodController(BloodService bloodService, BloodIdValidator bloodIdValidator, BloodValidator bloodValidator, ModelMapper modelMapper) {
        this.bloodService = bloodService;
        this.bloodIdValidator = bloodIdValidator;
        this.bloodValidator = bloodValidator;
        this.modelMapper = modelMapper;
    }

    @GetMapping
    public ResponseEntity<List<BloodDTO>> sendListBlood() {

        List<Blood> bloodList = bloodService.findAll();

        List<BloodDTO> bloodDTOList = bloodList.stream().map(this::convertToBloodDTO).collect(Collectors.toList());

        return new ResponseEntity<>(bloodDTOList, HttpStatus.OK);
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
    public Blood convertToBlood(BloodDTO bloodDTO) {
        return this.modelMapper.map(bloodDTO, Blood.class);
    }

    // Метод конверсии из модели в DTO
    public BloodDTO convertToBloodDTO(Blood blood) {
        return this.modelMapper.map(blood, BloodDTO.class);
    }
}
