package ru.shuman.Project_Aibolit_Server.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.shuman.Project_Aibolit_Server.models.*;
import ru.shuman.Project_Aibolit_Server.repositories.RegionRepository;
import ru.shuman.Project_Aibolit_Server.util.StandardMethods;

import java.util.List;
import java.util.Optional;

@Service
@Transactional(readOnly = true)
public class RegionService {

    private final RegionRepository regionRepository;

    @Autowired
    public RegionService(RegionRepository regionRepository) {
        this.regionRepository = regionRepository;
    }

    public Optional<Region> findById(String regionId) {
        return regionRepository.findById(regionId);
    }

    public Optional<Region> findById(Integer regionId) {
        return regionRepository.findById(regionId);
    }

    public Optional<Region> findByName(String name) {
        return regionRepository.findByName(name);
    }

    public void setAddressesForRegion(Address address, Region region) {
        StandardMethods.addObjectOneInListForObjectTwo(address, region, this);
    }

    @Transactional
    public void create(Region region) {
        regionRepository.save(region);
    }

    @Transactional
    public void update(Region region) {
        regionRepository.save(region);
    }

    public List<Region> findAll() {
        return regionRepository.findAll();
    }
}
