package com.eleven.access.core;

public abstract class AbstractDisposable implements Disposable {
    private boolean isGarbage;

    @Override
    public void markGarbage() {
        isGarbage = true;
    }

    @Override
    public boolean isGarbage() {
        return isGarbage;
    }
}
