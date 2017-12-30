package org.openbw.bwapi4j.unit;

import java.util.Collection;
import java.util.Comparator;
import java.util.List;

import org.openbw.bwapi4j.Player;
import org.openbw.bwapi4j.Position;
import org.openbw.bwapi4j.TilePosition;
import org.openbw.bwapi4j.type.UnitCommandType;
import org.openbw.bwapi4j.type.UnitType;
import org.openbw.bwapi4j.type.WeaponType;

public abstract class PlayerUnit extends Unit {

    // static
    protected int initialHitPoints;

    // dynamic
    protected int hitPoints;
    protected int shields;
    protected int killCount;
    protected boolean isCloaked;
    protected boolean isDetected;
    protected double velocityX;
    protected double velocityY;
    protected boolean isIdle;
    protected boolean isCompleted;
    protected Weapon groundWeapon = new Weapon(WeaponType.None, 0);
    protected Weapon airWeapon = new Weapon(WeaponType.None, 0);
    protected int spellCooldown;
    protected int targetId;
    private boolean isAccelerating;
    private boolean isAttacking;
    private boolean isAttackFrame;
    private boolean isBeingConstructed;
    private boolean isBeingHealed;
    private boolean isIrradiated;
    private boolean isLockedDown;
    private boolean isMaelstrommed;
    private boolean isStartingAttack;
    private boolean isUnderAttack;
    private boolean isPowered;
    
    protected int playerId;

    // other
    private Position lastKnownPosition;
    private TilePosition lastKnownTilePosition;
    private int lastKnownHitPoints;

    protected PlayerUnit(int id, UnitType unitType) {
        
        super(id, unitType);
    }

    @Override
    public void initialize(int[] unitData, int index) {

        this.initialHitPoints = unitData[index + Unit.INITIAL_HITPOINTS_INDEX];
        super.initialize(unitData, index);
        
        this.lastKnownPosition = this.initialPosition;
        this.lastKnownTilePosition = this.initialTilePosition;
        this.lastKnownHitPoints = this.initialHitPoints;
    }

    @Override
    public void update(int[] unitData, int index, int frame) {

        this.playerId = unitData[index + Unit.PLAYER_ID_INDEX];
        this.hitPoints = unitData[index + Unit.HITPOINTS_INDEX];
        this.shields = unitData[index + Unit.SHIELDS_INDEX];
        this.killCount = unitData[index + Unit.KILLCOUNT_INDEX];
        this.isCloaked = unitData[index + Unit.IS_CLOAKED_INDEX] == 1;
        this.isDetected = unitData[index + Unit.IS_DETECTED_INDEX] == 1;
        this.velocityX = unitData[index + Unit.VELOCITY_X_INDEX] / 100.0;
        this.velocityY = unitData[index + Unit.VELOCITY_Y_INDEX] / 100.0;
        this.isIdle = unitData[index + Unit.IS_IDLE_INDEX] == 1;
        this.isCompleted = unitData[index + Unit.IS_COMPLETED_INDEX] == 1;
        this.spellCooldown = unitData[index + Unit.SPELL_COOLDOWN_INDEX];
        this.isAccelerating = unitData[index + Unit.IS_ACCELERATING_INDEX] == 1;
        this.isAttacking = unitData[index + Unit.IS_ATTACKING_INDEX] == 1;
        this.isAttackFrame = unitData[index + Unit.IS_ATTACK_FRAME_INDEX] == 1;
        this.isBeingConstructed = unitData[index + Unit.IS_BEING_CONSTRUCTED_INDEX] == 1;
        this.isBeingHealed = unitData[index + Unit.IS_BEING_HEALED_INDEX] == 1;
        this.isIrradiated = unitData[index + Unit.IS_IRRADIATED_INDEX] == 1;
        this.isLockedDown = unitData[index + Unit.IS_LOCKED_DOWN_INDEX] == 1;
        this.isMaelstrommed = unitData[index + Unit.IS_MAELSTROMMED_INDEX] == 1;
        this.isStartingAttack = unitData[index + Unit.IS_STARTING_ATTACK_INDEX] == 1;
        this.isUnderAttack = unitData[index + Unit.IS_UNDER_ATTACK_INDEX] == 1;
        this.isPowered = unitData[index + Unit.IS_POWERED_INDEX] == 1;
        this.targetId = unitData[index + Unit.TARGET_ID_INDEX];

        super.update(unitData, index, frame);

        this.groundWeapon.update(type.groundWeapon(), unitData[index + Unit.GROUND_WEAPON_COOLDOWN_INDEX]);
        this.airWeapon.update(type.airWeapon(), unitData[index + Unit.AIR_WEAPON_COOLDOWN_INDEX]);

        if (this.isVisible) {
        	
            this.lastKnownPosition = this.position;
            this.lastKnownTilePosition = this.tilePosition;
            this.lastKnownHitPoints = this.hitPoints;
        }
    }

    protected Unit getTargetUnit() {

        return this.getUnit(this.targetId);
    }

    /**
     * Convenience method to be used e.g. when doing target-fire micro (just
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
            
            Comparator<T> comp = Comparator.comparingInt(PlayerUnit::getHitPoints);
            weakestUnit = inRange.stream().min(comp).get();
        }
        return weakestUnit;
    }

    /**
     * Perform the right-click command on a given position.
     * @param position the position to right-click to
     * @param queued true if command is queued, false else
     * @return true if command is successful, false else
     */
    public boolean rightClick(Position position, boolean queued) {
        
        return issueCommand(this.id, UnitCommandType.Right_Click_Position.ordinal(), -1, 
                position.getX(), position.getY(), queued ? 1 : 0);
    }

    /**
     * Perform the right-click command on a given unit.
     * @param target the unit to right-click to
     * @param queued true if command is queued, false else
     * @return true if command is successful, false else
     */
    public boolean rightClick(Unit target, boolean queued) {
        
        return issueCommand(this.id, UnitCommandType.Right_Click_Unit.ordinal(), target.getId(), -1, -1,
                queued ? 1 : 0);
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

    public int getArmor() {
        return type.armor();
    }

    public int getShields() {
    	
    	return this.shields;
    }
    
    public int getSpellCooldown() {
        
        return this.spellCooldown;
    }

    public Player getPlayer() {
        
        return this.getPlayer(this.playerId);
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

    public boolean isAccelerating() {
        
        return this.isAccelerating;
    }
    
    public boolean isAttacking() {
        
        return this.isAttacking;
    }
    
    public boolean isAttackFrame() {
        
        return this.isAttackFrame;
    }
    
    public boolean isBeingConstructed() {
        
        return this.isBeingConstructed;
    }
    
    public boolean isBeingHealed() {
        
        return this.isBeingHealed;
    }
    
    public boolean isIrradiated() {
        
        return this.isIrradiated;
    }
    
    public boolean isLockedDown() {
        
        return this.isLockedDown;
    }
    
    public boolean isMaelstrommed() {
        
        return this.isMaelstrommed;
    }
    
    public boolean isStartingAttack() {
        
        return this.isStartingAttack;
    }
    
    public boolean isUnderAttack() {
        
        return this.isUnderAttack;
    }
    
    public boolean isPowered() {
        
        return this.isPowered;
    }
    
    public int getDamageTo(PlayerUnit to) {

        return this.getDamageEvaluator().getDamageTo(to.initialType, this.initialType, to.getPlayer(), this.getPlayer());
    }

    public int getDamageFrom(PlayerUnit from) {

    	return this.getDamageEvaluator().getDamageFrom(from.initialType, this.initialType, from.getPlayer(), this.getPlayer());
    }
}
