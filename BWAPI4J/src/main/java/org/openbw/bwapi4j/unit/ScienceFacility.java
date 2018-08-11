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
import org.openbw.bwapi4j.type.UnitType;
import org.openbw.bwapi4j.type.UpgradeType;

import static org.openbw.bwapi4j.type.UnitCommandType.Build_Addon;
import static org.openbw.bwapi4j.type.UnitCommandType.Cancel_Addon;
import static org.openbw.bwapi4j.type.UnitType.Terran_Covert_Ops;
import static org.openbw.bwapi4j.type.UnitType.Terran_Physics_Lab;

public class ScienceFacility extends BuildingImpl implements Mechanical, ResearchingFacility, ExtendibleByAddon {

    protected ScienceFacility(int id, int timeSpotted) {
        
        super(id, UnitType.Terran_Science_Facility, timeSpotted);
    }

    /**
     * Gets the Covert Ops addon, if there is one.
     * @return Covert Ops if exists, <code>null</code> else
     */
    public CovertOps getCovertOps() {

        Unit unit = this.getUnit(addonId);
        if (unit != null && unit instanceof CovertOps) {
            return (CovertOps) unit;
        } else {
            return null;
        }
    }

    /**
     * Gets the Physics Lab addon, if there is one.
     * @return Physics Lab if exists, <code>null</code> else
     */
    public PhysicsLab getPhysicsLab() {

        Unit unit = this.getUnit(addonId);
        if (unit != null && unit instanceof PhysicsLab) {
            return (PhysicsLab) unit;
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
    
    /**
     * Builds a Physics Lab addon to this science facility.
     * @return true if command successful, false else
     */
    public boolean buildPhysicslab() {
        
        return issueCommand(this.id, Build_Addon, -1, -1, -1, Terran_Physics_Lab.getId());
    }

    /**
     * Builds a Covert Ops addon to this science facility.
     * @return true if command successful, false else
     */
    public boolean buildCovertOps() {
        
        return issueCommand(this.id, Build_Addon, -1, -1, -1, Terran_Covert_Ops.getId());
    }

    @Override
    public boolean build(UnitType addon) {
        return issueCommand(this.id, Build_Addon, -1, -1, -1, addon.getId());
    }

    public boolean researchEmpShockwave() {
        
        return super.research(TechType.EMP_Shockwave);
    }

    public boolean researchIrradiate() {
        
        return super.research(TechType.Irradiate);
    }

    public boolean upgradeTitanReactor() {
        
        return super.upgrade(UpgradeType.Titan_Reactor);
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
}
