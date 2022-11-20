package com.eleven.gateway.core;

public interface RouteFilterFactory {

    RouteFilter apply(String configString);

    String getName();
}
