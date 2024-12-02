package ru.shuman.Project_Aibolit_Server.util;

import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Errors;
import org.springframework.validation.FieldError;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/*
Класс GeneralMethods содержит статические методы, которые используются многократно по коду.
 */

public class GeneralMethods {

//    private static final Logger LOGGER = LogManager.getLogger(GeneralMethods.class);

    /*
    Метод выполняет проверку на наличие ошибок в bindingResult,
    в случае наличия ошибок формирует строку ошибок в виде StringBuilder и
    выбрасывает исключение разных типов с сообщением об ошибках в виде данной строки,
    в зависимости от того, какой тип исключения поступит на вход,
    на входе тип исключений ограничен RuntimeException.
     */

    public static void collectErrorsToString(BindingResult bindingResult, Class<? extends RuntimeException> exception) {

        try {
            if (bindingResult.hasErrors()) {

                StringBuilder errorMsg = new StringBuilder();

                List<FieldError> errors = bindingResult.getFieldErrors();
                for (FieldError error : errors) {
                    errorMsg.append(error.getField())
                            .append(" - ")
                            .append(error.getDefaultMessage() == null ? error.getCode() : error.getDefaultMessage())
                            .append("; ");
                }
                throw exception.getConstructor(String.class).newInstance(errorMsg.toString());
            }
        } catch (NoSuchMethodException | InvocationTargetException | InstantiationException |
                 IllegalAccessException e) {
            e.printStackTrace();
        }
    }

    /*
    Данный метод осуществляет поиск названия поля для указания ошибки в валидаторе в родительской сущности
    (ниже описан пример для понимания), в таргетном классе errors искомого типа, из которого запускается данный метод,
    либо такого типа, который в последовательности связей классов DTO через поля содержит искомый тип.

    Для удобства, если таргетный класс это не класс DTO, а класс модели и searchableFieldType тоже класс модели приходит
    по умолчанию из валидатора, они оба переводятся в аналогичные классы DTO. Сделано это для того, чтобы упростить код и
    избежать зацикленности связей в моделях, в DTO этого нет.

    Например, идет создание вызова врача CallingDTO, при этом осуществляется валидация по цепочке всех дочерних сущностей DTO,
    с которыми есть связь у CallingDTO, например, DoctorDTO, SpecializationDTO, ProfileDTO, RoleDTO и т.д.,
    при валидации RoleDTO в соответствующем валидаторе при наличии ошибок необходимо указать
    название поля, в котором ошибка, причем название поля из родительской сущности - СallingDTO, через которое
    осуществляется связь RoleDTO и СallingDTO, и в данном случае это будет поле с названием "doctor",
    т.к. данное поле имеет тип DoctorDTO, а он в свою очередь имеет
    поле "profile" типа ProfileDTO, а оно в свою очередь имеет поле искомого типа "role" типа RoleDTO.

    На вход данный метод принимает объект errors типа Errors и тип искомого поля типа CLass - класс модели из валидатора.

    Данный метод возвращает название такого поля в таргетном классе.
    В случае когда создается непосредственно сам подобъект, не из главной сущности, например Specialization,
    и в этом случае таргетный класс в errors будет совпадать с типом искомого поля, это будет в обоих случаях с учетом
    преобразования SpecializationDTO, в этом случае метод возвращает null.
     */

    public static String searchNameFieldInParentEntity(Errors errors, Class<?> searchableFieldType) {

        final String PACKAGE_DTO = "ru.shuman.Project_Aibolit_Server.dto";

        if (!searchableFieldType.getPackageName().equals(PACKAGE_DTO)) {
            searchableFieldType = formClassDTO(searchableFieldType);
        }

        Class<?> targetClass = ((BeanPropertyBindingResult) errors).getTarget().getClass();
        if (!targetClass.getPackageName().equals(PACKAGE_DTO)) {
            targetClass = formClassDTO(targetClass);
        }

        if (targetClass.equals(searchableFieldType)) {
            return null;
        }

        class Container {
            public final Class<?> fieldType;
            public final String fieldName;
            public final int fieldNumber;

            public Container(Class<?> fieldType, String fieldName, int fieldNumber) {
                this.fieldType = fieldType;
                this.fieldName = fieldName;
                this.fieldNumber = fieldNumber;
            }
        }

        List<Container> sequence = new ArrayList<>();
        sequence.add(new Container(targetClass, "", 0));

        boolean breakMainCycle = false;

        int level = 0;
        int fieldNumberAtLevelAbove = 0;
        while (!breakMainCycle) {

            int length = targetClass.getDeclaredFields().length;
            int currentFieldNumber = 0;
            for (Field field : targetClass.getDeclaredFields()) {

                currentFieldNumber += 1;

                if (fieldNumberAtLevelAbove == length && level > 0) {
                    targetClass = sequence.get(level - 1).fieldType;
                    fieldNumberAtLevelAbove = sequence.get(level).fieldNumber;
                    sequence.remove(level);
                    level -= 1;
                    break;

                } else if (fieldNumberAtLevelAbove == length && level == 0) {
                    breakMainCycle = true;
                    break;
                }

                if (currentFieldNumber < fieldNumberAtLevelAbove + 1) {
                    continue;
                }

                Class<?> currentTypeField = field.getType();
                if (currentTypeField.equals(List.class)) {
                    currentTypeField = (Class<?>) ((ParameterizedType) field.getGenericType()).getActualTypeArguments()[0];
                }

                if (currentTypeField.getPackageName().equals(PACKAGE_DTO)) {

                    level += 1;
                    sequence.add(new Container(currentTypeField, field.getName(), currentFieldNumber));

                    if (currentTypeField.equals(searchableFieldType)) {
                        return sequence.get(1).fieldName;
                    }

                    targetClass = currentTypeField;
                    fieldNumberAtLevelAbove = 0;
                    break;

                }

                if (currentFieldNumber == length && level > 0) {
                    targetClass = sequence.get(level - 1).fieldType;
                    fieldNumberAtLevelAbove = sequence.get(level).fieldNumber;
                    sequence.remove(level);
                    level -= 1;
                    break;
                }
            }
        }
        return null;
    }

