package ru.shuman.Project_Aibolit_Server.models;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@ToString

@Entity
@Table(name = "parent")
public class Parent {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "lastname")
    @Size(max = 100, message = "Фамилия должна быть не более 100 символов")
    private String lastname;

    @Column(name = "firstname")
    @Size(max = 100, message = "Имя должно быть не более 100 символов")
    private String firstname;

    @Column(name = "patronymic")
    @Size(max = 100, message = "Отчество должно быть не более 100 символов")
    private String patronymic;

    @Column(name = "parent_now")
    private Boolean parentNow;

    @Column(name = "power_of_attorney")
    @Size(max = 100, message = "Документ должен быть не более 100 символов")
    private String powerOfAttorney;

    @Column(name = "phone")
    @Size(max = 30, message = "Номер телефона должен быть не более 30 символов")
    private String phone;

    @Column(name = "email")
    @Size(max = 100, message = "Электронная почта должна быть не более 100 символов")
    @Email(message = "Электронная почта должна быть в формате ххх@xxx.xx")
    private String email;

    @Column(name = "policy")
    @Size(max = 50, message = "Полис должен быть не более 50 символов")
    private String policy;

    @Column(name = "snils")
    @Size(max = 100, message = "СНИЛС должен быть не более 100 символов")
    private String snils;

    @Column(name = "medical_insurance_company")
    @Size(max = 255, message = "Медицинская страховая компания должна быть не более 255 символов")
    private String medicalInsuranceCompany;

    @Column(name = "inn")
    @Size(max = 20, message = "ИНН должен быть не более 20 символов")
    private String inn;

    @Column(name = "job")
    @Size(max = 255, message = "Место работы должно быть не более 255 символов")
    private String job;

    @Column(name = "birthday")
    private Date birthday;

    @Column(name = "published")
    private Boolean published;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    @OneToOne
    @JoinColumn(name = "document_id", referencedColumnName = "id")
    private Document document;

    @OneToOne
    @JoinColumn(name = "address_id", referencedColumnName = "id")
    private Address address;

    @ManyToOne
    @JoinColumn(name = "type_relationship_with_patient_id", referencedColumnName = "id")
    private TypeRelationshipWithPatient typeRelationshipWithPatient;

    @ManyToOne
    @JoinColumn(name = "education_id", referencedColumnName = "id")
    private Education education;

    @ManyToOne
    @JoinColumn(name = "blood_id", referencedColumnName = "id")
    private Blood blood;

    @ManyToOne
    @JoinColumn(name = "type_employment_id", referencedColumnName = "id")
    private TypeEmployment typeEmployment;

    @ManyToOne
    @JoinColumn(name = "gender_id", referencedColumnName = "id")
    private Gender gender;

    @ManyToMany
    @JoinTable(
            name = "patient_parent",
            joinColumns = @JoinColumn(name = "parent_id"),
            inverseJoinColumns = @JoinColumn(name = "patient_id")
    )
    private List<Patient> patients;
}
