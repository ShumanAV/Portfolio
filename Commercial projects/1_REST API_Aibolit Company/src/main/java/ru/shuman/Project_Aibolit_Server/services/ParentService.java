package ru.shuman.Project_Aibolit_Server.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.shuman.Project_Aibolit_Server.models.Parent;
import ru.shuman.Project_Aibolit_Server.models.Patient;
import ru.shuman.Project_Aibolit_Server.repositories.ParentRepository;
import ru.shuman.Project_Aibolit_Server.util.GeneralMethods;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Optional;

import static ru.shuman.Project_Aibolit_Server.util.GeneralMethods.copyNonNullProperties;

@Service
@Transactional(readOnly = true)
public class ParentService {

    private final ParentRepository parentRepository;
    private final DocumentService documentService;
    private final AddressService addressService;
    private final TypeRelationshipWithPatientService typeRelationshipWithPatientService;
    private final EducationService educationService;
    private final TypeEmploymentService typeEmploymentService;
    private final BloodService bloodService;
    private final GenderService genderService;

    /*
    Внедрение зависимостей
     */
    @Autowired
    public ParentService(ParentRepository parentRepository, DocumentService documentService, AddressService addressService,
                         TypeRelationshipWithPatientService typeRelationshipWithPatientService, EducationService educationService,
                         TypeEmploymentService typeEmploymentService, BloodService bloodService, GenderService genderService) {
        this.parentRepository = parentRepository;
        this.documentService = documentService;
        this.addressService = addressService;
        this.typeRelationshipWithPatientService = typeRelationshipWithPatientService;
        this.educationService = educationService;
        this.typeEmploymentService = typeEmploymentService;
        this.bloodService = bloodService;
        this.genderService = genderService;
    }

    /*
    Метод ищет родителя пациента по id и возвращает его в обертке Optional
     */
    public Optional<Parent> findById(Integer parentId) {
        return parentRepository.findById(parentId);
    }

    /*
    Метод ищет родителя пациента по номеру телефона и возвращает его в обертке Optional
     */
    public Optional<Parent> findByPhone(String phone) {
        return parentRepository.findByPhone(phone);
    }

    /*
    Метод ищет родителя пациента по имэйлу и возвращает его в обертке Optional
     */
    public Optional<Parent> findByEmail(String email) {
        return parentRepository.findByEmail(email);
    }

    /*
    Метод ищет родителя пациента по номеру медицинского полиса и возвращает его в обертке Optional
     */
    public Optional<Parent> findByPolicy(String policy) {
        return parentRepository.findByPolicy(policy);
    }

    /*
    Метод ищет родителя пациента по номеру снилс и возвращает его в обертке Optional
     */
    public Optional<Parent> findBySnils(String snils) {
        return parentRepository.findBySnils(snils);
    }

    /*
    Метод ищет родителя пациента по ИНН и возвращает его в обертке Optional
     */
    public Optional<Parent> findByInn(String inn) {
        return parentRepository.findByInn(inn);
    }

    /*
    Метод сохраняет нового родителя пациента, добавляет дату и время создания и изменения, по цепочке сохраняем дочерние
    сущности такие как: Document - документ подтверждения личности, Address - адрес проживания,
    TypeRelationshipWithPatient - тип отношения с пациентом (мать, отец, бабаушка и т.д.),
    Education - тип образования, Blood - группа крови, TypeEmployment - тип занятости (работает, учится, на пенсии и т.д.),
    Gender - гендер.
    Для кэша устанавливает родителя пациента для данных сущностей.
    Далее родитель пациента сохраняется.
     */
    @Transactional
    public void create(Parent newParent) {

        //Записываем дату и время создания и изменения
        newParent.setCreatedAt(LocalDateTime.now());
        newParent.setUpdatedAt(LocalDateTime.now());

        //для кэша в документ родителя прописываем данного родителя и создаем родителя
        newParent.getDocument().setParent(newParent);
        documentService.create(newParent.getDocument());

        //для кэша в домашний адрес родителя прописываем данного родителя и создаем адрес
        newParent.getAddress().setParent(newParent);
        addressService.create(newParent.getAddress());

        //если у родителя выбран тип отношения с пациентом, т.е. он не null, то для кэша добавляем данного родителя в список
        // родителей у выбранного типа отношения с пациентом
        if (newParent.getTypeRelationshipWithPatient() != null) {
            typeRelationshipWithPatientService.addParentAtListForTypeRelationship(newParent, newParent.getTypeRelationshipWithPatient());
        }

        //если у родителя выбрано образование, т.е. оно не null, то для кэша добавляем данного родителя в список
        // родителей у выбранного образования
        if (newParent.getEducation() != null) {
            educationService.addParentAtListForEducation(newParent, newParent.getEducation());
        }

        //если у родителя выбрана группа крови, т.е. она не null, то для кэша добавляем данного родителя в список
        // родителей у выбранной группы крови
        if (newParent.getBlood() != null) {
            bloodService.addParentAtListForBlood(newParent, newParent.getBlood());
        }

        //если у родителя выбран тип занятости, т.е. он не null, то для кэша добавляем данного родителя в список
        // родителей у выбранного типа занятости
        if (newParent.getTypeEmployment() != null) {
            typeEmploymentService.addParentAtListForTypeEmployment(newParent, newParent.getTypeEmployment());
        }

        //если у родителя выбран гендер, т.е. он не null, то для кэша добавляем данного родителя в список
        // родителей у выбранного гендера
        if (newParent.getGender() != null) {
            genderService.addParentAtListForGender(newParent, newParent.getGender());
        }

        //сохраняем данного родителя
        parentRepository.save(newParent);
    }

