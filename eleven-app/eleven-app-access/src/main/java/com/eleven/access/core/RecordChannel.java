package com.eleven.access.core;

public interface RecordChannel extends AutoCloseable {
    void write(java.lang.Record record) throws Exception;
}
