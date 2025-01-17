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
@Table(name = "role")
public class Role {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "name")
    @NotEmpty(message = "Наименование роли на английском языке отсутствует или не заполнено")
    @Size(max = 50, message = "Наименование роли на английском языке должно быть не более 50 символов")
    private String name;

    @Column(name = "runame")
    @NotNull(message = "Наименование роли на русском языке отсутствует")
    @Size(max = 50, message = "Наименование роли на русском языке должно быть не более 50 символов")
    private String runame;

    @OneToMany(mappedBy = "role")
    private List<Profile> profiles;

    @Override
    public boolean equals(Object object) {
        if (this == object) return true;
        if (object == null || getClass() != object.getClass()) return false;

        Role role = (Role) object;

        if (!Objects.equals(name, role.name)) return false;
        return Objects.equals(runame, role.runame);
    }
}
