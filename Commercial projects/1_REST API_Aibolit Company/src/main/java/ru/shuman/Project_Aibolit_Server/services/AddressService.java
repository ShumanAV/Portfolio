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

    /*
    Внедрение зависимостей
     */
    @Autowired
    public AddressService(AddressRepository addressRepository, RegionService regionService) {
        this.addressRepository = addressRepository;
        this.regionService = regionService;
    }

    /*
    Метод ищет адрес по id и возвращает его в обертке Optional
     */
    public Optional<Address> findById(Integer addressId) {
        return addressRepository.findById(addressId);
    }

    /*
    Метод сохраняет новый адрес в БД
     */
    @Transactional
    public void create(Address newAddress) {

        //для кэша добавляем адрес в список адресов для региона указанного в адресе
        regionService.AddAddressAtListForRegion(newAddress, newAddress.getRegion());

        addressRepository.save(newAddress);
    }

    /*
    Метод сохраняет измененный адрес в БД
     */
    @Transactional
    public void update(Address updatedAddress) {

        //для кэша добавляем адрес в список адресов для региона указанного в адресе
        regionService.AddAddressAtListForRegion(updatedAddress, updatedAddress.getRegion());

        addressRepository.save(updatedAddress);
    }
}
