package ru.shuman.Project_Aibolit_Server.config;

import com.auth0.jwt.exceptions.JWTVerificationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
import org.springframework.web.bind.annotation.ExceptionHandler;
import ru.shuman.Project_Aibolit_Server.services.ProfileDetailsService;
import ru.shuman.Project_Aibolit_Server.util.RESTAuthenticationEntryPoint;
import ru.shuman.Project_Aibolit_Server.util.errors.AuthenticationErrorResponse;

import javax.servlet.http.HttpServletResponse;

@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    private final ProfileDetailsService profileDetailsService;
//    private final RESTAuthenticationEntryPoint restAuthenticationEntryPoint;
    private final JWTFilter jwtFilter;

    @Autowired
    public SecurityConfig(ProfileDetailsService profileDetailsService, RESTAuthenticationEntryPoint restAuthenticationEntryPoint, JWTFilter jwtFilter) {
        this.profileDetailsService = profileDetailsService;
//        this.restAuthenticationEntryPoint = restAuthenticationEntryPoint;
        this.jwtFilter = jwtFilter;
    }

    // Конфигурируем сам Спринг Секурити, т.е. какая страница отвечает за вход какая за ошибки и т.д.,
    // а также в данном методе конфигурируем авторизацию
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .csrf().disable()         // отключение защиты csrf
                .authorizeRequests()
//                .antMatchers("/admin").hasRole("ADMIN")
                .antMatchers("/admin", "/auth/login", "/auth/registration", "/error").permitAll()
                .anyRequest().hasAnyRole("USER", "PHONE_ADMIN", "DIRECTOR", "DOCTOR")
                .and()
                .formLogin().loginPage("/auth/login")
                .loginProcessingUrl("/process_login")
//                .defaultSuccessUrl("/hello", true)
//                .failureUrl("/auth/login?error")
                .and()
                .logout()
                .logoutUrl("/logout")
//                .logoutSuccessUrl("/auth/login")
                .and()
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS);

        http.exceptionHandling()
                .authenticationEntryPoint(
                        (request, response, ex) -> {
                            response.sendError(
                                    HttpServletResponse.SC_UNAUTHORIZED,
                                    ex.getMessage()
                            );
//                            response.setContentType("application/json");
//                            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
//                            response.getOutputStream().println("{ \"error\": \"" + "Access is denied, the user is not authenticated. Possibly an invalid or expired JWT token." + "\" }");
                        }
                );

//        http.exceptionHandling()
//                .authenticationEntryPoint(restAuthenticationEntryPoint);

        http.addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class);
    }

    // Настраиваем аутентификацию
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(profileDetailsService)
                .passwordEncoder(getPasswordEncoder());
    }

    // Данный бин необходим для шифрования/не шифрования паролей, без шифрования NoOpPasswordEncoder.getInstance(),
    // с шифрованием new BCryptPasswordEncoder()
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

    // Метод обработчик исключения ProfileNotAuthenticatedException
    @ExceptionHandler
    private ResponseEntity<AuthenticationErrorResponse> handleExceptionProfileNotAuthenticated(JWTVerificationException e) {
        AuthenticationErrorResponse response = new AuthenticationErrorResponse(
                e.getMessage(),
                System.currentTimeMillis()
        );

        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

}
