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

package org.openbw.bwapi4j;

import bwapi.Position;
import org.openbw.bwapi4j.unit.Unit;

public interface BWEventListener {
  public void onStart();

  public void onEnd(boolean isWinner);

  public void onFrame();

  public void onSendText(String text);

  public void onReceiveText(Player player, String text);

  public void onPlayerLeft(Player player);

  public void onNukeDetect(Position target);

  public void onUnitDiscover(Unit unit);

  public void onUnitEvade(Unit unit);

  public void onUnitShow(Unit unit);

  public void onUnitHide(Unit unit);

  public void onUnitCreate(Unit unit);

  public void onUnitDestroy(Unit unit);

  public void onUnitMorph(Unit unit);

  public void onUnitRenegade(Unit unit);

  public void onSaveGame(String gameName);

  public void onUnitComplete(Unit unit);
}
