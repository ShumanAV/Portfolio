package ru.shuman.Project_Aibolit_Server.util;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.BeanWrapper;
import org.springframework.beans.BeanWrapperImpl;
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Errors;
import org.springframework.validation.FieldError;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.util.*;

/*
Класс GeneralMethods содержит статические методы, которые используются многократно по коду.
 */

public class GeneralMethods {

    /*
    Метод выполняет проверку на наличие ошибок в bindingResult,
    в случае наличия ошибок формирует строку ошибок в виде StringBuilder и
    выбрасывает исключение разных типов с сообщением об ошибках в виде данной строки,
    в зависимости от того, какой тип исключения поступит на вход,
    на входе тип исключений ограничен RuntimeException.
     */
    public static void checkingForErrorsAndThrowsException(BindingResult bindingResult, Class<? extends RuntimeException> exception) {

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
    Данный метод осуществляет поиск имени поля в родительской сущности для указания ошибки в валидаторе для дочерней
    сущности, из которой запускается данный метод, (ниже описан пример), в данном случае родительская сущность -
    таргетный класс в errors.

    Для удобства, если таргетный класс это не класс DTO, а класс модели и searchableFieldType тоже класс модели приходит
    по умолчанию из валидатора, они оба переводятся в аналогичные классы DTO. Сделано это для того, чтобы упростить код и
    избежать зацикленности связей в моделях, в DTO этого нет.

    Например, идет создание вызова врача - CallingDTO, при этом осуществляется валидация по цепочке всех дочерних сущностей DTO,
    с которыми есть связь у CallingDTO, например, PatientDTO, DoctorDTO, SpecializationDTO, ProfileDTO, RoleDTO и т.д.,
    при валидации RoleDTO в соответствующем валидаторе, при наличии ошибок необходимо указать название поля,
    в котором ошибка, причем название поля из родительской сущности - СallingDTO, через которое осуществляется связь
    RoleDTO и СallingDTO, и в данном случае это будет поле с названием "doctor", т.к. данное поле имеет тип DoctorDTO,
     а оно в свою очередь имеет поле "profile" типа ProfileDTO, а оно в свою очередь имеет поле искомого типа "role" типа RoleDTO.

    На вход данный метод принимает объект errors типа Errors, где содержится таргетный класс и тип искомого поля
    типа CLass - класс модели из валидатора.

    Данный метод возвращает название такого поля в таргетном классе.
    В случае когда создается непосредственно сам подобъект, не из главной сущности, например Specialization,
    и в этом случае таргетный класс в errors будет совпадать с типом искомого поля, тоже будет Specialization, или
    в случае, когда по разным причинам не удалось найти искомый тип поля, в обоих случаях метод возвращает null.
     */
    public static String searchNameFieldInParentEntity(Errors errors, Class<?> searchableFieldType) {

        //создадим переменную с именем пакета где лежат все dto данного проекта
        final String PACKAGE_FOR_DTO = "ru.shuman.Project_Aibolit_Server.dto";

        //создадим переменную с именем пакета где лежат все модели данного проекта
        final String PACKAGE_FOR_MODELS = "ru.shuman.Project_Aibolit_Server.models";

        //т.к. данный метод предназначен для работы только с моделями и dto, на случай если на вход приходит другая
        // сущность не из этих пакетов, просто возвращаем null
        if (!searchableFieldType.getPackageName().equals(PACKAGE_FOR_DTO) &&
                !searchableFieldType.getPackageName().equals(PACKAGE_FOR_MODELS)) {
            return null;
        }

        //если название пакета объекта, который пришел в метод совпадает с пакетом моделей, это значит что на вход
        // пришла модель из пакета моделей, преобразуем название класса из модели в ее dto и запишем в переменную
        // искомого типа поля
        if (searchableFieldType.getPackageName().equals(PACKAGE_FOR_MODELS)) {
            searchableFieldType = formClassDTO(searchableFieldType);
        }

        //получаем таргетный класс из объекта errors, если его название пакета совпадает с пакетом моделей, это значит,
        // что таргетный класс это модель из пакета моделей, а не dto, преобразуем название таргетного класса из модели
        // в ее dto и запишем в переменную таргетного класса
        Class<?> targetClass = ((BeanPropertyBindingResult) errors).getTarget().getClass();
        if (targetClass.getPackageName().equals(PACKAGE_FOR_MODELS)) {
            targetClass = formClassDTO(targetClass);
        }

        //если искомый тип и таргетный равны возвращаем null, это значит, что создается или апдейтится родительская
        // сущность например, Specialization, и в данном случае таргетный класс также будет Specialization,
        // в этом случае вернется null и название поля, которое будет указано в валидаторе для ошибки будет то,
        // которое прописано в данном валидаторе, откуда вызывался данный метод
        if (targetClass.equals(searchableFieldType)) {
            return null;
        }

        //создаем внутренний класс - контейнер для полей классов, по которым будем осуществлять поиск искомого типа поля,
        // нужен для запоминания и отслеживания уровней погружения и прохождения полей в классах,
        // поля: тип поля, имя поля, номер поля
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

        //нам потребуется некое хранилище для полей, когда мы будем из одной сущности погружаться в другую
        // и переходить с одного уровня на другой, для этого создаем список контейнеров и создаем первый контейнер
        // в качестве первого поля выступить таргетный класс, как самый верхний уровень
        List<Container> sequence = new ArrayList<>();
        sequence.add(new Container(targetClass, "", 0));

        //создаем переменную константу обозначающую уровень 1, с которого будем возвращать искомое имя поля, т.к. именно
        // на этом уровне поле находится в родительской сущности, и под ней уже все дочерние сущности
        final byte firstLevel = 1;

        //создаем переменную - флаг для работы главного цикла
        boolean breakMainCycle = false;

        //создаем переменную уровень, на самом верхнем уровне - на уровне родительской сущности, это 0
        int level = 0;

        //создаем переменную номер поля на уровне выше, чтобы запоминать, в случае если мы провалившись в поле,
        // тип которого очередная сущность dto, но не та, которая нам нужна, могли бы вернуться обратно в сущность
        // на уровень выше и продолжить поиск со следующего поля по списку
        int fieldNumberAtLevelAbove = 0;

        //запускаем главный цикл пока флаг - переменная false
        while (!breakMainCycle) {

            //получаем кол-во полей в текущем таргетном классе
            int length = targetClass.getDeclaredFields().length;
            //создаем переменную счетчик текущий номер поля в текущем таргетном классе
            int currentFieldNumber = 0;
            //проходимся циклом по всем полям текущего таргетного класса
            for (Field field : targetClass.getDeclaredFields()) {

                //увеличиваем счетчик номера текущего поля в классе
                currentFieldNumber += 1;

                //если мы прошлись по всем полям дочерней сущности, раз дочерней значит и уровень больше 0,
                // но так и не нашли искомый тип поля т.к. мы дошли до этого условия, значит возвращаемся на уровень
                // выше обратно, но т.к. это было последнее поле, которое проверяли и "проваливались" в него, значит в
                // данной сущности мы проверили все поля и нужно подниматься еще на уровень выше. Для этого из списка
                // уровней полей, получаем новый таргетный класс и номер поля в этом таргетном классе, в которое
                // "проваливались" на этом уровне, также удаляем из списка уровень, который уже проверили
                if (fieldNumberAtLevelAbove == length && level > 0) {
                    targetClass = sequence.get(level - 1).fieldType;
                    fieldNumberAtLevelAbove = sequence.get(level).fieldNumber;
                    sequence.remove(level);
                    level -= 1;
                    break;

                    //в случае, если мы прошлись по всем полям родительской сущности (уровень 0), включая все промежуточные,
                    // которые уже проверили, но так и не нашли по каким-то причинам искомый тип поля,
                    // то останавливаем основной цикл и метод возвращает null
                } else if (fieldNumberAtLevelAbove == length && level == 0) {
                    breakMainCycle = true;
                    break;
                }

                //когда мы возвращаемся в сущность на уровень выше, чтобы заново не проверять те поля, которые уже
                // проверили, требуется это условие, чтобы пропустить этот шаг цикла, т.к. цикл foreach без встроенного
                // счетчика, пока текущий номер поля в текущем таргетном классе не будет +1 от того поля,
                // когда мы "провалились" в дочернюю сущность
                if (currentFieldNumber < fieldNumberAtLevelAbove + 1) {
                    continue;
                }

                //получаем текущий тип поля, нам нужно поле с типом данных List, проверяем если это List, то присваиваем
                // переменной текущего типа поля тип, которым параметризирован List
                Class<?> currentTypeField = field.getType();
                if (currentTypeField.equals(List.class)) {
                    currentTypeField = (Class<?>) ((ParameterizedType) field.getGenericType()).getActualTypeArguments()[0];
                }

                //проверяем, если пакет текущего типа поля это пакет с dto, значит это, интересующая нас сущность,
                // в которой может быть искомый тип поля, поэтому идем теперь по этой сущности, если нет,
                // то просто двигаемся дальше
                if (currentTypeField.getPackageName().equals(PACKAGE_FOR_DTO)) {

                    //увеличиваем счетчик уровней на 1, т.к. переходим в следующую сущность на уровень ниже,
                    // добавляем в список полей новый контейнер с текущим типом поля, в которое "проваливаемся",
                    // именем поля и номером поля
                    level += 1;
                    sequence.add(new Container(currentTypeField, field.getName(), currentFieldNumber));

                    //проверяем если текущий тип поля и есть искомый тип поля, то возвращаем имя этого поля
                    if (currentTypeField.equals(searchableFieldType)) {
                        return sequence.get(firstLevel).fieldName;
                    }

                    //если текущий тип поля и искомый разные типы, то идем дальше, присваиваем переменной таргетного
                    // класса класс текущего типа поля, обнуляем переменную номер поля на уровне выше,
                    // останавливаем цикл, чтобы заново пойти по полям, но уже нового таргетного класса
                    targetClass = currentTypeField;
                    fieldNumberAtLevelAbove = 0;
                    break;

                }

                //если мы прошлись по всем полям дочерней сущности, раз дочерней значит и уровень больше 0,
                // но так и не нашли искомый тип поля т.к. мы дошли до этого условия, значит возвращаемся на уровень
                // выше обратно, и продолжаем поиск в сущности на уровень выше со следующего поля от того, где
                // "провалились" в данную дочернюю сущность. Для этого из списка уровней полей, получаем новый таргетный
                // класс и номер поля в этом таргетном классе, в которое погружались, также удаляем из списка уровень,
                // который уже проверили
                if (currentFieldNumber == length && level > 0) {
                    targetClass = sequence.get(level - 1).fieldType;
                    fieldNumberAtLevelAbove = sequence.get(level).fieldNumber;
                    sequence.remove(level);
                    level -= 1;
                    break;
                }
            }
        }
        //если не нашли искомый тип поля возвращаем null
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

    Пример, добавляем новый вызов врача, указываем для вызова существующий прайс, т.к. тип отношения
    прайса к вызовам 1 ко многим, то у вызова будет уже выбранный прайс, а вот в кэше у прайса в списке вызовов не будет
    этого нового вызова, вот в таких случаях когда отношение многие к одному мы и добавляем в список новый объект.

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
    public static String searchGetterName(Object objectOne, Object objectTwo) {
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
    public static String searchSetterName(Object objectOne, Object objectTwo) {
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
    public static String searchGetIdName(Object o) {

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

    /*
    Метод копирует значения полей, которые не null из объекта источника в объект цель.
    Используется при апдейте сущностей, чтобы при сохранении поля со значениями null не заменяли значения в БД,
    чтобы в БД не терять данные (как пример, поле createdAt, при изменении объекта в json его нет, поэтому оно null).
     */
    public static void copyNonNullProperties(Object src, Object target) {
        BeanUtils.copyProperties(src, target, getNullPropertyNames(src));
    }

    /*
    Метод возвращает массив String полей объекта источника, которые имеют значение null, на вход принимает объект
    источник.

    Например, объект источник - прайс Price, когда фронт-енд присылает объект Price для апдейта, могут быть заполнены
    не все поля, нужно чтобы записались в БД только те поля, которые не null, данный метод определяет поля со значением
    null и возвращает массив данных полей
     */
    public static String[] getNullPropertyNames (Object source) {
        final BeanWrapper src = new BeanWrapperImpl(source);
        java.beans.PropertyDescriptor[] pds = src.getPropertyDescriptors();

        Set<String> emptyNames = new HashSet<>();
        for(java.beans.PropertyDescriptor pd : pds) {
            Object srcValue = src.getPropertyValue(pd.getName());
            if (srcValue == null) emptyNames.add(pd.getName());
        }
        String[] result = new String[emptyNames.size()];
        return emptyNames.toArray(result);
    }

}
