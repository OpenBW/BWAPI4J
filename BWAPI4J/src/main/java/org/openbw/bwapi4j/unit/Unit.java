package org.openbw.bwapi4j.unit;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openbw.bwapi4j.*;
import org.openbw.bwapi4j.type.*;

import java.util.Collection;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public abstract class Unit implements Comparable<Unit> {

    private static final Logger logger = LogManager.getLogger();

    protected static int ID_INDEX = 0;
    protected static int REPLAY_ID_INDEX = 1;
    protected static int PLAYER_ID_INDEX = 2;
    protected static int TYPE_ID_INDEX = 3;
    protected static int POSITION_X_INDEX = 4;
    protected static int POSITION_Y_INDEX = 5;
    protected static int TILEPOSITION_X_INDEX = 6;
    protected static int TILEPOSITION_Y_INDEX = 7;
    protected static int ANGLE_INDEX = 8;
    protected static int VELOCITY_X_INDEX = 9;
    protected static int VELOCITY_Y_INDEX = 10;
    protected static int HITPOINTS_INDEX = 11;
    protected static int SHIELDS_INDEX = 12;
    protected static int ENERGY_INDEX = 13;
    protected static int RESOURCES_INDEX = 14;
    protected static int RESOURCE_GROUP_INDEX = 15;
    protected static int LAST_COMMAND_FRAME_INDEX = 16;
    protected static int LAST_COMMAND_TYPE_ID_INDEX = 17;
    protected static int LAST_ATTACKING_PLAYER_INDEX = 18;
    protected static int INITIAL_TYPE_ID_INDEX = 19;
    protected static int INITIAL_POSITION_X_INDEX = 20;
    protected static int INITIAL_POSITION_Y_INDEX = 21;
    protected static int INITIAL_TILEPOSITION_X_INDEX = 22;
    protected static int INITIAL_TILEPOSITION_Y_INDEX = 23;
    protected static int INITIAL_HITPOINTS_INDEX = 24;
    protected static int INITIAL_RESOURCES_INDEX = 25;
    protected static int KILLCOUNT_INDEX = 26;
    protected static int ACID_SPORE_COUNT_INDEX = 27;
    protected static int INTERCEPTOR_COUNT_INDEX = 28;
    protected static int SCARAB_COUNT_INDEX = 29;
    protected static int SPIDERMINE_COUNT_INDEX = 30;
    protected static int GROUND_WEAPON_COOLDOWN_INDEX = 31;
    protected static int AIR_WEAPON_COOLDOWN_INDEX = 32;
    protected static int SPELL_COOLDOWN_INDEX = 33;
    protected static int DEFENSE_MATRIX_POINTS_INDEX = 34;
    protected static int DEFENSE_MATRIX_TIMER_INDEX = 35;
    protected static int ENSNARE_TIMER_INDEX = 36;
    protected static int IRRADIATE_TIMER_INDEX = 37;
    protected static int LOCKDOWN_TIMER_INDEX = 38;
    protected static int MAELSTROM_TIMER_INDEX = 39;
    protected static int ORDER_TIMER_INDEX = 40;
    protected static int PLAGUE_TIMER_INDEX = 41;
    protected static int REMOVE_TIMER_INDEX = 42;
    protected static int STASIS_TIMER_INDEX = 43;
    protected static int STOM_TIMER_INDEX = 44;
    protected static int BUILDTYPE_ID_INDEX = 45;
    protected static int TRAINING_QUEUE_SIZE_INDEX = 46;
    protected static int TECH_ID_INDEX = 47;
    protected static int UPGRADE_ID_INDEX = 48;
    protected static int REMAINING_BUILD_TIME_INDEX = 49;
    protected static int REMAINING_TRAIN_TIME_INDEX = 50;
    protected static int REMAINING_RESEARCH_TIME_INDEX = 51;
    protected static int REMAINING_UPGRADE_TIME_INDEX = 52;
    protected static int BUILD_UNIT_ID_INDEX = 53;
    protected static int TARGET_ID_INDEX = 54;
    protected static int TARGET_POSITION_X_INDEX = 55;
    protected static int TARGET_POSITION_Y_INDEX = 56;
    protected static int ORDER_ID_INDEX = 57;
    protected static int ORDER_TARGET_ID_INDEX = 58;
    protected static int SECONDARY_ORDER_ID_INDEX = 59;
    protected static int RALLY_POSITION_X_INDEX = 60;
    protected static int RALLY_POSITION_Y_INDEX = 61;
    protected static int RALLY_UNIT_INDEX = 62;
    protected static int ADDON_INDEX = 63;
    protected static int NYDUS_EXIT_INDEX = 64;
    protected static int TRANSPORT_INDEX = 65;
    protected static int LOADED_UNITS_SIZE_INDEX = 66;
    protected static int CARRIER_INDEX = 67;
    protected static int HATCHERY_INDEX = 68;
    protected static int LARVA_SIZE_INDEX = 69;
    protected static int POWERUP_ID_INDEX = 70;
    protected static int EXISTS_INDEX = 71;
    protected static int HAS_NUKE_INDEX = 72;
    protected static int IS_ACCELERATING_INDEX = 73;
    protected static int IS_ATTACKING_INDEX = 74;
    protected static int IS_ATTACK_FRAME_INDEX = 75;
    protected static int IS_BEING_CONSTRUCTED_INDEX = 76;
    protected static int IS_BEING_GATHERED_INDEX = 77;
    protected static int IS_BEING_HEALED_INDEX = 78;
    protected static int IS_BLIND_INDEX = 79;
    protected static int IS_BRAKING_INDEX = 80;
    protected static int IS_BURROWED_INDEX = 81;
    protected static int IS_CARRYING_GAS_INDEX = 82;
    protected static int IS_CARRYING_MINERALS_INDEX = 83;
    protected static int IS_CLOAKED_INDEX = 84;
    protected static int IS_COMPLETED_INDEX = 85;
    protected static int IS_CONSTRUCTING_INDEX = 86;
    protected static int IS_DEFENSE_MATRIXED_INDEX = 87;
    protected static int IS_DETECTED_INDEX = 88;
    protected static int IS_ENSNARED_INDEX = 89;
    protected static int IS_FOLLOWING_INDEX = 90;
    protected static int IS_GATHERING_GAS_INDEX = 91;
    protected static int IS_GATHERING_MINERALS_INDEX = 92;
    protected static int IS_HALLUCINATION_INDEX = 93;
    protected static int IS_HOLDING_POSITION_INDEX = 94;
    protected static int IS_IDLE_INDEX = 95;
    protected static int IS_INTERRUPTIBLE_INDEX = 96;
    protected static int IS_INVINCIBLE_INDEX = 97;
    protected static int IS_IRRADIATED_INDEX = 98;
    protected static int IS_LIFTED_INDEX = 99;
    protected static int IS_LOADED_INDEX = 100;
    protected static int IS_LOCKED_DOWN_INDEX = 101;
    protected static int IS_MAELSTROMMED_INDEX = 102;
    protected static int IS_MORPHING_INDEX = 103;
    protected static int IS_MOVING_INDEX = 104;
    protected static int IS_PARASITED_INDEX = 105;
    protected static int IS_PATROLLING_INDEX = 106;
    protected static int IS_PLAGUED_INDEX = 107;
    protected static int IS_REPAIRING_INDEX = 108;
    protected static int IS_SELECTED_INDEX = 109;
    protected static int IS_SIEGED_INDEX = 110;
    protected static int IS_STARTING_ATTACK_INDEX = 111;
    protected static int IS_STASISED_INDEX = 112;
    protected static int IS_STIMMED_INDEX = 113;
    protected static int IS_STUCK_INDEX = 114;
    protected static int IS_TRAINING_INDEX = 115;
    protected static int IS_UNDER_ATTACK_INDEX = 116;
    protected static int IS_UNDER_DARK_SWARM_INDEX = 117;
    protected static int IS_UNDER_DISRUPTION_WEB_INDEX = 118;
    protected static int IS_UNDER_STORM_INDEX = 119;
    protected static int IS_POWERED_INDEX = 120;
    protected static int IS_UPGRADING_INDEX = 121;
    protected static int IS_VISIBLE_INDEX = 122;
    protected static int IS_RESEARCHING_INDEX = 123;
    protected static int IS_FLYING_INDEX = 124;
    protected static int ORDER_TARGET_POSITION_X_INDEX = 125;
    protected static int ORDER_TARGET_POSITION_Y_INDEX = 126;
    protected static int TRAINING_QUEUE_SLOT_0_INDEX = 127;
    protected static int TRAINING_QUEUE_SLOT_1_INDEX = 128;
    protected static int TRAINING_QUEUE_SLOT_2_INDEX = 129;
    protected static int TRAINING_QUEUE_SLOT_3_INDEX = 130;
    protected static int TRAINING_QUEUE_SLOT_4_INDEX = 131;
    protected static int MAX_TRAINING_QUEUE_SIZE = 5;

    public static int TOTAL_PROPERTIES = 132;

    // static
    protected int id;
    protected UnitType initialType;
    protected Position initialPosition;
    protected TilePosition initialTilePosition;
    protected int initiallySpotted;

    // dynamic
    protected UnitType type;
    protected int x;
    protected int y;
    protected Position position;
    protected TilePosition tilePosition;
    protected double angle;
    protected int lastCommandFrame;
    protected UnitCommandType lastCommand;

    protected boolean isVisible;
    protected boolean exists;
    protected boolean isSelected;
    protected boolean isFlying;

    // internal
    private BW bw;
    private int lastSpotted;

    protected Unit(int id, UnitType unitType) {

        this.id = id;
        this.type = unitType;
        this.initialType = unitType;
        this.lastSpotted = 0;
    }

    final void setBW(BW bw) {
        this.bw = bw;
    }

    public void initialize(int[] unitData, int index, int frame) {

        // TODO this is a workaround because initialTilePosition gives wrong results with OpenBW
        this.initialPosition = new Position(unitData[index + Unit.POSITION_X_INDEX],
                unitData[index + Unit.POSITION_Y_INDEX]);
        this.initialTilePosition = new TilePosition(unitData[index + Unit.TILEPOSITION_X_INDEX],
                unitData[index + Unit.TILEPOSITION_Y_INDEX]);
        this.initiallySpotted = frame;
    }

    public void preUpdate() {
        this.isVisible = false;
        this.exists = false;
    }

    public void update(int[] unitData, int index, int frame) {

        this.type = UnitType.values()[unitData[index + Unit.TYPE_ID_INDEX]];
        this.x = unitData[index + Unit.POSITION_X_INDEX];
        this.y = unitData[index + Unit.POSITION_Y_INDEX];
        this.position = new Position(x, y);
        this.tilePosition = new TilePosition(unitData[index + Unit.TILEPOSITION_X_INDEX],
                unitData[index + Unit.TILEPOSITION_Y_INDEX]);
        this.angle = unitData[index + Unit.ANGLE_INDEX] * Math.PI / 180.0;
        this.isVisible = unitData[index + Unit.IS_VISIBLE_INDEX] == 1;
        if (this.isVisible) {
            this.lastSpotted = frame;
        }
        this.exists = unitData[index + Unit.EXISTS_INDEX] == 1;
        this.isSelected = unitData[index + Unit.IS_SELECTED_INDEX] == 1;
        this.isFlying = unitData[index + Unit.IS_FLYING_INDEX] == 1;

        this.order = Order.values()[unitData[index + Unit.ORDER_ID_INDEX]];
        this.orderTargetId = unitData[index + Unit.ORDER_TARGET_ID_INDEX];

        final int orderTargetPositionX = unitData[index + Unit.ORDER_TARGET_POSITION_X_INDEX];
        final int orderTargetPositionY = unitData[index + Unit.ORDER_TARGET_POSITION_Y_INDEX];
        this.orderTargetPosition = new Position(orderTargetPositionX, orderTargetPositionY);

        this.secondaryOrder = Order.values()[unitData[index + Unit.SECONDARY_ORDER_ID_INDEX]];
    }

    public int getLastSpotted() {

        return this.lastSpotted;
    }

    public int getInitiallySpotted() {
        return initiallySpotted;
    }

    protected Collection<Unit> getAllUnits() {

        return bw.getAllUnits();
    }

    protected Unit getUnit(int id) {

        return bw.getUnit(id);
    }

    protected DamageEvaluator getDamageEvaluator() {

        return bw.getDamageEvaluator();
    }

    protected Player getPlayer(int id) {

        return bw.getPlayer(id);
    }

    public boolean isA(UnitType type) {
        return this.type == type;
    }

    public int getId() {

        return this.id;
    }

    public int getLeft() {
        return this.x - this.type.dimensionLeft();
    }

    public int getTop() {
        return this.y - this.type.dimensionUp();
    }

    public int getRight() {
        return this.x + this.type.dimensionRight();
    }

    public int getBottom() {
        return this.y + this.type.dimensionDown();
    }

    public Position getMiddle(Unit unit) {

        int x = this.getPosition().getX();
        int y = this.getPosition().getY();

        int dx = unit.getPosition().getX() - x;
        int dy = unit.getPosition().getY() - y;

        return new Position(x + dx / 2, y + dy / 2);
    }

    public double getAngle() {

        return this.angle;
    }

    public <T extends Unit> T getClosest(Collection<T> group) {

        Comparator<T> comp = Comparator.comparingDouble(this::getDistance);
        return group.stream().min(comp).get();
    }

    public <T extends Unit> List<T> getUnitsInRadius(int radius, Collection<T> group) {

        return group.stream().filter(t -> this.getDistance(t) <= radius).collect(Collectors.toList());
    }

    public int getX() {
        return this.position.getX();
    }

    public int getY() {
        return this.position.getY();
    }

    public int height() {
        return this.type.height();
    }

    public int width() {
        return this.type.width();
    }

    public int tileHeight() {
        return this.type.tileHeight();
    }

    public int tileWidth() {
        return this.type.tileWidth();
    }

    public TilePosition getTilePosition() {
        return this.tilePosition;
    }

    public Position getPosition() {
        return this.position;
    }

    public UnitSizeType getSize() {
        return type.size();
    }

    public double getDistance(Position target) {

        return getDistance(target.getX(), target.getY());
    }

    public double getDistance(int x, int y) {
        int xDist = getLeft() - (x + 1);
        if (xDist < 0) {
            xDist = x - (getRight() + 1);
            if (xDist < 0) {
                xDist = 0;
            }
        }
        int yDist = getTop() - (y + 1);
        if (yDist < 0) {
            yDist = y - (getBottom() + 1);
            if (yDist < 0) {
                yDist = 0;
            }
        }

        return new Position(0, 0).getDistance(new Position(xDist, yDist));
    }

    public int getDistance(Unit target) {
        if (this == target) {
            return 0;
        }

        int xDist = getLeft() - (target.getRight() + 1);
        if (xDist < 0) {
            xDist = target.getLeft() - (getRight() + 1);
            if (xDist < 0) {
                xDist = 0;
            }
        }
        int yDist = getTop() - (target.getBottom() + 1);
        if (yDist < 0) {
            yDist = target.getTop() - (getBottom() + 1);
            if (yDist < 0) {
                yDist = 0;
            }
        }
        logger.trace("dx, dy: {}, {}.", xDist, yDist);

        return new Position(0, 0).getDistance(new Position(xDist, yDist));
    }

    public boolean isFlying() {

        return this.isFlying;
    }

    public boolean isVisible() {

        return this.isVisible;
    }

    public boolean isSelected() {

        return this.isSelected;
    }

    public boolean exists() {

        return this.exists;
    }

    public UnitType getInitialType() {

        return initialType;
    }

    public Position getInitialPosition() {

        return initialPosition;
    }

    public TilePosition getInitialTilePosition() {

        return initialTilePosition;
    }

    protected Order getOrder() {

        return this.order;
    }

    protected Unit getOrderTarget() {

        return (this.orderTargetId >= 0)
                ? this.getUnit(this.orderTargetId)
                : null;
    }

    protected Position getOrderTargetPosition() {

        return this.orderTargetPosition;
    }

    protected Order getSecondaryOrder() {

        return this.secondaryOrder;
    }

    protected int getCurrentFrame() {
        return bw.getInteractionHandler().getFrameCount();
    }

    @Override
    public int hashCode() {

        return this.id;
    }

    @Override
    public boolean equals(Object obj) {

        if (obj instanceof Unit) {
            return this.getId() == ((Unit) obj).getId();
        } else {
            return false;
        }
    }

    @Override
    public String toString() {

        return this.getId() + ":" + this.type;
    }

    @Override
    public int compareTo(Unit otherUnit) {

        return this.getId() - otherUnit.getId();
    }

    protected boolean issueCommand(int unitId, UnitCommandType unitCommandType, int targetUnitId, int x, int y, int extra) {
        if (issueCommand(unitId, unitCommandType.ordinal(), targetUnitId, x, y, extra)) {
            lastCommandFrame = getCurrentFrame();
            lastCommand = unitCommandType;
            return true;
        }
        return false;
    }

    private native boolean issueCommand(int unitId, int unitCommandTypeId, int targetUnitId, int x, int y, int extra);

    // --------------------------------------------------

    // dynamic
    private int replayID;

    private int resourceGroup;
    private Player lastAttackingPlayer;
    private int defenseMatrixPoints;
    private int defenseMatrixTimer;
    private int ensnareTimer;
    private int irradiateTimer;
    private int lockDownTimer;
    private int maelstromTimer;
    private int orderTimer;
    private int plagueTimer;
    private int removeTimer;
    private int stasisTimer;
    private int stimTimer;
    private UnitType buildType;
    private TechType tech;
    private UpgradeType uppgrade;

    private Unit buildUnit;
    private Order order;
    private int orderTargetId;
    private Position orderTargetPosition;
    private Order secondaryOrder;

    private boolean isMorphing;
    private boolean isTargetable;
    private boolean isInvincible;
    private boolean isInWeaponRange;
}
