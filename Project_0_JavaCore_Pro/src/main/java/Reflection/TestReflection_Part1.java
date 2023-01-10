package Reflection;

import Annotation.MethodInfo;
import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.Arrays;

public class TestReflection_Part1 {

    public static void main(String[] args) throws ClassNotFoundException {

        /*
          Рефлексия часть 1
          Идея рефлексии заключается в том, что мы работаем с объектом класса Person, как с объектом класса Class,
            применяем к нему все методы класса Class,
            на этой идее рефлексии работают множество библиотек, которые работают с кодом
         */

        Person person = new Person();

        // три одиковых метода с получением объекта класса Person как объекта класса Class
        Class personClass = Person.class;
        Class personClass2 = person.getClass();
        Class personClass3 = Class.forName("Reflection.Person");

        // получаем все методы класса Person, getMethods() - с учетом инкапсуляции, getDeclaredMethods() - все методы
        Method[] methods = personClass.getMethods();
        for (Method method: methods) {
            System.out.println(method.getName() + ", " +
                               method.getReturnType() + ", " +
                               Arrays.toString(method.getParameterTypes()));
        }

        // получаем все поля класса Person с помощью метода getFields(), который учитывает инкапсуляцию и выводит только поля public,
        // метод getDeclaredFields() выводит все поля
        Field[] fields = personClass.getDeclaredFields();
        for (Field field: fields) {
            System.out.println(field.getName() + ", " +
                    field.getType());
        }

        // получаем все аннотации класса Person
        Annotation[] annotations  = personClass.getAnnotations();
        for (Annotation annotation: annotations) {
            if (annotation instanceof MethodInfo) {
                System.out.println("Yes");
            }
        }


    }

}
