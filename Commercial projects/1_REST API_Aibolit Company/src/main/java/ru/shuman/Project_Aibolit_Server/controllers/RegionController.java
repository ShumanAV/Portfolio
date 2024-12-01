package ru.shuman.Project_Aibolit_Server.controllers;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.shuman.Project_Aibolit_Server.dto.PriceDTO;
import ru.shuman.Project_Aibolit_Server.dto.RegionDTO;
import ru.shuman.Project_Aibolit_Server.models.Price;
import ru.shuman.Project_Aibolit_Server.models.Region;
import ru.shuman.Project_Aibolit_Server.services.RegionService;
import ru.shuman.Project_Aibolit_Server.util.validators.RegionIdValidator;
import ru.shuman.Project_Aibolit_Server.util.validators.RegionValidator;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/regions")
public class RegionController {

    private final RegionService regionService;
    private final RegionIdValidator regionIdValidator;
    private final RegionValidator regionValidator;
    private final ModelMapper modelMapper;

    @Autowired
    public RegionController(RegionService regionService, RegionIdValidator regionIdValidator,
                            RegionValidator regionValidator, ModelMapper modelMapper) {
        this.regionService = regionService;
        this.regionIdValidator = regionIdValidator;
        this.regionValidator = regionValidator;
        this.modelMapper = modelMapper;
    }

    @GetMapping
    public ResponseEntity<List<RegionDTO>> sendListRegions() {

        List<Region> regions = regionService.findAll();

        List<RegionDTO> regionDTOList = regions.stream().map(this::convertToRegionDTO).collect(Collectors.toList());

        return new ResponseEntity<>(regionDTOList, HttpStatus.OK);
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
    private Region convertToRegion(RegionDTO regionDTO) {
        return this.modelMapper.map(regionDTO, Region.class);
    }

    // Метод конверсии из модели в DTO
    private RegionDTO convertToRegionDTO(Region region) {
        return this.modelMapper.map(region, RegionDTO.class);
    }
}
