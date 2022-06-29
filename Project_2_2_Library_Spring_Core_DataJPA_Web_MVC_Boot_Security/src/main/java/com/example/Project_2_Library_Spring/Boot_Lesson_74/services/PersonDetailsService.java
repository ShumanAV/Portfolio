package com.example.Project_2_Library_Spring.Boot_Lesson_74.services;

import com.example.Project_2_Library_Spring.Boot_Lesson_74.models.Person;
import com.example.Project_2_Library_Spring.Boot_Lesson_74.repositories.PersonRepository;
import com.example.Project_2_Library_Spring.Boot_Lesson_74.security.PersonDetails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.Optional;

// этот сервис будет специальным, т.к. он предназначается для Spring Security, чтобы spring security это знал, что этот сервис загружает пользователя
// здесь мы должны реализовать интерфейс UserDetailsService
@Service
@Transactional(readOnly = true)
public class PersonDetailsService implements UserDetailsService {

    private final PersonRepository personRepository;

    @Autowired
    public PersonDetailsService(PersonRepository personRepository) {
        this.personRepository = personRepository;
    }

    // возвращает любой объект, который реализует интерфейс UserDetails
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<Person> person = personRepository.findByUsername(username);

        // если пользователь не найден, то выкинем исключение
        if (!person.isPresent()) {
            // это исключение будет поймано Spring Security, и сообщение будет показано на странице логина
            throw new UsernameNotFoundException("Пользователь с таким именем не найден!");
        }
        // если пользователь найден, то возвращаем его в обертке PersonDetails
        return new PersonDetails(person.get());
    }
}
