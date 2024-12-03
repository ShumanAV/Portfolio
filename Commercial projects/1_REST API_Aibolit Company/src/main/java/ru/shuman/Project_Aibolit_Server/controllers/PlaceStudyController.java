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

    /*
    Внедрение зависимостей
     */
    @Autowired
    public PlaceStudyController(PlaceStudyService placeStudyService, PlaceStudyIdValidator placeStudyIdValidator,
                                PlaceStudyValidator placeStudyValidator, ModelMapper modelMapper) {
        this.placeStudyService = placeStudyService;
        this.placeStudyIdValidator = placeStudyIdValidator;
        this.placeStudyValidator = placeStudyValidator;
        this.modelMapper = modelMapper;
    }

    /*
    Метод формирует и возвращает список мест учебы пациентов в обертке ResponseEntity
     */
    @GetMapping
    public ResponseEntity<List<PlaceStudyDTO>> sendListPlaceStudies() {

        List<PlaceStudy> placeStudies = placeStudyService.findAll();

        List<PlaceStudyDTO> placeStudyDTOList = placeStudies.stream().map(this::convertToPlaceStudyDTO).collect(Collectors.toList());

        return new ResponseEntity<>(placeStudyDTOList, HttpStatus.OK);
    }

    /*
    Метод конверсии из DTO в модель
     */
    private PlaceStudy convertToPlaceStudy(PlaceStudyDTO placeStudyDTO) {
        return this.modelMapper.map(placeStudyDTO, PlaceStudy.class);
    }

    /*
    Метод конверсии из модели в DTO
     */
    private PlaceStudyDTO convertToPlaceStudyDTO(PlaceStudy placeStudy) {
        return this.modelMapper.map(placeStudy, PlaceStudyDTO.class);
    }
}
