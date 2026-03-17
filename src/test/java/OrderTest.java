import data.OrderData;
import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.Response;
import model.OrderModel;
import model.UserLoginModel;
import model.UserModel;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static data.OrderData.*;
import static data.UserData.*;
import static org.apache.http.HttpStatus.*;
import static org.hamcrest.CoreMatchers.equalTo;
import static steps.OrderSteps.*;
import static steps.UserSteps.*;


public class OrderTest extends BaseApiTest {

    String token;

    @Before
    public void setUp() {

        super.setUp();
        // 1. Создаём пользователя
        UserModel user = new UserModel(EMAIL, PASSWORD, NAME);
        Response createResponse = createUser(user);


        // 2. Авторизуемся и получаем токен
        UserLoginModel login = new UserLoginModel(EMAIL, PASSWORD);
        Response loginResponse = loginUser(login)
                .then()
                .statusCode(SC_OK)
                .extract().response();

        token = loginResponse.path("accessToken");
    }

    @Test
    @DisplayName("Order test with authorization")
    @Description("Checking created order with authorization and with ingredients")
    public void orderTestWithAuthorization() { // с ингредиентами  и авторизацией
        OrderModel order = new OrderModel(INGREDIENTS);
        createOrderWithAuthorization(token, order)
                .then()
                .log().all()
                .statusCode(SC_OK)
                .body("success", equalTo(true));
    }

    @Test
    @DisplayName("Order test without authorization and with ingredients")
    @Description("Checking created order  without authorization and with ingredients")
    public void orderTestWithoutAuthorization() { // с ингредиентами  но без авторизации
        OrderModel order = new OrderModel(INGREDIENTS);
        createOrderWithoutAuthorization(order)
                .then()
                .log().all()
                .statusCode(SC_OK)
                .body("success", equalTo(true));
    }

    @Test
    @DisplayName("Order test without authorization and with ingredients")
    @Description("Checking created order  without authorization and with ingredients")
    public void orderTestWithoutAuthorizationWithoutIngredients() { //   без авторизации и без ингредиентов
        OrderModel order = new OrderModel(null);
        createOrderWithoutAuthorization(order)
                .then()
                .log().all()
                .statusCode(SC_BAD_REQUEST)
                .body("message", equalTo(OrderData.NO_INGREDIENTS));
    }

    @Test
    @DisplayName("Order test with authorization and without ingredients")
    @Description("Checking created order with authorization and without ingredients")
    public void orderTestWithoutIngredients() {  //без ингредиентов но с авторизацией
        OrderModel order = new OrderModel(null);
        createOrderWithAuthorization(token, order)
                .then()
                .log().all()
                .statusCode(SC_BAD_REQUEST)
                .body("message", equalTo(OrderData.NO_INGREDIENTS));
    }

    @Test
    @DisplayName("Order test with invalid ingredients")
    @Description("Checking created order without authorization and invalid ingredients")
    public void orderTestWithInvalidIngredients() {  // не валидный хеш но с авторизацией
        OrderModel order = new OrderModel(INVALID_INGREDIENTS);
        createOrderWithAuthorization(token, order)
                .then()
                .log().all()
                .statusCode(SC_INTERNAL_SERVER_ERROR);
    }

    @After
    public void cleanUp() {
        if (token != null) {
            deleteUser(token);
        }
    }
}

