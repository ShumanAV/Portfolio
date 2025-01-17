package ru.shuman.Project_Aibolit_Server.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.shuman.Project_Aibolit_Server.models.Education;
import ru.shuman.Project_Aibolit_Server.models.Parent;
import ru.shuman.Project_Aibolit_Server.repositories.EducationRepository;
import ru.shuman.Project_Aibolit_Server.util.GeneralMethods;

import java.util.List;
import java.util.Optional;

import static ru.shuman.Project_Aibolit_Server.util.GeneralMethods.addObjectOneInListForObjectTwo;

@Service
@Transactional(readOnly = true)
public class EducationService {

    private final EducationRepository educationRepository;

    /*
    Внедрение зависимостей
     */
    @Autowired
    public EducationService(EducationRepository educationRepository) {
        this.educationRepository = educationRepository;
    }

    /*
    Метод формирует и возвращает список всех типов образования
     */
    public List<Education> findAll() {
        return educationRepository.findAll();
    }

    /*
    Метод ищет тип образования по названию и возвращает его в обертке Optional
     */
    public Optional<Education> findByName(String name) {
        return educationRepository.findByName(name);
    }

    /*
    Метод ищет тип образования по id и возвращает его в обертке Optional
     */
    public Optional<Education> findById(Integer educationId) {
        return educationRepository.findById(educationId);
    }

    /*
    Метод сохраняет новый тип образования в БД
     */
    @Transactional
    public void create(Education newEducation) {
        educationRepository.save(newEducation);
    }

    /*
    Метод сохраняет измененный тип образования в БД
     */
    @Transactional
    public void update(Education updatedEducation) {
        educationRepository.save(updatedEducation);
    }

    /*
    Метод добавляет родителя пациента в список родителей для типа образования указанного у родителя, делается это для кэша
     */
    public void addParentAtListForEducation(Parent parent, Education education) {
        addObjectOneInListForObjectTwo(parent, education, this);
    }

}
