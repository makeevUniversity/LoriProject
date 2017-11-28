package com.example.timy.loriproject.restApi;

import com.example.timy.loriproject.restApi.domain.JsonVo;
import com.example.timy.loriproject.restApi.domain.User;

import java.util.List;
import java.util.Map;
import java.util.UUID;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;
import retrofit2.http.QueryMap;

/**
 * Created by dmitr on 22.11.2017.
 */

public interface LoriRestApi {

    String STATIC_PATH = "app/dispatch/api/";

    @GET(STATIC_PATH + "login")
    Call<JsonVo> login(@Query("u") String user,
                       @Query("p") String password);

//    @GET(STATIC_PATH + "logout")
//    Call<Void> logout(@Query("session") UUID session);

//    @POST(STATIC_PATH+"query.json")
//    Call<List<String>> loadUsersByLogin(@Body LoadUserLoginRequest request);

//    @GET(STATIC_PATH + "query.json")
//    Call<List<String>> executeQuery(@Query("e") String entity,
//                                    @Query("q") String query,
//                                    @QueryMap Map<String, Object> parameters,
//                                    @Query("view") String view);

//    @POST(STATIC_PATH+"service.json")
//    Call<List<String>> loadActiveProfects(@Body )


}


