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

import org.openbw.bwapi4j.type.TechType;
import org.openbw.bwapi4j.type.UnitCommandType;
import org.openbw.bwapi4j.type.UnitType;
import org.openbw.bwapi4j.type.UpgradeType;

import static org.openbw.bwapi4j.type.UnitCommandType.Morph;
import static org.openbw.bwapi4j.type.UnitType.Zerg_Greater_Spire;
import static org.openbw.bwapi4j.type.UnitType.Zerg_Spire;

public class Spire extends Building implements Organic, ResearchingFacility, Morphable {

    private Researcher researcher;
    
    protected Spire(int id, int timeSpotted) {
        this(id, Zerg_Spire, timeSpotted);
    }

    protected Spire(int id, UnitType type, int timeSpotted) {

        super(id, type, timeSpotted);
        this.researcher = new Researcher();
    }

    @Override
    public void update(int[] unitData, int index, int frame) {

        super.update(unitData, index, frame);
        this.researcher.update(unitData, index);
    }
    
    public boolean upgradeFlyerAttacks() {
        
        return this.researcher.upgrade(UpgradeType.Zerg_Flyer_Attacks);
    }
    
    public boolean upgradeFlyerCarapace() {
        
        return this.researcher.upgrade(UpgradeType.Zerg_Flyer_Carapace);
    }
    
    @Override
    public boolean isUpgrading() {
        
        return this.researcher.isUpgrading();
    }

    @Override
    public boolean isResearching() {
        
        return this.researcher.isResearching();
    }

    @Override
    public boolean cancelResearch() {
        
        return this.researcher.cancelResearch();
    }

    @Override
    public boolean cancelUpgrade() {
        
        return this.researcher.cancelUpgrade();
    }

    @Override
    public boolean morph(UnitType type) {
        if (type != Zerg_Greater_Spire) {
            throw new IllegalArgumentException("Cannot morph to " + type);
        }
        return issueCommand(this.id, Morph, -1, -1, -1, Zerg_Greater_Spire.getId());
    }

    @Override
    public boolean canResearch(TechType techType) {
        return this.researcher.canResearch(techType);
    }

    @Override
    public boolean canUpgrade(UpgradeType upgradeType) {
        return this.researcher.canUpgrade(upgradeType);
    }

    @Override
    public boolean research(TechType techType) {
        return this.researcher.research(techType);
    }

    @Override
    public boolean upgrade(UpgradeType upgradeType) {
        return this.researcher.upgrade(upgradeType);
    }

    @Override
    public UpgradeInProgress getUpgradeInProgress() {
        return researcher.getUpgradeInProgress();
    }

    @Override
    public ResearchInProgress getResearchInProgress() {
        return researcher.getResearchInProgress();
    }
}
