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

import java.util.List;
import java.util.stream.Collectors;

import org.openbw.bwapi4j.Position;
import org.openbw.bwapi4j.type.TechType;
import org.openbw.bwapi4j.type.UnitType;
import org.openbw.bwapi4j.type.UpgradeType;
import org.openbw.bwapi4j.unit.UnitImpl.TrainingSlot;

public abstract class ZergResourceDepotImpl extends BuildingImpl implements Organic, ResourceDepot, ResearchingFacility, Morphable, TrainingFacility {

	public ZergResourceDepotImpl(int id, UnitType unitType, int timeSpotted) {
		
		super(id, unitType, timeSpotted);
	}
	
    @Override
    public boolean isReadyForResources() {
        return isCompleted;
    }
    
    /**
     * Retrieves a list of larvae present at this hatchery.
     * @return list of larvae
     */
    public List<Larva> getLarva() {
        return super.getAllUnits().stream()
                .filter(u -> u instanceof Larva && ((Larva)u).getHatchery().getId() == this.getId())
                .map(u -> (Larva)u).collect(Collectors.toList());
    }
	
    @Override
    public boolean trainWorker() {
        return super.train(UnitType.Zerg_Drone);
    }

    @Override
    public boolean isUpgrading() {
        return isUpgrading;
    }

    @Override
    public boolean isResearching() {
        return isResearching;
    }

    @Override
    public boolean cancelResearch() {
        return super.cancelResearch();
    }

    @Override
    public boolean cancelUpgrade() {
        return super.cancelUpgrade();
    }

    @Override
    public boolean canResearch(TechType techType) {
        return super.canResearch(techType);
    }

    @Override
    public boolean canUpgrade(UpgradeType upgradeType) {
        return super.canUpgrade(upgradeType);
    }

    @Override
    public boolean research(TechType techType) {
        return super.research(techType);
    }

    @Override
    public boolean upgrade(UpgradeType upgradeType) {
        return super.upgrade(upgradeType);
    }

    @Override
    public UpgradeInProgress getUpgradeInProgress() {
        return super.getUpgradeInProgress();
    }

    @Override
    public ResearchInProgress getResearchInProgress() {
        return super.getResearchInProgress();
    }
	
    @Override
    public int supplyProvided() {
        return type.supplyProvided();
    }
    
    public boolean researchBurrowing() {
        return super.research(TechType.Burrowing);
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
    public boolean canTrain(UnitType type) {
        return UnitType.Zerg_Larva.equals(type.whatBuilds().getFirst())
                && getPlayer(playerId).canMake(type);
        //I feel that a check on whether there are any larva attached to this Hatchery would be appropriate, but there's no tracking for that and getLarva() is expensive
    }
    
    //This returns the time until the next larva for Zerg! 
    @Override
    public int getRemainingTrainTime() {
        return remainingTrainTime;
    }

    @Override
    public List<TrainingSlot> getTrainingQueue() {
        return trainingQueue;
    }
    
    @Override
    public int getTrainingQueueSize() {
        return trainingQueueSize;
    }
    
    @Override
    public boolean isTraining() {
        return isTraining;
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
    public boolean train(UnitType type) {
        return super.train(type);
    }
    
    public abstract boolean morph(UnitType type);
    public abstract boolean morph();
    
}
