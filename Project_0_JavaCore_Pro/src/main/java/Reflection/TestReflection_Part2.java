package Reflection;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Scanner;

public class TestReflection_Part2 {

    public static void main(String[] args) throws ClassNotFoundException, NoSuchMethodException, IllegalAccessException, InstantiationException, InvocationTargetException {

        /*
         Рефлексия часть 2
         С помощью рефлексии можно:
           - создавать новые объекты класса (по умолчанию вызывает пустой конструктор) - newInstance(),
           данный метод можно вызывать на объектах класса Class и на объектах класса Constructor
           - получать методы по сигнатуре (имя метода и набор параметров) - getMethod(), можем передавать в него необходимые аргументы
           - получать конструктор по сигнатуре - getConstructor(),
           - для объекта класса Method вызывать методы - invoke(), выполняет данный метод, вызывается на методе типа getMethod().invoke()
           и многое другое
         Мы создавали объекты с помощью метода new и запускали методы, все это можно делать с помощью рефлексии
         */

        //при запуске вводим - "Reflection.Person java.lang.String setName"
        Scanner scanner = new Scanner(System.in);

        // передаем в консоль Название_класса_1 Название_класса_2 Название_метода
        Class classObject1 = Class.forName(scanner.next()); // название класса 1
        Class classObject2 = Class.forName(scanner.next()); // название класса 2
        String methodName = scanner.next(); // название метода

        Method method = classObject1.getMethod(methodName, classObject2);
        Object o1 = classObject1.newInstance();
        Object o2 = classObject2.getConstructor(String.class).newInstance("Pukan");

        method.invoke(o1, o2);      // метод вызывается на объекте о1 с аргументом объект o2

        System.out.println(o1);


    }

}
