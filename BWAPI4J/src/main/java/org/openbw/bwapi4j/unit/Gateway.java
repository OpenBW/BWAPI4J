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

import java.util.List;
import org.openbw.bwapi4j.Position;
import org.openbw.bwapi4j.type.UnitType;

public class Gateway extends BuildingImpl implements Mechanical, TrainingFacility {
  protected Gateway(int id, int timeSpotted) {
    super(id, UnitType.Protoss_Gateway, timeSpotted);
  }

  public boolean trainZealot() {
    return super.train(UnitType.Protoss_Zealot);
  }

  public boolean trainDragoon() {
    return super.train(UnitType.Protoss_Dragoon);
  }

  public boolean trainHighTemplar() {
    return super.train(UnitType.Protoss_High_Templar);
  }

  public boolean trainDarkTemplar() {
    return super.train(UnitType.Protoss_Dark_Templar);
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
