package test.delete;

import consts.BoardsEndpoints;
import consts.UrlParamValues;
import io.restassured.http.ContentType;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import test.BaseTest;

import java.util.Map;

public class DeleteBoardTest extends BaseTest {

    private String createdBoardId;

    @BeforeEach
    public void createBoard() {
        createdBoardId = requestWithAuth()
                .body(Map.of("name", "New Board"))
                .contentType(ContentType.JSON)
                .post(BoardsEndpoints.CREATE_BOARD_URL)
                .body().jsonPath().get("id");
    }

    @Test
    public void checkDeleteBoard() {
        requestWithAuth()
                .pathParam("id", createdBoardId)
                .delete(BoardsEndpoints.DELETE_BOARD_URL)
                .then()
                .statusCode(200)
                .body("_value", Matchers.equalTo(null));
        requestWithAuth()
                .queryParam("fields", "id,name")
                .pathParam("member", UrlParamValues.USER_NAME)
                .get(BoardsEndpoints.GET_ALL_BOARDS_URL)
                .then()
                .body("id", Matchers.not(Matchers.hasItem(createdBoardId)));
    }
}
