package ru.shuman.Project_Aibolit_Server.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.shuman.Project_Aibolit_Server.models.Address;
import ru.shuman.Project_Aibolit_Server.repositories.AddressRepository;

import java.util.Optional;

@Service
@Transactional(readOnly = true)
public class AddressService {

    private final AddressRepository addressRepository;
    private final RegionService regionService;

    @Autowired
    public AddressService(AddressRepository addressRepository, RegionService regionService) {
        this.addressRepository = addressRepository;
        this.regionService = regionService;
    }

    public Optional<Address> findById(Integer addressId) {
        return addressRepository.findById(addressId);
    }

    @Transactional
    public void create(Address address) {

        regionService.setAddressesForRegion(address, address.getRegion());

        addressRepository.save(address);
    }

    @Transactional
    public void update(Address address) {

        regionService.setAddressesForRegion(address, address.getRegion());

        addressRepository.save(address);
    }
}
