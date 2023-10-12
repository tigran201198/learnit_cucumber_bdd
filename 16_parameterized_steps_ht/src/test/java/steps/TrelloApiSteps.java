package steps;

import consts.UrlParamValues;
import io.cucumber.core.options.CurlOption;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.restassured.RestAssured;
import io.restassured.module.jsv.JsonSchemaValidator;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.hamcrest.Matchers;

import java.util.Map;

public class TrelloApiSteps {

    private RequestSpecification request;
    private Response response;

    private RequestSpecification requestWithAuth() {
        return requestWithoutAuth()
                .queryParams(UrlParamValues.AUTH_QUERY_PARAMS);
    }

    private RequestSpecification requestWithoutAuth() {
        RestAssured.baseURI = "https://api.trello.com";
        return RestAssured.given();
    }

    @Given("a request with authorization")
    public void aRequestWithAuthorization() {
        request = requestWithAuth();
    }

    @And("the request has '{word}' query param with value {string}")
    public void theRequestHasQueryParam(String paramName, String paramValue) {
        request = request.queryParam(paramName, paramValue);
    }

    @And("the request has {string} body param with value {string}")
    public void theRequestHasBodyParam(String bodyParam, String value) {
        request = request.body(Map.of(bodyParam, value));
    }

    @And("the request has {string} header with value {string}")
    public void thrRequestHasHeader(String headerName, String value) {
        request = request.header(headerName, value);
    }

    @And("the request has {string} path param with value {string}")
    public void theRequestHasPathParam(String paramName, String paramValue) {
        request = request.pathParam(paramName, paramValue);
    }

    @When("the '{}' request is sent to {string} endpoint")
    public void theRequestIsSentToEndpoint(CurlOption.HttpMethod method, String endpoint) {
        switch (method) {
            case GET ->  response = request.get(endpoint);
            case PUT ->  response = request.put(endpoint);
            default -> throw new RuntimeException();
        }
    }

    @Then("the response status code is {int}")
    public void theResponseStatusCodeIs(int expectedStatusCode) {
        response.then().statusCode(expectedStatusCode);
    }

    @And("the response matches '{}' schema")
    public void theResponseMatchesSchema(String schemaName) {
        response.then().body(JsonSchemaValidator.matchesJsonSchemaInClasspath("schemas/" + schemaName));
    }

    @And("body value by path {string} is equal to {string}")
    public void bodyValueByPathIsEqualTo(String bodyPath, String expectedValue) {
        response.then().body(bodyPath, Matchers.equalTo(expectedValue));
    }
}
