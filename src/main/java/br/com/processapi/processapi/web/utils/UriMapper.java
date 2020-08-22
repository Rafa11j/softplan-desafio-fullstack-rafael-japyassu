package br.com.processapi.processapi.web.utils;

public class UriMapper {

    private static final String API_BASE_URL = "/api/v1";

    public static final String AUTH = API_BASE_URL + "/auth";

    public static final String[] PUBLIC_URI = {
            AUTH,
    };

}
