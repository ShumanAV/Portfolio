package ru.shuman.Project_Aibolit_Server.util;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.Mock;
import org.reflections.Reflections;
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Errors;
import ru.shuman.Project_Aibolit_Server.dto.*;
import ru.shuman.Project_Aibolit_Server.models.*;
import ru.shuman.Project_Aibolit_Server.repositories.PatientRepository;
import ru.shuman.Project_Aibolit_Server.services.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Stream;

public class GeneralMethodsTest {

    private static BindingResult bindingResultEmpty;
    private static BindingResult bindingResultWithErrors;
    private static Errors errors;

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

    private static PatientService patientService;

//    @Autowired
    private GeneralMethodsTest () {
//        this.patientRepository = patientRepository;
//        this.placeStudyService = placeStudyService;
//        this.parentService = parentService;
//        this.typeDocService = typeDocService;
//        this.documentService = documentService;
//        this.addressService = addressService;
//        this.bloodService = bloodService;
//        this.genderService = genderService;


        this.patientService = new PatientService(patientRepository, placeStudyService, parentService,
                documentService, addressService, bloodService, genderService);
    }

    @BeforeAll
    public static void init() {
        bindingResultEmpty = new BeanPropertyBindingResult(new Calling(), "Calling");

        bindingResultWithErrors = new BeanPropertyBindingResult(new Calling(), "Calling");

        // создаем в bindingResultWithErrors ошибку для поля id в качестве примера,
        // теперь метод collectStringAboutErrors должен выбрасывать исключение
        bindingResultWithErrors.rejectValue("id", "", "У объекта отсутствует id!");

        // создаем объект типа Errors с таргетным классом Calling
        errors = new BeanPropertyBindingResult(new Calling(), "Calling");
    }

    // Метод тестирует метод collectStringAboutErrors на то, что не будет выброшено исключение в случае, когда
    // в bindingResultEmpty нет ошибок
    @ParameterizedTest
    @MethodSource("generateExceptions")
    public void collectStringAboutErrorsTestWithoutExceptions(Class<? extends RuntimeException> clazz) {
        Assertions.assertDoesNotThrow(() -> {
            GeneralMethods.checkingForErrorsAndThrowsException(bindingResultEmpty, clazz);
        });
    }

    // Метод тестирует метод collectStringAboutErrors на то, что будет выброшено исключение в случае, когда
    // в bindingResultWithErrors есть хотя бы одна ошибка
    @ParameterizedTest
    @MethodSource("generateExceptions")
    public void collectStringAboutErrorsTest(Class<? extends RuntimeException> clazz) {
        // проверка на выброс исключения
        Assertions.assertThrows(clazz, () -> {
            GeneralMethods.checkingForErrorsAndThrowsException(bindingResultWithErrors, clazz);
        });
    }

    // Метод подготавливает данные в виде списка классов Exception
    // из пакета "ru.shuman.Project_Aibolit_Server.util.exceptions" для вышестоящих методов
    public static Stream<Arguments> generateExceptions() {
        List<Arguments> out = new ArrayList<>();

        Reflections reflections = new Reflections("ru.shuman.Project_Aibolit_Server.util.exceptions");
        Set<Class<? extends RuntimeException>> allClassesInPackage = reflections.getSubTypesOf(RuntimeException.class);
        for (Class<? extends RuntimeException> clazz : allClassesInPackage) {
            out.add(Arguments.arguments(clazz));
        }

        return out.stream();
    }

    // Метод тестирует метод searchNameFieldInTargetClass
    @ParameterizedTest
    @MethodSource("generateFields")
    public void searchNameFieldInTargetClassTest(String fieldName, Class<?> searchableFieldType) {
        Assertions.assertEquals(fieldName, GeneralMethods.searchNameFieldInParentEntity(errors, searchableFieldType));
    }

    // Метод тестирует метод searchNameFieldInTargetClass на возврат null в случае,
    // если метод searchNameFieldInTargetClass не находит поле такого типа,
    // на примере моделей Contract и TypeContract, т.к. их dto не являются дочерними сущностями
    // таргетного класса CallingDTO
    @Test
    public void searchNameFieldInTargetClassTestOnNull() {
        Assertions.assertNull(GeneralMethods.searchNameFieldInParentEntity(errors, Contract.class));
        Assertions.assertNull(GeneralMethods.searchNameFieldInParentEntity(errors, TypeContract.class));
    }

