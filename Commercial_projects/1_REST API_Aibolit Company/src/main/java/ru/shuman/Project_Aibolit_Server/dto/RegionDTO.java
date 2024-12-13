package ru.shuman.Project_Aibolit_Server.dto;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.validation.constraints.*;
import java.util.List;

@Getter
@Setter
public class RegionDTO {

    private Integer id;

    @NotNull(message = "Код региона отсутствует")
    @Max(value = 9999, message = "Код региона должен быть не более 9 999")
    @Min(value = 0, message = "Код региона должен быть более 0")
    private Integer code;

    @NotEmpty(message = "Наименование региона отсутствует или не заполнено")
    @Size(max = 255, message = "Наименование региона должно быть не более 255 символов")
    private String name;

}
