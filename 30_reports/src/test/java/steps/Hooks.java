package steps;

import consts.Endpoint;
import consts.UrlParamValues;
import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.cucumber.java.Scenario;
import io.restassured.http.ContentType;
import utils.RestRequestProvider;

import java.util.Map;

public class Hooks {

    private final ScenarioContext scenarioContext;

    public Hooks(ScenarioContext scenarioContext) {
        this.scenarioContext = scenarioContext;
    }

    @Before("@createBoard")
    public void createBoard() {
        String boardId = RestRequestProvider.requestWithAuth()
                .queryParams(UrlParamValues.AUTH_QUERY_PARAMS)
                .contentType(ContentType.JSON)
                .body(Map.of("name", "New board"))
                .post(Endpoint.CREATE_A_BOARD.getUrl())
                .then().statusCode(200)
                .extract().jsonPath().getString("id");
        scenarioContext.setBoardId(boardId);
    }

    @After("@deleteBoard")
    public void deleteBoard() {
        RestRequestProvider.requestWithAuth()
                .queryParams(UrlParamValues.AUTH_QUERY_PARAMS)
                .pathParam("id", scenarioContext.getBoardId())
                .delete(Endpoint.DELETE_A_BOARD.getUrl())
                .then().statusCode(200);
    }

    @After
    public void attachResponse(Scenario scenario) {
        scenario.attach(scenarioContext.getResponse().asPrettyString(), "text/plain", "Response");
    }
}
