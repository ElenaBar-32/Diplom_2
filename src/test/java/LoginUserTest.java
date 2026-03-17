import data.UserData;
import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import model.UserLoginModel;
import model.UserModel;
import org.junit.Before;
import org.junit.Test;
import static data.UserData.*;
import static org.apache.http.HttpStatus.*;
import static org.hamcrest.CoreMatchers.equalTo;
import static steps.UserSteps.*;

public class LoginUserTest extends BaseApiTest{

     String token;

    @Before
    public void start() {
        UserModel user = new UserModel(EMAIL, PASSWORD, NAME);
        createUser(user);
    }
    @Test
    @DisplayName("Success login user")
    @Description("Check successful user authorization")
    public void testSuccessLoginUser() {
       UserLoginModel login = new UserLoginModel(EMAIL, PASSWORD);
        loginUser(login)
                .then()
                .log().all()
                .statusCode(SC_OK)
                .body("success", equalTo(true));
    }
    @Test
    @DisplayName ("Login user non-existent password")
    @Description ("Checking user authorization with a non-existent password")
    public void testLoginUserNonExistentPassword() {
        UserLoginModel login = new UserLoginModel(EMAIL, PASSWORD + "new");
        loginUser(login)
                .then()
                .log().all()
                .statusCode(SC_UNAUTHORIZED)
                .body("message", equalTo(UserData.ERROR_ACCOUNT_NOT_FOUND));
    }
    @Test
    @DisplayName ("Login user non-existent login")
    @Description ("Checking user authorization with a non-existent login")
    public void testLoginUserNonExistentLogin() {
        UserLoginModel login = new UserLoginModel(EMAIL + "new", PASSWORD);
        loginUser(login)
                .then()
                .log().all()
                .statusCode(SC_UNAUTHORIZED)
                .body("message", equalTo(ERROR_ACCOUNT_NOT_FOUND));
    }
    @Test
    @DisplayName ("Login user non-existent login and password")
    @Description ("Checking user authorization with a non-existent login and password")
    public void testLoginUserNonExistentLoginAndPassword() {
        UserLoginModel login = new UserLoginModel(EMAIL + "new", PASSWORD + "new");
        loginUser(login)
                .then()
                .log().all()
                .statusCode(SC_UNAUTHORIZED)
                .body("message", equalTo(ERROR_ACCOUNT_NOT_FOUND));
    }

    }




