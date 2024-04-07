package tests.withoutPojo;

import io.qameta.allure.Feature;
import io.qameta.allure.Story;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import tests.annotations.Layer;

import static io.restassured.RestAssured.get;
import static io.restassured.RestAssured.given;
import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchemaInClasspath;
import static org.hamcrest.Matchers.hasKey;
import static org.hamcrest.Matchers.is;

@Layer("web")
@Feature("Selenide tests without PO")
public class SelenoidTests {
    @Test
    @Story("Check total")
    void checkTotal(){
        // три этапа жизнедеятельности теста (наследовано от BDD подхода) given-when-then в языке описания
        // сценариев Gherkin
        // given() - прекондишены, начальный конфиг типо Configuration.browser = "chrome"
        // если в данном блоке ничего не указать, то будет применен дефолтный конфиг (как и в selenide)
        // если данные блоки пусты, их в принципе можно и не писать, а начать тест сразу с "get()"
        given()
                // when() - когда делаем какое-то действие
        .when()
                // get() - как open() в selenide
                .get("https://selenoid.autotests.cloud/status")
                // then() - проверка
        .then()
                .body("total", is(20));
    }

    // проверка статуса
    @Test
    @Story("Check total without PO")
    void checkTotalWithStatusCode200(){
        get("https://selenoid.autotests.cloud/status")
                .then()
                .statusCode(200)
                .body("total", is(20));
    }

    // полное логирование
    @Test
    @Story("Check total without PO")
    void checkTotalWithAllLogs(){
        given()
                // логируем запрос
                .log().all()
        .when()
                .get("https://selenoid.autotests.cloud/status")
        .then()
                // логируем ответ
                .log().all()
                .body("total", is(20));
    }

    // выборочное логирование
    @Test
    @Story("Check total without PO")
    void checkTotalWithSomeLogs(){
        given()
                .log().uri()
                //.log().body() // для post запросов
        .when()
                .get("https://selenoid.autotests.cloud/status")
        .then()
                .log().status()
                .log().body()
                .body("total", is(20));
    }

    // проверка внутреннего элемента
    @Test
    @Story("Check chrome without PO")
    void checkChromeVersion(){
        given()
                .log().uri()
        .when()
                .get("https://selenoid.autotests.cloud/status")
        .then()
                .log().status()
                .log().body()
                .body("total", is(20))
                .body("browsers.chrome", hasKey("100.0"));
    }

    @Disabled
    // Bad practice
    @Test
    void checkResponseBadPractice(){
        String expectedResponse = "{\"total\":20,\"used\":0,\"queued\":0,\"pending\":0,\"browsers\":{\"chrome\":{\"100.0\":{},\"120.0\":{},\"121.0\":{},\"122.0\":{},\"99.0\":{}},\"firefox\":{\"122.0\":{},\"123.0\":{}},\"opera\":{\"106.0\":{},\"107.0\":{}}}}";
        String actualResponse = given()
                .log().uri()
        .when()
                .get("https://selenoid.autotests.cloud/status")
        .then()
                .log().status()
                .log().body()
                .statusCode(200)
                .extract().response().asString();

        Assertions.assertEquals(expectedResponse, actualResponse);
    }

    // Good practice
    @Test
    @Story("Check response without PO")
    void checkResponseGoodPractice(){
        Integer expectedTotal = 20;

        Integer actualTotal = given()
                .log().uri()
        .when()
                .get("https://selenoid.autotests.cloud/status")
        .then()
                .log().status()
                .log().body()
                .statusCode(200)
                .extract().path("total");

        Assertions.assertEquals(expectedTotal, actualTotal);
    }

    // тест на Json схему
    // вытаскиваем Json схему через сайт 'https://www.liquid-technologies.com/online-json-to-schema-converter'
    // создаем файл '.json' в папке 'resources' и вставляем туда сгенерированную схему
    @Test
    @Story("Check JSON without PO")
    void checkJsonScheme(){
        given()
                .log().uri()
        .when()
                .get("https://selenoid.autotests.cloud/status")
        .then()
                .log().status()
                .log().body()
                .statusCode(200)
                .body("total", is(20))
                .body("browsers.chrome", hasKey("100.0"))
                // проверяем Json схему
                .body(matchesJsonSchemaInClasspath("schemes/status-scheme-response.json"));
    }
}
