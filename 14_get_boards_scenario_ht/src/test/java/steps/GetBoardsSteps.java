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

    @And("the request has fields query param")
    public void theRequestHasFieldsQueryParam() {
        request = request.queryParam("fields", "id,name");
    }

    @And("the request has member path param")
    public void theRequestHasMemberPathParam() {
        request = request.pathParam("member", UrlParamValues.USER_NAME);
    }

    @And("the request has ID path param")
    public void theRequestHasIdPathParam() {
        request = request.pathParam("id", UrlParamValues.EXISTING_BOARD_ID);
    }

    @When("the request is sent to getBoards endpoint")
    public void theRequestIsSentToGetBoardsEndpoint() {
        response = request.get(BoardsEndpoints.GET_ALL_BOARDS_URL);
    }

    @When("the request is sent to getBoard endpoint")
    public void theRequestIsSentToGetBoardEndpoint() {
        response = request.get(BoardsEndpoints.GET_BOARD_URL);
    }

    @Then("the response status code is 200")
    public void theResponseStatusCodeIs() {
        response.then().statusCode(200);
    }

    @And("the getBoards response matches get_boards.json schema")
    public void theGetBoardsResponseMatchesGet_boardsJsonSchema() {
        response.then().body(JsonSchemaValidator.matchesJsonSchemaInClasspath("schemas/get_boards.json"));
    }

    @And("the getBoard response matches get_board.json schema")
    public void theGetBoardResponseMatchesGetBoardJsonSchema() {
        response.then().body(JsonSchemaValidator.matchesJsonSchemaInClasspath("schemas/get_board.json"));
    }

    @And("body value by path name is equal to New Board")
    public void bodyValueByPathNameIsEqualToNewBoard() {
        response.then().body("name", Matchers.equalTo("New Board"));
    }
}
