package ru.shuman.Project_Aibolit_Server.dto;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class JournalDTO {

    private Integer id;

    private String diagnosis;

    private String complaint;

    private String anamnesis;

    private String therapy;

    private String recommendation;

    private boolean published;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;

}
