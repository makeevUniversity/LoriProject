package com.example.timy.loriproject.restApi;


public class QueriesAndTypes {
    public static final String STATIC_PATH = "/app/dispatch/api/";
    public static final String QUERY_PATH = "query.json?";

    public static final String TYPE_USER = "sec$User";
    public static final String TYPE_PROJECT = "ts$Project";
    public static final String TYPE_TASK = "ts$Task";
    public static final String TYPE_TIME_ENTRIES = "ts$TimeEntry";

    public static final String QUERY_GET_USERS = "select+a+from+sec$User+a";
    public static final String QUERY_GET_USER = "select+a+from+sec$User+a+where+a.login=:login";
    public static final String QUERY_GET_PROJECTS = "select+a+from+ts$Project+a";
    public static final String QUERY_GET_TASKS = "select+a+from+ts$Task+a&view=_minimal";
    public static final String QUERY_GET_TIME_ENTRIES = "select+a+from+ts$TimeEntry+a+where+a.createdBy=:name+and+a.date+between+:from+and+:to&view=timeEntry-browse";


}
