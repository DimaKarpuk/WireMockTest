import com.github.tomakehurst.wiremock.client.WireMock;
import com.github.tomakehurst.wiremock.junit5.WireMockExtension;
import com.github.tomakehurst.wiremock.junit5.WireMockTest;
import io.restassured.http.ContentType;
import models.GenerateRandomBookModel;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.RegisterExtension;

import static com.github.tomakehurst.wiremock.client.WireMock.*;
import static com.github.tomakehurst.wiremock.core.WireMockConfiguration.wireMockConfig;
import static io.restassured.RestAssured.given;
import static specs.BookStoreSpecs.requestSpecificationWithContentTypeApplicationJson;
import static specs.BookStoreSpecs.responseSpecificationWithStatusCode200;

@WireMockTest
public class MockTest extends TestBase {

    private final String ISBN = "9781449325862";

    GenerateRandomBookModel genRandBook = new GenerateRandomBookModel();

    @RegisterExtension
    static WireMockExtension wireMockExtension = WireMockExtension.newInstance()
            .options(wireMockConfig().dynamicPort()).build();


    public void stub(String ISBN) {
        wireMockExtension.stubFor(
                WireMock.get(urlEqualTo("/BookStore/v1/Book?ISBN=" + ISBN))
                        .willReturn(aResponse()
                                .withHeader("Content-Type", "application/json")
                                .withBody(genRandBook.jsonBodyBook())
                                .withStatus(200))
        );
    }

    @Test
    public void getBookWithWiremock() {
        stub(ISBN);
       given(requestSpecificationWithContentTypeApplicationJson)
                .contentType(ContentType.JSON)
                .queryParam("ISBN", ISBN)
                .when()
                .get(wireMockExtension.baseUrl() + "/BookStore/v1/Book")
                .then()
                .spec(responseSpecificationWithStatusCode200);

        wireMockExtension.verify(getRequestedFor(urlEqualTo("/BookStore/v1/Book?ISBN=" + ISBN)));
    }

    @Test
    public void getBook() {
        given(requestSpecificationWithContentTypeApplicationJson)
                .contentType(ContentType.JSON)
                .queryParam("ISBN", ISBN)
                .when()
                .get("/BookStore/v1/Book")
                .then()
                .spec(responseSpecificationWithStatusCode200);
    }
}