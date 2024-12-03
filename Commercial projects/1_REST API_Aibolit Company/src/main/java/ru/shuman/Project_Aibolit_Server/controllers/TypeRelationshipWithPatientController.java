package ru.shuman.Project_Aibolit_Server.controllers;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.shuman.Project_Aibolit_Server.dto.TypeRelationshipWithPatientDTO;
import ru.shuman.Project_Aibolit_Server.models.TypeRelationshipWithPatient;
import ru.shuman.Project_Aibolit_Server.services.TypeRelationshipWithPatientService;
import ru.shuman.Project_Aibolit_Server.util.validators.TypeRelationshipWithPatientIdValidator;
import ru.shuman.Project_Aibolit_Server.util.validators.TypeRelationshipWithPatientValidator;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/typesrelationships")
public class TypeRelationshipWithPatientController {

    private final TypeRelationshipWithPatientService relationshipService;
    private final TypeRelationshipWithPatientIdValidator idValidator;
    private final TypeRelationshipWithPatientValidator validator;
    private final ModelMapper modelMapper;

    @Autowired
    public TypeRelationshipWithPatientController(TypeRelationshipWithPatientService relationshipService,
                                                 TypeRelationshipWithPatientIdValidator idValidator,
                                                 TypeRelationshipWithPatientValidator validator, ModelMapper modelMapper) {
        this.relationshipService = relationshipService;
        this.idValidator = idValidator;
        this.validator = validator;
        this.modelMapper = modelMapper;
    }

    @GetMapping
    public ResponseEntity<List<TypeRelationshipWithPatientDTO>> sendListTypesRelationshipWithPatient() {

        List<TypeRelationshipWithPatient> relationshipList = relationshipService.findAll();

        List<TypeRelationshipWithPatientDTO> relationshipDTOList = relationshipList.stream().map(this::convertToRelationshipDTO).collect(Collectors.toList());

        return new ResponseEntity<>(relationshipDTOList, HttpStatus.OK);
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
    private TypeRelationshipWithPatient convertToRelationship(TypeRelationshipWithPatientDTO dto) {
        return this.modelMapper.map(dto, TypeRelationshipWithPatient.class);
    }

    // Метод конверсии из модели в DTO
    private TypeRelationshipWithPatientDTO convertToRelationshipDTO(TypeRelationshipWithPatient relationship) {
        return this.modelMapper.map(relationship, TypeRelationshipWithPatientDTO.class);
    }
}
