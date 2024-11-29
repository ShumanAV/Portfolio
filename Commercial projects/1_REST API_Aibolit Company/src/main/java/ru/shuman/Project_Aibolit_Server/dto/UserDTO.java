package ru.shuman.Project_Aibolit_Server.dto;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;

@Getter
@Setter
public class UserDTO {

    private Integer id;

    @NotEmpty(message = "Поле имя пользователя не должно быть пустым!")
    @Size(min = 2, max = 100, message = "Поле имя пользователя должно быть не менее 2 и не более 100 символов")
    @Email(message = "Поле имя пользователя должно быть в формате емэйла ххх@xxx.xx")
    private String username;

    @Size(min = 6, max = 100, message = "Пароль должен быть не менее 6 и не более 100 символов")
    private String password;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;

    private RoleDTO role;

}
