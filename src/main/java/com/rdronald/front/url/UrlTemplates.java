package com.rdronald.front.url;

public class UrlTemplates {
    public static final String SERVER = "http://localhost:8082";

    public static final String SCHEDULE_MANAGEMENT = "/schedule-management";

    public static final String LOCAL_URL_MAIN_PAGE = "/";
    public static final String LOCAL_URL_GET_SCHEDULE_FOR_GROUP = "/{audience}/{institute}/{group}/schedule";

    public static final String URL_GET_SCHEDULE_FOR_GROUP = SERVER + SCHEDULE_MANAGEMENT + "/get-schedule-for-group?" +
            "audience={audience}&institute={institute}&group={group}&date={date}";
}
