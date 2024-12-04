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
@Table(name = "type_employment")
public class TypeEmployment {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "name")
    @NotNull(message = "Наименование типа занятости родителя отсутствует")
    @NotEmpty(message = "Наименование типа занятости родителя не заполнено")
    @Size(max = 100, message = "Наименование типа занятости родителя должно быть не более 100 символов")
    private String name;

    @OneToMany(mappedBy = "typeEmployment")
    private List<Parent> parents;

}
