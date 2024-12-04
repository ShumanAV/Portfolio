package ru.shuman.Project_Aibolit_Server.models;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
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
    @NotNull(message = "Наименование группы крови отсутствует")
    @NotEmpty(message = "Наименование группы крови не заполнено")
    @Size(max = 50, message = "Наименование группы крови должно быть не более 50 символов")
    private String name;

    @OneToMany(mappedBy = "blood")
    private List<Parent> parents;

    @OneToMany(mappedBy = "blood")
    private List<Patient> patients;

}
