package com.example.mahasiswaapp.apihelper;

public class UtilsApi {

    public static final String BASE_URL_API = "http://172.23.3.10/cobasia/";
    //public static final String BASE_URL_API = "http://192.168.1.7/cobasia/";

    public static BaseApiService getAPIService() {
        return RetrofitClient.getClient(BASE_URL_API).create(BaseApiService.class);
    }

}
