package steps;

import io.qameta.allure.Step;
import io.restassured.response.Response;
import model.UserLoginModel;
import model.UserModel;
import static data.UserData.*;
import static io.restassured.RestAssured.given;

public class UserSteps {

    @Step("Send POST request to /api/auth/register")
    public static Response createUser(UserModel user) {
        return given()
                .log().all()
                .header("Content-Type", "application/json")
                .body(user)
                .when()
                .post(CREATE_USER)
                .then()
                .extract().response();

    }
    @Step("Send POST request to /api/auth/login")
    public static Response loginUser( UserLoginModel login) {
        return given()
                .log().all()
                .header("Content-Type", "application/json")
                .body(login)
                .when()
                .post(LOGIN_USER)
                .then()
                .extract().response();
    }
    @Step("Send DELETE request to /api/auth/user")
    public static void deleteUser(String token) {
        given()
                .log().all()
                .header("Content-Type", "application/json")
                .header("Authorization", token)
                .when()
                .delete(DELETE_USER)
                .then()
                .extract().response();
    }
}

