package steps;

import consts.Endpoint;
import io.cucumber.java.ParameterType;

public class StepArgumentsTransformer {

    @ParameterType("(with|without)")
    public boolean with(String string) {
        return string.equals("with");
    }

    @ParameterType("(GET_A_BOARD|GET_ALL_BOARDS|CREATE_A_BOARD|DELETE_A_BOARD|UPDATE_A_BOARD)")
    public Endpoint endpoint(String endpoint) {
        return Endpoint.valueOf(endpoint);
    }
}
