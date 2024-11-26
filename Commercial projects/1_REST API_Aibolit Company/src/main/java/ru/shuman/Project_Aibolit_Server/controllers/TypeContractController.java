package ru.shuman.Project_Aibolit_Server.controllers;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.shuman.Project_Aibolit_Server.dto.EducationDTO;
import ru.shuman.Project_Aibolit_Server.dto.TypeContractDTO;
import ru.shuman.Project_Aibolit_Server.models.Education;
import ru.shuman.Project_Aibolit_Server.models.TypeContract;
import ru.shuman.Project_Aibolit_Server.services.TypeContractService;
import ru.shuman.Project_Aibolit_Server.util.validators.TypeContractIdValidator;
import ru.shuman.Project_Aibolit_Server.util.validators.TypeContractValidator;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/typecontracts")
public class TypeContractController {

    private final TypeContractService typeContractService;
    private final TypeContractIdValidator typeContractIdValidator;
    private final TypeContractValidator typeContractValidator;
    private final ModelMapper modelMapper;

    @Autowired
    public TypeContractController(TypeContractService typeContractService, TypeContractIdValidator typeContractIdValidator,
                                  TypeContractValidator typeContractValidator, ModelMapper modelMapper) {
        this.typeContractService = typeContractService;
        this.typeContractIdValidator = typeContractIdValidator;
        this.typeContractValidator = typeContractValidator;
        this.modelMapper = modelMapper;
    }

    @GetMapping
    public ResponseEntity<List<TypeContractDTO>> sendListTypeContracts() {

        List<TypeContract> typeContracts = typeContractService.findAll();

        List<TypeContractDTO> typeContractDTOList = typeContracts.stream().map(this::convertToTypeContractDTO).collect(Collectors.toList());

        return new ResponseEntity<>(typeContractDTOList, HttpStatus.OK);
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
    public TypeContract convertToTypeContract(TypeContractDTO typeContractDTO) {
        return this.modelMapper.map(typeContractDTO, TypeContract.class);
    }

    // Метод конверсии из модели в DTO
    public TypeContractDTO convertToTypeContractDTO(TypeContract typeContract) {
        return this.modelMapper.map(typeContract, TypeContractDTO.class);
    }
}
