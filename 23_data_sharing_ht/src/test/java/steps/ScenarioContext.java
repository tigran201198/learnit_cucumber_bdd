package steps;

import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

public class ScenarioContext {

    private RequestSpecification request;
    private Response response;

    public RequestSpecification getRequest() {
        return request;
    }

    public void setRequest(RequestSpecification request) {
        this.request = request;
    }

    public Response getResponse() {
        return response;
    }

    public void setResponse(Response response) {
        this.response = response;
    }
}
