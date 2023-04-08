package com.eleven.core.time;

public interface TimestampProvider {

    long provide() throws TimestampProviderException;

}
