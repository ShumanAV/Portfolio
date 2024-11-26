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
    private final UserService userService;
    private final PatientService patientService;
    private final TypeContractService typeContractService;

    @Autowired
    public ContractService(ContractRepository contractRepository, UserService userService, PatientService patientService,
                           TypeContractService typeContractService) {
        this.contractRepository = contractRepository;
        this.userService = userService;
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

        userService.setContractsForUser(contract, contract.getUser());

        patientService.setContractsForPatient(contract, contract.getPatient());
        if (contract.getPatient().getId() == null) {
            patientService.create(contract.getPatient());
        } else {
            patientService.update(contract.getPatient());
        }

        typeContractService.setContractsForTypeContract(contract, contract.getTypeContract());

        contractRepository.save(contract);
    }

    @Transactional
    public void update(Contract contract) {

        contract.setUpdatedAt(LocalDateTime.now());

        userService.setContractsForUser(contract, contract.getUser());

        patientService.setContractsForPatient(contract, contract.getPatient());
        if (contract.getPatient().getId() == null) {
            patientService.create(contract.getPatient());
        } else {
            patientService.update(contract.getPatient());
        }

        typeContractService.setContractsForTypeContract(contract, contract.getTypeContract());

        contractRepository.save(contract);
    }
}
