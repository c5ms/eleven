package com.eleven.gateway.core;

import java.util.Collection;

public interface GatewayServiceLoader {

    Collection<GatewayService> load();

}
