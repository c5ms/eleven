package com.eleven.core.web;

public interface WebConstants {

    // front : for consumer
    // admin : for administrator
    // inner : for microservice
    // open  : for openAPI
    String INNER_API_PREFIX = "/api/inner";
    String FRONT_API_PREFIX = "/api/front";
    String ADMIN_API_PREFIX = "/api/admin";
    String OPEN_API_PREFIX = "/api/open";

    String DEFAULT_DATE_FORMAT = "yyyy-MM-dd";
    String DEFAULT_TIME_FORMAT = "HH:mm:ss";

}
