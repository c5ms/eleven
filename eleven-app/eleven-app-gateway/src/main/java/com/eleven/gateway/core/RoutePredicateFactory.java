package com.eleven.gateway.core;

public interface RoutePredicateFactory {

    RoutePredicate apply(String configString);

    String getName();
}
