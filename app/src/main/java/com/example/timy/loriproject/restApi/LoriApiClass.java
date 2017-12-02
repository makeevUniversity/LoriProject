package com.example.timy.loriproject.restApi;

import android.app.Application;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public class LoriApiClass extends Application {

    private static LoriRestApi loriRestApi;
    private Retrofit retrofit;

    @Override
    public void onCreate() {
        super.onCreate();

        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(this);

        String host = sp.getString("host", "localhost");
        String port = sp.getString("port", "8080");

        if(!host.isEmpty() && !port.isEmpty()) {
            retrofit = new Retrofit.Builder()
                    .baseUrl("http://" + host + ":" + port)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
            loriRestApi = retrofit.create(LoriRestApi.class);
        }
    }

    public static LoriRestApi getApi() {
        return loriRestApi;
    }
}
