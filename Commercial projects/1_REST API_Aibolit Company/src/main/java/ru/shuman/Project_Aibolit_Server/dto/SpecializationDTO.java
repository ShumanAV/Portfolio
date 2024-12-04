package ru.shuman.Project_Aibolit_Server.dto;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.Size;
import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
public class SpecializationDTO {

    private Integer id;

    @Size(max = 100, message = "Наименование специализации должно быть не более 100 символов")
    private String name;

    private String description;

    private Boolean published;
}
