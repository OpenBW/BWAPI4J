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

import static org.openbw.bwapi4j.util.MathUtils.estimateDistanceBetween;

import org.openbw.bwapi4j.ap.BridgeValue;

public class Position {

  private final int x;
  private final int y;

  @BridgeValue
  public Position(final int x, final int y) {
    this.x = x;
    this.y = y;
  }

  public Position(final TilePosition tilePosition) {
    this.x = tilePosition.getX() * TilePosition.SIZE_IN_PIXELS;
    this.y = tilePosition.getY() * TilePosition.SIZE_IN_PIXELS;
  }

  public Position(final WalkPosition walkPosition) {
    this.x = walkPosition.getX() * WalkPosition.SIZE_IN_PIXELS;
    this.y = walkPosition.getY() * WalkPosition.SIZE_IN_PIXELS;
  }

  public int getX() {
    return this.x;
  }

  public int getY() {
    return this.y;
  }

  /** Returns the distance as BW would. This is ported from BWAPI's getApproxDistance method. */
  public int getDistance(final Position position) {
    return estimateDistanceBetween(getX(), getY(), position.getX(), position.getY());
  }

  public TilePosition toTilePosition() {
    final int x = getX() / TilePosition.SIZE_IN_PIXELS;
    final int y = getY() / TilePosition.SIZE_IN_PIXELS;
    return new TilePosition(x, y);
  }

  public WalkPosition toWalkPosition() {
    final int x = getX() / WalkPosition.SIZE_IN_PIXELS;
    final int y = getY() / WalkPosition.SIZE_IN_PIXELS;
    return new WalkPosition(x, y);
  }

  public Position add(final Position position) {
    final int x = getX() + position.getX();
    final int y = getY() + position.getY();
    return new Position(x, y);
  }

  public Position subtract(final Position position) {
    final int x = getX() - position.getX();
    final int y = getY() - position.getY();
    return new Position(x, y);
  }

  public Position multiply(final Position position) {
    final int x = getX() * position.getX();
    final int y = getY() * position.getY();
    return new Position(x, y);
  }

  public Position divide(final Position position) {
    final int x = getX() / position.getX();
    final int y = getY() / position.getY();
    return new Position(x, y);
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

    if (!(object instanceof Position)) {
      return false;
    }

    final Position position = (Position) object;
    if (getX() != position.getX()) {
      return false;
    }
    if (getY() != position.getY()) {
      return false;
    }

    return true;
  }

  @Override
  public int hashCode() {
    return (getX() * 2048 * TilePosition.SIZE_IN_PIXELS + getY());
  }
}
