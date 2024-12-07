package ru.shuman.Project_Aibolit_Server.dto;

import lombok.Getter;
import lombok.Setter;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;
import java.util.Date;

@Getter
@Setter
public class CallingDTO {

    private Integer id;

    @NotNull(message = "Дата посещения отсутствует")
    @Size(max = 30, message = "Дата посещения врача должна быть не более 30 символов")
    private String date_visit;

    @NotNull(message = "Дата вызова отсутствует")
    @Size(max = 30, message = "Дата вызова врача должна быть не более 30 символов")
    private String date_call;

    @NotNull(message = "Время начала вызова отсутствует")
    @Size(max = 50, message = "Время начала должно быть не более 50 символов")
    private String time_start;

    @NotNull(message = "Время окончания вызова отсутствует")
    @Size(max = 50, message = "Время окончания должно быть не более 50 символов")
    private String time_end;

    @NotNull(message = "Описание вызова отсутствует")
    private String description;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;

    @NotNull(message = "Поле врач отсутствует")
    private DoctorDTO doctor;

    @Valid
    @NotNull(message = "Карточка вызова отсутствует")
    private JournalDTO journal;

    @NotNull(message = "Прайс отсутствует")
    private PriceDTO price;

    @Valid
    @NotNull(message = "Пациент отсутствует")
    private PatientDTO patient;

}
