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

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openbw.bwapi4j.Position;
import org.openbw.bwapi4j.TilePosition;
import org.openbw.bwapi4j.annotation.Px;
import org.openbw.bwapi4j.annotation.Tile;
import org.openbw.bwapi4j.type.UnitType;

import static org.openbw.bwapi4j.type.UnitCommandType.*;

public abstract class Building extends PlayerUnit {

    private static final Logger logger = LogManager.getLogger();

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
    @Tile
    public int getLastKnownDistance(TilePosition tilePosition) {

        // compute x distance
        int left = getLastKnownTilePosition().getX();
        int tileX = tilePosition.getX();
        int distX = calculateDelta(left, left + type.tileWidth(), tileX, tileX);

        // compute y distance
        int top = getLastKnownTilePosition().getY();
        int tileY = tilePosition.getY();
        int distY = calculateDelta(top, top + type.tileHeight(), tileY, tileY);

        return (int) Math.sqrt(distX * distX + distY * distY);
    }

    /**
     * Returns the distance to given position from where this unit was located when it last was visible.
     *
     * @param position position to measure distance to
     * @return distance in pixels
     */
    @Px
    public double getLastKnownDistance(Position position) {

        // compute x distance
        int centerX = getLastKnownPosition().getX();
        int posX = position.getX();
        int distX = calculateDelta(centerX - type.dimensionLeft(), centerX + type.dimensionRight(), posX - 1, posX + 1);

        // compute y distance
        int centerY = getLastKnownPosition().getY();
        int posY = position.getY();
        int distY = calculateDelta(centerY - type.dimensionUp(), centerY + type.dimensionDown(), posY - 1, posY + 1);

        return (int) Math.sqrt(distX * distX + distY * distY);
    }

    @Px
    public double getLastKnownDistance(Unit target) {

        if (this == target) {
            return 0;
        }

        int centerX = getLastKnownPosition().getX();
        int distX = calculateDelta(centerX - type.dimensionLeft(), centerX + type.dimensionRight(), target.getLeft() - 1, target.getRight() + 1);

        int centerY = getLastKnownPosition().getY();
        int distY = calculateDelta(centerY - type.dimensionUp(), centerY + type.dimensionDown(), target.getTop() - 1, target.getBottom() + 1);

        return Position.getApproxDistance(distX, distY);
    }

    private static int calculateDelta(
            int srcTopLeftBound, int srcBottomRightBound, int dstTopLeftBound, int dstBottomRightBound){
        // Assuming the destination is top/left of the source
        int xDist = srcTopLeftBound - dstBottomRightBound;
        // The destination is to the bottom/right of the source.
        if (xDist < 0) {
            xDist = dstTopLeftBound - srcBottomRightBound;
            if (xDist < 0) {
                return 0;
            }
        }
        return xDist;
    }
}
