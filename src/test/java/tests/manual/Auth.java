package tests.manual;

import io.qameta.allure.AllureId;
import io.qameta.allure.Feature;
import io.qameta.allure.Owner;
import io.qameta.allure.Story;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static io.qameta.allure.Allure.step;

public class Auth {
    @Test
    @AllureId("31437")
    @DisplayName("Correct auth")
    @Story("Authorization")
    @Owner("allure8")
    @Feature("Manual login tests")
    public void authTest() {
        step("Open app", () ->{
            Assertions.assertTrue(true);
        });
        step("Enter correct pin-code", () -> {
            Assertions.assertTrue(true);
        });
        step("Check that main page is opened", () -> {
            Assertions.assertTrue(true);
        });
    }
}
