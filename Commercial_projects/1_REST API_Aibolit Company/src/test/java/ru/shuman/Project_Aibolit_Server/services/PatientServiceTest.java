package ru.shuman.Project_Aibolit_Server.services;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import org.mockito.stubbing.Answer;
import ru.shuman.Project_Aibolit_Server.models.*;
import ru.shuman.Project_Aibolit_Server.repositories.PatientRepository;
import ru.shuman.Project_Aibolit_Server.util.GeneralMethods;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.*;

class PatientServiceTest {

    @Mock
    private PatientRepository patientRepository;
    @Mock
    private PlaceStudyService placeStudyService;
    @Mock
    private ParentService parentService;
    @Mock
    private DocumentService documentService;
    @Mock
    private AddressService addressService;
    @Mock
    private BloodService bloodService;
    @Mock
    private GenderService genderService;
    @InjectMocks
    private PatientService patientService;

    @BeforeEach
    public void init() {
        MockitoAnnotations.initMocks(this);
    }

    /**
     * Метод тестирует метод findById в сервисе PatientService, задаем поведение метода findById в репозитории
     * patientRepository, если запускаем метод findById с входным параметром в виде любого объекта типа Integer,
     * он возвращает объект типа Patient в обертке Optional, запускаем метод в сервисе и сравниваем результат
     * возврата из метода и mockPatient,
     * с помощью captor'a отслеживаем, какой id был передан при запуске в метод findById сервиса
     */

    @Test
    void findByIdShouldReturnPatient() {
        Optional<Patient> mockPatient = Optional.of(new Patient());
        when(patientRepository.findById(anyInt())).thenReturn(mockPatient);

        int patientId = 1;
        Optional<Patient> resultPatient = patientService.findById(patientId);

        Assertions.assertEquals(mockPatient, resultPatient);

        ArgumentCaptor<Integer> captor = ArgumentCaptor.forClass(Integer.class);
        verify(patientRepository).findById(captor.capture());
        int checkedId = captor.getValue();

        Assertions.assertEquals(patientId, checkedId);
    }

    /**
     * Метод тестирует метод findByPhone в сервисе PatientService, задаем поведение метода findByPhone в репозитории
     * patientRepository, если запускаем метод findByPhone с входным параметром в виде любого объекта типа String,
     * он возвращает пустой объект типа Patient в обертке Optional, запускаем метод в сервисе и сравниваем результат
     * возврата из метода и mockPatient,
     * с помощью captor'a отслеживаем, какой номер телефона был передан при запуске в метод findByPhone сервиса
     */

    @Test
    void findByPhoneShouldReturnPatient() {
        Optional<Patient> mockPatient = Optional.of(new Patient());
        when(patientRepository.findByPhone(anyString())).thenReturn(mockPatient);

        String patientPhone = "+79998887755";
        Optional<Patient> resultPatient = patientService.findByPhone(patientPhone);

        Assertions.assertEquals(mockPatient, resultPatient);

        ArgumentCaptor<String> captor = ArgumentCaptor.forClass(String.class);
        verify(patientRepository).findByPhone(captor.capture());
        String checkedPhone = captor.getValue();

        Assertions.assertEquals(patientPhone, checkedPhone);
    }

    /**
     * Метод тестирует метод findByEmail в сервисе PatientService, задаем поведение метода findByEmail в репозитории
     * patientRepository, если запускаем метод findByEmail с входным параметром в виде любого объекта типа String,
     * он возвращает пустой объект типа Patient в обертке Optional, запускаем метод в сервисе и сравниваем результат
     * возврата из метода и mockPatient,
     * с помощью captor'a отслеживаем, какой имэйл был передан при запуске в метод findByEmail сервиса
     */

    @Test
    void findByEmailShouldReturnPatient() {
        Optional<Patient> mockPatient = Optional.of(new Patient());
        when(patientRepository.findByEmail(anyString())).thenReturn(mockPatient);

        String patientEmail = "test@mail.ru";
        Optional<Patient> resultPatient = patientService.findByEmail(patientEmail);

        Assertions.assertEquals(mockPatient, resultPatient);

        ArgumentCaptor<String> captor = ArgumentCaptor.forClass(String.class);
        verify(patientRepository).findByEmail(captor.capture());
        String checkedEmail = captor.getValue();

        Assertions.assertEquals(patientEmail, checkedEmail);
    }

