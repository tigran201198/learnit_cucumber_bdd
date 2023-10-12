package steps;

import consts.UrlParamValues;
import io.cucumber.datatable.DataTable;
import io.cucumber.java.DataTableType;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MapTransformer {

    @DataTableType
    public Map<String, String> map(DataTable dataTable) {
        List<List<String>> cells = dataTable.cells();
        Map<String, String> transformerMap = new HashMap<>();
        for (List<String> row : cells) {
            transformerMap.put(row.get(0), convertValue(row.get(1)));
        }
        return transformerMap;
    }

    private String convertValue(String value) {
        return switch (value) {
            case "current_user_key" -> UrlParamValues.VALID_KEY;
            case "current_user_token" -> UrlParamValues.VALID_TOKEN;
            case "empty_value" -> "";
            case "another_user_key" -> UrlParamValues.ANOTHER_USER_KEY;
            case "another_user_token" -> UrlParamValues.ANOTHER_USER_TOKEN;
            default -> value;
        };
    }
}
