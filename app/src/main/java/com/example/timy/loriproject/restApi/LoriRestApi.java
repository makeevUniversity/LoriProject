package com.example.timy.loriproject.restApi;

import com.example.timy.loriproject.restApi.domain.Tag;
import com.example.timy.loriproject.restApi.domain.Task;
import com.example.timy.loriproject.restApi.domain.TimeEntry;
import com.example.timy.loriproject.restApi.domain.User;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface LoriRestApi {

    String STATIC_PATH = "app/dispatch/api/";
    String QUERY_PATH = "query.json?";

    String TYPE_USER = "sec$User";
    String TYPE_PROJECT = "ts$Project";
    String TYPE_TASK = "ts$Task";
    String TYPE_TIME_ENTRIES = "ts$TimeEntry";
    String TYPE_TAG = "ts$Tag";

    String QUERY_GET_TIME_ENTRIES = "select+a+from+ts$TimeEntry+a+where+a.createdBy=:name+and+a.date+between+:from+and+:to&view=timeEntry-browse";
    String QUERY_GET_USERS = "select+a+from+sec$User+a";
    String QUERY_GET_USER = "select+a+from+sec$User+a+where+a.login=:login";
    String QUERY_GET_PROJECTS = "select+a+from+ts$Project+a";
    String QUERY_GET_TASKS = "select+a+from+ts$Task+a&view=_minimal";
    String QUERY_GET_TAGS = "select+a+from+ts$Tag+a";


    @GET(STATIC_PATH + "login")
    Call<String> login(@Query("u") String user,
                       @Query("p") String password);

    @GET(STATIC_PATH + "logout")
    Call<Void> logout(@Query("session") String tokken);

    @GET(STATIC_PATH + QUERY_PATH + "e=" + TYPE_USER + "&q=" + QUERY_GET_USER)
    Call<List<User>> getUserEntity(
            @Query("s") String tokken,
            @Query("login") String login);

    @GET(STATIC_PATH + QUERY_PATH + "e=" + TYPE_TAG + "&q=" + QUERY_GET_TAGS)
    Call<List<Tag>> getTags(@Query("s") String tokken);


    @GET(STATIC_PATH + QUERY_PATH + "e=" + TYPE_TIME_ENTRIES + "&q=" + QUERY_GET_TIME_ENTRIES)
    Call<List<TimeEntry>> getTimeEntries(
            @Query("s") String tokken,
            @Query("name") String user,
            @Query("from") String fromDate,
            @Query("to") String toDate
    );

    @POST(STATIC_PATH + "commit?")
    @Headers("Content-Type: application/json")
    Call<String> commit(@Query("s") String tokken,
                        @Body String body);

    @GET(STATIC_PATH + QUERY_PATH + "e=" + TYPE_TASK + "&q=" + QUERY_GET_TASKS)
    Call<List<Task>> getTasks(@Query("s") String tokken);


}


