package ru.shuman.Project_Aibolit_Server.services;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import ru.shuman.Project_Aibolit_Server.models.Address;
import ru.shuman.Project_Aibolit_Server.repositories.AddressRepository;

import javax.persistence.criteria.CriteriaBuilder;
import java.util.Optional;

import static org.mockito.Mockito.*;

class AddressServiceTest {

    private AddressRepository addressRepository;
    private RegionService regionService;
    private AddressService addressService;

    private AddressServiceTest () {
        addressRepository = mock(AddressRepository.class);
        regionService = mock(RegionService.class);
        addressService = new AddressService(addressRepository, regionService);
    }

    /**
     * Метод тестирует метод findById в сервисе AddressService, если мы в репозиторий AddressRepository
     * передаем id адреса = 1, то он нам возвращает пустой объект класса Address в обертке Optional
     */
    @Test
    void findByIdShouldBeEmptyAddress() {
        when(addressRepository.findById(1)).thenReturn(Optional.of(new Address()));
        Optional<Address> address = addressService.findById(1);
        Assertions.assertEquals(Optional.of(new Address()), address);

        verify(addressRepository).findById(1);
    }

    /**
     * Метод тестирует метод findById в сервисе AddressService, если мы в репозиторий AddressRepository
     * передаем id адреса = 2, то он нам возвращает пустой объект класса Optional
     */
    @Test
    void findByIdShouldBeEmptyOptional () {
        when(addressRepository.findById(anyInt())).thenReturn(Optional.of(new Address()));
        Optional<Address> address = addressService.findById(2);
        Assertions.assertEquals(Optional.of(new Address()), address);

        verify(addressRepository).findById(2);
    }

    /**
     * Метод тестирует метод findById в сервисе AddressService, если мы в репозиторий AddressRepository
     * передаем любой id, то он нам возвращает пустой объект Address в обертке Optional, далее отслеживаем с каким
     * параметром был вызван метод findById
     */
    @Test
    void testCaptor () {
        when(addressRepository.findById(anyInt())).thenReturn(Optional.of(new Address()));
        Optional<Address> address = addressService.findById(2);
        Assertions.assertEquals(Optional.of(new Address()), address);

        ArgumentCaptor<Integer> captor = ArgumentCaptor.forClass(Integer.class);

        verify(addressRepository).findById(captor.capture());

        Integer argument = captor.getValue();

        Assertions.assertEquals(2, argument);
    }

    @Test
    void create() {
        when(addressRepository.save(new Address())).thenReturn();
    }

    @Test
    void update() {
    }
}