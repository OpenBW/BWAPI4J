package org.openbw.bwapi4j.util;

import org.openbw.bwapi4j.InteractionHandler;

import java.util.concurrent.Callable;

public class Cache<T> {

    private T data;
    private int lastFrameUpdate;
    private final int refreshPeriod;
    private final Callable<T> updateFunction;
    private final InteractionHandler interactionHandler;

    public Cache(final Callable<T> updateFunction, final InteractionHandler interactionHandler) {
        this.lastFrameUpdate = -1;
        this.refreshPeriod = 1;
        this.updateFunction = updateFunction;
        this.interactionHandler = interactionHandler;
    }

    public T get() {
        final int currentFrameCount = this.interactionHandler.getFrameCount();

        if (this.lastFrameUpdate + this.refreshPeriod < currentFrameCount + 1) {
            try {
                this.data = this.updateFunction.call();
            } catch (Exception e) {
                throw new RuntimeException(e);
            }

            this.lastFrameUpdate = currentFrameCount;
        }

        return this.data;
    }

}