import Junit.NetworkUtils;
import org.junit.Test;

public class NetworkUtilsTest {

    // тест пройден если метод выполнится за время < 1000мс
    @Test(timeout = 1000)
    public void getConnectionShouldReturnFasterThanOneSecond() {
        NetworkUtils.getConnection();
    }

}
