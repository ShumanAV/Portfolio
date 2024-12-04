package ru.shuman.Project_Aibolit_Server.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@ToString

@Entity
@Table(name = "patient")
public class Patient {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "lastname")
    @NotNull(message = "Фамилия пациента отсутствует")
    @Size(max = 100, message = "Фамилия должна быть не более 100 символов")
    private String lastname;

    @Column(name = "firstname")
    @NotNull(message = "Имя пациента отсутствует")
    @NotEmpty(message = "Имя пациента не может быть пустым")
    @Size(max = 100, message = "Имя должно быть не более 100 символов")
    private String firstname;

    @Column(name = "patronymic")
    @NotNull(message = "Отчество пациента отсутствует")
    @Size(max = 100, message = "Отчество должно быть не более 100 символов")
    private String patronymic;

    @Column(name = "description")
    @NotNull(message = "Описание пациента отсутствует")
    private String description;

    @Column(name = "phone")
    @NotNull(message = "Номер телефона пациента отсутствует")
    @Size(max = 30, message = "Номер телефона должен быть не более 30 символов")
    private String phone;

    @Column(name = "email")
    @NotNull(message = "Электронная почта пациента отсутствует")
    @Size(max = 100, message = "Электронная почта должна быть не более 100 символов")
    @Email(message = "Электронная почта должна быть в формате ххх@xxx.xx")
    private String email;

    @Column(name = "policy")
    @NotNull(message = "Номер медицинского полиса пациента отсутствует")
    @Size(max = 50, message = "Номер медицинского полиса должен быть не более 50 символов")
    private String policy;

    @Column(name = "snils")
    @NotNull(message = "СНИЛС пациента отсутствует")
    @Size(max = 100, message = "СНИЛС должен быть не более 100 символов")
    private String snils;

    @Column(name = "medical_insurance_company")
    @NotNull(message = "Медицинская страховая компания пациента отсутствует")
    @Size(max = 255, message = "Медицинская страховая компания должна быть не более 255 символов")
    private String medicalInsuranceCompany;

    @Column(name = "inn")
    @NotNull(message = "ИНН пациента отсутствует")
    @Size(max = 20, message = "ИНН должен быть не более 20 символов")
    private String inn;

    @Column(name = "disability")
    @NotNull(message = "Инвалидность пациента отсутствует")
    @Size(max = 100, message = "Инвалидность должна быть не более 100 символов")
    private String disability;

    @Column(name = "allergy")
    @NotNull(message = "Описание аллергии пациента отсутствует")
    @Size(max = 255, message = "Описание аллергии должно быть не более 255 символов")
    private String allergy;

    @Column(name = "birthday")
    @NotNull(message = "День рождения пациента отсутствует")
    private Date birthday;

    @Column(name = "published")
    @NotNull(message = "Поле published пациента отсутствует")
    private Boolean published;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    @OneToOne
    @JoinColumn(name = "place_study_id", referencedColumnName = "id")
    @NotNull(message = "Место учебы пациента отсутствует")
    private PlaceStudy placeStudy;

    @OneToOne
    @JoinColumn(name = "document_id", referencedColumnName = "id")
    @NotNull(message = "Документ пациента отсутствует")
    private Document document;

    @OneToOne
    @JoinColumn(name = "address_id", referencedColumnName = "id")
    @NotNull(message = "Адрес пациента отсутствует")
    private Address address;

    @ManyToOne()
    @JoinColumn(name = "blood_id", referencedColumnName = "id")
    @NotNull(message = "Группа крови пациента отсутствует")
    private Blood blood;

    @ManyToOne()
    @JoinColumn(name = "gender_id", referencedColumnName = "id")
    @NotNull(message = "Гендер пациента отсутствует")
    private Gender gender;

    @ManyToMany(mappedBy = "patients")
    @NotNull(message = "Родители пациента отсутствуют")
    private List<Parent> parents;

    @OneToMany(mappedBy = "patient")
    private List<Contract> contracts;

    @OneToMany(mappedBy = "patient")
    private List<Calling> callings;

}
