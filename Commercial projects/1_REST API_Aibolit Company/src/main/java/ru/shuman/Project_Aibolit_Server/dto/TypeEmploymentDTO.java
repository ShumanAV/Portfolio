package ru.shuman.Project_Aibolit_Server.dto;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.Size;

@Getter
@Setter
public class TypeEmploymentDTO {

    private Integer id;

    @Size(max = 100, message = "Наименование типа занятости должно быть не более 100 символов")
    private String name;

}
