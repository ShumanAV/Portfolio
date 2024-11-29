package ru.shuman.Project_Aibolit_Server.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.shuman.Project_Aibolit_Server.models.Contract;
import ru.shuman.Project_Aibolit_Server.repositories.ContractRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@Transactional(readOnly = true)
public class ContractService {

    private final ContractRepository contractRepository;
    private final DoctorService doctorService;
    private final PatientService patientService;
    private final TypeContractService typeContractService;

    @Autowired
    public ContractService(ContractRepository contractRepository, DoctorService doctorService, PatientService patientService,
                           TypeContractService typeContractService) {
        this.contractRepository = contractRepository;
        this.doctorService = doctorService;
        this.patientService = patientService;
        this.typeContractService = typeContractService;
    }

    public Optional<Contract> findById(Integer contractId) {
        return contractRepository.findById(contractId);
    }

    public List<Contract> findAll() {
        return contractRepository.findAll();
    }

    @Transactional
    public void create(Contract contract) {

        contract.setCreatedAt(LocalDateTime.now());
        contract.setUpdatedAt(LocalDateTime.now());

        doctorService.addContractAtListForDoctor(contract, contract.getDoctor());

        patientService.addContractAtListForPatient(contract, contract.getPatient());
        if (contract.getPatient().getId() == null) {
            patientService.create(contract.getPatient());
        } else {
            patientService.update(contract.getPatient());
        }

        typeContractService.addContractAtListForTypeContract(contract, contract.getTypeContract());

        contractRepository.save(contract);
    }

    @Transactional
    public void update(Contract contract) {

        contract.setUpdatedAt(LocalDateTime.now());

        doctorService.addContractAtListForDoctor(contract, contract.getDoctor());

        patientService.addContractAtListForPatient(contract, contract.getPatient());
        if (contract.getPatient().getId() == null) {
            patientService.create(contract.getPatient());
        } else {
            patientService.update(contract.getPatient());
        }

        typeContractService.addContractAtListForTypeContract(contract, contract.getTypeContract());

        contractRepository.save(contract);
    }
}
