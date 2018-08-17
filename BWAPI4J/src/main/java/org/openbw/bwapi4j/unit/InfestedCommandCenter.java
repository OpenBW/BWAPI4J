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

public class InfestedCommandCenter extends BuildingImpl implements Organic, FlyingBuilding, TrainingFacility {

    protected InfestedCommandCenter(int id, int timeSpotted) {
        super(id, UnitType.Zerg_Infested_Command_Center, timeSpotted);
    }


    public boolean trainInfestedTerran() {
        
        return super.train(UnitType.Zerg_Infested_Terran);
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
}
