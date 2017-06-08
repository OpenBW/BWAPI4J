package org.openbw.bwapi4j.unit;

import java.util.Map;

import org.openbw.bwapi4j.Position;
import org.openbw.bwapi4j.type.UnitCommandType;
import org.openbw.bwapi4j.type.UnitType;

public class CommandCenter extends Building implements Mechanical, FlyingBuilding, TrainingFacility {

    private int addonId;

    private Flyer flyer;
    private Trainer trainer;

    protected CommandCenter(int id, int timeSpotted) {
        
        super(id, UnitType.Terran_Command_Center, timeSpotted);
        this.flyer = new Flyer();
        this.trainer = new Trainer();
    }

    @Override
    public void initialize(int[] unitData, int index, Map<Integer, Unit> allUnits) {

        this.addonId = -1;
        super.initialize(unitData, index, allUnits);
    }

    @Override
    public void update(int[] unitData, int index) {

        this.flyer.update(unitData, index);
        this.trainer.update(unitData, index);
        this.addonId = unitData[index + Unit.ADDON_INDEX];
        super.update(unitData, index);
    }

    /**
     * Gets the Nuclear Silo Addon, if there is one.
     * @return Nuclear Silo if exists, <code>null</code> else
     */
    public NuclearSilo getNuclearSilo() {

        Unit unit = super.getUnit(addonId);
        if (unit != null && unit instanceof NuclearSilo) {
            return (NuclearSilo) unit;
        } else {
            return null;
        }
    }

    /**
     * Gets the Comsat Station Addon, if there is one.
     * @return Comsat Station if exists, <code>null</code> else
     */
    public ComsatStation getComsatStation() {

        Unit unit = super.getUnit(addonId);
        if (unit != null && unit instanceof ComsatStation) {
            return (ComsatStation) unit;
        } else {
            return null;
        }
    }

    public boolean buildComsatStation() {
        
        return issueCommand(this.id, UnitCommandType.Build_Addon.ordinal(), UnitType.Terran_Comsat_Station.getId(), -1,
                -1, -1);
    }

    public boolean buildNuclearSilo() {
        
        return issueCommand(this.id, UnitCommandType.Build_Addon.ordinal(), UnitType.Terran_Nuclear_Silo.getId(), -1,
                -1, -1);
    }

    public boolean trainWorker() {
        
        return this.trainer.train(UnitType.Terran_SCV);
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
