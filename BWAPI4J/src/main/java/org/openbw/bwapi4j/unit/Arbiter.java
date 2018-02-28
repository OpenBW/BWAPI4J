package org.openbw.bwapi4j.unit;

import org.openbw.bwapi4j.Position;
import org.openbw.bwapi4j.type.TechType;
import org.openbw.bwapi4j.type.UnitCommandType;
import org.openbw.bwapi4j.type.UnitType;

import static org.openbw.bwapi4j.type.TechType.Recall;
import static org.openbw.bwapi4j.type.TechType.Stasis_Field;
import static org.openbw.bwapi4j.type.UnitCommandType.Use_Tech_Position;

public class Arbiter extends MobileUnit implements Mechanical, SpellCaster, Armed {

    private int energy;

    protected Arbiter(int id) {
        
        super(id, UnitType.Protoss_Arbiter);
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
    
    public boolean stasisField(Position position) {
        
        if (this.energy < TechType.Stasis_Field.energyCost()) {
            return false;
        } else {
            return issueCommand(this.id, Use_Tech_Position, -1,
                    position.getX(), position.getY(), Stasis_Field.getId());
        }
    }
    
    public boolean recall(Position position) {
        
        if (this.energy < TechType.Recall.energyCost()) {
            return false;
        } else {
            return issueCommand(this.id, Use_Tech_Position, -1,
                    position.getX(), position.getY(), Recall.getId());
        }
    }

    @Override
    public Weapon getGroundWeapon() {
        return groundWeapon;
    }

    @Override
    public Weapon getAirWeapon() {
        return airWeapon;
    }
}
