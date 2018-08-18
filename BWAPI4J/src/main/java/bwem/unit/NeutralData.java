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

import java.util.List;
import org.openbw.bwapi4j.unit.Unit;

public interface NeutralData {
  List<Mineral> getMinerals();

  /**
   * If a Mineral wrappers the given BWAPI unit, returns a pointer to it. Otherwise, returns null.
   */
  Mineral getMineral(Unit u);

  // Returns a reference to the geysers (Cf. Geyser).
  List<Geyser> getGeysers();

  // If a Geyser wrappers the given BWAPI unit, returns a pointer to it.
  // Otherwise, returns nullptr.
  Geyser getGeyser(Unit g);

  // Returns a reference to the StaticBuildings (Cf. StaticBuilding).
  List<StaticBuilding> getStaticBuildings();
}
