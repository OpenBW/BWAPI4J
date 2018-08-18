// Original work Copyright (c) 2015, 2017, Igor Dimitrijevic
// Modified work Copyright (c) 2017-2018 OpenBW Team

//////////////////////////////////////////////////////////////////////////
//
// This file is part of the BWEM Library.
// BWEM is free software, licensed under the MIT/X11 License.
// A copy of the license is provided with the library in the LICENSE file.
// Copyright (c) 2015, 2017, Igor Dimitrijevic
//
//////////////////////////////////////////////////////////////////////////

package bwem.map;

import bwem.util.XYCropper;
import java.util.ArrayList;
import java.util.List;
import java.util.SplittableRandom;
import org.openbw.bwapi4j.Position;
import org.openbw.bwapi4j.TilePosition;
import org.openbw.bwapi4j.WalkPosition;

public class MapDataImpl implements MapData {
  private final SplittableRandom randomGenerator;
  private final TilePosition tileSize;
  private final WalkPosition walkSize;
  private final Position pixelSize;
  private final Position center;
  private final List<TilePosition> startingLocations;
  private final XYCropper tileSizeCropper;
  private final XYCropper walkSizeCropper;
  private final XYCropper pixelSizeCropper;

  public MapDataImpl(
      final int tileWidth, final int tileHeight, final List<TilePosition> startingLocations) {
    this.randomGenerator = new SplittableRandom();

    this.tileSize = new TilePosition(tileWidth, tileHeight);
    this.walkSize = this.tileSize.toWalkPosition();
    this.pixelSize = this.tileSize.toPosition();

    this.center = new Position(this.pixelSize.getX() / 2, this.pixelSize.getY() / 2);

    this.startingLocations = new ArrayList<>(startingLocations);

    this.tileSizeCropper = new XYCropper(0, 0, getTileSize().getX() - 1, getTileSize().getY() - 1);
    this.walkSizeCropper = new XYCropper(0, 0, getWalkSize().getX() - 1, getWalkSize().getY() - 1);
    this.pixelSizeCropper =
        new XYCropper(0, 0, getPixelSize().getX() - 1, getPixelSize().getY() - 1);
  }

  @Override
  public TilePosition getTileSize() {
    return this.tileSize;
  }

  @Override
  public WalkPosition getWalkSize() {
    return this.walkSize;
  }

  @Override
  public Position getPixelSize() {
    return this.pixelSize;
  }

  @Override
  public Position getCenter() {
    return this.center;
  }

  @Override
  public List<TilePosition> getStartingLocations() {
    return this.startingLocations;
  }

  @Override
  public boolean isValid(final TilePosition tilePosition) {
    return isValid(
        tilePosition.getX(), tilePosition.getY(), getTileSize().getX(), getTileSize().getY());
  }

  @Override
  public boolean isValid(final WalkPosition walkPosition) {
    return isValid(
        walkPosition.getX(), walkPosition.getY(), getWalkSize().getX(), getWalkSize().getY());
  }

  @Override
  public boolean isValid(final Position position) {
    return isValid(position.getX(), position.getY(), getPixelSize().getX(), getPixelSize().getY());
  }

  private boolean isValid(final int x, final int y, final int maxX, final int maxY) {
    return (x >= 0 && x < maxX && y >= 0 && y < maxY);
  }

  @Override
  public TilePosition crop(final TilePosition tilePosition) {
    final int[] xy = this.tileSizeCropper.crop(tilePosition.getX(), tilePosition.getY());
    return new TilePosition(xy[0], xy[1]);
  }

  @Override
  public WalkPosition crop(final WalkPosition walkPosition) {
    final int[] xy = this.walkSizeCropper.crop(walkPosition.getX(), walkPosition.getY());
    return new WalkPosition(xy[0], xy[1]);
  }

  @Override
  public Position crop(final Position position) {
    final int[] xy = this.pixelSizeCropper.crop(position.getX(), position.getY());
    return new Position(xy[0], xy[1]);
  }

  @Override
  public Position getRandomPosition() {
    final int x = this.randomGenerator.nextInt(getPixelSize().getX());
    final int y = this.randomGenerator.nextInt(getPixelSize().getY());
    return new Position(x, y);
  }
}
