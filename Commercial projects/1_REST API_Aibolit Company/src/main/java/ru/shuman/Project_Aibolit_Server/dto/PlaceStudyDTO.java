package ru.shuman.Project_Aibolit_Server.dto;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.List;

@Getter
@Setter
public class PlaceStudyDTO {

    private Integer id;

    @NotNull(message = "Наименование места учебы пациента отсутствует")
    @NotEmpty(message = "Наименование места учебы пациента не заполнено")
    @Size(max = 100, message = "Наименование места учебы должно быть не более 100 символов")
    private String name;

}
