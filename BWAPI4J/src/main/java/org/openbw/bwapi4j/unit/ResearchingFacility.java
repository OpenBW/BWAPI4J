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
import org.openbw.bwapi4j.type.UpgradeType;

public interface ResearchingFacility extends Building {
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
