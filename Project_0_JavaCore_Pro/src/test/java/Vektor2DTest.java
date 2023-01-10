import Junit.Vector2D;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

public class Vektor2DTest {
    private final double EPS = 1e-9;
    private static Vector2D v1;

    @BeforeClass
    public static void createNewVector() {
         v1 = new Vector2D();
    }

    @Test
    public void newVectorShouldHaveZeroLength() {
        Assert.assertEquals(0, v1.length(),EPS);    // 0 - ожидаемый результат, vek1.length() - фактический, точность
    }

    @Test
    public void newVectorShouldHAveZeroX() {
        Assert.assertEquals(0, v1.getX(),EPS);
    }

    @Test
    public void newVectorShouldHAveZeroY() {
        Assert.assertEquals(0, v1.getY(),EPS);
    }

}
