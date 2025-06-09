package steps;

import dto.UserDto;
import io.qameta.allure.Step;
import io.restassured.response.Response;

import static io.restassured.RestAssured.given;
import static utils.BaseHelper.CREATE_USER_ENDPOINT;

public class CreateUserSteps {
    @Step("Создать пользователя")
    public static Response createUser(UserDto user) {
        return given()
                .contentType("application/json")
                .body(user)
                .when()
                .post(CREATE_USER_ENDPOINT);
    }


    @Step("Получить из ответа 'accessToken'")
    public static String getClearAccessToken(Response response) {
        return response.path("accessToken").toString().split(" ")[1];
    }
}
