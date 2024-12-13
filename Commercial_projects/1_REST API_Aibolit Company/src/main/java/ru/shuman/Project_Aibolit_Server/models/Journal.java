package ru.shuman.Project_Aibolit_Server.models;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@ToString

@Entity
@Table(name = "journal")
public class Journal {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @NotNull(message = "Диагноз отсутствует")
    @Column(name = "diagnosis")
    private String diagnosis;

    @NotNull(message = "Поле жалобы отсутствует")
    @Column(name = "complaint")
    private String complaint;

    @NotNull(message = "Поле анамнез отсутствует")
    @Column(name = "anamnesis")
    private String anamnesis;

    @NotNull(message = "Поле лечение отсутствует")
    @Column(name = "therapy")
    private String therapy;

    @NotNull(message = "Рекомендации отсутствуют")
    @Column(name = "recommendation")
    private String recommendation;

    @NotNull(message = "Поле published отсутствует")
    @Column(name = "published")
    private Boolean published;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    @OneToOne(mappedBy = "journal")
    private Calling calling;

}
