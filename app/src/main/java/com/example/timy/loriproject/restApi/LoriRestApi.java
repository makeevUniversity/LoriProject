package com.example.timy.loriproject.restApi;

import com.example.timy.loriproject.restApi.domain.Project;
import com.example.timy.loriproject.restApi.domain.Tag;
import com.example.timy.loriproject.restApi.domain.Task;
import com.example.timy.loriproject.restApi.domain.TimeEntry;
import com.example.timy.loriproject.restApi.domain.Token;
import com.example.timy.loriproject.restApi.domain.User;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface LoriRestApi {

    String STATIC_PATH = "app/rest/v2/";

    String QUERY_GET_TIME_ENTRIES = "select+a+from+ts$TimeEntry+a+where+a.createdBy=:name+and+a.date+between+:from+and+:to&view=timeEntry-browse";

    @Headers({
            "Authorization: Basic Y2xpZW50OnNlY3JldA=="
    })
    @FormUrlEncoded
    @POST(STATIC_PATH + "oauth/token")
    Call<Token> login(@Field("username") String user,
                      @Field("password") String password,
                      @Field("grant_type") String grantType);

    @GET(STATIC_PATH + "userInfo")
    Call<User> getUserEntity(@Header("Authorization") String token);

    @GET(STATIC_PATH + "entities/ts$Tag")
    Call<Tag> getTags(@Header("Authorization") String token);

    @GET(STATIC_PATH + "entities/ts$Project")
    Call<List<Project>> getProjects(@Header("Authorization") String tokken);

    @POST(STATIC_PATH + "entities/ts$TimeEntry")
    @Headers("Content-Type: application/json")
    Call<String> commit(@Body TimeEntry body,
                        @Header("Authorization") String tokken);

    @GET(STATIC_PATH + "entities/ts$TimeEntry?view=timeEntry-full&sort=-date")
    Call<List<TimeEntry>> getTimeEntries(
            @Header("Authorization") String tokken
    );

    @GET(STATIC_PATH + "entities/ts$Task?view=task-full")
    Call<List<Task>> getTasks(@Header("Authorization") String tokken);

    @DELETE(STATIC_PATH + "entities/ts$TimeEntry/{id}")
    Call<Void> deleteTimeEntry(@Path("id") String id, @Header("Authorization") String tokken);

    @PUT(STATIC_PATH + "entities/ts$TimeEntry/{id}")
    Call<TimeEntry> updateTimeEntry(@Path("id") String id, @Body TimeEntry timeEntry, @Header("Authorization") String tokken);
}


