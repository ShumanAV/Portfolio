package ru.shuman.Project_Aibolit_Server.services;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import ru.shuman.Project_Aibolit_Server.models.*;
import ru.shuman.Project_Aibolit_Server.repositories.PatientRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.*;

class PatientServiceTest {

    private final PatientRepository patientRepository;
    private final PlaceStudyService placeStudyService;
    private final ParentService parentService;
    private final DocumentService documentService;
    private final AddressService addressService;
    private final BloodService bloodService;
    private final GenderService genderService;
    private final PatientService patientService;

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

    /**
     * Метод тестирует метод findAll в сервисе PatientService, задаем поведение метода findAll в репозитории
     * patientRepository, если запускаем метод findAll, он возвращает пустой лист, параметризированный Patient,
     * запускаем метод в сервисе и сравниваем, то что возвращено и пустой лист
     */

    @Test
    void findAllShouldReturnEmptyList() {
        List<Patient> emptyList = new ArrayList<>();
        when(patientRepository.findAll()).thenReturn(emptyList);

        Assertions.assertEquals(emptyList, patientService.findAll());
    }

    /**
     * Метод тестирует метод findAllByPublished в сервисе PatientService, задаем поведение метода findByPublished в репозитории
     * patientRepository, если запускаем метод findByPublished, он возвращает пустой лист, параметризированный Patient,
     * запускаем метод в сервисе и сравниваем, то что возвращено и пустой лист
     */

    @Test
    void findAllByPublishedShouldReturnEmptyList() {
        List<Patient> emptyList = new ArrayList<>();
        when(patientRepository.findByPublished(true)).thenReturn(emptyList);

        Assertions.assertEquals(emptyList, patientService.findAllByPublished(true));
    }

    /**
     * Метод тестирует метод search в сервисе PatientService, задаем поведение методов:
     * findByFirstnameStartingWith, findByLastnameStartingWith, findByPhoneContaining в репозитории
     * patientRepository, если запускаем данные методы, каждый из них возвращает пустой лист, параметризированный Patient,
     * запускаем метод в сервисе и сравниваем, то что возвращено и пустой лист
     */

    @Test
    void searchShouldReturnEmptyList() {
        List<Patient> emptyList = new ArrayList<>();

        String textSearch = "Ivan";
        when(patientRepository.findByFirstnameStartingWith(textSearch)).thenReturn(emptyList);
        when(patientRepository.findByLastnameStartingWith(textSearch)).thenReturn(emptyList);
        when(patientRepository.findByPhoneContaining(textSearch)).thenReturn(emptyList);

        Assertions.assertEquals(emptyList, patientService.search(textSearch));
    }

    /**
     * Метод тестирует метод search в сервисе PatientService, задаем поведение методов:
     * findByFirstnameStartingWith, findByLastnameStartingWith, findByPhoneContaining в репозитории
     * patientRepository, если запускаем данные методы, каждый из них возвращает лист с тремя записями,
     * параметризированный Patient, запускаем метод в сервисе и сравниваем размер возвращенного списка,
     * должно получиться 9 записей
     */

    @Test
    void searchShouldReturnNotEmptyList() {
        List<Patient> notEmptyList = new ArrayList<>();
        notEmptyList.add(new Patient());
        notEmptyList.add(new Patient());
        notEmptyList.add(new Patient());

        String textSearch = "Ivan";
        when(patientRepository.findByFirstnameStartingWith(textSearch)).thenReturn(notEmptyList);
        when(patientRepository.findByLastnameStartingWith(textSearch)).thenReturn(notEmptyList);
        when(patientRepository.findByPhoneContaining(textSearch)).thenReturn(notEmptyList);

        int sizeTotalOfList = 9;
        Assertions.assertEquals(sizeTotalOfList, patientService.search(textSearch).size());
    }

    /**
     * Метод тестирует метод create в сервисе PatientService, т.к. он void, проверяем факт запуска мока
     * patientRepository и метода в нем save(emptyPatient), при сохранении пациента осуществляются запуски методов
     * create следующих сервисов: PlaceStudyService, DocumentService, AddressService, поэтому при помощи их моков
     * задаем поведение этим методам ничего не делать
     */

    @Test
    void createShouldBeRunningPatientRepository() {
        PlaceStudy emptyPlaceStudy = new PlaceStudy();
        Document emptyDocument = new Document();
        Address emptyAddress = new Address();

        doNothing().when(placeStudyService).create(emptyPlaceStudy);
        doNothing().when(documentService).create(emptyDocument);
        doNothing().when(addressService).create(emptyAddress);

        Patient emptyPatient = new Patient();
        emptyPatient.setPlaceStudy(emptyPlaceStudy);
        emptyPatient.setDocument(emptyDocument);
        emptyPatient.setAddress(emptyAddress);
        emptyPatient.setParents(new ArrayList<>());

        when(patientRepository.save(emptyPatient)).thenReturn(emptyPatient);
        patientService.create(emptyPatient);

        verify(patientRepository).save(emptyPatient);
    }

    /**
     * Метод тестирует метод create в сервисе PatientService, при сохранении пациента осуществляются запуски методов
     * create следующих сервисов: PlaceStudyService, DocumentService, AddressService, поэтому при помощи их моков
     * задаем поведение этим методам ничего не делать, также при сохранении пациента должны заполняться следующие
     * поля у пациента: CreatedAt, UpdatedAt, а также для кэша patient у следующих полей: placeStudy, document, address,
     * поэтому проверяем не null ли они
     */

    @Test
    void createCheckFieldsNotNull() {
        PlaceStudy emptyPlaceStudy = new PlaceStudy();
        Document emptyDocument = new Document();
        Address emptyAddress = new Address();

        doNothing().when(placeStudyService).create(emptyPlaceStudy);
        doNothing().when(documentService).create(emptyDocument);
        doNothing().when(addressService).create(emptyAddress);

        Patient emptyPatient = new Patient();
        emptyPatient.setPlaceStudy(emptyPlaceStudy);
        emptyPatient.setDocument(emptyDocument);
        emptyPatient.setAddress(emptyAddress);
        emptyPatient.setParents(new ArrayList<>());

        when(patientRepository.save(emptyPatient)).thenReturn(emptyPatient);
        patientService.create(emptyPatient);

        Assertions.assertNotNull(emptyPatient.getCreatedAt());
        Assertions.assertNotNull(emptyPatient.getUpdatedAt());
        Assertions.assertNotNull(emptyPatient.getPlaceStudy().getPatient());
        Assertions.assertNotNull(emptyPatient.getDocument().getPatient());
        Assertions.assertNotNull(emptyPatient.getAddress().getPatient());
    }

}