package com.example.REST_Server_With_JWT.services;

import com.example.REST_Server_With_JWT.models.Message;
import com.example.REST_Server_With_JWT.models.Person;
import com.example.REST_Server_With_JWT.repositories.MessageRepository;
import com.example.REST_Server_With_JWT.repositories.PersonRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

// Сервис для работы с таблицей Message с БД
@Service
public class MessageService {

    private final int TEN = 10;

    private final PersonRepository personRepository;
    private final MessageRepository messageRepository;

    // внедрение зависимостей через конструктор
    @Autowired
    public MessageService(PersonRepository personRepository, MessageRepository messageRepository) {
        this.personRepository = personRepository;
        this.messageRepository = messageRepository;
    }

    // в данном методе найдем в БД человека, указанного в сообщении, и заполним его в сообщении, т.к. в сообщении присутствует только имя
    // также запишем сообщение в БД, метод возвращает пустой Лист с целью унификации кода
    public List<Message> save(Message message) {
        Optional<Person> person = personRepository.findByName(message.getName().getName());
        message.setName(person.get());

        messageRepository.save(message);
        return new ArrayList<>();
    }

    // данный метод возвращает 10 последних сообщений из БД в виде листа сообщений
    public List<Message> get10LastMessages() {
        List<Message> allMessages = messageRepository.findAll();
        List<Message> tenMessages = new ArrayList<>();

        if (allMessages.size() > 0) {
            for (int i = allMessages.size() - 1; i > allMessages.size() - TEN - 1; i--) {
                tenMessages.add(allMessages.get(i));
            }
        }

        return tenMessages;
    }

}
