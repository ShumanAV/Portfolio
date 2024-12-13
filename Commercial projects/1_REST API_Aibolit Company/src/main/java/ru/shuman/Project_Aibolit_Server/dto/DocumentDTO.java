package ru.shuman.Project_Aibolit_Server.dto;

import lombok.Getter;
import lombok.Setter;
import ru.shuman.Project_Aibolit_Server.models.TypeDoc;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Date;
import java.util.List;

@Getter
@Setter
public class DocumentDTO {

    private Integer id;

    @NotEmpty(message = "Наименование документа отсутствует или не заполнено")
    @Size(max = 255, message = "Наименование документа должно быть не более 255 символов")
    private String name;

    @NotNull(message = "Тип документа отсутствует")
    private TypeDocDTO typeDoc;

}
