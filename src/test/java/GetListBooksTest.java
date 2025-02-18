import com.github.tomakehurst.wiremock.client.WireMock;
import com.github.tomakehurst.wiremock.junit5.WireMockExtension;
import com.github.tomakehurst.wiremock.junit5.WireMockTest;
import models.BooksModels;
import models.GenerateRandomBooksModel;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.RegisterExtension;

import static com.github.tomakehurst.wiremock.client.WireMock.aResponse;
import static com.github.tomakehurst.wiremock.client.WireMock.urlEqualTo;
import static com.github.tomakehurst.wiremock.core.WireMockConfiguration.wireMockConfig;
import static io.restassured.RestAssured.given;
import static specs.BookStoreSpecs.*;

@WireMockTest
public class GetListBooksTest extends TestBase {
    GenerateRandomBooksModel genRandBook = new GenerateRandomBooksModel();

    @RegisterExtension
    static WireMockExtension wireMockExtension = WireMockExtension.newInstance()
            .options(wireMockConfig().dynamicPort()).build();

    public void stubForGetListBooks(int count) {
        wireMockExtension.stubFor(
                WireMock.get(urlEqualTo("/BookStore/v1/Books"))
                        .willReturn(aResponse()
                                .withHeader("Content-Type", "application/json")
                                .withBody(genRandBook.generateBooks(count))
                                .withStatus(200))
        );
    }

    @Test
    public void getListBooksTest() {
        stubForGetListBooks(10);
      BooksModels booksModels = given(requestSpecificationWithContentTypeApplicationJson)
                .when()
                .get(wireMockExtension.baseUrl() + "/BookStore/v1/Books")
                .then()
                .spec(responseSpecificationWithStatusCode200)
              .extract().as(BooksModels.class);

        Assertions.assertTrue((long) booksModels.getBooks().size() > 1);
    }
}
