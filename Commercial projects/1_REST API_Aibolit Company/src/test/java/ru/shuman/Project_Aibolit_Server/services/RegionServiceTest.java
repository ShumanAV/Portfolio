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
     * Метод тестирует метод findById, который принимает на вход параметр типа String сервиса RegionService,
     * если в метод findById передаем любой регион, то он должен вернуть нам пустой объект типа Region в обертке
     * Optional, далее отслеживаем, что был вызван метод findById с регионом checkedRegion = "Tomsk"
     */

    @Test
    void findByIdShouldReturnEmptyRegionAndCheckedRegion() {
//        Optional<Region> emptyRegion = Optional.of(new Region());
//        when(regionRepository.findById(anyString())).thenReturn(emptyRegion);
//
//        String checkedRegion = "Tomsk";
////        Assertions.assertEquals(emptyRegion, regionService.findById(checkedRegion));
//
//        ArgumentCaptor<String> captor = ArgumentCaptor.forClass(String.class);
//        verify(regionRepository).findById(captor.capture());
//        String checkedArgument = captor.getValue();
//        Assertions.assertEquals(checkedRegion, checkedArgument);
    }

    /**
     * Метод тестирует метод findById, который принимает на вход параметр типа Integer сервиса RegionService,
     * если в метод findById передаем любой id региона, то он должен вернуть нам пустой объект типа Region в обертке
     * Optional, далее отслеживаем, что был вызван метод findById с идентификатором региона checkedId = 1
     */

    @Test
    void findByIdShouldReturnEmptyRegionAndCheckedId() {
        Optional<Region> emptyRegion = Optional.of(new Region());
        when(regionRepository.findById(anyInt())).thenReturn(emptyRegion);

        Integer checkedId = 1;
        Assertions.assertEquals(emptyRegion, regionService.findById(checkedId));

        ArgumentCaptor<Integer> captor = ArgumentCaptor.forClass(Integer.class);
        verify(regionRepository).findById(captor.capture());
        Integer checkedArgument = captor.getValue();
        Assertions.assertEquals(checkedId, checkedArgument);
    }

    /**
     * Метод тестирует метод findByName, который принимает на вход параметр типа String сервиса RegionService,
     * если в метод findByName передаем любое имя региона, то он должен вернуть нам пустой объект типа Region в обертке
     * Optional, далее отслеживаем, что был вызван метод findByName с именем региона checkedName = "Tomsk"
     */

    @Test
    void findByNameShouldReturnEmptyRegion() {
        Optional<Region> emptyRegion = Optional.of(new Region());
        when(regionRepository.findByName(anyString())).thenReturn(emptyRegion);

        String checkedName = "Tomsk";
        Assertions.assertEquals(emptyRegion, regionService.findByName(checkedName));

        ArgumentCaptor<String> captor = ArgumentCaptor.forClass(String.class);
        verify(regionRepository).findByName(captor.capture());
        String checkedArgument = captor.getValue();
        Assertions.assertEquals(checkedName, checkedArgument);
    }

    /**
     * Метод тестирует метод setAddressesForRegion, который принимает на вход параметры типа Address и Region,
     * если в метод setAddressesForRegion передаем новый пустой объект типа Address и Region, то проверяем, что был
     * вызван метод GeneralMethods.addObjectOneInListForObjectTwo с любыми входными параметрами
     */

    @Test
    void setAddressesForRegionShouldRunningGeneralMethods() {
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
    void createShouldBeRunningRegionRepository() {
        Region emptyRegion = new Region();

        when(regionRepository.save(emptyRegion)).thenReturn(emptyRegion);
        regionService.create(emptyRegion);

        verify(regionRepository).save(emptyRegion);
    }

    /**
     * Метод тестирует метод update в сервисе RegionService, т.к. он void, проверяем факт запуска мока
     * regionRepository и метода в нем save(emptyRegion)
     */

    @Test
    void updateShouldBeRunningRegionRepository() {
        Region emptyRegion = new Region();

        when(regionRepository.save(emptyRegion)).thenReturn(emptyRegion);
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

        Assertions.assertEquals(emptyList, regionService.findAll());
    }
}