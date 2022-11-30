package com.demcia.eleven.core.domain.time;

public interface TimestampProvider {

    long provide() throws TimestampProviderException;

}
