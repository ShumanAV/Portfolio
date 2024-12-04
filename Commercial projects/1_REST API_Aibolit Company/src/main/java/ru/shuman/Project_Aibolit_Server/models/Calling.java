package ru.shuman.Project_Aibolit_Server.models;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;
import java.util.Date;

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
    private Date date_visit;

    @Column(name = "date_call")
    @NotNull(message = "Дата вызова отсутствует")
    private Date date_call;

    @Column(name = "time_start")
    @NotNull(message = "Время начала вызова отсутствует")
    @Size(max = 50, message = "Время начала должно быть не более 50 символов")
    private String time_start;

    @Column(name = "time_end")
    @NotNull(message = "Время окончания вызова отсутствует")
    @Size(max = 50, message = "Время окончания должно быть не более 50 символов")
    private String time_end;

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
