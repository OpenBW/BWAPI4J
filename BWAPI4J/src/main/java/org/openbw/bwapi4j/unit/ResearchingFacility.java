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

    UpgradeInProgress getUpgradeInProgress();

    ResearchInProgress getResearchInProgress();

    class UpgradeInProgress {
        public static final UpgradeInProgress NONE = new UpgradeInProgress(UpgradeType.None, 0);
        private final UpgradeType upgradeType;
        private final int remainingUpgradeTime;

        protected UpgradeInProgress(UpgradeType upgradeType, int remainingUpgradeTime) {
            this.upgradeType = upgradeType;
            this.remainingUpgradeTime = remainingUpgradeTime;
        }

        public UpgradeType getUpgradeType() {
            return upgradeType;
        }

        public int getRemainingUpgradeTime() {
            return remainingUpgradeTime;
        }
    }

    class ResearchInProgress {
        public static final ResearchInProgress NONE = new ResearchInProgress(TechType.None, 0);
        private final TechType researchType;
        private final int remainingResearchTime;

        protected ResearchInProgress(TechType researchType, int remainingResearchTime) {
            this.researchType = researchType;
            this.remainingResearchTime = remainingResearchTime;
        }

        public int getRemainingResearchTime() {
            return remainingResearchTime;
        }

        public TechType getResearchType() {
            return researchType;
        }
    }
}
