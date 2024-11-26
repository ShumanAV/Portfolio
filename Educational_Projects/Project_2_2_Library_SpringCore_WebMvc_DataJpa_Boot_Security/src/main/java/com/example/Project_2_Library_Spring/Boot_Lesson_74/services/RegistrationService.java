package com.example.Project_2_Library_Spring.Boot_Lesson_74.services;

import com.example.Project_2_Library_Spring.Boot_Lesson_74.models.Person;
import com.example.Project_2_Library_Spring.Boot_Lesson_74.repositories.PersonRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
public class RegistrationService {

    private final PasswordEncoder passwordEncoder;
    private final PersonRepository personRepository;

    /*
    Внедрение зависимостей
     */
    @Autowired
    public RegistrationService(PasswordEncoder passwordEncoder, PersonRepository personRepository) {
        this.passwordEncoder = passwordEncoder;
        this.personRepository = personRepository;
    }

    /*
    Метод регистрации новых пользователей
     */
    @Transactional
    public void register(Person person) {
        // зашифруем пароль через passwordEncoder метод .encode(person.getPassword()) и обновим на уже
        // зашифрованный пароль у человека person
        String encodedPassword = passwordEncoder.encode(person.getPassword());
        person.setPassword(encodedPassword);

        // установим всем по умолчанию пользователям роль user, роль для спринга всегда должны начинать с ROLE_,
        // тогда он будет понимать, что это роль
        person.setRole("ROLE_USER");
        personRepository.save(person);
    }
}
