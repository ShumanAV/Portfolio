package ru.shuman.Project_Aibolit_Server.models;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Getter
@Setter
@NoArgsConstructor
@ToString

@Entity
@Table(name = "place_study")
public class PlaceStudy {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "name")
    @NotNull(message = "Наименование места учебы пациента отсутствует")
    @NotEmpty(message = "Наименование места учебы пациента не заполнено")
    @Size(max = 100, message = "Наименование места учебы пациента должно быть не более 100 символов")
    private String name;

    @OneToOne(mappedBy = "placeStudy")
    private Patient patient;

}
