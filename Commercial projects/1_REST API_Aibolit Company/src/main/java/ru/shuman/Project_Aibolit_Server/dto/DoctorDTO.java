package ru.shuman.Project_Aibolit_Server.dto;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@Setter
public class DoctorDTO {

    private Integer id;

    @Size(max = 100, message = "Фамилия должна быть не более 100 символов")
    private String lastname;

    @NotEmpty(message = "Имя не может быть пустым")
    @Size(max = 100, message = "Имя должно быть не более 100 символов")
    private String firstname;

    @Size(max = 100, message = "Отчество должно быть не более 100 символов")
    private String patronymic;

    @Size(max = 30, message = "Номер телефона должен быть не более 30 символов")
    private String phone;

    @Size(max = 100, message = "СНИЛС должен быть не более 100 символов")
    private String snils;

    @Size(max = 20, message = "ИНН должен быть не более 20 символов")
    private String inn;

    private String description;

    private LocalDate birthday;

    private boolean accessToSystem;

    private boolean showInSchedule;

    private boolean published;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;

    private SpecializationDTO specialization;

    private ProfileDTO profileDTO;
}
