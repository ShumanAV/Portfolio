package ru.shuman.Project_Aibolit_Server.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.shuman.Project_Aibolit_Server.models.Calling;
import ru.shuman.Project_Aibolit_Server.models.Contract;
import ru.shuman.Project_Aibolit_Server.models.Parent;
import ru.shuman.Project_Aibolit_Server.models.Patient;
import ru.shuman.Project_Aibolit_Server.repositories.PatientRepository;
import ru.shuman.Project_Aibolit_Server.util.GeneralMethods;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static ru.shuman.Project_Aibolit_Server.util.GeneralMethods.addObjectOneInListForObjectTwo;
import static ru.shuman.Project_Aibolit_Server.util.GeneralMethods.copyNonNullProperties;

@Service
@Transactional(readOnly = true)
public class PatientService {

    private final PatientRepository patientRepository;
    private final PlaceStudyService placeStudyService;
    private final ParentService parentService;
    private final DocumentService documentService;
    private final AddressService addressService;
    private final BloodService bloodService;
    private final GenderService genderService;

    /*
    Внедряем зависимости
     */
    @Autowired
    public PatientService(PatientRepository patientRepository, PlaceStudyService placeStudyService,
                          ParentService parentService, DocumentService documentService,
                          AddressService addressService, BloodService bloodService, GenderService genderService) {
        this.patientRepository = patientRepository;
        this.placeStudyService = placeStudyService;
        this.parentService = parentService;
        this.documentService = documentService;
        this.addressService = addressService;
        this.bloodService = bloodService;
        this.genderService = genderService;
    }

    /*
    Метод ищет пациента по его id и возвращает его в обертке Optional
     */
    public Optional<Patient> findById(Integer patientId) {
        return patientRepository.findById(patientId);
    }

    /*
    Метод ищет пациента по номеру телефона и возвращает пациента в обертке Optional
     */
    public Optional<Patient> findByPhone(String phone) {
        return patientRepository.findByPhone(phone);
    }

    /*
    Метод ищет пациента по имэйлу и возвращает пациента в обертке Optional
     */
    public Optional<Patient> findByEmail(String email) {
        return patientRepository.findByEmail(email);
    }

    /*
    Метод ищет пациента по медицинскому полюсу и возвращает пациента в обертке Optional
     */
    public Optional<Patient> findByPolicy(String policy) {
        return patientRepository.findByPolicy(policy);
    }

    /*
    Метод ищет пациента по снилсу и возвращает пациента в обертке Optional
     */
    public Optional<Patient> findBySnils(String snils) {
        return patientRepository.findBySnils(snils);
    }

    /*
    Метод ищет пациента по ИНН и возвращает пациента в обертке Optional
     */
    public Optional<Patient> findByInn(String inn) {
        return patientRepository.findByInn(inn);
    }

    /*
    Метод формирует и возвращает список всех пациентов
     */
    public List<Patient> findAll() {
        return patientRepository.findAll();
    }

    /*
    Метод формирует и возвращает список всех пациентов учитывая флаг published
     */
    public List<Patient> findAllByPublished(Boolean published) {
        return patientRepository.findByPublished(published);
    }

    /*
    Метод ищет пациентов по имени, фамилии и номеру телефона, возвращает список найденных пациентов
     */
    public List<Patient> search(String textSearch) {
        List<Patient> patients = new ArrayList<>();
        patients.addAll(patientRepository.findByFirstnameStartingWith(textSearch));
        patients.addAll(patientRepository.findByLastnameStartingWith(textSearch));
        patients.addAll(patientRepository.findByPhoneContaining(textSearch));
        return patients;
    }

