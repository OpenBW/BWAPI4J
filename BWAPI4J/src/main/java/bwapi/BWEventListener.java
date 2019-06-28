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

package bwapi;

public interface BWEventListener {
  void onStart();

  void onEnd(boolean isWinner);

  void onFrame();

  void onSendText(String text);

  void onReceiveText(Player player, String text);

  void onPlayerLeft(Player player);

  void onNukeDetect(Position target);

  void onUnitDiscover(Unit unit);

  void onUnitEvade(Unit unit);

  void onUnitShow(Unit unit);

  void onUnitHide(Unit unit);

  void onUnitCreate(Unit unit);

  void onUnitDestroy(Unit unit);

  void onUnitMorph(Unit unit);

  void onUnitRenegade(Unit unit);

  void onSaveGame(String gameName);

  void onUnitComplete(Unit unit);
}
