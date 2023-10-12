package steps;

import io.cucumber.datatable.DataTable;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Then;
import io.restassured.module.jsv.JsonSchemaValidator;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Assertions;

import java.util.List;
import java.util.Map;

public class TrelloApiAssertSteps {

    private final ScenarioContext scenarioContext;

    public TrelloApiAssertSteps(ScenarioContext scenarioContext) {
        this.scenarioContext = scenarioContext;
    }

    @Then("the response status code is {int}")
    public void theResponseStatusCodeIs(int expectedStatusCode) {
        scenarioContext.getResponse().then().statusCode(expectedStatusCode);
    }

    @And("the response matches '{}' schema")
    public void theResponseMatchesSchema(String schemaName) {
        scenarioContext.getResponse().then().body(JsonSchemaValidator.matchesJsonSchemaInClasspath("schemas/" + schemaName));
    }

    @And("body value has the following values by paths:")
    public void bodyValueByPathIsEqualTo(DataTable dataTable) {
        List<Map<String, String>> rows = dataTable.asMaps();
        for (Map<String, String> row : rows) {
            String rowExpectedValue = row.get("expected_value");
            String expectedValue = rowExpectedValue.equals("null") ? null : rowExpectedValue;
            scenarioContext.getResponse().then().body(row.get("path"), Matchers.equalTo(expectedValue));
        }
    }

    @And("the response body is equal to {string}")
    public void theResponseBodyIsEqualTo(String expectedValue) {
        Assertions.assertEquals(expectedValue, scenarioContext.getResponse().body().asString());
    }

    @And("the response body doesn't have any item by paths:")
    public void theResponseBodyDoesntHaveAnyItemByPaths(DataTable dataTable) {
        for (Map.Entry<String, String> row : dataTable.asMap().entrySet()) {
            String rowValue = row.getValue();
            String expectedValue = rowValue.equals("created_board_id") ? scenarioContext.getBoardId() : rowValue;
            scenarioContext.getResponse().then().body("id", Matchers.not(Matchers.hasItem(expectedValue)));
        }
    }

    @And("the response body has any item by paths:")
    public void theResponseBodyHasAnyItemByPaths(DataTable dataTable) {
        for (Map.Entry<String, String> row : dataTable.asMap().entrySet()) {
            String rowValue = row.getValue();
            String expectedValue = rowValue.equals("created_board_id") ? scenarioContext.getBoardId() : rowValue;
            scenarioContext.getResponse().then().body("id", Matchers.hasItem(expectedValue));
        }
    }
}
