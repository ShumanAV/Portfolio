package com.example.Project_2_Library_Spring.Boot_Lesson_74.security;

import com.example.Project_2_Library_Spring.Boot_Lesson_74.models.Person;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.Collections;

/*
Это класс обертка для сущности Person, этот класс предоставляет информацию о пользователе,
он должен имплементировать интерфейс UserDetails, методы которого нужно реализовать, методы стандартные
со стандартными сигнатурами, чтобы spring security корректно работал, поэтому используется класс обертка
 */
public class PersonDetails implements UserDetails {

    private final Person person;

    public PersonDetails(Person person) {
        this.person = person;
    }

    /*
    Данный метод нужен для авторизации, здесь будем получать роли, которые есть у пользователя из БД,
    и возвращать коллекцию тех прав, которые есть
     */
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        // Если бы мы делали авторизацию посредством списка действий, то мы бы создали List и наполняли бы его действиями
        // возвращаем коллекцию с одним элементом - тип роли человека
        return Collections.singletonList(new SimpleGrantedAuthority(person.getRole()));
    }

    /*
    Метод возвращает пароль человека
    */
    @Override
    public String getPassword() {
        return this.person.getPassword();
    }

    /*
    Метод возвращает имя пользователя
     */
    @Override
    public String getUsername() {
        return this.person.getUsername();
    }

    /*
     Метод возвращает то, что этот аккаунт не просрочен, поэтому пока возвращаем true
     */
    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    /*
    Метод возвращает то, что этот аккаунт не заблокирован, поэтому пока возвращаем true
     */
    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    /*
    Метод возвращает то, что пароль не просрочен, пока возвращаем true
     */
    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    /*
     Метод возвращает то, что этот аккаунт включен, он работает, пока возвращаем true
     */
    @Override
    public boolean isEnabled() {
        return true;
    }

    /*
    Добавим метод, который позволит обратиться к данным объекта Person, нужно, чтобы получать данные
     аутентифицированного пользователя
     */
    public Person getPerson() {
        return this.person;
    }
}
