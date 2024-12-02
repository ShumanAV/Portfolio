package ru.shuman.Project_Aibolit_Server.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.shuman.Project_Aibolit_Server.models.Parent;
import ru.shuman.Project_Aibolit_Server.models.Patient;
import ru.shuman.Project_Aibolit_Server.repositories.ParentRepository;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Optional;

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
    Education - тип образования, Blood - группа крови, TypeEmployment - тип занятости (рабоает, учится, на пенсии и т.д.),
    Gender - гендер.
    Далее для кэша устанавливает родителя пациента для данных сущностей.
    Далее родитель пациента сохраняется.
     */
    @Transactional
    public void create(Parent parent) {

        parent.setCreatedAt(LocalDateTime.now());
        parent.setUpdatedAt(LocalDateTime.now());

        parent.getDocument().setParent(parent);
        documentService.create(parent.getDocument());

        parent.getAddress().setParent(parent);
        addressService.create(parent.getAddress());

        if (parent.getTypeRelationshipWithPatient() != null) {
            typeRelationshipWithPatientService.addParentAtListForTypeRelationship(parent, parent.getTypeRelationshipWithPatient());
        }

        if (parent.getEducation() != null) {
            educationService.addParentAtListForEducation(parent, parent.getEducation());
        }

        if (parent.getBlood() != null) {
            bloodService.addParentAtListForBlood(parent, parent.getBlood());
        }

        if (parent.getTypeEmployment() != null) {
            typeEmploymentService.addParentAtListForTypeEmployment(parent, parent.getTypeEmployment());
        }

        if (parent.getGender() != null) {
            genderService.addParentAtListForGender(parent, parent.getGender());
        }

        parentRepository.save(parent);
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
    public void update(Parent parent) {

        Parent existingParent = parentRepository.findById(parent.getId()).get();

        parent.setCreatedAt(existingParent.getCreatedAt());
        parent.setUpdatedAt(LocalDateTime.now());

        parent.getDocument().setParent(parent);
        documentService.update(parent.getDocument());

        parent.getAddress().setParent(parent);
        addressService.update(parent.getAddress());


        if (parent.getTypeRelationshipWithPatient() != null) {
            typeRelationshipWithPatientService.addParentAtListForTypeRelationship(parent, parent.getTypeRelationshipWithPatient());
        }

        if (parent.getEducation() != null) {
            educationService.addParentAtListForEducation(parent, parent.getEducation());
        }

        if (parent.getBlood() != null) {
            bloodService.addParentAtListForBlood(parent, parent.getBlood());
        }

        if (parent.getTypeEmployment() != null) {
            typeEmploymentService.addParentAtListForTypeEmployment(parent, parent.getTypeEmployment());
        }

        if (parent.getGender() != null) {
            genderService.addParentAtListForGender(parent, parent.getGender());
        }

        parentRepository.save(parent);
    }

    /*
    Метод добавляет пациента в лист родителя
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
