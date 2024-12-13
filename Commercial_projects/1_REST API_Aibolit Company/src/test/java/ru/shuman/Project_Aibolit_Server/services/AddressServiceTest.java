package ru.shuman.Project_Aibolit_Server.services;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import ru.shuman.Project_Aibolit_Server.models.Address;
import ru.shuman.Project_Aibolit_Server.models.Region;
import ru.shuman.Project_Aibolit_Server.repositories.AddressRepository;

import java.util.Optional;

import static org.mockito.Mockito.*;

public class AddressServiceTest {

    @Mock
    private AddressRepository addressRepository;
    @Mock
    private RegionService regionService;
    @InjectMocks
    private AddressService addressService;

    @BeforeEach
    public void init() {
        MockitoAnnotations.initMocks(this);
    }

    /**
     * Метод тестирует метод findById в сервисе AddressService,
     * если мы в репозиторий AddressRepository передаем конкретный id адреса = 1, то он нам возвращает объект класса
     * Address с id = 1 в обертке Optional
     * Также проверяем выполнялся ли метод findById(1) в репозитории addressRepository
     */
    @Test
    void findByIdShouldReturnAddress() {
        Integer addressId = 1;
        Optional<Address> mockAddress = Optional.of(new Address());
        mockAddress.get().setId(addressId);

        when(addressRepository.findById(addressId)).thenReturn(mockAddress);

        Optional<Address> resultAddress = addressService.findById(addressId);

        Assertions.assertEquals(mockAddress, resultAddress);

        verify(addressRepository, times(1)).findById(addressId);
    }

    /**
     * Метод тестирует метод findById в сервисе AddressService,
     * если мы в репозиторий AddressRepository передаем любой id, то он нам возвращает объект Address c id = 2
     * в обертке Optional, далее отслеживаем, что был вызван метод findById с идентификатором addressId = 2
     */
    @Test
    void findByIdShouldReturnAddressAndCheckedId() {

        Integer addressId = 2;
        Optional<Address> mockAddress = Optional.of(new Address());
        mockAddress.get().setId(addressId);
        when(addressRepository.findById(anyInt())).thenReturn(mockAddress);

        Optional<Address> resultAddress = addressService.findById(addressId);
        Assertions.assertEquals(mockAddress, resultAddress);

        ArgumentCaptor<Integer> captor = ArgumentCaptor.forClass(Integer.class);
        verify(addressRepository, times(1)).findById(captor.capture());
        Integer checkedId = captor.getValue();
        Assertions.assertEquals(addressId, checkedId);
    }


    /**
     * Метод тестирует метод create в сервисе AddressService,
     * т.к. он void, проверяем факт запуска мока addressRepository и метода в нем save(emptyAddress),
     * при запуске метода AddAddressAtListForRegion сервиса regionService делаем чтобы ничего не происходило
     */

    @Test
    void createShouldBeRunningAddressRepository() {
        Address emptyAddress = new Address();
        Region emptyRegion = new Region();

        doNothing().when(regionService).AddAddressAtListForRegion(emptyAddress, emptyRegion);
        addressService.create(emptyAddress);

        verify(addressRepository).save(emptyAddress);
    }

    /**
     * Метод тестирует метод update в сервисе AddressService,
     * т.к. он void, проверяем факт запуска мока addressRepository и метода в нем save(emptyAddress),
     * при запуске метода AddAddressAtListForRegion сервиса regionService делаем чтобы ничего не происходило
     */

    @Test
    void updateShouldBeRunningAddressRepository() {
        Address emptyAddress = new Address();
        Region emptyRegion = new Region();

        doNothing().when(regionService).AddAddressAtListForRegion(emptyAddress, emptyRegion);
        addressService.update(emptyAddress);

        verify(addressRepository).save(emptyAddress);
    }
}