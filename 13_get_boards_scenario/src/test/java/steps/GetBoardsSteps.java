package steps;

import consts.BoardsEndpoints;
import consts.UrlParamValues;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.restassured.RestAssured;
import io.restassured.module.jsv.JsonSchemaValidator;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

public class GetBoardsSteps {

    private RequestSpecification request;
    private Response response;

    protected RequestSpecification requestWithAuth() {
        return requestWithoutAuth()
                .queryParams(UrlParamValues.AUTH_QUERY_PARAMS);
    }

    protected RequestSpecification requestWithoutAuth() {
        RestAssured.baseURI = "https://api.trello.com";
        return RestAssured.given();
    }

    @Given("a request with authorization")
    public void aRequestWithAuthorization() {
        request = requestWithAuth();
    }

    @Given("the request has fields query param")
    public void theRequestHasFieldsQueryParam() {
        request = request.queryParam("fields", "id,name");
    }

    @Given("the request has member path param")
    public void theRequestHasMemberPathParams() {
        request = request.pathParam("member", UrlParamValues.USER_NAME);
    }

    @When("the request is sent to getBoards endpoint")
    public void theRequestIsSentToGetBoardsEndpoint() {
        response = request.get(BoardsEndpoints.GET_ALL_BOARDS_URL);
    }

    @Then("the getBoards response status code is 200")
    public void theGetBoardsResponseStatusCodeIsOk() {
        response.then().statusCode(200);
    }

    @Then("the getBoards response matches get_boards.json scheme")
    public void theGetBoardsResponseMatchesGetBoardsScheme() {
        response.then()
                .body(JsonSchemaValidator.matchesJsonSchemaInClasspath("schemas/get_boards.json"));
    }
}
