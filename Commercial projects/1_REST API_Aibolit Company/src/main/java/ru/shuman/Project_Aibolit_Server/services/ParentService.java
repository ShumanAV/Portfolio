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

    public Optional<Parent> findById(Integer parentId) {
        return parentRepository.findById(parentId);
    }

    public Optional<Parent> findByPhone(String phone) {
        return parentRepository.findByPhone(phone);
    }

    ;

    public Optional<Parent> findByEmail(String email) {
        return parentRepository.findByEmail(email);
    }

    ;

    public Optional<Parent> findByPolicy(String policy) {
        return parentRepository.findByPolicy(policy);
    }

    ;

    public Optional<Parent> findBySnils(String snils) {
        return parentRepository.findBySnils(snils);
    }

    ;

    public Optional<Parent> findByInn(String inn) {
        return parentRepository.findByInn(inn);
    }

    ;

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

    @Transactional
    public void update(Parent parent) {

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

    public void addPatientAtListForParent(Patient patient, Parent parent, int numberParent) {

        if (patient.getParents() != null) {

            if (parent.getId() != null) {
                Optional<Parent> existingParent = parentRepository.findById(parent.getId());
                if (existingParent.isPresent()) {
                    patient.getParents().get(numberParent).setPatients(existingParent.get().getPatients());
                }
            }

            if (patient.getParents().get(numberParent).getPatients() == null) {
                patient.getParents().get(numberParent).setPatients(new ArrayList<>());

            } else {
                int index = 0;
                for (Patient currentPatient : patient.getParents().get(numberParent).getPatients()) {
                    if (patient.getId() == currentPatient.getId()) {
                        patient.getParents().get(numberParent).getPatients().set(index, patient);
                    }
                    index += 1;
                }
            }

            if (!patient.getParents().get(numberParent).getPatients().contains(patient)) {
                patient.getParents().get(numberParent).getPatients().add(patient);
            }
        }
    }
}
