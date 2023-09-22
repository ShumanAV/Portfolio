package ru.shuman.Project_Aibolit_Server.models;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.annotations.Cascade;

import javax.persistence.*;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;
import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
@ToString

@Entity
@Table(name = "calling")
public class Calling {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "date_visit")
    private Date date_visit;

    @Column(name = "date_call")
    private Date date_call;

    @Column(name = "time_start")
    @Size(max = 50, message = "Время начала должно быть не более 50 символов")
    private String time_start;

    @Column(name = "time_end")
    @Size(max = 50, message = "Время окончания должно быть не более 50 символов")
    private String time_end;

    @Column(name = "description")
    private String description;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    @ManyToOne
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private User user;

    @OneToOne
    @JoinColumn(name = "diary_id", referencedColumnName = "id")
    private Diary diary;

    @ManyToOne
    @JoinColumn(name = "price_id", referencedColumnName = "id")
    private Price price;

    @ManyToOne
    @JoinColumn(name = "patient_id", referencedColumnName = "id")
    private Patient patient;

}
