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
import java.util.Objects;

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
    @NotEmpty(message = "Наименование группы крови отсутствует или не заполнено")
    @Size(max = 50, message = "Наименование группы крови должно быть не более 50 символов")
    private String name;

    @OneToMany(mappedBy = "blood")
    private List<Parent> parents;

    @OneToMany(mappedBy = "blood")
    private List<Patient> patients;

    /*
    Данный метод нужен для тестирования
     */
    @Override
    public boolean equals(Object object) {
        if (this == object) return true;
        if (object == null || getClass() != object.getClass()) return false;

        Blood blood = (Blood) object;

        if (!Objects.equals(id, blood.id)) return false;
        if (!Objects.equals(name, blood.name)) return false;
        if (!Objects.equals(parents, blood.parents)) return false;
        return Objects.equals(patients, blood.patients);
    }

}
