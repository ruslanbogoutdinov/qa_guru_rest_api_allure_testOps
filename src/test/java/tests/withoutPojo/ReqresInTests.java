package tests.withoutPojo;

import io.qameta.allure.Feature;
import io.qameta.allure.Story;
import org.junit.jupiter.api.Test;
import tests.annotations.Layer;

import static io.restassured.RestAssured.given;
import static io.restassured.http.ContentType.JSON;
import static org.hamcrest.Matchers.is;

@Layer("rest api")
@Feature("Not extended tests")
public class ReqresInTests {
    // тест с post запросом
    @Test
    @Story("Successful user login")
    void successfulLoginTest(){
        String body = "{\n" +
                "    \"email\": \"eve.holt@reqres.in\",\n" +
                "    \"password\": \"cityslicka\"\n" +
                "}";

        given()
                .log().uri()
                // передаем body
                .body(body)
                // и указываем обязательно тип body
                .contentType(JSON)
        .when()
                .post("https://reqres.in/api/login")
        .then()
                .log().status()
                .log().body()
                .statusCode(200)
                // проверяем token в ответе
                .body("token", is("QpwL5tke4Pnpja7X4"));
    }
    // негативный тест
    @Test
    @Story("Unsuccessful user login")
    void unSuccessfulLoginTestWithMissingArgument(){
        String body = "{\n" +
                "    \"password\": \"cityslicka\"\n" +
                "}";

        given()
                .log().uri()
                // передаем body
                .body(body)
                // и указываем обязательно тип body
                .contentType(JSON)
        .when()
                .post("https://reqres.in/api/login")
        .then()
                .log().status()
                .log().body()
                .statusCode(400)
                // проверяем token в ответе
                .body("error", is("Missing email or username"));
    }
}
