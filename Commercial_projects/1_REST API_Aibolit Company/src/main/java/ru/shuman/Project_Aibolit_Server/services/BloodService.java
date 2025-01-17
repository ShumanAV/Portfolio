package ru.shuman.Project_Aibolit_Server.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.shuman.Project_Aibolit_Server.models.Blood;
import ru.shuman.Project_Aibolit_Server.models.Parent;
import ru.shuman.Project_Aibolit_Server.models.Patient;
import ru.shuman.Project_Aibolit_Server.repositories.BloodRepository;
import ru.shuman.Project_Aibolit_Server.util.GeneralMethods;

import java.util.List;
import java.util.Optional;

import static ru.shuman.Project_Aibolit_Server.util.GeneralMethods.addObjectOneInListForObjectTwo;

@Service
@Transactional(readOnly = true)
public class BloodService {

    private final BloodRepository bloodRepository;

    /*
    Внедрение зависимостей
     */
    @Autowired
    public BloodService(BloodRepository bloodRepository) {
        this.bloodRepository = bloodRepository;
    }

    /*
    Метод формирует список всех групп крови и возвращает его
    */
    public List<Blood> findAll() {
        return bloodRepository.findAll();
    }

    /*
    Метод находит группу крови по id и возвращает ее в обертке Optional
     */
    public Optional<Blood> findById(Integer bloodId) {
        return bloodRepository.findById(bloodId);
    }

    /*
    Метод находит группу крови по названию и возвращает ее в обертке Optional
     */
    public Optional<Blood> findByName(String name) {
        return bloodRepository.findByName(name);
    }

    /*
    Метод сохраняет новую группу крови в БД
     */
    @Transactional
    public void create(Blood newBlood) {
        bloodRepository.save(newBlood);
    }

    /*
    Метод изменяет существующую группу крови в БД
     */
    @Transactional
    public void update(Blood updatedBlood) {
        bloodRepository.save(updatedBlood);
    }

    /*
    Метод добавляет пациента в список пациентов для группы крови указанной у пациента, делается это для кэша
    */
    public void addPatientAtListForBlood(Patient patient, Blood blood) {
        addObjectOneInListForObjectTwo(patient, blood, this);
    }

    /*
    Метод добавляет родителя пациента в список родителей для группы крови указанной у родителя, делается это для кэша
     */
    public void addParentAtListForBlood(Parent parent, Blood blood) {
        addObjectOneInListForObjectTwo(parent, blood, this);
    }

}
