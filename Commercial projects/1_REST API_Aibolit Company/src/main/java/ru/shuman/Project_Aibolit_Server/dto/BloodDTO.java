package ru.shuman.Project_Aibolit_Server.dto;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.Size;
import java.util.List;

@Getter
@Setter
public class BloodDTO {

    private Integer id;

    @Size(max = 30, message = "Наименование группы крови должно быть не более 30 символов")
    private String name;

}
