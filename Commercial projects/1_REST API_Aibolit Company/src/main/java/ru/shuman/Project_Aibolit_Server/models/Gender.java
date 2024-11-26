package ru.shuman.Project_Aibolit_Server.models;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import javax.validation.constraints.Size;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@ToString

@Entity
@Table(name = "gender")
public class Gender {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "name")
    @Size(max = 20, message = "Наименование гендерного признака должно быть не более 20 символов")
    private String name;

    @OneToMany(mappedBy = "gender")
    private List<Parent> parents;

    @OneToMany(mappedBy = "gender")
    private List<Patient> patients;

}
