package steps;

import consts.Endpoint;
import io.cucumber.core.options.CurlOption;
import io.cucumber.datatable.DataTable;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.When;
import utils.RestRequestProvider;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TrelloApiActionSteps {
    
    private final ScenarioContext scenarioContext;

    public TrelloApiActionSteps(ScenarioContext scenarioContext) {
        this.scenarioContext = scenarioContext;
    }

    @Given("a request {with} authorization")
    public void aRequestWithAuthorization(boolean withAuth) {
        scenarioContext.setRequest(withAuth ? RestRequestProvider.requestWithAuth() : RestRequestProvider.requestWithoutAuth());
    }

    @And("the request has query params:")
    public void theRequestHasQueryParam(Map<String, String> queryParams) {
        scenarioContext.setRequest(scenarioContext.getRequest().queryParams(queryParams));
    }

    @And("the request has body params:")
    public void theRequestHasBodyParam(DataTable dataTable) {
        scenarioContext.setRequest(scenarioContext.getRequest().body(dataTable.asMap()));
    }

    @And("the request has headers:")
    public void theRequestHasHeader(DataTable dataTable) {
        scenarioContext.setRequest(scenarioContext.getRequest().headers(dataTable.asMap()));
    }

    @And("the request has path params:")
    public void theRequestHasPathParam(DataTable dataTable) {
        Map<String, String> pathParams = new HashMap<>();
        List<Map<String, String>> rows = dataTable.asMaps();
        for (Map<String, String> row : rows) {
            String rowValue = row.get("value");
            String value = rowValue.equals("created_board_id") ? scenarioContext.getBoardId() : rowValue;
            pathParams.put(row.get("name"), value);
        }
        scenarioContext.setRequest(scenarioContext.getRequest().pathParams(pathParams));
    }

    @When("the '{}' request is sent to '{endpoint}' endpoint")
    public void theRequestIsSentToEndpoint(CurlOption.HttpMethod method, Endpoint endpoint) {
        String url = endpoint.getUrl();
        switch (method) {
            case GET -> scenarioContext.setResponse(scenarioContext.getRequest().get(url));
            case PUT -> scenarioContext.setResponse(scenarioContext.getRequest().put(url));
            case POST -> scenarioContext.setResponse(scenarioContext.getRequest().post(url));
            case DELETE -> scenarioContext.setResponse(scenarioContext.getRequest().delete(url));
            default -> throw new RuntimeException();
        }
    }

    @When("the board ID from the response is remembered")
    public void theBoardIdFromTheResponseIsRemembered() {
        String createdBoardId = scenarioContext.getResponse().body()
                .jsonPath().getString("id");
        scenarioContext.setBoardId(createdBoardId);
    }
}
