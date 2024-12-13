package ru.shuman.Project_Aibolit_Server.dto;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.List;

@Getter
@Setter
public class GenderDTO {

    private Integer id;

    @NotEmpty(message = "Наименование гендерного признака отсутствует или не заполнено")
    @Size(max = 20, message = "Наименование гендерного признака должно быть не более 20 символов")
    private String name;

}
