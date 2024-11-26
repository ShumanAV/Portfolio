package ru.shuman.Project_Aibolit_Server.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
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
@Table(name = "patient")
public class Patient {

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

    @Column(name = "description")
    private String description;

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

    @Column(name = "disability")
    @Size(max = 100, message = "Инвалидность должна быть не более 100 символов")
    private String disability;

    @Column(name = "allergy")
    @Size(max = 255, message = "Аллергия должна быть не более 255 символов")
    private String allergy;

    @Column(name = "birthday")
    private Date birthday;

    @Column(name = "published")
    private boolean published;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    @OneToOne
    @JoinColumn(name = "place_study_id", referencedColumnName = "id")
    private PlaceStudy placeStudy;

    @OneToOne
    @JoinColumn(name = "document_id", referencedColumnName = "id")
    private Document document;

    @OneToOne
    @JoinColumn(name = "address_id", referencedColumnName = "id")
    private Address address;

    @ManyToOne()
    @JoinColumn(name = "blood_id", referencedColumnName = "id")
    private Blood blood;

    @ManyToOne()
    @JoinColumn(name = "gender_id", referencedColumnName = "id")
    private Gender gender;

    @ManyToMany(mappedBy = "patients")
    private List<Parent> parents;

    @OneToMany(mappedBy = "patient")
    private List<Contract> contracts;

    @OneToMany(mappedBy = "patient")
    private List<Calling> callings;

}
