package ru.shuman.Project_Aibolit_Server.models;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

@Getter
@Setter
@NoArgsConstructor
@ToString

@Entity
@Table(name = "specialization")
public class Specialization {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "name")
    @NotNull(message = "Наименование специализации отсутствует")
    @NotEmpty(message = "Наименование специализации не заполнено")
    @Size(max = 100, message = "Наименование специализации должно быть не более 100 символов")
    private String name;

    @Column(name = "description")
    @NotNull(message = "Описание специализации отсутствует")
    private String description;

    @Column(name = "published")
    @NotNull(message = "Поле published отсутствует")
    private Boolean published;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    @OneToMany(mappedBy = "specialization")
    private List<Doctor> doctors;

    @Override
    public boolean equals(Object object) {
        if (this == object) return true;
        if (object == null || getClass() != object.getClass()) return false;

        Specialization that = (Specialization) object;

        return Objects.equals(name, that.name);
    }
}
