package com.example.atmasalon.api;

public class UserApi
{
    //TODO: isi URL dengan api asli
    public static final String BASE_URL = "";
    public static final String API_URL = BASE_URL + "api/";

    //TODO: ini diganti2 juga, diganti berdasarkan route
    public static final String ADD_URL       = API_URL + "register";
    public static final String LOGIN_URL       = API_URL + "login";

    public static final String GET_URL       = API_URL + "produk";
    public static final String GET_BY_ID_URL = API_URL + "produk/";
    public static final String UPDATE_URL    = API_URL + "produk/";
    public static final String DELETE_URL    = API_URL + "produk/";
}
