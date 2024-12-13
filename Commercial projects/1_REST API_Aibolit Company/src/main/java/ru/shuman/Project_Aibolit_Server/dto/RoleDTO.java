package ru.shuman.Project_Aibolit_Server.dto;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.List;

@Getter
@Setter
public class RoleDTO {

    private Integer id;

    @NotEmpty(message = "Наименование роли на английском языке отсутствует или не заполнено")
    @Size(max = 50, message = "Наименование роли на английском языке должно быть не более 50 символов")
    private String name;

    @NotNull(message = "Наименование роли на русском языке отсутствует")
    @Size(max = 50, message = "Наименование роли на русском языке должно быть не более 50 символов")
    private String runame;

}
