package ru.shuman.Project_Aibolit_Server.dto;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
public class PriceDTO {

    private Integer id;

    @Size(max = 255, message = "Наименование услуги должно быть не более 255 символов")
    private String name;

    @Max(value = 999999999, message = "Стоимость должна быть не более 999 999 999")
    @Min(value = 0, message = "Стоимость должна быть более 0")
    private int cost;

    private boolean published;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;

}
