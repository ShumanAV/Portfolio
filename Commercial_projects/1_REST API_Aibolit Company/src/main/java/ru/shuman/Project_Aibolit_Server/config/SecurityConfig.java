package ru.shuman.Project_Aibolit_Server.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import ru.shuman.Project_Aibolit_Server.services.ProfileDetailsService;

@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    private final ProfileDetailsService profileDetailsService;
    private final JWTFilter jwtFilter;

    @Autowired
    public SecurityConfig(ProfileDetailsService profileDetailsService, JWTFilter jwtFilter) {
        this.profileDetailsService = profileDetailsService;
        this.jwtFilter = jwtFilter;
    }

    // Конфигурируем сам Спринг Секурити
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .csrf().disable()
                .authorizeRequests()
                .antMatchers(HttpMethod.GET, "/doctors").hasAnyRole("DIRECTOR", "ADMIN")
                .antMatchers(HttpMethod.GET, "/doctors/**").hasAnyRole("DIRECTOR", "ADMIN")
                .antMatchers(HttpMethod.PATCH, "/doctors/{id}").hasAnyRole("DIRECTOR", "ADMIN")
                .antMatchers("/admin", "/auth/login", "/auth/registration", "/error").permitAll()
                .anyRequest().hasAnyRole("USER", "ADMIN", "DIRECTOR", "DOCTOR")
                .and()
                .formLogin().loginPage("/auth/login")
                .loginProcessingUrl("/process_login")
                .and()
                .logout()
                .logoutUrl("/logout")
                .logoutSuccessUrl("/auth/login")
                .and()
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS);

        http.addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class);
    }

    // Настраиваем аутентификацию
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(profileDetailsService)
                .passwordEncoder(getPasswordEncoder());
    }

    // Создадим бин для шифрования/не шифрования паролей
    @Bean
    public PasswordEncoder getPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }

    // Создадим бин для создания AuthenticationManager для проверки логина и пароля
    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

}
