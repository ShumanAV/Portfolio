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
@Table(name = "blood")
public class Blood {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "name")
    @Size(max = 30, message = "Наименование группы крови должно быть не более 30 символов")
    private String name;

    @OneToMany(mappedBy = "blood")
    private List<Parent> parents;

    @OneToMany(mappedBy = "blood")
    private List<Patient> patients;

}
