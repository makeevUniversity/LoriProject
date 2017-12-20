package com.example.timy.loriproject.restApi;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;


public class LoriApiClass extends Application {

    private static LoriRestApi loriRestApi;
    private static Retrofit retrofit;

    @Override
    public void onCreate() {
        super.onCreate();

        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(this);

        String host = sp.getString("host", "");
        String port = sp.getString("port", "");

        if(!host.isEmpty() && !port.isEmpty()) {
            retrofit = new Retrofit.Builder()
                    .baseUrl("http://" + host + ":" + port+"/")
                    .addConverterFactory(ScalarsConverterFactory.create())
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
            loriRestApi = retrofit.create(LoriRestApi.class);
        }
    }

    public static void rebuildRetrofit(Context context){

        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(context);

        String host = sp.getString("host", "");
        String port = sp.getString("port", "");

        if(!host.isEmpty() && !port.isEmpty()) {
            retrofit = new Retrofit.Builder()
                    .baseUrl("http://" + host + ":" + port+"/")
                    .addConverterFactory(ScalarsConverterFactory.create())
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
            loriRestApi = retrofit.create(LoriRestApi.class);
        }
    }

    public static LoriRestApi getApi() {
        return loriRestApi;
    }
}
