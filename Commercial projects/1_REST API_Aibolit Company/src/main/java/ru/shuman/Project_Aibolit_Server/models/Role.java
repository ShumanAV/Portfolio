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
@Table(name = "role")
public class Role {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "name")
    @NotNull(message = "Наименование роли на английском языке отсутствует")
    @NotEmpty(message = "Наименование роли на английском языке не заполнено")
    @Size(max = 50, message = "Наименование роли на английском языке должно быть не более 50 символов")
    private String name;

    @Column(name = "runame")
    @NotNull(message = "Наименование роли на русском языке отсутствует")
    @Size(max = 50, message = "Наименование роли на русском языке должно быть не более 50 символов")
    private String runame;

    @OneToMany(mappedBy = "role")
    private List<Profile> profiles;

}
