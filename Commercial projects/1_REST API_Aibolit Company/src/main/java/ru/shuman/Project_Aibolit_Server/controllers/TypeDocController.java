package ru.shuman.Project_Aibolit_Server.controllers;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.shuman.Project_Aibolit_Server.dto.TypeDocDTO;
import ru.shuman.Project_Aibolit_Server.models.TypeDoc;
import ru.shuman.Project_Aibolit_Server.services.TypeDocService;
import ru.shuman.Project_Aibolit_Server.util.validators.TypeDocIdValidator;
import ru.shuman.Project_Aibolit_Server.util.validators.TypeDocValidator;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/typedocs")
public class TypeDocController {

    private final TypeDocService typeDocService;
    private final TypeDocIdValidator typeDocIdValidator;
    private final TypeDocValidator typeDocValidator;
    private final ModelMapper modelMapper;

    @Autowired
    public TypeDocController(TypeDocService typeDocService, TypeDocIdValidator typeDocIdValidator, TypeDocValidator typeDocValidator, ModelMapper modelMapper) {
        this.typeDocService = typeDocService;
        this.typeDocIdValidator = typeDocIdValidator;
        this.typeDocValidator = typeDocValidator;
        this.modelMapper = modelMapper;
    }

    @GetMapping
    public ResponseEntity<List<TypeDocDTO>> sendListTypeDocs() {

        List<TypeDoc> typeDocs = typeDocService.findAll();

        List<TypeDocDTO> typeDocDTOList = typeDocs.stream().map(this::convertToTypeDocDTO).collect(Collectors.toList());

        return new ResponseEntity<>(typeDocDTOList, HttpStatus.OK);
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
    public TypeDoc convertToTypeDoc(TypeDocDTO typeDocDTO) {
        return this.modelMapper.map(typeDocDTO, TypeDoc.class);
    }

    // Метод конверсии из модели в DTO
    public TypeDocDTO convertToTypeDocDTO(TypeDoc typeDoc) {
        return this.modelMapper.map(typeDoc, TypeDocDTO.class);
    }
}
