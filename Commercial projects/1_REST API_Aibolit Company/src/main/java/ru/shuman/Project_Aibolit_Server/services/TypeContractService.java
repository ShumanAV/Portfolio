package ru.shuman.Project_Aibolit_Server.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.shuman.Project_Aibolit_Server.models.Contract;
import ru.shuman.Project_Aibolit_Server.models.TypeContract;
import ru.shuman.Project_Aibolit_Server.repositories.TypeContractRepository;
import ru.shuman.Project_Aibolit_Server.util.GeneralMethods;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@Transactional(readOnly = true)
public class TypeContractService {

    private final TypeContractRepository typeContractRepository;

    @Autowired
    public TypeContractService(TypeContractRepository typeContractRepository) {
        this.typeContractRepository = typeContractRepository;
    }

    public Optional<TypeContract> findById(Integer typeContractId) {
        return typeContractRepository.findById(typeContractId);
    }

    public void addContractAtListForTypeContract(Contract contract, TypeContract typeContract) {
        GeneralMethods.addObjectOneInListForObjectTwo(contract, typeContract, this);
    }

    @Transactional
    public void create(TypeContract typeContract) {

        typeContract.setCreatedAt(LocalDateTime.now());
        typeContract.setUpdatedAt(LocalDateTime.now());

        typeContractRepository.save(typeContract);
    }

    @Transactional
    public void update(TypeContract typeContract) {

        typeContract.setUpdatedAt(LocalDateTime.now());

        typeContractRepository.save(typeContract);
    }

    public List<TypeContract> findAll() {
        return typeContractRepository.findAll();
    }
}