    // Метод генерирует имена полей и их типы - DTO на примере класса CallingDTO, а также его дочерних сущностей DTO
    public static Stream<Arguments> generateFields() {
        List<Arguments> out = new ArrayList<>();

        out.add(Arguments.arguments("doctor", Doctor.class));
        out.add(Arguments.arguments("doctor", Specialization.class));
        out.add(Arguments.arguments("doctor", Profile.class));
        out.add(Arguments.arguments("doctor", Role.class));

        out.add(Arguments.arguments("diary", Journal.class));

        out.add(Arguments.arguments("price", Price.class));

        out.add(Arguments.arguments("patient", Patient.class));

        out.add(Arguments.arguments("patient", PlaceStudy.class));
        out.add(Arguments.arguments("patient", Document.class));
        out.add(Arguments.arguments("patient", Address.class));
        out.add(Arguments.arguments("patient", Blood.class));
        out.add(Arguments.arguments("patient", Gender.class));
        out.add(Arguments.arguments("patient", Parent.class));

        out.add(Arguments.arguments("patient", Document.class));
        out.add(Arguments.arguments("patient", Address.class));
        out.add(Arguments.arguments("patient", TypeRelationshipWithPatient.class));
        out.add(Arguments.arguments("patient", Education.class));
        out.add(Arguments.arguments("patient", Blood.class));
        out.add(Arguments.arguments("patient", TypeEmployment.class));
        out.add(Arguments.arguments("patient", Gender.class));

        out.add(Arguments.arguments("patient", TypeDoc.class));

        out.add(Arguments.arguments("patient", Region.class));

        return out.stream();
    }

    // Метод тестирует метод formClassDTO на соответствие класса модели и класса DTO для нее данной модели
    @ParameterizedTest
    @MethodSource("generateClassesModelsAndDTOs")
    public void formClassDTOTest(Class<?> model, Class<?> dto) {
        Assertions.assertEquals(dto, GeneralMethods.formClassDTO(model));
    }

    // Метод генерирует классы моделей и им соответствующие классы DTO
    public static Stream<Arguments> generateClassesModelsAndDTOs() {
        List<Arguments> out = new ArrayList<>();

        out.add(Arguments.arguments(Address.class, AddressDTO.class));
        out.add(Arguments.arguments(Blood.class, BloodDTO.class));
        out.add(Arguments.arguments(Calling.class, CallingDTO.class));
        out.add(Arguments.arguments(Contract.class, ContractDTO.class));
        out.add(Arguments.arguments(Document.class, DocumentDTO.class));
        out.add(Arguments.arguments(Education.class, EducationDTO.class));
        out.add(Arguments.arguments(Gender.class, GenderDTO.class));
        out.add(Arguments.arguments(Parent.class, ParentDTO.class));
        out.add(Arguments.arguments(Patient.class, PatientDTO.class));
        out.add(Arguments.arguments(PlaceStudy.class,PlaceStudyDTO.class));
        out.add(Arguments.arguments(Price.class, PriceDTO.class));
        out.add(Arguments.arguments(Profile.class, ProfileDTO.class));
        out.add(Arguments.arguments(Region.class, RegionDTO.class));
        out.add(Arguments.arguments(Role.class, RoleDTO.class));
        out.add(Arguments.arguments(Specialization.class, SpecializationDTO.class));
        out.add(Arguments.arguments(TypeContract.class, TypeContractDTO.class));
        out.add(Arguments.arguments(TypeDoc.class, TypeDocDTO.class));
        out.add(Arguments.arguments(TypeEmployment.class, TypeEmploymentDTO.class));
        out.add(Arguments.arguments(TypeRelationshipWithPatient.class, TypeRelationshipWithPatientDTO.class));
        out.add(Arguments.arguments(Doctor.class, DoctorDTO.class));

        return out.stream();
    }

    @ParameterizedTest
    @MethodSource("generateObjectOfModels")
    public void addObjectOneInListForObjectTwoTest(Patient patientWithParentForAssert, Parent parent,
                                                   Patient patientWithoutParent, PatientService patientService) {
        GeneralMethods.addObjectOneInListForObjectTwo(parent, patientWithoutParent, patientService);
        Assertions.assertEquals(patientWithParentForAssert.getParents(), patientWithoutParent.getParents());
    }

    public static Stream<Arguments> generateObjectOfModels() {

        List<Arguments> out = new ArrayList<>();

        Optional<Patient> patientWithoutParent = patientService.findById(1);
        Optional<Patient> patientWithParentForAssert = patientService.findById(1);
        if (patientWithoutParent.isEmpty() || patientWithParentForAssert.isEmpty()) {
            return null;
        }

        Parent parent = new Parent();
        patientWithParentForAssert.get().getParents().add(parent);

        out.add(Arguments.arguments(patientWithParentForAssert, parent, patientWithoutParent, patientService));

        return out.stream();
    }

}
