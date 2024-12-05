package ru.shuman.Project_Aibolit_Server.dto;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.Date;

@Getter
@Setter
public class ContractDTO {

    private Integer id;

    @NotNull(message = "Дата начала договора отсутствует")
    private Date date_start;

    @NotNull(message = "Дата окончания договора отсутствует")
    private Date date_end;

    @NotNull(message = "Описание договора отсутствует")
    private String description;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;

    @NotNull(message = "Врач в договоре отсутствует")
    private DoctorDTO doctor;

    @NotNull(message = "Пациент в договоре отсутствует")
    private PatientDTO patient;

    @NotNull(message = "Тип договора отсутствует")
    private TypeContractDTO typeContract;

}
