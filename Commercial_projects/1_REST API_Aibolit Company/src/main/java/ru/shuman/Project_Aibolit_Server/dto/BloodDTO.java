package ru.shuman.Project_Aibolit_Server.dto;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.List;

@Getter
@Setter
public class BloodDTO {

    private Integer id;

    @NotEmpty(message = "Наименование группы крови отсутствует или не заполнено")
    @Size(max = 50, message = "Наименование группы крови должно быть не более 50 символов")
    private String name;

}
