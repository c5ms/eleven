package com.eleven.gateway.core;

import java.util.Collection;

public interface GatewayTokenLoader {

    Collection<GatewayToken> loadTokens();

}