    /*
    Метод формирует из названия класса модели аналогичное название класса DTO

    На вход приходит класс модели, на выходе класс DTO аналогичный классу модели
     */

    public static Class<?> formClassDTO(Class<?> aClass) {
        final String PACKAGE_DTO = "ru.shuman.Project_Aibolit_Server.dto";
        final String DTO = "DTO";
        try {
            aClass = Class.forName(PACKAGE_DTO + "." + aClass.getSimpleName() + DTO);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return aClass;
    }

    /*
    Метод добавляет первый объект Модели objectOne в List<objectOne> для второго объекта Модели objectTwo, делается это
    для актуализации кэша при сохранении или обновлении объектов в БД, когда отношение objectOne к objectTwo -
    многие к одному.

    Пример для понимания, добавляем новый вызов врача, указываем для вызова существующий прайс, т.к. тип отношения
    прайса к вывозам 1 ко многим, то у вызова будет уже выбранный прайс, а вот у прайса в списке вызовов не будет этого
    нового вызова, вот в таких случаях когда отношение многие к одному мы и добавляем в список новый объект.

    На вход поступает два объекта Моделей objectOne и objectTwo, а также объект сервисного класса второго объекта objectTwo.

    Объект сервисного класса второго объекта нужен для поиска в БД существующего объекта objectTwo с существующим списком
    объектов List<objectOne>.
    Существующий список объектов List<objectOne>, содержащихся в существующем объекте objectTwo из БД, записываем в
    objectTwo пришедший из JSON, далее работаем с ним, проверяем содержится ли в нем уже объект objectOne,
    если содержится обновляем на objectOne пришедший из JSON, перезаписывая его в списке, если не содержится, добавляем.
     */
    public static void addObjectOneInListForObjectTwo(Object objectOne, Object objectTwo, Object instanceOfServiceClassObjectTWO) {

        try {
            // найдем геттер в objectOne возвращающий ObjectTwo
            Method getterObjectOne = objectOne.getClass().getMethod(searchGetterName(objectOne, objectTwo));

            // найдем getter в ObjectTwo возвращающий List<objectOne>
            Method getterObjectTwo = objectTwo.getClass().getMethod(searchGetterName(objectTwo, objectOne));

            // найдем setter в ObjectTwo устанавливающий List<objectOne>
            Method setterObjectTwo = objectTwo.getClass().getMethod(searchSetterName(objectTwo, objectOne), List.class);

            // проверим при помощи геттера не равно ли null поле типа ObjectTwo в objectOne
            if (getterObjectOne.invoke(objectOne) != null) {

                // найдем метод getId() для ObjectOne и objectTwo
                Method getIdObjectOne = objectOne.getClass().getMethod(searchGetIdName(objectOne));
                Method getIdObjectTwo = objectTwo.getClass().getMethod(searchGetIdName(objectTwo));

                // получим id для объекта ObjectTwo
                Object idObjectTwo = getIdObjectTwo.invoke(objectTwo);

                // получим метод findById из сервисного класса objectTwo
                final String findById = "findById";
                Method findByIdServiceObjectTwo = instanceOfServiceClassObjectTWO.getClass().getMethod(findById, idObjectTwo.getClass());

                // проверим не равен ли id для ObjectTwo null
                if (idObjectTwo != null) {

                    // получаем существующий объект ObjectTwo из БД по id со списком объектов List<ObjectOne>
                    Optional<?> existingObjectTwo = (Optional<?>) findByIdServiceObjectTwo.invoke(instanceOfServiceClassObjectTWO, idObjectTwo);

                    // проверяем не пустой ли существующий объект ObjectTwo из БД
                    if (existingObjectTwo.isPresent()) {
                        // устанавливаем при помощи сеттера List<ObjectOne> у существующего объекта ObjectTwo из БД в ObjectTwo из JSON
                        setterObjectTwo.invoke(objectTwo, getterObjectTwo.invoke(existingObjectTwo.get()));
                    }
                }

                // проверяем не равен ли null List<ObjectOne> в ObjectTwo, и если равен null,
                // то при помощи сеттера устанавливаем new ArrayList<>()
                if (getterObjectTwo.invoke(objectTwo) == null) {
                    setterObjectTwo.invoke(objectTwo, new ArrayList<>());

                } else {
                    // если List<ObjectOne> в ObjectTwo не равен null, получаем список объектов List<ObjectOne> в ObjectTwo
                    List<?> listObjectsOne = (List<?>) getterObjectTwo.invoke(objectTwo);

                    // проверяем по id нет ли уже в этом списке объекта ObjectOne
                    for (int i = 0; i < listObjectsOne.size(); i++) {
                        if (getIdObjectOne.invoke(objectOne) == getIdObjectOne.invoke(listObjectsOne.get(i))) {
                            // если ObjectOne в этом списке уже есть, то мы его заменяем на измененный ObjectOne из JSON
                            ((List<Object>) getterObjectTwo.invoke(objectTwo)).set(i, objectOne);
                        } else {
                            // проверяем если id не равны и это последний элемент, т.е. это значит что в этом списке
                            // нет такого объекта как ObjectOne, в этом случае добавляем его в этот список
                            if (i == listObjectsOne.size() - 1) {
                                ((List<Object>) getterObjectTwo.invoke(objectTwo)).add(objectOne);
                            }
                        }
                    }
                }
            }
        } catch (NoSuchMethodException | InvocationTargetException | IllegalAccessException e) {
            e.printStackTrace();
        }
    }

    /*
    Метод находит и возвращает название метода геттера, который содержится в первом объекте objectOne Модели и возвращает
    второй объект objectTwo, второй Модели

    На вход поступает два объекта Моделей objectOne и objectTwo

    Геттер находим по типу возвращаемого значения, в случае если тип возвращаемого значения List<objectTwo>, сначала
    проверяем, что тип возвращаемого значения это List и далее проверяем, что дженерик листа это класс объекта objectTwo,
    также проверяем, что название метода начинается с "get".

     При нахождении нужного геттера, возвращаем имя этого метода, return null указан в качестве заглушки
     */
    private static String searchGetterName(Object objectOne, Object objectTwo) {
        final String startNameMethod = "get";
        for (Method method : objectOne.getClass().getDeclaredMethods()) {
            if ((method.getReturnType().equals(objectTwo.getClass()) ||
                    (method.getReturnType().equals(List.class) &&
                            ((ParameterizedType) method.getGenericReturnType()).getActualTypeArguments()[0].equals(objectTwo.getClass()))) &&
                    method.getName().startsWith(startNameMethod)) {
                return method.getName();
            }
        }
        return null;
    }

    /*
    Метод находит и возвращает название метода сеттера, который содержится в первом объекте objectOne Модели и возвращает
    второй объект objectTwo, второй Модели

    На вход поступает два объекта Моделей objectOne и objectTwo

    Сеттер находим по типу входного значения, в нашем случае, тип входного значения всегда только List<objectTwo>,
    т.к. данный метод предназначен только для связи один ко многим, сначала проверяем, что тип возвращаемого значения
    это List и далее проверяем, что дженерик этого листа это класс объекта objectTwo,
    также проверяем, что название метода начинается с "set", и чтобы не было иксепшена в начале проверяем, что в данном
    методе количество типов входящих параметров более 0, геттеры сюда не попадают их стразу отсекаем, т.к. у них 0

     При нахождении нужного сеттера, возвращаем имя этого метода, return null указан в качестве заглушки
     */
    private static String searchSetterName(Object objectOne, Object objectTwo) {
        final String startMethodName = "set";
        for (Method method : objectOne.getClass().getDeclaredMethods()) {
            if (method.getParameterTypes().length > 0 &&
                    method.getParameterTypes()[0].equals(List.class) &&
                    ((ParameterizedType) method.getGenericParameterTypes()[0]).getActualTypeArguments()[0].equals(objectTwo.getClass()) &&
                    method.getName().startsWith(startMethodName)) {
                return method.getName();
            }
        }
        return null;
    }

    /*
    Метод находит и возвращает название метода getId у объекта Модели

    Т.к. есть Модели, у которых в качестве id выступает не только идентификатор типа int, но и типа String, поэтому
    имя поля идентификатора может быть любым, поэтому поиск осуществляется по полям класса входящего объекта о,
    далее ищем поле, у которого есть аннотация @Id, именно это поле и есть идентификатор Модели

    При нахождении идентификатора, формируется название метода из приставки "get" + большая первая буква имени поля
    идентификатора + остальная часть имени поля, и возвращаем название этого метода, return null указан в качестве заглушки
     */
    private static String searchGetIdName(Object o) {

        final String annotationId = "Id";
        final String startNameMethodGetId = "get";

        for (Field field : o.getClass().getDeclaredFields()) {
            for (Annotation annotation : field.getAnnotations()) {
                if (annotation.annotationType().getSimpleName().equals(annotationId)) {
                    String firstLetter = String.valueOf(field.getName().charAt(0)).toUpperCase();
                    return startNameMethodGetId + firstLetter + field.getName().substring(1);
                }
            }
        }
        return null;
    }
}
