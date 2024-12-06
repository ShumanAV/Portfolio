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
    public void create(Contract newContract) {

        //Записываем дату и время создания и изменения
        newContract.setCreatedAt(LocalDateTime.now());
        newContract.setUpdatedAt(LocalDateTime.now());

        //для кэша добавляем данный договор в лист договоров доктору, который в договоре
        doctorService.addContractAtListForDoctor(newContract, newContract.getDoctor());

        //для кэша добавляем данный договор в лист договоров пациенту, который в договоре
        patientService.addContractAtListForPatient(newContract, newContract.getPatient());

        //если id пациента null, это значит что пациент создан новый, поэтому нужно создать и пациента тоже,
        // если id не null, значит пациент выбран уже существующий, в этом случае его нужно апдейтить
        if (newContract.getPatient().getId() == null) {
            patientService.create(newContract.getPatient());
        } else {
            patientService.update(newContract.getPatient());
        }

        //для кэша добавляем данный договор в лист договоров типу договора, который указан в договоре
        typeContractService.addContractAtListForTypeContract(newContract, newContract.getTypeContract());

        //сохраняем новый договор в БД
        contractRepository.save(newContract);
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
