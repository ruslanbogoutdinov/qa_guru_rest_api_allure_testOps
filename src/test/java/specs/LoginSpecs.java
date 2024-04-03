package specs;

import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;

import static helpers.CustomAllureListener.withCustomTemplates;
import static io.restassured.RestAssured.with;
import static io.restassured.filter.log.LogDetail.BODY;
import static io.restassured.http.ContentType.JSON;
import static io.restassured.filter.log.LogDetail.STATUS;
import static org.hamcrest.Matchers.notNullValue;

public class LoginSpecs {
    // описываем спецификацию запроса
    public static RequestSpecification loginRequestSpec = with()
            .filter(withCustomTemplates())
            .log().uri()
            .log().body()
            .contentType(JSON)
            // можем прописать baseUri
            .baseUri("https://reqres.in")
            // также можно прописать и basePath
            .basePath("/api/login");

    // описываем спецификацию ответа
    public static ResponseSpecification loginResponseSpec = new ResponseSpecBuilder()
            // вместо '.log().status()', пишем
            .log(STATUS)
            // вместо '.log().body()', пишем
            .log(BODY)
            // вместо '.statusCode(200)', пишем
            .expectStatusCode(200)
            // также можем проверить что токен не равен null
            .expectBody("token", notNullValue())
            // и в конце прописываем
            .build();
}
