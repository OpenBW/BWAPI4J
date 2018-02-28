package org.openbw.bwapi4j.unit;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openbw.bwapi4j.Position;
import org.openbw.bwapi4j.type.UnitCommandType;
import org.openbw.bwapi4j.type.UnitType;

import static org.openbw.bwapi4j.type.UnitCommandType.*;

public abstract class Transporter extends MobileUnit implements Loadable {

    private static final Logger logger = LogManager.getLogger();

    protected boolean isLoaded;

    protected Transporter(final int id, final UnitType unitType) {
        super(id, unitType);
    }

    @Override
    public void update(final int[] unitData, final int index, final int frame) {
        this.isLoaded = unitData[index + Unit.IS_LOADED_INDEX] == 1;
        super.update(unitData, index, frame);
    }

    @Override
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
            logger.error("Can't load a {} into a transport. Only non-flying units allowed.", target);
            return false;
        } else {
            return issueCommand(this.id, Load, target.getId(), -1, -1, queued ? 1 : 0);
        }
    }

    @Override
    public boolean unload(final MobileUnit target) {
        return issueCommand(this.id, Unload, target.getId(), -1, -1, -1);
    }

    @Override
    public boolean unloadAll() {
        return unloadAll(false);
    }

    @Override
    public boolean unloadAll(final boolean queued) {
        return issueCommand(this.id, Unload_All, -1, -1, -1, queued ? 1 : 0);
    }

    public boolean unloadAll(final Position p) {
        return unloadAll(p, false);
    }

    public boolean unloadAll(final Position p, final boolean queued) {
        return issueCommand(this.id, Unload_All_Position, -1, p.getX(), p.getY(), queued ? 1 : 0);
    }

}
