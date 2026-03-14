package steps;

import io.qameta.allure.Step;
import io.restassured.response.Response;
import model.OrderModel;

import static data.OrderData.CREATE_ORDER;
import static io.restassured.RestAssured.given;

public class OrderSteps {
    @Step("Send POST request to create order with authorization")
    public static Response createOrderWithAuthorization(String token,OrderModel order) {
        return given()
                .log().all()
                .header("Content-Type", "application/json")
                .header("Authorization", token)
                .body(order)
                .when()
                .post(CREATE_ORDER)
                .then()
                .extract().response();
    }

    @Step("Send POST request to create order with authorization")
    public static Response createOrderWithoutAuthorization(OrderModel order) {
        return given()
                .log().all()
                .header("Content-Type", "application/json")
                .body(order)
                .when()
                .post(CREATE_ORDER)
                .then()
                .extract().response();
    }
}
