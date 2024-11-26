package ru.shuman.Project_Aibolit_Server.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@ToString

@Entity
@Table(name = "diary")
public class Diary {

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
    private boolean published;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    @OneToOne(mappedBy = "diary")
    private Calling calling;

}
