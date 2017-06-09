package org.openbw.bwapi4j.unit;

import org.openbw.bwapi4j.type.TechType;
import org.openbw.bwapi4j.type.UnitCommandType;
import org.openbw.bwapi4j.type.UnitType;
import org.openbw.bwapi4j.type.UpgradeType;

public class ScienceFacility extends Building implements Mechanical, ResearchingFacility {

    private int addonId;
    private Researcher researcher;

    protected ScienceFacility(int id, int timeSpotted) {
        
        super(id, UnitType.Terran_Science_Facility, timeSpotted);
        this.researcher = new Researcher();
    }

    @Override
    public void initialize(int[] unitData, int index) {

        this.addonId = -1;
        super.initialize(unitData, index);
    }
    
    @Override
    public void update(int[] unitData, int index) {

        this.researcher.update(unitData, index);
        this.addonId = unitData[index + Unit.ADDON_INDEX];
        super.update(unitData, index);
    }

    /**
     * Gets the Covert Ops addon, if there is one.
     * @return Covert Ops if exists, <code>null</code> else
     */
    public CovertOps getCovertOps() {

        Unit unit = super.getUnit(addonId);
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

        Unit unit = super.getUnit(addonId);
        if (unit != null && unit instanceof PhysicsLab) {
            return (PhysicsLab) unit;
        } else {
            return null;
        }
    }

    public boolean cancelAddon() {
        
        return issueCommand(this.id, UnitCommandType.Cancel_Addon.ordinal(), -1, -1, -1, -1);
    }
    
    /**
     * Builds a Physics Lab addon to this science facility.
     * @return true if command successful, false else
     */
    public boolean buildPhysicslab() {
        
        return issueCommand(this.id, UnitCommandType.Build_Addon.ordinal(), UnitType.Terran_Physics_Lab.getId(), -1,
                -1, -1);
    }

    /**
     * Builds a Covert Ops addon to this science facility.
     * @return true if command successful, false else
     */
    public boolean buildCovertOps() {
        
        return issueCommand(this.id, UnitCommandType.Build_Addon.ordinal(), UnitType.Terran_Covert_Ops.getId(), -1,
                -1, -1);
    }

    public boolean researchEmpShockwave() {
        
        return this.researcher.research(TechType.EMP_Shockwave);
    }

    public boolean researchIrradiate() {
        
        return this.researcher.research(TechType.Irradiate);
    }

    public boolean upgradeTitanReactor() {
        
        return this.researcher.upgrade(UpgradeType.Titan_Reactor);
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
}
