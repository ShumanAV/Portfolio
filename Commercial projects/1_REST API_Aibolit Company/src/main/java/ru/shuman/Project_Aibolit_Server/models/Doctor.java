package ru.shuman.Project_Aibolit_Server.models;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@ToString

@Entity
@Table(name = "doctor")
public class Doctor {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "lastname")
    @NotNull(message = "Фамилия отсутствует")
    @Size(max = 100, message = "Фамилия должна быть не более 100 символов")
    private String lastname;

    @Column(name = "firstname")
    @NotNull(message = "Имя отсутствует")
    @NotEmpty(message = "Имя не может быть пустым")
    @Size(max = 100, message = "Имя должно быть не более 100 символов")
    private String firstname;

    @Column(name = "patronymic")
    @NotNull(message = "Отчество отсутствует")
    @Size(max = 100, message = "Отчество должно быть не более 100 символов")
    private String patronymic;

    @Column(name = "phone")
    @NotNull(message = "Номер телефона отсутствует")
    @Size(max = 30, message = "Номер телефона должен быть не более 30 символов")
    private String phone;

    @Column(name = "snils")
    @NotNull(message = "СНИЛС отсутствует")
    @Size(max = 100, message = "СНИЛС должен быть не более 100 символов")
    private String snils;

    @Column(name = "inn")
    @NotNull(message = "ИНН отсутствует")
    @Size(max = 20, message = "ИНН должен быть не более 20 символов")
    private String inn;

    @Column(name = "description")
    @NotNull(message = "Описание врача отсутствует")
    private String description;

    @Column(name = "birthday")
    @NotNull(message = "День рождения отсутствует")
    private LocalDate birthday;

    @Column(name = "access_to_system")
    @NotNull(message = "Поле доступа к системе отсутствует")
    private Boolean accessToSystem;

    @Column(name = "show_in_schedule")
    @NotNull(message = "Поле отображения в расписании отсутствует")
    private Boolean showInSchedule;

    @Column(name = "published")
    @NotNull(message = "Поле published отсутствует")
    private Boolean published;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    @ManyToOne
    @JoinColumn(name = "specialization_id", referencedColumnName = "id")
    @NotNull(message = "Специализация отсутствует")
    private Specialization specialization;

    @OneToOne()
    @JoinColumn(name = "profile_id", referencedColumnName = "id")
    @NotNull(message = "Профиль отсутствует")
    private Profile profile;

    @OneToMany(mappedBy = "doctor")
    private List<Contract> contracts;

    @OneToMany(mappedBy = "doctor")
    private List<Calling> callings;


}
