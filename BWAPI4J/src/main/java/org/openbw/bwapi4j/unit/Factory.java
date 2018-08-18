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

import static org.openbw.bwapi4j.type.UnitCommandType.Build_Addon;
import static org.openbw.bwapi4j.type.UnitCommandType.Cancel_Addon;
import static org.openbw.bwapi4j.type.UnitType.Terran_Machine_Shop;

import java.util.List;
import org.openbw.bwapi4j.Position;
import org.openbw.bwapi4j.type.UnitType;

public class Factory extends BuildingImpl
    implements Mechanical, FlyingBuilding, TrainingFacility, ExtendibleByAddon {
  protected Factory(int id, int timeSpotted) {
    super(id, UnitType.Terran_Factory, timeSpotted);
  }

  public boolean trainVulture() {
    return super.train(UnitType.Terran_Vulture);
  }

  public boolean trainSiegeTank() {
    return super.train(UnitType.Terran_Siege_Tank_Tank_Mode);
  }

  public boolean trainGoliath() {
    return super.train(UnitType.Terran_Goliath);
  }

  @Override
  public boolean canTrain(UnitType type) {
    return super.canTrain(type);
  }

  @Override
  public boolean train(UnitType type) {
    return super.train(type);
  }

  @Override
  public boolean cancelAddon() {
    return issueCommand(this.id, Cancel_Addon, -1, -1, -1, -1);
  }

  /**
   * Builds a machine shop addon to this factory.
   *
   * @return true if command successful, false else
   */
  public boolean buildMachineShop() {
    return issueCommand(this.id, Build_Addon, -1, -1, -1, Terran_Machine_Shop.getId());
  }

  public MachineShop getMachineShop() {
    return (MachineShop) this.getUnit(this.addonId);
  }

  @Override
  public Addon getAddon() {
    return getMachineShop();
  }

  @Override
  public boolean build(UnitType addon) {
    return issueCommand(this.id, Build_Addon, -1, -1, -1, addon.getId());
  }

  @Override
  public boolean isLifted() {
    return isLifted;
  }

  @Override
  public boolean lift() {
    return super.lift();
  }

  @Override
  public boolean land(Position p) {
    return super.land(p);
  }

  @Override
  public boolean move(Position p) {
    return super.move(p);
  }

  @Override
  public boolean isTraining() {
    return isTraining;
  }

  @Override
  public int getTrainingQueueSize() {
    return trainingQueueSize;
  }

  @Override
  public List<TrainingSlot> getTrainingQueue() {
    return trainingQueue;
  }

  @Override
  public boolean cancelTrain(int slot) {
    return super.cancelTrain(slot);
  }

  @Override
  public boolean cancelTrain() {
    return super.cancelTrain();
  }

  @Override
  public boolean setRallyPoint(Position p) {
    return super.setRallyPoint(p);
  }

  @Override
  public boolean setRallyPoint(Unit target) {
    return super.setRallyPoint(target);
  }

  @Override
  public int getRemainingTrainTime() {
    return remainingTrainTime;
  }
}
