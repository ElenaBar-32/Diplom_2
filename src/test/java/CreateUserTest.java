import data.UserData;
import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import model.UserModel;
import org.junit.Test;
import static data.UserData.*;
import static org.apache.http.HttpStatus.*;
import static org.hamcrest.CoreMatchers.equalTo;
import static steps.UserSteps.createUser;

public class CreateUserTest  extends BaseApiTest {

    private String generateUniqueLogin() {
        return EMAIL + System.currentTimeMillis();

    }
    @Test
    @DisplayName("Create user")
    @Description("Checking the successful creation of a user")
    public void testCreateUser() {
        String uniqueLogin = generateUniqueLogin();
        UserModel user = new UserModel(uniqueLogin, PASSWORD, NAME);
        String token = createUser(user)
                .then()
                .log().all()
                .statusCode(SC_OK)
                .body("success", equalTo(true))
                .extract()
                .path("accessToken");
    }
    @Test
    @DisplayName("Create user with same login")
    @Description("Checking for repeated creation of a user with the same login")
    public void testCreateUserWithSameLogin() {
        String uniqueLogin = generateUniqueLogin();
        UserModel user = new UserModel(uniqueLogin, PASSWORD, NAME);
        createUser(user)
                .then()
                .log().all()
                .statusCode(SC_OK)
                .body("success", equalTo(true));

        createUser(user)
                .then()
                .log().all()
                .statusCode(SC_FORBIDDEN)
                .body("message", equalTo(ERROR_LOGIN_ALREADY_USED ));
    }
    @Test
    @DisplayName("Create user without login")
    @Description("Checking the creation of a user without a login")
    public void testCreateUserWithoutLogin() {
        UserModel user = new UserModel(null, PASSWORD, NAME);
        createUser(user)
                .then()
                .log().all()
                .statusCode(SC_FORBIDDEN)
                .body("message", equalTo(UserData.ERROR_INSUFFICIENT_DATA));
    }

    @Test
    @DisplayName("Create user without password")
    @Description("Checking the creation of a user without a password")
    public void testCreateUserWithoutPassword() {
        String uniqueLogin = generateUniqueLogin();
        UserModel user = new UserModel(uniqueLogin, null, NAME);
        createUser(user)
                .then()
                .log().all()
                .statusCode(SC_FORBIDDEN)
                .body("message", equalTo(UserData.ERROR_INSUFFICIENT_DATA));
    }
    @Test
    @DisplayName("Create user without firstname")
    @Description("Checking the creation of a user without a firstname")
    public void testCreateUserWithoutFirstname() {
        String uniqueLogin = generateUniqueLogin();
        UserModel user = new UserModel(uniqueLogin, PASSWORD, null);
        createUser(user)
                .then()
                .log().all()
                .statusCode(SC_FORBIDDEN)
                .body("message", equalTo(UserData.ERROR_INSUFFICIENT_DATA));
    }
}