    /**
     * Метод тестирует метод findByPolicy в сервисе PatientService, задаем поведение метода findByPolicy в репозитории
     * patientRepository, если запускаем метод findByPolicy с входным параметром в виде любого объекта типа String,
     * он возвращает пустой объект типа Patient в обертке Optional, запускаем метод в сервисе и сравниваем результат
     * возврата из метода и mockPatient,
     * с помощью captor'a отслеживаем, какой номер полиса был передан при запуске в метод findByPolicy сервиса
     */

    @Test
    void findByPolicyShouldReturnPatient() {
        Optional<Patient> mockPatient = Optional.of(new Patient());
        when(patientRepository.findByPolicy(anyString())).thenReturn(mockPatient);

        String patientPolicy = "123456789";
        Optional<Patient> resultPatient = patientService.findByPolicy(patientPolicy);

        Assertions.assertEquals(mockPatient, resultPatient);

        ArgumentCaptor<String> captor = ArgumentCaptor.forClass(String.class);
        verify(patientRepository).findByPolicy(captor.capture());
        String checkedPolicy = captor.getValue();

        Assertions.assertEquals(patientPolicy, checkedPolicy);
    }

    /**
     * Метод тестирует метод findBySnils в сервисе PatientService, задаем поведение метода findBySnils в репозитории
     * patientRepository, если запускаем метод findBySnils с входным параметром в виде любого объекта типа String,
     * он возвращает пустой объект типа Patient в обертке Optional, запускаем метод в сервисе и сравниваем результат
     * возврата из метода и mockPatient,
     * с помощью captor'a отслеживаем, какой номер СНИЛС был передан при запуске в метод findBySnils сервиса
     */

    @Test
    void findBySnilsShouldReturnPatient() {
        Optional<Patient> mockPatient = Optional.of(new Patient());
        when(patientRepository.findBySnils(anyString())).thenReturn(mockPatient);

        String patientSnils = "VI-OM 123456";
        Optional<Patient> resultPatient = patientService.findBySnils(patientSnils);

        Assertions.assertEquals(mockPatient, resultPatient);

        ArgumentCaptor<String> captor = ArgumentCaptor.forClass(String.class);
        verify(patientRepository).findBySnils(captor.capture());
        String checkedSnils = captor.getValue();

        Assertions.assertEquals(patientSnils, checkedSnils);
    }

    /**
     * Метод тестирует метод findByInn в сервисе PatientService, задаем поведение метода findByInn в репозитории
     * patientRepository, если запускаем метод findByInn с входным параметром в виде любого объекта типа String,
     * он возвращает пустой объект типа Patient в обертке Optional, запускаем метод в сервисе и сравниваем результат
     * возврата из метода и mockPatient,
     * с помощью captor'a отслеживаем, какой номер ИНН был передан при запуске в метод findByInn сервиса
     */

    @Test
    void findByInnShouldReturnPatient() {
        Optional<Patient> mockPatient = Optional.of(new Patient());
        when(patientRepository.findByInn(anyString())).thenReturn(mockPatient);

        String patientInn = "701702999555";
        Optional<Patient> resultPatient = patientService.findByInn(patientInn);

        Assertions.assertEquals(mockPatient, resultPatient);

        ArgumentCaptor<String> captor = ArgumentCaptor.forClass(String.class);
        verify(patientRepository).findByInn(captor.capture());
        String checkedInn = captor.getValue();

        Assertions.assertEquals(patientInn, checkedInn);
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

        List<Patient> resultList = patientService.findAll();

        Assertions.assertEquals(emptyList, resultList);
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

        List<Patient> resultList = patientService.findAllByPublished(true);

        Assertions.assertEquals(emptyList, resultList);
    }

    /**
     * Метод тестирует метод search в сервисе PatientService, задаем поведение методов:
     * findByFirstnameStartingWith, findByLastnameStartingWith, findByPhoneContaining в репозитории
     * patientRepository, если запускаем данные методы, каждый из них возвращает пустой лист, параметризиpованный Patient,
     * запускаем метод в сервисе и сравниваем, то что возвращено и пустой лист
     */

