////////////////////////////////////////////////////////////////////////////////
//
//    Copyright (C) 2017-2018 OpenBW Team
//
//    This file is part of BWAPI4J.
//
//    BWAPI4J is free software: you can redistribute it and/or modify
//    it under the terms of the Lesser GNU General Public License as published
//    by the Free Software Foundation, version 3 only.
//
//    BWAPI4J is distributed in the hope that it will be useful,
//    but WITHOUT ANY WARRANTY; without even the implied warranty of
//    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
//    GNU General Public License for more details.
//
//    You should have received a copy of the GNU General Public License
//    along with BWAPI4J.  If not, see <http://www.gnu.org/licenses/>.
//
////////////////////////////////////////////////////////////////////////////////

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
