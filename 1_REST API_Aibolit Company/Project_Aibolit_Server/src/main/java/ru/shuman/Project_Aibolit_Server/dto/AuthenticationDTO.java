package ru.shuman.Project_Aibolit_Server.dto;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

@Getter
@Setter
public class AuthenticationDTO {

//    @NotEmpty(message = "Поле имя пользователя не должно быть пустым!")
//    @Size(max = 100, message = "Поле имя пользователя должно быть не более 100 символов")
//    @Email(message = "Поле имя пользователя должно быть в формате емэйла ххх@xxx.xx")
    private String username;

//    @Size(max = 100, message = "Пароль должен быть не более 100 символов")
    private String password;

}

