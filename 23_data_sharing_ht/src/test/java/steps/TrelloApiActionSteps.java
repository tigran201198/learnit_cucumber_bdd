package steps;

import consts.UrlParamValues;
import io.cucumber.core.options.CurlOption;
import io.cucumber.datatable.DataTable;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.When;
import io.restassured.RestAssured;
import io.restassured.specification.RequestSpecification;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TrelloApiActionSteps {
    
    private final ScenarioContext scenarioContext;

    public TrelloApiActionSteps(ScenarioContext scenarioContext) {
        this.scenarioContext = scenarioContext;
    }
    
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
        scenarioContext.setRequest(requestWithAuth());
    }

    @Given("the request without authorization")
    public void theRequestWithoutAuthorization() {
        scenarioContext.setRequest(requestWithoutAuth());
    }

    @And("the request has query params:")
    public void theRequestHasQueryParam(DataTable dataTable) {
        Map<String, String> queryParams = dataTable.asMap();
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
            pathParams.put(row.get("name"), row.get("value"));
        }
        scenarioContext.setRequest(scenarioContext.getRequest().pathParams(pathParams));
    }

    @When("the '{}' request is sent to {string} endpoint")
    public void theRequestIsSentToEndpoint(CurlOption.HttpMethod method, String endpoint) {
        switch (method) {
            case GET -> scenarioContext.setResponse(scenarioContext.getRequest().get(endpoint));
            case PUT -> scenarioContext.setResponse(scenarioContext.getRequest().put(endpoint));
            case POST -> scenarioContext.setResponse(scenarioContext.getRequest().post(endpoint));
            case DELETE -> scenarioContext.setResponse(scenarioContext.getRequest().delete(endpoint));
            default -> throw new RuntimeException();
        }
    }
}
