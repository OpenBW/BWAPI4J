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

import org.openbw.bwapi4j.Position;
import org.openbw.bwapi4j.type.UnitType;

import java.util.List;

import static org.openbw.bwapi4j.type.UnitCommandType.Build_Addon;
import static org.openbw.bwapi4j.type.UnitCommandType.Cancel_Addon;
import static org.openbw.bwapi4j.type.UnitType.Terran_Comsat_Station;
import static org.openbw.bwapi4j.type.UnitType.Terran_Nuclear_Silo;

public class CommandCenter extends Building implements Mechanical, FlyingBuilding, TrainingFacility, ExtendibleByAddon, ResourceDepot {

    protected CommandCenter(int id, int timeSpotted) {
        
        super(id, UnitType.Terran_Command_Center, timeSpotted);
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
    public Addon getAddon() {
        return (Addon) getUnit(addonId);
    }

    @Override
    public boolean cancelAddon() {
        
        return issueCommand(this.id, Cancel_Addon, -1, -1, -1, -1);
    }
    
    public boolean buildComsatStation() {
        
        return issueCommand(this.id, Build_Addon, -1, -1, -1, Terran_Comsat_Station.getId());
    }

    public boolean buildNuclearSilo() {
        
        return issueCommand(this.id, Build_Addon, -1, -1, -1, Terran_Nuclear_Silo.getId());
    }

    @Override
    public boolean build(UnitType addon) {
        return issueCommand(this.id, Build_Addon, -1, -1, -1, addon.getId());
    }

    public boolean trainWorker() {
        
        return super.train(UnitType.Terran_SCV);
    }

    @Override
    public boolean canTrain(UnitType type) {
        return super.canTrain(type);
    }

    @Override
    public boolean train(UnitType type) {
        return super.train(type);
    }

    @Override
    public boolean isLifted() {
        
        return isLifted;
    }

    @Override
    public boolean lift() {
        
        return super.lift();
    }

    @Override
    public boolean land(Position p) {
        
        return super.land(p);
    }

    @Override
    public boolean move(Position p) {
        
        return super.move(p);
    }

    @Override
    public boolean isTraining() {
        
        return isTraining;
    }

    @Override
    public int getTrainingQueueSize() {
        
        return trainingQueueSize;
    }

    @Override
    public List<TrainingSlot> getTrainingQueue() {

        return trainingQueue;
    }

    @Override
    public boolean cancelTrain(int slot) {
        
        return super.cancelTrain(slot);
    }

    @Override
    public boolean cancelTrain() {
        
        return super.cancelTrain();
    }

    @Override
    public boolean setRallyPoint(Position p) {
        
        return super.setRallyPoint(p);
    }

    @Override
    public boolean setRallyPoint(Unit target) {
        
        return super.setRallyPoint(target);
    }

    @Override
    public int getRemainingTrainTime() {
        return remainingTrainTime;
    }

    @Override
    public int supplyProvided() {
        return type.supplyProvided();
    }
}
