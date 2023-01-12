package Lesson_14.Training_Exceptions_Lesson9;

public class MyArrayDataException_2 extends Throwable{
    public MyArrayDataException_2(int i, int j) {
        System.out.printf("В массиве в строке %d в столбце %d неверный тип данных%n", i, j);
    }
}
