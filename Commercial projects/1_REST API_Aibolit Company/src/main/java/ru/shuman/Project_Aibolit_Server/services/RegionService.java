package ru.shuman.Project_Aibolit_Server.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.shuman.Project_Aibolit_Server.models.*;
import ru.shuman.Project_Aibolit_Server.repositories.RegionRepository;
import ru.shuman.Project_Aibolit_Server.util.GeneralMethods;

import java.util.List;
import java.util.Optional;

@Service
@Transactional(readOnly = true)
public class RegionService {

    private final RegionRepository regionRepository;

    /*
    Внедрение зависимостей
     */
    @Autowired
    public RegionService(RegionRepository regionRepository) {
        this.regionRepository = regionRepository;
    }

    /*
    Метод формирует и возвращает список всех регионов
     */
    public List<Region> findAll() {
        return regionRepository.findAll();
    }

    /*
    Метод ищет регион по коду региона и возвращает его в обертке Optional
     */
    public Optional<Region> findByCode(Integer code) {
        return regionRepository.findByCode(code);
    }

    /*
    Метод ищет регион по String id и возвращает его в обертке Optional
     */
    public Optional<Region> findById(String regionId) {
        return regionRepository.findById(regionId);
    }

    /*
    Метод ищет регион по id и возвращает его в обертке Optional
     */
    public Optional<Region> findById(Integer regionId) {
        return regionRepository.findById(regionId);
    }

    /*
    Метод ищет регион по названию и возвращает его в обертке Optional
     */
    public Optional<Region> findByName(String name) {
        return regionRepository.findByName(name);
    }

    /*
    Метод сохраняет новый регион в БД
     */
    @Transactional
    public void create(Region region) {
        regionRepository.save(region);
    }

    /*
    Метод сохраняет измененный регион в БД
     */
    @Transactional
    public void update(Region region) {
        regionRepository.save(region);
    }

    /*
    Метод добавляет адрес в лист региона, делается это для кэша
     */
    public void AddAddressAtListForRegion(Address address, Region region) {
        GeneralMethods.addObjectOneInListForObjectTwo(address, region, this);
    }
}
