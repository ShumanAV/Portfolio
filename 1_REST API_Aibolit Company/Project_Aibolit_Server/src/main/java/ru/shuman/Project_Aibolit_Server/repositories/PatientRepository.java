package ru.shuman.Project_Aibolit_Server.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.shuman.Project_Aibolit_Server.models.Patient;

import java.util.List;
import java.util.Optional;

@Repository
public interface PatientRepository extends JpaRepository<Patient, Integer> {
    Optional<Patient> findByPhone(String phone);
    Optional<Patient> findByEmail(String email);
    Optional<Patient> findByPolicy(String policy);
    Optional<Patient> findBySnils(String snils);
    Optional<Patient> findByInn(String inn);
    List<Patient> findByPublished(boolean published);
    List<Patient> findByFirstnameStartingWith(String firstname);
    List<Patient> findByLastnameStartingWith(String lastname);
    List<Patient> findByPhoneContaining(String phone);
}