    @Test
    void searchShouldReturnEmptyList() {
        List<Patient> emptyList = new ArrayList<>();

        String textSearch = "Ivan";
        when(patientRepository.findByFirstnameStartingWith(textSearch)).thenReturn(emptyList);
        when(patientRepository.findByLastnameStartingWith(textSearch)).thenReturn(emptyList);
        when(patientRepository.findByPhoneContaining(textSearch)).thenReturn(emptyList);

        List<Patient> resultList = patientService.search(textSearch);

        Assertions.assertEquals(emptyList, resultList);
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
    void createShouldBeRunning() {
        PlaceStudy emptyPlaceStudy = new PlaceStudy();
        Document emptyDocument = new Document();
        Address emptyAddress = new Address();

        doNothing().when(placeStudyService).create(emptyPlaceStudy);
        doNothing().when(documentService).create(emptyDocument);
        doNothing().when(addressService).create(emptyAddress);

        Patient mockPatient = new Patient();
        mockPatient.setPlaceStudy(emptyPlaceStudy);
        mockPatient.setDocument(emptyDocument);
        mockPatient.setAddress(emptyAddress);
        mockPatient.setParents(new ArrayList<>());

        patientService.create(mockPatient);

        verify(patientRepository).save(mockPatient);
    }

    /**
     * Метод тестирует метод create в сервисе PatientService, при сохранении пациента осуществляются запуски методов
     * create следующих сервисов: PlaceStudyService, DocumentService, AddressService, поэтому при помощи их моков
     * задаем поведение этим методам ничего не делать, также при сохранении пациента должны заполняться следующие
     * поля у пациента: CreatedAt, UpdatedAt, а также для кэша patient у следующих полей: placeStudy, document, address,
     * поэтому проверяем не null ли они
     */

    @Test
    void createShouldFieldsNotNull() {
        PlaceStudy emptyPlaceStudy = new PlaceStudy();
        Document emptyDocument = new Document();
        Address emptyAddress = new Address();

        doNothing().when(placeStudyService).create(emptyPlaceStudy);
        doNothing().when(documentService).create(emptyDocument);
        doNothing().when(addressService).create(emptyAddress);

        Patient mockPatient = new Patient();
        mockPatient.setPlaceStudy(emptyPlaceStudy);
        mockPatient.setDocument(emptyDocument);
        mockPatient.setAddress(emptyAddress);
        mockPatient.setParents(new ArrayList<>());

        patientService.create(mockPatient);

        Assertions.assertNotNull(mockPatient.getCreatedAt());
        Assertions.assertNotNull(mockPatient.getUpdatedAt());
        Assertions.assertNotNull(mockPatient.getPlaceStudy().getPatient());
        Assertions.assertNotNull(mockPatient.getDocument().getPatient());
        Assertions.assertNotNull(mockPatient.getAddress().getPatient());
    }

    /**
     * Метод тестирует метод addCallingAtListForPatient, который принимает на вход параметры типа Calling и Patient,
     * если в метод addCallingAtListForPatient передаем новый пустой объект типа Calling и Patient, то проверяем, что был
     * вызван метод GeneralMethods.addObjectOneInListForObjectTwo с любыми входными параметрами
     */

    @Test
    void addCallingAtListForPatientShouldRunningGeneralMethods() {
        try (MockedStatic<GeneralMethods> mock = Mockito.mockStatic(GeneralMethods.class)) {
            mock.when(() -> GeneralMethods.addObjectOneInListForObjectTwo(any(), any(), any())).
                    thenAnswer((Answer<Void>) invocation -> null);

            patientService.addCallingAtListForPatient(new Calling(), new Patient());

            mock.verify(() -> GeneralMethods.addObjectOneInListForObjectTwo(any(), any(), any()));
        }
    }

    /**
     * Метод тестирует метод addContractAtListForPatient, который принимает на вход параметры типа Contract и Patient,
     * если в метод addContractAtListForPatient передаем новый пустой объект типа Contract и Patient, то проверяем, что был
     * вызван метод GeneralMethods.addObjectOneInListForObjectTwo с любыми входными параметрами
     */

    @Test
    void addContractAtListForPatientShouldRunningGeneralMethods() {
        try (MockedStatic<GeneralMethods> mock = Mockito.mockStatic(GeneralMethods.class)) {
            mock.when(() -> GeneralMethods.addObjectOneInListForObjectTwo(any(), any(), any())).
                    thenAnswer((Answer<Void>) invocation -> null);
            patientService.addContractAtListForPatient( new Contract(), new Patient());

            ArgumentCaptor<Patient> captor = ArgumentCaptor.forClass(Patient.class);

            mock.verify(() -> GeneralMethods.addObjectOneInListForObjectTwo(any(), captor.capture(), any()));
            System.out.println(captor.getValue());
        }
    }

}