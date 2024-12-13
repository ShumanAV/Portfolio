package ru.shuman.Project_Aibolit_Server.models;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@ToString

@Entity
@Table(name = "calling")
public class Calling {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "date_visit")
    @NotNull(message = "Дата посещения отсутствует")
    @Size(max = 30, message = "Дата посещения врача должна быть не более 30 символов")
    private String dateVisit;

    @Column(name = "date_call")
    @NotNull(message = "Дата вызова отсутствует")
    @Size(max = 30, message = "Дата вызова врача должна быть не более 30 символов")
    private String dateCall;

    @Column(name = "time_start")
    @NotNull(message = "Время начала вызова отсутствует")
    @Size(max = 50, message = "Время начала должно быть не более 50 символов")
    private String timeStart;

    @Column(name = "time_end")
    @NotNull(message = "Время окончания вызова отсутствует")
    @Size(max = 50, message = "Время окончания должно быть не более 50 символов")
    private String timeEnd;

    @Column(name = "description")
    @NotNull(message = "Описание вызова отсутствует")
    private String description;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    @ManyToOne
    @JoinColumn(name = "doctor_id", referencedColumnName = "id")
    @NotNull(message = "Поле врач отсутствует")
    private Doctor doctor;

    @OneToOne
    @JoinColumn(name = "journal_id", referencedColumnName = "id")
    @NotNull(message = "Карточка вызова отсутствует")
    private Journal journal;

    @ManyToOne
    @JoinColumn(name = "price_id", referencedColumnName = "id")
    @NotNull(message = "Прайс отсутствует")
    private Price price;

    @ManyToOne
    @JoinColumn(name = "patient_id", referencedColumnName = "id")
    @NotNull(message = "Пациент отсутствует")
    private Patient patient;

}
