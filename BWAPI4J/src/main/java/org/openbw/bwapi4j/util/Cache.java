package org.openbw.bwapi4j.util;

import java.util.concurrent.Callable;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openbw.bwapi4j.InteractionHandler;

public class Cache<T> {

  private static final Logger logger = LogManager.getLogger();

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

    if (this.lastFrameUpdate + this.refreshPeriod < currentFrameCount + 1
        || this.lastFrameUpdate > currentFrameCount) {
      try {
        this.data = this.updateFunction.call();
      } catch (final Exception e) {
        logger.warn("Update function failed. Returning null.");
        logger.throwing(e);
        return null;
      }

      this.lastFrameUpdate = currentFrameCount;
    }

    return this.data;
  }
}
