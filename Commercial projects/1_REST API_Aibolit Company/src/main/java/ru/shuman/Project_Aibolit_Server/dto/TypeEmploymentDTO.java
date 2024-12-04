package ru.shuman.Project_Aibolit_Server.dto;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Getter
@Setter
public class TypeEmploymentDTO {

    private Integer id;

    @NotNull(message = "Наименование типа занятости родителя отсутствует")
    @NotEmpty(message = "Наименование типа занятости родителя не заполнено")
    @Size(max = 100, message = "Наименование типа занятости родителя должно быть не более 100 символов")
    private String name;

}
