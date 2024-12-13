package ru.shuman.Project_Aibolit_Server.models;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@ToString

@Entity
@Table(name = "type_contract")
public class TypeContract {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "name")
    @NotEmpty(message = "Наименование типа контракта отсутствует или не заполнено")
    @Size(max = 255, message = "Наименование типа контракта должно быть не более 255 символов")
    private String name;

    @Column(name = "cost")
    @NotNull(message = "Стоимость типа контракта отсутствует")
    @Max(value = 999999999, message = "Стоимость должна быть не более 999 999 999")
    @Min(value = 0, message = "Стоимость должна быть более 0")
    private Integer cost;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    @OneToMany(mappedBy = "typeContract")
    private List<Contract> contracts;
}
