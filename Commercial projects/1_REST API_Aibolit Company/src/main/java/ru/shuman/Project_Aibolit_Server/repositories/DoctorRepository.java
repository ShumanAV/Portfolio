package ru.shuman.Project_Aibolit_Server.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.shuman.Project_Aibolit_Server.models.Doctor;

import java.util.List;
import java.util.Optional;

@Repository
public interface DoctorRepository extends JpaRepository<Doctor, Integer> {
    Optional<Doctor> findByPhone(String phone);
    Optional<Doctor> findBySnils(String snils);
    Optional<Doctor> findByInn(String inn);
    List<Doctor> findByPublished(boolean published);
    List<Doctor> findByShowInSchedule(boolean showInSchedule);
    List<Doctor> findByPublishedAndShowInSchedule(boolean published, boolean showInSchedule);
    Optional<Doctor> findByProfileUsername(String username);
}
