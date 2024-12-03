package ru.shuman.Project_Aibolit_Server.models;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@ToString

@Entity
@Table(name = "region")
public class Region {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @Column(name = "code")
    @Max(value = 9999, message = "Код региона должен быть не более 9 999")
    @Min(value = 0, message = "Код региона должен быть более 0")
    private Integer code;

    @Column(name = "name")
    @NotNull(message = "Наименование региона не должно быть пустым")
    @Size(max = 255, message = "Наименование региона должно быть не более 255 символов")
    private String name;

    @OneToMany(mappedBy = "region")
    private List<Address> addresses;

}
