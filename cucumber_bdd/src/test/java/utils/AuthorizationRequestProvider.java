package utils;

import consts.UrlParamValues;
import io.restassured.RestAssured;
import io.restassured.specification.RequestSpecification;

public class AuthorizationRequestProvider {

    public static RequestSpecification requestWithAuth() {
        return requestWithoutAuth()
                .queryParams(UrlParamValues.AUTH_QUERY_PARAMS);
    }

    public static RequestSpecification requestWithoutAuth() {
        RestAssured.baseURI = "https://api.trello.com";
        return RestAssured.given();
    }
}
