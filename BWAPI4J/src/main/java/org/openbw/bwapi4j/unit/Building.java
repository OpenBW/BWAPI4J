package org.openbw.bwapi4j.unit;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openbw.bwapi4j.Position;
import org.openbw.bwapi4j.TilePosition;
import org.openbw.bwapi4j.type.TechType;
import org.openbw.bwapi4j.type.UnitCommandType;
import org.openbw.bwapi4j.type.UnitType;
import org.openbw.bwapi4j.type.UpgradeType;

import static org.openbw.bwapi4j.type.UnitCommandType.*;

public abstract class Building extends PlayerUnit {

    private static final Logger logger = LogManager.getLogger();

    public boolean cancelConstruction() {

        return issueCommand(this.id, Cancel_Construction, -1, -1, -1, -1);
    }

    protected class Researcher implements ResearchingFacility {

        private boolean isUpgrading;
        private boolean isResearching;
        private int remainingResearchTime;
        private int remainingUpgradeTime;
        private UpgradeType currentUpgrade;
        private TechType currentResearch;

        public void update(int[] unitData, int index) {

            this.isUpgrading = unitData[index + Unit.IS_UPGRADING_INDEX] == 1;
            this.isResearching = unitData[index + Unit.IS_RESEARCHING_INDEX] == 1;
            this.remainingResearchTime = unitData[index + Unit.REMAINING_RESEARCH_TIME_INDEX];
            this.remainingUpgradeTime = unitData[index + Unit.REMAINING_UPGRADE_TIME_INDEX];
            this.currentUpgrade = UpgradeType.withId(unitData[index + Unit.UPGRADE_ID_INDEX]);
            this.currentResearch = TechType.withId(unitData[index + Unit.TECH_ID_INDEX]);
        }

        public boolean isUpgrading() {

            return this.isUpgrading;
        }

        public boolean isResearching() {

            return this.isResearching;
        }

        public boolean cancelResearch() {

            return issueCommand(id, Cancel_Research, -1, -1, -1, -1);
        }

        public boolean cancelUpgrade() {

            return issueCommand(id, Cancel_Upgrade, -1, -1, -1, -1);
        }

        @Override
        public boolean canResearch(TechType techType) {
            return type.equals(techType.whatResearches())
                    && getPlayer().canResearch(techType);
        }

        @Override
        public boolean canUpgrade(UpgradeType upgradeType) {
            return type.equals(upgradeType.whatUpgrades())
                    && getPlayer().canUpgrade(upgradeType);
        }

        @Override
        public boolean research(TechType techType) {

            return issueCommand(id, Research, -1, -1, -1, techType.getId());
        }

        @Override
        public boolean upgrade(UpgradeType upgrade) {

            return issueCommand(id, Upgrade, -1, -1, -1, upgrade.getId());
        }

        @Override
        public UpgradeInProgress getUpgradeInProgress() {
            if (currentUpgrade == UpgradeType.None) {
                return UpgradeInProgress.NONE;
            }
            return new UpgradeInProgress(currentUpgrade, remainingUpgradeTime);
        }

        @Override
        public ResearchInProgress getResearchInProgress() {
            if (currentResearch == TechType.None) {
                return ResearchInProgress.NONE;
            }
            return new ResearchInProgress(currentResearch, remainingResearchTime);
        }
    }

    protected class Trainer {

        private boolean isTraining;
        private int trainingQueueSize;
        private int remainingTrainTime;
        private int rallyPositionX;
        private int rallyPositionY;
        private int rallyUnitId;

        public void update(int[] unitData, int index) {

            this.isTraining = unitData[index + Unit.IS_TRAINING_INDEX] == 1;
            this.trainingQueueSize = unitData[index + Unit.TRAINING_QUEUE_SIZE_INDEX];
            this.remainingTrainTime = unitData[index + Unit.REMAINING_TRAIN_TIME_INDEX];
            this.rallyUnitId = unitData[index + Unit.RALLY_UNIT_INDEX];
            this.rallyPositionX = unitData[index + Unit.RALLY_POSITION_X_INDEX];
            this.rallyPositionY = unitData[index + Unit.RALLY_POSITION_Y_INDEX];
        }

        public Position getRallyPosition() {

            return new Position(rallyPositionX, rallyPositionY);
        }

        public int getRemainingTrainingTime() {

            return this.remainingTrainTime;
        }

        public Unit getRallyUnit() {

            return getUnit(this.rallyUnitId);
        }

        public boolean isTraining() {

            return this.isTraining;
        }

        public int getTrainingQueueSize() {

            return this.trainingQueueSize;
        }

        public boolean canTrain(UnitType type) {
            Addon addon = (Building.this instanceof ExtendibleByAddon) ? ((ExtendibleByAddon) Building.this).getAddon() : null;
            return Building.this.type.equals(type.whatBuilds().first)
                    && getPlayer().canMake(type)
                    && type.requiredUnits().stream().noneMatch(ut ->
                    ut.isAddon() && (addon == null || !addon.isA(ut)));
        }

        public boolean train(UnitType type) {

            return issueCommand(id, Train, -1, -1, -1, type.getId());
        }

        public boolean cancelTrain(int slot) {

            return issueCommand(id, Cancel_Train_Slot, -1, -1, -1, slot);
        }

        public boolean cancelTrain() {

            return issueCommand(id, Cancel_Train, -1, -1, -1, -1);
        }

        public boolean setRallyPoint(Position p) {

            return issueCommand(id, Set_Rally_Position, -1, p.getX(), p.getY(), -1);
        }

        public boolean setRallyPoint(Unit target) {

            return issueCommand(id, Set_Rally_Unit, target.getId(), -1, -1, -1);
        }
    }

    protected class Flyer implements FlyingBuilding {

        private boolean isLifted;

        public void update(int[] unitData, int index) {

            this.isLifted = unitData[index + Unit.IS_LIFTED_INDEX] == 1;
        }

        @Override
        public boolean lift() {

            return issueCommand(id, Lift, -1, -1, -1, -1);
        }

        @Override
        public boolean land(Position p) {

            return issueCommand(id, Land, -1, p.getX(), p.getY(), -1);
        }

        @Override
        public boolean move(Position p) {

            return issueCommand(id, Move, -1, p.getX(), p.getY(), -1);
        }

        @Override
        public boolean isLifted() {

            return isLifted;
        }
    }

    protected int probableConstructionStart;
    protected int remainingBuildTime;
    protected int builderId;

    protected Building(int id, UnitType type, int timeSpotted) {

        super(id, type);
        this.probableConstructionStart = calculateProbableConstructionStart(timeSpotted);
    }

    @Override
    public void update(int[] unitData, int index, int frame) {

        this.builderId = unitData[index + Unit.BUILD_UNIT_ID_INDEX];
        this.remainingBuildTime = unitData[index + Unit.REMAINING_BUILD_TIME_INDEX];

        super.update(unitData, index, frame);
    }

    public SCV getBuildUnit() {

        Unit unit = this.getUnit(builderId);
        if (unit instanceof SCV) {
            return (SCV) unit;
        } else {

            logger.error("build unit for {} should be SCV but is {}.", this, unit);
            return null;
        }
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
