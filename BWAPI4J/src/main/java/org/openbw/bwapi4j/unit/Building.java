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

package org.openbw.bwapi4j.unit;

import org.openbw.bwapi4j.Position;
import org.openbw.bwapi4j.TilePosition;
import org.openbw.bwapi4j.type.UnitType;

import static org.openbw.bwapi4j.type.UnitCommandType.Cancel_Construction;
import static org.openbw.bwapi4j.util.MathUtils.distanceBetween;
import static org.openbw.bwapi4j.util.MathUtils.estimateDistanceBetween;

public abstract class Building extends PlayerUnit {

    public boolean cancelConstruction() {

        return issueCommand(this.id, Cancel_Construction, -1, -1, -1, -1);
    }


    protected int probableConstructionStart;

    protected Building(int id, UnitType type, int timeSpotted) {

        super(id, type);
        this.probableConstructionStart = calculateProbableConstructionStart(timeSpotted);
    }

    public int getBuildTime() {

        return this.type.buildTime();
    }

    public int getRemainingBuildTime() {

        return this.remainingBuildTime;
    }

    private int calculateProbableConstructionStart(int currentFrame) {

        int time;
        if (this.isCompleted()) {
            time = currentFrame - this.type.buildTime();
        } else {
            time = currentFrame - this.getHitPoints() * this.type.buildTime() / this.type.maxHitPoints();
        }
        return time;
    }

    public int getProbableConstructionStart() {

        return this.probableConstructionStart;
    }

    /**
     * Returns the distance to given position from where this unit was located when it last was visible.
     *
     * @param tilePosition tile position to measure distance to
     * @return distance in tiles
     */
    public int getLastKnownDistance(TilePosition tilePosition) {
        int left = getLastKnownTilePosition().getX();
        int tileX = tilePosition.getX();
        int top = getLastKnownTilePosition().getY();
        int tileY = tilePosition.getY();
        return (int) distanceBetween(left, top, left + type.tileWidth(), top + type.tileHeight(), tileX, tileY, tileX, tileY);
    }

    /**
     * Returns the distance to given position from where this unit was located when it last was visible.
     *
     * @param position position to measure distance to
     * @return distance in pixels
     */
    public double getLastKnownDistance(Position position) {
        int centerX = getLastKnownPosition().getX();
        int posX = position.getX();
        int centerY = getLastKnownPosition().getY();
        int posY = position.getY();
        return (int) distanceBetween(
                centerX - type.dimensionLeft(), centerY - type.dimensionUp(), centerX + type.dimensionRight(), centerY + type.dimensionDown(),
                posX - 1, posY - 1, posX + 1, posY + 1);
    }

    public double getLastKnownDistance(Unit target) {

        if (this == target) {
            return 0;
        }

        int centerX = getLastKnownPosition().getX();
        int centerY = getLastKnownPosition().getY();
        return estimateDistanceBetween(
                centerX - type.dimensionLeft(), centerY - type.dimensionUp(), centerX + type.dimensionRight(), centerY + type.dimensionDown(),
                target.getLeft() - 1, target.getTop() - 1, target.getRight() + 1, target.getBottom() + 1);
    }
}
