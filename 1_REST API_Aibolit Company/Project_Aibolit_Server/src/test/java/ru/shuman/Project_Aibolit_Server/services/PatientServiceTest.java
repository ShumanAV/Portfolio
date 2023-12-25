package ru.shuman.Project_Aibolit_Server.services;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import ru.shuman.Project_Aibolit_Server.models.Patient;
import ru.shuman.Project_Aibolit_Server.repositories.PatientRepository;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.*;

class PatientServiceTest {

    private PatientRepository patientRepository;
    private PlaceStudyService placeStudyService;
    private ParentService parentService;
    private DocumentService documentService;
    private AddressService addressService;
    private BloodService bloodService;
    private GenderService genderService;
    private PatientService patientService;

    private PatientServiceTest() {
        patientRepository = mock(PatientRepository.class);
        placeStudyService = mock(PlaceStudyService.class);
        parentService = mock(ParentService.class);
        documentService = mock(DocumentService.class);
        addressService = mock(AddressService.class);
        bloodService = mock(BloodService.class);
        genderService = mock(GenderService.class);
        patientService = new PatientService(patientRepository, placeStudyService, parentService,
                documentService, addressService, bloodService, genderService);
    }

    /**
     * Метод тестирует метод findById в сервисе PatientService, задаем поведение метода findById в репозитории
     * patientRepository, если запускаем метод findById с входным параметром в виде любого объекта типа Integer,
     * он возвращает пустой объект типа Patient в обертке Optional,
     * запускаем метод в сервисе и с помощью captor'a отслеживаем, какой checkId был передан при запуске
     * в метод findById сервиса
     */

    @Test
    void findByIdShouldReturnEmptyPatient() {
        Optional<Patient> emptyPatient = Optional.of(new Patient());
        when(patientRepository.findById(anyInt())).thenReturn(emptyPatient);

        int checkId = 1;
        patientService.findById(checkId);
        ArgumentCaptor<Integer> captor = ArgumentCaptor.forClass(Integer.class);
        verify(patientRepository).findById(captor.capture());
        int argument = captor.getValue();

        Assertions.assertEquals(checkId, argument);
    }

    /**
     * Метод тестирует метод findByPhone в сервисе PatientService, задаем поведение метода findByPhone в репозитории
     * patientRepository, если запускаем метод findByPhone с входным параметром в виде любого объекта типа String,
     * он возвращает пустой объект типа Patient в обертке Optional,
     * запускаем метод в сервисе и с помощью captor'a отслеживаем, какой checkPhone был передан при запуске
     * в метод findByPhone сервиса
     */

    @Test
    void findByPhoneShouldReturnEmptyPatient() {
        Optional<Patient> emptyPatient = Optional.of(new Patient());
        when(patientRepository.findByPhone(anyString())).thenReturn(emptyPatient);

        String checkPhone = "+79998887755";
        patientService.findByPhone(checkPhone);
        ArgumentCaptor<String> captor = ArgumentCaptor.forClass(String.class);
        verify(patientRepository).findByPhone(captor.capture());
        String argument = captor.getValue();

        Assertions.assertEquals(checkPhone, argument);
    }

    /**
     * Метод тестирует метод findByEmail в сервисе PatientService, задаем поведение метода findByEmail в репозитории
     * patientRepository, если запускаем метод findByEmail с входным параметром в виде любого объекта типа String,
     * он возвращает пустой объект типа Patient в обертке Optional,
     * запускаем метод в сервисе и с помощью captor'a отслеживаем, какой checkEmail был передан при запуске
     * в метод findByEmail сервиса
     */

    @Test
    void findByEmailShouldReturnEmptyPatient() {
        Optional<Patient> emptyPatient = Optional.of(new Patient());
        when(patientRepository.findByEmail(anyString())).thenReturn(emptyPatient);

        String checkEmail = "test@mail.ru";
        patientService.findByEmail(checkEmail);
        ArgumentCaptor<String> captor = ArgumentCaptor.forClass(String.class);
        verify(patientRepository).findByEmail(captor.capture());
        String argument = captor.getValue();

        Assertions.assertEquals(checkEmail, argument);
    }

    /**
     * Метод тестирует метод findByPolicy в сервисе PatientService, задаем поведение метода findByPolicy в репозитории
     * patientRepository, если запускаем метод findByPolicy с входным параметром в виде любого объекта типа String,
     * он возвращает пустой объект типа Patient в обертке Optional,
     * запускаем метод в сервисе и с помощью captor'a отслеживаем, какой checkPolicy был передан при запуске
     * в метод findByPolicy сервиса
     */

    @Test
    void findByPolicyShouldReturnEmptyPatient() {
        Optional<Patient> emptyPatient = Optional.of(new Patient());
        when(patientRepository.findByPolicy(anyString())).thenReturn(emptyPatient);

        String checkPolicy = "123456789";
        patientService.findByPolicy(checkPolicy);
        ArgumentCaptor<String> captor = ArgumentCaptor.forClass(String.class);
        verify(patientRepository).findByPolicy(captor.capture());
        String argument = captor.getValue();

        Assertions.assertEquals(checkPolicy, argument);
    }

    /**
     * Метод тестирует метод findBySnils в сервисе PatientService, задаем поведение метода findBySnils в репозитории
     * patientRepository, если запускаем метод findBySnils с входным параметром в виде любого объекта типа String,
     * он возвращает пустой объект типа Patient в обертке Optional,
     * запускаем метод в сервисе и с помощью captor'a отслеживаем, какой checkSnils был передан при запуске
     * в метод findBySnils сервиса
     */

    @Test
    void findBySnilsShouldReturnEmptyPatient() {
        Optional<Patient> emptyPatient = Optional.of(new Patient());
        when(patientRepository.findBySnils(anyString())).thenReturn(emptyPatient);

        String checkSnils = "VI-OM 123456";
        patientService.findBySnils(checkSnils);
        ArgumentCaptor<String> captor = ArgumentCaptor.forClass(String.class);
        verify(patientRepository).findBySnils(captor.capture());
        String argument = captor.getValue();

        Assertions.assertEquals(checkSnils, argument);
    }

    /**
     * Метод тестирует метод findByInn в сервисе PatientService, задаем поведение метода findByInn в репозитории
     * patientRepository, если запускаем метод findByInn с входным параметром в виде любого объекта типа String,
     * он возвращает пустой объект типа Patient в обертке Optional,
     * запускаем метод в сервисе и с помощью captor'a отслеживаем, какой checkInn был передан при запуске
     * в метод findByInn сервиса
     */

    @Test
    void findByInnShouldReturnEmptyPatient() {
        Optional<Patient> emptyPatient = Optional.of(new Patient());
        when(patientRepository.findByInn(anyString())).thenReturn(emptyPatient);

        String checkInn = "701702999555";
        patientService.findByInn(checkInn);
        ArgumentCaptor<String> captor = ArgumentCaptor.forClass(String.class);
        verify(patientRepository).findByInn(captor.capture());
        String argument = captor.getValue();

        Assertions.assertEquals(checkInn, argument);
    }

}