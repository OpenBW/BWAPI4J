package org.openbw.bwapi4j.unit;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openbw.bwapi4j.Position;
import org.openbw.bwapi4j.type.TechType;
import org.openbw.bwapi4j.type.UnitCommandType;
import org.openbw.bwapi4j.type.UnitType;

public class Ghost extends MobileUnit implements SpellCaster, Organic {

    private static final Logger logger = LogManager.getLogger();

    private int energy;

    protected Ghost(int id) {
        
        super(id, UnitType.Terran_Ghost);
    }

    @Override
    public void initialize(int[] unitData, int index) {

        this.energy = 0;
        super.initialize(unitData, index);
    }

    @Override
    public void update(int[] unitData, int index, int frame) {

        this.energy = unitData[index + Unit.ENERGY_INDEX];
        super.update(unitData, index, frame);
    }

    @Override
    public int getEnergy() {
        
        return this.energy;
    }

    public boolean personnelCloaking() {
        
        return issueCommand(this.id, UnitCommandType.Use_Tech.ordinal(), -1, -1, -1,
                TechType.Personnel_Cloaking.getId());
    }

    /**
     * Use the Lockdown ability on a mechanical unit.
     * @param unit target unit
     * @return true if command succeeded, false else.
     */
    public boolean lockdown(Mechanical unit) {
        
        if (unit instanceof Unit) {
            return issueCommand(this.id, UnitCommandType.Use_Tech_Unit.ordinal(), ((Unit) unit).getId(), -1, -1,
                    TechType.Lockdown.getId());
        } else {
            logger.error("unit {} is not a valid target for lockDown.", unit);
            return false;
        }
    }

    public boolean nuclearStrike(Position p) {
        
        return issueCommand(this.id, UnitCommandType.Use_Tech_Position.ordinal(), -1, p.getX(), p.getY(),
                TechType.Nuclear_Strike.getId());
    }
}
