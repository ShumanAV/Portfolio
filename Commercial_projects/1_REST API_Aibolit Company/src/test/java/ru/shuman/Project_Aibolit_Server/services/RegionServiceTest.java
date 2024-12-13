package ru.shuman.Project_Aibolit_Server.services;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.MockedStatic;
import org.mockito.Mockito;
import org.mockito.stubbing.Answer;
import ru.shuman.Project_Aibolit_Server.models.Address;
import ru.shuman.Project_Aibolit_Server.models.Region;
import ru.shuman.Project_Aibolit_Server.repositories.RegionRepository;
import ru.shuman.Project_Aibolit_Server.util.GeneralMethods;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.*;

class RegionServiceTest {

    private RegionRepository regionRepository;
    private RegionService regionService;

    private RegionServiceTest() {
        regionRepository = mock(RegionRepository.class);
        regionService = new RegionService(regionRepository);
    }

    /**
     * Метод тестирует метод findById сервиса RegionService,
     * который принимает на вход параметр типа Integer, если в метод findById передаем любой Int,
     * то он должен вернуть нам объект типа Region в обертке Optional, далее отслеживаем, что был вызван метод findById
     * с id checkedId = 1
     */

    @Test
    void findByIdShouldReturnRegionAndCheckedRegion() {
        int regionId = 1;
        Optional<Region> mockRegion = Optional.of(new Region());
        mockRegion.get().setId(regionId);
        when(regionRepository.findById(anyInt())).thenReturn(mockRegion);

        Optional<Region> resultRegion = regionService.findById(regionId);

        Assertions.assertEquals(mockRegion,resultRegion);

        ArgumentCaptor<Integer> captor = ArgumentCaptor.forClass(Integer.class);
        verify(regionRepository).findById(captor.capture());
        Integer checkedId = captor.getValue();
        Assertions.assertEquals(regionId, checkedId);
    }

    /**
     * Метод тестирует метод findByName сервиса RegionService, который принимает на вход параметр типа String,
     * если в метод findByName передаем любое имя региона, то он должен вернуть нам объект типа Region в обертке
     * Optional, далее отслеживаем, что был вызван метод findByName с именем региона checkedName = "Tomsk"
     */

    @Test
    void findByNameShouldReturnRegion() {
        Optional<Region> mockRegion = Optional.of(new Region());
        when(regionRepository.findByName(anyString())).thenReturn(mockRegion);

        String regionName = "Tomsk";
        Optional<Region> resultRegion = regionService.findByName(regionName);

        Assertions.assertEquals(mockRegion, resultRegion);

        ArgumentCaptor<String> captor = ArgumentCaptor.forClass(String.class);
        verify(regionRepository).findByName(captor.capture());
        String checkedName = captor.getValue();
        Assertions.assertEquals(regionName, checkedName);
    }

    /**
     * Метод тестирует метод setAddressesForRegion, который принимает на вход параметры типа Address и Region,
     * если в метод setAddressesForRegion передаем новый пустой объект типа Address и Region, то проверяем, что был
     * вызван метод GeneralMethods.addObjectOneInListForObjectTwo с любыми входными параметрами
     */

    @Test
    void addObjectOneInListForObjectTwoShouldRunning() {
        try (MockedStatic<GeneralMethods> mock = Mockito.mockStatic(GeneralMethods.class)) {
            mock.when(() -> GeneralMethods.addObjectOneInListForObjectTwo(any(), any(), any())).
                    thenAnswer((Answer<Void>) invocation -> null);

            regionService.AddAddressAtListForRegion(new Address(), new Region());

            mock.verify(() -> GeneralMethods.addObjectOneInListForObjectTwo(any(), any(), any()));
        }
    }

    /**
     * Метод тестирует метод create в сервисе RegionService, т.к. он void, проверяем факт запуска мока
     * regionRepository и метода в нем save(emptyRegion)
     */
    @Test
    void createShouldBeRunning() {
        Region emptyRegion = new Region();

        regionService.create(emptyRegion);

        verify(regionRepository).save(emptyRegion);
    }

    /**
     * Метод тестирует метод update в сервисе RegionService, т.к. он void, проверяем факт запуска мока
     * regionRepository и метода в нем save(emptyRegion)
     */
    @Test
    void updateShouldBeRunning() {
        Region emptyRegion = new Region();

        regionService.update(emptyRegion);

        verify(regionRepository).save(emptyRegion);
    }

    /**
     * Метод тестирует метод findAll в сервисе RegionService, задаем поведение метода findAll в репозитории
     * regionRepository, если запускаем метод findAll, он возвращает пустой лист, параметризированный Region,
     * запускаем метод в сервисе и сравниваем, то что возвращено и пустой лист
     */

    @Test
    void findAllShouldReturnEmptyList() {
        List<Region> emptyList = new ArrayList<>();
        when(regionRepository.findAll()).thenReturn(emptyList);

        List<Region> resultList = regionService.findAll();

        Assertions.assertEquals(emptyList, resultList);
    }
}