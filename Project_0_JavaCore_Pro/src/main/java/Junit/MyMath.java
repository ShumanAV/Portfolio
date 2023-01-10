package Junit;

public class MyMath extends Throwable{
    public static double divide(int number1, int number2) {
        if (number2 == 0) {
            throw new ArithmeticException("Can't devide by zero");
        }
        return number1 / number2;
    }
}
