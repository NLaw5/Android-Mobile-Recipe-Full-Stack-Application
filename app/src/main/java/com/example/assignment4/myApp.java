package com.example.assignment4;

import android.app.Application;

public class myApp extends Application {
    private NetworkingService networkingService = new NetworkingService();

    public JsonService getJsonService() {
        return jsonService;
    }

    private JsonService jsonService = new JsonService();


    public NetworkingService getNetworkingService() {
        return networkingService;
    }
}
