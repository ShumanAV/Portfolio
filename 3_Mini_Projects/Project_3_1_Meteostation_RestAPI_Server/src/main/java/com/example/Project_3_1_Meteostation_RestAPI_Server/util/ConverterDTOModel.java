package com.example.Project_3_1_Meteostation_RestAPI_Server.util;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

// класс конвертации из DTO в модели и обратно
@Component
public class ConverterDTOModel {

    // метод создания нового экземпляра класса ModelMapper
    public ModelMapper modelMapper() {
        return new ModelMapper();
    }

    // метод конвертации из DTO в модель и наоборот
    public Object convert(Object o, Class<?> needClass) {
        return modelMapper().map(o,needClass);
    }

}
