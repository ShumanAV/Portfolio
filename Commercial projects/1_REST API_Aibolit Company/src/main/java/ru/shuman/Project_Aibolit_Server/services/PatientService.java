package ru.shuman.Project_Aibolit_Server.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.shuman.Project_Aibolit_Server.models.*;
import ru.shuman.Project_Aibolit_Server.repositories.PatientRepository;
import ru.shuman.Project_Aibolit_Server.util.GeneralMethods;

import java.time.LocalDateTime;
import java.util.*;

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
    Далее для кэша устанавливает пациента для данных сущностей.
    Также создаются или обновляются родители пациента, их может быть несколько.
    Далее пациент сохраняется.
     */
    @Transactional
    public void create(Patient patient) {

        patient.setCreatedAt(LocalDateTime.now());
        patient.setUpdatedAt(LocalDateTime.now());

        patient.getPlaceStudy().setPatient(patient);
        placeStudyService.create(patient.getPlaceStudy());

        patient.getDocument().setPatient(patient);
        documentService.create(patient.getDocument());

        patient.getAddress().setPatient(patient);
        addressService.create(patient.getAddress());

        if (patient.getBlood() != null) {
            bloodService.addPatientAtListForBlood(patient, patient.getBlood());
        }

        if (patient.getGender() != null) {
            genderService.addPatientAtListForGender(patient, patient.getGender());
        }

        int count = 0;
        for (Parent parent : patient.getParents()) {
            parentService.addPatientAtListForParent(patient, parent, count);
            if (parent.getId() == null) {
                parentService.create(parent);
            } else {
                parentService.update(parent);
            }
            count += 1;
        }

        patientRepository.save(patient);
    }

    /*
    Метод сохраняет измененного пациента, находим существующего пациента в БД, переносим в изменяемого пациента
    время создания, далее по цепочке обновляем все дочерние сущности такие как:  PlaceStudy - место учебы,
    Document - документ подтверждения личности, Address - адрес проживания, Blood - группа крови, Gender - гендер.
    Также для кэша устанавливаем пациента для данных сущностей.
    Также создаются или обновляются родители пациента, их может быть несколько,
    они могут быть изменены либо добавлены новые.
    Далее пациент сохраняется.
     */
    @Transactional
    public void update(Patient patient) {

        Patient existingPatient = patientRepository.findById(patient.getId()).get();

        patient.setCreatedAt(existingPatient.getCreatedAt());
        patient.setUpdatedAt(LocalDateTime.now());

        patient.getPlaceStudy().setPatient(patient);
        placeStudyService.update(patient.getPlaceStudy());

        patient.getDocument().setPatient(patient);
        documentService.update(patient.getDocument());

        patient.getAddress().setPatient(patient);
        addressService.update(patient.getAddress());

        if (patient.getBlood() != null) {
            bloodService.addPatientAtListForBlood(patient, patient.getBlood());
        }

        if (patient.getGender() != null) {
            genderService.addPatientAtListForGender(patient, patient.getGender());
        }

        int numberParent = 0;
        for (Parent parent : patient.getParents()) {
            parentService.addPatientAtListForParent(patient, parent, numberParent);
            if (parent.getId() == null) {
                parentService.create(parent);
            } else {
                parentService.update(parent);
            }
            numberParent += 1;
        }

        patientRepository.save(patient);
    }

    /*
    Метод добавляет вызов врача в лист пациента, делается это для кэша
     */
    public void addCallingAtListForPatient(Calling calling, Patient patient) {
        GeneralMethods.addObjectOneInListForObjectTwo(calling, patient, this);
    }

    /*
    Метод добавляет договор в лист пациента, делается это для кэша
     */
    public void addContractAtListForPatient(Contract contract, Patient patient) {
        GeneralMethods.addObjectOneInListForObjectTwo(contract, patient, this);
    }
}
