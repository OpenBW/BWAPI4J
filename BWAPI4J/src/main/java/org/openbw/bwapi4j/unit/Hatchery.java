package org.openbw.bwapi4j.unit;

import java.util.List;
import java.util.stream.Collectors;

import org.openbw.bwapi4j.type.TechType;
import org.openbw.bwapi4j.type.UnitCommandType;
import org.openbw.bwapi4j.type.UnitType;

public class Hatchery extends Building implements Organic, ResearchingFacility, Base {

    protected Researcher researcher;
    
    protected Hatchery(int id, UnitType type, int timeSpotted) {
        
        super(id, type, timeSpotted);
        this.researcher = new Researcher();
    }
    
    protected Hatchery(int id, int timeSpotted) {
        
        this(id, UnitType.Zerg_Hatchery, timeSpotted);
    }

    @Override
    public void update(int[] unitData, int index, int frame) {

        this.researcher.update(unitData, index);
        super.update(unitData, index, frame);
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
        
        return this.researcher.research(TechType.Burrowing);
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
    
    public boolean morph() {
        
        return issueCommand(this.id, UnitCommandType.Morph.ordinal(), -1, -1, -1, UnitType.Zerg_Lair.getId());
    }
}
