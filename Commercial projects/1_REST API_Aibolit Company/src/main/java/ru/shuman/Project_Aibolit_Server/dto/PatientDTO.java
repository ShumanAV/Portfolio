package ru.shuman.Project_Aibolit_Server.dto;

import lombok.Getter;
import lombok.Setter;
import ru.shuman.Project_Aibolit_Server.models.Parent;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

@Getter
@Setter
public class PatientDTO {

    private Integer id;

    @NotNull(message = "Фамилия пациента отсутствует")
    @Size(max = 100, message = "Фамилия должна быть не более 100 символов")
    private String lastname;

    @NotNull(message = "Имя пациента отсутствует")
    @NotEmpty(message = "Имя пациента не может быть пустым")
    @Size(max = 100, message = "Имя должно быть не более 100 символов")
    private String firstname;

    @NotNull(message = "Отчество пациента отсутствует")
    @Size(max = 100, message = "Отчество должно быть не более 100 символов")
    private String patronymic;

    @NotNull(message = "Описание пациента отсутствует")
    private String description;

    @NotNull(message = "Номер телефона пациента отсутствует")
    @Size(max = 30, message = "Номер телефона должен быть не более 30 символов")
    private String phone;

    @NotNull(message = "Электронная почта пациента отсутствует")
    @Size(max = 100, message = "Электронная почта должна быть не более 100 символов")
    @Email(message = "Электронная почта должна быть в формате ххх@xxx.xx")
    private String email;

    @NotNull(message = "Номер медицинского полиса пациента отсутствует")
    @Size(max = 50, message = "Полис должен быть не более 50 символов")
    private String policy;

    @NotNull(message = "СНИЛС пациента отсутствует")
    @Size(max = 100, message = "СНИЛС должен быть не более 100 символов")
    private String snils;

    @NotNull(message = "Медицинская страховая компания пациента отсутствует")
    @Size(max = 255, message = "Медицинская страховая компания должна быть не более 255 символов")
    private String medicalInsuranceCompany;

    @NotNull(message = "ИНН пациента отсутствует")
    @Size(max = 20, message = "ИНН должен быть не более 20 символов")
    private String inn;

    @NotNull(message = "Инвалидность пациента отсутствует")
    @Size(max = 100, message = "Инвалидность должна быть не более 100 символов")
    private String disability;

    @NotNull(message = "Описание аллергии пациента отсутствует")
    @Size(max = 255, message = "Описание аллергии должно быть не более 255 символов")
    private String allergy;

    @NotNull(message = "День рождения пациента отсутствует")
    private Date birthday;

    @NotNull(message = "Поле published пациента отсутствует")
    private Boolean published;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;

    @NotNull(message = "Место учебы пациента отсутствует")
    private PlaceStudyDTO placeStudy;

    @NotNull(message = "Документ пациента отсутствует")
    private DocumentDTO document;

    @NotNull(message = "Адрес пациента отсутствует")
    private AddressDTO address;

    @NotNull(message = "Группа крови пациента отсутствует")
    private BloodDTO blood;

    @NotNull(message = "Гендер пациента отсутствует")
    private GenderDTO gender;

    @NotNull(message = "Родители пациента отсутствуют")
    private List<ParentDTO> parents;

}
