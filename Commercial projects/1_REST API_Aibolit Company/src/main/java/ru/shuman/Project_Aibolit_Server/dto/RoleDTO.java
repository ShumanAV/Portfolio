package ru.shuman.Project_Aibolit_Server.dto;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.Size;
import java.util.List;

@Getter
@Setter
public class RoleDTO {

    private Integer id;

    @Size(max = 50, message = "Наименование роли на английском языке должно быть не более 50 символов")
    private String name;

    @Size(max = 50, message = "Наименование роли на русском языке должно быть не более 50 символов")
    private String runame;

}
