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
public class ContractDTO {

    private Integer id;

    @NotNull(message = "Дата начала договора отсутствует")
    @Size(max = 30, message = "Дата начала действия договора должна быть не более 30 символов")
    private String date_start;

    @NotNull(message = "Дата окончания договора отсутствует")
    @Size(max = 30, message = "Дата окончания действия договора должна быть не более 30 символов")
    private String date_end;

    @NotNull(message = "Описание договора отсутствует")
    private String description;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;

    @NotNull(message = "Врач в договоре отсутствует")
    private DoctorDTO doctor;

    @Valid
    @NotNull(message = "Пациент в договоре отсутствует")
    private PatientDTO patient;

    @Valid
    @NotNull(message = "Тип договора отсутствует")
    private TypeContractDTO typeContract;

}
