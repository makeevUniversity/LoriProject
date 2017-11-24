package com.example.timy.loriproject.restApi;

import com.example.timy.loriproject.restApi.domain.JsonVo;

import retrofit2.Call;
import retrofit2.http.POST;

/**
 * Created by dmitr on 22.11.2017.
 */

public interface LoriRestApi {

    @POST("/address")
    Call<JsonVo> getEvent();


}
