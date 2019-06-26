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

/** Contains all damage-related bwapi functionality. */
public final class DamageEvaluator {
  DamageEvaluator() {}

  private native int getDamageFrom_native(int fromType, int toType, int fromPlayer, int toPlayer);

  public int getDamageFrom(
      final UnitType fromType,
      final UnitType toType,
      final Player fromPlayer,
      final Player toPlayer) {
    return getDamageFrom_native(
        fromType.ordinal(), toType.ordinal(), fromPlayer.getID(), toPlayer.getID());
  }

  private native int getDamageFrom_native(int fromType, int toType);

  public int getDamageFrom(final UnitType fromType, final UnitType toType) {
    return getDamageFrom_native(fromType.ordinal(), toType.ordinal());
  }

  private native int getDamageFrom_native(int fromType, int toType, int fromPlayer);

  public int getDamageFrom(
      final UnitType fromType, final UnitType toType, final Player fromPlayer) {
    return getDamageFrom_native(fromType.ordinal(), toType.ordinal(), fromPlayer.getID());
  }

  private native int getDamageTo_native(int toType, int fromType);

  public int getDamageTo(final UnitType toType, final UnitType fromType) {
    return getDamageTo_native(toType.ordinal(), fromType.ordinal());
  }

  private native int getDamageTo_native(int toType, int fromType, int toPlayer);

  public int getDamageTo(final UnitType toType, final UnitType fromType, final Player toPlayer) {
    return getDamageTo_native(toType.ordinal(), fromType.ordinal(), toPlayer.getID());
  }

  private native int getDamageTo_native(int toType, int fromType, int toPlayer, int fromPlayer);

  public int getDamageTo(
      final UnitType toType,
      final UnitType fromType,
      final Player toPlayer,
      final Player fromPlayer) {
    return getDamageTo_native(
        toType.ordinal(), fromType.ordinal(), toPlayer.getID(), fromPlayer.getID());
  }
}
