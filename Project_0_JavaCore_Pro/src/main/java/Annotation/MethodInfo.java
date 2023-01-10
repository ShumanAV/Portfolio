package Annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

    /*
    Аннотации
    в java есть аннотации, которые аннотируют аннотации, например:
    @Target - указывает, к чему может быть применена аннотация,
        значения берутся из Enum ElementType того же пакета (FIELD - поле, METHOD - метод, TYPE - класс, интерфейс, перечисление
        лежат такие аннотации в пакете java.lang.annotation
    @Retention - указывается, до какого этапа компилирования или выполнения аннотация видна: SOURCE - отбрасывается при компиляции,
        CLASS - сохраняются в байт-коде, но недоступны при выполнении программы, RUNTIME - сохраняются в байт-коде и доступны при выполнении программы
        (при помощи рефлексии)
     */

@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.METHOD, ElementType.TYPE})
// аннотация для создания пометок своего кода
public @interface MethodInfo {
    String author() default "Alex";
    int dateOfCreation() default 2022;
    String purpose();
}
