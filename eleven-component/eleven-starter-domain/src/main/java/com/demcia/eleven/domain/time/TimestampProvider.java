package com.demcia.eleven.domain.time;

public interface TimestampProvider {

    long provide() throws TimestampProviderException;

}
