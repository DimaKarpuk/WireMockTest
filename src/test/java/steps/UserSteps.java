package steps;

import com.github.javafaker.Faker;
import io.qameta.allure.Step;
import models.GetTokenModel;
import models.RegistrationNewUserModel;
import models.ResponseRegistrationNewUserModel;

import static io.restassured.RestAssured.given;
import static specs.BookStoreSpecs.*;

public class UserSteps {
    Faker faker = new Faker();
    private final String password = faker.internet().password(6, 8, true) + "2!";
    private final String userName = faker.name().username();
    private final String lastName = faker.name().lastName();
    private final String firstName = faker.name().firstName();

    @Step("Регистрация нового пользователя")
    public ResponseRegistrationNewUserModel registrationNewUser() {
        RegistrationNewUserModel regModel = new RegistrationNewUserModel();
        regModel.setUserName(userName);
        regModel.setLastName(lastName);
        regModel.setFirstName(firstName);
        regModel.setPassword(password);
        return (given(requestSpecificationWithContentTypeApplicationJson)
                .when()
                .body(regModel)
                .post("/Account/v1/User")
                .then()
                .spec(responseSpecificationWithStatusCode201)
                .extract().as(ResponseRegistrationNewUserModel.class));
    }

    @Step("Удаление пользователя")
    public void deleteUser(ResponseRegistrationNewUserModel response, GetTokenModel getTokenModel) {
        given(requestSpecificationWithContentTypeApplicationJson)
                .header("Authorization", "Bearer " + getTokenModel.getToken())
                .when()
                .delete("/Account/v1/User/"+response.getUserID())
                .then()
                .spec(responseSpecificationWithStatusCode200);
    }

    @Step("Получение токена авторизации")
    public GetTokenModel getTokenAuthorization() {
        RegistrationNewUserModel regModel = new RegistrationNewUserModel();
        regModel.setUserName(userName);
        regModel.setPassword(password);
       return (given(requestSpecificationWithContentTypeApplicationJson)
                .when()
                .body(regModel)
                .post("/Account/v1/GenerateToken")
                .then()
                .spec(responseSpecificationWithStatusCode200)
               .extract().as(GetTokenModel.class));
    }
}
