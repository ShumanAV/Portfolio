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

import static ru.shuman.Project_Aibolit_Server.util.GeneralMethods.addObjectOneInListForObjectTwo;
import static ru.shuman.Project_Aibolit_Server.util.GeneralMethods.copyNonNullProperties;

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

        //добавляем дату и время создания и изменения типа договора
        newTypeContract.setCreatedAt(LocalDateTime.now());
        newTypeContract.setUpdatedAt(LocalDateTime.now());

        //сохраняем новый тип договора
        typeContractRepository.save(newTypeContract);
    }

    /*
    Метод сохраняет измененный тип договора в БД, ищет существующий тип договора в БД по id, копирует значения всех
    не null полей из измененного типа договора в существующий, обновляет дату и время изменения типа договора
     */
    @Transactional
    public void update(TypeContract updatedtypeContract) {

        //находим существующий тип договора в БД по id
        TypeContract existingTypeContract = typeContractRepository.findById(updatedtypeContract.getId()).get();

        //копируем значения всех полей кроме тех, которые null, из измененного типа договора в существующий
        copyNonNullProperties(updatedtypeContract, existingTypeContract);

        //обновляем дату и время изменения типа договора
        existingTypeContract.setUpdatedAt(LocalDateTime.now());
    }

    /*
    Метод добавляет договор в список договоров для типа договора, делается это для кэша
    */
    public void addContractAtListForTypeContract(Contract contract, TypeContract typeContract) {
        addObjectOneInListForObjectTwo(contract, typeContract, this);
    }
}
