package ru.shuman.Project_Aibolit_Server.services;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import ru.shuman.Project_Aibolit_Server.models.Address;
import ru.shuman.Project_Aibolit_Server.models.Region;
import ru.shuman.Project_Aibolit_Server.repositories.AddressRepository;

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
     * передаем id адреса = 0, то он нам возвращает пустой объект класса Address в обертке Optional
     * Также проверяем выполнялся ли метод findById(0) в репозитории addressRepository
     */
    @Test
    void findByIdShouldReturnEmptyAddress() {
        when(addressRepository.findById(0)).thenReturn(Optional.of(new Address()));
        Optional<Address> address = addressService.findById(0);
        Assertions.assertEquals(Optional.of(new Address()), address);

        verify(addressRepository).findById(0);
    }

    /**
     * Метод тестирует метод findById в сервисе AddressService, если мы в репозиторий AddressRepository
     * передаем любой id, то он нам возвращает пустой объект Address в обертке Optional, далее отслеживаем, что
     * был вызван метод findById с идентификатором addressId = 2
     */
    @Test
    void findByIdShouldReturnEmptyAddressAndCheckedId () {

        Optional<Address> emptyAddress = Optional.of(new Address());
        when(addressRepository.findById(anyInt())).thenReturn(emptyAddress);

        Integer checkedId = 2;
        Assertions.assertEquals(emptyAddress, addressService.findById(checkedId));

        ArgumentCaptor<Integer> captor = ArgumentCaptor.forClass(Integer.class);
        verify(addressRepository).findById(captor.capture());
        Integer checkedArgument = captor.getValue();
        Assertions.assertEquals(checkedId, checkedArgument);
    }


    /**
     * Метод тестирует метод create в сервисе AddressService, т.к. он void, проверяем факт запуска мока
     * addressRepository и метода в нем save(emptyAddress), при запуске метода setAddressesForRegion сервиса
     * regionService делаем чтобы ничего не происходило
     */

    @Test
    void createShouldBeRunningAddressRepository() {
        Address emptyAddress = new Address();
        Region emptyRegion = new Region();

        doNothing().when(regionService).AddAddressAtListForRegion(emptyAddress, emptyRegion);
        when(addressRepository.save(emptyAddress)).thenReturn(emptyAddress);
        addressService.create(emptyAddress);

        verify(addressRepository).save(emptyAddress);
    }

    /**
     * Метод тестирует метод update в сервисе AddressService, т.к. он void, проверяем факт запуска мока
     * addressRepository и метода в нем save(emptyAddress), при запуске метода setAddressesForRegion сервиса
     * regionService делаем чтобы ничего не происходило
     */

    @Test
    void updateShouldBeRunningAddressRepository() {
        Address emptyAddress = new Address();
        Region emptyRegion = new Region();

        doNothing().when(regionService).AddAddressAtListForRegion(emptyAddress, emptyRegion);
        when(addressRepository.save(emptyAddress)).thenReturn(emptyAddress);
        addressService.update(emptyAddress);

        verify(addressRepository).save(emptyAddress);
    }
}