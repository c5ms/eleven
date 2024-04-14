package com.eleven.gateway.core;

import java.util.Collection;

public interface GatewayProvider<T> {

    Collection<T> getInstances();

}
