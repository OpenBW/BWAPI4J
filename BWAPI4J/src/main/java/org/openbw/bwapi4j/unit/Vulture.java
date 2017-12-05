package org.openbw.bwapi4j.unit;

import org.openbw.bwapi4j.Position;
import org.openbw.bwapi4j.type.TechType;
import org.openbw.bwapi4j.type.UnitCommandType;
import org.openbw.bwapi4j.type.UnitType;

public class Vulture extends MobileUnit implements Mechanical {

    private int spiderMineCount;
    
    protected Vulture(int id) {
        
        super(id, UnitType.Terran_Vulture);
    }
    
    @Override
    public void initialize(int[] unitData, int index) {

        this.spiderMineCount = 0;
        super.initialize(unitData, index);
    }

    @Override
    public void update(int[] unitData, int index, int frame) {

        this.spiderMineCount = unitData[index + Unit.SPIDERMINE_COUNT_INDEX];
        super.update(unitData, index, frame);
    }
    
    public int getSpiderMineCount() {
        
        return this.spiderMineCount;
    }
    
    /**
     * Places a spider mine at the given position.
     * @param position target position of the spider mine to be placed
     * @return true if successful, false else
     */
    public boolean spiderMine(Position position) {
        
        return issueCommand(this.id, UnitCommandType.Use_Tech.ordinal(), -1, 
                position.getX(), position.getY(), TechType.Spider_Mines.getId());
    }
}
