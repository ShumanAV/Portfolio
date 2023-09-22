package ru.shuman.Project_Aibolit_Server.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.shuman.Project_Aibolit_Server.models.User;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {
    Optional<User> findByPhone(String phone);
    Optional<User> findBySnils(String snils);
    Optional<User> findByInn(String inn);
    List<User> findByPublished(boolean published);
    List<User> findByShowInSchedule(boolean showInSchedule);
    List<User> findByPublishedAndShowInSchedule(boolean published, boolean showInSchedule);
}
