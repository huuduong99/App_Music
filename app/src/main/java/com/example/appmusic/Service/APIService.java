package com.example.appmusic.Service;

public class APIService {

    private static String base_url="https://lendy3159.000webhostapp.com/Server/";

    public static DataService getService(){
        return APIRetrofitClient.getClient(base_url).create(DataService.class);
    }
}
