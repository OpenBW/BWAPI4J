package org.openbw.bwapi4j.unit;

import java.util.Collection;
import java.util.Comparator;
import java.util.List;
import java.util.Map;

import org.openbw.bwapi4j.Player;
import org.openbw.bwapi4j.Position;
import org.openbw.bwapi4j.TilePosition;
import org.openbw.bwapi4j.type.UnitType;

public abstract class PlayerUnit extends Unit {

    // static
    protected int initialHitPoints;

    // dynamic
    protected int hitPoints;
    protected boolean isCloaked;
    protected boolean isDetected;
    protected boolean isFlying;
    protected double velocityX;
    protected double velocityY;
    protected boolean isIdle;
    protected boolean isCompleted;
    protected int groundWeaponCooldown;
    protected int airWeaponCooldown;
    protected int spellCooldown;

    // TODO set player
    protected Player player;

    // other
    private Position lastKnownPosition;
    private TilePosition lastKnownTilePosition;
    private int lastKnownHitPoints;

    protected PlayerUnit(int id, UnitType unitType) {
        super(id, unitType);
    }

    @Override
    public int initialize(int[] unitData, int index, Map<Integer, Unit> allUnits) {

        index = super.initialize(unitData, index, allUnits);
        this.initialHitPoints = unitData[index + Unit.INITIAL_HITPOINTS_INDEX];

        return index;
    }

    @Override
    public int update(int[] unitData, int index) {

        index = super.update(unitData, index);
        this.hitPoints = unitData[index + Unit.HITPOINTS_INDEX];
        this.isCloaked = unitData[index + Unit.IS_CLOAKED_INDEX] == 1;
        this.isDetected = unitData[index + Unit.IS_DETECTED_INDEX] == 1;
        this.isFlying = unitData[index + Unit.IS_FLYING_INDEX] == 1;
        this.velocityX = unitData[index + Unit.VELOCITY_X_INDEX] / 100.0;
        this.velocityY = unitData[index + Unit.VELOCITY_Y_INDEX] / 100.0;
        this.isIdle = unitData[index + Unit.IS_IDLE_INDEX] == 1;
        this.isCompleted = unitData[index + Unit.IS_COMPLETED_INDEX] == 1;
        this.groundWeaponCooldown = unitData[index + Unit.GROUND_WEAPON_COOLDOWN_INDEX];
        this.airWeaponCooldown = unitData[index + Unit.AIR_WEAPON_COOLDOWN_INDEX];
        this.spellCooldown = unitData[index + Unit.SPELL_COOLDOWN_INDEX];

        if (super.isVisible) {
            this.lastKnownPosition = super.position;
            this.lastKnownTilePosition = super.tilePosition;
            this.lastKnownHitPoints = this.hitPoints;
        }
        return index;
    }

    /**
     * Convenience method to be used e.g. when doing target-file micro (just
     * provide unit weapon range as radius).
     * 
     * @param radius
     *            pixel radius
     * @param units
     *            units to check for distance
     * @return weakest unit within given radius
     */
    public <T extends PlayerUnit> T getWeakestUnitInRadius(int radius, Collection<T> units) {

        List<T> inRange = this.getUnitsInRadius(radius, units);
        T weakestUnit;
        if (inRange.isEmpty()) {
            weakestUnit = this.getClosest(units);
        } else {
            Comparator<T> comp = (u1, u2) -> Integer.compare(u1.getHitPoints(), u2.getHitPoints());
            weakestUnit = inRange.parallelStream().min(comp).get();
        }
        return weakestUnit;
    }

    public boolean isCompleted() {
        return this.isCompleted;
    }

    public int maxHitPoints() {
        return this.type.maxHitPoints();
    }

    public int getHitPoints() {
        return this.hitPoints;
    }

    public int getGroundWeaponCooldown() {
        return groundWeaponCooldown;
    }

    public int getAirWeaponCooldown() {
        return airWeaponCooldown;
    }

    public int getSpellCooldown() {
        return spellCooldown;
    }

    public Player getPlayer() {
        return player;
    }

    public int getInitialHitPoints() {
        return this.initialHitPoints;
    }

    public int getMineralPrice() {
        return this.type.mineralPrice();
    }

    public int getGasPrice() {
        return this.type.gasPrice();
    }

    public int getLastKnownHitPoints() {
        return this.lastKnownHitPoints;
    }

    public Position getLastKnownPosition() {
        return this.lastKnownPosition;
    }

    public TilePosition getLastKnownTilePosition() {
        return this.lastKnownTilePosition;
    }

    public int getSightRange() {
        return this.type.sightRange();
    }

    public boolean isFlying() {
        return this.isFlying;
    }

    public boolean isDetected() {
        return this.isDetected;
    }

    public boolean isCloaked() {
        return this.isCloaked;
    }

    public boolean isFlyer() {
        return this.type.isFlyer();
    }

    public int tileWidth() {
        return this.type.tileWidth();
    }

    public int tileHeight() {
        return this.type.tileHeight();
    }

    public double topSpeed() {
        return this.type.topSpeed();
    }

    public double getVelocityX() {
        return this.velocityX;
    }

    public double getVelocityY() {
        return this.velocityY;
    }

    public boolean isIdle() {
        return this.isIdle;
    }

    public int getDamageTo(PlayerUnit to) {

        return 0;
        // TODO return damageEvaluator.getDamageTo(to.bwUnit.getType(),
        // this.unitType, to.bwUnit.getPlayer(), this.bwUnit.getPlayer());
    }

    public int getDamageFrom(PlayerUnit from) {

        return 0;
        // TODO return damageEvaluator.getDamageFrom(from.bwUnit.getType(),
        // this.unitType, from.bwUnit.getPlayer(), this.bwUnit.getPlayer());
    }
}