    /*
    Метод сохраняет измененного родителя пациента, находим существующего родителя в БД, переносим в изменяемого родителя
    время создания, обновляем дату изменения, далее по цепочке обновляем все дочерние сущности такие как:
    Document - документ подтверждения личности, Address - адрес проживания,
    TypeRelationshipWithPatient - тип отношения с пациентом (мать, отец, бабаушка и т.д.),
    Education - тип образования, Blood - группа крови, TypeEmployment - тип занятости (рабоает, учится, на пенсии и т.д.),
    Gender - гендер.
    Также для кэша устанавливаем измененного родителя пациента для данных сущностей.
    Далее пациент сохраняется.
     */
    @Transactional
    public void update(Parent updatedParent) {

        //находим существующего родителя в БД по id
        Parent existingParent = parentRepository.findById(updatedParent.getId()).get();

        //копируем значения всех полей, кроме тех, которые не null, из измененного родителя в существующего
        copyNonNullProperties(updatedParent, existingParent);

        //обновляем дату и время изменения
        existingParent.setUpdatedAt(LocalDateTime.now());

        //для кэша, для указанного документа родителя устанавливаем данного родителя и апдейтим документ
        existingParent.getDocument().setParent(existingParent);
        documentService.update(existingParent.getDocument());

        //для кэша, для указанного адреса проживания родителя устанавливаем данного родителя и апдейтим адрес
        existingParent.getAddress().setParent(existingParent);
        addressService.update(existingParent.getAddress());

        //если у родителя выбран тип отношения с пациентом, т.е. он не null, то для кэша добавляем данного родителя в список
        // родителей у выбранного типа отношения с пациентом
        if (existingParent.getTypeRelationshipWithPatient() != null) {
            typeRelationshipWithPatientService.addParentAtListForTypeRelationship(existingParent, existingParent.getTypeRelationshipWithPatient());
        }

        //если у родителя выбрано образование, т.е. оно не null, то для кэша добавляем данного родителя в список
        // родителей у выбранного образования
        if (existingParent.getEducation() != null) {
            educationService.addParentAtListForEducation(existingParent, existingParent.getEducation());
        }

        //если у родителя выбрана группа крови, т.е. она не null, то для кэша добавляем данного родителя в список
        // родителей у выбранной группы крови
        if (existingParent.getBlood() != null) {
            bloodService.addParentAtListForBlood(existingParent, existingParent.getBlood());
        }

        //если у родителя выбран тип занятости, т.е. он не null, то для кэша добавляем данного родителя в список
        // родителей у выбранного типа занятости
        if (existingParent.getTypeEmployment() != null) {
            typeEmploymentService.addParentAtListForTypeEmployment(existingParent, existingParent.getTypeEmployment());
        }

        //если у родителя выбран гендер, т.е. он не null, то для кэша добавляем данного родителя в список
        // родителей у выбранного гендера
        if (existingParent.getGender() != null) {
            genderService.addParentAtListForGender(existingParent, existingParent.getGender());
        }
        //т.к. существующий родитель находится в персистенс контексте, сохранять его не нужно
    }

    /*
    Метод добавляет пациента в список пациентов для родителя указанного в пациенте, делается это для кэша
     */
    public void addPatientAtListForParent(Patient patient, Parent parent, int numberParent) {

        //Проверяем есть ли родители вообще
        if (patient.getParents() != null) {

            //Для начала достанем список пациентов для родителя, который на входе из БД, то что уже есть, и положим
            // в данного родителя
            if (parent.getId() != null) {
                Optional<Parent> existingParent = parentRepository.findById(parent.getId());
                if (existingParent.isPresent()) {
                    patient.getParents().get(numberParent).setPatients(existingParent.get().getPatients());
                }
            }

            //Если у данного родителя список пациентов null, это значит что на текущий момент у существующего родителя
            // нет еще пациентов (из БД), в этом случае создаем для него пустой список
            if (patient.getParents().get(numberParent).getPatients() == null) {
                patient.getParents().get(numberParent).setPatients(new ArrayList<>());

                //Если список не null, то мы идем по списку и если в этом списке уже есть такой пациент обновляем
                // его измененным
            } else {
                int index = 0;
                for (Patient currentPatient : patient.getParents().get(numberParent).getPatients()) {
                    if (patient.getId() == currentPatient.getId()) {
                        patient.getParents().get(numberParent).getPatients().set(index, patient);
                    }
                    index += 1;
                }
            }

            //Если в списке пациентов у данного родителя нет такого пациента, то добавляем его в список
            if (!patient.getParents().get(numberParent).getPatients().contains(patient)) {
                patient.getParents().get(numberParent).getPatients().add(patient);
            }
        }
    }
}
