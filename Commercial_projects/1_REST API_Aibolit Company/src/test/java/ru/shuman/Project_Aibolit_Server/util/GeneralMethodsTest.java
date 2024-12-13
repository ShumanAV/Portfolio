package ru.shuman.Project_Aibolit_Server.util;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.reflections.Reflections;
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Errors;
import ru.shuman.Project_Aibolit_Server.dto.*;
import ru.shuman.Project_Aibolit_Server.models.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Stream;

import static ru.shuman.Project_Aibolit_Server.util.GeneralMethods.checkingForErrorsAndThrowsException;

public class GeneralMethodsTest {

    private static BindingResult bindingResultEmpty;
    private static BindingResult bindingResultWithErrors;
    private static Errors errors;

    @BeforeAll
    public static void init() {
        //инициализируем пустой bindingResult без ошибок, в качестве таргетного класса будет выступать класс вызова врача
        bindingResultEmpty = new BeanPropertyBindingResult(new Calling(), "Calling");

        //инициализируем пустой bindingResult с ошибками, в качестве таргетного класса будет выступать класс вызова врача
        bindingResultWithErrors = new BeanPropertyBindingResult(new Calling(), "Calling");

        // создаем в bindingResultWithErrors ошибку для поля id в качестве примера,
        // теперь метод checkingForErrorsAndThrowsException должен выбрасывать исключение
        bindingResultWithErrors.rejectValue("id", "", "У объекта отсутствует id!");

        // создаем объект типа Errors с таргетным классом Calling
        errors = new BeanPropertyBindingResult(new Calling(), "Calling");
    }

    // Метод тестирует метод checkingForErrorsAndThrowsException на то, что не будет выброшено исключение в случае, когда
    // в bindingResultEmpty нет ошибок
    @ParameterizedTest
    @MethodSource("generateExceptions")
    public void checkingForErrorsAndThrowsExceptionTestWithoutExceptions(Class<? extends RuntimeException> clazz) {
        Assertions.assertDoesNotThrow(() -> {
            checkingForErrorsAndThrowsException(bindingResultEmpty, clazz);
        });
    }

