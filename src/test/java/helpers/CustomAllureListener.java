package helpers;

import io.qameta.allure.restassured.AllureRestAssured;

public class CustomAllureListener {
    private static final AllureRestAssured FILTER = new AllureRestAssured();

    public static AllureRestAssured withCustomTemplates() {
        // добавляем шаблон для раскраски запроса
        FILTER.setRequestTemplate("request.ftl");
        // добавляем шаблон для раскраски ответа
        FILTER.setResponseTemplate("response.ftl");

        return FILTER;
    }
}
