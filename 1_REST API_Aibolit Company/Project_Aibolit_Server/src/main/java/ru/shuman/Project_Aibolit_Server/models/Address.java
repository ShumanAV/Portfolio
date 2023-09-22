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
@Table(name = "address")
public class Address {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "city")
    @Size(max = 100, message = "Город должен быть не более 100 символов")
    private String city;

    @Column(name = "district")
    @Size(max = 100, message = "Область должна быть не более 100 символов")
    private String district;

    @Column(name = "village")
    @Size(max = 100, message = "Поселение должно быть не более 100 символов")
    private String village;

    @Column(name = "street")
    @Size(max = 100, message = "Улица должна быть не более 100 символов")
    private String street;

    @Column(name = "house")
    @Size(max = 20, message = "Номер дома должен быть не более 20 символов")
    private String house;

    @Column(name = "apartment")
    @Size(max = 20, message = "Номер квартиры должен быть не более 20 символов")
    private String apartment;

    @Column(name = "postcode")
    @Size(max = 6, message = "Почтовый индекс должен быть не более 6 символов")
    private String postcode;

    @OneToOne(mappedBy = "address")
    private Parent parent;

    @OneToOne(mappedBy = "address")
    private Patient patient;

    @ManyToOne
    @JoinColumn(name = "region_name", referencedColumnName = "name")
    private Region region;

}
