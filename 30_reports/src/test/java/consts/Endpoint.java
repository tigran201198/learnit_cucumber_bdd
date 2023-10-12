package consts;

public enum Endpoint {
    GET_ALL_BOARDS("/1/members/{member}/boards"),
    GET_A_BOARD("/1/boards/{id}"),
    CREATE_A_BOARD("/1/boards"),
    DELETE_A_BOARD("/1/boards/{id}"),
    UPDATE_A_BOARD("/1/boards/{id}");

    private final String url;

    Endpoint(String url) {
        this.url = url;
    }

    public String getUrl() {
        return url;
    }
}
