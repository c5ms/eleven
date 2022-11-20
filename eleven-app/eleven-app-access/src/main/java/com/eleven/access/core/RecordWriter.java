package com.eleven.access.core;

import java.io.Closeable;

public interface RecordWriter extends Closeable {

    void write(Record record) throws Exception;

    void flush() throws Exception;
}
