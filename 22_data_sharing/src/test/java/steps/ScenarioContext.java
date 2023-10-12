package steps;

import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

public class ScenarioContext {

    private static final ThreadLocal<RequestSpecification> REQUEST = new ThreadLocal<>();
    private static final ThreadLocal<Response> RESPONSE = new ThreadLocal<>();

    public RequestSpecification getRequest() {
        return REQUEST.get();
    }

    public void setRequest(RequestSpecification request) {
        REQUEST.set(request);
    }

    public Response getResponse() {
        return RESPONSE.get();
    }

    public void setResponse(Response response) {
        RESPONSE.set(response);
    }
}
