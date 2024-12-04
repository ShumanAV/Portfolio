package ru.shuman.Project_Aibolit_Server.dto;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
public class PriceDTO {

    private Integer id;

    @NotNull(message = "Наименование услуги не заполнено")
    @Size(max = 255, message = "Наименование услуги должно быть не более 255 символов")
    private String name;

    @NotNull(message = "Стоимость услуги не заполнена")
    @Max(value = 999999999, message = "Стоимость услуги должна быть не более 999 999 999")
    @Min(value = 0, message = "Стоимость услуги должна быть более 0")
    private Integer cost;

    private Boolean published;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;

}
