import io.restassured.RestAssured;
import org.junit.After;
import org.junit.Before;
import static data.UserData.BASE_URI;
import static steps.UserSteps.deleteUser;

public class BaseApiTest {

    String token;

    @Before
    public void setUp() {
        RestAssured.baseURI = BASE_URI;
    }

    @After
    public void cleanUp() {
        if (token != null) {
            deleteUser(token);
        }
    }
}

