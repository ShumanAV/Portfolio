package com.example.REST_Server_With_JWT.repositories;

import com.example.REST_Server_With_JWT.models.Message;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

// Репозиторий для работы с таблицей Message с БД
@Repository
public interface MessageRepository extends JpaRepository<Message, Integer> {
}
