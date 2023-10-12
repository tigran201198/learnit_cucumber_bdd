package steps;

import consts.Endpoint;
import io.cucumber.java.Before;
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
                .contentType(ContentType.JSON)
                .body(Map.of("name", "New board"))
                .post(Endpoint.CREATE_A_BOARD.getUrl())
                .then().statusCode(200)
                .extract().jsonPath().getString("id");
        scenarioContext.setBoardId(boardId);
    }
}
