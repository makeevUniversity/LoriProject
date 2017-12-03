package com.example.timy.loriproject.restApi;

import com.example.timy.loriproject.restApi.domain.Project;
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

import static com.example.timy.loriproject.restApi.QueriesAndTypes.QUERY_PATH;
import static com.example.timy.loriproject.restApi.QueriesAndTypes.STATIC_PATH;

public interface LoriRestApi {

    @GET(STATIC_PATH + "login")
    Call<String> login(@Query("u") String user,
                       @Query("p") String password);

    @GET(STATIC_PATH + "logout")
    Call<Void> logout(@Query("session") String tokken);

    @GET(STATIC_PATH + QUERY_PATH)
    Call<List<User>> getUsersEntity(@Query("e") String entityName,
                                    @Query("q") String query,
                                    @Query("s") String tokken);

    @GET(STATIC_PATH + QUERY_PATH)
    Call<List<User>> getUserEntity(@Query("e") String entityName,
                                   @Query("q") String query,
                                   @Query("s") String tokken,
                                   @Query("login") String login);

    @GET(STATIC_PATH + QUERY_PATH)
    Call<List<TimeEntry>> getTimeEntries(@Query("e") String entityName,
                                         @Query("q") String query,
                                         @Query("s") String tokken,
                                         @Query("name") String user,
                                         @Query("from") String fromDate,
                                         @Query("to") String toDate);

    @POST(STATIC_PATH + "commit")
    @Headers("Content-Type: application/json")
    Call<String> commit(@Query("s") String tokken,
                        @Body String body);

    @GET(STATIC_PATH + QUERY_PATH)
    Call<List<Project>> getProjects(@Query("e") String entityName,
                                    @Query("q") String query,
                                    @Query("s") String tokken);

    @GET(STATIC_PATH + QUERY_PATH)
    Call<List<Task>> getTasks(@Query("e") String entityName,
                              @Query("q") String query,
                              @Query("s") String tokken);


}


