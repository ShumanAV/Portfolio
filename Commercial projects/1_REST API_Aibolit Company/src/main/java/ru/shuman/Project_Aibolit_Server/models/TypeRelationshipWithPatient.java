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
@Table(name = "type_relationship_with_patient")
public class TypeRelationshipWithPatient {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "name")
    @NotEmpty(message = "Наименование типа отношений с пациентом отсутствует или не заполнено")
    @Size(max = 100, message = "Наименование типа отношений с пациентом должно быть не более 100 символов")
    private String name;

    @OneToMany(mappedBy = "typeRelationshipWithPatient")
    private List<Parent> parents;


}
