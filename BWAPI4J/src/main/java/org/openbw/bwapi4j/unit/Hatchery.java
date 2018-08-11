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

import org.openbw.bwapi4j.type.TechType;
import org.openbw.bwapi4j.type.UnitType;
import org.openbw.bwapi4j.type.UpgradeType;

import static org.openbw.bwapi4j.type.UnitCommandType.Morph;
import static org.openbw.bwapi4j.type.UnitType.Zerg_Lair;

public class Hatchery extends BuildingImpl implements Organic, ResearchingFacility, ResourceDepot, Morphable {

    protected Hatchery(int id, UnitType type, int timeSpotted) {
        
        super(id, type, timeSpotted);
    }
    
    protected Hatchery(int id, int timeSpotted) {
        
        this(id, UnitType.Zerg_Hatchery, timeSpotted);
    }

    @Override
    public boolean isReadyForResources() {
        return isCompleted;
    }

    @Override
    public boolean trainWorker() {
        return super.train(UnitType.Zerg_Drone);
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

    public boolean researchBurrowing() {
        
        return super.research(TechType.Burrowing);
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
    public boolean morph(UnitType type) {
        if (type != Zerg_Lair) {
            throw new IllegalArgumentException("Cannot morph to " + type);
        }

        return issueCommand(this.id, Morph, -1, -1, -1, Zerg_Lair.getId());
    }

    public boolean morph() {
        return morph(Zerg_Lair);
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
}
