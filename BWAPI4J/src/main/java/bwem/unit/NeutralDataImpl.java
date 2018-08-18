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
import java.util.ArrayList;
import java.util.List;
import org.openbw.bwapi4j.unit.Building;
import org.openbw.bwapi4j.unit.MineralPatch;
import org.openbw.bwapi4j.unit.PlayerUnit;
import org.openbw.bwapi4j.unit.Unit;
import org.openbw.bwapi4j.unit.VespeneGeyser;

public class NeutralDataImpl implements NeutralData {
  private final List<Mineral> minerals;
  private final List<Geyser> geysers;
  private final List<StaticBuilding> staticBuildings;

  public NeutralDataImpl(
      final Map map,
      final List<MineralPatch> mineralPatches,
      final List<VespeneGeyser> vespeneGeysers,
      final List<PlayerUnit> neutralUnits) {
    ////////////////////////////////////////////////////////////////////////
    // MapImpl::InitializeNeutrals
    ////////////////////////////////////////////////////////////////////////

    this.minerals = new ArrayList<>();
    for (final MineralPatch mineralPatch : mineralPatches) {
      this.minerals.add(new Mineral(mineralPatch, map));
    }

    this.geysers = new ArrayList<>();
    for (final VespeneGeyser vespeneGeyser : vespeneGeysers) {
      this.geysers.add(new Geyser(vespeneGeyser, map));
    }

    this.staticBuildings = new ArrayList<>();
    for (final Unit neutralUnit : neutralUnits) {
      if (neutralUnit instanceof Building) {
        this.staticBuildings.add(new StaticBuilding(neutralUnit, map));
      }
    }

    // TODO: Add "Special_Pit_Door" and "Special_Right_Pit_Door" to static buildings list? See
    // mapImpl.cpp:238.
    //				if (n->getType() == Special_Pit_Door)
    //					m_StaticBuildings.push_back(make_unique<StaticBuilding>(n, this));
    //				if (n->getType() == Special_Right_Pit_Door)
    //					m_StaticBuildings.push_back(make_unique<StaticBuilding>(n, this));

    ////////////////////////////////////////////////////////////////////////
  }

  @Override
  public List<Mineral> getMinerals() {
    return this.minerals;
  }

  @Override
  public Mineral getMineral(final Unit unit) {
    for (final Mineral mineral : getMinerals()) {
      if (mineral.getUnit().equals(unit)) {
        return mineral;
      }
    }
    return null;
  }

  @Override
  public List<Geyser> getGeysers() {
    return this.geysers;
  }

  @Override
  public Geyser getGeyser(final Unit unit) {
    for (final Geyser geyser : getGeysers()) {
      if (geyser.getUnit().equals(unit)) {
        return geyser;
      }
    }
    return null;
  }

  @Override
  public List<StaticBuilding> getStaticBuildings() {
    return this.staticBuildings;
  }
}
