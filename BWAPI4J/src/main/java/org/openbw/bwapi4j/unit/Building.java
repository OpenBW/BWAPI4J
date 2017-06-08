package org.openbw.bwapi4j.unit;

import org.openbw.bwapi4j.Position;
import org.openbw.bwapi4j.TilePosition;
import org.openbw.bwapi4j.type.TechType;
import org.openbw.bwapi4j.type.UnitCommandType;
import org.openbw.bwapi4j.type.UnitType;
import org.openbw.bwapi4j.type.UpgradeType;

public abstract class Building extends PlayerUnit {

    protected class Researcher implements ResearchingFacility {

        private boolean isUpgrading;
        private boolean isResearching;

        public void update(int[] unitData, int index) {

            this.isUpgrading = unitData[Unit.IS_UPGRADING_INDEX] == 1;
            this.isResearching = unitData[Unit.IS_RESEARCHING_INDEX] == 1;
        }

        public boolean isUpgrading() {
            return this.isUpgrading;
        }

        public boolean isResearching() {
            return this.isResearching;
        }

        public boolean cancelResearch() {
            return issueCommand(id, UnitCommandType.Cancel_Research.ordinal(), -1, -1, -1, -1);
        }

        public boolean cancelUpgrade() {
            return issueCommand(id, UnitCommandType.Cancel_Upgrade.ordinal(), -1, -1, -1, -1);
        }

        public boolean research(TechType techType) {
            return issueCommand(id, UnitCommandType.Research.ordinal(), -1, -1, -1, techType.getId());
        }

        public boolean upgrade(UpgradeType upgrade) {
            return issueCommand(id, UnitCommandType.Research.ordinal(), -1, -1, -1, upgrade.getId());
        }
    }

    protected class Trainer {

        private boolean isTraining;
        private int trainingQueueSize;

        public void update(int[] unitData, int index) {
            this.isTraining = unitData[index + Unit.IS_TRAINING_INDEX] == 1;
            this.trainingQueueSize = unitData[index + Unit.TRAINING_QUEUE_SIZE_INDEX];
        }

        public boolean isTraining() {
            return this.isTraining;
        }

        public int getTrainingQueueSize() {
            return this.trainingQueueSize;
        }

        public boolean train(UnitType type) {
            return issueCommand(id, UnitCommandType.Train.ordinal(), type.getId(), -1, -1, -1);
        }

        public boolean cancelTrain(int slot) {
            return issueCommand(id, UnitCommandType.Cancel_Train_Slot.ordinal(), -1, -1, -1, slot);
        }

        public boolean cancelTrain() {
            return issueCommand(id, UnitCommandType.Cancel_Train.ordinal(), -1, -1, -1, -1);
        }

        public boolean setRallyPoint(Position p) {
            return issueCommand(id, UnitCommandType.Set_Rally_Position.ordinal(), -1, p.getX(), p.getY(), -1);
        }

        public boolean setRallyPoint(Unit target) {
            return issueCommand(id, UnitCommandType.Set_Rally_Unit.ordinal(), target.getId(), -1, -1, -1);
        }
    }

    protected class Flyer implements FlyingBuilding {

        private boolean isLifted;

        public void update(int[] unitData, int index) {
            this.isLifted = unitData[index + Unit.IS_LIFTED_INDEX] == 1;
        }

        @Override
        public boolean lift() {
            return issueCommand(id, UnitCommandType.Lift.ordinal(), -1, -1, -1, -1);
        }

        @Override
        public boolean land(Position p) {
            return issueCommand(id, UnitCommandType.Land.ordinal(), -1, p.getX(), p.getY(), -1);
        }

        @Override
        public boolean move(Position p) {
            return issueCommand(id, UnitCommandType.Move.ordinal(), -1, p.getX(), p.getY(), -1);
        }

        @Override
        public boolean isLifted() {
            return isLifted;
        }
    }

    protected int probableConstructionStart;
    protected int remainingBuildTime;
    private int builderId;

    protected Building(int id, UnitType type, int timeSpotted) {
        
        super(id, type);
        this.probableConstructionStart = calculateProbableConstructionStart(timeSpotted);
    }

    @Override
    public void update(int[] unitData, int index) {

        this.builderId = unitData[index + Unit.BUILD_UNIT_ID_INDEX];
        this.remainingBuildTime = unitData[index + Unit.REMAINING_BUILD_TIME_INDEX];

        super.update(unitData, index);
    }

    public SCV getBuildUnit() {
        
        return (SCV) super.getUnit(builderId);
    }

    public int getBuildTime() {
        
        return this.type.buildTime();
    }

    public int getRemainingBuildTime() {
        
        return this.remainingBuildTime;
    }

    private int calculateProbableConstructionStart(int currentFrame) {

        int time = currentFrame;
        if (this.isCompleted()) {
            time = currentFrame - this.type.buildTime();
        } else {
            time = currentFrame - (this.getHitPoints() / this.type.maxHitPoints()) * this.type.buildTime();
        }
        return time;
    }

    public int getProbableConstructionStart() {
        
        return this.probableConstructionStart;
    }

    public int getLastKnownDistance(TilePosition position) {

        // compute x distance
        int xDist = super.getLastKnownTilePosition().getX() - position.getX();
        if (xDist < 0) {
            xDist = position.getX() - (super.getLastKnownTilePosition().getX() + this.type.tileWidth());
            if (xDist < 0) {
                xDist = 0;
            }
        }

        // compute y distance
        int yDist = super.getLastKnownTilePosition().getY() - position.getY();
        if (yDist < 0) {
            yDist = position.getY() - (super.getLastKnownTilePosition().getY() + this.type.tileHeight());
            if (yDist < 0) {
                yDist = 0;
            }
        }
        return (int) Math.sqrt(xDist * xDist + yDist * yDist);
    }

    public double getLastKnownDistance(Position position) {

        int left = position.getX() - 1;
        int top = position.getY() - 1;
        int right = position.getX() + 1;
        int bottom = position.getY() + 1;

        // compute x distance
        int xDist = (super.getLastKnownPosition().getX() - this.type.dimensionLeft()) - right;
        if (xDist < 0) {
            xDist = left - (super.getLastKnownPosition().getX() + this.type.dimensionRight());
            if (xDist < 0) {
                xDist = 0;
            }
        }

        // compute y distance
        int yDist = (super.getLastKnownPosition().getY() - this.type.dimensionUp()) - bottom;
        if (yDist < 0) {
            yDist = top - (super.getLastKnownPosition().getY() + this.type.dimensionDown());
            if (yDist < 0) {
                yDist = 0;
            }
        }
        return (int) Math.sqrt(xDist * xDist + yDist * yDist);
    }
}
