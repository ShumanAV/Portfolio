package ru.shuman.Project_Aibolit_Server.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.shuman.Project_Aibolit_Server.models.Contract;
import ru.shuman.Project_Aibolit_Server.repositories.ContractRepository;
import ru.shuman.Project_Aibolit_Server.util.GeneralMethods;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static ru.shuman.Project_Aibolit_Server.util.GeneralMethods.copyNonNullProperties;

@Service
@Transactional(readOnly = true)
public class ContractService {

    private final ContractRepository contractRepository;
    private final DoctorService doctorService;
    private final PatientService patientService;
    private final TypeContractService typeContractService;

    /*
    Внедрение зависимостей
     */
    @Autowired
    public ContractService(ContractRepository contractRepository, DoctorService doctorService, PatientService patientService,
                           TypeContractService typeContractService) {
        this.contractRepository = contractRepository;
        this.doctorService = doctorService;
        this.patientService = patientService;
        this.typeContractService = typeContractService;
    }

    /*
    Метод ищет договор по id и возвращет его в обертке Optional
     */
    public Optional<Contract> findById(Integer contractId) {
        return contractRepository.findById(contractId);
    }

    /*
    Метод формирует и возвращает список всех договоров из бД
     */
    public List<Contract> findAll() {
        return contractRepository.findAll();
    }

    /*
    Метод сохраняет новый договор в БД, записывает дату и время создания и изменения договора, создает или апдейтит
    пациента указанного в договоре, для кэша добавляет данный договор в сущности: для доктора, пациента, типа договора
     */
    @Transactional
    public void create(Contract newContract) {

        //Записываем дату и время создания и изменения
        newContract.setCreatedAt(LocalDateTime.now());
        newContract.setUpdatedAt(LocalDateTime.now());

        //для кэша добавляем данный договор в лист договоров доктору, который в договоре
        doctorService.addContractAtListForDoctor(newContract, newContract.getDoctor());

        //если id пациента null, это значит что пациент создан новый, поэтому нужно создать и пациента тоже,
        // если id не null, значит пациент выбран уже существующий, в этом случае его нужно апдейтить
        if (newContract.getPatient().getId() == null) {
            patientService.create(newContract.getPatient());
        } else {
            patientService.update(newContract.getPatient());
        }

        //для кэша добавляем данный договор в лист договоров пациенту, который в договоре, данная строка должна быть ниже
        // создания пациента, т.к. в методе потребуется id пациента
        patientService.addContractAtListForPatient(newContract, newContract.getPatient());

        //для кэша добавляем данный договор в лист договоров типу договора, который указан в договоре
        typeContractService.addContractAtListForTypeContract(newContract, newContract.getTypeContract());

        //сохраняем новый договор в БД
        contractRepository.save(newContract);
    }

    /*
    Метод сохраняет измененный договор в БД, находит существующий договор в БД, копирует все значения полей из измененного
     договора в существующий, обновляем время изменения договора, создает или апдейтит пациента указанного в договоре,
     для кэша добавляет данный договор в сущности: для доктора, пациента, типа договора
     */
    @Transactional
    public void update(Contract updatedContract) {

        //находим существующий договор в БД по id
        Contract existingContract = contractRepository.findById(updatedContract.getId()).get();

        //копируем значения всех полей кроме тех, которые не null, из изменяемого договора в существующий
        copyNonNullProperties(updatedContract, existingContract);

        //обновляем дату и время изменения договора
        existingContract.setUpdatedAt(LocalDateTime.now());

        //для кэша добавляем договор в список договоров для доктора указанного в договоре
        doctorService.addContractAtListForDoctor(existingContract, existingContract.getDoctor());

        //если id пациента равен null, это значит, что пациент создан новый и его нужно создать, если не null, значит
        // пациент выбран существующий и поэтому его нужно апдейтить
        if (existingContract.getPatient().getId() == null) {
            patientService.create(existingContract.getPatient());
        } else {
            patientService.update(existingContract.getPatient());
        }

        //для кэша добавляем договор в список договоров для пациента указанного в договоре, эта строка должна быть ниже
        // создания пациента, т.к. в методе понадобится его id
        patientService.addContractAtListForPatient(existingContract, existingContract.getPatient());

        //для кэша добавляем договор в список договоров для типа договора указанного в договоре
        typeContractService.addContractAtListForTypeContract(existingContract, existingContract.getTypeContract());

        //сохранять существующий договор не нужно, т.к. он находится в персистенс контексте
    }
}
