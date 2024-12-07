package ru.shuman.Project_Aibolit_Server.dto;

import lombok.Getter;
import lombok.Setter;

import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@Setter
public class DoctorDTO {

    private Integer id;

    @NotNull(message = "Фамилия отсутствует")
    @Size(max = 100, message = "Фамилия должна быть не более 100 символов")
    private String lastname;

    @NotEmpty(message = "Имя отсутствует или не заполнено")
    @Size(max = 100, message = "Имя должно быть не более 100 символов")
    private String firstname;

    @NotNull(message = "Отчество отсутствует")
    @Size(max = 100, message = "Отчество должно быть не более 100 символов")
    private String patronymic;

    @NotNull(message = "Номер телефона отсутствует")
    @Size(max = 30, message = "Номер телефона должен быть не более 30 символов")
    private String phone;

    @NotNull(message = "СНИЛС отсутствует")
    @Size(max = 100, message = "СНИЛС должен быть не более 100 символов")
    private String snils;

    @NotNull(message = "ИНН отсутствует")
    @Size(max = 20, message = "ИНН должен быть не более 20 символов")
    private String inn;

    @NotNull(message = "Описание врача отсутствует")
    private String description;

    @NotNull(message = "День рождения отсутствует")
    private LocalDate birthday;

    @NotNull(message = "Поле доступа к системе отсутствует")
    private Boolean accessToSystem;

    @NotNull(message = "Поле отображения в расписании отсутствует")
    private Boolean showInSchedule;

    @NotNull(message = "Поле published отсутствует")
    private Boolean published;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;

    @NotNull(message = "Специализация отсутствует")
    private SpecializationDTO specialization;

    @Valid
    private ProfileDTO profile;
}
