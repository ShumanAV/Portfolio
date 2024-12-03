package ru.shuman.Project_Aibolit_Server.controllers;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ru.shuman.Project_Aibolit_Server.dto.RegionDTO;
import ru.shuman.Project_Aibolit_Server.models.Region;
import ru.shuman.Project_Aibolit_Server.services.RegionService;
import ru.shuman.Project_Aibolit_Server.util.errors.RegionErrorResponse;
import ru.shuman.Project_Aibolit_Server.util.exceptions.RegionNotCreatedOrUpdatedException;
import ru.shuman.Project_Aibolit_Server.util.exceptions.RegionNotFoundException;
import ru.shuman.Project_Aibolit_Server.util.validators.RegionIdValidator;
import ru.shuman.Project_Aibolit_Server.util.validators.RegionValidator;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

import static ru.shuman.Project_Aibolit_Server.util.GeneralMethods.checkingForErrorsAndThrowsException;

@RestController
@RequestMapping("/regions")
public class RegionController {

    private final RegionService regionService;
    private final RegionIdValidator regionIdValidator;
    private final RegionValidator regionValidator;
    private final ModelMapper modelMapper;

    /*
    Внедрение зависимостей
     */
    @Autowired
    public RegionController(RegionService regionService, RegionIdValidator regionIdValidator,
                            RegionValidator regionValidator, ModelMapper modelMapper) {
        this.regionService = regionService;
        this.regionIdValidator = regionIdValidator;
        this.regionValidator = regionValidator;
        this.modelMapper = modelMapper;
    }

    /*
    Метод формирует и возвращает список регионов в обертке ResponseEntity
     */
    @GetMapping
    public ResponseEntity<List<RegionDTO>> sendListRegions() {

        List<Region> regions = regionService.findAll();

        List<RegionDTO> regionDTOList = regions.stream().map(this::convertToRegionDTO).collect(Collectors.toList());

        return new ResponseEntity<>(regionDTOList, HttpStatus.OK);
    }

    /*
    Метод возвращает один регион по id в обертке ResponseEntity, id берем из url,
    при помощи @ModelAttribute создаем пустой объект типа Region, устанавливаем в нем переданный id, далее валидируем id,
    находим регион и возвращаем его
     */
    @GetMapping("/{id}")
    public ResponseEntity<RegionDTO> sendOneRegion(@PathVariable(value = "id") int regionId,
                                                 @ModelAttribute(value = "region") Region region,
                                                 BindingResult bindingResult) {

        region.setId(regionId);

        regionIdValidator.validate(region, bindingResult);

        checkingForErrorsAndThrowsException(bindingResult, RegionNotFoundException.class);

        RegionDTO regionDTO = convertToRegionDTO(regionService.findById(regionId).get());

        return new ResponseEntity<>(regionDTO, HttpStatus.OK);

    }

    /*
    Метод создает новый регион, на вход поступает объект RegionDTO в виде json, принимаем его, валидируем его,
    в случае отсутствия ошибок при валидации создаем новый регион, возвращаем код 200 в обертке ResponseEntity
     */
    @PostMapping
    public ResponseEntity<HttpStatus> create(@RequestBody @Valid RegionDTO regionDTO,
                                             BindingResult bindingResult) {

        Region region = convertToRegion(regionDTO);

        regionValidator.validate(region, bindingResult);

        checkingForErrorsAndThrowsException(bindingResult, RegionNotCreatedOrUpdatedException.class);

        regionService.create(region);

        return new ResponseEntity<>(HttpStatus.OK);
    }

    /*
    Метод изменяет существующий регион, в URL передается id и в виде json объект BloodDTO с новыми данными
    для изменения, валидируем его, при отсутствии ошибок сохраняем изменения, возвращаем код 200 в обертке ResponseEntity
     */
    @PatchMapping("/{id}")
    public ResponseEntity<HttpStatus> update(@PathVariable(value = "id") int regionId,
                                             @RequestBody @Valid RegionDTO regionDTO,
                                             BindingResult bindingResult) {
        regionDTO.setId(regionId);
        Region region = convertToRegion(regionDTO);

        regionIdValidator.validate(region, bindingResult);
        regionValidator.validate(region, bindingResult);

        checkingForErrorsAndThrowsException(bindingResult, RegionNotCreatedOrUpdatedException.class);

        regionService.update(region);

        return new ResponseEntity<>(HttpStatus.OK);
    }

    // Метод обработчик исключения RegionNotFoundException
    @ExceptionHandler
    private ResponseEntity<RegionErrorResponse> handleException(RegionNotFoundException e) {
        RegionErrorResponse response = new RegionErrorResponse(
                e.getMessage(),
                System.currentTimeMillis()
        );

        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    // Метод обработчик исключения RegionNotCreatedOrUpdatedException
    @ExceptionHandler
    private ResponseEntity<RegionErrorResponse> handleException(RegionNotCreatedOrUpdatedException e) {
        RegionErrorResponse response = new RegionErrorResponse(
                e.getMessage(),
                System.currentTimeMillis()
        );

        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    // Метод конверсии из DTO в модель
    private Region convertToRegion(RegionDTO regionDTO) {
        return this.modelMapper.map(regionDTO, Region.class);
    }

    // Метод конверсии из модели в DTO
    private RegionDTO convertToRegionDTO(Region region) {
        return this.modelMapper.map(region, RegionDTO.class);
    }
}
