package consts;

import java.util.Map;

public class UrlParamValues {

    public static final String VALID_KEY = "fb04999a731923c2e3137153b1ad5de0";
    public static final String VALID_TOKEN = "b73120fb537fceb444050a2a4c08e2f96f47389931bd80253d2440708f2a57e1";

    public static final Map<String, String> AUTH_QUERY_PARAMS = Map.of(
            "key", VALID_KEY,
            "token", VALID_TOKEN
    );

    public static final Map<String, String> ANOTHER_USER_AUTH_QUERY_PARAMS = Map.of(
            "key", "8b32218e6887516d17c84253faf967b6",
            "token", "492343b8106e7df3ebb7f01e219cbf32827c852a5f9e2b8f9ca296b1cc604955"
    );

    public static final String EXISTING_BOARD_ID = "6288cc5d3ce8fc87542dff31";
    public static final String BOARD_ID_TO_UPDATE = "60d84769c4ce7a09f9140220";
    public static final String USER_NAME = "learnpostman";
}
