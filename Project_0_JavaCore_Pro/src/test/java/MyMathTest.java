import Junit.MyMath;
import org.junit.Test;

public class MyMathTest {

    // в этом тестовом сценарии ожидаем ексепшн класса ArithmeticException
    @Test(expected = ArithmeticException.class)
    public void zeroDenominatorShouldThrowException() {
        MyMath.divide(1, 0);
    }

}
