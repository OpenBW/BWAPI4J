package org.openbw.bwapi4j.unit;

import org.openbw.bwapi4j.Position;
import org.openbw.bwapi4j.type.UnitCommandType;
import org.openbw.bwapi4j.type.UnitType;

public class CommandCenter extends Building implements Mechanical, FlyingBuilding, TrainingFacility, ExtendibleByAddon, Base {

    private int addonId;

    private Flyer flyer;
    private Trainer trainer;

    protected CommandCenter(int id, int timeSpotted) {
        
        super(id, UnitType.Terran_Command_Center, timeSpotted);
        this.flyer = new Flyer();
        this.trainer = new Trainer();
    }

    @Override
    public void initialize(int[] unitData, int index) {

        this.addonId = -1;
        super.initialize(unitData, index);
    }

    @Override
    public void update(int[] unitData, int index, int frame) {

        this.flyer.update(unitData, index);
        this.trainer.update(unitData, index);
        this.addonId = unitData[index + Unit.ADDON_INDEX];
        super.update(unitData, index, frame);
    }

    /**
     * Gets the Nuclear Silo addon, if there is one.
     * @return Nuclear Silo if exists, <code>null</code> else
     */
    public NuclearSilo getNuclearSilo() {

        Unit unit = this.getUnit(addonId);
        if (unit != null && unit instanceof NuclearSilo) {
            return (NuclearSilo) unit;
        } else {
            return null;
        }
    }

    /**
     * Gets the Comsat Station addon, if there is one.
     * @return Comsat Station if exists, <code>null</code> else
     */
    public ComsatStation getComsatStation() {

        Unit unit = this.getUnit(addonId);
        if (unit != null && unit instanceof ComsatStation) {
            return (ComsatStation) unit;
        } else {
            return null;
        }
    }

    @Override
    public boolean cancelAddon() {
        
        return issueCommand(this.id, UnitCommandType.Cancel_Addon.ordinal(), -1, -1, -1, -1);
    }
    
    public boolean buildComsatStation() {
        
        return issueCommand(this.id, UnitCommandType.Build_Addon.ordinal(), -1, -1, -1, UnitType.Terran_Comsat_Station.getId());
    }

    public boolean buildNuclearSilo() {
        
        return issueCommand(this.id, UnitCommandType.Build_Addon.ordinal(), -1, -1, -1, UnitType.Terran_Nuclear_Silo.getId());
    }

    @Override
    public boolean build(UnitType addon) {
        return issueCommand(this.id, UnitCommandType.Build_Addon.ordinal(), -1, -1, -1, addon.getId());
    }

    public boolean trainWorker() {
        
        return this.trainer.train(UnitType.Terran_SCV);
    }

    @Override
    public boolean canTrain(UnitType type) {
        return this.trainer.canTrain(type);
    }

    @Override
    public boolean train(UnitType type) {
        return this.trainer.train(type);
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
