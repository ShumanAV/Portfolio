package com.example.Project_2_Library_Spring.Boot_Lesson_74.config;

import com.example.Project_2_Library_Spring.Boot_Lesson_74.services.PersonDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

/*
Класс, где мы настраиваем Spring Security
*/
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    /*
    Т.к. у нас стандартная аутентификация внедрим провайдера наш сервис.
    Spring с помощью сервиса сам получит человека, вызовет сервис проверит логин и пароль
    */
    private final PersonDetailsService personDetailsService;

    @Autowired
    public SecurityConfig(PersonDetailsService personDetailsService) {
        this.personDetailsService = personDetailsService;
    }

    // конфигурируем сам Spring Security и авторизацию, в этот метод поступает http запрос
    @Override
    protected void configure(HttpSecurity http) throws Exception {

        /*
        Конфигурируем Spring Security и авторизацию
         */
        http
                .authorizeRequests()
                .antMatchers("/admin").hasRole("ADMIN")
                .antMatchers("/auth/login", "/auth/registration", "/error").permitAll()
                .anyRequest().hasAnyRole("USER", "ADMIN")
                .and()
                .exceptionHandling().accessDeniedPage("/accessDenied")
//                .anyRequest().authenticated()   // для настройки авторизации по ролям убираем эту строку
                .and()
                .formLogin().loginPage("/auth/login")
                .loginProcessingUrl("/process_login")
                .defaultSuccessUrl("/index", true)
                .failureUrl("/auth/login?error")
                .and()
                .logout()
                .logoutUrl("/logout")
                .logoutSuccessUrl("/auth/login");
    }

    /*
    настраиваем аутентификацию
     */
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(personDetailsService)
                .passwordEncoder(getPasswordEncoder());
    }

    /*
    В данном бине указываем алгоритм шифрования паролей, его Spring создаст, теперь мы должны его использовать
    при регистрации и аутентификации
    Если мы используем такой подход без провайдера мы должны указать с помощью какого алгоритма мы шифруем наш пароль
     */
    @Bean
    public PasswordEncoder getPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }

}
