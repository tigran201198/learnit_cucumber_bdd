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

public class TrelloApiActionSteps extends ScenarioContext {
    
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
        setRequest(requestWithAuth());
    }

    @Given("the request without authorization")
    public void theRequestWithoutAuthorization() {
        setRequest(requestWithoutAuth());
    }

    @And("the request has query params:")
    public void theRequestHasQueryParam(DataTable dataTable) {
        Map<String, String> queryParams = dataTable.asMap();
        setRequest(getRequest().queryParams(queryParams));
    }

    @And("the request has body params:")
    public void theRequestHasBodyParam(DataTable dataTable) {
        setRequest(getRequest().body(dataTable.asMap()));
    }

    @And("the request has headers:")
    public void theRequestHasHeader(DataTable dataTable) {
        setRequest(getRequest().headers(dataTable.asMap()));
    }

    @And("the request has path params:")
    public void theRequestHasPathParam(DataTable dataTable) {
        Map<String, String> pathParams = new HashMap<>();
        List<Map<String, String>> rows = dataTable.asMaps();
        for (Map<String, String> row : rows) {
            pathParams.put(row.get("name"), row.get("value"));
        }
        setRequest(getRequest().pathParams(pathParams));
    }

    @When("the '{}' request is sent to {string} endpoint")
    public void theRequestIsSentToEndpoint(CurlOption.HttpMethod method, String endpoint) {
        switch (method) {
            case GET -> setResponse(getRequest().get(endpoint));
            case PUT -> setResponse(getRequest().put(endpoint));
            case POST -> setResponse(getRequest().post(endpoint));
            case DELETE -> setResponse(getRequest().delete(endpoint));
            default -> throw new RuntimeException();
        }
    }
}
