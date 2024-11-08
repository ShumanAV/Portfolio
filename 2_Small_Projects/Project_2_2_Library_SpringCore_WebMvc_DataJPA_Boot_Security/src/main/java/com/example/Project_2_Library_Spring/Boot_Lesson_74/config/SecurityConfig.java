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

// класс, где мы настраиваем Spring Security, здесь будем настраивать аутентификацию и авторизацию и все, что касается Spring Security, настраивается здесь,
// обязательно должны унаследовать WebSecurityConfigurerAdapter
//@EnableWebSecurity дает понять спрингу, что это конфигурационный класс
// далее Spring Security может за нас сделать проверку username и password, поэтому свой провайдер можно не реализовывать
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    // т.к. у нас стандартная аутентификация внедрим провайдера наш сервис
    // Spring с помощью сервиса сам получит человека, вызовет сервис проверит логин и пароль
    private final PersonDetailsService personDetailsService;

    @Autowired
    public SecurityConfig(PersonDetailsService personDetailsService) {
        this.personDetailsService = personDetailsService;
    }

    // конфигурируем сам Spring Security и авторизацию, в этот метод поступает http запрос
    @Override
    protected void  configure(HttpSecurity http) throws Exception {

        // таким образом мы конфигурируем Spring Security и авторизацию
        // далее начинается блок авторизации
        // вызов метода authorizeRequests() делает, что все запросы, которые приходят в приложение будут проходить авторизацию, которую здесь настроим,
        // далее .antMatchers("/admin") мы хотим, чтобы для url /admin у пользователя была роль .hasRole("ADMIN"), это авторизация на уровне адресов
        // здесь уже не надо ROLE_ писать в роли, вместо метода .hasRole() есть метод .hasAuthority(), который идентичен, но здесь нужно писать ROLE_
        // и здесь можно прописать действие а не роль, пока уберем эту строку, чтобы настроить еще одним методом через указание аннотации у методов
        // далее добавляем antMatchers(), чтобы видеть какие запросы приходят в приложение, и если неаутентифицированный пользователь перешел на данные url
        // "/auth/login" или "/error" мы его должны пускать на эти страницы для аутентификации, далее даем всем доступ на эти страницы permitAll()
        // далее .anyRequest().authenticated() мы должны указать что на все остальные страницы мы не пускаем неаутентифицированных пользователей,
        // т.е. любые запросы должны быть аутентифицированны - для настройки авторизации по ролям убираем эту строку
        // вместо нее вставляем .anyRequest().hasAnyRole("USER", "ADMIN") это о том, что обе этих роли имеют доступ ко всем остальным страницам, кроме
        // /admin, куда имеет только admin
        // этот блок настройки авторизации называется правилами авторизации и читается сверху вниз, т.е. сверху должны быть частные правила,
        // снизу общие правила, как if else в java, если поступил запрос на страницу /auth/login, то нужно дать доступ, если на /hello, то нет
        // далее после настройки блока авторизации, чтобы перейти к блоку настройке страницы логина ставим .and()
        // .and()
        // далее указываем formLogin().loginPage("/auth/login"), что у нас своя страница аутентификации, ее url,
        // далее указываем url loginProcessingUrl("/process_login"), на котором Spring Security будет ждать, что ему придут логин и пароль,
        // он логин посмотрит в БД с помощью нашего сервиса, сам сравнит пароли и сам проведет аутентификацию, метод обработки @PostMapping
        // в контроллере AuthController делать отдельно не нужно, это сделает Spring Security
        // далее укажем defaultSuccessUrl("/hello", true), на какую страницу отправлять пользователя по умолчанию при успешной
        // аутентификации, и вторым параметром true - в любом случае переводить на эту страницу, если указать не true, то он не всегда нас будет
        // переводить на страницу /hello
        // далее укажем failureUrl("/auth/login?error") на какую страницу переводить, в случае неправильного ввода логина и пароля с параметром error
        // для отображения ошибки на странице
        // .and()
        // далее идет блок настройки разлогирования - logout - это процесс удаления сессии пользователя и удаление у пользователя куки, сам процесс
        // разлогирования сделает за сам Spring Security
        // далее .logoutUrl("/logout") указывает на то, на каком url будет осуществленно разлогирование, т.е. если на него заходит пользователь, то
        // произойдет разлогирование
        // далее logoutSuccessUrl("/auth/login") указывает на то, на какую страницу перевести пользователя после разлогирования,
        // также делать Get или PostMapping для url разлогирования - /logout не нужно
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

    // настраиваем аутентификацию
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(personDetailsService)
                .passwordEncoder(getPasswordEncoder()); // в него передаем бин getPasswordEncoder(), в итоге при аутентификации будет использовано шифрование и уже зашифрованные пароли будут сравниваться
    }

    // В данном бине мы указываем алгоритм шифрования паролей, его Spring создаст, теперь мы должны его использовать при регистрации и аутентификации
    @Bean
    public PasswordEncoder getPasswordEncoder() {
        // Если мы используем такой подход без провайдера мы должны указать с помощью какого алгоритма мы шифруем наш пароль
        return new BCryptPasswordEncoder();
    }

}
