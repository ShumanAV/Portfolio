package ru.shuman.Project_Aibolit_Server.controllers;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.shuman.Project_Aibolit_Server.dto.RoleDTO;
import ru.shuman.Project_Aibolit_Server.dto.TypeEmploymentDTO;
import ru.shuman.Project_Aibolit_Server.models.Role;
import ru.shuman.Project_Aibolit_Server.models.TypeEmployment;
import ru.shuman.Project_Aibolit_Server.services.TypeEmploymentService;
import ru.shuman.Project_Aibolit_Server.util.validators.TypeEmploymentIdValidator;
import ru.shuman.Project_Aibolit_Server.util.validators.TypeEmploymentValidator;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/typeemployments")
public class TypeEmploymentController {

    private final TypeEmploymentService typeEmploymentService;
    private final TypeEmploymentIdValidator typeEmploymentIdValidator;
    private final TypeEmploymentValidator typeEmploymentValidator;
    private final ModelMapper modelMapper;

    @Autowired
    public TypeEmploymentController(TypeEmploymentService typeEmploymentService,
                                    TypeEmploymentIdValidator typeEmploymentIdValidator,
                                    TypeEmploymentValidator typeEmploymentValidator, ModelMapper modelMapper) {
        this.typeEmploymentService = typeEmploymentService;
        this.typeEmploymentIdValidator = typeEmploymentIdValidator;
        this.typeEmploymentValidator = typeEmploymentValidator;
        this.modelMapper = modelMapper;
    }

    @GetMapping
    public ResponseEntity<List<TypeEmploymentDTO>> sendListTypeEmployments() {

        List<TypeEmployment> typeEmployments = typeEmploymentService.findAll();

        List<TypeEmploymentDTO> typeEmploymentDTOList = typeEmployments.stream().map(this::convertToTypeEmploymentDTO).collect(Collectors.toList());

        return new ResponseEntity<>(typeEmploymentDTOList, HttpStatus.OK);
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
    public TypeEmployment convertToTypeEmployment(TypeEmploymentDTO typeEmploymentDTO) {
        return this.modelMapper.map(typeEmploymentDTO, TypeEmployment.class);
    }

    // Метод конверсии из модели в DTO
    public TypeEmploymentDTO convertToTypeEmploymentDTO(TypeEmployment typeEmployment) {
        return this.modelMapper.map(typeEmployment, TypeEmploymentDTO.class);
    }
}
