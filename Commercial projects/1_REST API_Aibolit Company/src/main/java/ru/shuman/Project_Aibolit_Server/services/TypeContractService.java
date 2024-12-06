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

    /*
    Внедрение зависимостей
     */
    @Autowired
    public TypeContractService(TypeContractRepository typeContractRepository) {
        this.typeContractRepository = typeContractRepository;
    }

    /*
    Метод формирует список всех типов договоров и возвращает его
    */
    public List<TypeContract> findAll() {
        return typeContractRepository.findAll();
    }

    /*
    Метод находит тип договора по id и возвращает его в обертке Optional
     */
    public Optional<TypeContract> findById(Integer typeContractId) {
        return typeContractRepository.findById(typeContractId);
    }

    /*
    Метод сохраняет новый тип договора в БД
     */
    @Transactional
    public void create(TypeContract newTypeContract) {

        //добавляем дату и время создания и изменения
        newTypeContract.setCreatedAt(LocalDateTime.now());
        newTypeContract.setUpdatedAt(LocalDateTime.now());

        typeContractRepository.save(newTypeContract);
    }

    /*
    Метод изменяет существующий тип договора в БД, ищем существующий тип договора в БД по id, переносим время создания
    из типа договора из БД в изменяемый, обновляем дату и время изменения и сохраняем
     */
    @Transactional
    public void update(TypeContract updatedtypeContract) {

        Optional<TypeContract> existingTypeContract = typeContractRepository.findById(updatedtypeContract.getId());

        updatedtypeContract.setCreatedAt(existingTypeContract.get().getCreatedAt());
        updatedtypeContract.setUpdatedAt(LocalDateTime.now());

        typeContractRepository.save(updatedtypeContract);
    }

    /*
    Метод добавляет договор в лист типа договора, делается это для кэша
    */
    public void addContractAtListForTypeContract(Contract contract, TypeContract typeContract) {
        GeneralMethods.addObjectOneInListForObjectTwo(contract, typeContract, this);
    }
}
