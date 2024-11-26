package ru.shuman.Project_Aibolit_Server.dto;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.List;

@Getter
@Setter
public class TypeDocDTO {

    private Integer id;

    @NotNull(message = "Наименование типа документа не должно быть пустым")
    @Size(max = 255, message = "Наименование типа документа должно быть не более 255 символов")
    private String name;

}
