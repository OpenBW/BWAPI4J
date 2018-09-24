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
import static org.openbw.bwapi4j.type.UnitType.Terran_Control_Tower;

import java.util.List;
import org.openbw.bwapi4j.Position;
import org.openbw.bwapi4j.type.UnitType;

public class Starport extends BuildingImpl
    implements Mechanical, FlyingBuilding, TrainingFacility, ExtendibleByAddon {

  protected Starport(UnitType unitType, int timeSpotted) {
    super(unitType, timeSpotted);
  }

  @Override
  public boolean cancelAddon() {
    return issueCommand(this.iD, Cancel_Addon, -1, -1, -1, -1);
  }

  /**
   * Builds a control tower addon to this starport.
   *
   * @return true if command successful, false else
   */
  public boolean buildControlTower() {
    return issueCommand(this.iD, Build_Addon, -1, -1, -1, Terran_Control_Tower.getId());
  }

  public ControlTower getControlTower() {
    return (ControlTower) addon;
  }

  @Override
  public Addon getAddon() {
    return (Addon) addon;
  }

  @Override
  public boolean build(UnitType addon) {
    return issueCommand(this.iD, Build_Addon, -1, -1, -1, addon.getId());
  }

  public boolean trainWraith() {
    return super.train(UnitType.Terran_Wraith);
  }

  public boolean trainDropship() {
    return super.train(UnitType.Terran_Dropship);
  }

  public boolean trainScienceVessel() {
    return super.train(UnitType.Terran_Science_Vessel);
  }

  public boolean trainValkyrie() {
    return super.train(UnitType.Terran_Valkyrie);
  }

  public boolean trainBattlecruiser() {
    return super.train(UnitType.Terran_Battlecruiser);
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
  public boolean isLifted() {
    return lifted;
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
    return training;
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
