package com.eleven.core.data;

public interface SerialGenerator {
    String DELIMITER = "#";

    long next(String group, String name);

    long drop(String group, String name);

    default long next(String group) {
        return next(group, DELIMITER);
    }

    default long next(String group, String parent, String parentId) {
        return next(group, parent + DELIMITER  + parentId);
    }

    default long drop(String group) {
        return drop(group, DELIMITER);
    }

    default long drop(String group, String parent, String parentId) {
        return drop(group,parent + DELIMITER  + parentId);
    }

    default String nextString(String group, String name) {
        return String.valueOf(next(group, name));
    }

    default String nextString(String group, String parent, String parentId) {
        return String.valueOf(next(group, parent, parentId));
    }

    default String nextString(String group) {
        return nextString(group, DELIMITER);
    }
}
