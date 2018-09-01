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
import org.openbw.bwapi4j.type.UnitType;
import org.openbw.bwapi4j.type.UpgradeType;

public class Academy extends BuildingImpl implements Mechanical, ResearchingFacility {
  protected Academy(int id, int timeSpotted) {
    super(id, UnitType.Terran_Academy, timeSpotted);
  }

  public boolean researchStimPacks() {
    return super.research(TechType.Stim_Packs);
  }

  public boolean researchRestoration() {
    return super.research(TechType.Restoration);
  }

  public boolean researchOpticalFlare() {
    return super.research(TechType.Optical_Flare);
  }

  public boolean upgradeU238Shells() {
    return super.upgrade(UpgradeType.U_238_Shells);
  }

  public boolean upgradeCaduceusReactor() {
    return super.upgrade(UpgradeType.Caduceus_Reactor);
  }

  @Override
  public boolean isUpgrading() {
    return upgrading;
  }

  @Override
  public boolean isResearching() {
    return researching;
  }

  @Override
  public boolean cancelResearch() {
    return super.cancelResearch();
  }

  @Override
  public boolean cancelUpgrade() {
    return super.cancelUpgrade();
  }

  @Override
  public boolean canResearch(TechType techType) {
    return super.canResearch(techType);
  }

  @Override
  public boolean canUpgrade(UpgradeType upgradeType) {
    return super.canUpgrade(upgradeType);
  }

  @Override
  public boolean research(TechType techType) {
    return super.research(techType);
  }

  @Override
  public boolean upgrade(UpgradeType upgradeType) {
    return super.upgrade(upgradeType);
  }

  @Override
  public UpgradeInProgress getUpgradeInProgress() {
    return getUpgradeInProgress();
  }

  @Override
  public ResearchInProgress getResearchInProgress() {
    return getResearchInProgress();
  }
}
