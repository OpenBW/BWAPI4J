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

public interface TrainingFacility extends Building {

  boolean isTraining();

  int getTrainingQueueSize();

  List<BuildingImpl.TrainingSlot> getTrainingQueue();

  int getRemainingTrainTime();

  boolean train(UnitType type);

  boolean canTrain(UnitType type);

  boolean cancelTrain(int slot);

  boolean cancelTrain();

  boolean setRallyPoint(Position p);

  boolean setRallyPoint(Unit target);
}
