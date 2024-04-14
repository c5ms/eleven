package com.eleven.gateway.core;

import java.util.Collection;
import java.util.Optional;

public interface GatewayResource {

    String getId();

    String getName();

    String getIndex();

    Collection<String> getFiles();

    Optional<GatewayContent> load(String path);

}
