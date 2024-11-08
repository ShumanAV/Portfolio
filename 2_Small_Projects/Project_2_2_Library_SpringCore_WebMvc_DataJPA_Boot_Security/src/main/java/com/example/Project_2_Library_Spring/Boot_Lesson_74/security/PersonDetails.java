package com.example.Project_2_Library_Spring.Boot_Lesson_74.security;

import com.example.Project_2_Library_Spring.Boot_Lesson_74.models.Person;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.Collections;

// это класс обертка для сущности Person, этот класс предоставляет информацию о пользователе, он должен имплементировать интерфейс UserDetails,
// методы которого нужно реализовать, методы стандартные со стандартными сигнатурами, чтобы spring security корректно работал, поэтому используется
// класс обертка, в нашей сущности могут быть методы и поля любого названия, а чтобы работа с пользователями была стандартизирована сделан этот интерфейс
public class PersonDetails implements UserDetails {

    private final Person person;

    public PersonDetails(Person person) {
        this.person = person;
    }

    // данный метод нужен для авторизации, здесь будем получать роли, которые есть у пользователя из БД, и возвращать коллекцию тех прав, которые есть
    // у этого пользователя, благодаря этому, spring security будет это понимать
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        // Если бы мы делали авторизацию посредством списка действий, то мы бы создали List и наполняли бы его действиями
        // возвращаем коллекцию с одним элементом - тип роли человека
        return Collections.singletonList(new SimpleGrantedAuthority(person.getRole()));
    }

    // возвращает пароль человека
    @Override
    public String getPassword() {
        return this.person.getPassword();
    }

    // возвращает имя пользователя
    @Override
    public String getUsername() {
        return this.person.getUsername();
    }

    // то что этот аккаунт не просрочен, поэтому пока возвращаем true
    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    // то что этот аккаунт не заблокирован, поэтому пока возвращаем true
    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    // пароль не просрочен, пока возвращаем true
    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    // этот аккаунт включен, он работает, пока возвращаем true
    @Override
    public boolean isEnabled() {
        return true;
    }

    //добавим метод, который позволит обратиться к данным объекта Person, нужно, чтобы получать данные аутентифицированного пользователя
    public Person getPerson() {
        return this.person;
    }
}
