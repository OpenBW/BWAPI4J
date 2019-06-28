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

public enum BulletType implements WithId {
  Melee(0),
  Fusion_Cutter_Hit(141),
  Gauss_Rifle_Hit(142),
  C_10_Canister_Rifle_Hit(143),
  Gemini_Missiles(144),
  Fragmentation_Grenade(145),
  Longbolt_Missile(146),
  Unused_Lockdown(147),
  ATS_ATA_Laser_Battery(148),
  Burst_Lasers(149),
  Arclite_Shock_Cannon_Hit(150),
  EMP_Missile(151),
  Dual_Photon_Blasters_Hit(152),
  Particle_Beam_Hit(153),
  Anti_Matter_Missile(154),
  Pulse_Cannon(155),
  Psionic_Shockwave_Hit(156),
  Psionic_Storm(157),
  Yamato_Gun(158),
  Phase_Disruptor(159),
  STA_STS_Cannon_Overlay(160),
  Sunken_Colony_Tentacle(161),
  Venom_Unused(162),
  Acid_Spore(163),
  Plasma_Drip_Unused(164),
  Glave_Wurm(165),
  Seeker_Spores(166),
  Queen_Spell_Carrier(167),
  Plague_Cloud(168),
  Consume(169),
  Ensnare(170),
  Needle_Spine_Hit(171),
  Invisible(172),
  Optical_Flare_Grenade(201),
  Halo_Rockets(202),
  Subterranean_Spines(203),
  Corrosive_Acid_Shot(204),
  Corrosive_Acid_Hit(205),
  Neutron_Flare(206),
  None(209),
  Unknown(210),
  MAX(211);

  private final int typeId;

  BulletType(final int typeId) {
    this.typeId = typeId;
  }

  @Override
  public int getID() {
    return typeId;
  }

  public static BulletType withId(int id) {
    return IdMapper.bulletTypeForId[id];
  }

  private static class IdMapper {

    static final BulletType[] bulletTypeForId = IdMapperHelper.toIdTypeArray(BulletType.class);
  }
}
