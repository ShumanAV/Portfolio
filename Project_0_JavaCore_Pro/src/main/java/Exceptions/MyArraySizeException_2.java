package Lesson_14.Training_Exceptions_Lesson9;

public class MyArraySizeException_2 extends Throwable{
    public MyArraySizeException_2() {
        System.out.println("В массиве неверное количество столбцов или строк");
    }
}
