package steps;

import consts.UrlParamValues;
import io.cucumber.core.options.CurlOption;
import io.cucumber.datatable.DataTable;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.restassured.RestAssured;
import io.restassured.module.jsv.JsonSchemaValidator;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Assertions;

import java.util.HashMap;
import java.util.List;
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

    @Given("a request without authorization")
    public void aRequestWithoutAuthorization() {
        request = requestWithoutAuth();
    }

    @And("the request has query params:")
    public void theRequestHasQueryParams(DataTable dataTable) {
        Map<String, String> queryParams = dataTable.asMap();
        request = request.queryParams(queryParams);
    }

    @And("the request has path params:")
    public void theRequestHasPathParams(DataTable dataTable) {
        Map<String, String> pathParams = new HashMap<>();
        List<Map<String, String>> rows = dataTable.asMaps();
        for (Map<String, String> columns : rows) {
            pathParams.put(columns.get("name"), columns.get("value"));
        }
        request = request.pathParams(pathParams);
    }

    @And("the request has body params:")
    public void theRequestHasBodyParams(DataTable dataTable) {
        request = request.body(dataTable.asMap());
    }

    @And("the request has headers:")
    public void thrRequestHasHeaders(DataTable dataTable) {
        request = request.headers(dataTable.asMap());
    }

    @When("the '{}' request is sent to {string} endpoint")
    public void theRequestIsSentToEndpoint(CurlOption.HttpMethod method, String endpoint) {
        switch (method) {
            case GET -> response = request.get(endpoint);
            case PUT -> response = request.put(endpoint);
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

    @And("body value has the following values by paths:")
    public void bodyValueByPathIsEqualTo(DataTable dataTable) {
        List<Map<String, String>> rows = dataTable.asMaps();
        for (Map<String, String> columns : rows) {
            response.then().body(columns.get("path"), Matchers.equalTo(columns.get("expected_value")));

        }
    }

    @And("the response body is equal to {string}")
    public void theResponseBodyIsEqualTo(String expectedValue) {
        Assertions.assertEquals(expectedValue, response.body().asString());
    }
}
