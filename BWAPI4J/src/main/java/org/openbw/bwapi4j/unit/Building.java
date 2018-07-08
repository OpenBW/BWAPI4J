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
import org.openbw.bwapi4j.type.TechType;
import org.openbw.bwapi4j.type.UnitType;
import org.openbw.bwapi4j.type.UpgradeType;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

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
     * @param position tile position to measure distance to
     * @return distance in tiles
     */
    public int getLastKnownDistance(TilePosition position) {

        // compute x distance
        int distX = this.getLastKnownTilePosition().getX() - position.getX();
        if (distX < 0) {
            distX = position.getX() - (this.getLastKnownTilePosition().getX() + this.type.tileWidth());
            if (distX < 0) {
                distX = 0;
            }
        }

        // compute y distance
        int distY = this.getLastKnownTilePosition().getY() - position.getY();
        if (distY < 0) {
            distY = position.getY() - (this.getLastKnownTilePosition().getY() + this.type.tileHeight());
            if (distY < 0) {
                distY = 0;
            }
        }
        return (int) Math.sqrt(distX * distX + distY * distY);
    }

    /**
     * Returns the distance to given position from where this unit was located when it last was visible.
     *
     * @param position position to measure distance to
     * @return distance in pixels
     */
    public double getLastKnownDistance(Position position) {

        int left = position.getX() - 1;
        int top = position.getY() - 1;
        int right = position.getX() + 1;
        int bottom = position.getY() + 1;

        // compute x distance
        int distX = (this.getLastKnownPosition().getX() - this.type.dimensionLeft()) - right;
        if (distX < 0) {
            distX = left - (this.getLastKnownPosition().getX() + this.type.dimensionRight());
            if (distX < 0) {
                distX = 0;
            }
        }

        // compute y distance
        int distY = (this.getLastKnownPosition().getY() - this.type.dimensionUp()) - bottom;
        if (distY < 0) {
            distY = top - (this.getLastKnownPosition().getY() + this.type.dimensionDown());
            if (distY < 0) {
                distY = 0;
            }
        }
        return (int) Math.sqrt(distX * distX + distY * distY);
    }

    public double getLasKnownDistance(Unit target) {

        if (this == target) {
            return 0;
        }

        int xDist = (this.getLastKnownPosition().getX() - this.type.dimensionLeft()) - (target.getRight() + 1);
        if (xDist < 0) {
            xDist = target.getLeft() - ((this.getLastKnownPosition().getX() + this.type.dimensionRight()) + 1);
            if (xDist < 0) {
                xDist = 0;
            }
        }
        int yDist = (this.getLastKnownPosition().getY() - this.type.dimensionUp()) - (target.getBottom() + 1);
        if (yDist < 0) {
            yDist = target.getTop() - ((this.getLastKnownPosition().getY() + this.type.dimensionDown()) + 1);
            if (yDist < 0) {
                yDist = 0;
            }
        }
        logger.trace("dx, dy: {}, {}.", xDist, yDist);

        return new Position(0, 0).getDistance(new Position(xDist, yDist));
    }
}
