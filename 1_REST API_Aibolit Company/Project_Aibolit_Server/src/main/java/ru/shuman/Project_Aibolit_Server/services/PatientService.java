package ru.shuman.Project_Aibolit_Server.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.shuman.Project_Aibolit_Server.models.*;
import ru.shuman.Project_Aibolit_Server.repositories.PatientRepository;
import ru.shuman.Project_Aibolit_Server.util.StandardMethods;

import java.time.LocalDateTime;
import java.util.*;

@Service
@Transactional(readOnly = true)
public class PatientService {

    private final PatientRepository patientRepository;
    private final PlaceStudyService placeStudyService;
    private final ParentService parentService;
    private final TypeDocService typeDocService;
    private final DocumentService documentService;
    private final AddressService addressService;
    private final BloodService bloodService;
    private final GenderService genderService;

    @Autowired
    public PatientService(PatientRepository patientRepository, PlaceStudyService placeStudyService,
                          ParentService parentService, TypeDocService typeDocService, DocumentService documentService, AddressService addressService, BloodService bloodService, GenderService genderService) {
        this.patientRepository = patientRepository;
        this.placeStudyService = placeStudyService;
        this.parentService = parentService;
        this.typeDocService = typeDocService;
        this.documentService = documentService;
        this.addressService = addressService;
        this.bloodService = bloodService;
        this.genderService = genderService;
    }

    public Optional<Patient> findById(Integer patientId) {
        return patientRepository.findById(patientId);
    }

    public Optional<Patient> findByPhone(String phone) {
        return patientRepository.findByPhone(phone);
    }

    public Optional<Patient> findByEmail(String email) {
        return patientRepository.findByEmail(email);
    }

    public Optional<Patient> findByPolicy(String policy) {
        return patientRepository.findByPolicy(policy);
    }

    public Optional<Patient> findBySnils(String snils) {
        return patientRepository.findBySnils(snils);
    }

    public Optional<Patient> findByInn(String inn) {
        return patientRepository.findByInn(inn);
    }

    public List<Patient> findAll() {
        return patientRepository.findAll();
    }

    public List<Patient> findAllByPublished(Boolean published) {
        return patientRepository.findByPublished(published);
    }

    public List<Patient> search(String textSearch) {
        List<Patient> patients = new ArrayList<>();
        patientRepository.findByFirstnameStartingWith(textSearch).forEach(patient -> patients.add(patient));
        patientRepository.findByLastnameStartingWith(textSearch).forEach(patient -> patients.add(patient));
        patientRepository.findByPhoneContaining(textSearch).forEach(patient -> patients.add(patient));
        return patients;
    }

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
            bloodService.setPatientsForBlood(patient, patient.getBlood());
        }

        if (patient.getGender() != null) {
            genderService.setPatientsForGender(patient, patient.getGender());
        }

        int count = 0;
        for (Parent parent : patient.getParents()) {
            parentService.setPatientForParent(patient, parent, count);
            if (parent.getId() == null) {
                parentService.create(parent);
            } else {
                parentService.update(parent);
            }
            count += 1;
        }

        patientRepository.save(patient);
    }

    @Transactional
    public void update(Patient patient) {

        patient.setUpdatedAt(LocalDateTime.now());

        patient.getPlaceStudy().setPatient(patient);
        placeStudyService.update(patient.getPlaceStudy());

        patient.getDocument().setPatient(patient);
        documentService.update(patient.getDocument());

        patient.getAddress().setPatient(patient);
        addressService.update(patient.getAddress());

        if (patient.getBlood() != null) {
            bloodService.setPatientsForBlood(patient, patient.getBlood());
        }

        if (patient.getGender() != null) {
            genderService.setPatientsForGender(patient, patient.getGender());
        }

        int numberParent = 0;
        for (Parent parent : patient.getParents()) {
            parentService.setPatientForParent(patient, parent, numberParent);
            if (parent.getId() == null) {
                parentService.create(parent);
            } else {
                parentService.update(parent);
            }
            numberParent += 1;
        }

        patientRepository.save(patient);
    }

    public void setCallingsForPatient(Calling calling, Patient patient) {
        StandardMethods.addObjectOneInListForObjectTwo(calling, patient, this);
    }

    public void setContractsForPatient(Contract contract, Patient patient) {
        StandardMethods.addObjectOneInListForObjectTwo(contract, patient, this);
    }
}
