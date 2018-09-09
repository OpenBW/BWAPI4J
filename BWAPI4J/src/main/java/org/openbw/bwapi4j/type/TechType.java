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

package org.openbw.bwapi4j.type;

import org.openbw.bwapi4j.ap.BridgeValue;
import org.openbw.bwapi4j.ap.NativeClass;

@NativeClass(name = "BWAPI::TechType", accessOperator = ".")
public enum TechType implements WithId {
  Stim_Packs,
  Lockdown,
  EMP_Shockwave,
  Spider_Mines,
  Scanner_Sweep,
  Tank_Siege_Mode,
  Defensive_Matrix,
  Irradiate,
  Yamato_Gun,
  Cloaking_Field,
  Personnel_Cloaking,
  Burrowing,
  Infestation,
  Spawn_Broodlings,
  Dark_Swarm,
  Plague,
  Consume,
  Ensnare,
  Parasite,
  Psionic_Storm,
  Hallucination,
  Recall,
  Stasis_Field,
  Archon_Warp,
  Restoration,
  Disruption_Web,
  Mind_Control,
  Dark_Archon_Meld,
  Feedback,
  Optical_Flare,
  Maelstrom,
  Lurker_Aspect,
  Healing,
  None,
  Nuclear_Strike,
  Unknown;

  int id;
  @BridgeValue
  Race race;
  @BridgeValue(accessor = "mineralPrice()")
  int mineralPrice;
  @BridgeValue(accessor = "gasPrice()")
  int gasPrice;
  @BridgeValue(accessor = "researchTime()")
  int researchTime;
  @BridgeValue(accessor = "energyCost()")
  int energyCost;
  @BridgeValue(accessor = "whatResearches()")
  UnitType whatResearches;
  @BridgeValue(accessor = "getWeapon()")
  WeaponType weaponType;
  @BridgeValue(accessor = "targetsUnit()")
  boolean targetsUnit;
  @BridgeValue(accessor = "targetsPosition()")
  boolean targetsPosition;
  @BridgeValue
  Order order;
  @BridgeValue(accessor = "requiredUnit()")
  UnitType requiredUnit;

  public static TechType withId(int id) {
    return IdMapper.techTypesForId[id];
  }

  @Override
  public int getId() {
    return this.id;
  }

  /**
   * Retrieves the race that is required to research or use the TechType. Note There is an exception
   * where Infested Kerrigan can use Psionic Storm. This does not apply to the behavior of this
   * function. Returns Race object indicating which race is designed to use this technology type.
   */
  public Race getRace() {
    return this.race;
  }

  /**
   * Retrieves the mineral cost of researching this technology. Returns Amount of minerals needed in
   * order to research this technology.
   */
  public int mineralPrice() {
    return this.mineralPrice;
  }

  /**
   * Retrieves the vespene gas cost of researching this technology. Returns Amount of vespene gas
   * needed in order to research this technology.
   */
  public int gasPrice() {
    return this.gasPrice;
  }

  /**
   * Retrieves the number of frames needed to research the tech type. Returns The time, in frames,
   * it will take for the research to complete. See also UnitInterface::getRemainingResearchTime
   */
  public int researchTime() {
    return this.researchTime;
  }

  /**
   * Retrieves the amount of energy needed to use this TechType as an ability. Returns Energy cost
   * of the ability. See also UnitInterface::getEnergy
   */
  public int energyCost() {
    return this.energyCost;
  }

  /**
   * Retrieves the UnitType that can research this technology. Returns UnitType that is able to
   * research the technology in the game. Return values UnitTypes::None If the technology/ability is
   * either provided for free or never available.
   */
  public UnitType whatResearches() {
    return this.whatResearches;
  }

  /**
   * Retrieves the Weapon that is attached to this tech type. A technology's WeaponType is used to
   * indicate the range and behaviour of the ability when used by a Unit. Returns WeaponType
   * containing information about the ability's behavior. Return values WeaponTypes::None If there
   * is no corresponding WeaponType.
   */
  public WeaponType getWeapon() {
    return this.weaponType;
  }

  /**
   * Checks if this ability can be used on other units. Returns true if the ability can be used on
   * other units, and false if it can not.
   */
  public boolean targetsUnit() {
    return this.targetsUnit;
  }

  /**
   * Checks if this ability can be used on the terrain (ground). Returns true if the ability can be
   * used on the terrain.
   */
  public boolean targetsPosition() {
    return this.targetsPosition;
  }

  /**
   * Retrieves the Order that a Unit uses when using this ability. Returns Order representing the
   * action a Unit uses to perform this ability
   */
  public Order getOrder() {
    return this.order;
  }

  /**
   * Retrieves the UnitType required to research this technology. The required unit type must be a
   * completed unit owned by the player researching the technology. Returns UnitType that is needed
   * to research this tech type. Return values UnitTypes::None if no unit is required to research
   * this tech type. See also PlayerInterface::completedUnitCount Since 4.1.2
   */
  public UnitType requiredUnit() {
    return this.requiredUnit;
  }

  private static class IdMapper {

    static final TechType[] techTypesForId = IdMapperHelper.toIdTypeArray(TechType.class);
  }
}
