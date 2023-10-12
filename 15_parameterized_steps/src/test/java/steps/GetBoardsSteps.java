package steps;

import consts.BoardsEndpoints;
import consts.UrlParamValues;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.restassured.RestAssured;
import io.restassured.module.jsv.JsonSchemaValidator;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.hamcrest.Matchers;

public class GetBoardsSteps {

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

    @And("the request has {string} path param with value {string}")
    public void theRequestHasPathParam(String paramName, String paramValue) {
        request = request.pathParam(paramName, paramValue);
    }

    @When("the request is sent to getBoards endpoint")
    public void theRequestIsSentToGetBoardsEndpoint() {
        response = request.get(BoardsEndpoints.GET_ALL_BOARDS_URL);
    }

    @When("the request is sent to getBoard endpoint")
    public void theRequestIsSentToGetBoardEndpoint() {
        response = request.get(BoardsEndpoints.GET_BOARD_URL);
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
