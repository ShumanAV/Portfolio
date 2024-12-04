package ru.shuman.Project_Aibolit_Server.dto;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.List;

@Getter
@Setter
public class EducationDTO {

    private Integer id;

    @NotNull(message = "Наименование образования отсутствует")
    @NotEmpty(message = "Наименование образования не заполнено")
    @Size(max = 50, message = "Наименование образования должно быть не более 50 символов")
    private String name;

}