    // Метод тестирует метод collectStringAboutErrors на то, что будет выброшено исключение в случае, когда
    // в bindingResultWithErrors есть хотя бы одна ошибка
    @ParameterizedTest
    @MethodSource("generateExceptions")
    public void checkingForErrorsAndThrowsExceptionTestWithException(Class<? extends RuntimeException> clazz) {
        // проверка на выброс исключения
        Assertions.assertThrows(clazz, () -> {
            checkingForErrorsAndThrowsException(bindingResultWithErrors, clazz);
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

    /*
    Метод тестирует метод searchNameFieldInParentEntity, на вход принимает название поля, которое мы ожидаем увидеть,
    название класса сущности, имя поля которой будем искать в родительской сущности - в таргентном классе errors - в данном
    случае - это CallingDTO
     */
    @ParameterizedTest
    @MethodSource("generateFields")
    public void searchNameFieldInParentEntityTest(String fieldName, Class<?> searchableFieldType) {
        Assertions.assertEquals(fieldName, GeneralMethods.searchNameFieldInParentEntity(errors, searchableFieldType));
    }

    /*
    Метод тестирует метод searchNameFieldInParentEntity на возврат null в случае,
    если данный метод не находит поле такого типа, на примере моделей Contract и TypeContract, т.к. их dto не являются
     дочерними сущностями таргетного класса CallingDTO
     */
    @Test
    public void searchNameFieldInParentEntityTestOnNull() {
        Assertions.assertNull(GeneralMethods.searchNameFieldInParentEntity(errors, Contract.class));
        Assertions.assertNull(GeneralMethods.searchNameFieldInParentEntity(errors, TypeContract.class));
    }

    /*
    Метод генерирует имена полей в родительской сущности на примере класса CallingDTO, а также его дочерних сущностей DTO, например,
    DoctorDTO имеет название поля doctor в CallingDTO, SpecializationDTO также через связь с DoctorDTO имеет название поля
    doctor и т.д.
     */
    public static Stream<Arguments> generateFields() {
        List<Arguments> out = new ArrayList<>();

        //сущность Doctor и ее дочерние сущности
        out.add(Arguments.arguments("doctor", Doctor.class));
        out.add(Arguments.arguments("doctor", Specialization.class));
        out.add(Arguments.arguments("doctor", Profile.class));
        out.add(Arguments.arguments("doctor", Role.class));

        //сущность карточка вызова
        out.add(Arguments.arguments("journal", Journal.class));

        //сущность прайс
        out.add(Arguments.arguments("price", Price.class));

        //сущность пациент и его дочерние сущности
        out.add(Arguments.arguments("patient", Patient.class));
        out.add(Arguments.arguments("patient", PlaceStudy.class));
        out.add(Arguments.arguments("patient", Document.class));
        out.add(Arguments.arguments("patient", TypeDoc.class));
        out.add(Arguments.arguments("patient", Address.class));
        out.add(Arguments.arguments("patient", Region.class));
        out.add(Arguments.arguments("patient", Blood.class));
        out.add(Arguments.arguments("patient", Gender.class));

        //сущность родитель и его дочерние сущности
        out.add(Arguments.arguments("patient", Parent.class));
        out.add(Arguments.arguments("patient", Document.class));
        out.add(Arguments.arguments("patient", Address.class));
        out.add(Arguments.arguments("patient", TypeRelationshipWithPatient.class));
        out.add(Arguments.arguments("patient", Education.class));
        out.add(Arguments.arguments("patient", Blood.class));
        out.add(Arguments.arguments("patient", TypeEmployment.class));
        out.add(Arguments.arguments("patient", Gender.class));

        return out.stream();
    }

    // Метод тестирует метод formClassDTO на соответствие класса модели и ее класса DTO
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
        out.add(Arguments.arguments(Doctor.class, DoctorDTO.class));
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

        return out.stream();
    }

    // Метод тестирует метод searchGetterName на соответствие названий геттеров
    @ParameterizedTest
    @MethodSource("generateGetterMethodsNames")
    public void searchGetterNameTest(String getterOfObjectOne, Object objectOne, Object objectTwo) {
        Assertions.assertEquals(getterOfObjectOne, GeneralMethods.searchGetterName(objectOne, objectTwo));
    }

    // Метод генерирует названия геттеров в соответствующих классах
    public static Stream<Arguments> generateGetterMethodsNames() {
        List<Arguments> out = new ArrayList<>();

        //примеры названия геттеров в objectOne возвращающий ObjectTwo
        out.add(Arguments.arguments("getRegion", new Address(), new Region()));
        out.add(Arguments.arguments("getTypeDoc", new Document(), new TypeDoc()));
        out.add(Arguments.arguments("getTypeContract", new Contract(), new TypeContract()));
        out.add(Arguments.arguments("getPrice", new Calling(), new Price()));
        out.add(Arguments.arguments("getRole", new Profile(), new Role()));

        //примеры названия геттеров в ObjectTwo возвращающий List<objectOne>
        out.add(Arguments.arguments("getAddresses", new Region(), new Address()));
        out.add(Arguments.arguments("getDocuments", new TypeDoc(), new Document()));
        out.add(Arguments.arguments("getContracts", new TypeContract(), new Contract()));
        out.add(Arguments.arguments("getCallings", new Price(), new Calling()));
        out.add(Arguments.arguments("getProfiles", new Role(), new Profile()));

        return out.stream();
    }

    // Метод тестирует метод searchSetterName на соответствие названий cеттеров
    @ParameterizedTest
    @MethodSource("generateSetterMethodsNames")
    public void searchSetterNameTest(String getterOfObjectOne, Object objectOne, Object objectTwo) {
        Assertions.assertEquals(getterOfObjectOne, GeneralMethods.searchSetterName(objectOne, objectTwo));
    }

    // Метод генерирует названия cеттеров в соответствующих классах
    public static Stream<Arguments> generateSetterMethodsNames() {
        List<Arguments> out = new ArrayList<>();

        //примеры названия cеттеров в ObjectTwo возвращающий List<objectOne>
        out.add(Arguments.arguments("setAddresses", new Region(), new Address()));
        out.add(Arguments.arguments("setDocuments", new TypeDoc(), new Document()));
        out.add(Arguments.arguments("setContracts", new TypeContract(), new Contract()));
        out.add(Arguments.arguments("setCallings", new Price(), new Calling()));
        out.add(Arguments.arguments("setProfiles", new Role(), new Profile()));

        return out.stream();
    }

    // Метод тестирует метод searchGetIdName на соответствие названий методов getId
    @ParameterizedTest
    @MethodSource("generateGetIdMethodsNames")
    public void searchSetterNameTest(String getIdMethodName, Object o) {
        Assertions.assertEquals(getIdMethodName, GeneralMethods.searchGetIdName(o));
    }

    // Метод генерирует названия методов getId в соответствующих классах
    public static Stream<Arguments> generateGetIdMethodsNames() {
        List<Arguments> out = new ArrayList<>();

        //примеры названия cеттеров в ObjectTwo возвращающий List<objectOne>
        out.add(Arguments.arguments("getId", new Address()));
        out.add(Arguments.arguments("getId", new Document()));
        out.add(Arguments.arguments("getId", new Contract()));
        out.add(Arguments.arguments("getId", new Calling()));
        out.add(Arguments.arguments("getId", new Profile()));

        return out.stream();
    }

    // Метод тестирует метод getNullPropertyNames на соответствие массивов с полями которые null в объектах
    @ParameterizedTest
    @MethodSource("generateNonNullProperties")
    public void getNullPropertyNamesTest(String[] arrayOfNullPropertyNames, Object src) {
        Assertions.assertArrayEquals(arrayOfNullPropertyNames, GeneralMethods.getNullPropertyNames(src));
    }

    // Метод генерирует строковые массивы с полями которые null в соответствующих классах
    public static Stream<Arguments> generateNonNullProperties() {
        List<Arguments> out = new ArrayList<>();

        //примеры строковых массивов с названиями полей которые null в объектах
        Blood blood = new Blood();
        blood.setId(1);
        out.add(Arguments.arguments(new String[] {"patients", "name", "parents"}, blood));

        Gender gender = new Gender();
        gender.setId(1);
        gender.setName("name");
        out.add(Arguments.arguments(new String[] {"patients", "parents"}, gender));

        Document document = new Document();
        document.setId(1);
        document.setName("name");
        out.add(Arguments.arguments(new String[] {"parent",  "patient", "typeDoc"}, document));

        Education education = new Education();
        education.setId(1);
        education.setName("name");
        out.add(Arguments.arguments(new String[] {"parents"}, education));

        TypeDoc typeDoc = new TypeDoc();
        typeDoc.setId(1);
        typeDoc.setName("name");
        typeDoc.setDocuments(new ArrayList<>());
        out.add(Arguments.arguments(new String[] {}, typeDoc));

        return out.stream();
    }

    // Метод тестирует метод copyNonNullProperties на соответствие полей в результирующем объекте, которые не null
    @ParameterizedTest
    @MethodSource("generateExampleTargetObjects")
    public void copyNonNullPropertiesTest(Object exampleResultTarget, Object src, Object target) {
        GeneralMethods.copyNonNullProperties(src, target);
        Assertions.assertEquals(exampleResultTarget, target);
    }

    // Метод генерирует объекты: пример результирующего целевого объекта, объект источник, целевой объект
    public static Stream<Arguments> generateExampleTargetObjects() {
        List<Arguments> out = new ArrayList<>();

        //примеры строковых массивов с названиями полей которые null в объектах
        Blood bloodResultTarget = new Blood();
        bloodResultTarget.setId(1);
        bloodResultTarget.setName("name");

        Blood bloodSrc = new Blood();
        bloodSrc.setId(1);
        bloodSrc.setName("name");

        Blood bloodTarget = new Blood();

        out.add(Arguments.arguments(bloodResultTarget, bloodSrc, bloodTarget));

        return out.stream();
    }
}
