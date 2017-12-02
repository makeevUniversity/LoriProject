package com.example.timy.loriproject.restApi;


public class QueriesAndTypes {
    static final String STATIC_PATH = "app/dispatch/api/";
    static final String QUERY_PATH = "query.json";

    public static final String QUERY_GET_USERS = "select+te+from+sec$User+te";
    public static final String QUERY_GET_USER = "select+te+from+sec$User+te+where+te.login=:login";
    public static final String QUERY_GET_PROJECTS = "select+te+from+ts$Project+te";
    public static final String QUERY_GET_TASKS = "select+te+from+ts$Task+te&view=_minimal";
    public static final String QUERY_GET_TIME_ENTRIES = "select+te+from+ts$TimeEntry+te+where+te.createdBy=:name+and+te.date+between+:from+and+:to&view=timeEntry-browse";

    public static final String TYPE_USER = "sec$User";
    public static final String TYPE_PROJECT = "ts$Project";
    public static final String TYPE_TASK = "ts$Task";
    public static final String TYPE_TIME_ENTRIES = "ts$TimeEntry";
}
