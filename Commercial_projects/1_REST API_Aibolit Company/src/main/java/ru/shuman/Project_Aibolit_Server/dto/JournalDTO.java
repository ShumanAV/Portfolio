package ru.shuman.Project_Aibolit_Server.dto;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

@Getter
@Setter
public class JournalDTO {

    private Integer id;

    @NotNull(message = "Диагноз отсутствует")
    private String diagnosis;

    @NotNull(message = "Поле жалобы отсутствует")
    private String complaint;

    @NotNull(message = "Поле анамнез отсутствует")
    private String anamnesis;

    @NotNull(message = "Поле лечение отсутствует")
    private String therapy;

    @NotNull(message = "Рекомендации отсутствуют")
    private String recommendation;

    @NotNull(message = "Поле published отсутствует")
    private Boolean published;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;

}
