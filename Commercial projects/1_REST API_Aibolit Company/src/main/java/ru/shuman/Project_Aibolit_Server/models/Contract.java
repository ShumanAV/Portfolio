package ru.shuman.Project_Aibolit_Server.models;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
@ToString

@Entity
@Table(name = "contract")
public class Contract {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "date_start")
    @NotNull(message = "Дата начала договора отсутствует")
    private Date date_start;

    @Column(name = "date_end")
    @NotNull(message = "Дата окончания договора отсутствует")
    private Date date_end;

    @Column(name = "description")
    @NotNull(message = "Описание договора отсутствует")
    private String description;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    @ManyToOne
    @JoinColumn(name = "doctor_id", referencedColumnName = "id")
    @NotNull(message = "Врач в договоре отсутствует")
    private Doctor doctor;

    @ManyToOne
    @JoinColumn(name = "patient_id", referencedColumnName = "id")
    @NotNull(message = "Пациент в договоре отсутствует")
    private Patient patient;

    @ManyToOne
    @JoinColumn(name = "type_contract_id", referencedColumnName = "id")
    @NotNull(message = "Тип договора отсутствует")
    private TypeContract typeContract;

}
