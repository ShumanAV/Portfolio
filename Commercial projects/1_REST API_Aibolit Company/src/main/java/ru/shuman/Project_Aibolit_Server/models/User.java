package ru.shuman.Project_Aibolit_Server.models;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@ToString

@Entity
@Table(name = "_user")
public class User {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "username")
    @Size(min = 2, max = 100, message = "Электронная почта должна быть не менее 2 и не более 100 символов")
    @Email(message = "Электронная почта должна быть в формате ххх@xxx.xx")
    private String username;

    @Column(name = "password")
    @Size(min = 6, max = 100, message = "Пароль должен быть не менее 6 и не более 100 символов")
    private String password;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    @OneToOne(mappedBy = "user")
    private Doctor doctor;

    @ManyToOne
    @JoinColumn(name = "role_id", referencedColumnName = "id")
    private Role role;

}
