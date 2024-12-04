package ru.shuman.Project_Aibolit_Server.models;

import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Getter
@Setter
@NoArgsConstructor
@EqualsAndHashCode
@ToString

@Entity
@Table(name = "address")
public class Address {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "city")
    @NotNull(message = "Город отсутствует")
    @Size(max = 100, message = "Город должен быть не более 100 символов")
    private String city;

    @Column(name = "district")
    @NotNull(message = "Область отсутствует")
    @Size(max = 100, message = "Область должна быть не более 100 символов")
    private String district;

    @Column(name = "village")
    @NotNull(message = "Поселение отсутствует")
    @Size(max = 100, message = "Поселение должно быть не более 100 символов")
    private String village;

    @Column(name = "street")
    @NotNull(message = "Улица отсутствует")
    @Size(max = 100, message = "Улица должна быть не более 100 символов")
    private String street;

    @Column(name = "house")
    @NotNull(message = "Номер дома отсутствует")
    @Size(max = 20, message = "Номер дома должен быть не более 20 символов")
    private String house;

    @Column(name = "apartment")
    @NotNull(message = "Номер квартиры отсутствует")
    @Size(max = 20, message = "Номер квартиры должен быть не более 20 символов")
    private String apartment;

    @Column(name = "postcode")
    @NotNull(message = "Почтовый индекс отсутствует")
    @Size(max = 6, message = "Почтовый индекс должен быть не более 6 символов")
    private String postcode;

    @OneToOne(mappedBy = "address")
    private Parent parent;

    @OneToOne(mappedBy = "address")
    private Patient patient;

    @ManyToOne
    @JoinColumn(name = "region_id", referencedColumnName = "id")
    @NotNull(message = "Регион отсутствует")
    private Region region;

}
