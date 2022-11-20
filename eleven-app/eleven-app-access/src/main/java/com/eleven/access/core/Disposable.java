package com.eleven.access.core;

public interface Disposable extends Checkable {
    String getId();

    void markGarbage();

    boolean isGarbage();
}
