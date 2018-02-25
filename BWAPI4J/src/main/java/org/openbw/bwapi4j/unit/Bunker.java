package org.openbw.bwapi4j.unit;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openbw.bwapi4j.type.UnitCommandType;
import org.openbw.bwapi4j.type.UnitType;

public class Bunker extends Building implements Mechanical, Loadable {

    private static final Logger logger = LogManager.getLogger();

    private boolean isLoaded;

    protected Bunker(final int id, final int timeSpotted) {
        super(id, UnitType.Terran_Bunker, timeSpotted);
    }

    @Override
    public void update(final int[] unitData, final int index, final int frame) {
        this.isLoaded = unitData[index + Unit.IS_LOADED_INDEX] == 1;
        super.update(unitData, index, frame);
    }

    public boolean isLoaded() {
        return this.isLoaded;
    }

    @Override
    public boolean load(final MobileUnit target) {
        return load(target, false);
    }

    @Override
    public boolean load(final MobileUnit target, final boolean queued) {
        if (target.isFlyer()) {
            logger.error("Can't load a {} into a garrison. Only non-flying units allowed.", target);
            return false;
        } else {
            return issueCommand(this.id, UnitCommandType.Load.ordinal(), target.getId(), -1, -1, queued ? 1 : 0);
        }
    }

    @Override
    public boolean unload(final MobileUnit target) {
        return issueCommand(this.id, UnitCommandType.Unload.ordinal(), target.getId(), -1, -1, -1);
    }

    @Override
    public boolean unloadAll() {
        return unloadAll(false);
    }

    @Override
    public boolean unloadAll(final boolean queued) {
        return issueCommand(this.id, UnitCommandType.Unload_All.ordinal(), -1, -1, -1, queued ? 1 : 0);
    }

}
