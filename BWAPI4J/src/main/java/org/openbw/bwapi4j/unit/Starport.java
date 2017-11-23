package org.openbw.bwapi4j.unit;

import org.openbw.bwapi4j.Position;
import org.openbw.bwapi4j.type.UnitCommandType;
import org.openbw.bwapi4j.type.UnitType;

public class Starport extends Building implements Mechanical, FlyingBuilding, TrainingFacility {

    private int addonId;
    
    private Flyer flyer;
    private Trainer trainer;

    protected Starport(int id, int timeSpotted) {
        
        super(id, UnitType.Terran_Starport, timeSpotted);
        this.flyer = new Flyer();
        this.trainer = new Trainer();
    }

    @Override
    public void initialize(int[] unitData, int index) {

        this.addonId = -1;
        super.initialize(unitData, index);
    }
    
    @Override
    public void update(int[] unitData, int index) {

        this.flyer.update(unitData, index);
        this.trainer.update(unitData, index);
        this.addonId = unitData[index + Unit.ADDON_INDEX];
        super.update(unitData, index);
    }

    public boolean cancelAddon() {
        
        return issueCommand(this.id, UnitCommandType.Cancel_Addon.ordinal(), -1, -1, -1, -1);
    }
    
    /**
     * Builds a control tower addon to this starport.
     * @return true if command successful, false else
     */
    public boolean buildControlTower() {
        
        return issueCommand(this.id, UnitCommandType.Build_Addon.ordinal(), UnitType.Terran_Control_Tower.getId(), -1,
                -1, -1);
    }

    public ControlTower getControlTower() {
        
        return (ControlTower) this.getUnit(this.addonId);
    }
    
    public boolean trainWraith() {
        
        return this.trainer.train(UnitType.Terran_Wraith);
    }

    public boolean trainDropship() {
        
        return this.trainer.train(UnitType.Terran_Dropship);
    }

    public boolean trainScienceVessel() {
        
        return this.trainer.train(UnitType.Terran_Science_Vessel);
    }

    public boolean trainValkyrie() {
        
        return this.trainer.train(UnitType.Terran_Valkyrie);
    }

    public boolean trainBattlecruiser() {
        
        return this.trainer.train(UnitType.Terran_Battlecruiser);
    }

    @Override
    public boolean isLifted() {
        
        return this.flyer.isLifted();
    }

    @Override
    public boolean lift() {
        
        return this.flyer.lift();
    }

    @Override
    public boolean land(Position p) {
        
        return this.flyer.land(p);
    }

    @Override
    public boolean move(Position p) {
        
        return this.flyer.move(p);
    }

    @Override
    public boolean isTraining() {
        
        return this.trainer.isTraining();
    }

    @Override
    public int getTrainingQueueSize() {
        
        return this.trainer.getTrainingQueueSize();
    }

    @Override
    public boolean cancelTrain(int slot) {
        
        return this.trainer.cancelTrain(slot);
    }

    @Override
    public boolean cancelTrain() {
        
        return this.trainer.cancelTrain();
    }

    @Override
    public boolean setRallyPoint(Position p) {
        
        return this.trainer.setRallyPoint(p);
    }

    @Override
    public boolean setRallyPoint(Unit target) {
        
        return this.trainer.setRallyPoint(target);
    }
}
