// Original work Copyright (c) 2015, 2017, Igor Dimitrijevic
// Modified work Copyright (c) 2017-2018 OpenBW Team

//////////////////////////////////////////////////////////////////////////
//
// This file is part of the BWEM Library.
// BWEM is free software, licensed under the MIT/X11 License.
// A copy of the license is provided with the library in the LICENSE file.
// Copyright (c) 2015, 2017, Igor Dimitrijevic
//
//////////////////////////////////////////////////////////////////////////

package bwem.unit;

import bwem.map.Map;
import org.openbw.bwapi4j.unit.MineralPatch;
import org.openbw.bwapi4j.unit.Unit;

/**
 * Minerals Correspond to the units in BWAPI::getStaticNeutralUnits() for which
 * getType().isMineralField().
 */
public final class Mineral extends Resource {

  public Mineral(final Unit unit, final Map map) {
    super(unit, map);

    //        bwem_assert(Type().isMineralField());
    if (!(unit instanceof MineralPatch)) {
      throw new IllegalArgumentException(
          "Unit is not a MineralPatch: " + unit.getClass().getName());
    }
  }

  @Override
  public int getInitialAmount() {
    final MineralPatch bwapi4jMineral = (MineralPatch) super.getUnit();
    return bwapi4jMineral.getInitialResources();
  }

  @Override
  public int getAmount() {
    final MineralPatch bwapi4jMineral = (MineralPatch) super.getUnit();
    return bwapi4jMineral.getResources();
  }

  @Override
  public boolean equals(final Object object) {
    if (this == object) {
      return true;
    } else if (!(object instanceof Mineral)) {
      return false;
    } else {
      final Mineral that = (Mineral) object;
      return (this.getUnit().getId() == that.getUnit().getId());
    }
  }

  @Override
  public int hashCode() {
    return getUnit().hashCode();
  }
}
