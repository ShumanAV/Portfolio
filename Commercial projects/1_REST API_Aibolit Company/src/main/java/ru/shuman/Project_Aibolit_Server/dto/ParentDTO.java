package ru.shuman.Project_Aibolit_Server.dto;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.Email;
import javax.validation.constraints.Size;
import java.util.Date;

@Getter
@Setter
public class ParentDTO {

    private Integer id;

    @Size(max = 100, message = "Фамилия должна быть не более 100 символов")
    private String lastname;

    @Size(max = 100, message = "Имя должно быть не более 100 символов")
    private String firstname;

    @Size(max = 100, message = "Отчество должно быть не более 100 символов")
    private String patronymic;

    private Boolean parentNow;

    @Size(max = 100, message = "Документ должен быть не более 100 символов")
    private String powerOfAttorney;

    @Size(max = 30, message = "Номер телефона должен быть не более 30 символов")
    private String phone;

    @Size(max = 100, message = "Электронная почта должна быть не более 100 символов")
    @Email(message = "Электронная почта должна быть в формате ххх@xxx.xx")
    private String email;

    @Size(max = 50, message = "Полис должен быть не более 50 символов")
    private String policy;

    @Size(max = 100, message = "СНИЛС должен быть не более 100 символов")
    private String snils;

    @Size(max = 255, message = "Медицинская страховая компания должна быть не более 255 символов")
    private String medicalInsuranceCompany;

    @Size(max = 20, message = "ИНН должен быть не более 20 символов")
    private String inn;

    @Size(max = 255, message = "Место работы должно быть не более 255 символов")
    private String job;

    private Date birthday;

    private Boolean published;

    private DocumentDTO document;

    private AddressDTO address;

    private TypeRelationshipWithPatientDTO typeRelationshipWithPatient;

    private EducationDTO education;

    private BloodDTO blood;

    private TypeEmploymentDTO typeEmployment;

    private GenderDTO gender;

}
