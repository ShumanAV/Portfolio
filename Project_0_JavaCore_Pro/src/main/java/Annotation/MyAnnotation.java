package Annotation;

public @interface MyAnnotation {

    String name() default "some name";  // поля объявляются как методы
    int dateOfBirth() default 2000;     //можно указать значение по умолчанию для полей
}
