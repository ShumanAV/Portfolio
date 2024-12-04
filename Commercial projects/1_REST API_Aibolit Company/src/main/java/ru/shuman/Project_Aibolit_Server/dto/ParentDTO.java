package ru.shuman.Project_Aibolit_Server.dto;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;
import java.util.Date;

@Getter
@Setter
public class ParentDTO {

    private Integer id;

    @NotNull(message = "Фамилия отсутствует")
    @Size(max = 100, message = "Фамилия должна быть не более 100 символов")
    private String lastname;

    @NotNull(message = "Имя отсутствует")
    @NotEmpty(message = "Имя не заполнено")
    @Size(max = 100, message = "Имя должно быть не более 100 символов")
    private String firstname;

    @NotNull(message = "Отчество отсутствует")
    @Size(max = 100, message = "Отчество должно быть не более 100 символов")
    private String patronymic;

    @NotNull(message = "Поле кто сейчас родитель отсутствует")
    private Boolean parentNow;

    @NotNull(message = "Доверенность отсутствует")
    @Size(max = 100, message = "Доверенность должна быть не более 100 символов")
    private String powerOfAttorney;

    @NotNull(message = "Номер телефона отсутствует")
    @Size(max = 30, message = "Номер телефона должен быть не более 30 символов")
    private String phone;

    @NotNull(message = "Электронная почта отсутствует")
    @Size(max = 100, message = "Электронная почта должна быть не более 100 символов")
    @Email(message = "Электронная почта должна быть в формате ххх@xxx.xx")
    private String email;

    @NotNull(message = "Медицинский полис отсутствует")
    @Size(max = 50, message = "Медицинский полис должен быть не более 50 символов")
    private String policy;

    @NotNull(message = "СНИЛС отсутствует")
    @Size(max = 100, message = "СНИЛС должен быть не более 100 символов")
    private String snils;

    @NotNull(message = "Медицинская страховая компания отсутствует")
    @Size(max = 255, message = "Медицинская страховая компания должна быть не более 255 символов")
    private String medicalInsuranceCompany;

    @NotNull(message = "ИНН отсутствует")
    @Size(max = 20, message = "ИНН должен быть не более 20 символов")
    private String inn;

    @NotNull(message = "Место работы отсутствует")
    @Size(max = 255, message = "Место работы должно быть не более 255 символов")
    private String job;

    @NotNull(message = "День рождения отсутствует")
    private Date birthday;

    @NotNull(message = "Поле published отсутствует")
    private Boolean published;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;

    @NotNull(message = "Документ отсутствует")
    private DocumentDTO document;

    @NotNull(message = "Адрес отсутствует")
    private AddressDTO address;

    @NotNull(message = "Тип отношения родителя с пациентом отсутствует")
    private TypeRelationshipWithPatientDTO typeRelationshipWithPatient;

    @NotNull(message = "Образование родителя отсутствует")
    private EducationDTO education;

    @NotNull(message = "Группа крови родителя отсутствует")
    private BloodDTO blood;

    @NotNull(message = "Тип занятости отсутствует")
    private TypeEmploymentDTO typeEmployment;

    @NotNull(message = "Гендерный признак отсутствует")
    private GenderDTO gender;

}
