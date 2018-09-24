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

package org.openbw.bwapi4j;

import org.openbw.bwapi4j.ap.BridgeValue;

public class TilePosition {
  public static final int SIZE_IN_PIXELS = 32;

  private final int x;
  private final int y;

  @BridgeValue
  public TilePosition(final int x, final int y) {
    this.x = x;
    this.y = y;
  }

  public TilePosition(final WalkPosition walkPosition) {
    this.x = (walkPosition.getX() * WalkPosition.SIZE_IN_PIXELS) / TilePosition.SIZE_IN_PIXELS;
    this.y = (walkPosition.getY() * WalkPosition.SIZE_IN_PIXELS) / TilePosition.SIZE_IN_PIXELS;
  }

  public TilePosition(final Position position) {
    this.x = position.getX() / TilePosition.SIZE_IN_PIXELS;
    this.y = position.getY() / TilePosition.SIZE_IN_PIXELS;
  }

  public int getX() {
    return this.x;
  }

  public int getY() {
    return this.y;
  }

  public double getDistance(final TilePosition position) {
    double dx = getX() - position.getX();
    double dy = getY() - position.getY();

    return Math.sqrt(dx * dx + dy * dy);
  }

  public WalkPosition toWalkPosition() {
    final int x = (getX() * TilePosition.SIZE_IN_PIXELS) / WalkPosition.SIZE_IN_PIXELS;
    final int y = (getY() * TilePosition.SIZE_IN_PIXELS) / WalkPosition.SIZE_IN_PIXELS;
    return new WalkPosition(x, y);
  }

  public Position toPosition() {
    final int x = getX() * TilePosition.SIZE_IN_PIXELS;
    final int y = getY() * TilePosition.SIZE_IN_PIXELS;
    return new Position(x, y);
  }

  public TilePosition add(final TilePosition tilePosition) {
    final int x = getX() + tilePosition.getX();
    final int y = getY() + tilePosition.getY();
    return new TilePosition(x, y);
  }

  public TilePosition subtract(final TilePosition tilePosition) {
    final int x = getX() - tilePosition.getX();
    final int y = getY() - tilePosition.getY();
    return new TilePosition(x, y);
  }

  public TilePosition multiply(final TilePosition tilePosition) {
    final int x = getX() * tilePosition.getX();
    final int y = getY() * tilePosition.getY();
    return new TilePosition(x, y);
  }

  public TilePosition divide(final TilePosition tilePosition) {
    final int x = getX() / tilePosition.getX();
    final int y = getY() / tilePosition.getY();
    return new TilePosition(x, y);
  }

  @Override
  public String toString() {
    return "[" + getX() + ", " + getY() + "]";
  }

  @Override
  public boolean equals(final Object object) {
    if (this == object) {
      return true;
    }

    if (!(object instanceof TilePosition)) {
      return false;
    }

    final TilePosition tilePosition = (TilePosition) object;
    if (getX() != tilePosition.getX()) {
      return false;
    }
    if (getY() != tilePosition.getY()) {
      return false;
    }

    return true;
  }

  @Override
  public int hashCode() {
    return (getX() * 2048 + getY());
  }
}
