package tests;

import io.qameta.allure.restassured.AllureRestAssured;
import models.lombok.LoginBodyLombokModel;
import models.lombok.LoginResponseLombokModel;
import models.pojo.LoginBodyPojoModel;
import models.pojo.LoginResponsePojoModel;
import org.junit.jupiter.api.Test;

import static helpers.CustomAllureListener.withCustomTemplates;
import static io.qameta.allure.Allure.step;
import static io.restassured.RestAssured.given;
import static io.restassured.http.ContentType.JSON;

import static org.assertj.core.api.Assertions.*;
import static specs.LoginSpecs.loginRequestSpec;
import static specs.LoginSpecs.loginResponseSpec;

public class ReqresInExtendedTests {
    // тест в стиле POJO
    // POJO - Plain old Java object (старый добрый Java-объект)
    @Test
    void successfulLoginWithPojoTest(){
        LoginBodyPojoModel loginBodyPojoModel = new LoginBodyPojoModel();
        loginBodyPojoModel.setEmail("eve.holt@reqres.in");
        loginBodyPojoModel.setPassword("QpwL5tke4Pnpja7X4");

        LoginResponsePojoModel responseModel = given()
                .log().uri()
                .body(loginBodyPojoModel)
                .contentType(JSON)
        .when()
                .post("https://reqres.in/api/login")
        .then()
                .log().status()
                .log().body()
                .statusCode(200)
                // парсим токен при помощи нашего класса 'LoginResponseModel'
                // данная строчка кода берет ответ запроса и представляет ее в описанном формате
                .extract().as(LoginResponsePojoModel.class);

        // ассерт от 'jUnit5'
        /*Assertions.assertEquals(
                "QpwL5tke4Pnpja7X4",
                responseModel.getToken()
        );*/

        // ассерт от 'assertj'
        assertThat(responseModel.getToken())
                .isEqualTo("QpwL5tke4Pnpja7X4");
    }

    // тест в стиле Lombok
    @Test
    void successfulLoginWithLombokTest(){
        LoginBodyLombokModel loginBodyLombokModel = new LoginBodyLombokModel();
        loginBodyLombokModel.setEmail("eve.holt@reqres.in");
        loginBodyLombokModel.setPassword("QpwL5tke4Pnpja7X4");

        LoginResponseLombokModel responseModel = given()
                .log().uri()
                .body(loginBodyLombokModel)
                .contentType(JSON)
        .when()
                .post("https://reqres.in/api/login")
        .then()
                .log().status()
                .log().body()
                .statusCode(200)
                .extract().as(LoginResponseLombokModel.class);

        assertThat(responseModel.getToken())
                .isEqualTo("QpwL5tke4Pnpja7X4");
    }

    // тест в стиле Lombok с Allure
    @Test
    void successfulLoginWithLombokAndAllureTest(){
        LoginBodyLombokModel loginBodyLombokModel = new LoginBodyLombokModel();
        loginBodyLombokModel.setEmail("eve.holt@reqres.in");
        loginBodyLombokModel.setPassword("QpwL5tke4Pnpja7X4");

        LoginResponseLombokModel responseModel = given()
                // подключаем Allure к тесту
                .filter(new AllureRestAssured())
                .log().uri()
                .body(loginBodyLombokModel)
                .contentType(JSON)
        .when()
                .post("https://reqres.in/api/login")
        .then()
                .log().status()
                .log().body()
                .statusCode(200)
                .extract().as(LoginResponseLombokModel.class);

        assertThat(responseModel.getToken())
                .isEqualTo("QpwL5tke4Pnpja7X4");
    }

    // тест в стиле Lombok с Allure с кастомизацией
    // создаем папку 'tpl' в 'resources'
    // внутри папки 'tpl' создаем два 'ftl' файла 'request' и 'response' и запонляем нужными данными
    // в дериктории проекта создаем папку 'helpers' и внутри создаем класс 'CustomAllureListener' с нужным кодом
    @Test
    void successfulLoginWithLombokAndCustomAllureTest(){
        LoginBodyLombokModel loginBodyLombokModel = new LoginBodyLombokModel();
        loginBodyLombokModel.setEmail("eve.holt@reqres.in");
        loginBodyLombokModel.setPassword("QpwL5tke4Pnpja7X4");

        LoginResponseLombokModel responseModel = given()
                // подключаем Allure к тесту
                .filter(withCustomTemplates())
                .log().uri()
                .body(loginBodyLombokModel)
                .contentType(JSON)
        .when()
                .post("https://reqres.in/api/login")
        .then()
                .log().status()
                .log().body()
                .statusCode(200)
                .extract().as(LoginResponseLombokModel.class);

        assertThat(responseModel.getToken())
                .isEqualTo("QpwL5tke4Pnpja7X4");
    }

    // тест в стиле Lombok с Allure с кастомизацией и со степами
    @Test
    void successfulLoginWithLombokAndCustomAllureStepsTest(){
        LoginBodyLombokModel loginBodyLombokModel = new LoginBodyLombokModel();
        loginBodyLombokModel.setEmail("eve.holt@reqres.in");
        loginBodyLombokModel.setPassword("QpwL5tke4Pnpja7X4");

        LoginResponseLombokModel responseModel = step("Отправка запроса", () ->
                given()
                // подключаем Allure к тесту
                .filter(withCustomTemplates())
                .log().uri()
                .body(loginBodyLombokModel)
                .contentType(JSON)
        .when()
                .post("https://reqres.in/api/login")
        .then()
                .log().status()
                .log().body()
                .statusCode(200)
                .extract().as(LoginResponseLombokModel.class));

        step("Проверка ответа", () -> assertThat(responseModel.getToken())
                .isEqualTo("QpwL5tke4Pnpja7X4"));
    }

    // тест в стиле Lombok с Allure с кастомизацией, со степами и с спецификациями
    @Test
    void successfulLoginWithLombokAndCustomAllureStepsAndSpecsTest(){
        LoginBodyLombokModel loginBodyLombokModel = new LoginBodyLombokModel();
        loginBodyLombokModel.setEmail("eve.holt@reqres.in");
        loginBodyLombokModel.setPassword("QpwL5tke4Pnpja7X4");

        LoginResponseLombokModel responseModel = step("Отправка запроса", () ->
                // в given() передаем наш статичный метод с описанным запросом
        given(loginRequestSpec)
                 // либо можно передать в spec()
                // .spec(loginRequestSpec)
                .body(loginBodyLombokModel)
        .when()
                .post("")
        .then()
                 // в .spec() передаем наш статичный метод с описанным ответом
                .spec(loginResponseSpec)
                .extract().as(LoginResponseLombokModel.class));

        step("Проверка ответа", () -> assertThat(responseModel.getToken())
                .isEqualTo("QpwL5tke4Pnpja7X4"));
    }
}
