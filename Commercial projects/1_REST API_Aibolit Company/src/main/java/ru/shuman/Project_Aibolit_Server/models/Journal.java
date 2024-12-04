package ru.shuman.Project_Aibolit_Server.models;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@ToString

@Entity
@Table(name = "diary")
public class Journal {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "diagnosis")
    private String diagnosis;

    @Column(name = "complaint")
    private String complaint;

    @Column(name = "anamnesis")
    private String anamnesis;

    @Column(name = "therapy")
    private String therapy;

    @Column(name = "recommendation")
    private String recommendation;

    @Column(name = "published")
    private Boolean published;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    @OneToOne(mappedBy = "journal")
    private Calling calling;

}
