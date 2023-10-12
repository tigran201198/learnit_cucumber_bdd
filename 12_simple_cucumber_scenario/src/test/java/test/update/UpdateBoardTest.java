package test.update;

import consts.BoardsEndpoints;
import consts.UrlParamValues;
import io.restassured.http.ContentType;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;
import test.BaseTest;

import java.time.LocalDateTime;
import java.util.Map;

public class UpdateBoardTest extends BaseTest {

    @Test
    public void checkUpdateBoard() {
        String updatedName = "Updated Name" + LocalDateTime.now();
        requestWithAuth()
                .pathParam("id", UrlParamValues.BOARD_ID_TO_UPDATE)
                .body(Map.of("name", updatedName))
                .contentType(ContentType.JSON)
                .put(BoardsEndpoints.UPDATE_BOARD_URL)
                .then()
                .statusCode(200)
                .body("name", Matchers.equalTo(updatedName));
        requestWithAuth()
                .pathParam("id", UrlParamValues.BOARD_ID_TO_UPDATE)
                .get(BoardsEndpoints.GET_BOARD_URL)
                .then()
                .body("name", Matchers.equalTo(updatedName));
    }
}
