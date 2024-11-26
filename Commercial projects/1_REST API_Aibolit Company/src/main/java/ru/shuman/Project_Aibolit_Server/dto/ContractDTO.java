package ru.shuman.Project_Aibolit_Server.dto;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.Date;

@Getter
@Setter
public class ContractDTO {

    private Integer id;

    private Date date_start;

    private Date date_end;

    private String description;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;

    private UserDTO user;

    private PatientDTO patient;

    private TypeContractDTO typeContract;

}
