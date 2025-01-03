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
@Table(name = "price")
public class Price {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "name")
    @NotEmpty(message = "Наименование услуги отсутствует или не заполнено")
    @Size(max = 255, message = "Наименование услуги должно быть не более 255 символов")
    private String name;

    @Column(name = "cost")
    @NotNull(message = "Стоимость услуги отсутствует")
    @Max(value = 999999999, message = "Стоимость услуги должна быть не более 999 999 999")
    @Min(value = 0, message = "Стоимость услуги должна быть более 0")
    private Integer cost;

    @Column(name = "published")
    @NotNull(message = "Поле published отсутствует")
    private Boolean published;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    @OneToMany(mappedBy = "price")
    private List<Calling> callings;

}
