package ru.shuman.Project_Aibolit_Server.dto;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
public class SpecializationDTO {

    private Integer id;

    @NotNull(message = "Наименование специализации отсутствует")
    @NotEmpty(message = "Наименование специализации не заполнено")
    @Size(max = 100, message = "Наименование специализации должно быть не более 100 символов")
    private String name;

    @NotNull(message = "Описание специализации отсутствует")
    private String description;

    @NotNull(message = "Поле published отсутствует")
    private Boolean published;
}
