package test.create;

import arguments.holders.AuthValidationArgumentsHolder;
import arguments.providers.AuthValidationArgumentsProvider;
import arguments.providers.BoardNameValidationArgumentsProvider;
import consts.BoardsEndpoints;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ArgumentsSource;
import test.BaseTest;

import java.util.Map;

public class CreateBoardValidationTest extends BaseTest {

    @ParameterizedTest
    @ArgumentsSource(BoardNameValidationArgumentsProvider.class)
    public void checkCreateBoardWithInvalidName(Map bodyParams) {
        Response response = requestWithAuth()
                .body(bodyParams)
                .contentType(ContentType.JSON)
                .post(BoardsEndpoints.CREATE_BOARD_URL);
        response.then()
                .statusCode(400)
                .body("message", Matchers.equalTo("invalid value for name"));
    }

    @ParameterizedTest
    @ArgumentsSource(AuthValidationArgumentsProvider.class)
    public void checkCreateBoardWithInvalidAuth(AuthValidationArgumentsHolder validationArguments) {
        Response response = requestWithoutAuth()
                .queryParams(validationArguments.getAuthParams())
                .body(Map.of("name", "new board"))
                .contentType(ContentType.JSON)
                .post(BoardsEndpoints.CREATE_BOARD_URL);
        response
                .then()
                .statusCode(401);
        Assertions.assertEquals(validationArguments.getErrorMessage(), response.body().asString());
    }
}
