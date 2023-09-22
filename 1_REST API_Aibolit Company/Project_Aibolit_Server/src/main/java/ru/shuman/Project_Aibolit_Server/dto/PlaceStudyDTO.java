package ru.shuman.Project_Aibolit_Server.dto;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.Size;
import java.util.List;

@Getter
@Setter
public class PlaceStudyDTO {

    private Integer id;

    @Size(max = 100, message = "Наименование места учебы должно быть не более 100 символов")
    private String name;

}
