package test.delete;

import arguments.holders.AuthValidationArgumentsHolder;
import arguments.holders.BoardIdValidationArgumentsHolder;
import arguments.providers.AuthValidationArgumentsProvider;
import arguments.providers.BoardIdValidationArgumentsProvider;
import consts.BoardsEndpoints;
import consts.UrlParamValues;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ArgumentsSource;
import test.BaseTest;

import java.util.Map;

public class DeleteBoardValidationTest extends BaseTest {

    @ParameterizedTest
    @ArgumentsSource(BoardIdValidationArgumentsProvider.class)
    public void checkDeleteBoardWithInvalidId(BoardIdValidationArgumentsHolder argumentsHolder) {
        Response response = requestWithAuth()
                .pathParams(argumentsHolder.getPathParams())
                .delete(BoardsEndpoints.DELETE_BOARD_URL);
        response
                .then()
                .statusCode(argumentsHolder.getStatusCode());
        Assertions.assertEquals(argumentsHolder.getErrorMessage(), response.body().asString());
    }

    @ParameterizedTest
    @ArgumentsSource(AuthValidationArgumentsProvider.class)
    public void checkDeleteBoardWithInvalidAuth(AuthValidationArgumentsHolder validationArguments) {
        Response response = requestWithoutAuth()
                .queryParams(validationArguments.getAuthParams())
                .pathParam("id", UrlParamValues.EXISTING_BOARD_ID)
                .delete(BoardsEndpoints.DELETE_BOARD_URL);
        response
                .then()
                .statusCode(401);
        Assertions.assertEquals(validationArguments.getErrorMessage(), response.body().asString());
    }

    @Test
    public void checkDeleteBoardWithAnotherUserCredentials() {
        Response response = requestWithoutAuth()
                .queryParams(UrlParamValues.ANOTHER_USER_AUTH_QUERY_PARAMS)
                .pathParam("id", UrlParamValues.EXISTING_BOARD_ID)
                .delete(BoardsEndpoints.DELETE_BOARD_URL);
        response
                .then()
                .statusCode(401);
        Assertions.assertEquals("invalid token", response.body().asString());
    }
}
