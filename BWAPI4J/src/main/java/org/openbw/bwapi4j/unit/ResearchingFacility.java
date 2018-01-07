package org.openbw.bwapi4j.unit;

import org.openbw.bwapi4j.type.TechType;
import org.openbw.bwapi4j.type.UpgradeType;

public interface ResearchingFacility {

    boolean isUpgrading();

    boolean isResearching();

    boolean cancelResearch();

    boolean cancelUpgrade();

    boolean canResearch(TechType techType);

    boolean canUpgrade(UpgradeType upgradeType);

    boolean research(TechType techType);

    boolean upgrade(UpgradeType upgradeType);
}
