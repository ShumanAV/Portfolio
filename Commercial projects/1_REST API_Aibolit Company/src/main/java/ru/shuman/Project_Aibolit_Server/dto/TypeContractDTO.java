package ru.shuman.Project_Aibolit_Server.dto;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.validation.constraints.*;
import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
public class TypeContractDTO {

    private Integer id;

    @NotEmpty(message = "Наименование типа контракта отсутствует или не заполнено")
    @Size(max = 255, message = "Наименование типа контракта должно быть не более 255 символов")
    private String name;

    @NotNull(message = "Стоимость типа контракта отсутствует")
    @Max(value = 999999999, message = "Стоимость должна быть не более 999 999 999")
    @Min(value = 0, message = "Стоимость должна быть более 0")
    private Integer cost;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;

}
