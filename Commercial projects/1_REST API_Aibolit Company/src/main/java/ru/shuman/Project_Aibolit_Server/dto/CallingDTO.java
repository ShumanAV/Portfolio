package ru.shuman.Project_Aibolit_Server.dto;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.Size;
import java.time.LocalDateTime;
import java.util.Date;

@Getter
@Setter
public class CallingDTO {

    private Integer id;

    private Date date_visit;

    private Date date_call;

    @Size(max = 50, message = "Время начала должно быть не более 50 символов")
    private String time_start;

    @Size(max = 50, message = "Время окончания должно быть не более 50 символов")
    private String time_end;

    private String description;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;

    private DoctorDTO doctorDTO;

    private JournalDTO diary;

    private PriceDTO price;

    private PatientDTO patient;

}
