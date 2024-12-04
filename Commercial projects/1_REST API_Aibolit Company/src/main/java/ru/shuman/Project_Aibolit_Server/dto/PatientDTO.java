package ru.shuman.Project_Aibolit_Server.dto;

import lombok.Getter;
import lombok.Setter;
import ru.shuman.Project_Aibolit_Server.models.Parent;

import javax.validation.constraints.Email;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

@Getter
@Setter
public class PatientDTO {

    private Integer id;

    @Size(max = 100, message = "Фамилия должна быть не более 100 символов")
    private String lastname;

    @Size(max = 100, message = "Имя должно быть не более 100 символов")
    private String firstname;

    @Size(max = 100, message = "Отчество должно быть не более 100 символов")
    private String patronymic;

    private String description;

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

    @Size(max = 100, message = "Инвалидность должна быть не более 100 символов")
    private String disability;

    @Size(max = 255, message = "Аллергия должна быть не более 255 символов")
    private String allergy;

    private Date birthday;

    private Boolean published;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;

    private PlaceStudyDTO placeStudy;

    private DocumentDTO document;

    private AddressDTO address;

    private BloodDTO blood;

    private GenderDTO gender;

    private List<ParentDTO> parents;

}
