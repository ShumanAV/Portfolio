package ru.shuman.Project_Aibolit_Server.controllers;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.shuman.Project_Aibolit_Server.dto.PlaceStudyDTO;
import ru.shuman.Project_Aibolit_Server.dto.RoleDTO;
import ru.shuman.Project_Aibolit_Server.models.PlaceStudy;
import ru.shuman.Project_Aibolit_Server.models.Role;
import ru.shuman.Project_Aibolit_Server.services.PlaceStudyService;
import ru.shuman.Project_Aibolit_Server.util.validators.PlaceStudyIdValidator;
import ru.shuman.Project_Aibolit_Server.util.validators.PlaceStudyValidator;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/placestudies")
public class PlaceStudyController {

    private final PlaceStudyService placeStudyService;
    private final PlaceStudyIdValidator placeStudyIdValidator;
    private final PlaceStudyValidator placeStudyValidator;
    private final ModelMapper modelMapper;

    @Autowired
    public PlaceStudyController(PlaceStudyService placeStudyService, PlaceStudyIdValidator placeStudyIdValidator,
                                PlaceStudyValidator placeStudyValidator, ModelMapper modelMapper) {
        this.placeStudyService = placeStudyService;
        this.placeStudyIdValidator = placeStudyIdValidator;
        this.placeStudyValidator = placeStudyValidator;
        this.modelMapper = modelMapper;
    }

    @GetMapping
    public ResponseEntity<List<PlaceStudyDTO>> sendListPlaceStudies() {

        List<PlaceStudy> placeStudies = placeStudyService.findAll();

        List<PlaceStudyDTO> placeStudyDTOList = placeStudies.stream().map(this::convertToPlaceStudyDTO).collect(Collectors.toList());

        return new ResponseEntity<>(placeStudyDTOList, HttpStatus.OK);
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
    public PlaceStudy convertToPlaceStudy(PlaceStudyDTO placeStudyDTO) {
        return this.modelMapper.map(placeStudyDTO, PlaceStudy.class);
    }

    // Метод конверсии из модели в DTO
    public PlaceStudyDTO convertToPlaceStudyDTO(PlaceStudy placeStudy) {
        return this.modelMapper.map(placeStudy, PlaceStudyDTO.class);
    }
}
