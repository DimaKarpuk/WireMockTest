import com.codeborne.selenide.logevents.SelenideLogger;
import io.qameta.allure.selenide.AllureSelenide;
import io.restassured.RestAssured;
import org.junit.jupiter.api.BeforeAll;

public class TestBase {

    @BeforeAll
    public static void beforeAll() {
        RestAssured.baseURI = "https://demoqa.com";
        SelenideLogger.addListener("allure", new AllureSelenide());
    }
}
