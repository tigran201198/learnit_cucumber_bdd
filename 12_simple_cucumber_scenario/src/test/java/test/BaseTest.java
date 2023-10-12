package test;

import consts.UrlParamValues;
import io.restassured.RestAssured;
import io.restassured.specification.RequestSpecification;
import org.junit.jupiter.api.BeforeAll;

public class BaseTest {

    @BeforeAll
    public static void setBaseUrl() {
        RestAssured.baseURI = "https://api.trello.com";
    }

    protected RequestSpecification requestWithAuth() {
        return requestWithoutAuth()
                .queryParams(UrlParamValues.AUTH_QUERY_PARAMS);
    }

    protected RequestSpecification requestWithoutAuth() {
        return RestAssured.given();
    }
}
