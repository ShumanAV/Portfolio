package ru.shuman.Project_Aibolit_Server.dto;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.List;

@Getter
@Setter
public class RegionDTO {

    private Integer id;

    @Max(value = 9999, message = "Код региона должен быть не более 9 999")
    @Min(value = 0, message = "Код региона должен быть более 0")
    private Integer code;

    @NotNull(message = "Наименование региона не должно быть пустым")
    @Size(max = 255, message = "Наименование региона должно быть не более 255 символов")
    private String name;

}
