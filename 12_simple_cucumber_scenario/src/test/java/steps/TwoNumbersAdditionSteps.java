package steps;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.junit.jupiter.api.Assertions;

public class TwoNumbersAdditionSteps {

    private int numberOne;
    private int numberTwo;
    private int result;

    @Given("user has number one as 10")
    public void aRequestWithAuthorization() {
        numberOne = 10;
    }

    @Given("user has number two as 20")
    public void theRequestHasFieldsQueryParam() {
        numberTwo = 20;
    }

    @When("user adds number one and number two")
    public void theRequestIsSentToGetBoardsEndpoint() {
        result = numberOne + numberTwo;
    }

    @Then("the result is 30")
    public void theGetBoardsResponseStatusCodeIsOk() {
        Assertions.assertEquals(result, 30);
    }
}