    /*
    Метод сохраняет нового пациента, добавляет дату и время создания и изменения, по цепочке сохраняем дочерние
    сущности такие как: PlaceStudy - место учебы, Document - документ подтверждения личности,
    Address - адрес проживания, Blood - группа крови, Gender - гендер.
    Для кэша устанавливает пациента для данных сущностей.
    Также создаются или обновляются родители пациента, их может быть несколько.
    Далее пациент сохраняется.
     */
    @Transactional
    public void create(Patient newPatient) {

        //Записываем дату и время создания и изменения
        newPatient.setCreatedAt(LocalDateTime.now());
        newPatient.setUpdatedAt(LocalDateTime.now());

        //создаем место учебы пациента, плюс для кэша, для указанного места учебы пациента устанавливаем данного пациента
        placeStudyService.create(newPatient.getPlaceStudy());
        newPatient.getPlaceStudy().setPatient(newPatient);

        //создаем документ пациента, плюс для кэша, для указанного документа пациента устанавливаем данного пациента
        documentService.create(newPatient.getDocument());
        newPatient.getDocument().setPatient(newPatient);

        //создаем адрес проживания пациента, плюс для кэша, для указанного адреса проживания пациента устанавливаем данного пациента
        addressService.create(newPatient.getAddress());
        newPatient.getAddress().setPatient(newPatient);

        //если у пациента выбрана группа крови, т.е. она не null, то для кэша добавляем данного пациента в список
        // пауиентов у выбранной группы крови
        if (newPatient.getBlood() != null) {
            bloodService.addPatientAtListForBlood(newPatient, newPatient.getBlood());
        }

        //если у пациента выбран гендер, т.е. он не null, то для кэша добавляем данного пациента в список
        // пауиентов у выбранного гендера
        if (newPatient.getGender() != null) {
            genderService.addPatientAtListForGender(newPatient, newPatient.getGender());
        }

        //проходимся в цикле по всем указанным родителям данного пациента, их может быть несколько
        for (int i = 0; i < newPatient.getParents().size(); i++) {

            //получаем по очереди каждого родителя по номеру в списке i
            Parent parent = newPatient.getParents().get(i);

            //если id текущего родителя null, это значит что родитель создан новый и его нужно создать, если не null,
            // значит он уже есть в БД и его нужно апдейтить
            if (parent.getId() == null) {
                parentService.create(parent);
            } else {
                parentService.update(parent);
            }
            //для кэша добавляем данного пациента в список пациентов для текущего родителя
            parentService.addPatientAtListForParent(newPatient, parent, i);
        }

        //сохраняем данного пациента
        patientRepository.save(newPatient);
    }

    /*
    Метод сохраняет измененного пациента, находим существующего пациента в БД, копируем значения полей из изменяемого
    пациента в существующего, обновляем дату изменения, далее по цепочке обновляем все дочерние сущности такие как:
    PlaceStudy - место учебы, Document - документ подтверждения личности, Address - адрес проживания.
    Также для кэша устанавливаем измененного пациента для дочерних сущностей.
    Также создаются или обновляются родители пациента, их может быть несколько,
    они могут быть изменены либо добавлены новые.
     */
    @Transactional
    public void update(Patient updatedPatient) {

        //находим в БД существующего пациента по id
        Patient existingPatient = patientRepository.findById(updatedPatient.getId()).get();

        //копируем значения всех полей, кроме тех, которые не null, из измененного пациента в существующего
        copyNonNullProperties(updatedPatient, existingPatient);

        //обновляем дату и время изменения
        existingPatient.setUpdatedAt(LocalDateTime.now());

        //для кэша, для указанного места учебы пациента устанавливаем данного пациента и апдейтим место учебы
        existingPatient.getPlaceStudy().setPatient(existingPatient);
        placeStudyService.update(existingPatient.getPlaceStudy());

        //для кэша, для указанного документа пациента устанавливаем данного пациента и апдейтим документ
        existingPatient.getDocument().setPatient(existingPatient);
        documentService.update(existingPatient.getDocument());

        //для кэша, для указанного адреса проживания пациента устанавливаем данного пациента и апдейтим адрес
        existingPatient.getAddress().setPatient(existingPatient);
        addressService.update(existingPatient.getAddress());

        //если у пациента выбрана группа крови, т.е. она не null, то для кэша добавляем данного пациента в список
        // пауиентов у выбранной группы крови
        if (existingPatient.getBlood() != null) {
            bloodService.addPatientAtListForBlood(existingPatient, existingPatient.getBlood());
        }

        //если у пациента выбран гендер, т.е. он не null, то для кэша добавляем данного пациента в список
        // пауиентов у выбранного гендера
        if (existingPatient.getGender() != null) {
            genderService.addPatientAtListForGender(existingPatient, existingPatient.getGender());
        }

        //проходимся в цикле по всем указанным родителям данного пациента, их может быть несколько
        for (int i = 0; i < existingPatient.getParents().size(); i++) {

            //получаем по очереди каждого родителя по номеру в списке i
            Parent parent = existingPatient.getParents().get(i);

            //если id текущего родителя null, это значит что родитель создан новый и его нужно создать, если не null,
            // значит он уже есть в БД и его нужно апдейтить
            if (parent.getId() == null) {
                parentService.create(parent);
            } else {
                parentService.update(parent);
            }
            //для кэша добавляем данного пациента в список пациентов для текущего родителя
            parentService.addPatientAtListForParent(existingPatient, parent, i);
        }
        //т.к. существующий пациент находится в персистенс контексте, сохранять его не нужно
    }

    /*
    Метод добавляет вызов врача в список вызовов для пациента указанного в вызове, делается это для кэша
     */
    public void addCallingAtListForPatient(Calling calling, Patient patient) {
        addObjectOneInListForObjectTwo(calling, patient, this);
    }

    /*
    Метод добавляет договор в список договоров для пациента указанного в договоре, делается это для кэша
     */
    public void addContractAtListForPatient(Contract contract, Patient patient) {
        addObjectOneInListForObjectTwo(contract, patient, this);
    }
}
