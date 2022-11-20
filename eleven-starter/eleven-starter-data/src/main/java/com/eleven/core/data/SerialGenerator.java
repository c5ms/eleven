package com.eleven.core.data;

public interface SerialGenerator {

    long next(String group, String name);

    long drop(String group, String name);
}
