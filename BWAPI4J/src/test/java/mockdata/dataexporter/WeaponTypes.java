package mockdata.dataexporter;

import bwapi.*;
import java.lang.reflect.*;
import java.util.*;
import java.util.stream.*;
import org.openbw.bwapi4j.util.*;

class WeaponTypes {
  static void initializeWeaponType() throws Exception {
    initializeWeaponType_Gauss_Rifle();
    initializeWeaponType_Gauss_Rifle_Jim_Raynor();
    initializeWeaponType_C_10_Canister_Rifle();
    initializeWeaponType_C_10_Canister_Rifle_Sarah_Kerrigan();
    initializeWeaponType_Fragmentation_Grenade();
    initializeWeaponType_Fragmentation_Grenade_Jim_Raynor();
    initializeWeaponType_Spider_Mines();
    initializeWeaponType_Twin_Autocannons();
    initializeWeaponType_Hellfire_Missile_Pack();
    initializeWeaponType_Twin_Autocannons_Alan_Schezar();
    initializeWeaponType_Hellfire_Missile_Pack_Alan_Schezar();
    initializeWeaponType_Arclite_Cannon();
    initializeWeaponType_Arclite_Cannon_Edmund_Duke();
    initializeWeaponType_Fusion_Cutter();
    initializeWeaponType_unk_14();
    initializeWeaponType_Gemini_Missiles();
    initializeWeaponType_Burst_Lasers();
    initializeWeaponType_Gemini_Missiles_Tom_Kazansky();
    initializeWeaponType_Burst_Lasers_Tom_Kazansky();
    initializeWeaponType_ATS_Laser_Battery();
    initializeWeaponType_ATA_Laser_Battery();
    initializeWeaponType_ATS_Laser_Battery_Hero();
    initializeWeaponType_ATA_Laser_Battery_Hero();
    initializeWeaponType_ATS_Laser_Battery_Hyperion();
    initializeWeaponType_ATA_Laser_Battery_Hyperion();
    initializeWeaponType_Flame_Thrower();
    initializeWeaponType_Flame_Thrower_Gui_Montag();
    initializeWeaponType_Arclite_Shock_Cannon();
    initializeWeaponType_Arclite_Shock_Cannon_Edmund_Duke();
    initializeWeaponType_Longbolt_Missile();
    initializeWeaponType_Yamato_Gun();
    initializeWeaponType_Nuclear_Strike();
    initializeWeaponType_Lockdown();
    initializeWeaponType_EMP_Shockwave();
    initializeWeaponType_Irradiate();
    initializeWeaponType_Claws();
    initializeWeaponType_Claws_Devouring_One();
    initializeWeaponType_Claws_Infested_Kerrigan();
    initializeWeaponType_Needle_Spines();
    initializeWeaponType_Needle_Spines_Hunter_Killer();
    initializeWeaponType_Kaiser_Blades();
    initializeWeaponType_Kaiser_Blades_Torrasque();
    initializeWeaponType_Toxic_Spores();
    initializeWeaponType_Spines();
    initializeWeaponType_unk_44();
    initializeWeaponType_unk_45();
    initializeWeaponType_Acid_Spore();
    initializeWeaponType_Acid_Spore_Kukulza();
    initializeWeaponType_Glave_Wurm();
    initializeWeaponType_Glave_Wurm_Kukulza();
    initializeWeaponType_unk_50();
    initializeWeaponType_unk_51();
    initializeWeaponType_Seeker_Spores();
    initializeWeaponType_Subterranean_Tentacle();
    initializeWeaponType_Suicide_Infested_Terran();
    initializeWeaponType_Suicide_Scourge();
    initializeWeaponType_Parasite();
    initializeWeaponType_Spawn_Broodlings();
    initializeWeaponType_Ensnare();
    initializeWeaponType_Dark_Swarm();
    initializeWeaponType_Plague();
    initializeWeaponType_Consume();
    initializeWeaponType_Particle_Beam();
    initializeWeaponType_unk_63();
    initializeWeaponType_Psi_Blades();
    initializeWeaponType_Psi_Blades_Fenix();
    initializeWeaponType_Phase_Disruptor();
    initializeWeaponType_Phase_Disruptor_Fenix();
    initializeWeaponType_unk_68();
    initializeWeaponType_Psi_Assault();
    initializeWeaponType_Psionic_Shockwave();
    initializeWeaponType_Psionic_Shockwave_TZ_Archon();
    initializeWeaponType_unk_72();
    initializeWeaponType_Dual_Photon_Blasters();
    initializeWeaponType_Anti_Matter_Missiles();
    initializeWeaponType_Dual_Photon_Blasters_Mojo();
    initializeWeaponType_Anti_Matter_Missiles_Mojo();
    initializeWeaponType_Phase_Disruptor_Cannon();
    initializeWeaponType_Phase_Disruptor_Cannon_Danimoth();
    initializeWeaponType_Pulse_Cannon();
    initializeWeaponType_STS_Photon_Cannon();
    initializeWeaponType_STA_Photon_Cannon();
    initializeWeaponType_Scarab();
    initializeWeaponType_Stasis_Field();
    initializeWeaponType_Psionic_Storm();
    initializeWeaponType_Warp_Blades_Zeratul();
    initializeWeaponType_Warp_Blades_Hero();
    initializeWeaponType_unk_87();
    initializeWeaponType_unk_88();
    initializeWeaponType_unk_89();
    initializeWeaponType_unk_90();
    initializeWeaponType_unk_91();
    initializeWeaponType_Platform_Laser_Battery();
    initializeWeaponType_Independant_Laser_Battery();
    initializeWeaponType_unk_94();
    initializeWeaponType_unk_95();
    initializeWeaponType_Twin_Autocannons_Floor_Trap();
    initializeWeaponType_Hellfire_Missile_Pack_Wall_Trap();
    initializeWeaponType_Flame_Thrower_Wall_Trap();
    initializeWeaponType_Hellfire_Missile_Pack_Floor_Trap();
    initializeWeaponType_Neutron_Flare();
    initializeWeaponType_Disruption_Web();
    initializeWeaponType_Restoration();
    initializeWeaponType_Halo_Rockets();
    initializeWeaponType_Corrosive_Acid();
    initializeWeaponType_Mind_Control();
    initializeWeaponType_Feedback();
    initializeWeaponType_Optical_Flare();
    initializeWeaponType_Maelstrom();
    initializeWeaponType_Subterranean_Spines();
    initializeWeaponType_Warp_Blades();
    initializeWeaponType_C_10_Canister_Rifle_Samir_Duran();
    initializeWeaponType_C_10_Canister_Rifle_Infested_Duran();
    initializeWeaponType_Dual_Photon_Blasters_Artanis();
    initializeWeaponType_Anti_Matter_Missiles_Artanis();
    initializeWeaponType_C_10_Canister_Rifle_Alexei_Stukov();
    initializeWeaponType_None();
    initializeWeaponType_Unknown();
    initializeWeaponType_MAX();
  }

  private static void initializeWeaponType_Gauss_Rifle() throws Exception {
    Class<?> c = WeaponType.class;
    Map<String, Field> fields =
        Stream.of(c.getDeclaredFields())
            .collect(
                Collectors.toMap(
                    f -> f.getName(),
                    f -> {
                      f.setAccessible(true);
                      return f;
                    }));
    fields.get("iD").set(WeaponType.Gauss_Rifle, 0);
    fields.get("tech").set(WeaponType.Gauss_Rifle, TechType.None);
    fields.get("whatUses").set(WeaponType.Gauss_Rifle, UnitType.Terran_Marine);
    fields.get("damageAmount").set(WeaponType.Gauss_Rifle, 6);
    fields.get("damageBonus").set(WeaponType.Gauss_Rifle, 1);
    fields.get("damageCooldown").set(WeaponType.Gauss_Rifle, 15);
    fields.get("damageFactor").set(WeaponType.Gauss_Rifle, 1);
    fields.get("upgradeType").set(WeaponType.Gauss_Rifle, UpgradeType.Terran_Infantry_Weapons);
    fields.get("damageType").set(WeaponType.Gauss_Rifle, DamageType.Normal);
    fields.get("explosionType").set(WeaponType.Gauss_Rifle, ExplosionType.Normal);
    fields.get("minRange").set(WeaponType.Gauss_Rifle, 0);
    fields.get("maxRange").set(WeaponType.Gauss_Rifle, 128);
    fields.get("innerSplashRadius").set(WeaponType.Gauss_Rifle, 0);
    fields.get("medianSplashRadius").set(WeaponType.Gauss_Rifle, 0);
    fields.get("outerSplashRadius").set(WeaponType.Gauss_Rifle, 0);
    fields.get("targetsAir").set(WeaponType.Gauss_Rifle, true);
    fields.get("targetsGround").set(WeaponType.Gauss_Rifle, true);
    fields.get("targetsMechanical").set(WeaponType.Gauss_Rifle, false);
    fields.get("targetsOrganic").set(WeaponType.Gauss_Rifle, false);
    fields.get("targetsNonBuilding").set(WeaponType.Gauss_Rifle, false);
    fields.get("targetsNonRobotic").set(WeaponType.Gauss_Rifle, false);
    fields.get("targetsTerrain").set(WeaponType.Gauss_Rifle, false);
    fields.get("targetsOrgOrMech").set(WeaponType.Gauss_Rifle, false);
    fields.get("targetsOwn").set(WeaponType.Gauss_Rifle, false);
  }

  private static void initializeWeaponType_Gauss_Rifle_Jim_Raynor() throws Exception {
    Class<?> c = WeaponType.class;
    Map<String, Field> fields =
        Stream.of(c.getDeclaredFields())
            .collect(
                Collectors.toMap(
                    f -> f.getName(),
                    f -> {
                      f.setAccessible(true);
                      return f;
                    }));
    fields.get("iD").set(WeaponType.Gauss_Rifle_Jim_Raynor, 1);
    fields.get("tech").set(WeaponType.Gauss_Rifle_Jim_Raynor, TechType.None);
    fields.get("whatUses").set(WeaponType.Gauss_Rifle_Jim_Raynor, UnitType.Hero_Jim_Raynor_Marine);
    fields.get("damageAmount").set(WeaponType.Gauss_Rifle_Jim_Raynor, 18);
    fields.get("damageBonus").set(WeaponType.Gauss_Rifle_Jim_Raynor, 1);
    fields.get("damageCooldown").set(WeaponType.Gauss_Rifle_Jim_Raynor, 15);
    fields.get("damageFactor").set(WeaponType.Gauss_Rifle_Jim_Raynor, 1);
    fields
        .get("upgradeType")
        .set(WeaponType.Gauss_Rifle_Jim_Raynor, UpgradeType.Terran_Infantry_Weapons);
    fields.get("damageType").set(WeaponType.Gauss_Rifle_Jim_Raynor, DamageType.Normal);
    fields.get("explosionType").set(WeaponType.Gauss_Rifle_Jim_Raynor, ExplosionType.Normal);
    fields.get("minRange").set(WeaponType.Gauss_Rifle_Jim_Raynor, 0);
    fields.get("maxRange").set(WeaponType.Gauss_Rifle_Jim_Raynor, 160);
    fields.get("innerSplashRadius").set(WeaponType.Gauss_Rifle_Jim_Raynor, 0);
    fields.get("medianSplashRadius").set(WeaponType.Gauss_Rifle_Jim_Raynor, 0);
    fields.get("outerSplashRadius").set(WeaponType.Gauss_Rifle_Jim_Raynor, 0);
    fields.get("targetsAir").set(WeaponType.Gauss_Rifle_Jim_Raynor, true);
    fields.get("targetsGround").set(WeaponType.Gauss_Rifle_Jim_Raynor, true);
    fields.get("targetsMechanical").set(WeaponType.Gauss_Rifle_Jim_Raynor, false);
    fields.get("targetsOrganic").set(WeaponType.Gauss_Rifle_Jim_Raynor, false);
    fields.get("targetsNonBuilding").set(WeaponType.Gauss_Rifle_Jim_Raynor, false);
    fields.get("targetsNonRobotic").set(WeaponType.Gauss_Rifle_Jim_Raynor, false);
    fields.get("targetsTerrain").set(WeaponType.Gauss_Rifle_Jim_Raynor, false);
    fields.get("targetsOrgOrMech").set(WeaponType.Gauss_Rifle_Jim_Raynor, false);
    fields.get("targetsOwn").set(WeaponType.Gauss_Rifle_Jim_Raynor, false);
  }

  private static void initializeWeaponType_C_10_Canister_Rifle() throws Exception {
    Class<?> c = WeaponType.class;
    Map<String, Field> fields =
        Stream.of(c.getDeclaredFields())
            .collect(
                Collectors.toMap(
                    f -> f.getName(),
                    f -> {
                      f.setAccessible(true);
                      return f;
                    }));
    fields.get("iD").set(WeaponType.C_10_Canister_Rifle, 2);
    fields.get("tech").set(WeaponType.C_10_Canister_Rifle, TechType.None);
    fields.get("whatUses").set(WeaponType.C_10_Canister_Rifle, UnitType.Terran_Ghost);
    fields.get("damageAmount").set(WeaponType.C_10_Canister_Rifle, 10);
    fields.get("damageBonus").set(WeaponType.C_10_Canister_Rifle, 1);
    fields.get("damageCooldown").set(WeaponType.C_10_Canister_Rifle, 22);
    fields.get("damageFactor").set(WeaponType.C_10_Canister_Rifle, 1);
    fields
        .get("upgradeType")
        .set(WeaponType.C_10_Canister_Rifle, UpgradeType.Terran_Infantry_Weapons);
    fields.get("damageType").set(WeaponType.C_10_Canister_Rifle, DamageType.Concussive);
    fields.get("explosionType").set(WeaponType.C_10_Canister_Rifle, ExplosionType.Normal);
    fields.get("minRange").set(WeaponType.C_10_Canister_Rifle, 0);
    fields.get("maxRange").set(WeaponType.C_10_Canister_Rifle, 224);
    fields.get("innerSplashRadius").set(WeaponType.C_10_Canister_Rifle, 0);
    fields.get("medianSplashRadius").set(WeaponType.C_10_Canister_Rifle, 0);
    fields.get("outerSplashRadius").set(WeaponType.C_10_Canister_Rifle, 0);
    fields.get("targetsAir").set(WeaponType.C_10_Canister_Rifle, true);
    fields.get("targetsGround").set(WeaponType.C_10_Canister_Rifle, true);
    fields.get("targetsMechanical").set(WeaponType.C_10_Canister_Rifle, false);
    fields.get("targetsOrganic").set(WeaponType.C_10_Canister_Rifle, false);
    fields.get("targetsNonBuilding").set(WeaponType.C_10_Canister_Rifle, false);
    fields.get("targetsNonRobotic").set(WeaponType.C_10_Canister_Rifle, false);
    fields.get("targetsTerrain").set(WeaponType.C_10_Canister_Rifle, false);
    fields.get("targetsOrgOrMech").set(WeaponType.C_10_Canister_Rifle, false);
    fields.get("targetsOwn").set(WeaponType.C_10_Canister_Rifle, false);
  }

  private static void initializeWeaponType_C_10_Canister_Rifle_Sarah_Kerrigan() throws Exception {
    Class<?> c = WeaponType.class;
    Map<String, Field> fields =
        Stream.of(c.getDeclaredFields())
            .collect(
                Collectors.toMap(
                    f -> f.getName(),
                    f -> {
                      f.setAccessible(true);
                      return f;
                    }));
    fields.get("iD").set(WeaponType.C_10_Canister_Rifle_Sarah_Kerrigan, 3);
    fields.get("tech").set(WeaponType.C_10_Canister_Rifle_Sarah_Kerrigan, TechType.None);
    fields
        .get("whatUses")
        .set(WeaponType.C_10_Canister_Rifle_Sarah_Kerrigan, UnitType.Hero_Sarah_Kerrigan);
    fields.get("damageAmount").set(WeaponType.C_10_Canister_Rifle_Sarah_Kerrigan, 30);
    fields.get("damageBonus").set(WeaponType.C_10_Canister_Rifle_Sarah_Kerrigan, 1);
    fields.get("damageCooldown").set(WeaponType.C_10_Canister_Rifle_Sarah_Kerrigan, 22);
    fields.get("damageFactor").set(WeaponType.C_10_Canister_Rifle_Sarah_Kerrigan, 1);
    fields
        .get("upgradeType")
        .set(WeaponType.C_10_Canister_Rifle_Sarah_Kerrigan, UpgradeType.Terran_Infantry_Weapons);
    fields
        .get("damageType")
        .set(WeaponType.C_10_Canister_Rifle_Sarah_Kerrigan, DamageType.Concussive);
    fields
        .get("explosionType")
        .set(WeaponType.C_10_Canister_Rifle_Sarah_Kerrigan, ExplosionType.Normal);
    fields.get("minRange").set(WeaponType.C_10_Canister_Rifle_Sarah_Kerrigan, 0);
    fields.get("maxRange").set(WeaponType.C_10_Canister_Rifle_Sarah_Kerrigan, 192);
    fields.get("innerSplashRadius").set(WeaponType.C_10_Canister_Rifle_Sarah_Kerrigan, 0);
    fields.get("medianSplashRadius").set(WeaponType.C_10_Canister_Rifle_Sarah_Kerrigan, 0);
    fields.get("outerSplashRadius").set(WeaponType.C_10_Canister_Rifle_Sarah_Kerrigan, 0);
    fields.get("targetsAir").set(WeaponType.C_10_Canister_Rifle_Sarah_Kerrigan, true);
    fields.get("targetsGround").set(WeaponType.C_10_Canister_Rifle_Sarah_Kerrigan, true);
    fields.get("targetsMechanical").set(WeaponType.C_10_Canister_Rifle_Sarah_Kerrigan, false);
    fields.get("targetsOrganic").set(WeaponType.C_10_Canister_Rifle_Sarah_Kerrigan, false);
    fields.get("targetsNonBuilding").set(WeaponType.C_10_Canister_Rifle_Sarah_Kerrigan, false);
    fields.get("targetsNonRobotic").set(WeaponType.C_10_Canister_Rifle_Sarah_Kerrigan, false);
    fields.get("targetsTerrain").set(WeaponType.C_10_Canister_Rifle_Sarah_Kerrigan, false);
    fields.get("targetsOrgOrMech").set(WeaponType.C_10_Canister_Rifle_Sarah_Kerrigan, false);
    fields.get("targetsOwn").set(WeaponType.C_10_Canister_Rifle_Sarah_Kerrigan, false);
  }

  private static void initializeWeaponType_Fragmentation_Grenade() throws Exception {
    Class<?> c = WeaponType.class;
    Map<String, Field> fields =
        Stream.of(c.getDeclaredFields())
            .collect(
                Collectors.toMap(
                    f -> f.getName(),
                    f -> {
                      f.setAccessible(true);
                      return f;
                    }));
    fields.get("iD").set(WeaponType.Fragmentation_Grenade, 4);
    fields.get("tech").set(WeaponType.Fragmentation_Grenade, TechType.None);
    fields.get("whatUses").set(WeaponType.Fragmentation_Grenade, UnitType.Terran_Vulture);
    fields.get("damageAmount").set(WeaponType.Fragmentation_Grenade, 20);
    fields.get("damageBonus").set(WeaponType.Fragmentation_Grenade, 2);
    fields.get("damageCooldown").set(WeaponType.Fragmentation_Grenade, 30);
    fields.get("damageFactor").set(WeaponType.Fragmentation_Grenade, 1);
    fields
        .get("upgradeType")
        .set(WeaponType.Fragmentation_Grenade, UpgradeType.Terran_Vehicle_Weapons);
    fields.get("damageType").set(WeaponType.Fragmentation_Grenade, DamageType.Concussive);
    fields.get("explosionType").set(WeaponType.Fragmentation_Grenade, ExplosionType.Normal);
    fields.get("minRange").set(WeaponType.Fragmentation_Grenade, 0);
    fields.get("maxRange").set(WeaponType.Fragmentation_Grenade, 160);
    fields.get("innerSplashRadius").set(WeaponType.Fragmentation_Grenade, 0);
    fields.get("medianSplashRadius").set(WeaponType.Fragmentation_Grenade, 0);
    fields.get("outerSplashRadius").set(WeaponType.Fragmentation_Grenade, 0);
    fields.get("targetsAir").set(WeaponType.Fragmentation_Grenade, false);
    fields.get("targetsGround").set(WeaponType.Fragmentation_Grenade, true);
    fields.get("targetsMechanical").set(WeaponType.Fragmentation_Grenade, false);
    fields.get("targetsOrganic").set(WeaponType.Fragmentation_Grenade, false);
    fields.get("targetsNonBuilding").set(WeaponType.Fragmentation_Grenade, false);
    fields.get("targetsNonRobotic").set(WeaponType.Fragmentation_Grenade, false);
    fields.get("targetsTerrain").set(WeaponType.Fragmentation_Grenade, false);
    fields.get("targetsOrgOrMech").set(WeaponType.Fragmentation_Grenade, false);
    fields.get("targetsOwn").set(WeaponType.Fragmentation_Grenade, false);
  }

  private static void initializeWeaponType_Fragmentation_Grenade_Jim_Raynor() throws Exception {
    Class<?> c = WeaponType.class;
    Map<String, Field> fields =
        Stream.of(c.getDeclaredFields())
            .collect(
                Collectors.toMap(
                    f -> f.getName(),
                    f -> {
                      f.setAccessible(true);
                      return f;
                    }));
    fields.get("iD").set(WeaponType.Fragmentation_Grenade_Jim_Raynor, 5);
    fields.get("tech").set(WeaponType.Fragmentation_Grenade_Jim_Raynor, TechType.None);
    fields
        .get("whatUses")
        .set(WeaponType.Fragmentation_Grenade_Jim_Raynor, UnitType.Hero_Jim_Raynor_Vulture);
    fields.get("damageAmount").set(WeaponType.Fragmentation_Grenade_Jim_Raynor, 30);
    fields.get("damageBonus").set(WeaponType.Fragmentation_Grenade_Jim_Raynor, 2);
    fields.get("damageCooldown").set(WeaponType.Fragmentation_Grenade_Jim_Raynor, 22);
    fields.get("damageFactor").set(WeaponType.Fragmentation_Grenade_Jim_Raynor, 1);
    fields
        .get("upgradeType")
        .set(WeaponType.Fragmentation_Grenade_Jim_Raynor, UpgradeType.Terran_Vehicle_Weapons);
    fields
        .get("damageType")
        .set(WeaponType.Fragmentation_Grenade_Jim_Raynor, DamageType.Concussive);
    fields
        .get("explosionType")
        .set(WeaponType.Fragmentation_Grenade_Jim_Raynor, ExplosionType.Normal);
    fields.get("minRange").set(WeaponType.Fragmentation_Grenade_Jim_Raynor, 0);
    fields.get("maxRange").set(WeaponType.Fragmentation_Grenade_Jim_Raynor, 160);
    fields.get("innerSplashRadius").set(WeaponType.Fragmentation_Grenade_Jim_Raynor, 0);
    fields.get("medianSplashRadius").set(WeaponType.Fragmentation_Grenade_Jim_Raynor, 0);
    fields.get("outerSplashRadius").set(WeaponType.Fragmentation_Grenade_Jim_Raynor, 0);
    fields.get("targetsAir").set(WeaponType.Fragmentation_Grenade_Jim_Raynor, false);
    fields.get("targetsGround").set(WeaponType.Fragmentation_Grenade_Jim_Raynor, true);
    fields.get("targetsMechanical").set(WeaponType.Fragmentation_Grenade_Jim_Raynor, false);
    fields.get("targetsOrganic").set(WeaponType.Fragmentation_Grenade_Jim_Raynor, false);
    fields.get("targetsNonBuilding").set(WeaponType.Fragmentation_Grenade_Jim_Raynor, false);
    fields.get("targetsNonRobotic").set(WeaponType.Fragmentation_Grenade_Jim_Raynor, false);
    fields.get("targetsTerrain").set(WeaponType.Fragmentation_Grenade_Jim_Raynor, false);
    fields.get("targetsOrgOrMech").set(WeaponType.Fragmentation_Grenade_Jim_Raynor, false);
    fields.get("targetsOwn").set(WeaponType.Fragmentation_Grenade_Jim_Raynor, false);
  }

  private static void initializeWeaponType_Spider_Mines() throws Exception {
    Class<?> c = WeaponType.class;
    Map<String, Field> fields =
        Stream.of(c.getDeclaredFields())
            .collect(
                Collectors.toMap(
                    f -> f.getName(),
                    f -> {
                      f.setAccessible(true);
                      return f;
                    }));
    fields.get("iD").set(WeaponType.Spider_Mines, 6);
    fields.get("tech").set(WeaponType.Spider_Mines, TechType.Spider_Mines);
    fields.get("whatUses").set(WeaponType.Spider_Mines, UnitType.Terran_Vulture_Spider_Mine);
    fields.get("damageAmount").set(WeaponType.Spider_Mines, 125);
    fields.get("damageBonus").set(WeaponType.Spider_Mines, 0);
    fields.get("damageCooldown").set(WeaponType.Spider_Mines, 22);
    fields.get("damageFactor").set(WeaponType.Spider_Mines, 1);
    fields.get("upgradeType").set(WeaponType.Spider_Mines, UpgradeType.Upgrade_60);
    fields.get("damageType").set(WeaponType.Spider_Mines, DamageType.Explosive);
    fields.get("explosionType").set(WeaponType.Spider_Mines, ExplosionType.Radial_Splash);
    fields.get("minRange").set(WeaponType.Spider_Mines, 0);
    fields.get("maxRange").set(WeaponType.Spider_Mines, 10);
    fields.get("innerSplashRadius").set(WeaponType.Spider_Mines, 50);
    fields.get("medianSplashRadius").set(WeaponType.Spider_Mines, 75);
    fields.get("outerSplashRadius").set(WeaponType.Spider_Mines, 100);
    fields.get("targetsAir").set(WeaponType.Spider_Mines, false);
    fields.get("targetsGround").set(WeaponType.Spider_Mines, true);
    fields.get("targetsMechanical").set(WeaponType.Spider_Mines, false);
    fields.get("targetsOrganic").set(WeaponType.Spider_Mines, false);
    fields.get("targetsNonBuilding").set(WeaponType.Spider_Mines, true);
    fields.get("targetsNonRobotic").set(WeaponType.Spider_Mines, false);
    fields.get("targetsTerrain").set(WeaponType.Spider_Mines, false);
    fields.get("targetsOrgOrMech").set(WeaponType.Spider_Mines, false);
    fields.get("targetsOwn").set(WeaponType.Spider_Mines, false);
  }

  private static void initializeWeaponType_Twin_Autocannons() throws Exception {
    Class<?> c = WeaponType.class;
    Map<String, Field> fields =
        Stream.of(c.getDeclaredFields())
            .collect(
                Collectors.toMap(
                    f -> f.getName(),
                    f -> {
                      f.setAccessible(true);
                      return f;
                    }));
    fields.get("iD").set(WeaponType.Twin_Autocannons, 7);
    fields.get("tech").set(WeaponType.Twin_Autocannons, TechType.None);
    fields.get("whatUses").set(WeaponType.Twin_Autocannons, UnitType.Terran_Goliath);
    fields.get("damageAmount").set(WeaponType.Twin_Autocannons, 12);
    fields.get("damageBonus").set(WeaponType.Twin_Autocannons, 1);
    fields.get("damageCooldown").set(WeaponType.Twin_Autocannons, 22);
    fields.get("damageFactor").set(WeaponType.Twin_Autocannons, 1);
    fields.get("upgradeType").set(WeaponType.Twin_Autocannons, UpgradeType.Terran_Vehicle_Weapons);
    fields.get("damageType").set(WeaponType.Twin_Autocannons, DamageType.Normal);
    fields.get("explosionType").set(WeaponType.Twin_Autocannons, ExplosionType.Normal);
    fields.get("minRange").set(WeaponType.Twin_Autocannons, 0);
    fields.get("maxRange").set(WeaponType.Twin_Autocannons, 192);
    fields.get("innerSplashRadius").set(WeaponType.Twin_Autocannons, 0);
    fields.get("medianSplashRadius").set(WeaponType.Twin_Autocannons, 0);
    fields.get("outerSplashRadius").set(WeaponType.Twin_Autocannons, 0);
    fields.get("targetsAir").set(WeaponType.Twin_Autocannons, false);
    fields.get("targetsGround").set(WeaponType.Twin_Autocannons, true);
    fields.get("targetsMechanical").set(WeaponType.Twin_Autocannons, false);
    fields.get("targetsOrganic").set(WeaponType.Twin_Autocannons, false);
    fields.get("targetsNonBuilding").set(WeaponType.Twin_Autocannons, false);
    fields.get("targetsNonRobotic").set(WeaponType.Twin_Autocannons, false);
    fields.get("targetsTerrain").set(WeaponType.Twin_Autocannons, false);
    fields.get("targetsOrgOrMech").set(WeaponType.Twin_Autocannons, false);
    fields.get("targetsOwn").set(WeaponType.Twin_Autocannons, false);
  }

  private static void initializeWeaponType_Hellfire_Missile_Pack() throws Exception {
    Class<?> c = WeaponType.class;
    Map<String, Field> fields =
        Stream.of(c.getDeclaredFields())
            .collect(
                Collectors.toMap(
                    f -> f.getName(),
                    f -> {
                      f.setAccessible(true);
                      return f;
                    }));
    fields.get("iD").set(WeaponType.Hellfire_Missile_Pack, 8);
    fields.get("tech").set(WeaponType.Hellfire_Missile_Pack, TechType.None);
    fields.get("whatUses").set(WeaponType.Hellfire_Missile_Pack, UnitType.Terran_Goliath);
    fields.get("damageAmount").set(WeaponType.Hellfire_Missile_Pack, 10);
    fields.get("damageBonus").set(WeaponType.Hellfire_Missile_Pack, 2);
    fields.get("damageCooldown").set(WeaponType.Hellfire_Missile_Pack, 22);
    fields.get("damageFactor").set(WeaponType.Hellfire_Missile_Pack, 2);
    fields
        .get("upgradeType")
        .set(WeaponType.Hellfire_Missile_Pack, UpgradeType.Terran_Vehicle_Weapons);
    fields.get("damageType").set(WeaponType.Hellfire_Missile_Pack, DamageType.Explosive);
    fields.get("explosionType").set(WeaponType.Hellfire_Missile_Pack, ExplosionType.Normal);
    fields.get("minRange").set(WeaponType.Hellfire_Missile_Pack, 0);
    fields.get("maxRange").set(WeaponType.Hellfire_Missile_Pack, 160);
    fields.get("innerSplashRadius").set(WeaponType.Hellfire_Missile_Pack, 0);
    fields.get("medianSplashRadius").set(WeaponType.Hellfire_Missile_Pack, 0);
    fields.get("outerSplashRadius").set(WeaponType.Hellfire_Missile_Pack, 0);
    fields.get("targetsAir").set(WeaponType.Hellfire_Missile_Pack, true);
    fields.get("targetsGround").set(WeaponType.Hellfire_Missile_Pack, false);
    fields.get("targetsMechanical").set(WeaponType.Hellfire_Missile_Pack, false);
    fields.get("targetsOrganic").set(WeaponType.Hellfire_Missile_Pack, false);
    fields.get("targetsNonBuilding").set(WeaponType.Hellfire_Missile_Pack, false);
    fields.get("targetsNonRobotic").set(WeaponType.Hellfire_Missile_Pack, false);
    fields.get("targetsTerrain").set(WeaponType.Hellfire_Missile_Pack, false);
    fields.get("targetsOrgOrMech").set(WeaponType.Hellfire_Missile_Pack, false);
    fields.get("targetsOwn").set(WeaponType.Hellfire_Missile_Pack, false);
  }

  private static void initializeWeaponType_Twin_Autocannons_Alan_Schezar() throws Exception {
    Class<?> c = WeaponType.class;
    Map<String, Field> fields =
        Stream.of(c.getDeclaredFields())
            .collect(
                Collectors.toMap(
                    f -> f.getName(),
                    f -> {
                      f.setAccessible(true);
                      return f;
                    }));
    fields.get("iD").set(WeaponType.Twin_Autocannons_Alan_Schezar, 9);
    fields.get("tech").set(WeaponType.Twin_Autocannons_Alan_Schezar, TechType.None);
    fields
        .get("whatUses")
        .set(WeaponType.Twin_Autocannons_Alan_Schezar, UnitType.Hero_Alan_Schezar);
    fields.get("damageAmount").set(WeaponType.Twin_Autocannons_Alan_Schezar, 24);
    fields.get("damageBonus").set(WeaponType.Twin_Autocannons_Alan_Schezar, 1);
    fields.get("damageCooldown").set(WeaponType.Twin_Autocannons_Alan_Schezar, 22);
    fields.get("damageFactor").set(WeaponType.Twin_Autocannons_Alan_Schezar, 1);
    fields
        .get("upgradeType")
        .set(WeaponType.Twin_Autocannons_Alan_Schezar, UpgradeType.Terran_Vehicle_Weapons);
    fields.get("damageType").set(WeaponType.Twin_Autocannons_Alan_Schezar, DamageType.Normal);
    fields.get("explosionType").set(WeaponType.Twin_Autocannons_Alan_Schezar, ExplosionType.Normal);
    fields.get("minRange").set(WeaponType.Twin_Autocannons_Alan_Schezar, 0);
    fields.get("maxRange").set(WeaponType.Twin_Autocannons_Alan_Schezar, 160);
    fields.get("innerSplashRadius").set(WeaponType.Twin_Autocannons_Alan_Schezar, 0);
    fields.get("medianSplashRadius").set(WeaponType.Twin_Autocannons_Alan_Schezar, 0);
    fields.get("outerSplashRadius").set(WeaponType.Twin_Autocannons_Alan_Schezar, 0);
    fields.get("targetsAir").set(WeaponType.Twin_Autocannons_Alan_Schezar, false);
    fields.get("targetsGround").set(WeaponType.Twin_Autocannons_Alan_Schezar, true);
    fields.get("targetsMechanical").set(WeaponType.Twin_Autocannons_Alan_Schezar, false);
    fields.get("targetsOrganic").set(WeaponType.Twin_Autocannons_Alan_Schezar, false);
    fields.get("targetsNonBuilding").set(WeaponType.Twin_Autocannons_Alan_Schezar, false);
    fields.get("targetsNonRobotic").set(WeaponType.Twin_Autocannons_Alan_Schezar, false);
    fields.get("targetsTerrain").set(WeaponType.Twin_Autocannons_Alan_Schezar, false);
    fields.get("targetsOrgOrMech").set(WeaponType.Twin_Autocannons_Alan_Schezar, false);
    fields.get("targetsOwn").set(WeaponType.Twin_Autocannons_Alan_Schezar, false);
  }

  private static void initializeWeaponType_Hellfire_Missile_Pack_Alan_Schezar() throws Exception {
    Class<?> c = WeaponType.class;
    Map<String, Field> fields =
        Stream.of(c.getDeclaredFields())
            .collect(
                Collectors.toMap(
                    f -> f.getName(),
                    f -> {
                      f.setAccessible(true);
                      return f;
                    }));
    fields.get("iD").set(WeaponType.Hellfire_Missile_Pack_Alan_Schezar, 10);
    fields.get("tech").set(WeaponType.Hellfire_Missile_Pack_Alan_Schezar, TechType.None);
    fields
        .get("whatUses")
        .set(WeaponType.Hellfire_Missile_Pack_Alan_Schezar, UnitType.Hero_Alan_Schezar);
    fields.get("damageAmount").set(WeaponType.Hellfire_Missile_Pack_Alan_Schezar, 20);
    fields.get("damageBonus").set(WeaponType.Hellfire_Missile_Pack_Alan_Schezar, 1);
    fields.get("damageCooldown").set(WeaponType.Hellfire_Missile_Pack_Alan_Schezar, 22);
    fields.get("damageFactor").set(WeaponType.Hellfire_Missile_Pack_Alan_Schezar, 2);
    fields
        .get("upgradeType")
        .set(WeaponType.Hellfire_Missile_Pack_Alan_Schezar, UpgradeType.Terran_Vehicle_Weapons);
    fields
        .get("damageType")
        .set(WeaponType.Hellfire_Missile_Pack_Alan_Schezar, DamageType.Explosive);
    fields
        .get("explosionType")
        .set(WeaponType.Hellfire_Missile_Pack_Alan_Schezar, ExplosionType.Normal);
    fields.get("minRange").set(WeaponType.Hellfire_Missile_Pack_Alan_Schezar, 0);
    fields.get("maxRange").set(WeaponType.Hellfire_Missile_Pack_Alan_Schezar, 160);
    fields.get("innerSplashRadius").set(WeaponType.Hellfire_Missile_Pack_Alan_Schezar, 0);
    fields.get("medianSplashRadius").set(WeaponType.Hellfire_Missile_Pack_Alan_Schezar, 0);
    fields.get("outerSplashRadius").set(WeaponType.Hellfire_Missile_Pack_Alan_Schezar, 0);
    fields.get("targetsAir").set(WeaponType.Hellfire_Missile_Pack_Alan_Schezar, true);
    fields.get("targetsGround").set(WeaponType.Hellfire_Missile_Pack_Alan_Schezar, false);
    fields.get("targetsMechanical").set(WeaponType.Hellfire_Missile_Pack_Alan_Schezar, false);
    fields.get("targetsOrganic").set(WeaponType.Hellfire_Missile_Pack_Alan_Schezar, false);
    fields.get("targetsNonBuilding").set(WeaponType.Hellfire_Missile_Pack_Alan_Schezar, false);
    fields.get("targetsNonRobotic").set(WeaponType.Hellfire_Missile_Pack_Alan_Schezar, false);
    fields.get("targetsTerrain").set(WeaponType.Hellfire_Missile_Pack_Alan_Schezar, false);
    fields.get("targetsOrgOrMech").set(WeaponType.Hellfire_Missile_Pack_Alan_Schezar, false);
    fields.get("targetsOwn").set(WeaponType.Hellfire_Missile_Pack_Alan_Schezar, false);
  }

  private static void initializeWeaponType_Arclite_Cannon() throws Exception {
    Class<?> c = WeaponType.class;
    Map<String, Field> fields =
        Stream.of(c.getDeclaredFields())
            .collect(
                Collectors.toMap(
                    f -> f.getName(),
                    f -> {
                      f.setAccessible(true);
                      return f;
                    }));
    fields.get("iD").set(WeaponType.Arclite_Cannon, 11);
    fields.get("tech").set(WeaponType.Arclite_Cannon, TechType.None);
    fields.get("whatUses").set(WeaponType.Arclite_Cannon, UnitType.Terran_Siege_Tank_Tank_Mode);
    fields.get("damageAmount").set(WeaponType.Arclite_Cannon, 30);
    fields.get("damageBonus").set(WeaponType.Arclite_Cannon, 3);
    fields.get("damageCooldown").set(WeaponType.Arclite_Cannon, 37);
    fields.get("damageFactor").set(WeaponType.Arclite_Cannon, 1);
    fields.get("upgradeType").set(WeaponType.Arclite_Cannon, UpgradeType.Terran_Vehicle_Weapons);
    fields.get("damageType").set(WeaponType.Arclite_Cannon, DamageType.Explosive);
    fields.get("explosionType").set(WeaponType.Arclite_Cannon, ExplosionType.Normal);
    fields.get("minRange").set(WeaponType.Arclite_Cannon, 0);
    fields.get("maxRange").set(WeaponType.Arclite_Cannon, 224);
    fields.get("innerSplashRadius").set(WeaponType.Arclite_Cannon, 0);
    fields.get("medianSplashRadius").set(WeaponType.Arclite_Cannon, 0);
    fields.get("outerSplashRadius").set(WeaponType.Arclite_Cannon, 0);
    fields.get("targetsAir").set(WeaponType.Arclite_Cannon, false);
    fields.get("targetsGround").set(WeaponType.Arclite_Cannon, true);
    fields.get("targetsMechanical").set(WeaponType.Arclite_Cannon, false);
    fields.get("targetsOrganic").set(WeaponType.Arclite_Cannon, false);
    fields.get("targetsNonBuilding").set(WeaponType.Arclite_Cannon, false);
    fields.get("targetsNonRobotic").set(WeaponType.Arclite_Cannon, false);
    fields.get("targetsTerrain").set(WeaponType.Arclite_Cannon, false);
    fields.get("targetsOrgOrMech").set(WeaponType.Arclite_Cannon, false);
    fields.get("targetsOwn").set(WeaponType.Arclite_Cannon, false);
  }

  private static void initializeWeaponType_Arclite_Cannon_Edmund_Duke() throws Exception {
    Class<?> c = WeaponType.class;
    Map<String, Field> fields =
        Stream.of(c.getDeclaredFields())
            .collect(
                Collectors.toMap(
                    f -> f.getName(),
                    f -> {
                      f.setAccessible(true);
                      return f;
                    }));
    fields.get("iD").set(WeaponType.Arclite_Cannon_Edmund_Duke, 12);
    fields.get("tech").set(WeaponType.Arclite_Cannon_Edmund_Duke, TechType.None);
    fields
        .get("whatUses")
        .set(WeaponType.Arclite_Cannon_Edmund_Duke, UnitType.Hero_Edmund_Duke_Tank_Mode);
    fields.get("damageAmount").set(WeaponType.Arclite_Cannon_Edmund_Duke, 70);
    fields.get("damageBonus").set(WeaponType.Arclite_Cannon_Edmund_Duke, 3);
    fields.get("damageCooldown").set(WeaponType.Arclite_Cannon_Edmund_Duke, 37);
    fields.get("damageFactor").set(WeaponType.Arclite_Cannon_Edmund_Duke, 1);
    fields
        .get("upgradeType")
        .set(WeaponType.Arclite_Cannon_Edmund_Duke, UpgradeType.Terran_Vehicle_Weapons);
    fields.get("damageType").set(WeaponType.Arclite_Cannon_Edmund_Duke, DamageType.Explosive);
    fields.get("explosionType").set(WeaponType.Arclite_Cannon_Edmund_Duke, ExplosionType.Normal);
    fields.get("minRange").set(WeaponType.Arclite_Cannon_Edmund_Duke, 0);
    fields.get("maxRange").set(WeaponType.Arclite_Cannon_Edmund_Duke, 224);
    fields.get("innerSplashRadius").set(WeaponType.Arclite_Cannon_Edmund_Duke, 0);
    fields.get("medianSplashRadius").set(WeaponType.Arclite_Cannon_Edmund_Duke, 0);
    fields.get("outerSplashRadius").set(WeaponType.Arclite_Cannon_Edmund_Duke, 0);
    fields.get("targetsAir").set(WeaponType.Arclite_Cannon_Edmund_Duke, false);
    fields.get("targetsGround").set(WeaponType.Arclite_Cannon_Edmund_Duke, true);
    fields.get("targetsMechanical").set(WeaponType.Arclite_Cannon_Edmund_Duke, false);
    fields.get("targetsOrganic").set(WeaponType.Arclite_Cannon_Edmund_Duke, false);
    fields.get("targetsNonBuilding").set(WeaponType.Arclite_Cannon_Edmund_Duke, false);
    fields.get("targetsNonRobotic").set(WeaponType.Arclite_Cannon_Edmund_Duke, false);
    fields.get("targetsTerrain").set(WeaponType.Arclite_Cannon_Edmund_Duke, false);
    fields.get("targetsOrgOrMech").set(WeaponType.Arclite_Cannon_Edmund_Duke, false);
    fields.get("targetsOwn").set(WeaponType.Arclite_Cannon_Edmund_Duke, false);
  }

  private static void initializeWeaponType_Fusion_Cutter() throws Exception {
    Class<?> c = WeaponType.class;
    Map<String, Field> fields =
        Stream.of(c.getDeclaredFields())
            .collect(
                Collectors.toMap(
                    f -> f.getName(),
                    f -> {
                      f.setAccessible(true);
                      return f;
                    }));
    fields.get("iD").set(WeaponType.Fusion_Cutter, 13);
    fields.get("tech").set(WeaponType.Fusion_Cutter, TechType.None);
    fields.get("whatUses").set(WeaponType.Fusion_Cutter, UnitType.Terran_SCV);
    fields.get("damageAmount").set(WeaponType.Fusion_Cutter, 5);
    fields.get("damageBonus").set(WeaponType.Fusion_Cutter, 1);
    fields.get("damageCooldown").set(WeaponType.Fusion_Cutter, 15);
    fields.get("damageFactor").set(WeaponType.Fusion_Cutter, 1);
    fields.get("upgradeType").set(WeaponType.Fusion_Cutter, UpgradeType.Upgrade_60);
    fields.get("damageType").set(WeaponType.Fusion_Cutter, DamageType.Normal);
    fields.get("explosionType").set(WeaponType.Fusion_Cutter, ExplosionType.Normal);
    fields.get("minRange").set(WeaponType.Fusion_Cutter, 0);
    fields.get("maxRange").set(WeaponType.Fusion_Cutter, 10);
    fields.get("innerSplashRadius").set(WeaponType.Fusion_Cutter, 0);
    fields.get("medianSplashRadius").set(WeaponType.Fusion_Cutter, 0);
    fields.get("outerSplashRadius").set(WeaponType.Fusion_Cutter, 0);
    fields.get("targetsAir").set(WeaponType.Fusion_Cutter, false);
    fields.get("targetsGround").set(WeaponType.Fusion_Cutter, true);
    fields.get("targetsMechanical").set(WeaponType.Fusion_Cutter, false);
    fields.get("targetsOrganic").set(WeaponType.Fusion_Cutter, false);
    fields.get("targetsNonBuilding").set(WeaponType.Fusion_Cutter, false);
    fields.get("targetsNonRobotic").set(WeaponType.Fusion_Cutter, false);
    fields.get("targetsTerrain").set(WeaponType.Fusion_Cutter, false);
    fields.get("targetsOrgOrMech").set(WeaponType.Fusion_Cutter, false);
    fields.get("targetsOwn").set(WeaponType.Fusion_Cutter, false);
  }

  private static void initializeWeaponType_unk_14() throws Exception {
    Class<?> c = WeaponType.class;
    Map<String, Field> fields =
        Stream.of(c.getDeclaredFields())
            .collect(
                Collectors.toMap(
                    f -> f.getName(),
                    f -> {
                      f.setAccessible(true);
                      return f;
                    }));
    fields.get("iD").set(WeaponType.unk_14, 14);
    fields.get("tech").set(WeaponType.unk_14, null);
    fields.get("whatUses").set(WeaponType.unk_14, null);
    fields.get("damageAmount").set(WeaponType.unk_14, 0);
    fields.get("damageBonus").set(WeaponType.unk_14, 0);
    fields.get("damageCooldown").set(WeaponType.unk_14, 0);
    fields.get("damageFactor").set(WeaponType.unk_14, 0);
    fields.get("upgradeType").set(WeaponType.unk_14, null);
    fields.get("damageType").set(WeaponType.unk_14, null);
    fields.get("explosionType").set(WeaponType.unk_14, null);
    fields.get("minRange").set(WeaponType.unk_14, 0);
    fields.get("maxRange").set(WeaponType.unk_14, 0);
    fields.get("innerSplashRadius").set(WeaponType.unk_14, 0);
    fields.get("medianSplashRadius").set(WeaponType.unk_14, 0);
    fields.get("outerSplashRadius").set(WeaponType.unk_14, 0);
    fields.get("targetsAir").set(WeaponType.unk_14, false);
    fields.get("targetsGround").set(WeaponType.unk_14, false);
    fields.get("targetsMechanical").set(WeaponType.unk_14, false);
    fields.get("targetsOrganic").set(WeaponType.unk_14, false);
    fields.get("targetsNonBuilding").set(WeaponType.unk_14, false);
    fields.get("targetsNonRobotic").set(WeaponType.unk_14, false);
    fields.get("targetsTerrain").set(WeaponType.unk_14, false);
    fields.get("targetsOrgOrMech").set(WeaponType.unk_14, false);
    fields.get("targetsOwn").set(WeaponType.unk_14, false);
  }

  private static void initializeWeaponType_Gemini_Missiles() throws Exception {
    Class<?> c = WeaponType.class;
    Map<String, Field> fields =
        Stream.of(c.getDeclaredFields())
            .collect(
                Collectors.toMap(
                    f -> f.getName(),
                    f -> {
                      f.setAccessible(true);
                      return f;
                    }));
    fields.get("iD").set(WeaponType.Gemini_Missiles, 15);
    fields.get("tech").set(WeaponType.Gemini_Missiles, TechType.None);
    fields.get("whatUses").set(WeaponType.Gemini_Missiles, UnitType.Terran_Wraith);
    fields.get("damageAmount").set(WeaponType.Gemini_Missiles, 20);
    fields.get("damageBonus").set(WeaponType.Gemini_Missiles, 2);
    fields.get("damageCooldown").set(WeaponType.Gemini_Missiles, 22);
    fields.get("damageFactor").set(WeaponType.Gemini_Missiles, 1);
    fields.get("upgradeType").set(WeaponType.Gemini_Missiles, UpgradeType.Terran_Ship_Weapons);
    fields.get("damageType").set(WeaponType.Gemini_Missiles, DamageType.Explosive);
    fields.get("explosionType").set(WeaponType.Gemini_Missiles, ExplosionType.Normal);
    fields.get("minRange").set(WeaponType.Gemini_Missiles, 0);
    fields.get("maxRange").set(WeaponType.Gemini_Missiles, 160);
    fields.get("innerSplashRadius").set(WeaponType.Gemini_Missiles, 0);
    fields.get("medianSplashRadius").set(WeaponType.Gemini_Missiles, 0);
    fields.get("outerSplashRadius").set(WeaponType.Gemini_Missiles, 0);
    fields.get("targetsAir").set(WeaponType.Gemini_Missiles, true);
    fields.get("targetsGround").set(WeaponType.Gemini_Missiles, false);
    fields.get("targetsMechanical").set(WeaponType.Gemini_Missiles, false);
    fields.get("targetsOrganic").set(WeaponType.Gemini_Missiles, false);
    fields.get("targetsNonBuilding").set(WeaponType.Gemini_Missiles, false);
    fields.get("targetsNonRobotic").set(WeaponType.Gemini_Missiles, false);
    fields.get("targetsTerrain").set(WeaponType.Gemini_Missiles, false);
    fields.get("targetsOrgOrMech").set(WeaponType.Gemini_Missiles, false);
    fields.get("targetsOwn").set(WeaponType.Gemini_Missiles, false);
  }

  private static void initializeWeaponType_Burst_Lasers() throws Exception {
    Class<?> c = WeaponType.class;
    Map<String, Field> fields =
        Stream.of(c.getDeclaredFields())
            .collect(
                Collectors.toMap(
                    f -> f.getName(),
                    f -> {
                      f.setAccessible(true);
                      return f;
                    }));
    fields.get("iD").set(WeaponType.Burst_Lasers, 16);
    fields.get("tech").set(WeaponType.Burst_Lasers, TechType.None);
    fields.get("whatUses").set(WeaponType.Burst_Lasers, UnitType.Terran_Wraith);
    fields.get("damageAmount").set(WeaponType.Burst_Lasers, 8);
    fields.get("damageBonus").set(WeaponType.Burst_Lasers, 1);
    fields.get("damageCooldown").set(WeaponType.Burst_Lasers, 30);
    fields.get("damageFactor").set(WeaponType.Burst_Lasers, 1);
    fields.get("upgradeType").set(WeaponType.Burst_Lasers, UpgradeType.Terran_Ship_Weapons);
    fields.get("damageType").set(WeaponType.Burst_Lasers, DamageType.Normal);
    fields.get("explosionType").set(WeaponType.Burst_Lasers, ExplosionType.Normal);
    fields.get("minRange").set(WeaponType.Burst_Lasers, 0);
    fields.get("maxRange").set(WeaponType.Burst_Lasers, 160);
    fields.get("innerSplashRadius").set(WeaponType.Burst_Lasers, 0);
    fields.get("medianSplashRadius").set(WeaponType.Burst_Lasers, 0);
    fields.get("outerSplashRadius").set(WeaponType.Burst_Lasers, 0);
    fields.get("targetsAir").set(WeaponType.Burst_Lasers, false);
    fields.get("targetsGround").set(WeaponType.Burst_Lasers, true);
    fields.get("targetsMechanical").set(WeaponType.Burst_Lasers, false);
    fields.get("targetsOrganic").set(WeaponType.Burst_Lasers, false);
    fields.get("targetsNonBuilding").set(WeaponType.Burst_Lasers, false);
    fields.get("targetsNonRobotic").set(WeaponType.Burst_Lasers, false);
    fields.get("targetsTerrain").set(WeaponType.Burst_Lasers, false);
    fields.get("targetsOrgOrMech").set(WeaponType.Burst_Lasers, false);
    fields.get("targetsOwn").set(WeaponType.Burst_Lasers, false);
  }

  private static void initializeWeaponType_Gemini_Missiles_Tom_Kazansky() throws Exception {
    Class<?> c = WeaponType.class;
    Map<String, Field> fields =
        Stream.of(c.getDeclaredFields())
            .collect(
                Collectors.toMap(
                    f -> f.getName(),
                    f -> {
                      f.setAccessible(true);
                      return f;
                    }));
    fields.get("iD").set(WeaponType.Gemini_Missiles_Tom_Kazansky, 17);
    fields.get("tech").set(WeaponType.Gemini_Missiles_Tom_Kazansky, TechType.None);
    fields.get("whatUses").set(WeaponType.Gemini_Missiles_Tom_Kazansky, UnitType.Hero_Tom_Kazansky);
    fields.get("damageAmount").set(WeaponType.Gemini_Missiles_Tom_Kazansky, 40);
    fields.get("damageBonus").set(WeaponType.Gemini_Missiles_Tom_Kazansky, 2);
    fields.get("damageCooldown").set(WeaponType.Gemini_Missiles_Tom_Kazansky, 22);
    fields.get("damageFactor").set(WeaponType.Gemini_Missiles_Tom_Kazansky, 1);
    fields
        .get("upgradeType")
        .set(WeaponType.Gemini_Missiles_Tom_Kazansky, UpgradeType.Terran_Ship_Weapons);
    fields.get("damageType").set(WeaponType.Gemini_Missiles_Tom_Kazansky, DamageType.Explosive);
    fields.get("explosionType").set(WeaponType.Gemini_Missiles_Tom_Kazansky, ExplosionType.Normal);
    fields.get("minRange").set(WeaponType.Gemini_Missiles_Tom_Kazansky, 0);
    fields.get("maxRange").set(WeaponType.Gemini_Missiles_Tom_Kazansky, 160);
    fields.get("innerSplashRadius").set(WeaponType.Gemini_Missiles_Tom_Kazansky, 0);
    fields.get("medianSplashRadius").set(WeaponType.Gemini_Missiles_Tom_Kazansky, 0);
    fields.get("outerSplashRadius").set(WeaponType.Gemini_Missiles_Tom_Kazansky, 0);
    fields.get("targetsAir").set(WeaponType.Gemini_Missiles_Tom_Kazansky, true);
    fields.get("targetsGround").set(WeaponType.Gemini_Missiles_Tom_Kazansky, false);
    fields.get("targetsMechanical").set(WeaponType.Gemini_Missiles_Tom_Kazansky, false);
    fields.get("targetsOrganic").set(WeaponType.Gemini_Missiles_Tom_Kazansky, false);
    fields.get("targetsNonBuilding").set(WeaponType.Gemini_Missiles_Tom_Kazansky, false);
    fields.get("targetsNonRobotic").set(WeaponType.Gemini_Missiles_Tom_Kazansky, false);
    fields.get("targetsTerrain").set(WeaponType.Gemini_Missiles_Tom_Kazansky, false);
    fields.get("targetsOrgOrMech").set(WeaponType.Gemini_Missiles_Tom_Kazansky, false);
    fields.get("targetsOwn").set(WeaponType.Gemini_Missiles_Tom_Kazansky, false);
  }

  private static void initializeWeaponType_Burst_Lasers_Tom_Kazansky() throws Exception {
    Class<?> c = WeaponType.class;
    Map<String, Field> fields =
        Stream.of(c.getDeclaredFields())
            .collect(
                Collectors.toMap(
                    f -> f.getName(),
                    f -> {
                      f.setAccessible(true);
                      return f;
                    }));
    fields.get("iD").set(WeaponType.Burst_Lasers_Tom_Kazansky, 18);
    fields.get("tech").set(WeaponType.Burst_Lasers_Tom_Kazansky, TechType.None);
    fields.get("whatUses").set(WeaponType.Burst_Lasers_Tom_Kazansky, UnitType.Hero_Tom_Kazansky);
    fields.get("damageAmount").set(WeaponType.Burst_Lasers_Tom_Kazansky, 16);
    fields.get("damageBonus").set(WeaponType.Burst_Lasers_Tom_Kazansky, 1);
    fields.get("damageCooldown").set(WeaponType.Burst_Lasers_Tom_Kazansky, 30);
    fields.get("damageFactor").set(WeaponType.Burst_Lasers_Tom_Kazansky, 1);
    fields
        .get("upgradeType")
        .set(WeaponType.Burst_Lasers_Tom_Kazansky, UpgradeType.Terran_Ship_Weapons);
    fields.get("damageType").set(WeaponType.Burst_Lasers_Tom_Kazansky, DamageType.Normal);
    fields.get("explosionType").set(WeaponType.Burst_Lasers_Tom_Kazansky, ExplosionType.Normal);
    fields.get("minRange").set(WeaponType.Burst_Lasers_Tom_Kazansky, 0);
    fields.get("maxRange").set(WeaponType.Burst_Lasers_Tom_Kazansky, 160);
    fields.get("innerSplashRadius").set(WeaponType.Burst_Lasers_Tom_Kazansky, 0);
    fields.get("medianSplashRadius").set(WeaponType.Burst_Lasers_Tom_Kazansky, 0);
    fields.get("outerSplashRadius").set(WeaponType.Burst_Lasers_Tom_Kazansky, 0);
    fields.get("targetsAir").set(WeaponType.Burst_Lasers_Tom_Kazansky, false);
    fields.get("targetsGround").set(WeaponType.Burst_Lasers_Tom_Kazansky, true);
    fields.get("targetsMechanical").set(WeaponType.Burst_Lasers_Tom_Kazansky, false);
    fields.get("targetsOrganic").set(WeaponType.Burst_Lasers_Tom_Kazansky, false);
    fields.get("targetsNonBuilding").set(WeaponType.Burst_Lasers_Tom_Kazansky, false);
    fields.get("targetsNonRobotic").set(WeaponType.Burst_Lasers_Tom_Kazansky, false);
    fields.get("targetsTerrain").set(WeaponType.Burst_Lasers_Tom_Kazansky, false);
    fields.get("targetsOrgOrMech").set(WeaponType.Burst_Lasers_Tom_Kazansky, false);
    fields.get("targetsOwn").set(WeaponType.Burst_Lasers_Tom_Kazansky, false);
  }

  private static void initializeWeaponType_ATS_Laser_Battery() throws Exception {
    Class<?> c = WeaponType.class;
    Map<String, Field> fields =
        Stream.of(c.getDeclaredFields())
            .collect(
                Collectors.toMap(
                    f -> f.getName(),
                    f -> {
                      f.setAccessible(true);
                      return f;
                    }));
    fields.get("iD").set(WeaponType.ATS_Laser_Battery, 19);
    fields.get("tech").set(WeaponType.ATS_Laser_Battery, TechType.None);
    fields.get("whatUses").set(WeaponType.ATS_Laser_Battery, UnitType.Terran_Battlecruiser);
    fields.get("damageAmount").set(WeaponType.ATS_Laser_Battery, 25);
    fields.get("damageBonus").set(WeaponType.ATS_Laser_Battery, 3);
    fields.get("damageCooldown").set(WeaponType.ATS_Laser_Battery, 30);
    fields.get("damageFactor").set(WeaponType.ATS_Laser_Battery, 1);
    fields.get("upgradeType").set(WeaponType.ATS_Laser_Battery, UpgradeType.Terran_Ship_Weapons);
    fields.get("damageType").set(WeaponType.ATS_Laser_Battery, DamageType.Normal);
    fields.get("explosionType").set(WeaponType.ATS_Laser_Battery, ExplosionType.Normal);
    fields.get("minRange").set(WeaponType.ATS_Laser_Battery, 0);
    fields.get("maxRange").set(WeaponType.ATS_Laser_Battery, 192);
    fields.get("innerSplashRadius").set(WeaponType.ATS_Laser_Battery, 0);
    fields.get("medianSplashRadius").set(WeaponType.ATS_Laser_Battery, 0);
    fields.get("outerSplashRadius").set(WeaponType.ATS_Laser_Battery, 0);
    fields.get("targetsAir").set(WeaponType.ATS_Laser_Battery, false);
    fields.get("targetsGround").set(WeaponType.ATS_Laser_Battery, true);
    fields.get("targetsMechanical").set(WeaponType.ATS_Laser_Battery, false);
    fields.get("targetsOrganic").set(WeaponType.ATS_Laser_Battery, false);
    fields.get("targetsNonBuilding").set(WeaponType.ATS_Laser_Battery, false);
    fields.get("targetsNonRobotic").set(WeaponType.ATS_Laser_Battery, false);
    fields.get("targetsTerrain").set(WeaponType.ATS_Laser_Battery, false);
    fields.get("targetsOrgOrMech").set(WeaponType.ATS_Laser_Battery, false);
    fields.get("targetsOwn").set(WeaponType.ATS_Laser_Battery, false);
  }

  private static void initializeWeaponType_ATA_Laser_Battery() throws Exception {
    Class<?> c = WeaponType.class;
    Map<String, Field> fields =
        Stream.of(c.getDeclaredFields())
            .collect(
                Collectors.toMap(
                    f -> f.getName(),
                    f -> {
                      f.setAccessible(true);
                      return f;
                    }));
    fields.get("iD").set(WeaponType.ATA_Laser_Battery, 20);
    fields.get("tech").set(WeaponType.ATA_Laser_Battery, TechType.None);
    fields.get("whatUses").set(WeaponType.ATA_Laser_Battery, UnitType.Terran_Battlecruiser);
    fields.get("damageAmount").set(WeaponType.ATA_Laser_Battery, 25);
    fields.get("damageBonus").set(WeaponType.ATA_Laser_Battery, 3);
    fields.get("damageCooldown").set(WeaponType.ATA_Laser_Battery, 30);
    fields.get("damageFactor").set(WeaponType.ATA_Laser_Battery, 1);
    fields.get("upgradeType").set(WeaponType.ATA_Laser_Battery, UpgradeType.Terran_Ship_Weapons);
    fields.get("damageType").set(WeaponType.ATA_Laser_Battery, DamageType.Normal);
    fields.get("explosionType").set(WeaponType.ATA_Laser_Battery, ExplosionType.Normal);
    fields.get("minRange").set(WeaponType.ATA_Laser_Battery, 0);
    fields.get("maxRange").set(WeaponType.ATA_Laser_Battery, 192);
    fields.get("innerSplashRadius").set(WeaponType.ATA_Laser_Battery, 0);
    fields.get("medianSplashRadius").set(WeaponType.ATA_Laser_Battery, 0);
    fields.get("outerSplashRadius").set(WeaponType.ATA_Laser_Battery, 0);
    fields.get("targetsAir").set(WeaponType.ATA_Laser_Battery, true);
    fields.get("targetsGround").set(WeaponType.ATA_Laser_Battery, false);
    fields.get("targetsMechanical").set(WeaponType.ATA_Laser_Battery, false);
    fields.get("targetsOrganic").set(WeaponType.ATA_Laser_Battery, false);
    fields.get("targetsNonBuilding").set(WeaponType.ATA_Laser_Battery, false);
    fields.get("targetsNonRobotic").set(WeaponType.ATA_Laser_Battery, false);
    fields.get("targetsTerrain").set(WeaponType.ATA_Laser_Battery, false);
    fields.get("targetsOrgOrMech").set(WeaponType.ATA_Laser_Battery, false);
    fields.get("targetsOwn").set(WeaponType.ATA_Laser_Battery, false);
  }

  private static void initializeWeaponType_ATS_Laser_Battery_Hero() throws Exception {
    Class<?> c = WeaponType.class;
    Map<String, Field> fields =
        Stream.of(c.getDeclaredFields())
            .collect(
                Collectors.toMap(
                    f -> f.getName(),
                    f -> {
                      f.setAccessible(true);
                      return f;
                    }));
    fields.get("iD").set(WeaponType.ATS_Laser_Battery_Hero, 21);
    fields.get("tech").set(WeaponType.ATS_Laser_Battery_Hero, TechType.None);
    fields.get("whatUses").set(WeaponType.ATS_Laser_Battery_Hero, UnitType.Hero_Norad_II);
    fields.get("damageAmount").set(WeaponType.ATS_Laser_Battery_Hero, 50);
    fields.get("damageBonus").set(WeaponType.ATS_Laser_Battery_Hero, 3);
    fields.get("damageCooldown").set(WeaponType.ATS_Laser_Battery_Hero, 30);
    fields.get("damageFactor").set(WeaponType.ATS_Laser_Battery_Hero, 1);
    fields
        .get("upgradeType")
        .set(WeaponType.ATS_Laser_Battery_Hero, UpgradeType.Terran_Ship_Weapons);
    fields.get("damageType").set(WeaponType.ATS_Laser_Battery_Hero, DamageType.Normal);
    fields.get("explosionType").set(WeaponType.ATS_Laser_Battery_Hero, ExplosionType.Normal);
    fields.get("minRange").set(WeaponType.ATS_Laser_Battery_Hero, 0);
    fields.get("maxRange").set(WeaponType.ATS_Laser_Battery_Hero, 192);
    fields.get("innerSplashRadius").set(WeaponType.ATS_Laser_Battery_Hero, 0);
    fields.get("medianSplashRadius").set(WeaponType.ATS_Laser_Battery_Hero, 0);
    fields.get("outerSplashRadius").set(WeaponType.ATS_Laser_Battery_Hero, 0);
    fields.get("targetsAir").set(WeaponType.ATS_Laser_Battery_Hero, false);
    fields.get("targetsGround").set(WeaponType.ATS_Laser_Battery_Hero, true);
    fields.get("targetsMechanical").set(WeaponType.ATS_Laser_Battery_Hero, false);
    fields.get("targetsOrganic").set(WeaponType.ATS_Laser_Battery_Hero, false);
    fields.get("targetsNonBuilding").set(WeaponType.ATS_Laser_Battery_Hero, false);
    fields.get("targetsNonRobotic").set(WeaponType.ATS_Laser_Battery_Hero, false);
    fields.get("targetsTerrain").set(WeaponType.ATS_Laser_Battery_Hero, false);
    fields.get("targetsOrgOrMech").set(WeaponType.ATS_Laser_Battery_Hero, false);
    fields.get("targetsOwn").set(WeaponType.ATS_Laser_Battery_Hero, false);
  }

  private static void initializeWeaponType_ATA_Laser_Battery_Hero() throws Exception {
    Class<?> c = WeaponType.class;
    Map<String, Field> fields =
        Stream.of(c.getDeclaredFields())
            .collect(
                Collectors.toMap(
                    f -> f.getName(),
                    f -> {
                      f.setAccessible(true);
                      return f;
                    }));
    fields.get("iD").set(WeaponType.ATA_Laser_Battery_Hero, 22);
    fields.get("tech").set(WeaponType.ATA_Laser_Battery_Hero, TechType.None);
    fields.get("whatUses").set(WeaponType.ATA_Laser_Battery_Hero, UnitType.Hero_Norad_II);
    fields.get("damageAmount").set(WeaponType.ATA_Laser_Battery_Hero, 50);
    fields.get("damageBonus").set(WeaponType.ATA_Laser_Battery_Hero, 3);
    fields.get("damageCooldown").set(WeaponType.ATA_Laser_Battery_Hero, 30);
    fields.get("damageFactor").set(WeaponType.ATA_Laser_Battery_Hero, 1);
    fields
        .get("upgradeType")
        .set(WeaponType.ATA_Laser_Battery_Hero, UpgradeType.Terran_Ship_Weapons);
    fields.get("damageType").set(WeaponType.ATA_Laser_Battery_Hero, DamageType.Normal);
    fields.get("explosionType").set(WeaponType.ATA_Laser_Battery_Hero, ExplosionType.Normal);
    fields.get("minRange").set(WeaponType.ATA_Laser_Battery_Hero, 0);
    fields.get("maxRange").set(WeaponType.ATA_Laser_Battery_Hero, 192);
    fields.get("innerSplashRadius").set(WeaponType.ATA_Laser_Battery_Hero, 0);
    fields.get("medianSplashRadius").set(WeaponType.ATA_Laser_Battery_Hero, 0);
    fields.get("outerSplashRadius").set(WeaponType.ATA_Laser_Battery_Hero, 0);
    fields.get("targetsAir").set(WeaponType.ATA_Laser_Battery_Hero, true);
    fields.get("targetsGround").set(WeaponType.ATA_Laser_Battery_Hero, false);
    fields.get("targetsMechanical").set(WeaponType.ATA_Laser_Battery_Hero, false);
    fields.get("targetsOrganic").set(WeaponType.ATA_Laser_Battery_Hero, false);
    fields.get("targetsNonBuilding").set(WeaponType.ATA_Laser_Battery_Hero, false);
    fields.get("targetsNonRobotic").set(WeaponType.ATA_Laser_Battery_Hero, false);
    fields.get("targetsTerrain").set(WeaponType.ATA_Laser_Battery_Hero, false);
    fields.get("targetsOrgOrMech").set(WeaponType.ATA_Laser_Battery_Hero, false);
    fields.get("targetsOwn").set(WeaponType.ATA_Laser_Battery_Hero, false);
  }

  private static void initializeWeaponType_ATS_Laser_Battery_Hyperion() throws Exception {
    Class<?> c = WeaponType.class;
    Map<String, Field> fields =
        Stream.of(c.getDeclaredFields())
            .collect(
                Collectors.toMap(
                    f -> f.getName(),
                    f -> {
                      f.setAccessible(true);
                      return f;
                    }));
    fields.get("iD").set(WeaponType.ATS_Laser_Battery_Hyperion, 23);
    fields.get("tech").set(WeaponType.ATS_Laser_Battery_Hyperion, TechType.None);
    fields.get("whatUses").set(WeaponType.ATS_Laser_Battery_Hyperion, UnitType.Hero_Hyperion);
    fields.get("damageAmount").set(WeaponType.ATS_Laser_Battery_Hyperion, 30);
    fields.get("damageBonus").set(WeaponType.ATS_Laser_Battery_Hyperion, 3);
    fields.get("damageCooldown").set(WeaponType.ATS_Laser_Battery_Hyperion, 22);
    fields.get("damageFactor").set(WeaponType.ATS_Laser_Battery_Hyperion, 1);
    fields
        .get("upgradeType")
        .set(WeaponType.ATS_Laser_Battery_Hyperion, UpgradeType.Terran_Ship_Weapons);
    fields.get("damageType").set(WeaponType.ATS_Laser_Battery_Hyperion, DamageType.Normal);
    fields.get("explosionType").set(WeaponType.ATS_Laser_Battery_Hyperion, ExplosionType.Normal);
    fields.get("minRange").set(WeaponType.ATS_Laser_Battery_Hyperion, 0);
    fields.get("maxRange").set(WeaponType.ATS_Laser_Battery_Hyperion, 192);
    fields.get("innerSplashRadius").set(WeaponType.ATS_Laser_Battery_Hyperion, 0);
    fields.get("medianSplashRadius").set(WeaponType.ATS_Laser_Battery_Hyperion, 0);
    fields.get("outerSplashRadius").set(WeaponType.ATS_Laser_Battery_Hyperion, 0);
    fields.get("targetsAir").set(WeaponType.ATS_Laser_Battery_Hyperion, false);
    fields.get("targetsGround").set(WeaponType.ATS_Laser_Battery_Hyperion, true);
    fields.get("targetsMechanical").set(WeaponType.ATS_Laser_Battery_Hyperion, false);
    fields.get("targetsOrganic").set(WeaponType.ATS_Laser_Battery_Hyperion, false);
    fields.get("targetsNonBuilding").set(WeaponType.ATS_Laser_Battery_Hyperion, false);
    fields.get("targetsNonRobotic").set(WeaponType.ATS_Laser_Battery_Hyperion, false);
    fields.get("targetsTerrain").set(WeaponType.ATS_Laser_Battery_Hyperion, false);
    fields.get("targetsOrgOrMech").set(WeaponType.ATS_Laser_Battery_Hyperion, false);
    fields.get("targetsOwn").set(WeaponType.ATS_Laser_Battery_Hyperion, false);
  }

  private static void initializeWeaponType_ATA_Laser_Battery_Hyperion() throws Exception {
    Class<?> c = WeaponType.class;
    Map<String, Field> fields =
        Stream.of(c.getDeclaredFields())
            .collect(
                Collectors.toMap(
                    f -> f.getName(),
                    f -> {
                      f.setAccessible(true);
                      return f;
                    }));
    fields.get("iD").set(WeaponType.ATA_Laser_Battery_Hyperion, 24);
    fields.get("tech").set(WeaponType.ATA_Laser_Battery_Hyperion, TechType.None);
    fields.get("whatUses").set(WeaponType.ATA_Laser_Battery_Hyperion, UnitType.Hero_Hyperion);
    fields.get("damageAmount").set(WeaponType.ATA_Laser_Battery_Hyperion, 30);
    fields.get("damageBonus").set(WeaponType.ATA_Laser_Battery_Hyperion, 3);
    fields.get("damageCooldown").set(WeaponType.ATA_Laser_Battery_Hyperion, 22);
    fields.get("damageFactor").set(WeaponType.ATA_Laser_Battery_Hyperion, 1);
    fields
        .get("upgradeType")
        .set(WeaponType.ATA_Laser_Battery_Hyperion, UpgradeType.Terran_Ship_Weapons);
    fields.get("damageType").set(WeaponType.ATA_Laser_Battery_Hyperion, DamageType.Normal);
    fields.get("explosionType").set(WeaponType.ATA_Laser_Battery_Hyperion, ExplosionType.Normal);
    fields.get("minRange").set(WeaponType.ATA_Laser_Battery_Hyperion, 0);
    fields.get("maxRange").set(WeaponType.ATA_Laser_Battery_Hyperion, 192);
    fields.get("innerSplashRadius").set(WeaponType.ATA_Laser_Battery_Hyperion, 0);
    fields.get("medianSplashRadius").set(WeaponType.ATA_Laser_Battery_Hyperion, 0);
    fields.get("outerSplashRadius").set(WeaponType.ATA_Laser_Battery_Hyperion, 0);
    fields.get("targetsAir").set(WeaponType.ATA_Laser_Battery_Hyperion, true);
    fields.get("targetsGround").set(WeaponType.ATA_Laser_Battery_Hyperion, false);
    fields.get("targetsMechanical").set(WeaponType.ATA_Laser_Battery_Hyperion, false);
    fields.get("targetsOrganic").set(WeaponType.ATA_Laser_Battery_Hyperion, false);
    fields.get("targetsNonBuilding").set(WeaponType.ATA_Laser_Battery_Hyperion, false);
    fields.get("targetsNonRobotic").set(WeaponType.ATA_Laser_Battery_Hyperion, false);
    fields.get("targetsTerrain").set(WeaponType.ATA_Laser_Battery_Hyperion, false);
    fields.get("targetsOrgOrMech").set(WeaponType.ATA_Laser_Battery_Hyperion, false);
    fields.get("targetsOwn").set(WeaponType.ATA_Laser_Battery_Hyperion, false);
  }

  private static void initializeWeaponType_Flame_Thrower() throws Exception {
    Class<?> c = WeaponType.class;
    Map<String, Field> fields =
        Stream.of(c.getDeclaredFields())
            .collect(
                Collectors.toMap(
                    f -> f.getName(),
                    f -> {
                      f.setAccessible(true);
                      return f;
                    }));
    fields.get("iD").set(WeaponType.Flame_Thrower, 25);
    fields.get("tech").set(WeaponType.Flame_Thrower, TechType.None);
    fields.get("whatUses").set(WeaponType.Flame_Thrower, UnitType.Terran_Firebat);
    fields.get("damageAmount").set(WeaponType.Flame_Thrower, 8);
    fields.get("damageBonus").set(WeaponType.Flame_Thrower, 1);
    fields.get("damageCooldown").set(WeaponType.Flame_Thrower, 22);
    fields.get("damageFactor").set(WeaponType.Flame_Thrower, 1);
    fields.get("upgradeType").set(WeaponType.Flame_Thrower, UpgradeType.Terran_Infantry_Weapons);
    fields.get("damageType").set(WeaponType.Flame_Thrower, DamageType.Concussive);
    fields.get("explosionType").set(WeaponType.Flame_Thrower, ExplosionType.Enemy_Splash);
    fields.get("minRange").set(WeaponType.Flame_Thrower, 0);
    fields.get("maxRange").set(WeaponType.Flame_Thrower, 32);
    fields.get("innerSplashRadius").set(WeaponType.Flame_Thrower, 15);
    fields.get("medianSplashRadius").set(WeaponType.Flame_Thrower, 20);
    fields.get("outerSplashRadius").set(WeaponType.Flame_Thrower, 25);
    fields.get("targetsAir").set(WeaponType.Flame_Thrower, false);
    fields.get("targetsGround").set(WeaponType.Flame_Thrower, true);
    fields.get("targetsMechanical").set(WeaponType.Flame_Thrower, false);
    fields.get("targetsOrganic").set(WeaponType.Flame_Thrower, false);
    fields.get("targetsNonBuilding").set(WeaponType.Flame_Thrower, false);
    fields.get("targetsNonRobotic").set(WeaponType.Flame_Thrower, false);
    fields.get("targetsTerrain").set(WeaponType.Flame_Thrower, false);
    fields.get("targetsOrgOrMech").set(WeaponType.Flame_Thrower, false);
    fields.get("targetsOwn").set(WeaponType.Flame_Thrower, false);
  }

  private static void initializeWeaponType_Flame_Thrower_Gui_Montag() throws Exception {
    Class<?> c = WeaponType.class;
    Map<String, Field> fields =
        Stream.of(c.getDeclaredFields())
            .collect(
                Collectors.toMap(
                    f -> f.getName(),
                    f -> {
                      f.setAccessible(true);
                      return f;
                    }));
    fields.get("iD").set(WeaponType.Flame_Thrower_Gui_Montag, 26);
    fields.get("tech").set(WeaponType.Flame_Thrower_Gui_Montag, TechType.None);
    fields.get("whatUses").set(WeaponType.Flame_Thrower_Gui_Montag, UnitType.Hero_Gui_Montag);
    fields.get("damageAmount").set(WeaponType.Flame_Thrower_Gui_Montag, 16);
    fields.get("damageBonus").set(WeaponType.Flame_Thrower_Gui_Montag, 1);
    fields.get("damageCooldown").set(WeaponType.Flame_Thrower_Gui_Montag, 22);
    fields.get("damageFactor").set(WeaponType.Flame_Thrower_Gui_Montag, 1);
    fields
        .get("upgradeType")
        .set(WeaponType.Flame_Thrower_Gui_Montag, UpgradeType.Terran_Infantry_Weapons);
    fields.get("damageType").set(WeaponType.Flame_Thrower_Gui_Montag, DamageType.Concussive);
    fields
        .get("explosionType")
        .set(WeaponType.Flame_Thrower_Gui_Montag, ExplosionType.Enemy_Splash);
    fields.get("minRange").set(WeaponType.Flame_Thrower_Gui_Montag, 0);
    fields.get("maxRange").set(WeaponType.Flame_Thrower_Gui_Montag, 32);
    fields.get("innerSplashRadius").set(WeaponType.Flame_Thrower_Gui_Montag, 15);
    fields.get("medianSplashRadius").set(WeaponType.Flame_Thrower_Gui_Montag, 20);
    fields.get("outerSplashRadius").set(WeaponType.Flame_Thrower_Gui_Montag, 25);
    fields.get("targetsAir").set(WeaponType.Flame_Thrower_Gui_Montag, false);
    fields.get("targetsGround").set(WeaponType.Flame_Thrower_Gui_Montag, true);
    fields.get("targetsMechanical").set(WeaponType.Flame_Thrower_Gui_Montag, false);
    fields.get("targetsOrganic").set(WeaponType.Flame_Thrower_Gui_Montag, false);
    fields.get("targetsNonBuilding").set(WeaponType.Flame_Thrower_Gui_Montag, false);
    fields.get("targetsNonRobotic").set(WeaponType.Flame_Thrower_Gui_Montag, false);
    fields.get("targetsTerrain").set(WeaponType.Flame_Thrower_Gui_Montag, false);
    fields.get("targetsOrgOrMech").set(WeaponType.Flame_Thrower_Gui_Montag, false);
    fields.get("targetsOwn").set(WeaponType.Flame_Thrower_Gui_Montag, false);
  }

  private static void initializeWeaponType_Arclite_Shock_Cannon() throws Exception {
    Class<?> c = WeaponType.class;
    Map<String, Field> fields =
        Stream.of(c.getDeclaredFields())
            .collect(
                Collectors.toMap(
                    f -> f.getName(),
                    f -> {
                      f.setAccessible(true);
                      return f;
                    }));
    fields.get("iD").set(WeaponType.Arclite_Shock_Cannon, 27);
    fields.get("tech").set(WeaponType.Arclite_Shock_Cannon, TechType.None);
    fields
        .get("whatUses")
        .set(WeaponType.Arclite_Shock_Cannon, UnitType.Terran_Siege_Tank_Siege_Mode);
    fields.get("damageAmount").set(WeaponType.Arclite_Shock_Cannon, 70);
    fields.get("damageBonus").set(WeaponType.Arclite_Shock_Cannon, 5);
    fields.get("damageCooldown").set(WeaponType.Arclite_Shock_Cannon, 75);
    fields.get("damageFactor").set(WeaponType.Arclite_Shock_Cannon, 1);
    fields
        .get("upgradeType")
        .set(WeaponType.Arclite_Shock_Cannon, UpgradeType.Terran_Vehicle_Weapons);
    fields.get("damageType").set(WeaponType.Arclite_Shock_Cannon, DamageType.Explosive);
    fields.get("explosionType").set(WeaponType.Arclite_Shock_Cannon, ExplosionType.Radial_Splash);
    fields.get("minRange").set(WeaponType.Arclite_Shock_Cannon, 64);
    fields.get("maxRange").set(WeaponType.Arclite_Shock_Cannon, 384);
    fields.get("innerSplashRadius").set(WeaponType.Arclite_Shock_Cannon, 10);
    fields.get("medianSplashRadius").set(WeaponType.Arclite_Shock_Cannon, 25);
    fields.get("outerSplashRadius").set(WeaponType.Arclite_Shock_Cannon, 40);
    fields.get("targetsAir").set(WeaponType.Arclite_Shock_Cannon, false);
    fields.get("targetsGround").set(WeaponType.Arclite_Shock_Cannon, true);
    fields.get("targetsMechanical").set(WeaponType.Arclite_Shock_Cannon, false);
    fields.get("targetsOrganic").set(WeaponType.Arclite_Shock_Cannon, false);
    fields.get("targetsNonBuilding").set(WeaponType.Arclite_Shock_Cannon, false);
    fields.get("targetsNonRobotic").set(WeaponType.Arclite_Shock_Cannon, false);
    fields.get("targetsTerrain").set(WeaponType.Arclite_Shock_Cannon, false);
    fields.get("targetsOrgOrMech").set(WeaponType.Arclite_Shock_Cannon, false);
    fields.get("targetsOwn").set(WeaponType.Arclite_Shock_Cannon, false);
  }

  private static void initializeWeaponType_Arclite_Shock_Cannon_Edmund_Duke() throws Exception {
    Class<?> c = WeaponType.class;
    Map<String, Field> fields =
        Stream.of(c.getDeclaredFields())
            .collect(
                Collectors.toMap(
                    f -> f.getName(),
                    f -> {
                      f.setAccessible(true);
                      return f;
                    }));
    fields.get("iD").set(WeaponType.Arclite_Shock_Cannon_Edmund_Duke, 28);
    fields.get("tech").set(WeaponType.Arclite_Shock_Cannon_Edmund_Duke, TechType.None);
    fields
        .get("whatUses")
        .set(WeaponType.Arclite_Shock_Cannon_Edmund_Duke, UnitType.Hero_Edmund_Duke_Siege_Mode);
    fields.get("damageAmount").set(WeaponType.Arclite_Shock_Cannon_Edmund_Duke, 150);
    fields.get("damageBonus").set(WeaponType.Arclite_Shock_Cannon_Edmund_Duke, 5);
    fields.get("damageCooldown").set(WeaponType.Arclite_Shock_Cannon_Edmund_Duke, 75);
    fields.get("damageFactor").set(WeaponType.Arclite_Shock_Cannon_Edmund_Duke, 1);
    fields
        .get("upgradeType")
        .set(WeaponType.Arclite_Shock_Cannon_Edmund_Duke, UpgradeType.Terran_Vehicle_Weapons);
    fields.get("damageType").set(WeaponType.Arclite_Shock_Cannon_Edmund_Duke, DamageType.Explosive);
    fields
        .get("explosionType")
        .set(WeaponType.Arclite_Shock_Cannon_Edmund_Duke, ExplosionType.Radial_Splash);
    fields.get("minRange").set(WeaponType.Arclite_Shock_Cannon_Edmund_Duke, 64);
    fields.get("maxRange").set(WeaponType.Arclite_Shock_Cannon_Edmund_Duke, 384);
    fields.get("innerSplashRadius").set(WeaponType.Arclite_Shock_Cannon_Edmund_Duke, 10);
    fields.get("medianSplashRadius").set(WeaponType.Arclite_Shock_Cannon_Edmund_Duke, 25);
    fields.get("outerSplashRadius").set(WeaponType.Arclite_Shock_Cannon_Edmund_Duke, 40);
    fields.get("targetsAir").set(WeaponType.Arclite_Shock_Cannon_Edmund_Duke, false);
    fields.get("targetsGround").set(WeaponType.Arclite_Shock_Cannon_Edmund_Duke, true);
    fields.get("targetsMechanical").set(WeaponType.Arclite_Shock_Cannon_Edmund_Duke, false);
    fields.get("targetsOrganic").set(WeaponType.Arclite_Shock_Cannon_Edmund_Duke, false);
    fields.get("targetsNonBuilding").set(WeaponType.Arclite_Shock_Cannon_Edmund_Duke, false);
    fields.get("targetsNonRobotic").set(WeaponType.Arclite_Shock_Cannon_Edmund_Duke, false);
    fields.get("targetsTerrain").set(WeaponType.Arclite_Shock_Cannon_Edmund_Duke, false);
    fields.get("targetsOrgOrMech").set(WeaponType.Arclite_Shock_Cannon_Edmund_Duke, false);
    fields.get("targetsOwn").set(WeaponType.Arclite_Shock_Cannon_Edmund_Duke, false);
  }

  private static void initializeWeaponType_Longbolt_Missile() throws Exception {
    Class<?> c = WeaponType.class;
    Map<String, Field> fields =
        Stream.of(c.getDeclaredFields())
            .collect(
                Collectors.toMap(
                    f -> f.getName(),
                    f -> {
                      f.setAccessible(true);
                      return f;
                    }));
    fields.get("iD").set(WeaponType.Longbolt_Missile, 29);
    fields.get("tech").set(WeaponType.Longbolt_Missile, TechType.None);
    fields.get("whatUses").set(WeaponType.Longbolt_Missile, UnitType.Terran_Missile_Turret);
    fields.get("damageAmount").set(WeaponType.Longbolt_Missile, 20);
    fields.get("damageBonus").set(WeaponType.Longbolt_Missile, 0);
    fields.get("damageCooldown").set(WeaponType.Longbolt_Missile, 15);
    fields.get("damageFactor").set(WeaponType.Longbolt_Missile, 1);
    fields.get("upgradeType").set(WeaponType.Longbolt_Missile, UpgradeType.Upgrade_60);
    fields.get("damageType").set(WeaponType.Longbolt_Missile, DamageType.Explosive);
    fields.get("explosionType").set(WeaponType.Longbolt_Missile, ExplosionType.Normal);
    fields.get("minRange").set(WeaponType.Longbolt_Missile, 0);
    fields.get("maxRange").set(WeaponType.Longbolt_Missile, 224);
    fields.get("innerSplashRadius").set(WeaponType.Longbolt_Missile, 0);
    fields.get("medianSplashRadius").set(WeaponType.Longbolt_Missile, 0);
    fields.get("outerSplashRadius").set(WeaponType.Longbolt_Missile, 0);
    fields.get("targetsAir").set(WeaponType.Longbolt_Missile, true);
    fields.get("targetsGround").set(WeaponType.Longbolt_Missile, false);
    fields.get("targetsMechanical").set(WeaponType.Longbolt_Missile, false);
    fields.get("targetsOrganic").set(WeaponType.Longbolt_Missile, false);
    fields.get("targetsNonBuilding").set(WeaponType.Longbolt_Missile, false);
    fields.get("targetsNonRobotic").set(WeaponType.Longbolt_Missile, false);
    fields.get("targetsTerrain").set(WeaponType.Longbolt_Missile, false);
    fields.get("targetsOrgOrMech").set(WeaponType.Longbolt_Missile, false);
    fields.get("targetsOwn").set(WeaponType.Longbolt_Missile, false);
  }

  private static void initializeWeaponType_Yamato_Gun() throws Exception {
    Class<?> c = WeaponType.class;
    Map<String, Field> fields =
        Stream.of(c.getDeclaredFields())
            .collect(
                Collectors.toMap(
                    f -> f.getName(),
                    f -> {
                      f.setAccessible(true);
                      return f;
                    }));
    fields.get("iD").set(WeaponType.Yamato_Gun, 30);
    fields.get("tech").set(WeaponType.Yamato_Gun, TechType.Yamato_Gun);
    fields.get("whatUses").set(WeaponType.Yamato_Gun, UnitType.Terran_Battlecruiser);
    fields.get("damageAmount").set(WeaponType.Yamato_Gun, 260);
    fields.get("damageBonus").set(WeaponType.Yamato_Gun, 0);
    fields.get("damageCooldown").set(WeaponType.Yamato_Gun, 15);
    fields.get("damageFactor").set(WeaponType.Yamato_Gun, 1);
    fields.get("upgradeType").set(WeaponType.Yamato_Gun, UpgradeType.Upgrade_60);
    fields.get("damageType").set(WeaponType.Yamato_Gun, DamageType.Explosive);
    fields.get("explosionType").set(WeaponType.Yamato_Gun, ExplosionType.Yamato_Gun);
    fields.get("minRange").set(WeaponType.Yamato_Gun, 0);
    fields.get("maxRange").set(WeaponType.Yamato_Gun, 320);
    fields.get("innerSplashRadius").set(WeaponType.Yamato_Gun, 0);
    fields.get("medianSplashRadius").set(WeaponType.Yamato_Gun, 0);
    fields.get("outerSplashRadius").set(WeaponType.Yamato_Gun, 0);
    fields.get("targetsAir").set(WeaponType.Yamato_Gun, true);
    fields.get("targetsGround").set(WeaponType.Yamato_Gun, true);
    fields.get("targetsMechanical").set(WeaponType.Yamato_Gun, false);
    fields.get("targetsOrganic").set(WeaponType.Yamato_Gun, false);
    fields.get("targetsNonBuilding").set(WeaponType.Yamato_Gun, false);
    fields.get("targetsNonRobotic").set(WeaponType.Yamato_Gun, false);
    fields.get("targetsTerrain").set(WeaponType.Yamato_Gun, false);
    fields.get("targetsOrgOrMech").set(WeaponType.Yamato_Gun, false);
    fields.get("targetsOwn").set(WeaponType.Yamato_Gun, false);
  }

  private static void initializeWeaponType_Nuclear_Strike() throws Exception {
    Class<?> c = WeaponType.class;
    Map<String, Field> fields =
        Stream.of(c.getDeclaredFields())
            .collect(
                Collectors.toMap(
                    f -> f.getName(),
                    f -> {
                      f.setAccessible(true);
                      return f;
                    }));
    fields.get("iD").set(WeaponType.Nuclear_Strike, 31);
    fields.get("tech").set(WeaponType.Nuclear_Strike, TechType.Nuclear_Strike);
    fields.get("whatUses").set(WeaponType.Nuclear_Strike, UnitType.Terran_Ghost);
    fields.get("damageAmount").set(WeaponType.Nuclear_Strike, 600);
    fields.get("damageBonus").set(WeaponType.Nuclear_Strike, 0);
    fields.get("damageCooldown").set(WeaponType.Nuclear_Strike, 1);
    fields.get("damageFactor").set(WeaponType.Nuclear_Strike, 1);
    fields.get("upgradeType").set(WeaponType.Nuclear_Strike, UpgradeType.Upgrade_60);
    fields.get("damageType").set(WeaponType.Nuclear_Strike, DamageType.Explosive);
    fields.get("explosionType").set(WeaponType.Nuclear_Strike, ExplosionType.Nuclear_Missile);
    fields.get("minRange").set(WeaponType.Nuclear_Strike, 0);
    fields.get("maxRange").set(WeaponType.Nuclear_Strike, 3);
    fields.get("innerSplashRadius").set(WeaponType.Nuclear_Strike, 128);
    fields.get("medianSplashRadius").set(WeaponType.Nuclear_Strike, 192);
    fields.get("outerSplashRadius").set(WeaponType.Nuclear_Strike, 256);
    fields.get("targetsAir").set(WeaponType.Nuclear_Strike, true);
    fields.get("targetsGround").set(WeaponType.Nuclear_Strike, true);
    fields.get("targetsMechanical").set(WeaponType.Nuclear_Strike, false);
    fields.get("targetsOrganic").set(WeaponType.Nuclear_Strike, false);
    fields.get("targetsNonBuilding").set(WeaponType.Nuclear_Strike, false);
    fields.get("targetsNonRobotic").set(WeaponType.Nuclear_Strike, false);
    fields.get("targetsTerrain").set(WeaponType.Nuclear_Strike, false);
    fields.get("targetsOrgOrMech").set(WeaponType.Nuclear_Strike, false);
    fields.get("targetsOwn").set(WeaponType.Nuclear_Strike, false);
  }

  private static void initializeWeaponType_Lockdown() throws Exception {
    Class<?> c = WeaponType.class;
    Map<String, Field> fields =
        Stream.of(c.getDeclaredFields())
            .collect(
                Collectors.toMap(
                    f -> f.getName(),
                    f -> {
                      f.setAccessible(true);
                      return f;
                    }));
    fields.get("iD").set(WeaponType.Lockdown, 32);
    fields.get("tech").set(WeaponType.Lockdown, TechType.Lockdown);
    fields.get("whatUses").set(WeaponType.Lockdown, UnitType.Terran_Ghost);
    fields.get("damageAmount").set(WeaponType.Lockdown, 0);
    fields.get("damageBonus").set(WeaponType.Lockdown, 0);
    fields.get("damageCooldown").set(WeaponType.Lockdown, 1);
    fields.get("damageFactor").set(WeaponType.Lockdown, 1);
    fields.get("upgradeType").set(WeaponType.Lockdown, UpgradeType.Upgrade_60);
    fields.get("damageType").set(WeaponType.Lockdown, DamageType.Concussive);
    fields.get("explosionType").set(WeaponType.Lockdown, ExplosionType.Lockdown);
    fields.get("minRange").set(WeaponType.Lockdown, 0);
    fields.get("maxRange").set(WeaponType.Lockdown, 256);
    fields.get("innerSplashRadius").set(WeaponType.Lockdown, 0);
    fields.get("medianSplashRadius").set(WeaponType.Lockdown, 0);
    fields.get("outerSplashRadius").set(WeaponType.Lockdown, 0);
    fields.get("targetsAir").set(WeaponType.Lockdown, true);
    fields.get("targetsGround").set(WeaponType.Lockdown, true);
    fields.get("targetsMechanical").set(WeaponType.Lockdown, true);
    fields.get("targetsOrganic").set(WeaponType.Lockdown, false);
    fields.get("targetsNonBuilding").set(WeaponType.Lockdown, true);
    fields.get("targetsNonRobotic").set(WeaponType.Lockdown, false);
    fields.get("targetsTerrain").set(WeaponType.Lockdown, false);
    fields.get("targetsOrgOrMech").set(WeaponType.Lockdown, false);
    fields.get("targetsOwn").set(WeaponType.Lockdown, false);
  }

  private static void initializeWeaponType_EMP_Shockwave() throws Exception {
    Class<?> c = WeaponType.class;
    Map<String, Field> fields =
        Stream.of(c.getDeclaredFields())
            .collect(
                Collectors.toMap(
                    f -> f.getName(),
                    f -> {
                      f.setAccessible(true);
                      return f;
                    }));
    fields.get("iD").set(WeaponType.EMP_Shockwave, 33);
    fields.get("tech").set(WeaponType.EMP_Shockwave, TechType.EMP_Shockwave);
    fields.get("whatUses").set(WeaponType.EMP_Shockwave, UnitType.Terran_Science_Vessel);
    fields.get("damageAmount").set(WeaponType.EMP_Shockwave, 0);
    fields.get("damageBonus").set(WeaponType.EMP_Shockwave, 0);
    fields.get("damageCooldown").set(WeaponType.EMP_Shockwave, 1);
    fields.get("damageFactor").set(WeaponType.EMP_Shockwave, 1);
    fields.get("upgradeType").set(WeaponType.EMP_Shockwave, UpgradeType.Upgrade_60);
    fields.get("damageType").set(WeaponType.EMP_Shockwave, DamageType.Concussive);
    fields.get("explosionType").set(WeaponType.EMP_Shockwave, ExplosionType.EMP_Shockwave);
    fields.get("minRange").set(WeaponType.EMP_Shockwave, 0);
    fields.get("maxRange").set(WeaponType.EMP_Shockwave, 256);
    fields.get("innerSplashRadius").set(WeaponType.EMP_Shockwave, 64);
    fields.get("medianSplashRadius").set(WeaponType.EMP_Shockwave, 64);
    fields.get("outerSplashRadius").set(WeaponType.EMP_Shockwave, 64);
    fields.get("targetsAir").set(WeaponType.EMP_Shockwave, true);
    fields.get("targetsGround").set(WeaponType.EMP_Shockwave, true);
    fields.get("targetsMechanical").set(WeaponType.EMP_Shockwave, false);
    fields.get("targetsOrganic").set(WeaponType.EMP_Shockwave, false);
    fields.get("targetsNonBuilding").set(WeaponType.EMP_Shockwave, false);
    fields.get("targetsNonRobotic").set(WeaponType.EMP_Shockwave, false);
    fields.get("targetsTerrain").set(WeaponType.EMP_Shockwave, true);
    fields.get("targetsOrgOrMech").set(WeaponType.EMP_Shockwave, false);
    fields.get("targetsOwn").set(WeaponType.EMP_Shockwave, false);
  }

  private static void initializeWeaponType_Irradiate() throws Exception {
    Class<?> c = WeaponType.class;
    Map<String, Field> fields =
        Stream.of(c.getDeclaredFields())
            .collect(
                Collectors.toMap(
                    f -> f.getName(),
                    f -> {
                      f.setAccessible(true);
                      return f;
                    }));
    fields.get("iD").set(WeaponType.Irradiate, 34);
    fields.get("tech").set(WeaponType.Irradiate, TechType.Irradiate);
    fields.get("whatUses").set(WeaponType.Irradiate, UnitType.Terran_Science_Vessel);
    fields.get("damageAmount").set(WeaponType.Irradiate, 250);
    fields.get("damageBonus").set(WeaponType.Irradiate, 0);
    fields.get("damageCooldown").set(WeaponType.Irradiate, 75);
    fields.get("damageFactor").set(WeaponType.Irradiate, 1);
    fields.get("upgradeType").set(WeaponType.Irradiate, UpgradeType.Upgrade_60);
    fields.get("damageType").set(WeaponType.Irradiate, DamageType.Ignore_Armor);
    fields.get("explosionType").set(WeaponType.Irradiate, ExplosionType.Irradiate);
    fields.get("minRange").set(WeaponType.Irradiate, 0);
    fields.get("maxRange").set(WeaponType.Irradiate, 288);
    fields.get("innerSplashRadius").set(WeaponType.Irradiate, 0);
    fields.get("medianSplashRadius").set(WeaponType.Irradiate, 0);
    fields.get("outerSplashRadius").set(WeaponType.Irradiate, 0);
    fields.get("targetsAir").set(WeaponType.Irradiate, true);
    fields.get("targetsGround").set(WeaponType.Irradiate, true);
    fields.get("targetsMechanical").set(WeaponType.Irradiate, false);
    fields.get("targetsOrganic").set(WeaponType.Irradiate, false);
    fields.get("targetsNonBuilding").set(WeaponType.Irradiate, false);
    fields.get("targetsNonRobotic").set(WeaponType.Irradiate, false);
    fields.get("targetsTerrain").set(WeaponType.Irradiate, true);
    fields.get("targetsOrgOrMech").set(WeaponType.Irradiate, false);
    fields.get("targetsOwn").set(WeaponType.Irradiate, false);
  }

  private static void initializeWeaponType_Claws() throws Exception {
    Class<?> c = WeaponType.class;
    Map<String, Field> fields =
        Stream.of(c.getDeclaredFields())
            .collect(
                Collectors.toMap(
                    f -> f.getName(),
                    f -> {
                      f.setAccessible(true);
                      return f;
                    }));
    fields.get("iD").set(WeaponType.Claws, 35);
    fields.get("tech").set(WeaponType.Claws, TechType.None);
    fields.get("whatUses").set(WeaponType.Claws, UnitType.Zerg_Zergling);
    fields.get("damageAmount").set(WeaponType.Claws, 5);
    fields.get("damageBonus").set(WeaponType.Claws, 1);
    fields.get("damageCooldown").set(WeaponType.Claws, 8);
    fields.get("damageFactor").set(WeaponType.Claws, 1);
    fields.get("upgradeType").set(WeaponType.Claws, UpgradeType.Zerg_Melee_Attacks);
    fields.get("damageType").set(WeaponType.Claws, DamageType.Normal);
    fields.get("explosionType").set(WeaponType.Claws, ExplosionType.Normal);
    fields.get("minRange").set(WeaponType.Claws, 0);
    fields.get("maxRange").set(WeaponType.Claws, 15);
    fields.get("innerSplashRadius").set(WeaponType.Claws, 0);
    fields.get("medianSplashRadius").set(WeaponType.Claws, 0);
    fields.get("outerSplashRadius").set(WeaponType.Claws, 0);
    fields.get("targetsAir").set(WeaponType.Claws, false);
    fields.get("targetsGround").set(WeaponType.Claws, true);
    fields.get("targetsMechanical").set(WeaponType.Claws, false);
    fields.get("targetsOrganic").set(WeaponType.Claws, false);
    fields.get("targetsNonBuilding").set(WeaponType.Claws, false);
    fields.get("targetsNonRobotic").set(WeaponType.Claws, false);
    fields.get("targetsTerrain").set(WeaponType.Claws, false);
    fields.get("targetsOrgOrMech").set(WeaponType.Claws, false);
    fields.get("targetsOwn").set(WeaponType.Claws, false);
  }

  private static void initializeWeaponType_Claws_Devouring_One() throws Exception {
    Class<?> c = WeaponType.class;
    Map<String, Field> fields =
        Stream.of(c.getDeclaredFields())
            .collect(
                Collectors.toMap(
                    f -> f.getName(),
                    f -> {
                      f.setAccessible(true);
                      return f;
                    }));
    fields.get("iD").set(WeaponType.Claws_Devouring_One, 36);
    fields.get("tech").set(WeaponType.Claws_Devouring_One, TechType.None);
    fields.get("whatUses").set(WeaponType.Claws_Devouring_One, UnitType.Hero_Devouring_One);
    fields.get("damageAmount").set(WeaponType.Claws_Devouring_One, 10);
    fields.get("damageBonus").set(WeaponType.Claws_Devouring_One, 1);
    fields.get("damageCooldown").set(WeaponType.Claws_Devouring_One, 8);
    fields.get("damageFactor").set(WeaponType.Claws_Devouring_One, 1);
    fields.get("upgradeType").set(WeaponType.Claws_Devouring_One, UpgradeType.Zerg_Melee_Attacks);
    fields.get("damageType").set(WeaponType.Claws_Devouring_One, DamageType.Normal);
    fields.get("explosionType").set(WeaponType.Claws_Devouring_One, ExplosionType.Normal);
    fields.get("minRange").set(WeaponType.Claws_Devouring_One, 0);
    fields.get("maxRange").set(WeaponType.Claws_Devouring_One, 15);
    fields.get("innerSplashRadius").set(WeaponType.Claws_Devouring_One, 0);
    fields.get("medianSplashRadius").set(WeaponType.Claws_Devouring_One, 0);
    fields.get("outerSplashRadius").set(WeaponType.Claws_Devouring_One, 0);
    fields.get("targetsAir").set(WeaponType.Claws_Devouring_One, false);
    fields.get("targetsGround").set(WeaponType.Claws_Devouring_One, true);
    fields.get("targetsMechanical").set(WeaponType.Claws_Devouring_One, false);
    fields.get("targetsOrganic").set(WeaponType.Claws_Devouring_One, false);
    fields.get("targetsNonBuilding").set(WeaponType.Claws_Devouring_One, false);
    fields.get("targetsNonRobotic").set(WeaponType.Claws_Devouring_One, false);
    fields.get("targetsTerrain").set(WeaponType.Claws_Devouring_One, false);
    fields.get("targetsOrgOrMech").set(WeaponType.Claws_Devouring_One, false);
    fields.get("targetsOwn").set(WeaponType.Claws_Devouring_One, false);
  }

  private static void initializeWeaponType_Claws_Infested_Kerrigan() throws Exception {
    Class<?> c = WeaponType.class;
    Map<String, Field> fields =
        Stream.of(c.getDeclaredFields())
            .collect(
                Collectors.toMap(
                    f -> f.getName(),
                    f -> {
                      f.setAccessible(true);
                      return f;
                    }));
    fields.get("iD").set(WeaponType.Claws_Infested_Kerrigan, 37);
    fields.get("tech").set(WeaponType.Claws_Infested_Kerrigan, TechType.None);
    fields.get("whatUses").set(WeaponType.Claws_Infested_Kerrigan, UnitType.Hero_Infested_Kerrigan);
    fields.get("damageAmount").set(WeaponType.Claws_Infested_Kerrigan, 50);
    fields.get("damageBonus").set(WeaponType.Claws_Infested_Kerrigan, 1);
    fields.get("damageCooldown").set(WeaponType.Claws_Infested_Kerrigan, 15);
    fields.get("damageFactor").set(WeaponType.Claws_Infested_Kerrigan, 1);
    fields
        .get("upgradeType")
        .set(WeaponType.Claws_Infested_Kerrigan, UpgradeType.Zerg_Melee_Attacks);
    fields.get("damageType").set(WeaponType.Claws_Infested_Kerrigan, DamageType.Normal);
    fields.get("explosionType").set(WeaponType.Claws_Infested_Kerrigan, ExplosionType.Normal);
    fields.get("minRange").set(WeaponType.Claws_Infested_Kerrigan, 0);
    fields.get("maxRange").set(WeaponType.Claws_Infested_Kerrigan, 15);
    fields.get("innerSplashRadius").set(WeaponType.Claws_Infested_Kerrigan, 0);
    fields.get("medianSplashRadius").set(WeaponType.Claws_Infested_Kerrigan, 0);
    fields.get("outerSplashRadius").set(WeaponType.Claws_Infested_Kerrigan, 0);
    fields.get("targetsAir").set(WeaponType.Claws_Infested_Kerrigan, false);
    fields.get("targetsGround").set(WeaponType.Claws_Infested_Kerrigan, true);
    fields.get("targetsMechanical").set(WeaponType.Claws_Infested_Kerrigan, false);
    fields.get("targetsOrganic").set(WeaponType.Claws_Infested_Kerrigan, false);
    fields.get("targetsNonBuilding").set(WeaponType.Claws_Infested_Kerrigan, false);
    fields.get("targetsNonRobotic").set(WeaponType.Claws_Infested_Kerrigan, false);
    fields.get("targetsTerrain").set(WeaponType.Claws_Infested_Kerrigan, false);
    fields.get("targetsOrgOrMech").set(WeaponType.Claws_Infested_Kerrigan, false);
    fields.get("targetsOwn").set(WeaponType.Claws_Infested_Kerrigan, false);
  }

  private static void initializeWeaponType_Needle_Spines() throws Exception {
    Class<?> c = WeaponType.class;
    Map<String, Field> fields =
        Stream.of(c.getDeclaredFields())
            .collect(
                Collectors.toMap(
                    f -> f.getName(),
                    f -> {
                      f.setAccessible(true);
                      return f;
                    }));
    fields.get("iD").set(WeaponType.Needle_Spines, 38);
    fields.get("tech").set(WeaponType.Needle_Spines, TechType.None);
    fields.get("whatUses").set(WeaponType.Needle_Spines, UnitType.Zerg_Hydralisk);
    fields.get("damageAmount").set(WeaponType.Needle_Spines, 10);
    fields.get("damageBonus").set(WeaponType.Needle_Spines, 1);
    fields.get("damageCooldown").set(WeaponType.Needle_Spines, 15);
    fields.get("damageFactor").set(WeaponType.Needle_Spines, 1);
    fields.get("upgradeType").set(WeaponType.Needle_Spines, UpgradeType.Zerg_Missile_Attacks);
    fields.get("damageType").set(WeaponType.Needle_Spines, DamageType.Explosive);
    fields.get("explosionType").set(WeaponType.Needle_Spines, ExplosionType.Normal);
    fields.get("minRange").set(WeaponType.Needle_Spines, 0);
    fields.get("maxRange").set(WeaponType.Needle_Spines, 128);
    fields.get("innerSplashRadius").set(WeaponType.Needle_Spines, 0);
    fields.get("medianSplashRadius").set(WeaponType.Needle_Spines, 0);
    fields.get("outerSplashRadius").set(WeaponType.Needle_Spines, 0);
    fields.get("targetsAir").set(WeaponType.Needle_Spines, true);
    fields.get("targetsGround").set(WeaponType.Needle_Spines, true);
    fields.get("targetsMechanical").set(WeaponType.Needle_Spines, false);
    fields.get("targetsOrganic").set(WeaponType.Needle_Spines, false);
    fields.get("targetsNonBuilding").set(WeaponType.Needle_Spines, false);
    fields.get("targetsNonRobotic").set(WeaponType.Needle_Spines, false);
    fields.get("targetsTerrain").set(WeaponType.Needle_Spines, false);
    fields.get("targetsOrgOrMech").set(WeaponType.Needle_Spines, false);
    fields.get("targetsOwn").set(WeaponType.Needle_Spines, false);
  }

  private static void initializeWeaponType_Needle_Spines_Hunter_Killer() throws Exception {
    Class<?> c = WeaponType.class;
    Map<String, Field> fields =
        Stream.of(c.getDeclaredFields())
            .collect(
                Collectors.toMap(
                    f -> f.getName(),
                    f -> {
                      f.setAccessible(true);
                      return f;
                    }));
    fields.get("iD").set(WeaponType.Needle_Spines_Hunter_Killer, 39);
    fields.get("tech").set(WeaponType.Needle_Spines_Hunter_Killer, TechType.None);
    fields.get("whatUses").set(WeaponType.Needle_Spines_Hunter_Killer, UnitType.Hero_Hunter_Killer);
    fields.get("damageAmount").set(WeaponType.Needle_Spines_Hunter_Killer, 20);
    fields.get("damageBonus").set(WeaponType.Needle_Spines_Hunter_Killer, 1);
    fields.get("damageCooldown").set(WeaponType.Needle_Spines_Hunter_Killer, 15);
    fields.get("damageFactor").set(WeaponType.Needle_Spines_Hunter_Killer, 1);
    fields
        .get("upgradeType")
        .set(WeaponType.Needle_Spines_Hunter_Killer, UpgradeType.Zerg_Missile_Attacks);
    fields.get("damageType").set(WeaponType.Needle_Spines_Hunter_Killer, DamageType.Explosive);
    fields.get("explosionType").set(WeaponType.Needle_Spines_Hunter_Killer, ExplosionType.Normal);
    fields.get("minRange").set(WeaponType.Needle_Spines_Hunter_Killer, 0);
    fields.get("maxRange").set(WeaponType.Needle_Spines_Hunter_Killer, 160);
    fields.get("innerSplashRadius").set(WeaponType.Needle_Spines_Hunter_Killer, 0);
    fields.get("medianSplashRadius").set(WeaponType.Needle_Spines_Hunter_Killer, 0);
    fields.get("outerSplashRadius").set(WeaponType.Needle_Spines_Hunter_Killer, 0);
    fields.get("targetsAir").set(WeaponType.Needle_Spines_Hunter_Killer, true);
    fields.get("targetsGround").set(WeaponType.Needle_Spines_Hunter_Killer, true);
    fields.get("targetsMechanical").set(WeaponType.Needle_Spines_Hunter_Killer, false);
    fields.get("targetsOrganic").set(WeaponType.Needle_Spines_Hunter_Killer, false);
    fields.get("targetsNonBuilding").set(WeaponType.Needle_Spines_Hunter_Killer, false);
    fields.get("targetsNonRobotic").set(WeaponType.Needle_Spines_Hunter_Killer, false);
    fields.get("targetsTerrain").set(WeaponType.Needle_Spines_Hunter_Killer, false);
    fields.get("targetsOrgOrMech").set(WeaponType.Needle_Spines_Hunter_Killer, false);
    fields.get("targetsOwn").set(WeaponType.Needle_Spines_Hunter_Killer, false);
  }

  private static void initializeWeaponType_Kaiser_Blades() throws Exception {
    Class<?> c = WeaponType.class;
    Map<String, Field> fields =
        Stream.of(c.getDeclaredFields())
            .collect(
                Collectors.toMap(
                    f -> f.getName(),
                    f -> {
                      f.setAccessible(true);
                      return f;
                    }));
    fields.get("iD").set(WeaponType.Kaiser_Blades, 40);
    fields.get("tech").set(WeaponType.Kaiser_Blades, TechType.None);
    fields.get("whatUses").set(WeaponType.Kaiser_Blades, UnitType.Zerg_Ultralisk);
    fields.get("damageAmount").set(WeaponType.Kaiser_Blades, 20);
    fields.get("damageBonus").set(WeaponType.Kaiser_Blades, 3);
    fields.get("damageCooldown").set(WeaponType.Kaiser_Blades, 15);
    fields.get("damageFactor").set(WeaponType.Kaiser_Blades, 1);
    fields.get("upgradeType").set(WeaponType.Kaiser_Blades, UpgradeType.Zerg_Melee_Attacks);
    fields.get("damageType").set(WeaponType.Kaiser_Blades, DamageType.Normal);
    fields.get("explosionType").set(WeaponType.Kaiser_Blades, ExplosionType.Normal);
    fields.get("minRange").set(WeaponType.Kaiser_Blades, 0);
    fields.get("maxRange").set(WeaponType.Kaiser_Blades, 25);
    fields.get("innerSplashRadius").set(WeaponType.Kaiser_Blades, 0);
    fields.get("medianSplashRadius").set(WeaponType.Kaiser_Blades, 0);
    fields.get("outerSplashRadius").set(WeaponType.Kaiser_Blades, 0);
    fields.get("targetsAir").set(WeaponType.Kaiser_Blades, false);
    fields.get("targetsGround").set(WeaponType.Kaiser_Blades, true);
    fields.get("targetsMechanical").set(WeaponType.Kaiser_Blades, false);
    fields.get("targetsOrganic").set(WeaponType.Kaiser_Blades, false);
    fields.get("targetsNonBuilding").set(WeaponType.Kaiser_Blades, false);
    fields.get("targetsNonRobotic").set(WeaponType.Kaiser_Blades, false);
    fields.get("targetsTerrain").set(WeaponType.Kaiser_Blades, false);
    fields.get("targetsOrgOrMech").set(WeaponType.Kaiser_Blades, false);
    fields.get("targetsOwn").set(WeaponType.Kaiser_Blades, false);
  }

  private static void initializeWeaponType_Kaiser_Blades_Torrasque() throws Exception {
    Class<?> c = WeaponType.class;
    Map<String, Field> fields =
        Stream.of(c.getDeclaredFields())
            .collect(
                Collectors.toMap(
                    f -> f.getName(),
                    f -> {
                      f.setAccessible(true);
                      return f;
                    }));
    fields.get("iD").set(WeaponType.Kaiser_Blades_Torrasque, 41);
    fields.get("tech").set(WeaponType.Kaiser_Blades_Torrasque, TechType.None);
    fields.get("whatUses").set(WeaponType.Kaiser_Blades_Torrasque, UnitType.Hero_Torrasque);
    fields.get("damageAmount").set(WeaponType.Kaiser_Blades_Torrasque, 50);
    fields.get("damageBonus").set(WeaponType.Kaiser_Blades_Torrasque, 3);
    fields.get("damageCooldown").set(WeaponType.Kaiser_Blades_Torrasque, 15);
    fields.get("damageFactor").set(WeaponType.Kaiser_Blades_Torrasque, 1);
    fields
        .get("upgradeType")
        .set(WeaponType.Kaiser_Blades_Torrasque, UpgradeType.Zerg_Melee_Attacks);
    fields.get("damageType").set(WeaponType.Kaiser_Blades_Torrasque, DamageType.Normal);
    fields.get("explosionType").set(WeaponType.Kaiser_Blades_Torrasque, ExplosionType.Normal);
    fields.get("minRange").set(WeaponType.Kaiser_Blades_Torrasque, 0);
    fields.get("maxRange").set(WeaponType.Kaiser_Blades_Torrasque, 25);
    fields.get("innerSplashRadius").set(WeaponType.Kaiser_Blades_Torrasque, 0);
    fields.get("medianSplashRadius").set(WeaponType.Kaiser_Blades_Torrasque, 0);
    fields.get("outerSplashRadius").set(WeaponType.Kaiser_Blades_Torrasque, 0);
    fields.get("targetsAir").set(WeaponType.Kaiser_Blades_Torrasque, false);
    fields.get("targetsGround").set(WeaponType.Kaiser_Blades_Torrasque, true);
    fields.get("targetsMechanical").set(WeaponType.Kaiser_Blades_Torrasque, false);
    fields.get("targetsOrganic").set(WeaponType.Kaiser_Blades_Torrasque, false);
    fields.get("targetsNonBuilding").set(WeaponType.Kaiser_Blades_Torrasque, false);
    fields.get("targetsNonRobotic").set(WeaponType.Kaiser_Blades_Torrasque, false);
    fields.get("targetsTerrain").set(WeaponType.Kaiser_Blades_Torrasque, false);
    fields.get("targetsOrgOrMech").set(WeaponType.Kaiser_Blades_Torrasque, false);
    fields.get("targetsOwn").set(WeaponType.Kaiser_Blades_Torrasque, false);
  }

  private static void initializeWeaponType_Toxic_Spores() throws Exception {
    Class<?> c = WeaponType.class;
    Map<String, Field> fields =
        Stream.of(c.getDeclaredFields())
            .collect(
                Collectors.toMap(
                    f -> f.getName(),
                    f -> {
                      f.setAccessible(true);
                      return f;
                    }));
    fields.get("iD").set(WeaponType.Toxic_Spores, 42);
    fields.get("tech").set(WeaponType.Toxic_Spores, TechType.None);
    fields.get("whatUses").set(WeaponType.Toxic_Spores, UnitType.Zerg_Broodling);
    fields.get("damageAmount").set(WeaponType.Toxic_Spores, 4);
    fields.get("damageBonus").set(WeaponType.Toxic_Spores, 1);
    fields.get("damageCooldown").set(WeaponType.Toxic_Spores, 15);
    fields.get("damageFactor").set(WeaponType.Toxic_Spores, 1);
    fields.get("upgradeType").set(WeaponType.Toxic_Spores, UpgradeType.Zerg_Melee_Attacks);
    fields.get("damageType").set(WeaponType.Toxic_Spores, DamageType.Normal);
    fields.get("explosionType").set(WeaponType.Toxic_Spores, ExplosionType.Normal);
    fields.get("minRange").set(WeaponType.Toxic_Spores, 0);
    fields.get("maxRange").set(WeaponType.Toxic_Spores, 2);
    fields.get("innerSplashRadius").set(WeaponType.Toxic_Spores, 0);
    fields.get("medianSplashRadius").set(WeaponType.Toxic_Spores, 0);
    fields.get("outerSplashRadius").set(WeaponType.Toxic_Spores, 0);
    fields.get("targetsAir").set(WeaponType.Toxic_Spores, false);
    fields.get("targetsGround").set(WeaponType.Toxic_Spores, true);
    fields.get("targetsMechanical").set(WeaponType.Toxic_Spores, false);
    fields.get("targetsOrganic").set(WeaponType.Toxic_Spores, false);
    fields.get("targetsNonBuilding").set(WeaponType.Toxic_Spores, false);
    fields.get("targetsNonRobotic").set(WeaponType.Toxic_Spores, false);
    fields.get("targetsTerrain").set(WeaponType.Toxic_Spores, false);
    fields.get("targetsOrgOrMech").set(WeaponType.Toxic_Spores, false);
    fields.get("targetsOwn").set(WeaponType.Toxic_Spores, false);
  }

  private static void initializeWeaponType_Spines() throws Exception {
    Class<?> c = WeaponType.class;
    Map<String, Field> fields =
        Stream.of(c.getDeclaredFields())
            .collect(
                Collectors.toMap(
                    f -> f.getName(),
                    f -> {
                      f.setAccessible(true);
                      return f;
                    }));
    fields.get("iD").set(WeaponType.Spines, 43);
    fields.get("tech").set(WeaponType.Spines, TechType.None);
    fields.get("whatUses").set(WeaponType.Spines, UnitType.Zerg_Drone);
    fields.get("damageAmount").set(WeaponType.Spines, 5);
    fields.get("damageBonus").set(WeaponType.Spines, 0);
    fields.get("damageCooldown").set(WeaponType.Spines, 22);
    fields.get("damageFactor").set(WeaponType.Spines, 1);
    fields.get("upgradeType").set(WeaponType.Spines, UpgradeType.Upgrade_60);
    fields.get("damageType").set(WeaponType.Spines, DamageType.Normal);
    fields.get("explosionType").set(WeaponType.Spines, ExplosionType.Normal);
    fields.get("minRange").set(WeaponType.Spines, 0);
    fields.get("maxRange").set(WeaponType.Spines, 32);
    fields.get("innerSplashRadius").set(WeaponType.Spines, 0);
    fields.get("medianSplashRadius").set(WeaponType.Spines, 0);
    fields.get("outerSplashRadius").set(WeaponType.Spines, 0);
    fields.get("targetsAir").set(WeaponType.Spines, false);
    fields.get("targetsGround").set(WeaponType.Spines, true);
    fields.get("targetsMechanical").set(WeaponType.Spines, false);
    fields.get("targetsOrganic").set(WeaponType.Spines, false);
    fields.get("targetsNonBuilding").set(WeaponType.Spines, false);
    fields.get("targetsNonRobotic").set(WeaponType.Spines, false);
    fields.get("targetsTerrain").set(WeaponType.Spines, false);
    fields.get("targetsOrgOrMech").set(WeaponType.Spines, false);
    fields.get("targetsOwn").set(WeaponType.Spines, false);
  }

  private static void initializeWeaponType_unk_44() throws Exception {
    Class<?> c = WeaponType.class;
    Map<String, Field> fields =
        Stream.of(c.getDeclaredFields())
            .collect(
                Collectors.toMap(
                    f -> f.getName(),
                    f -> {
                      f.setAccessible(true);
                      return f;
                    }));
    fields.get("iD").set(WeaponType.unk_44, 44);
    fields.get("tech").set(WeaponType.unk_44, null);
    fields.get("whatUses").set(WeaponType.unk_44, null);
    fields.get("damageAmount").set(WeaponType.unk_44, 0);
    fields.get("damageBonus").set(WeaponType.unk_44, 0);
    fields.get("damageCooldown").set(WeaponType.unk_44, 0);
    fields.get("damageFactor").set(WeaponType.unk_44, 0);
    fields.get("upgradeType").set(WeaponType.unk_44, null);
    fields.get("damageType").set(WeaponType.unk_44, null);
    fields.get("explosionType").set(WeaponType.unk_44, null);
    fields.get("minRange").set(WeaponType.unk_44, 0);
    fields.get("maxRange").set(WeaponType.unk_44, 0);
    fields.get("innerSplashRadius").set(WeaponType.unk_44, 0);
    fields.get("medianSplashRadius").set(WeaponType.unk_44, 0);
    fields.get("outerSplashRadius").set(WeaponType.unk_44, 0);
    fields.get("targetsAir").set(WeaponType.unk_44, false);
    fields.get("targetsGround").set(WeaponType.unk_44, false);
    fields.get("targetsMechanical").set(WeaponType.unk_44, false);
    fields.get("targetsOrganic").set(WeaponType.unk_44, false);
    fields.get("targetsNonBuilding").set(WeaponType.unk_44, false);
    fields.get("targetsNonRobotic").set(WeaponType.unk_44, false);
    fields.get("targetsTerrain").set(WeaponType.unk_44, false);
    fields.get("targetsOrgOrMech").set(WeaponType.unk_44, false);
    fields.get("targetsOwn").set(WeaponType.unk_44, false);
  }

  private static void initializeWeaponType_unk_45() throws Exception {
    Class<?> c = WeaponType.class;
    Map<String, Field> fields =
        Stream.of(c.getDeclaredFields())
            .collect(
                Collectors.toMap(
                    f -> f.getName(),
                    f -> {
                      f.setAccessible(true);
                      return f;
                    }));
    fields.get("iD").set(WeaponType.unk_45, 45);
    fields.get("tech").set(WeaponType.unk_45, null);
    fields.get("whatUses").set(WeaponType.unk_45, null);
    fields.get("damageAmount").set(WeaponType.unk_45, 0);
    fields.get("damageBonus").set(WeaponType.unk_45, 0);
    fields.get("damageCooldown").set(WeaponType.unk_45, 0);
    fields.get("damageFactor").set(WeaponType.unk_45, 0);
    fields.get("upgradeType").set(WeaponType.unk_45, null);
    fields.get("damageType").set(WeaponType.unk_45, null);
    fields.get("explosionType").set(WeaponType.unk_45, null);
    fields.get("minRange").set(WeaponType.unk_45, 0);
    fields.get("maxRange").set(WeaponType.unk_45, 0);
    fields.get("innerSplashRadius").set(WeaponType.unk_45, 0);
    fields.get("medianSplashRadius").set(WeaponType.unk_45, 0);
    fields.get("outerSplashRadius").set(WeaponType.unk_45, 0);
    fields.get("targetsAir").set(WeaponType.unk_45, false);
    fields.get("targetsGround").set(WeaponType.unk_45, false);
    fields.get("targetsMechanical").set(WeaponType.unk_45, false);
    fields.get("targetsOrganic").set(WeaponType.unk_45, false);
    fields.get("targetsNonBuilding").set(WeaponType.unk_45, false);
    fields.get("targetsNonRobotic").set(WeaponType.unk_45, false);
    fields.get("targetsTerrain").set(WeaponType.unk_45, false);
    fields.get("targetsOrgOrMech").set(WeaponType.unk_45, false);
    fields.get("targetsOwn").set(WeaponType.unk_45, false);
  }

  private static void initializeWeaponType_Acid_Spore() throws Exception {
    Class<?> c = WeaponType.class;
    Map<String, Field> fields =
        Stream.of(c.getDeclaredFields())
            .collect(
                Collectors.toMap(
                    f -> f.getName(),
                    f -> {
                      f.setAccessible(true);
                      return f;
                    }));
    fields.get("iD").set(WeaponType.Acid_Spore, 46);
    fields.get("tech").set(WeaponType.Acid_Spore, TechType.None);
    fields.get("whatUses").set(WeaponType.Acid_Spore, UnitType.Zerg_Guardian);
    fields.get("damageAmount").set(WeaponType.Acid_Spore, 20);
    fields.get("damageBonus").set(WeaponType.Acid_Spore, 2);
    fields.get("damageCooldown").set(WeaponType.Acid_Spore, 30);
    fields.get("damageFactor").set(WeaponType.Acid_Spore, 1);
    fields.get("upgradeType").set(WeaponType.Acid_Spore, UpgradeType.Zerg_Flyer_Attacks);
    fields.get("damageType").set(WeaponType.Acid_Spore, DamageType.Normal);
    fields.get("explosionType").set(WeaponType.Acid_Spore, ExplosionType.Normal);
    fields.get("minRange").set(WeaponType.Acid_Spore, 0);
    fields.get("maxRange").set(WeaponType.Acid_Spore, 256);
    fields.get("innerSplashRadius").set(WeaponType.Acid_Spore, 0);
    fields.get("medianSplashRadius").set(WeaponType.Acid_Spore, 0);
    fields.get("outerSplashRadius").set(WeaponType.Acid_Spore, 0);
    fields.get("targetsAir").set(WeaponType.Acid_Spore, false);
    fields.get("targetsGround").set(WeaponType.Acid_Spore, true);
    fields.get("targetsMechanical").set(WeaponType.Acid_Spore, false);
    fields.get("targetsOrganic").set(WeaponType.Acid_Spore, false);
    fields.get("targetsNonBuilding").set(WeaponType.Acid_Spore, false);
    fields.get("targetsNonRobotic").set(WeaponType.Acid_Spore, false);
    fields.get("targetsTerrain").set(WeaponType.Acid_Spore, false);
    fields.get("targetsOrgOrMech").set(WeaponType.Acid_Spore, false);
    fields.get("targetsOwn").set(WeaponType.Acid_Spore, false);
  }

  private static void initializeWeaponType_Acid_Spore_Kukulza() throws Exception {
    Class<?> c = WeaponType.class;
    Map<String, Field> fields =
        Stream.of(c.getDeclaredFields())
            .collect(
                Collectors.toMap(
                    f -> f.getName(),
                    f -> {
                      f.setAccessible(true);
                      return f;
                    }));
    fields.get("iD").set(WeaponType.Acid_Spore_Kukulza, 47);
    fields.get("tech").set(WeaponType.Acid_Spore_Kukulza, TechType.None);
    fields.get("whatUses").set(WeaponType.Acid_Spore_Kukulza, UnitType.Hero_Kukulza_Guardian);
    fields.get("damageAmount").set(WeaponType.Acid_Spore_Kukulza, 40);
    fields.get("damageBonus").set(WeaponType.Acid_Spore_Kukulza, 2);
    fields.get("damageCooldown").set(WeaponType.Acid_Spore_Kukulza, 30);
    fields.get("damageFactor").set(WeaponType.Acid_Spore_Kukulza, 1);
    fields.get("upgradeType").set(WeaponType.Acid_Spore_Kukulza, UpgradeType.Zerg_Flyer_Attacks);
    fields.get("damageType").set(WeaponType.Acid_Spore_Kukulza, DamageType.Normal);
    fields.get("explosionType").set(WeaponType.Acid_Spore_Kukulza, ExplosionType.Normal);
    fields.get("minRange").set(WeaponType.Acid_Spore_Kukulza, 0);
    fields.get("maxRange").set(WeaponType.Acid_Spore_Kukulza, 256);
    fields.get("innerSplashRadius").set(WeaponType.Acid_Spore_Kukulza, 0);
    fields.get("medianSplashRadius").set(WeaponType.Acid_Spore_Kukulza, 0);
    fields.get("outerSplashRadius").set(WeaponType.Acid_Spore_Kukulza, 0);
    fields.get("targetsAir").set(WeaponType.Acid_Spore_Kukulza, false);
    fields.get("targetsGround").set(WeaponType.Acid_Spore_Kukulza, true);
    fields.get("targetsMechanical").set(WeaponType.Acid_Spore_Kukulza, false);
    fields.get("targetsOrganic").set(WeaponType.Acid_Spore_Kukulza, false);
    fields.get("targetsNonBuilding").set(WeaponType.Acid_Spore_Kukulza, false);
    fields.get("targetsNonRobotic").set(WeaponType.Acid_Spore_Kukulza, false);
    fields.get("targetsTerrain").set(WeaponType.Acid_Spore_Kukulza, false);
    fields.get("targetsOrgOrMech").set(WeaponType.Acid_Spore_Kukulza, false);
    fields.get("targetsOwn").set(WeaponType.Acid_Spore_Kukulza, false);
  }

  private static void initializeWeaponType_Glave_Wurm() throws Exception {
    Class<?> c = WeaponType.class;
    Map<String, Field> fields =
        Stream.of(c.getDeclaredFields())
            .collect(
                Collectors.toMap(
                    f -> f.getName(),
                    f -> {
                      f.setAccessible(true);
                      return f;
                    }));
    fields.get("iD").set(WeaponType.Glave_Wurm, 48);
    fields.get("tech").set(WeaponType.Glave_Wurm, TechType.None);
    fields.get("whatUses").set(WeaponType.Glave_Wurm, UnitType.Zerg_Mutalisk);
    fields.get("damageAmount").set(WeaponType.Glave_Wurm, 9);
    fields.get("damageBonus").set(WeaponType.Glave_Wurm, 1);
    fields.get("damageCooldown").set(WeaponType.Glave_Wurm, 30);
    fields.get("damageFactor").set(WeaponType.Glave_Wurm, 1);
    fields.get("upgradeType").set(WeaponType.Glave_Wurm, UpgradeType.Zerg_Flyer_Attacks);
    fields.get("damageType").set(WeaponType.Glave_Wurm, DamageType.Normal);
    fields.get("explosionType").set(WeaponType.Glave_Wurm, ExplosionType.Normal);
    fields.get("minRange").set(WeaponType.Glave_Wurm, 0);
    fields.get("maxRange").set(WeaponType.Glave_Wurm, 96);
    fields.get("innerSplashRadius").set(WeaponType.Glave_Wurm, 0);
    fields.get("medianSplashRadius").set(WeaponType.Glave_Wurm, 0);
    fields.get("outerSplashRadius").set(WeaponType.Glave_Wurm, 0);
    fields.get("targetsAir").set(WeaponType.Glave_Wurm, true);
    fields.get("targetsGround").set(WeaponType.Glave_Wurm, true);
    fields.get("targetsMechanical").set(WeaponType.Glave_Wurm, false);
    fields.get("targetsOrganic").set(WeaponType.Glave_Wurm, false);
    fields.get("targetsNonBuilding").set(WeaponType.Glave_Wurm, false);
    fields.get("targetsNonRobotic").set(WeaponType.Glave_Wurm, false);
    fields.get("targetsTerrain").set(WeaponType.Glave_Wurm, false);
    fields.get("targetsOrgOrMech").set(WeaponType.Glave_Wurm, false);
    fields.get("targetsOwn").set(WeaponType.Glave_Wurm, false);
  }

  private static void initializeWeaponType_Glave_Wurm_Kukulza() throws Exception {
    Class<?> c = WeaponType.class;
    Map<String, Field> fields =
        Stream.of(c.getDeclaredFields())
            .collect(
                Collectors.toMap(
                    f -> f.getName(),
                    f -> {
                      f.setAccessible(true);
                      return f;
                    }));
    fields.get("iD").set(WeaponType.Glave_Wurm_Kukulza, 49);
    fields.get("tech").set(WeaponType.Glave_Wurm_Kukulza, TechType.None);
    fields.get("whatUses").set(WeaponType.Glave_Wurm_Kukulza, UnitType.Hero_Kukulza_Mutalisk);
    fields.get("damageAmount").set(WeaponType.Glave_Wurm_Kukulza, 18);
    fields.get("damageBonus").set(WeaponType.Glave_Wurm_Kukulza, 1);
    fields.get("damageCooldown").set(WeaponType.Glave_Wurm_Kukulza, 30);
    fields.get("damageFactor").set(WeaponType.Glave_Wurm_Kukulza, 1);
    fields.get("upgradeType").set(WeaponType.Glave_Wurm_Kukulza, UpgradeType.Zerg_Flyer_Attacks);
    fields.get("damageType").set(WeaponType.Glave_Wurm_Kukulza, DamageType.Normal);
    fields.get("explosionType").set(WeaponType.Glave_Wurm_Kukulza, ExplosionType.Normal);
    fields.get("minRange").set(WeaponType.Glave_Wurm_Kukulza, 0);
    fields.get("maxRange").set(WeaponType.Glave_Wurm_Kukulza, 96);
    fields.get("innerSplashRadius").set(WeaponType.Glave_Wurm_Kukulza, 0);
    fields.get("medianSplashRadius").set(WeaponType.Glave_Wurm_Kukulza, 0);
    fields.get("outerSplashRadius").set(WeaponType.Glave_Wurm_Kukulza, 0);
    fields.get("targetsAir").set(WeaponType.Glave_Wurm_Kukulza, true);
    fields.get("targetsGround").set(WeaponType.Glave_Wurm_Kukulza, true);
    fields.get("targetsMechanical").set(WeaponType.Glave_Wurm_Kukulza, false);
    fields.get("targetsOrganic").set(WeaponType.Glave_Wurm_Kukulza, false);
    fields.get("targetsNonBuilding").set(WeaponType.Glave_Wurm_Kukulza, false);
    fields.get("targetsNonRobotic").set(WeaponType.Glave_Wurm_Kukulza, false);
    fields.get("targetsTerrain").set(WeaponType.Glave_Wurm_Kukulza, false);
    fields.get("targetsOrgOrMech").set(WeaponType.Glave_Wurm_Kukulza, false);
    fields.get("targetsOwn").set(WeaponType.Glave_Wurm_Kukulza, false);
  }

  private static void initializeWeaponType_unk_50() throws Exception {
    Class<?> c = WeaponType.class;
    Map<String, Field> fields =
        Stream.of(c.getDeclaredFields())
            .collect(
                Collectors.toMap(
                    f -> f.getName(),
                    f -> {
                      f.setAccessible(true);
                      return f;
                    }));
    fields.get("iD").set(WeaponType.unk_50, 50);
    fields.get("tech").set(WeaponType.unk_50, null);
    fields.get("whatUses").set(WeaponType.unk_50, null);
    fields.get("damageAmount").set(WeaponType.unk_50, 0);
    fields.get("damageBonus").set(WeaponType.unk_50, 0);
    fields.get("damageCooldown").set(WeaponType.unk_50, 0);
    fields.get("damageFactor").set(WeaponType.unk_50, 0);
    fields.get("upgradeType").set(WeaponType.unk_50, null);
    fields.get("damageType").set(WeaponType.unk_50, null);
    fields.get("explosionType").set(WeaponType.unk_50, null);
    fields.get("minRange").set(WeaponType.unk_50, 0);
    fields.get("maxRange").set(WeaponType.unk_50, 0);
    fields.get("innerSplashRadius").set(WeaponType.unk_50, 0);
    fields.get("medianSplashRadius").set(WeaponType.unk_50, 0);
    fields.get("outerSplashRadius").set(WeaponType.unk_50, 0);
    fields.get("targetsAir").set(WeaponType.unk_50, false);
    fields.get("targetsGround").set(WeaponType.unk_50, false);
    fields.get("targetsMechanical").set(WeaponType.unk_50, false);
    fields.get("targetsOrganic").set(WeaponType.unk_50, false);
    fields.get("targetsNonBuilding").set(WeaponType.unk_50, false);
    fields.get("targetsNonRobotic").set(WeaponType.unk_50, false);
    fields.get("targetsTerrain").set(WeaponType.unk_50, false);
    fields.get("targetsOrgOrMech").set(WeaponType.unk_50, false);
    fields.get("targetsOwn").set(WeaponType.unk_50, false);
  }

  private static void initializeWeaponType_unk_51() throws Exception {
    Class<?> c = WeaponType.class;
    Map<String, Field> fields =
        Stream.of(c.getDeclaredFields())
            .collect(
                Collectors.toMap(
                    f -> f.getName(),
                    f -> {
                      f.setAccessible(true);
                      return f;
                    }));
    fields.get("iD").set(WeaponType.unk_51, 51);
    fields.get("tech").set(WeaponType.unk_51, null);
    fields.get("whatUses").set(WeaponType.unk_51, null);
    fields.get("damageAmount").set(WeaponType.unk_51, 0);
    fields.get("damageBonus").set(WeaponType.unk_51, 0);
    fields.get("damageCooldown").set(WeaponType.unk_51, 0);
    fields.get("damageFactor").set(WeaponType.unk_51, 0);
    fields.get("upgradeType").set(WeaponType.unk_51, null);
    fields.get("damageType").set(WeaponType.unk_51, null);
    fields.get("explosionType").set(WeaponType.unk_51, null);
    fields.get("minRange").set(WeaponType.unk_51, 0);
    fields.get("maxRange").set(WeaponType.unk_51, 0);
    fields.get("innerSplashRadius").set(WeaponType.unk_51, 0);
    fields.get("medianSplashRadius").set(WeaponType.unk_51, 0);
    fields.get("outerSplashRadius").set(WeaponType.unk_51, 0);
    fields.get("targetsAir").set(WeaponType.unk_51, false);
    fields.get("targetsGround").set(WeaponType.unk_51, false);
    fields.get("targetsMechanical").set(WeaponType.unk_51, false);
    fields.get("targetsOrganic").set(WeaponType.unk_51, false);
    fields.get("targetsNonBuilding").set(WeaponType.unk_51, false);
    fields.get("targetsNonRobotic").set(WeaponType.unk_51, false);
    fields.get("targetsTerrain").set(WeaponType.unk_51, false);
    fields.get("targetsOrgOrMech").set(WeaponType.unk_51, false);
    fields.get("targetsOwn").set(WeaponType.unk_51, false);
  }

  private static void initializeWeaponType_Seeker_Spores() throws Exception {
    Class<?> c = WeaponType.class;
    Map<String, Field> fields =
        Stream.of(c.getDeclaredFields())
            .collect(
                Collectors.toMap(
                    f -> f.getName(),
                    f -> {
                      f.setAccessible(true);
                      return f;
                    }));
    fields.get("iD").set(WeaponType.Seeker_Spores, 52);
    fields.get("tech").set(WeaponType.Seeker_Spores, TechType.None);
    fields.get("whatUses").set(WeaponType.Seeker_Spores, UnitType.Zerg_Spore_Colony);
    fields.get("damageAmount").set(WeaponType.Seeker_Spores, 15);
    fields.get("damageBonus").set(WeaponType.Seeker_Spores, 0);
    fields.get("damageCooldown").set(WeaponType.Seeker_Spores, 15);
    fields.get("damageFactor").set(WeaponType.Seeker_Spores, 1);
    fields.get("upgradeType").set(WeaponType.Seeker_Spores, UpgradeType.Upgrade_60);
    fields.get("damageType").set(WeaponType.Seeker_Spores, DamageType.Normal);
    fields.get("explosionType").set(WeaponType.Seeker_Spores, ExplosionType.Normal);
    fields.get("minRange").set(WeaponType.Seeker_Spores, 0);
    fields.get("maxRange").set(WeaponType.Seeker_Spores, 224);
    fields.get("innerSplashRadius").set(WeaponType.Seeker_Spores, 0);
    fields.get("medianSplashRadius").set(WeaponType.Seeker_Spores, 0);
    fields.get("outerSplashRadius").set(WeaponType.Seeker_Spores, 0);
    fields.get("targetsAir").set(WeaponType.Seeker_Spores, true);
    fields.get("targetsGround").set(WeaponType.Seeker_Spores, false);
    fields.get("targetsMechanical").set(WeaponType.Seeker_Spores, false);
    fields.get("targetsOrganic").set(WeaponType.Seeker_Spores, false);
    fields.get("targetsNonBuilding").set(WeaponType.Seeker_Spores, false);
    fields.get("targetsNonRobotic").set(WeaponType.Seeker_Spores, false);
    fields.get("targetsTerrain").set(WeaponType.Seeker_Spores, false);
    fields.get("targetsOrgOrMech").set(WeaponType.Seeker_Spores, false);
    fields.get("targetsOwn").set(WeaponType.Seeker_Spores, false);
  }

  private static void initializeWeaponType_Subterranean_Tentacle() throws Exception {
    Class<?> c = WeaponType.class;
    Map<String, Field> fields =
        Stream.of(c.getDeclaredFields())
            .collect(
                Collectors.toMap(
                    f -> f.getName(),
                    f -> {
                      f.setAccessible(true);
                      return f;
                    }));
    fields.get("iD").set(WeaponType.Subterranean_Tentacle, 53);
    fields.get("tech").set(WeaponType.Subterranean_Tentacle, TechType.None);
    fields.get("whatUses").set(WeaponType.Subterranean_Tentacle, UnitType.Zerg_Sunken_Colony);
    fields.get("damageAmount").set(WeaponType.Subterranean_Tentacle, 40);
    fields.get("damageBonus").set(WeaponType.Subterranean_Tentacle, 0);
    fields.get("damageCooldown").set(WeaponType.Subterranean_Tentacle, 32);
    fields.get("damageFactor").set(WeaponType.Subterranean_Tentacle, 1);
    fields.get("upgradeType").set(WeaponType.Subterranean_Tentacle, UpgradeType.Upgrade_60);
    fields.get("damageType").set(WeaponType.Subterranean_Tentacle, DamageType.Explosive);
    fields.get("explosionType").set(WeaponType.Subterranean_Tentacle, ExplosionType.Normal);
    fields.get("minRange").set(WeaponType.Subterranean_Tentacle, 0);
    fields.get("maxRange").set(WeaponType.Subterranean_Tentacle, 224);
    fields.get("innerSplashRadius").set(WeaponType.Subterranean_Tentacle, 0);
    fields.get("medianSplashRadius").set(WeaponType.Subterranean_Tentacle, 0);
    fields.get("outerSplashRadius").set(WeaponType.Subterranean_Tentacle, 0);
    fields.get("targetsAir").set(WeaponType.Subterranean_Tentacle, false);
    fields.get("targetsGround").set(WeaponType.Subterranean_Tentacle, true);
    fields.get("targetsMechanical").set(WeaponType.Subterranean_Tentacle, false);
    fields.get("targetsOrganic").set(WeaponType.Subterranean_Tentacle, false);
    fields.get("targetsNonBuilding").set(WeaponType.Subterranean_Tentacle, false);
    fields.get("targetsNonRobotic").set(WeaponType.Subterranean_Tentacle, false);
    fields.get("targetsTerrain").set(WeaponType.Subterranean_Tentacle, false);
    fields.get("targetsOrgOrMech").set(WeaponType.Subterranean_Tentacle, false);
    fields.get("targetsOwn").set(WeaponType.Subterranean_Tentacle, false);
  }

  private static void initializeWeaponType_Suicide_Infested_Terran() throws Exception {
    Class<?> c = WeaponType.class;
    Map<String, Field> fields =
        Stream.of(c.getDeclaredFields())
            .collect(
                Collectors.toMap(
                    f -> f.getName(),
                    f -> {
                      f.setAccessible(true);
                      return f;
                    }));
    fields.get("iD").set(WeaponType.Suicide_Infested_Terran, 54);
    fields.get("tech").set(WeaponType.Suicide_Infested_Terran, TechType.None);
    fields.get("whatUses").set(WeaponType.Suicide_Infested_Terran, UnitType.Zerg_Infested_Terran);
    fields.get("damageAmount").set(WeaponType.Suicide_Infested_Terran, 500);
    fields.get("damageBonus").set(WeaponType.Suicide_Infested_Terran, 0);
    fields.get("damageCooldown").set(WeaponType.Suicide_Infested_Terran, 1);
    fields.get("damageFactor").set(WeaponType.Suicide_Infested_Terran, 1);
    fields.get("upgradeType").set(WeaponType.Suicide_Infested_Terran, UpgradeType.Upgrade_60);
    fields.get("damageType").set(WeaponType.Suicide_Infested_Terran, DamageType.Explosive);
    fields
        .get("explosionType")
        .set(WeaponType.Suicide_Infested_Terran, ExplosionType.Radial_Splash);
    fields.get("minRange").set(WeaponType.Suicide_Infested_Terran, 0);
    fields.get("maxRange").set(WeaponType.Suicide_Infested_Terran, 3);
    fields.get("innerSplashRadius").set(WeaponType.Suicide_Infested_Terran, 20);
    fields.get("medianSplashRadius").set(WeaponType.Suicide_Infested_Terran, 40);
    fields.get("outerSplashRadius").set(WeaponType.Suicide_Infested_Terran, 60);
    fields.get("targetsAir").set(WeaponType.Suicide_Infested_Terran, false);
    fields.get("targetsGround").set(WeaponType.Suicide_Infested_Terran, true);
    fields.get("targetsMechanical").set(WeaponType.Suicide_Infested_Terran, false);
    fields.get("targetsOrganic").set(WeaponType.Suicide_Infested_Terran, false);
    fields.get("targetsNonBuilding").set(WeaponType.Suicide_Infested_Terran, false);
    fields.get("targetsNonRobotic").set(WeaponType.Suicide_Infested_Terran, false);
    fields.get("targetsTerrain").set(WeaponType.Suicide_Infested_Terran, false);
    fields.get("targetsOrgOrMech").set(WeaponType.Suicide_Infested_Terran, false);
    fields.get("targetsOwn").set(WeaponType.Suicide_Infested_Terran, false);
  }

  private static void initializeWeaponType_Suicide_Scourge() throws Exception {
    Class<?> c = WeaponType.class;
    Map<String, Field> fields =
        Stream.of(c.getDeclaredFields())
            .collect(
                Collectors.toMap(
                    f -> f.getName(),
                    f -> {
                      f.setAccessible(true);
                      return f;
                    }));
    fields.get("iD").set(WeaponType.Suicide_Scourge, 55);
    fields.get("tech").set(WeaponType.Suicide_Scourge, TechType.None);
    fields.get("whatUses").set(WeaponType.Suicide_Scourge, UnitType.Zerg_Scourge);
    fields.get("damageAmount").set(WeaponType.Suicide_Scourge, 110);
    fields.get("damageBonus").set(WeaponType.Suicide_Scourge, 0);
    fields.get("damageCooldown").set(WeaponType.Suicide_Scourge, 1);
    fields.get("damageFactor").set(WeaponType.Suicide_Scourge, 1);
    fields.get("upgradeType").set(WeaponType.Suicide_Scourge, UpgradeType.Upgrade_60);
    fields.get("damageType").set(WeaponType.Suicide_Scourge, DamageType.Normal);
    fields.get("explosionType").set(WeaponType.Suicide_Scourge, ExplosionType.Normal);
    fields.get("minRange").set(WeaponType.Suicide_Scourge, 0);
    fields.get("maxRange").set(WeaponType.Suicide_Scourge, 3);
    fields.get("innerSplashRadius").set(WeaponType.Suicide_Scourge, 0);
    fields.get("medianSplashRadius").set(WeaponType.Suicide_Scourge, 0);
    fields.get("outerSplashRadius").set(WeaponType.Suicide_Scourge, 0);
    fields.get("targetsAir").set(WeaponType.Suicide_Scourge, true);
    fields.get("targetsGround").set(WeaponType.Suicide_Scourge, false);
    fields.get("targetsMechanical").set(WeaponType.Suicide_Scourge, false);
    fields.get("targetsOrganic").set(WeaponType.Suicide_Scourge, false);
    fields.get("targetsNonBuilding").set(WeaponType.Suicide_Scourge, false);
    fields.get("targetsNonRobotic").set(WeaponType.Suicide_Scourge, false);
    fields.get("targetsTerrain").set(WeaponType.Suicide_Scourge, false);
    fields.get("targetsOrgOrMech").set(WeaponType.Suicide_Scourge, false);
    fields.get("targetsOwn").set(WeaponType.Suicide_Scourge, false);
  }

  private static void initializeWeaponType_Parasite() throws Exception {
    Class<?> c = WeaponType.class;
    Map<String, Field> fields =
        Stream.of(c.getDeclaredFields())
            .collect(
                Collectors.toMap(
                    f -> f.getName(),
                    f -> {
                      f.setAccessible(true);
                      return f;
                    }));
    fields.get("iD").set(WeaponType.Parasite, 56);
    fields.get("tech").set(WeaponType.Parasite, TechType.Parasite);
    fields.get("whatUses").set(WeaponType.Parasite, UnitType.Zerg_Queen);
    fields.get("damageAmount").set(WeaponType.Parasite, 0);
    fields.get("damageBonus").set(WeaponType.Parasite, 0);
    fields.get("damageCooldown").set(WeaponType.Parasite, 1);
    fields.get("damageFactor").set(WeaponType.Parasite, 1);
    fields.get("upgradeType").set(WeaponType.Parasite, UpgradeType.Upgrade_60);
    fields.get("damageType").set(WeaponType.Parasite, DamageType.Independent);
    fields.get("explosionType").set(WeaponType.Parasite, ExplosionType.Parasite);
    fields.get("minRange").set(WeaponType.Parasite, 0);
    fields.get("maxRange").set(WeaponType.Parasite, 384);
    fields.get("innerSplashRadius").set(WeaponType.Parasite, 0);
    fields.get("medianSplashRadius").set(WeaponType.Parasite, 0);
    fields.get("outerSplashRadius").set(WeaponType.Parasite, 0);
    fields.get("targetsAir").set(WeaponType.Parasite, true);
    fields.get("targetsGround").set(WeaponType.Parasite, true);
    fields.get("targetsMechanical").set(WeaponType.Parasite, false);
    fields.get("targetsOrganic").set(WeaponType.Parasite, false);
    fields.get("targetsNonBuilding").set(WeaponType.Parasite, true);
    fields.get("targetsNonRobotic").set(WeaponType.Parasite, false);
    fields.get("targetsTerrain").set(WeaponType.Parasite, false);
    fields.get("targetsOrgOrMech").set(WeaponType.Parasite, false);
    fields.get("targetsOwn").set(WeaponType.Parasite, false);
  }

  private static void initializeWeaponType_Spawn_Broodlings() throws Exception {
    Class<?> c = WeaponType.class;
    Map<String, Field> fields =
        Stream.of(c.getDeclaredFields())
            .collect(
                Collectors.toMap(
                    f -> f.getName(),
                    f -> {
                      f.setAccessible(true);
                      return f;
                    }));
    fields.get("iD").set(WeaponType.Spawn_Broodlings, 57);
    fields.get("tech").set(WeaponType.Spawn_Broodlings, TechType.Spawn_Broodlings);
    fields.get("whatUses").set(WeaponType.Spawn_Broodlings, UnitType.Zerg_Queen);
    fields.get("damageAmount").set(WeaponType.Spawn_Broodlings, 0);
    fields.get("damageBonus").set(WeaponType.Spawn_Broodlings, 0);
    fields.get("damageCooldown").set(WeaponType.Spawn_Broodlings, 1);
    fields.get("damageFactor").set(WeaponType.Spawn_Broodlings, 1);
    fields.get("upgradeType").set(WeaponType.Spawn_Broodlings, UpgradeType.Upgrade_60);
    fields.get("damageType").set(WeaponType.Spawn_Broodlings, DamageType.Independent);
    fields.get("explosionType").set(WeaponType.Spawn_Broodlings, ExplosionType.Broodlings);
    fields.get("minRange").set(WeaponType.Spawn_Broodlings, 0);
    fields.get("maxRange").set(WeaponType.Spawn_Broodlings, 288);
    fields.get("innerSplashRadius").set(WeaponType.Spawn_Broodlings, 0);
    fields.get("medianSplashRadius").set(WeaponType.Spawn_Broodlings, 0);
    fields.get("outerSplashRadius").set(WeaponType.Spawn_Broodlings, 0);
    fields.get("targetsAir").set(WeaponType.Spawn_Broodlings, false);
    fields.get("targetsGround").set(WeaponType.Spawn_Broodlings, true);
    fields.get("targetsMechanical").set(WeaponType.Spawn_Broodlings, false);
    fields.get("targetsOrganic").set(WeaponType.Spawn_Broodlings, false);
    fields.get("targetsNonBuilding").set(WeaponType.Spawn_Broodlings, true);
    fields.get("targetsNonRobotic").set(WeaponType.Spawn_Broodlings, true);
    fields.get("targetsTerrain").set(WeaponType.Spawn_Broodlings, false);
    fields.get("targetsOrgOrMech").set(WeaponType.Spawn_Broodlings, true);
    fields.get("targetsOwn").set(WeaponType.Spawn_Broodlings, false);
  }

  private static void initializeWeaponType_Ensnare() throws Exception {
    Class<?> c = WeaponType.class;
    Map<String, Field> fields =
        Stream.of(c.getDeclaredFields())
            .collect(
                Collectors.toMap(
                    f -> f.getName(),
                    f -> {
                      f.setAccessible(true);
                      return f;
                    }));
    fields.get("iD").set(WeaponType.Ensnare, 58);
    fields.get("tech").set(WeaponType.Ensnare, TechType.Ensnare);
    fields.get("whatUses").set(WeaponType.Ensnare, UnitType.Zerg_Queen);
    fields.get("damageAmount").set(WeaponType.Ensnare, 0);
    fields.get("damageBonus").set(WeaponType.Ensnare, 0);
    fields.get("damageCooldown").set(WeaponType.Ensnare, 1);
    fields.get("damageFactor").set(WeaponType.Ensnare, 1);
    fields.get("upgradeType").set(WeaponType.Ensnare, UpgradeType.Upgrade_60);
    fields.get("damageType").set(WeaponType.Ensnare, DamageType.Independent);
    fields.get("explosionType").set(WeaponType.Ensnare, ExplosionType.Ensnare);
    fields.get("minRange").set(WeaponType.Ensnare, 0);
    fields.get("maxRange").set(WeaponType.Ensnare, 288);
    fields.get("innerSplashRadius").set(WeaponType.Ensnare, 0);
    fields.get("medianSplashRadius").set(WeaponType.Ensnare, 0);
    fields.get("outerSplashRadius").set(WeaponType.Ensnare, 0);
    fields.get("targetsAir").set(WeaponType.Ensnare, true);
    fields.get("targetsGround").set(WeaponType.Ensnare, true);
    fields.get("targetsMechanical").set(WeaponType.Ensnare, false);
    fields.get("targetsOrganic").set(WeaponType.Ensnare, false);
    fields.get("targetsNonBuilding").set(WeaponType.Ensnare, false);
    fields.get("targetsNonRobotic").set(WeaponType.Ensnare, false);
    fields.get("targetsTerrain").set(WeaponType.Ensnare, true);
    fields.get("targetsOrgOrMech").set(WeaponType.Ensnare, false);
    fields.get("targetsOwn").set(WeaponType.Ensnare, false);
  }

  private static void initializeWeaponType_Dark_Swarm() throws Exception {
    Class<?> c = WeaponType.class;
    Map<String, Field> fields =
        Stream.of(c.getDeclaredFields())
            .collect(
                Collectors.toMap(
                    f -> f.getName(),
                    f -> {
                      f.setAccessible(true);
                      return f;
                    }));
    fields.get("iD").set(WeaponType.Dark_Swarm, 59);
    fields.get("tech").set(WeaponType.Dark_Swarm, TechType.Dark_Swarm);
    fields.get("whatUses").set(WeaponType.Dark_Swarm, UnitType.Zerg_Defiler);
    fields.get("damageAmount").set(WeaponType.Dark_Swarm, 0);
    fields.get("damageBonus").set(WeaponType.Dark_Swarm, 0);
    fields.get("damageCooldown").set(WeaponType.Dark_Swarm, 1);
    fields.get("damageFactor").set(WeaponType.Dark_Swarm, 1);
    fields.get("upgradeType").set(WeaponType.Dark_Swarm, UpgradeType.Upgrade_60);
    fields.get("damageType").set(WeaponType.Dark_Swarm, DamageType.Independent);
    fields.get("explosionType").set(WeaponType.Dark_Swarm, ExplosionType.Dark_Swarm);
    fields.get("minRange").set(WeaponType.Dark_Swarm, 0);
    fields.get("maxRange").set(WeaponType.Dark_Swarm, 288);
    fields.get("innerSplashRadius").set(WeaponType.Dark_Swarm, 0);
    fields.get("medianSplashRadius").set(WeaponType.Dark_Swarm, 0);
    fields.get("outerSplashRadius").set(WeaponType.Dark_Swarm, 0);
    fields.get("targetsAir").set(WeaponType.Dark_Swarm, true);
    fields.get("targetsGround").set(WeaponType.Dark_Swarm, true);
    fields.get("targetsMechanical").set(WeaponType.Dark_Swarm, false);
    fields.get("targetsOrganic").set(WeaponType.Dark_Swarm, false);
    fields.get("targetsNonBuilding").set(WeaponType.Dark_Swarm, false);
    fields.get("targetsNonRobotic").set(WeaponType.Dark_Swarm, false);
    fields.get("targetsTerrain").set(WeaponType.Dark_Swarm, false);
    fields.get("targetsOrgOrMech").set(WeaponType.Dark_Swarm, false);
    fields.get("targetsOwn").set(WeaponType.Dark_Swarm, false);
  }

  private static void initializeWeaponType_Plague() throws Exception {
    Class<?> c = WeaponType.class;
    Map<String, Field> fields =
        Stream.of(c.getDeclaredFields())
            .collect(
                Collectors.toMap(
                    f -> f.getName(),
                    f -> {
                      f.setAccessible(true);
                      return f;
                    }));
    fields.get("iD").set(WeaponType.Plague, 60);
    fields.get("tech").set(WeaponType.Plague, TechType.Plague);
    fields.get("whatUses").set(WeaponType.Plague, UnitType.Zerg_Defiler);
    fields.get("damageAmount").set(WeaponType.Plague, 300);
    fields.get("damageBonus").set(WeaponType.Plague, 0);
    fields.get("damageCooldown").set(WeaponType.Plague, 1);
    fields.get("damageFactor").set(WeaponType.Plague, 1);
    fields.get("upgradeType").set(WeaponType.Plague, UpgradeType.Upgrade_60);
    fields.get("damageType").set(WeaponType.Plague, DamageType.Independent);
    fields.get("explosionType").set(WeaponType.Plague, ExplosionType.Plague);
    fields.get("minRange").set(WeaponType.Plague, 0);
    fields.get("maxRange").set(WeaponType.Plague, 288);
    fields.get("innerSplashRadius").set(WeaponType.Plague, 0);
    fields.get("medianSplashRadius").set(WeaponType.Plague, 0);
    fields.get("outerSplashRadius").set(WeaponType.Plague, 0);
    fields.get("targetsAir").set(WeaponType.Plague, true);
    fields.get("targetsGround").set(WeaponType.Plague, true);
    fields.get("targetsMechanical").set(WeaponType.Plague, false);
    fields.get("targetsOrganic").set(WeaponType.Plague, false);
    fields.get("targetsNonBuilding").set(WeaponType.Plague, false);
    fields.get("targetsNonRobotic").set(WeaponType.Plague, false);
    fields.get("targetsTerrain").set(WeaponType.Plague, true);
    fields.get("targetsOrgOrMech").set(WeaponType.Plague, false);
    fields.get("targetsOwn").set(WeaponType.Plague, false);
  }

  private static void initializeWeaponType_Consume() throws Exception {
    Class<?> c = WeaponType.class;
    Map<String, Field> fields =
        Stream.of(c.getDeclaredFields())
            .collect(
                Collectors.toMap(
                    f -> f.getName(),
                    f -> {
                      f.setAccessible(true);
                      return f;
                    }));
    fields.get("iD").set(WeaponType.Consume, 61);
    fields.get("tech").set(WeaponType.Consume, TechType.Consume);
    fields.get("whatUses").set(WeaponType.Consume, UnitType.Zerg_Defiler);
    fields.get("damageAmount").set(WeaponType.Consume, 0);
    fields.get("damageBonus").set(WeaponType.Consume, 0);
    fields.get("damageCooldown").set(WeaponType.Consume, 1);
    fields.get("damageFactor").set(WeaponType.Consume, 1);
    fields.get("upgradeType").set(WeaponType.Consume, UpgradeType.Upgrade_60);
    fields.get("damageType").set(WeaponType.Consume, DamageType.Independent);
    fields.get("explosionType").set(WeaponType.Consume, ExplosionType.Consume);
    fields.get("minRange").set(WeaponType.Consume, 0);
    fields.get("maxRange").set(WeaponType.Consume, 16);
    fields.get("innerSplashRadius").set(WeaponType.Consume, 0);
    fields.get("medianSplashRadius").set(WeaponType.Consume, 0);
    fields.get("outerSplashRadius").set(WeaponType.Consume, 0);
    fields.get("targetsAir").set(WeaponType.Consume, true);
    fields.get("targetsGround").set(WeaponType.Consume, true);
    fields.get("targetsMechanical").set(WeaponType.Consume, false);
    fields.get("targetsOrganic").set(WeaponType.Consume, true);
    fields.get("targetsNonBuilding").set(WeaponType.Consume, true);
    fields.get("targetsNonRobotic").set(WeaponType.Consume, false);
    fields.get("targetsTerrain").set(WeaponType.Consume, false);
    fields.get("targetsOrgOrMech").set(WeaponType.Consume, false);
    fields.get("targetsOwn").set(WeaponType.Consume, true);
  }

  private static void initializeWeaponType_Particle_Beam() throws Exception {
    Class<?> c = WeaponType.class;
    Map<String, Field> fields =
        Stream.of(c.getDeclaredFields())
            .collect(
                Collectors.toMap(
                    f -> f.getName(),
                    f -> {
                      f.setAccessible(true);
                      return f;
                    }));
    fields.get("iD").set(WeaponType.Particle_Beam, 62);
    fields.get("tech").set(WeaponType.Particle_Beam, TechType.None);
    fields.get("whatUses").set(WeaponType.Particle_Beam, UnitType.Protoss_Probe);
    fields.get("damageAmount").set(WeaponType.Particle_Beam, 5);
    fields.get("damageBonus").set(WeaponType.Particle_Beam, 0);
    fields.get("damageCooldown").set(WeaponType.Particle_Beam, 22);
    fields.get("damageFactor").set(WeaponType.Particle_Beam, 1);
    fields.get("upgradeType").set(WeaponType.Particle_Beam, UpgradeType.Upgrade_60);
    fields.get("damageType").set(WeaponType.Particle_Beam, DamageType.Normal);
    fields.get("explosionType").set(WeaponType.Particle_Beam, ExplosionType.Normal);
    fields.get("minRange").set(WeaponType.Particle_Beam, 0);
    fields.get("maxRange").set(WeaponType.Particle_Beam, 32);
    fields.get("innerSplashRadius").set(WeaponType.Particle_Beam, 0);
    fields.get("medianSplashRadius").set(WeaponType.Particle_Beam, 0);
    fields.get("outerSplashRadius").set(WeaponType.Particle_Beam, 0);
    fields.get("targetsAir").set(WeaponType.Particle_Beam, false);
    fields.get("targetsGround").set(WeaponType.Particle_Beam, true);
    fields.get("targetsMechanical").set(WeaponType.Particle_Beam, false);
    fields.get("targetsOrganic").set(WeaponType.Particle_Beam, false);
    fields.get("targetsNonBuilding").set(WeaponType.Particle_Beam, false);
    fields.get("targetsNonRobotic").set(WeaponType.Particle_Beam, false);
    fields.get("targetsTerrain").set(WeaponType.Particle_Beam, false);
    fields.get("targetsOrgOrMech").set(WeaponType.Particle_Beam, false);
    fields.get("targetsOwn").set(WeaponType.Particle_Beam, false);
  }

  private static void initializeWeaponType_unk_63() throws Exception {
    Class<?> c = WeaponType.class;
    Map<String, Field> fields =
        Stream.of(c.getDeclaredFields())
            .collect(
                Collectors.toMap(
                    f -> f.getName(),
                    f -> {
                      f.setAccessible(true);
                      return f;
                    }));
    fields.get("iD").set(WeaponType.unk_63, 63);
    fields.get("tech").set(WeaponType.unk_63, null);
    fields.get("whatUses").set(WeaponType.unk_63, null);
    fields.get("damageAmount").set(WeaponType.unk_63, 0);
    fields.get("damageBonus").set(WeaponType.unk_63, 0);
    fields.get("damageCooldown").set(WeaponType.unk_63, 0);
    fields.get("damageFactor").set(WeaponType.unk_63, 0);
    fields.get("upgradeType").set(WeaponType.unk_63, null);
    fields.get("damageType").set(WeaponType.unk_63, null);
    fields.get("explosionType").set(WeaponType.unk_63, null);
    fields.get("minRange").set(WeaponType.unk_63, 0);
    fields.get("maxRange").set(WeaponType.unk_63, 0);
    fields.get("innerSplashRadius").set(WeaponType.unk_63, 0);
    fields.get("medianSplashRadius").set(WeaponType.unk_63, 0);
    fields.get("outerSplashRadius").set(WeaponType.unk_63, 0);
    fields.get("targetsAir").set(WeaponType.unk_63, false);
    fields.get("targetsGround").set(WeaponType.unk_63, false);
    fields.get("targetsMechanical").set(WeaponType.unk_63, false);
    fields.get("targetsOrganic").set(WeaponType.unk_63, false);
    fields.get("targetsNonBuilding").set(WeaponType.unk_63, false);
    fields.get("targetsNonRobotic").set(WeaponType.unk_63, false);
    fields.get("targetsTerrain").set(WeaponType.unk_63, false);
    fields.get("targetsOrgOrMech").set(WeaponType.unk_63, false);
    fields.get("targetsOwn").set(WeaponType.unk_63, false);
  }

  private static void initializeWeaponType_Psi_Blades() throws Exception {
    Class<?> c = WeaponType.class;
    Map<String, Field> fields =
        Stream.of(c.getDeclaredFields())
            .collect(
                Collectors.toMap(
                    f -> f.getName(),
                    f -> {
                      f.setAccessible(true);
                      return f;
                    }));
    fields.get("iD").set(WeaponType.Psi_Blades, 64);
    fields.get("tech").set(WeaponType.Psi_Blades, TechType.None);
    fields.get("whatUses").set(WeaponType.Psi_Blades, UnitType.Protoss_Zealot);
    fields.get("damageAmount").set(WeaponType.Psi_Blades, 8);
    fields.get("damageBonus").set(WeaponType.Psi_Blades, 1);
    fields.get("damageCooldown").set(WeaponType.Psi_Blades, 22);
    fields.get("damageFactor").set(WeaponType.Psi_Blades, 1);
    fields.get("upgradeType").set(WeaponType.Psi_Blades, UpgradeType.Protoss_Ground_Weapons);
    fields.get("damageType").set(WeaponType.Psi_Blades, DamageType.Normal);
    fields.get("explosionType").set(WeaponType.Psi_Blades, ExplosionType.Normal);
    fields.get("minRange").set(WeaponType.Psi_Blades, 0);
    fields.get("maxRange").set(WeaponType.Psi_Blades, 15);
    fields.get("innerSplashRadius").set(WeaponType.Psi_Blades, 0);
    fields.get("medianSplashRadius").set(WeaponType.Psi_Blades, 0);
    fields.get("outerSplashRadius").set(WeaponType.Psi_Blades, 0);
    fields.get("targetsAir").set(WeaponType.Psi_Blades, false);
    fields.get("targetsGround").set(WeaponType.Psi_Blades, true);
    fields.get("targetsMechanical").set(WeaponType.Psi_Blades, false);
    fields.get("targetsOrganic").set(WeaponType.Psi_Blades, false);
    fields.get("targetsNonBuilding").set(WeaponType.Psi_Blades, false);
    fields.get("targetsNonRobotic").set(WeaponType.Psi_Blades, false);
    fields.get("targetsTerrain").set(WeaponType.Psi_Blades, false);
    fields.get("targetsOrgOrMech").set(WeaponType.Psi_Blades, false);
    fields.get("targetsOwn").set(WeaponType.Psi_Blades, false);
  }

  private static void initializeWeaponType_Psi_Blades_Fenix() throws Exception {
    Class<?> c = WeaponType.class;
    Map<String, Field> fields =
        Stream.of(c.getDeclaredFields())
            .collect(
                Collectors.toMap(
                    f -> f.getName(),
                    f -> {
                      f.setAccessible(true);
                      return f;
                    }));
    fields.get("iD").set(WeaponType.Psi_Blades_Fenix, 65);
    fields.get("tech").set(WeaponType.Psi_Blades_Fenix, TechType.None);
    fields.get("whatUses").set(WeaponType.Psi_Blades_Fenix, UnitType.Hero_Fenix_Zealot);
    fields.get("damageAmount").set(WeaponType.Psi_Blades_Fenix, 20);
    fields.get("damageBonus").set(WeaponType.Psi_Blades_Fenix, 1);
    fields.get("damageCooldown").set(WeaponType.Psi_Blades_Fenix, 22);
    fields.get("damageFactor").set(WeaponType.Psi_Blades_Fenix, 1);
    fields.get("upgradeType").set(WeaponType.Psi_Blades_Fenix, UpgradeType.Protoss_Ground_Weapons);
    fields.get("damageType").set(WeaponType.Psi_Blades_Fenix, DamageType.Normal);
    fields.get("explosionType").set(WeaponType.Psi_Blades_Fenix, ExplosionType.Normal);
    fields.get("minRange").set(WeaponType.Psi_Blades_Fenix, 0);
    fields.get("maxRange").set(WeaponType.Psi_Blades_Fenix, 15);
    fields.get("innerSplashRadius").set(WeaponType.Psi_Blades_Fenix, 0);
    fields.get("medianSplashRadius").set(WeaponType.Psi_Blades_Fenix, 0);
    fields.get("outerSplashRadius").set(WeaponType.Psi_Blades_Fenix, 0);
    fields.get("targetsAir").set(WeaponType.Psi_Blades_Fenix, false);
    fields.get("targetsGround").set(WeaponType.Psi_Blades_Fenix, true);
    fields.get("targetsMechanical").set(WeaponType.Psi_Blades_Fenix, false);
    fields.get("targetsOrganic").set(WeaponType.Psi_Blades_Fenix, false);
    fields.get("targetsNonBuilding").set(WeaponType.Psi_Blades_Fenix, false);
    fields.get("targetsNonRobotic").set(WeaponType.Psi_Blades_Fenix, false);
    fields.get("targetsTerrain").set(WeaponType.Psi_Blades_Fenix, false);
    fields.get("targetsOrgOrMech").set(WeaponType.Psi_Blades_Fenix, false);
    fields.get("targetsOwn").set(WeaponType.Psi_Blades_Fenix, false);
  }

  private static void initializeWeaponType_Phase_Disruptor() throws Exception {
    Class<?> c = WeaponType.class;
    Map<String, Field> fields =
        Stream.of(c.getDeclaredFields())
            .collect(
                Collectors.toMap(
                    f -> f.getName(),
                    f -> {
                      f.setAccessible(true);
                      return f;
                    }));
    fields.get("iD").set(WeaponType.Phase_Disruptor, 66);
    fields.get("tech").set(WeaponType.Phase_Disruptor, TechType.None);
    fields.get("whatUses").set(WeaponType.Phase_Disruptor, UnitType.Protoss_Dragoon);
    fields.get("damageAmount").set(WeaponType.Phase_Disruptor, 20);
    fields.get("damageBonus").set(WeaponType.Phase_Disruptor, 2);
    fields.get("damageCooldown").set(WeaponType.Phase_Disruptor, 30);
    fields.get("damageFactor").set(WeaponType.Phase_Disruptor, 1);
    fields.get("upgradeType").set(WeaponType.Phase_Disruptor, UpgradeType.Protoss_Ground_Weapons);
    fields.get("damageType").set(WeaponType.Phase_Disruptor, DamageType.Explosive);
    fields.get("explosionType").set(WeaponType.Phase_Disruptor, ExplosionType.Normal);
    fields.get("minRange").set(WeaponType.Phase_Disruptor, 0);
    fields.get("maxRange").set(WeaponType.Phase_Disruptor, 128);
    fields.get("innerSplashRadius").set(WeaponType.Phase_Disruptor, 0);
    fields.get("medianSplashRadius").set(WeaponType.Phase_Disruptor, 0);
    fields.get("outerSplashRadius").set(WeaponType.Phase_Disruptor, 0);
    fields.get("targetsAir").set(WeaponType.Phase_Disruptor, true);
    fields.get("targetsGround").set(WeaponType.Phase_Disruptor, true);
    fields.get("targetsMechanical").set(WeaponType.Phase_Disruptor, false);
    fields.get("targetsOrganic").set(WeaponType.Phase_Disruptor, false);
    fields.get("targetsNonBuilding").set(WeaponType.Phase_Disruptor, false);
    fields.get("targetsNonRobotic").set(WeaponType.Phase_Disruptor, false);
    fields.get("targetsTerrain").set(WeaponType.Phase_Disruptor, false);
    fields.get("targetsOrgOrMech").set(WeaponType.Phase_Disruptor, false);
    fields.get("targetsOwn").set(WeaponType.Phase_Disruptor, false);
  }

  private static void initializeWeaponType_Phase_Disruptor_Fenix() throws Exception {
    Class<?> c = WeaponType.class;
    Map<String, Field> fields =
        Stream.of(c.getDeclaredFields())
            .collect(
                Collectors.toMap(
                    f -> f.getName(),
                    f -> {
                      f.setAccessible(true);
                      return f;
                    }));
    fields.get("iD").set(WeaponType.Phase_Disruptor_Fenix, 67);
    fields.get("tech").set(WeaponType.Phase_Disruptor_Fenix, TechType.None);
    fields.get("whatUses").set(WeaponType.Phase_Disruptor_Fenix, UnitType.Hero_Fenix_Dragoon);
    fields.get("damageAmount").set(WeaponType.Phase_Disruptor_Fenix, 45);
    fields.get("damageBonus").set(WeaponType.Phase_Disruptor_Fenix, 2);
    fields.get("damageCooldown").set(WeaponType.Phase_Disruptor_Fenix, 22);
    fields.get("damageFactor").set(WeaponType.Phase_Disruptor_Fenix, 1);
    fields
        .get("upgradeType")
        .set(WeaponType.Phase_Disruptor_Fenix, UpgradeType.Protoss_Ground_Weapons);
    fields.get("damageType").set(WeaponType.Phase_Disruptor_Fenix, DamageType.Explosive);
    fields.get("explosionType").set(WeaponType.Phase_Disruptor_Fenix, ExplosionType.Normal);
    fields.get("minRange").set(WeaponType.Phase_Disruptor_Fenix, 0);
    fields.get("maxRange").set(WeaponType.Phase_Disruptor_Fenix, 128);
    fields.get("innerSplashRadius").set(WeaponType.Phase_Disruptor_Fenix, 0);
    fields.get("medianSplashRadius").set(WeaponType.Phase_Disruptor_Fenix, 0);
    fields.get("outerSplashRadius").set(WeaponType.Phase_Disruptor_Fenix, 0);
    fields.get("targetsAir").set(WeaponType.Phase_Disruptor_Fenix, true);
    fields.get("targetsGround").set(WeaponType.Phase_Disruptor_Fenix, true);
    fields.get("targetsMechanical").set(WeaponType.Phase_Disruptor_Fenix, false);
    fields.get("targetsOrganic").set(WeaponType.Phase_Disruptor_Fenix, false);
    fields.get("targetsNonBuilding").set(WeaponType.Phase_Disruptor_Fenix, false);
    fields.get("targetsNonRobotic").set(WeaponType.Phase_Disruptor_Fenix, false);
    fields.get("targetsTerrain").set(WeaponType.Phase_Disruptor_Fenix, false);
    fields.get("targetsOrgOrMech").set(WeaponType.Phase_Disruptor_Fenix, false);
    fields.get("targetsOwn").set(WeaponType.Phase_Disruptor_Fenix, false);
  }

  private static void initializeWeaponType_unk_68() throws Exception {
    Class<?> c = WeaponType.class;
    Map<String, Field> fields =
        Stream.of(c.getDeclaredFields())
            .collect(
                Collectors.toMap(
                    f -> f.getName(),
                    f -> {
                      f.setAccessible(true);
                      return f;
                    }));
    fields.get("iD").set(WeaponType.unk_68, 68);
    fields.get("tech").set(WeaponType.unk_68, null);
    fields.get("whatUses").set(WeaponType.unk_68, null);
    fields.get("damageAmount").set(WeaponType.unk_68, 0);
    fields.get("damageBonus").set(WeaponType.unk_68, 0);
    fields.get("damageCooldown").set(WeaponType.unk_68, 0);
    fields.get("damageFactor").set(WeaponType.unk_68, 0);
    fields.get("upgradeType").set(WeaponType.unk_68, null);
    fields.get("damageType").set(WeaponType.unk_68, null);
    fields.get("explosionType").set(WeaponType.unk_68, null);
    fields.get("minRange").set(WeaponType.unk_68, 0);
    fields.get("maxRange").set(WeaponType.unk_68, 0);
    fields.get("innerSplashRadius").set(WeaponType.unk_68, 0);
    fields.get("medianSplashRadius").set(WeaponType.unk_68, 0);
    fields.get("outerSplashRadius").set(WeaponType.unk_68, 0);
    fields.get("targetsAir").set(WeaponType.unk_68, false);
    fields.get("targetsGround").set(WeaponType.unk_68, false);
    fields.get("targetsMechanical").set(WeaponType.unk_68, false);
    fields.get("targetsOrganic").set(WeaponType.unk_68, false);
    fields.get("targetsNonBuilding").set(WeaponType.unk_68, false);
    fields.get("targetsNonRobotic").set(WeaponType.unk_68, false);
    fields.get("targetsTerrain").set(WeaponType.unk_68, false);
    fields.get("targetsOrgOrMech").set(WeaponType.unk_68, false);
    fields.get("targetsOwn").set(WeaponType.unk_68, false);
  }

  private static void initializeWeaponType_Psi_Assault() throws Exception {
    Class<?> c = WeaponType.class;
    Map<String, Field> fields =
        Stream.of(c.getDeclaredFields())
            .collect(
                Collectors.toMap(
                    f -> f.getName(),
                    f -> {
                      f.setAccessible(true);
                      return f;
                    }));
    fields.get("iD").set(WeaponType.Psi_Assault, 69);
    fields.get("tech").set(WeaponType.Psi_Assault, TechType.None);
    fields.get("whatUses").set(WeaponType.Psi_Assault, UnitType.Hero_Tassadar);
    fields.get("damageAmount").set(WeaponType.Psi_Assault, 20);
    fields.get("damageBonus").set(WeaponType.Psi_Assault, 1);
    fields.get("damageCooldown").set(WeaponType.Psi_Assault, 22);
    fields.get("damageFactor").set(WeaponType.Psi_Assault, 1);
    fields.get("upgradeType").set(WeaponType.Psi_Assault, UpgradeType.Protoss_Ground_Weapons);
    fields.get("damageType").set(WeaponType.Psi_Assault, DamageType.Normal);
    fields.get("explosionType").set(WeaponType.Psi_Assault, ExplosionType.Normal);
    fields.get("minRange").set(WeaponType.Psi_Assault, 0);
    fields.get("maxRange").set(WeaponType.Psi_Assault, 96);
    fields.get("innerSplashRadius").set(WeaponType.Psi_Assault, 0);
    fields.get("medianSplashRadius").set(WeaponType.Psi_Assault, 0);
    fields.get("outerSplashRadius").set(WeaponType.Psi_Assault, 0);
    fields.get("targetsAir").set(WeaponType.Psi_Assault, false);
    fields.get("targetsGround").set(WeaponType.Psi_Assault, true);
    fields.get("targetsMechanical").set(WeaponType.Psi_Assault, false);
    fields.get("targetsOrganic").set(WeaponType.Psi_Assault, false);
    fields.get("targetsNonBuilding").set(WeaponType.Psi_Assault, false);
    fields.get("targetsNonRobotic").set(WeaponType.Psi_Assault, false);
    fields.get("targetsTerrain").set(WeaponType.Psi_Assault, false);
    fields.get("targetsOrgOrMech").set(WeaponType.Psi_Assault, false);
    fields.get("targetsOwn").set(WeaponType.Psi_Assault, false);
  }

  private static void initializeWeaponType_Psionic_Shockwave() throws Exception {
    Class<?> c = WeaponType.class;
    Map<String, Field> fields =
        Stream.of(c.getDeclaredFields())
            .collect(
                Collectors.toMap(
                    f -> f.getName(),
                    f -> {
                      f.setAccessible(true);
                      return f;
                    }));
    fields.get("iD").set(WeaponType.Psionic_Shockwave, 70);
    fields.get("tech").set(WeaponType.Psionic_Shockwave, TechType.None);
    fields.get("whatUses").set(WeaponType.Psionic_Shockwave, UnitType.Protoss_Archon);
    fields.get("damageAmount").set(WeaponType.Psionic_Shockwave, 30);
    fields.get("damageBonus").set(WeaponType.Psionic_Shockwave, 3);
    fields.get("damageCooldown").set(WeaponType.Psionic_Shockwave, 20);
    fields.get("damageFactor").set(WeaponType.Psionic_Shockwave, 1);
    fields.get("upgradeType").set(WeaponType.Psionic_Shockwave, UpgradeType.Protoss_Ground_Weapons);
    fields.get("damageType").set(WeaponType.Psionic_Shockwave, DamageType.Normal);
    fields.get("explosionType").set(WeaponType.Psionic_Shockwave, ExplosionType.Enemy_Splash);
    fields.get("minRange").set(WeaponType.Psionic_Shockwave, 0);
    fields.get("maxRange").set(WeaponType.Psionic_Shockwave, 64);
    fields.get("innerSplashRadius").set(WeaponType.Psionic_Shockwave, 3);
    fields.get("medianSplashRadius").set(WeaponType.Psionic_Shockwave, 15);
    fields.get("outerSplashRadius").set(WeaponType.Psionic_Shockwave, 30);
    fields.get("targetsAir").set(WeaponType.Psionic_Shockwave, true);
    fields.get("targetsGround").set(WeaponType.Psionic_Shockwave, true);
    fields.get("targetsMechanical").set(WeaponType.Psionic_Shockwave, false);
    fields.get("targetsOrganic").set(WeaponType.Psionic_Shockwave, false);
    fields.get("targetsNonBuilding").set(WeaponType.Psionic_Shockwave, false);
    fields.get("targetsNonRobotic").set(WeaponType.Psionic_Shockwave, false);
    fields.get("targetsTerrain").set(WeaponType.Psionic_Shockwave, false);
    fields.get("targetsOrgOrMech").set(WeaponType.Psionic_Shockwave, false);
    fields.get("targetsOwn").set(WeaponType.Psionic_Shockwave, false);
  }

  private static void initializeWeaponType_Psionic_Shockwave_TZ_Archon() throws Exception {
    Class<?> c = WeaponType.class;
    Map<String, Field> fields =
        Stream.of(c.getDeclaredFields())
            .collect(
                Collectors.toMap(
                    f -> f.getName(),
                    f -> {
                      f.setAccessible(true);
                      return f;
                    }));
    fields.get("iD").set(WeaponType.Psionic_Shockwave_TZ_Archon, 71);
    fields.get("tech").set(WeaponType.Psionic_Shockwave_TZ_Archon, TechType.None);
    fields
        .get("whatUses")
        .set(WeaponType.Psionic_Shockwave_TZ_Archon, UnitType.Hero_Tassadar_Zeratul_Archon);
    fields.get("damageAmount").set(WeaponType.Psionic_Shockwave_TZ_Archon, 60);
    fields.get("damageBonus").set(WeaponType.Psionic_Shockwave_TZ_Archon, 3);
    fields.get("damageCooldown").set(WeaponType.Psionic_Shockwave_TZ_Archon, 20);
    fields.get("damageFactor").set(WeaponType.Psionic_Shockwave_TZ_Archon, 1);
    fields
        .get("upgradeType")
        .set(WeaponType.Psionic_Shockwave_TZ_Archon, UpgradeType.Protoss_Ground_Weapons);
    fields.get("damageType").set(WeaponType.Psionic_Shockwave_TZ_Archon, DamageType.Normal);
    fields
        .get("explosionType")
        .set(WeaponType.Psionic_Shockwave_TZ_Archon, ExplosionType.Enemy_Splash);
    fields.get("minRange").set(WeaponType.Psionic_Shockwave_TZ_Archon, 0);
    fields.get("maxRange").set(WeaponType.Psionic_Shockwave_TZ_Archon, 64);
    fields.get("innerSplashRadius").set(WeaponType.Psionic_Shockwave_TZ_Archon, 3);
    fields.get("medianSplashRadius").set(WeaponType.Psionic_Shockwave_TZ_Archon, 15);
    fields.get("outerSplashRadius").set(WeaponType.Psionic_Shockwave_TZ_Archon, 30);
    fields.get("targetsAir").set(WeaponType.Psionic_Shockwave_TZ_Archon, true);
    fields.get("targetsGround").set(WeaponType.Psionic_Shockwave_TZ_Archon, true);
    fields.get("targetsMechanical").set(WeaponType.Psionic_Shockwave_TZ_Archon, false);
    fields.get("targetsOrganic").set(WeaponType.Psionic_Shockwave_TZ_Archon, false);
    fields.get("targetsNonBuilding").set(WeaponType.Psionic_Shockwave_TZ_Archon, false);
    fields.get("targetsNonRobotic").set(WeaponType.Psionic_Shockwave_TZ_Archon, false);
    fields.get("targetsTerrain").set(WeaponType.Psionic_Shockwave_TZ_Archon, false);
    fields.get("targetsOrgOrMech").set(WeaponType.Psionic_Shockwave_TZ_Archon, false);
    fields.get("targetsOwn").set(WeaponType.Psionic_Shockwave_TZ_Archon, false);
  }

  private static void initializeWeaponType_unk_72() throws Exception {
    Class<?> c = WeaponType.class;
    Map<String, Field> fields =
        Stream.of(c.getDeclaredFields())
            .collect(
                Collectors.toMap(
                    f -> f.getName(),
                    f -> {
                      f.setAccessible(true);
                      return f;
                    }));
    fields.get("iD").set(WeaponType.unk_72, 72);
    fields.get("tech").set(WeaponType.unk_72, null);
    fields.get("whatUses").set(WeaponType.unk_72, null);
    fields.get("damageAmount").set(WeaponType.unk_72, 0);
    fields.get("damageBonus").set(WeaponType.unk_72, 0);
    fields.get("damageCooldown").set(WeaponType.unk_72, 0);
    fields.get("damageFactor").set(WeaponType.unk_72, 0);
    fields.get("upgradeType").set(WeaponType.unk_72, null);
    fields.get("damageType").set(WeaponType.unk_72, null);
    fields.get("explosionType").set(WeaponType.unk_72, null);
    fields.get("minRange").set(WeaponType.unk_72, 0);
    fields.get("maxRange").set(WeaponType.unk_72, 0);
    fields.get("innerSplashRadius").set(WeaponType.unk_72, 0);
    fields.get("medianSplashRadius").set(WeaponType.unk_72, 0);
    fields.get("outerSplashRadius").set(WeaponType.unk_72, 0);
    fields.get("targetsAir").set(WeaponType.unk_72, false);
    fields.get("targetsGround").set(WeaponType.unk_72, false);
    fields.get("targetsMechanical").set(WeaponType.unk_72, false);
    fields.get("targetsOrganic").set(WeaponType.unk_72, false);
    fields.get("targetsNonBuilding").set(WeaponType.unk_72, false);
    fields.get("targetsNonRobotic").set(WeaponType.unk_72, false);
    fields.get("targetsTerrain").set(WeaponType.unk_72, false);
    fields.get("targetsOrgOrMech").set(WeaponType.unk_72, false);
    fields.get("targetsOwn").set(WeaponType.unk_72, false);
  }

  private static void initializeWeaponType_Dual_Photon_Blasters() throws Exception {
    Class<?> c = WeaponType.class;
    Map<String, Field> fields =
        Stream.of(c.getDeclaredFields())
            .collect(
                Collectors.toMap(
                    f -> f.getName(),
                    f -> {
                      f.setAccessible(true);
                      return f;
                    }));
    fields.get("iD").set(WeaponType.Dual_Photon_Blasters, 73);
    fields.get("tech").set(WeaponType.Dual_Photon_Blasters, TechType.None);
    fields.get("whatUses").set(WeaponType.Dual_Photon_Blasters, UnitType.Protoss_Scout);
    fields.get("damageAmount").set(WeaponType.Dual_Photon_Blasters, 8);
    fields.get("damageBonus").set(WeaponType.Dual_Photon_Blasters, 1);
    fields.get("damageCooldown").set(WeaponType.Dual_Photon_Blasters, 30);
    fields.get("damageFactor").set(WeaponType.Dual_Photon_Blasters, 1);
    fields.get("upgradeType").set(WeaponType.Dual_Photon_Blasters, UpgradeType.Protoss_Air_Weapons);
    fields.get("damageType").set(WeaponType.Dual_Photon_Blasters, DamageType.Normal);
    fields.get("explosionType").set(WeaponType.Dual_Photon_Blasters, ExplosionType.Normal);
    fields.get("minRange").set(WeaponType.Dual_Photon_Blasters, 0);
    fields.get("maxRange").set(WeaponType.Dual_Photon_Blasters, 128);
    fields.get("innerSplashRadius").set(WeaponType.Dual_Photon_Blasters, 0);
    fields.get("medianSplashRadius").set(WeaponType.Dual_Photon_Blasters, 0);
    fields.get("outerSplashRadius").set(WeaponType.Dual_Photon_Blasters, 0);
    fields.get("targetsAir").set(WeaponType.Dual_Photon_Blasters, false);
    fields.get("targetsGround").set(WeaponType.Dual_Photon_Blasters, true);
    fields.get("targetsMechanical").set(WeaponType.Dual_Photon_Blasters, false);
    fields.get("targetsOrganic").set(WeaponType.Dual_Photon_Blasters, false);
    fields.get("targetsNonBuilding").set(WeaponType.Dual_Photon_Blasters, false);
    fields.get("targetsNonRobotic").set(WeaponType.Dual_Photon_Blasters, false);
    fields.get("targetsTerrain").set(WeaponType.Dual_Photon_Blasters, false);
    fields.get("targetsOrgOrMech").set(WeaponType.Dual_Photon_Blasters, false);
    fields.get("targetsOwn").set(WeaponType.Dual_Photon_Blasters, false);
  }

  private static void initializeWeaponType_Anti_Matter_Missiles() throws Exception {
    Class<?> c = WeaponType.class;
    Map<String, Field> fields =
        Stream.of(c.getDeclaredFields())
            .collect(
                Collectors.toMap(
                    f -> f.getName(),
                    f -> {
                      f.setAccessible(true);
                      return f;
                    }));
    fields.get("iD").set(WeaponType.Anti_Matter_Missiles, 74);
    fields.get("tech").set(WeaponType.Anti_Matter_Missiles, TechType.None);
    fields.get("whatUses").set(WeaponType.Anti_Matter_Missiles, UnitType.Protoss_Scout);
    fields.get("damageAmount").set(WeaponType.Anti_Matter_Missiles, 14);
    fields.get("damageBonus").set(WeaponType.Anti_Matter_Missiles, 1);
    fields.get("damageCooldown").set(WeaponType.Anti_Matter_Missiles, 22);
    fields.get("damageFactor").set(WeaponType.Anti_Matter_Missiles, 2);
    fields.get("upgradeType").set(WeaponType.Anti_Matter_Missiles, UpgradeType.Protoss_Air_Weapons);
    fields.get("damageType").set(WeaponType.Anti_Matter_Missiles, DamageType.Explosive);
    fields.get("explosionType").set(WeaponType.Anti_Matter_Missiles, ExplosionType.Normal);
    fields.get("minRange").set(WeaponType.Anti_Matter_Missiles, 0);
    fields.get("maxRange").set(WeaponType.Anti_Matter_Missiles, 128);
    fields.get("innerSplashRadius").set(WeaponType.Anti_Matter_Missiles, 0);
    fields.get("medianSplashRadius").set(WeaponType.Anti_Matter_Missiles, 0);
    fields.get("outerSplashRadius").set(WeaponType.Anti_Matter_Missiles, 0);
    fields.get("targetsAir").set(WeaponType.Anti_Matter_Missiles, true);
    fields.get("targetsGround").set(WeaponType.Anti_Matter_Missiles, false);
    fields.get("targetsMechanical").set(WeaponType.Anti_Matter_Missiles, false);
    fields.get("targetsOrganic").set(WeaponType.Anti_Matter_Missiles, false);
    fields.get("targetsNonBuilding").set(WeaponType.Anti_Matter_Missiles, false);
    fields.get("targetsNonRobotic").set(WeaponType.Anti_Matter_Missiles, false);
    fields.get("targetsTerrain").set(WeaponType.Anti_Matter_Missiles, false);
    fields.get("targetsOrgOrMech").set(WeaponType.Anti_Matter_Missiles, false);
    fields.get("targetsOwn").set(WeaponType.Anti_Matter_Missiles, false);
  }

  private static void initializeWeaponType_Dual_Photon_Blasters_Mojo() throws Exception {
    Class<?> c = WeaponType.class;
    Map<String, Field> fields =
        Stream.of(c.getDeclaredFields())
            .collect(
                Collectors.toMap(
                    f -> f.getName(),
                    f -> {
                      f.setAccessible(true);
                      return f;
                    }));
    fields.get("iD").set(WeaponType.Dual_Photon_Blasters_Mojo, 75);
    fields.get("tech").set(WeaponType.Dual_Photon_Blasters_Mojo, TechType.None);
    fields.get("whatUses").set(WeaponType.Dual_Photon_Blasters_Mojo, UnitType.Hero_Mojo);
    fields.get("damageAmount").set(WeaponType.Dual_Photon_Blasters_Mojo, 20);
    fields.get("damageBonus").set(WeaponType.Dual_Photon_Blasters_Mojo, 1);
    fields.get("damageCooldown").set(WeaponType.Dual_Photon_Blasters_Mojo, 30);
    fields.get("damageFactor").set(WeaponType.Dual_Photon_Blasters_Mojo, 1);
    fields
        .get("upgradeType")
        .set(WeaponType.Dual_Photon_Blasters_Mojo, UpgradeType.Protoss_Air_Weapons);
    fields.get("damageType").set(WeaponType.Dual_Photon_Blasters_Mojo, DamageType.Normal);
    fields.get("explosionType").set(WeaponType.Dual_Photon_Blasters_Mojo, ExplosionType.Normal);
    fields.get("minRange").set(WeaponType.Dual_Photon_Blasters_Mojo, 0);
    fields.get("maxRange").set(WeaponType.Dual_Photon_Blasters_Mojo, 128);
    fields.get("innerSplashRadius").set(WeaponType.Dual_Photon_Blasters_Mojo, 0);
    fields.get("medianSplashRadius").set(WeaponType.Dual_Photon_Blasters_Mojo, 0);
    fields.get("outerSplashRadius").set(WeaponType.Dual_Photon_Blasters_Mojo, 0);
    fields.get("targetsAir").set(WeaponType.Dual_Photon_Blasters_Mojo, false);
    fields.get("targetsGround").set(WeaponType.Dual_Photon_Blasters_Mojo, true);
    fields.get("targetsMechanical").set(WeaponType.Dual_Photon_Blasters_Mojo, false);
    fields.get("targetsOrganic").set(WeaponType.Dual_Photon_Blasters_Mojo, false);
    fields.get("targetsNonBuilding").set(WeaponType.Dual_Photon_Blasters_Mojo, false);
    fields.get("targetsNonRobotic").set(WeaponType.Dual_Photon_Blasters_Mojo, false);
    fields.get("targetsTerrain").set(WeaponType.Dual_Photon_Blasters_Mojo, false);
    fields.get("targetsOrgOrMech").set(WeaponType.Dual_Photon_Blasters_Mojo, false);
    fields.get("targetsOwn").set(WeaponType.Dual_Photon_Blasters_Mojo, false);
  }

  private static void initializeWeaponType_Anti_Matter_Missiles_Mojo() throws Exception {
    Class<?> c = WeaponType.class;
    Map<String, Field> fields =
        Stream.of(c.getDeclaredFields())
            .collect(
                Collectors.toMap(
                    f -> f.getName(),
                    f -> {
                      f.setAccessible(true);
                      return f;
                    }));
    fields.get("iD").set(WeaponType.Anti_Matter_Missiles_Mojo, 76);
    fields.get("tech").set(WeaponType.Anti_Matter_Missiles_Mojo, TechType.None);
    fields.get("whatUses").set(WeaponType.Anti_Matter_Missiles_Mojo, UnitType.Hero_Mojo);
    fields.get("damageAmount").set(WeaponType.Anti_Matter_Missiles_Mojo, 28);
    fields.get("damageBonus").set(WeaponType.Anti_Matter_Missiles_Mojo, 1);
    fields.get("damageCooldown").set(WeaponType.Anti_Matter_Missiles_Mojo, 22);
    fields.get("damageFactor").set(WeaponType.Anti_Matter_Missiles_Mojo, 2);
    fields
        .get("upgradeType")
        .set(WeaponType.Anti_Matter_Missiles_Mojo, UpgradeType.Protoss_Air_Weapons);
    fields.get("damageType").set(WeaponType.Anti_Matter_Missiles_Mojo, DamageType.Explosive);
    fields.get("explosionType").set(WeaponType.Anti_Matter_Missiles_Mojo, ExplosionType.Normal);
    fields.get("minRange").set(WeaponType.Anti_Matter_Missiles_Mojo, 0);
    fields.get("maxRange").set(WeaponType.Anti_Matter_Missiles_Mojo, 128);
    fields.get("innerSplashRadius").set(WeaponType.Anti_Matter_Missiles_Mojo, 0);
    fields.get("medianSplashRadius").set(WeaponType.Anti_Matter_Missiles_Mojo, 0);
    fields.get("outerSplashRadius").set(WeaponType.Anti_Matter_Missiles_Mojo, 0);
    fields.get("targetsAir").set(WeaponType.Anti_Matter_Missiles_Mojo, true);
    fields.get("targetsGround").set(WeaponType.Anti_Matter_Missiles_Mojo, false);
    fields.get("targetsMechanical").set(WeaponType.Anti_Matter_Missiles_Mojo, false);
    fields.get("targetsOrganic").set(WeaponType.Anti_Matter_Missiles_Mojo, false);
    fields.get("targetsNonBuilding").set(WeaponType.Anti_Matter_Missiles_Mojo, false);
    fields.get("targetsNonRobotic").set(WeaponType.Anti_Matter_Missiles_Mojo, false);
    fields.get("targetsTerrain").set(WeaponType.Anti_Matter_Missiles_Mojo, false);
    fields.get("targetsOrgOrMech").set(WeaponType.Anti_Matter_Missiles_Mojo, false);
    fields.get("targetsOwn").set(WeaponType.Anti_Matter_Missiles_Mojo, false);
  }

  private static void initializeWeaponType_Phase_Disruptor_Cannon() throws Exception {
    Class<?> c = WeaponType.class;
    Map<String, Field> fields =
        Stream.of(c.getDeclaredFields())
            .collect(
                Collectors.toMap(
                    f -> f.getName(),
                    f -> {
                      f.setAccessible(true);
                      return f;
                    }));
    fields.get("iD").set(WeaponType.Phase_Disruptor_Cannon, 77);
    fields.get("tech").set(WeaponType.Phase_Disruptor_Cannon, TechType.None);
    fields.get("whatUses").set(WeaponType.Phase_Disruptor_Cannon, UnitType.Protoss_Arbiter);
    fields.get("damageAmount").set(WeaponType.Phase_Disruptor_Cannon, 10);
    fields.get("damageBonus").set(WeaponType.Phase_Disruptor_Cannon, 1);
    fields.get("damageCooldown").set(WeaponType.Phase_Disruptor_Cannon, 45);
    fields.get("damageFactor").set(WeaponType.Phase_Disruptor_Cannon, 1);
    fields
        .get("upgradeType")
        .set(WeaponType.Phase_Disruptor_Cannon, UpgradeType.Protoss_Air_Weapons);
    fields.get("damageType").set(WeaponType.Phase_Disruptor_Cannon, DamageType.Explosive);
    fields.get("explosionType").set(WeaponType.Phase_Disruptor_Cannon, ExplosionType.Normal);
    fields.get("minRange").set(WeaponType.Phase_Disruptor_Cannon, 0);
    fields.get("maxRange").set(WeaponType.Phase_Disruptor_Cannon, 160);
    fields.get("innerSplashRadius").set(WeaponType.Phase_Disruptor_Cannon, 0);
    fields.get("medianSplashRadius").set(WeaponType.Phase_Disruptor_Cannon, 0);
    fields.get("outerSplashRadius").set(WeaponType.Phase_Disruptor_Cannon, 0);
    fields.get("targetsAir").set(WeaponType.Phase_Disruptor_Cannon, true);
    fields.get("targetsGround").set(WeaponType.Phase_Disruptor_Cannon, true);
    fields.get("targetsMechanical").set(WeaponType.Phase_Disruptor_Cannon, false);
    fields.get("targetsOrganic").set(WeaponType.Phase_Disruptor_Cannon, false);
    fields.get("targetsNonBuilding").set(WeaponType.Phase_Disruptor_Cannon, false);
    fields.get("targetsNonRobotic").set(WeaponType.Phase_Disruptor_Cannon, false);
    fields.get("targetsTerrain").set(WeaponType.Phase_Disruptor_Cannon, false);
    fields.get("targetsOrgOrMech").set(WeaponType.Phase_Disruptor_Cannon, false);
    fields.get("targetsOwn").set(WeaponType.Phase_Disruptor_Cannon, false);
  }

  private static void initializeWeaponType_Phase_Disruptor_Cannon_Danimoth() throws Exception {
    Class<?> c = WeaponType.class;
    Map<String, Field> fields =
        Stream.of(c.getDeclaredFields())
            .collect(
                Collectors.toMap(
                    f -> f.getName(),
                    f -> {
                      f.setAccessible(true);
                      return f;
                    }));
    fields.get("iD").set(WeaponType.Phase_Disruptor_Cannon_Danimoth, 78);
    fields.get("tech").set(WeaponType.Phase_Disruptor_Cannon_Danimoth, TechType.None);
    fields.get("whatUses").set(WeaponType.Phase_Disruptor_Cannon_Danimoth, UnitType.Hero_Danimoth);
    fields.get("damageAmount").set(WeaponType.Phase_Disruptor_Cannon_Danimoth, 20);
    fields.get("damageBonus").set(WeaponType.Phase_Disruptor_Cannon_Danimoth, 1);
    fields.get("damageCooldown").set(WeaponType.Phase_Disruptor_Cannon_Danimoth, 45);
    fields.get("damageFactor").set(WeaponType.Phase_Disruptor_Cannon_Danimoth, 1);
    fields
        .get("upgradeType")
        .set(WeaponType.Phase_Disruptor_Cannon_Danimoth, UpgradeType.Protoss_Air_Weapons);
    fields.get("damageType").set(WeaponType.Phase_Disruptor_Cannon_Danimoth, DamageType.Explosive);
    fields
        .get("explosionType")
        .set(WeaponType.Phase_Disruptor_Cannon_Danimoth, ExplosionType.Normal);
    fields.get("minRange").set(WeaponType.Phase_Disruptor_Cannon_Danimoth, 0);
    fields.get("maxRange").set(WeaponType.Phase_Disruptor_Cannon_Danimoth, 160);
    fields.get("innerSplashRadius").set(WeaponType.Phase_Disruptor_Cannon_Danimoth, 0);
    fields.get("medianSplashRadius").set(WeaponType.Phase_Disruptor_Cannon_Danimoth, 0);
    fields.get("outerSplashRadius").set(WeaponType.Phase_Disruptor_Cannon_Danimoth, 0);
    fields.get("targetsAir").set(WeaponType.Phase_Disruptor_Cannon_Danimoth, true);
    fields.get("targetsGround").set(WeaponType.Phase_Disruptor_Cannon_Danimoth, true);
    fields.get("targetsMechanical").set(WeaponType.Phase_Disruptor_Cannon_Danimoth, false);
    fields.get("targetsOrganic").set(WeaponType.Phase_Disruptor_Cannon_Danimoth, false);
    fields.get("targetsNonBuilding").set(WeaponType.Phase_Disruptor_Cannon_Danimoth, false);
    fields.get("targetsNonRobotic").set(WeaponType.Phase_Disruptor_Cannon_Danimoth, false);
    fields.get("targetsTerrain").set(WeaponType.Phase_Disruptor_Cannon_Danimoth, false);
    fields.get("targetsOrgOrMech").set(WeaponType.Phase_Disruptor_Cannon_Danimoth, false);
    fields.get("targetsOwn").set(WeaponType.Phase_Disruptor_Cannon_Danimoth, false);
  }

  private static void initializeWeaponType_Pulse_Cannon() throws Exception {
    Class<?> c = WeaponType.class;
    Map<String, Field> fields =
        Stream.of(c.getDeclaredFields())
            .collect(
                Collectors.toMap(
                    f -> f.getName(),
                    f -> {
                      f.setAccessible(true);
                      return f;
                    }));
    fields.get("iD").set(WeaponType.Pulse_Cannon, 79);
    fields.get("tech").set(WeaponType.Pulse_Cannon, TechType.None);
    fields.get("whatUses").set(WeaponType.Pulse_Cannon, UnitType.Protoss_Interceptor);
    fields.get("damageAmount").set(WeaponType.Pulse_Cannon, 6);
    fields.get("damageBonus").set(WeaponType.Pulse_Cannon, 1);
    fields.get("damageCooldown").set(WeaponType.Pulse_Cannon, 1);
    fields.get("damageFactor").set(WeaponType.Pulse_Cannon, 1);
    fields.get("upgradeType").set(WeaponType.Pulse_Cannon, UpgradeType.Protoss_Air_Weapons);
    fields.get("damageType").set(WeaponType.Pulse_Cannon, DamageType.Normal);
    fields.get("explosionType").set(WeaponType.Pulse_Cannon, ExplosionType.Normal);
    fields.get("minRange").set(WeaponType.Pulse_Cannon, 0);
    fields.get("maxRange").set(WeaponType.Pulse_Cannon, 128);
    fields.get("innerSplashRadius").set(WeaponType.Pulse_Cannon, 0);
    fields.get("medianSplashRadius").set(WeaponType.Pulse_Cannon, 0);
    fields.get("outerSplashRadius").set(WeaponType.Pulse_Cannon, 0);
    fields.get("targetsAir").set(WeaponType.Pulse_Cannon, true);
    fields.get("targetsGround").set(WeaponType.Pulse_Cannon, true);
    fields.get("targetsMechanical").set(WeaponType.Pulse_Cannon, false);
    fields.get("targetsOrganic").set(WeaponType.Pulse_Cannon, false);
    fields.get("targetsNonBuilding").set(WeaponType.Pulse_Cannon, false);
    fields.get("targetsNonRobotic").set(WeaponType.Pulse_Cannon, false);
    fields.get("targetsTerrain").set(WeaponType.Pulse_Cannon, false);
    fields.get("targetsOrgOrMech").set(WeaponType.Pulse_Cannon, false);
    fields.get("targetsOwn").set(WeaponType.Pulse_Cannon, false);
  }

  private static void initializeWeaponType_STS_Photon_Cannon() throws Exception {
    Class<?> c = WeaponType.class;
    Map<String, Field> fields =
        Stream.of(c.getDeclaredFields())
            .collect(
                Collectors.toMap(
                    f -> f.getName(),
                    f -> {
                      f.setAccessible(true);
                      return f;
                    }));
    fields.get("iD").set(WeaponType.STS_Photon_Cannon, 80);
    fields.get("tech").set(WeaponType.STS_Photon_Cannon, TechType.None);
    fields.get("whatUses").set(WeaponType.STS_Photon_Cannon, UnitType.Protoss_Photon_Cannon);
    fields.get("damageAmount").set(WeaponType.STS_Photon_Cannon, 20);
    fields.get("damageBonus").set(WeaponType.STS_Photon_Cannon, 0);
    fields.get("damageCooldown").set(WeaponType.STS_Photon_Cannon, 22);
    fields.get("damageFactor").set(WeaponType.STS_Photon_Cannon, 1);
    fields.get("upgradeType").set(WeaponType.STS_Photon_Cannon, UpgradeType.Upgrade_60);
    fields.get("damageType").set(WeaponType.STS_Photon_Cannon, DamageType.Normal);
    fields.get("explosionType").set(WeaponType.STS_Photon_Cannon, ExplosionType.Normal);
    fields.get("minRange").set(WeaponType.STS_Photon_Cannon, 0);
    fields.get("maxRange").set(WeaponType.STS_Photon_Cannon, 224);
    fields.get("innerSplashRadius").set(WeaponType.STS_Photon_Cannon, 0);
    fields.get("medianSplashRadius").set(WeaponType.STS_Photon_Cannon, 0);
    fields.get("outerSplashRadius").set(WeaponType.STS_Photon_Cannon, 0);
    fields.get("targetsAir").set(WeaponType.STS_Photon_Cannon, false);
    fields.get("targetsGround").set(WeaponType.STS_Photon_Cannon, true);
    fields.get("targetsMechanical").set(WeaponType.STS_Photon_Cannon, false);
    fields.get("targetsOrganic").set(WeaponType.STS_Photon_Cannon, false);
    fields.get("targetsNonBuilding").set(WeaponType.STS_Photon_Cannon, false);
    fields.get("targetsNonRobotic").set(WeaponType.STS_Photon_Cannon, false);
    fields.get("targetsTerrain").set(WeaponType.STS_Photon_Cannon, false);
    fields.get("targetsOrgOrMech").set(WeaponType.STS_Photon_Cannon, false);
    fields.get("targetsOwn").set(WeaponType.STS_Photon_Cannon, false);
  }

  private static void initializeWeaponType_STA_Photon_Cannon() throws Exception {
    Class<?> c = WeaponType.class;
    Map<String, Field> fields =
        Stream.of(c.getDeclaredFields())
            .collect(
                Collectors.toMap(
                    f -> f.getName(),
                    f -> {
                      f.setAccessible(true);
                      return f;
                    }));
    fields.get("iD").set(WeaponType.STA_Photon_Cannon, 81);
    fields.get("tech").set(WeaponType.STA_Photon_Cannon, TechType.None);
    fields.get("whatUses").set(WeaponType.STA_Photon_Cannon, UnitType.Protoss_Photon_Cannon);
    fields.get("damageAmount").set(WeaponType.STA_Photon_Cannon, 20);
    fields.get("damageBonus").set(WeaponType.STA_Photon_Cannon, 0);
    fields.get("damageCooldown").set(WeaponType.STA_Photon_Cannon, 22);
    fields.get("damageFactor").set(WeaponType.STA_Photon_Cannon, 1);
    fields.get("upgradeType").set(WeaponType.STA_Photon_Cannon, UpgradeType.Upgrade_60);
    fields.get("damageType").set(WeaponType.STA_Photon_Cannon, DamageType.Normal);
    fields.get("explosionType").set(WeaponType.STA_Photon_Cannon, ExplosionType.Normal);
    fields.get("minRange").set(WeaponType.STA_Photon_Cannon, 0);
    fields.get("maxRange").set(WeaponType.STA_Photon_Cannon, 224);
    fields.get("innerSplashRadius").set(WeaponType.STA_Photon_Cannon, 0);
    fields.get("medianSplashRadius").set(WeaponType.STA_Photon_Cannon, 0);
    fields.get("outerSplashRadius").set(WeaponType.STA_Photon_Cannon, 0);
    fields.get("targetsAir").set(WeaponType.STA_Photon_Cannon, true);
    fields.get("targetsGround").set(WeaponType.STA_Photon_Cannon, false);
    fields.get("targetsMechanical").set(WeaponType.STA_Photon_Cannon, false);
    fields.get("targetsOrganic").set(WeaponType.STA_Photon_Cannon, false);
    fields.get("targetsNonBuilding").set(WeaponType.STA_Photon_Cannon, false);
    fields.get("targetsNonRobotic").set(WeaponType.STA_Photon_Cannon, false);
    fields.get("targetsTerrain").set(WeaponType.STA_Photon_Cannon, false);
    fields.get("targetsOrgOrMech").set(WeaponType.STA_Photon_Cannon, false);
    fields.get("targetsOwn").set(WeaponType.STA_Photon_Cannon, false);
  }

  private static void initializeWeaponType_Scarab() throws Exception {
    Class<?> c = WeaponType.class;
    Map<String, Field> fields =
        Stream.of(c.getDeclaredFields())
            .collect(
                Collectors.toMap(
                    f -> f.getName(),
                    f -> {
                      f.setAccessible(true);
                      return f;
                    }));
    fields.get("iD").set(WeaponType.Scarab, 82);
    fields.get("tech").set(WeaponType.Scarab, TechType.None);
    fields.get("whatUses").set(WeaponType.Scarab, UnitType.Protoss_Scarab);
    fields.get("damageAmount").set(WeaponType.Scarab, 100);
    fields.get("damageBonus").set(WeaponType.Scarab, 25);
    fields.get("damageCooldown").set(WeaponType.Scarab, 1);
    fields.get("damageFactor").set(WeaponType.Scarab, 1);
    fields.get("upgradeType").set(WeaponType.Scarab, UpgradeType.Scarab_Damage);
    fields.get("damageType").set(WeaponType.Scarab, DamageType.Normal);
    fields.get("explosionType").set(WeaponType.Scarab, ExplosionType.Enemy_Splash);
    fields.get("minRange").set(WeaponType.Scarab, 0);
    fields.get("maxRange").set(WeaponType.Scarab, 128);
    fields.get("innerSplashRadius").set(WeaponType.Scarab, 20);
    fields.get("medianSplashRadius").set(WeaponType.Scarab, 40);
    fields.get("outerSplashRadius").set(WeaponType.Scarab, 60);
    fields.get("targetsAir").set(WeaponType.Scarab, false);
    fields.get("targetsGround").set(WeaponType.Scarab, true);
    fields.get("targetsMechanical").set(WeaponType.Scarab, false);
    fields.get("targetsOrganic").set(WeaponType.Scarab, false);
    fields.get("targetsNonBuilding").set(WeaponType.Scarab, false);
    fields.get("targetsNonRobotic").set(WeaponType.Scarab, false);
    fields.get("targetsTerrain").set(WeaponType.Scarab, false);
    fields.get("targetsOrgOrMech").set(WeaponType.Scarab, false);
    fields.get("targetsOwn").set(WeaponType.Scarab, false);
  }

  private static void initializeWeaponType_Stasis_Field() throws Exception {
    Class<?> c = WeaponType.class;
    Map<String, Field> fields =
        Stream.of(c.getDeclaredFields())
            .collect(
                Collectors.toMap(
                    f -> f.getName(),
                    f -> {
                      f.setAccessible(true);
                      return f;
                    }));
    fields.get("iD").set(WeaponType.Stasis_Field, 83);
    fields.get("tech").set(WeaponType.Stasis_Field, TechType.Stasis_Field);
    fields.get("whatUses").set(WeaponType.Stasis_Field, UnitType.Protoss_Arbiter);
    fields.get("damageAmount").set(WeaponType.Stasis_Field, 0);
    fields.get("damageBonus").set(WeaponType.Stasis_Field, 1);
    fields.get("damageCooldown").set(WeaponType.Stasis_Field, 1);
    fields.get("damageFactor").set(WeaponType.Stasis_Field, 1);
    fields.get("upgradeType").set(WeaponType.Stasis_Field, UpgradeType.Upgrade_60);
    fields.get("damageType").set(WeaponType.Stasis_Field, DamageType.Independent);
    fields.get("explosionType").set(WeaponType.Stasis_Field, ExplosionType.Stasis_Field);
    fields.get("minRange").set(WeaponType.Stasis_Field, 0);
    fields.get("maxRange").set(WeaponType.Stasis_Field, 288);
    fields.get("innerSplashRadius").set(WeaponType.Stasis_Field, 0);
    fields.get("medianSplashRadius").set(WeaponType.Stasis_Field, 0);
    fields.get("outerSplashRadius").set(WeaponType.Stasis_Field, 0);
    fields.get("targetsAir").set(WeaponType.Stasis_Field, true);
    fields.get("targetsGround").set(WeaponType.Stasis_Field, true);
    fields.get("targetsMechanical").set(WeaponType.Stasis_Field, false);
    fields.get("targetsOrganic").set(WeaponType.Stasis_Field, false);
    fields.get("targetsNonBuilding").set(WeaponType.Stasis_Field, false);
    fields.get("targetsNonRobotic").set(WeaponType.Stasis_Field, false);
    fields.get("targetsTerrain").set(WeaponType.Stasis_Field, true);
    fields.get("targetsOrgOrMech").set(WeaponType.Stasis_Field, false);
    fields.get("targetsOwn").set(WeaponType.Stasis_Field, false);
  }

  private static void initializeWeaponType_Psionic_Storm() throws Exception {
    Class<?> c = WeaponType.class;
    Map<String, Field> fields =
        Stream.of(c.getDeclaredFields())
            .collect(
                Collectors.toMap(
                    f -> f.getName(),
                    f -> {
                      f.setAccessible(true);
                      return f;
                    }));
    fields.get("iD").set(WeaponType.Psionic_Storm, 84);
    fields.get("tech").set(WeaponType.Psionic_Storm, TechType.Psionic_Storm);
    fields.get("whatUses").set(WeaponType.Psionic_Storm, UnitType.Protoss_High_Templar);
    fields.get("damageAmount").set(WeaponType.Psionic_Storm, 14);
    fields.get("damageBonus").set(WeaponType.Psionic_Storm, 1);
    fields.get("damageCooldown").set(WeaponType.Psionic_Storm, 45);
    fields.get("damageFactor").set(WeaponType.Psionic_Storm, 1);
    fields.get("upgradeType").set(WeaponType.Psionic_Storm, UpgradeType.Upgrade_60);
    fields.get("damageType").set(WeaponType.Psionic_Storm, DamageType.Ignore_Armor);
    fields.get("explosionType").set(WeaponType.Psionic_Storm, ExplosionType.Radial_Splash);
    fields.get("minRange").set(WeaponType.Psionic_Storm, 0);
    fields.get("maxRange").set(WeaponType.Psionic_Storm, 288);
    fields.get("innerSplashRadius").set(WeaponType.Psionic_Storm, 48);
    fields.get("medianSplashRadius").set(WeaponType.Psionic_Storm, 48);
    fields.get("outerSplashRadius").set(WeaponType.Psionic_Storm, 48);
    fields.get("targetsAir").set(WeaponType.Psionic_Storm, true);
    fields.get("targetsGround").set(WeaponType.Psionic_Storm, true);
    fields.get("targetsMechanical").set(WeaponType.Psionic_Storm, false);
    fields.get("targetsOrganic").set(WeaponType.Psionic_Storm, false);
    fields.get("targetsNonBuilding").set(WeaponType.Psionic_Storm, true);
    fields.get("targetsNonRobotic").set(WeaponType.Psionic_Storm, false);
    fields.get("targetsTerrain").set(WeaponType.Psionic_Storm, true);
    fields.get("targetsOrgOrMech").set(WeaponType.Psionic_Storm, false);
    fields.get("targetsOwn").set(WeaponType.Psionic_Storm, false);
  }

  private static void initializeWeaponType_Warp_Blades_Zeratul() throws Exception {
    Class<?> c = WeaponType.class;
    Map<String, Field> fields =
        Stream.of(c.getDeclaredFields())
            .collect(
                Collectors.toMap(
                    f -> f.getName(),
                    f -> {
                      f.setAccessible(true);
                      return f;
                    }));
    fields.get("iD").set(WeaponType.Warp_Blades_Zeratul, 85);
    fields.get("tech").set(WeaponType.Warp_Blades_Zeratul, TechType.None);
    fields.get("whatUses").set(WeaponType.Warp_Blades_Zeratul, UnitType.Hero_Zeratul);
    fields.get("damageAmount").set(WeaponType.Warp_Blades_Zeratul, 100);
    fields.get("damageBonus").set(WeaponType.Warp_Blades_Zeratul, 1);
    fields.get("damageCooldown").set(WeaponType.Warp_Blades_Zeratul, 22);
    fields.get("damageFactor").set(WeaponType.Warp_Blades_Zeratul, 1);
    fields
        .get("upgradeType")
        .set(WeaponType.Warp_Blades_Zeratul, UpgradeType.Protoss_Ground_Weapons);
    fields.get("damageType").set(WeaponType.Warp_Blades_Zeratul, DamageType.Normal);
    fields.get("explosionType").set(WeaponType.Warp_Blades_Zeratul, ExplosionType.Normal);
    fields.get("minRange").set(WeaponType.Warp_Blades_Zeratul, 0);
    fields.get("maxRange").set(WeaponType.Warp_Blades_Zeratul, 15);
    fields.get("innerSplashRadius").set(WeaponType.Warp_Blades_Zeratul, 0);
    fields.get("medianSplashRadius").set(WeaponType.Warp_Blades_Zeratul, 0);
    fields.get("outerSplashRadius").set(WeaponType.Warp_Blades_Zeratul, 0);
    fields.get("targetsAir").set(WeaponType.Warp_Blades_Zeratul, false);
    fields.get("targetsGround").set(WeaponType.Warp_Blades_Zeratul, true);
    fields.get("targetsMechanical").set(WeaponType.Warp_Blades_Zeratul, false);
    fields.get("targetsOrganic").set(WeaponType.Warp_Blades_Zeratul, false);
    fields.get("targetsNonBuilding").set(WeaponType.Warp_Blades_Zeratul, false);
    fields.get("targetsNonRobotic").set(WeaponType.Warp_Blades_Zeratul, false);
    fields.get("targetsTerrain").set(WeaponType.Warp_Blades_Zeratul, false);
    fields.get("targetsOrgOrMech").set(WeaponType.Warp_Blades_Zeratul, false);
    fields.get("targetsOwn").set(WeaponType.Warp_Blades_Zeratul, false);
  }

  private static void initializeWeaponType_Warp_Blades_Hero() throws Exception {
    Class<?> c = WeaponType.class;
    Map<String, Field> fields =
        Stream.of(c.getDeclaredFields())
            .collect(
                Collectors.toMap(
                    f -> f.getName(),
                    f -> {
                      f.setAccessible(true);
                      return f;
                    }));
    fields.get("iD").set(WeaponType.Warp_Blades_Hero, 86);
    fields.get("tech").set(WeaponType.Warp_Blades_Hero, TechType.None);
    fields.get("whatUses").set(WeaponType.Warp_Blades_Hero, UnitType.Hero_Dark_Templar);
    fields.get("damageAmount").set(WeaponType.Warp_Blades_Hero, 45);
    fields.get("damageBonus").set(WeaponType.Warp_Blades_Hero, 1);
    fields.get("damageCooldown").set(WeaponType.Warp_Blades_Hero, 30);
    fields.get("damageFactor").set(WeaponType.Warp_Blades_Hero, 1);
    fields.get("upgradeType").set(WeaponType.Warp_Blades_Hero, UpgradeType.Protoss_Ground_Weapons);
    fields.get("damageType").set(WeaponType.Warp_Blades_Hero, DamageType.Normal);
    fields.get("explosionType").set(WeaponType.Warp_Blades_Hero, ExplosionType.Normal);
    fields.get("minRange").set(WeaponType.Warp_Blades_Hero, 0);
    fields.get("maxRange").set(WeaponType.Warp_Blades_Hero, 15);
    fields.get("innerSplashRadius").set(WeaponType.Warp_Blades_Hero, 0);
    fields.get("medianSplashRadius").set(WeaponType.Warp_Blades_Hero, 0);
    fields.get("outerSplashRadius").set(WeaponType.Warp_Blades_Hero, 0);
    fields.get("targetsAir").set(WeaponType.Warp_Blades_Hero, false);
    fields.get("targetsGround").set(WeaponType.Warp_Blades_Hero, true);
    fields.get("targetsMechanical").set(WeaponType.Warp_Blades_Hero, false);
    fields.get("targetsOrganic").set(WeaponType.Warp_Blades_Hero, false);
    fields.get("targetsNonBuilding").set(WeaponType.Warp_Blades_Hero, false);
    fields.get("targetsNonRobotic").set(WeaponType.Warp_Blades_Hero, false);
    fields.get("targetsTerrain").set(WeaponType.Warp_Blades_Hero, false);
    fields.get("targetsOrgOrMech").set(WeaponType.Warp_Blades_Hero, false);
    fields.get("targetsOwn").set(WeaponType.Warp_Blades_Hero, false);
  }

  private static void initializeWeaponType_unk_87() throws Exception {
    Class<?> c = WeaponType.class;
    Map<String, Field> fields =
        Stream.of(c.getDeclaredFields())
            .collect(
                Collectors.toMap(
                    f -> f.getName(),
                    f -> {
                      f.setAccessible(true);
                      return f;
                    }));
    fields.get("iD").set(WeaponType.unk_87, 87);
    fields.get("tech").set(WeaponType.unk_87, null);
    fields.get("whatUses").set(WeaponType.unk_87, null);
    fields.get("damageAmount").set(WeaponType.unk_87, 0);
    fields.get("damageBonus").set(WeaponType.unk_87, 0);
    fields.get("damageCooldown").set(WeaponType.unk_87, 0);
    fields.get("damageFactor").set(WeaponType.unk_87, 0);
    fields.get("upgradeType").set(WeaponType.unk_87, null);
    fields.get("damageType").set(WeaponType.unk_87, null);
    fields.get("explosionType").set(WeaponType.unk_87, null);
    fields.get("minRange").set(WeaponType.unk_87, 0);
    fields.get("maxRange").set(WeaponType.unk_87, 0);
    fields.get("innerSplashRadius").set(WeaponType.unk_87, 0);
    fields.get("medianSplashRadius").set(WeaponType.unk_87, 0);
    fields.get("outerSplashRadius").set(WeaponType.unk_87, 0);
    fields.get("targetsAir").set(WeaponType.unk_87, false);
    fields.get("targetsGround").set(WeaponType.unk_87, false);
    fields.get("targetsMechanical").set(WeaponType.unk_87, false);
    fields.get("targetsOrganic").set(WeaponType.unk_87, false);
    fields.get("targetsNonBuilding").set(WeaponType.unk_87, false);
    fields.get("targetsNonRobotic").set(WeaponType.unk_87, false);
    fields.get("targetsTerrain").set(WeaponType.unk_87, false);
    fields.get("targetsOrgOrMech").set(WeaponType.unk_87, false);
    fields.get("targetsOwn").set(WeaponType.unk_87, false);
  }

  private static void initializeWeaponType_unk_88() throws Exception {
    Class<?> c = WeaponType.class;
    Map<String, Field> fields =
        Stream.of(c.getDeclaredFields())
            .collect(
                Collectors.toMap(
                    f -> f.getName(),
                    f -> {
                      f.setAccessible(true);
                      return f;
                    }));
    fields.get("iD").set(WeaponType.unk_88, 88);
    fields.get("tech").set(WeaponType.unk_88, null);
    fields.get("whatUses").set(WeaponType.unk_88, null);
    fields.get("damageAmount").set(WeaponType.unk_88, 0);
    fields.get("damageBonus").set(WeaponType.unk_88, 0);
    fields.get("damageCooldown").set(WeaponType.unk_88, 0);
    fields.get("damageFactor").set(WeaponType.unk_88, 0);
    fields.get("upgradeType").set(WeaponType.unk_88, null);
    fields.get("damageType").set(WeaponType.unk_88, null);
    fields.get("explosionType").set(WeaponType.unk_88, null);
    fields.get("minRange").set(WeaponType.unk_88, 0);
    fields.get("maxRange").set(WeaponType.unk_88, 0);
    fields.get("innerSplashRadius").set(WeaponType.unk_88, 0);
    fields.get("medianSplashRadius").set(WeaponType.unk_88, 0);
    fields.get("outerSplashRadius").set(WeaponType.unk_88, 0);
    fields.get("targetsAir").set(WeaponType.unk_88, false);
    fields.get("targetsGround").set(WeaponType.unk_88, false);
    fields.get("targetsMechanical").set(WeaponType.unk_88, false);
    fields.get("targetsOrganic").set(WeaponType.unk_88, false);
    fields.get("targetsNonBuilding").set(WeaponType.unk_88, false);
    fields.get("targetsNonRobotic").set(WeaponType.unk_88, false);
    fields.get("targetsTerrain").set(WeaponType.unk_88, false);
    fields.get("targetsOrgOrMech").set(WeaponType.unk_88, false);
    fields.get("targetsOwn").set(WeaponType.unk_88, false);
  }

  private static void initializeWeaponType_unk_89() throws Exception {
    Class<?> c = WeaponType.class;
    Map<String, Field> fields =
        Stream.of(c.getDeclaredFields())
            .collect(
                Collectors.toMap(
                    f -> f.getName(),
                    f -> {
                      f.setAccessible(true);
                      return f;
                    }));
    fields.get("iD").set(WeaponType.unk_89, 89);
    fields.get("tech").set(WeaponType.unk_89, null);
    fields.get("whatUses").set(WeaponType.unk_89, null);
    fields.get("damageAmount").set(WeaponType.unk_89, 0);
    fields.get("damageBonus").set(WeaponType.unk_89, 0);
    fields.get("damageCooldown").set(WeaponType.unk_89, 0);
    fields.get("damageFactor").set(WeaponType.unk_89, 0);
    fields.get("upgradeType").set(WeaponType.unk_89, null);
    fields.get("damageType").set(WeaponType.unk_89, null);
    fields.get("explosionType").set(WeaponType.unk_89, null);
    fields.get("minRange").set(WeaponType.unk_89, 0);
    fields.get("maxRange").set(WeaponType.unk_89, 0);
    fields.get("innerSplashRadius").set(WeaponType.unk_89, 0);
    fields.get("medianSplashRadius").set(WeaponType.unk_89, 0);
    fields.get("outerSplashRadius").set(WeaponType.unk_89, 0);
    fields.get("targetsAir").set(WeaponType.unk_89, false);
    fields.get("targetsGround").set(WeaponType.unk_89, false);
    fields.get("targetsMechanical").set(WeaponType.unk_89, false);
    fields.get("targetsOrganic").set(WeaponType.unk_89, false);
    fields.get("targetsNonBuilding").set(WeaponType.unk_89, false);
    fields.get("targetsNonRobotic").set(WeaponType.unk_89, false);
    fields.get("targetsTerrain").set(WeaponType.unk_89, false);
    fields.get("targetsOrgOrMech").set(WeaponType.unk_89, false);
    fields.get("targetsOwn").set(WeaponType.unk_89, false);
  }

  private static void initializeWeaponType_unk_90() throws Exception {
    Class<?> c = WeaponType.class;
    Map<String, Field> fields =
        Stream.of(c.getDeclaredFields())
            .collect(
                Collectors.toMap(
                    f -> f.getName(),
                    f -> {
                      f.setAccessible(true);
                      return f;
                    }));
    fields.get("iD").set(WeaponType.unk_90, 90);
    fields.get("tech").set(WeaponType.unk_90, null);
    fields.get("whatUses").set(WeaponType.unk_90, null);
    fields.get("damageAmount").set(WeaponType.unk_90, 0);
    fields.get("damageBonus").set(WeaponType.unk_90, 0);
    fields.get("damageCooldown").set(WeaponType.unk_90, 0);
    fields.get("damageFactor").set(WeaponType.unk_90, 0);
    fields.get("upgradeType").set(WeaponType.unk_90, null);
    fields.get("damageType").set(WeaponType.unk_90, null);
    fields.get("explosionType").set(WeaponType.unk_90, null);
    fields.get("minRange").set(WeaponType.unk_90, 0);
    fields.get("maxRange").set(WeaponType.unk_90, 0);
    fields.get("innerSplashRadius").set(WeaponType.unk_90, 0);
    fields.get("medianSplashRadius").set(WeaponType.unk_90, 0);
    fields.get("outerSplashRadius").set(WeaponType.unk_90, 0);
    fields.get("targetsAir").set(WeaponType.unk_90, false);
    fields.get("targetsGround").set(WeaponType.unk_90, false);
    fields.get("targetsMechanical").set(WeaponType.unk_90, false);
    fields.get("targetsOrganic").set(WeaponType.unk_90, false);
    fields.get("targetsNonBuilding").set(WeaponType.unk_90, false);
    fields.get("targetsNonRobotic").set(WeaponType.unk_90, false);
    fields.get("targetsTerrain").set(WeaponType.unk_90, false);
    fields.get("targetsOrgOrMech").set(WeaponType.unk_90, false);
    fields.get("targetsOwn").set(WeaponType.unk_90, false);
  }

  private static void initializeWeaponType_unk_91() throws Exception {
    Class<?> c = WeaponType.class;
    Map<String, Field> fields =
        Stream.of(c.getDeclaredFields())
            .collect(
                Collectors.toMap(
                    f -> f.getName(),
                    f -> {
                      f.setAccessible(true);
                      return f;
                    }));
    fields.get("iD").set(WeaponType.unk_91, 91);
    fields.get("tech").set(WeaponType.unk_91, null);
    fields.get("whatUses").set(WeaponType.unk_91, null);
    fields.get("damageAmount").set(WeaponType.unk_91, 0);
    fields.get("damageBonus").set(WeaponType.unk_91, 0);
    fields.get("damageCooldown").set(WeaponType.unk_91, 0);
    fields.get("damageFactor").set(WeaponType.unk_91, 0);
    fields.get("upgradeType").set(WeaponType.unk_91, null);
    fields.get("damageType").set(WeaponType.unk_91, null);
    fields.get("explosionType").set(WeaponType.unk_91, null);
    fields.get("minRange").set(WeaponType.unk_91, 0);
    fields.get("maxRange").set(WeaponType.unk_91, 0);
    fields.get("innerSplashRadius").set(WeaponType.unk_91, 0);
    fields.get("medianSplashRadius").set(WeaponType.unk_91, 0);
    fields.get("outerSplashRadius").set(WeaponType.unk_91, 0);
    fields.get("targetsAir").set(WeaponType.unk_91, false);
    fields.get("targetsGround").set(WeaponType.unk_91, false);
    fields.get("targetsMechanical").set(WeaponType.unk_91, false);
    fields.get("targetsOrganic").set(WeaponType.unk_91, false);
    fields.get("targetsNonBuilding").set(WeaponType.unk_91, false);
    fields.get("targetsNonRobotic").set(WeaponType.unk_91, false);
    fields.get("targetsTerrain").set(WeaponType.unk_91, false);
    fields.get("targetsOrgOrMech").set(WeaponType.unk_91, false);
    fields.get("targetsOwn").set(WeaponType.unk_91, false);
  }

  private static void initializeWeaponType_Platform_Laser_Battery() throws Exception {
    Class<?> c = WeaponType.class;
    Map<String, Field> fields =
        Stream.of(c.getDeclaredFields())
            .collect(
                Collectors.toMap(
                    f -> f.getName(),
                    f -> {
                      f.setAccessible(true);
                      return f;
                    }));
    fields.get("iD").set(WeaponType.Platform_Laser_Battery, 92);
    fields.get("tech").set(WeaponType.Platform_Laser_Battery, null);
    fields.get("whatUses").set(WeaponType.Platform_Laser_Battery, null);
    fields.get("damageAmount").set(WeaponType.Platform_Laser_Battery, 0);
    fields.get("damageBonus").set(WeaponType.Platform_Laser_Battery, 0);
    fields.get("damageCooldown").set(WeaponType.Platform_Laser_Battery, 0);
    fields.get("damageFactor").set(WeaponType.Platform_Laser_Battery, 0);
    fields.get("upgradeType").set(WeaponType.Platform_Laser_Battery, null);
    fields.get("damageType").set(WeaponType.Platform_Laser_Battery, null);
    fields.get("explosionType").set(WeaponType.Platform_Laser_Battery, null);
    fields.get("minRange").set(WeaponType.Platform_Laser_Battery, 0);
    fields.get("maxRange").set(WeaponType.Platform_Laser_Battery, 0);
    fields.get("innerSplashRadius").set(WeaponType.Platform_Laser_Battery, 0);
    fields.get("medianSplashRadius").set(WeaponType.Platform_Laser_Battery, 0);
    fields.get("outerSplashRadius").set(WeaponType.Platform_Laser_Battery, 0);
    fields.get("targetsAir").set(WeaponType.Platform_Laser_Battery, false);
    fields.get("targetsGround").set(WeaponType.Platform_Laser_Battery, false);
    fields.get("targetsMechanical").set(WeaponType.Platform_Laser_Battery, false);
    fields.get("targetsOrganic").set(WeaponType.Platform_Laser_Battery, false);
    fields.get("targetsNonBuilding").set(WeaponType.Platform_Laser_Battery, false);
    fields.get("targetsNonRobotic").set(WeaponType.Platform_Laser_Battery, false);
    fields.get("targetsTerrain").set(WeaponType.Platform_Laser_Battery, false);
    fields.get("targetsOrgOrMech").set(WeaponType.Platform_Laser_Battery, false);
    fields.get("targetsOwn").set(WeaponType.Platform_Laser_Battery, false);
  }

  private static void initializeWeaponType_Independant_Laser_Battery() throws Exception {
    Class<?> c = WeaponType.class;
    Map<String, Field> fields =
        Stream.of(c.getDeclaredFields())
            .collect(
                Collectors.toMap(
                    f -> f.getName(),
                    f -> {
                      f.setAccessible(true);
                      return f;
                    }));
    fields.get("iD").set(WeaponType.Independant_Laser_Battery, 93);
    fields.get("tech").set(WeaponType.Independant_Laser_Battery, TechType.None);
    fields
        .get("whatUses")
        .set(WeaponType.Independant_Laser_Battery, UnitType.Special_Independant_Starport);
    fields.get("damageAmount").set(WeaponType.Independant_Laser_Battery, 7);
    fields.get("damageBonus").set(WeaponType.Independant_Laser_Battery, 1);
    fields.get("damageCooldown").set(WeaponType.Independant_Laser_Battery, 22);
    fields.get("damageFactor").set(WeaponType.Independant_Laser_Battery, 1);
    fields.get("upgradeType").set(WeaponType.Independant_Laser_Battery, UpgradeType.Upgrade_60);
    fields.get("damageType").set(WeaponType.Independant_Laser_Battery, DamageType.Normal);
    fields.get("explosionType").set(WeaponType.Independant_Laser_Battery, ExplosionType.Normal);
    fields.get("minRange").set(WeaponType.Independant_Laser_Battery, 0);
    fields.get("maxRange").set(WeaponType.Independant_Laser_Battery, 128);
    fields.get("innerSplashRadius").set(WeaponType.Independant_Laser_Battery, 0);
    fields.get("medianSplashRadius").set(WeaponType.Independant_Laser_Battery, 0);
    fields.get("outerSplashRadius").set(WeaponType.Independant_Laser_Battery, 0);
    fields.get("targetsAir").set(WeaponType.Independant_Laser_Battery, true);
    fields.get("targetsGround").set(WeaponType.Independant_Laser_Battery, false);
    fields.get("targetsMechanical").set(WeaponType.Independant_Laser_Battery, false);
    fields.get("targetsOrganic").set(WeaponType.Independant_Laser_Battery, false);
    fields.get("targetsNonBuilding").set(WeaponType.Independant_Laser_Battery, false);
    fields.get("targetsNonRobotic").set(WeaponType.Independant_Laser_Battery, false);
    fields.get("targetsTerrain").set(WeaponType.Independant_Laser_Battery, false);
    fields.get("targetsOrgOrMech").set(WeaponType.Independant_Laser_Battery, false);
    fields.get("targetsOwn").set(WeaponType.Independant_Laser_Battery, false);
  }

  private static void initializeWeaponType_unk_94() throws Exception {
    Class<?> c = WeaponType.class;
    Map<String, Field> fields =
        Stream.of(c.getDeclaredFields())
            .collect(
                Collectors.toMap(
                    f -> f.getName(),
                    f -> {
                      f.setAccessible(true);
                      return f;
                    }));
    fields.get("iD").set(WeaponType.unk_94, 94);
    fields.get("tech").set(WeaponType.unk_94, null);
    fields.get("whatUses").set(WeaponType.unk_94, null);
    fields.get("damageAmount").set(WeaponType.unk_94, 0);
    fields.get("damageBonus").set(WeaponType.unk_94, 0);
    fields.get("damageCooldown").set(WeaponType.unk_94, 0);
    fields.get("damageFactor").set(WeaponType.unk_94, 0);
    fields.get("upgradeType").set(WeaponType.unk_94, null);
    fields.get("damageType").set(WeaponType.unk_94, null);
    fields.get("explosionType").set(WeaponType.unk_94, null);
    fields.get("minRange").set(WeaponType.unk_94, 0);
    fields.get("maxRange").set(WeaponType.unk_94, 0);
    fields.get("innerSplashRadius").set(WeaponType.unk_94, 0);
    fields.get("medianSplashRadius").set(WeaponType.unk_94, 0);
    fields.get("outerSplashRadius").set(WeaponType.unk_94, 0);
    fields.get("targetsAir").set(WeaponType.unk_94, false);
    fields.get("targetsGround").set(WeaponType.unk_94, false);
    fields.get("targetsMechanical").set(WeaponType.unk_94, false);
    fields.get("targetsOrganic").set(WeaponType.unk_94, false);
    fields.get("targetsNonBuilding").set(WeaponType.unk_94, false);
    fields.get("targetsNonRobotic").set(WeaponType.unk_94, false);
    fields.get("targetsTerrain").set(WeaponType.unk_94, false);
    fields.get("targetsOrgOrMech").set(WeaponType.unk_94, false);
    fields.get("targetsOwn").set(WeaponType.unk_94, false);
  }

  private static void initializeWeaponType_unk_95() throws Exception {
    Class<?> c = WeaponType.class;
    Map<String, Field> fields =
        Stream.of(c.getDeclaredFields())
            .collect(
                Collectors.toMap(
                    f -> f.getName(),
                    f -> {
                      f.setAccessible(true);
                      return f;
                    }));
    fields.get("iD").set(WeaponType.unk_95, 95);
    fields.get("tech").set(WeaponType.unk_95, null);
    fields.get("whatUses").set(WeaponType.unk_95, null);
    fields.get("damageAmount").set(WeaponType.unk_95, 0);
    fields.get("damageBonus").set(WeaponType.unk_95, 0);
    fields.get("damageCooldown").set(WeaponType.unk_95, 0);
    fields.get("damageFactor").set(WeaponType.unk_95, 0);
    fields.get("upgradeType").set(WeaponType.unk_95, null);
    fields.get("damageType").set(WeaponType.unk_95, null);
    fields.get("explosionType").set(WeaponType.unk_95, null);
    fields.get("minRange").set(WeaponType.unk_95, 0);
    fields.get("maxRange").set(WeaponType.unk_95, 0);
    fields.get("innerSplashRadius").set(WeaponType.unk_95, 0);
    fields.get("medianSplashRadius").set(WeaponType.unk_95, 0);
    fields.get("outerSplashRadius").set(WeaponType.unk_95, 0);
    fields.get("targetsAir").set(WeaponType.unk_95, false);
    fields.get("targetsGround").set(WeaponType.unk_95, false);
    fields.get("targetsMechanical").set(WeaponType.unk_95, false);
    fields.get("targetsOrganic").set(WeaponType.unk_95, false);
    fields.get("targetsNonBuilding").set(WeaponType.unk_95, false);
    fields.get("targetsNonRobotic").set(WeaponType.unk_95, false);
    fields.get("targetsTerrain").set(WeaponType.unk_95, false);
    fields.get("targetsOrgOrMech").set(WeaponType.unk_95, false);
    fields.get("targetsOwn").set(WeaponType.unk_95, false);
  }

  private static void initializeWeaponType_Twin_Autocannons_Floor_Trap() throws Exception {
    Class<?> c = WeaponType.class;
    Map<String, Field> fields =
        Stream.of(c.getDeclaredFields())
            .collect(
                Collectors.toMap(
                    f -> f.getName(),
                    f -> {
                      f.setAccessible(true);
                      return f;
                    }));
    fields.get("iD").set(WeaponType.Twin_Autocannons_Floor_Trap, 96);
    fields.get("tech").set(WeaponType.Twin_Autocannons_Floor_Trap, TechType.None);
    fields
        .get("whatUses")
        .set(WeaponType.Twin_Autocannons_Floor_Trap, UnitType.Special_Floor_Gun_Trap);
    fields.get("damageAmount").set(WeaponType.Twin_Autocannons_Floor_Trap, 10);
    fields.get("damageBonus").set(WeaponType.Twin_Autocannons_Floor_Trap, 1);
    fields.get("damageCooldown").set(WeaponType.Twin_Autocannons_Floor_Trap, 22);
    fields.get("damageFactor").set(WeaponType.Twin_Autocannons_Floor_Trap, 1);
    fields
        .get("upgradeType")
        .set(WeaponType.Twin_Autocannons_Floor_Trap, UpgradeType.Terran_Vehicle_Weapons);
    fields.get("damageType").set(WeaponType.Twin_Autocannons_Floor_Trap, DamageType.Normal);
    fields.get("explosionType").set(WeaponType.Twin_Autocannons_Floor_Trap, ExplosionType.Normal);
    fields.get("minRange").set(WeaponType.Twin_Autocannons_Floor_Trap, 0);
    fields.get("maxRange").set(WeaponType.Twin_Autocannons_Floor_Trap, 160);
    fields.get("innerSplashRadius").set(WeaponType.Twin_Autocannons_Floor_Trap, 0);
    fields.get("medianSplashRadius").set(WeaponType.Twin_Autocannons_Floor_Trap, 0);
    fields.get("outerSplashRadius").set(WeaponType.Twin_Autocannons_Floor_Trap, 0);
    fields.get("targetsAir").set(WeaponType.Twin_Autocannons_Floor_Trap, false);
    fields.get("targetsGround").set(WeaponType.Twin_Autocannons_Floor_Trap, true);
    fields.get("targetsMechanical").set(WeaponType.Twin_Autocannons_Floor_Trap, false);
    fields.get("targetsOrganic").set(WeaponType.Twin_Autocannons_Floor_Trap, false);
    fields.get("targetsNonBuilding").set(WeaponType.Twin_Autocannons_Floor_Trap, false);
    fields.get("targetsNonRobotic").set(WeaponType.Twin_Autocannons_Floor_Trap, false);
    fields.get("targetsTerrain").set(WeaponType.Twin_Autocannons_Floor_Trap, false);
    fields.get("targetsOrgOrMech").set(WeaponType.Twin_Autocannons_Floor_Trap, false);
    fields.get("targetsOwn").set(WeaponType.Twin_Autocannons_Floor_Trap, false);
  }

  private static void initializeWeaponType_Hellfire_Missile_Pack_Wall_Trap() throws Exception {
    Class<?> c = WeaponType.class;
    Map<String, Field> fields =
        Stream.of(c.getDeclaredFields())
            .collect(
                Collectors.toMap(
                    f -> f.getName(),
                    f -> {
                      f.setAccessible(true);
                      return f;
                    }));
    fields.get("iD").set(WeaponType.Hellfire_Missile_Pack_Wall_Trap, 97);
    fields.get("tech").set(WeaponType.Hellfire_Missile_Pack_Wall_Trap, TechType.None);
    fields
        .get("whatUses")
        .set(WeaponType.Hellfire_Missile_Pack_Wall_Trap, UnitType.Special_Wall_Missile_Trap);
    fields.get("damageAmount").set(WeaponType.Hellfire_Missile_Pack_Wall_Trap, 10);
    fields.get("damageBonus").set(WeaponType.Hellfire_Missile_Pack_Wall_Trap, 1);
    fields.get("damageCooldown").set(WeaponType.Hellfire_Missile_Pack_Wall_Trap, 22);
    fields.get("damageFactor").set(WeaponType.Hellfire_Missile_Pack_Wall_Trap, 2);
    fields
        .get("upgradeType")
        .set(WeaponType.Hellfire_Missile_Pack_Wall_Trap, UpgradeType.Terran_Vehicle_Weapons);
    fields.get("damageType").set(WeaponType.Hellfire_Missile_Pack_Wall_Trap, DamageType.Explosive);
    fields
        .get("explosionType")
        .set(WeaponType.Hellfire_Missile_Pack_Wall_Trap, ExplosionType.Normal);
    fields.get("minRange").set(WeaponType.Hellfire_Missile_Pack_Wall_Trap, 0);
    fields.get("maxRange").set(WeaponType.Hellfire_Missile_Pack_Wall_Trap, 160);
    fields.get("innerSplashRadius").set(WeaponType.Hellfire_Missile_Pack_Wall_Trap, 0);
    fields.get("medianSplashRadius").set(WeaponType.Hellfire_Missile_Pack_Wall_Trap, 0);
    fields.get("outerSplashRadius").set(WeaponType.Hellfire_Missile_Pack_Wall_Trap, 0);
    fields.get("targetsAir").set(WeaponType.Hellfire_Missile_Pack_Wall_Trap, false);
    fields.get("targetsGround").set(WeaponType.Hellfire_Missile_Pack_Wall_Trap, true);
    fields.get("targetsMechanical").set(WeaponType.Hellfire_Missile_Pack_Wall_Trap, false);
    fields.get("targetsOrganic").set(WeaponType.Hellfire_Missile_Pack_Wall_Trap, false);
    fields.get("targetsNonBuilding").set(WeaponType.Hellfire_Missile_Pack_Wall_Trap, false);
    fields.get("targetsNonRobotic").set(WeaponType.Hellfire_Missile_Pack_Wall_Trap, false);
    fields.get("targetsTerrain").set(WeaponType.Hellfire_Missile_Pack_Wall_Trap, false);
    fields.get("targetsOrgOrMech").set(WeaponType.Hellfire_Missile_Pack_Wall_Trap, false);
    fields.get("targetsOwn").set(WeaponType.Hellfire_Missile_Pack_Wall_Trap, false);
  }

  private static void initializeWeaponType_Flame_Thrower_Wall_Trap() throws Exception {
    Class<?> c = WeaponType.class;
    Map<String, Field> fields =
        Stream.of(c.getDeclaredFields())
            .collect(
                Collectors.toMap(
                    f -> f.getName(),
                    f -> {
                      f.setAccessible(true);
                      return f;
                    }));
    fields.get("iD").set(WeaponType.Flame_Thrower_Wall_Trap, 98);
    fields.get("tech").set(WeaponType.Flame_Thrower_Wall_Trap, TechType.None);
    fields
        .get("whatUses")
        .set(WeaponType.Flame_Thrower_Wall_Trap, UnitType.Special_Wall_Flame_Trap);
    fields.get("damageAmount").set(WeaponType.Flame_Thrower_Wall_Trap, 8);
    fields.get("damageBonus").set(WeaponType.Flame_Thrower_Wall_Trap, 1);
    fields.get("damageCooldown").set(WeaponType.Flame_Thrower_Wall_Trap, 22);
    fields.get("damageFactor").set(WeaponType.Flame_Thrower_Wall_Trap, 1);
    fields
        .get("upgradeType")
        .set(WeaponType.Flame_Thrower_Wall_Trap, UpgradeType.Terran_Infantry_Weapons);
    fields.get("damageType").set(WeaponType.Flame_Thrower_Wall_Trap, DamageType.Concussive);
    fields.get("explosionType").set(WeaponType.Flame_Thrower_Wall_Trap, ExplosionType.Enemy_Splash);
    fields.get("minRange").set(WeaponType.Flame_Thrower_Wall_Trap, 0);
    fields.get("maxRange").set(WeaponType.Flame_Thrower_Wall_Trap, 64);
    fields.get("innerSplashRadius").set(WeaponType.Flame_Thrower_Wall_Trap, 15);
    fields.get("medianSplashRadius").set(WeaponType.Flame_Thrower_Wall_Trap, 20);
    fields.get("outerSplashRadius").set(WeaponType.Flame_Thrower_Wall_Trap, 25);
    fields.get("targetsAir").set(WeaponType.Flame_Thrower_Wall_Trap, false);
    fields.get("targetsGround").set(WeaponType.Flame_Thrower_Wall_Trap, true);
    fields.get("targetsMechanical").set(WeaponType.Flame_Thrower_Wall_Trap, false);
    fields.get("targetsOrganic").set(WeaponType.Flame_Thrower_Wall_Trap, false);
    fields.get("targetsNonBuilding").set(WeaponType.Flame_Thrower_Wall_Trap, false);
    fields.get("targetsNonRobotic").set(WeaponType.Flame_Thrower_Wall_Trap, false);
    fields.get("targetsTerrain").set(WeaponType.Flame_Thrower_Wall_Trap, false);
    fields.get("targetsOrgOrMech").set(WeaponType.Flame_Thrower_Wall_Trap, false);
    fields.get("targetsOwn").set(WeaponType.Flame_Thrower_Wall_Trap, false);
  }

  private static void initializeWeaponType_Hellfire_Missile_Pack_Floor_Trap() throws Exception {
    Class<?> c = WeaponType.class;
    Map<String, Field> fields =
        Stream.of(c.getDeclaredFields())
            .collect(
                Collectors.toMap(
                    f -> f.getName(),
                    f -> {
                      f.setAccessible(true);
                      return f;
                    }));
    fields.get("iD").set(WeaponType.Hellfire_Missile_Pack_Floor_Trap, 99);
    fields.get("tech").set(WeaponType.Hellfire_Missile_Pack_Floor_Trap, TechType.None);
    fields
        .get("whatUses")
        .set(WeaponType.Hellfire_Missile_Pack_Floor_Trap, UnitType.Special_Floor_Missile_Trap);
    fields.get("damageAmount").set(WeaponType.Hellfire_Missile_Pack_Floor_Trap, 10);
    fields.get("damageBonus").set(WeaponType.Hellfire_Missile_Pack_Floor_Trap, 1);
    fields.get("damageCooldown").set(WeaponType.Hellfire_Missile_Pack_Floor_Trap, 22);
    fields.get("damageFactor").set(WeaponType.Hellfire_Missile_Pack_Floor_Trap, 2);
    fields
        .get("upgradeType")
        .set(WeaponType.Hellfire_Missile_Pack_Floor_Trap, UpgradeType.Terran_Vehicle_Weapons);
    fields.get("damageType").set(WeaponType.Hellfire_Missile_Pack_Floor_Trap, DamageType.Explosive);
    fields
        .get("explosionType")
        .set(WeaponType.Hellfire_Missile_Pack_Floor_Trap, ExplosionType.Normal);
    fields.get("minRange").set(WeaponType.Hellfire_Missile_Pack_Floor_Trap, 0);
    fields.get("maxRange").set(WeaponType.Hellfire_Missile_Pack_Floor_Trap, 160);
    fields.get("innerSplashRadius").set(WeaponType.Hellfire_Missile_Pack_Floor_Trap, 0);
    fields.get("medianSplashRadius").set(WeaponType.Hellfire_Missile_Pack_Floor_Trap, 0);
    fields.get("outerSplashRadius").set(WeaponType.Hellfire_Missile_Pack_Floor_Trap, 0);
    fields.get("targetsAir").set(WeaponType.Hellfire_Missile_Pack_Floor_Trap, false);
    fields.get("targetsGround").set(WeaponType.Hellfire_Missile_Pack_Floor_Trap, true);
    fields.get("targetsMechanical").set(WeaponType.Hellfire_Missile_Pack_Floor_Trap, false);
    fields.get("targetsOrganic").set(WeaponType.Hellfire_Missile_Pack_Floor_Trap, false);
    fields.get("targetsNonBuilding").set(WeaponType.Hellfire_Missile_Pack_Floor_Trap, false);
    fields.get("targetsNonRobotic").set(WeaponType.Hellfire_Missile_Pack_Floor_Trap, false);
    fields.get("targetsTerrain").set(WeaponType.Hellfire_Missile_Pack_Floor_Trap, false);
    fields.get("targetsOrgOrMech").set(WeaponType.Hellfire_Missile_Pack_Floor_Trap, false);
    fields.get("targetsOwn").set(WeaponType.Hellfire_Missile_Pack_Floor_Trap, false);
  }

  private static void initializeWeaponType_Neutron_Flare() throws Exception {
    Class<?> c = WeaponType.class;
    Map<String, Field> fields =
        Stream.of(c.getDeclaredFields())
            .collect(
                Collectors.toMap(
                    f -> f.getName(),
                    f -> {
                      f.setAccessible(true);
                      return f;
                    }));
    fields.get("iD").set(WeaponType.Neutron_Flare, 100);
    fields.get("tech").set(WeaponType.Neutron_Flare, TechType.None);
    fields.get("whatUses").set(WeaponType.Neutron_Flare, UnitType.Protoss_Corsair);
    fields.get("damageAmount").set(WeaponType.Neutron_Flare, 5);
    fields.get("damageBonus").set(WeaponType.Neutron_Flare, 1);
    fields.get("damageCooldown").set(WeaponType.Neutron_Flare, 8);
    fields.get("damageFactor").set(WeaponType.Neutron_Flare, 1);
    fields.get("upgradeType").set(WeaponType.Neutron_Flare, UpgradeType.Protoss_Air_Weapons);
    fields.get("damageType").set(WeaponType.Neutron_Flare, DamageType.Explosive);
    fields.get("explosionType").set(WeaponType.Neutron_Flare, ExplosionType.Air_Splash);
    fields.get("minRange").set(WeaponType.Neutron_Flare, 0);
    fields.get("maxRange").set(WeaponType.Neutron_Flare, 160);
    fields.get("innerSplashRadius").set(WeaponType.Neutron_Flare, 5);
    fields.get("medianSplashRadius").set(WeaponType.Neutron_Flare, 50);
    fields.get("outerSplashRadius").set(WeaponType.Neutron_Flare, 100);
    fields.get("targetsAir").set(WeaponType.Neutron_Flare, true);
    fields.get("targetsGround").set(WeaponType.Neutron_Flare, false);
    fields.get("targetsMechanical").set(WeaponType.Neutron_Flare, false);
    fields.get("targetsOrganic").set(WeaponType.Neutron_Flare, false);
    fields.get("targetsNonBuilding").set(WeaponType.Neutron_Flare, false);
    fields.get("targetsNonRobotic").set(WeaponType.Neutron_Flare, false);
    fields.get("targetsTerrain").set(WeaponType.Neutron_Flare, false);
    fields.get("targetsOrgOrMech").set(WeaponType.Neutron_Flare, false);
    fields.get("targetsOwn").set(WeaponType.Neutron_Flare, false);
  }

  private static void initializeWeaponType_Disruption_Web() throws Exception {
    Class<?> c = WeaponType.class;
    Map<String, Field> fields =
        Stream.of(c.getDeclaredFields())
            .collect(
                Collectors.toMap(
                    f -> f.getName(),
                    f -> {
                      f.setAccessible(true);
                      return f;
                    }));
    fields.get("iD").set(WeaponType.Disruption_Web, 101);
    fields.get("tech").set(WeaponType.Disruption_Web, TechType.Disruption_Web);
    fields.get("whatUses").set(WeaponType.Disruption_Web, UnitType.Protoss_Corsair);
    fields.get("damageAmount").set(WeaponType.Disruption_Web, 0);
    fields.get("damageBonus").set(WeaponType.Disruption_Web, 0);
    fields.get("damageCooldown").set(WeaponType.Disruption_Web, 22);
    fields.get("damageFactor").set(WeaponType.Disruption_Web, 1);
    fields.get("upgradeType").set(WeaponType.Disruption_Web, UpgradeType.Upgrade_60);
    fields.get("damageType").set(WeaponType.Disruption_Web, DamageType.Ignore_Armor);
    fields.get("explosionType").set(WeaponType.Disruption_Web, ExplosionType.Disruption_Web);
    fields.get("minRange").set(WeaponType.Disruption_Web, 0);
    fields.get("maxRange").set(WeaponType.Disruption_Web, 288);
    fields.get("innerSplashRadius").set(WeaponType.Disruption_Web, 0);
    fields.get("medianSplashRadius").set(WeaponType.Disruption_Web, 0);
    fields.get("outerSplashRadius").set(WeaponType.Disruption_Web, 0);
    fields.get("targetsAir").set(WeaponType.Disruption_Web, false);
    fields.get("targetsGround").set(WeaponType.Disruption_Web, true);
    fields.get("targetsMechanical").set(WeaponType.Disruption_Web, false);
    fields.get("targetsOrganic").set(WeaponType.Disruption_Web, false);
    fields.get("targetsNonBuilding").set(WeaponType.Disruption_Web, false);
    fields.get("targetsNonRobotic").set(WeaponType.Disruption_Web, false);
    fields.get("targetsTerrain").set(WeaponType.Disruption_Web, false);
    fields.get("targetsOrgOrMech").set(WeaponType.Disruption_Web, false);
    fields.get("targetsOwn").set(WeaponType.Disruption_Web, false);
  }

  private static void initializeWeaponType_Restoration() throws Exception {
    Class<?> c = WeaponType.class;
    Map<String, Field> fields =
        Stream.of(c.getDeclaredFields())
            .collect(
                Collectors.toMap(
                    f -> f.getName(),
                    f -> {
                      f.setAccessible(true);
                      return f;
                    }));
    fields.get("iD").set(WeaponType.Restoration, 102);
    fields.get("tech").set(WeaponType.Restoration, TechType.Restoration);
    fields.get("whatUses").set(WeaponType.Restoration, UnitType.Terran_Medic);
    fields.get("damageAmount").set(WeaponType.Restoration, 20);
    fields.get("damageBonus").set(WeaponType.Restoration, 0);
    fields.get("damageCooldown").set(WeaponType.Restoration, 22);
    fields.get("damageFactor").set(WeaponType.Restoration, 1);
    fields.get("upgradeType").set(WeaponType.Restoration, UpgradeType.Upgrade_60);
    fields.get("damageType").set(WeaponType.Restoration, DamageType.Ignore_Armor);
    fields.get("explosionType").set(WeaponType.Restoration, ExplosionType.Restoration);
    fields.get("minRange").set(WeaponType.Restoration, 0);
    fields.get("maxRange").set(WeaponType.Restoration, 192);
    fields.get("innerSplashRadius").set(WeaponType.Restoration, 0);
    fields.get("medianSplashRadius").set(WeaponType.Restoration, 0);
    fields.get("outerSplashRadius").set(WeaponType.Restoration, 0);
    fields.get("targetsAir").set(WeaponType.Restoration, true);
    fields.get("targetsGround").set(WeaponType.Restoration, true);
    fields.get("targetsMechanical").set(WeaponType.Restoration, false);
    fields.get("targetsOrganic").set(WeaponType.Restoration, false);
    fields.get("targetsNonBuilding").set(WeaponType.Restoration, false);
    fields.get("targetsNonRobotic").set(WeaponType.Restoration, false);
    fields.get("targetsTerrain").set(WeaponType.Restoration, true);
    fields.get("targetsOrgOrMech").set(WeaponType.Restoration, false);
    fields.get("targetsOwn").set(WeaponType.Restoration, false);
  }

  private static void initializeWeaponType_Halo_Rockets() throws Exception {
    Class<?> c = WeaponType.class;
    Map<String, Field> fields =
        Stream.of(c.getDeclaredFields())
            .collect(
                Collectors.toMap(
                    f -> f.getName(),
                    f -> {
                      f.setAccessible(true);
                      return f;
                    }));
    fields.get("iD").set(WeaponType.Halo_Rockets, 103);
    fields.get("tech").set(WeaponType.Halo_Rockets, TechType.None);
    fields.get("whatUses").set(WeaponType.Halo_Rockets, UnitType.Terran_Valkyrie);
    fields.get("damageAmount").set(WeaponType.Halo_Rockets, 6);
    fields.get("damageBonus").set(WeaponType.Halo_Rockets, 1);
    fields.get("damageCooldown").set(WeaponType.Halo_Rockets, 64);
    fields.get("damageFactor").set(WeaponType.Halo_Rockets, 2);
    fields.get("upgradeType").set(WeaponType.Halo_Rockets, UpgradeType.Terran_Ship_Weapons);
    fields.get("damageType").set(WeaponType.Halo_Rockets, DamageType.Explosive);
    fields.get("explosionType").set(WeaponType.Halo_Rockets, ExplosionType.Air_Splash);
    fields.get("minRange").set(WeaponType.Halo_Rockets, 0);
    fields.get("maxRange").set(WeaponType.Halo_Rockets, 192);
    fields.get("innerSplashRadius").set(WeaponType.Halo_Rockets, 5);
    fields.get("medianSplashRadius").set(WeaponType.Halo_Rockets, 50);
    fields.get("outerSplashRadius").set(WeaponType.Halo_Rockets, 100);
    fields.get("targetsAir").set(WeaponType.Halo_Rockets, true);
    fields.get("targetsGround").set(WeaponType.Halo_Rockets, false);
    fields.get("targetsMechanical").set(WeaponType.Halo_Rockets, false);
    fields.get("targetsOrganic").set(WeaponType.Halo_Rockets, false);
    fields.get("targetsNonBuilding").set(WeaponType.Halo_Rockets, false);
    fields.get("targetsNonRobotic").set(WeaponType.Halo_Rockets, false);
    fields.get("targetsTerrain").set(WeaponType.Halo_Rockets, false);
    fields.get("targetsOrgOrMech").set(WeaponType.Halo_Rockets, false);
    fields.get("targetsOwn").set(WeaponType.Halo_Rockets, false);
  }

  private static void initializeWeaponType_Corrosive_Acid() throws Exception {
    Class<?> c = WeaponType.class;
    Map<String, Field> fields =
        Stream.of(c.getDeclaredFields())
            .collect(
                Collectors.toMap(
                    f -> f.getName(),
                    f -> {
                      f.setAccessible(true);
                      return f;
                    }));
    fields.get("iD").set(WeaponType.Corrosive_Acid, 104);
    fields.get("tech").set(WeaponType.Corrosive_Acid, TechType.None);
    fields.get("whatUses").set(WeaponType.Corrosive_Acid, UnitType.Zerg_Devourer);
    fields.get("damageAmount").set(WeaponType.Corrosive_Acid, 25);
    fields.get("damageBonus").set(WeaponType.Corrosive_Acid, 2);
    fields.get("damageCooldown").set(WeaponType.Corrosive_Acid, 100);
    fields.get("damageFactor").set(WeaponType.Corrosive_Acid, 1);
    fields.get("upgradeType").set(WeaponType.Corrosive_Acid, UpgradeType.Zerg_Flyer_Attacks);
    fields.get("damageType").set(WeaponType.Corrosive_Acid, DamageType.Explosive);
    fields.get("explosionType").set(WeaponType.Corrosive_Acid, ExplosionType.Corrosive_Acid);
    fields.get("minRange").set(WeaponType.Corrosive_Acid, 0);
    fields.get("maxRange").set(WeaponType.Corrosive_Acid, 192);
    fields.get("innerSplashRadius").set(WeaponType.Corrosive_Acid, 0);
    fields.get("medianSplashRadius").set(WeaponType.Corrosive_Acid, 0);
    fields.get("outerSplashRadius").set(WeaponType.Corrosive_Acid, 0);
    fields.get("targetsAir").set(WeaponType.Corrosive_Acid, true);
    fields.get("targetsGround").set(WeaponType.Corrosive_Acid, false);
    fields.get("targetsMechanical").set(WeaponType.Corrosive_Acid, false);
    fields.get("targetsOrganic").set(WeaponType.Corrosive_Acid, false);
    fields.get("targetsNonBuilding").set(WeaponType.Corrosive_Acid, false);
    fields.get("targetsNonRobotic").set(WeaponType.Corrosive_Acid, false);
    fields.get("targetsTerrain").set(WeaponType.Corrosive_Acid, false);
    fields.get("targetsOrgOrMech").set(WeaponType.Corrosive_Acid, false);
    fields.get("targetsOwn").set(WeaponType.Corrosive_Acid, false);
  }

  private static void initializeWeaponType_Mind_Control() throws Exception {
    Class<?> c = WeaponType.class;
    Map<String, Field> fields =
        Stream.of(c.getDeclaredFields())
            .collect(
                Collectors.toMap(
                    f -> f.getName(),
                    f -> {
                      f.setAccessible(true);
                      return f;
                    }));
    fields.get("iD").set(WeaponType.Mind_Control, 105);
    fields.get("tech").set(WeaponType.Mind_Control, TechType.Mind_Control);
    fields.get("whatUses").set(WeaponType.Mind_Control, UnitType.Protoss_Dark_Archon);
    fields.get("damageAmount").set(WeaponType.Mind_Control, 8);
    fields.get("damageBonus").set(WeaponType.Mind_Control, 1);
    fields.get("damageCooldown").set(WeaponType.Mind_Control, 22);
    fields.get("damageFactor").set(WeaponType.Mind_Control, 1);
    fields.get("upgradeType").set(WeaponType.Mind_Control, UpgradeType.Upgrade_60);
    fields.get("damageType").set(WeaponType.Mind_Control, DamageType.Normal);
    fields.get("explosionType").set(WeaponType.Mind_Control, ExplosionType.Mind_Control);
    fields.get("minRange").set(WeaponType.Mind_Control, 0);
    fields.get("maxRange").set(WeaponType.Mind_Control, 256);
    fields.get("innerSplashRadius").set(WeaponType.Mind_Control, 0);
    fields.get("medianSplashRadius").set(WeaponType.Mind_Control, 0);
    fields.get("outerSplashRadius").set(WeaponType.Mind_Control, 0);
    fields.get("targetsAir").set(WeaponType.Mind_Control, true);
    fields.get("targetsGround").set(WeaponType.Mind_Control, true);
    fields.get("targetsMechanical").set(WeaponType.Mind_Control, false);
    fields.get("targetsOrganic").set(WeaponType.Mind_Control, false);
    fields.get("targetsNonBuilding").set(WeaponType.Mind_Control, false);
    fields.get("targetsNonRobotic").set(WeaponType.Mind_Control, false);
    fields.get("targetsTerrain").set(WeaponType.Mind_Control, true);
    fields.get("targetsOrgOrMech").set(WeaponType.Mind_Control, false);
    fields.get("targetsOwn").set(WeaponType.Mind_Control, false);
  }

  private static void initializeWeaponType_Feedback() throws Exception {
    Class<?> c = WeaponType.class;
    Map<String, Field> fields =
        Stream.of(c.getDeclaredFields())
            .collect(
                Collectors.toMap(
                    f -> f.getName(),
                    f -> {
                      f.setAccessible(true);
                      return f;
                    }));
    fields.get("iD").set(WeaponType.Feedback, 106);
    fields.get("tech").set(WeaponType.Feedback, TechType.Feedback);
    fields.get("whatUses").set(WeaponType.Feedback, UnitType.Protoss_Dark_Archon);
    fields.get("damageAmount").set(WeaponType.Feedback, 8);
    fields.get("damageBonus").set(WeaponType.Feedback, 1);
    fields.get("damageCooldown").set(WeaponType.Feedback, 22);
    fields.get("damageFactor").set(WeaponType.Feedback, 1);
    fields.get("upgradeType").set(WeaponType.Feedback, UpgradeType.Upgrade_60);
    fields.get("damageType").set(WeaponType.Feedback, DamageType.Ignore_Armor);
    fields.get("explosionType").set(WeaponType.Feedback, ExplosionType.Feedback);
    fields.get("minRange").set(WeaponType.Feedback, 0);
    fields.get("maxRange").set(WeaponType.Feedback, 320);
    fields.get("innerSplashRadius").set(WeaponType.Feedback, 0);
    fields.get("medianSplashRadius").set(WeaponType.Feedback, 0);
    fields.get("outerSplashRadius").set(WeaponType.Feedback, 0);
    fields.get("targetsAir").set(WeaponType.Feedback, true);
    fields.get("targetsGround").set(WeaponType.Feedback, true);
    fields.get("targetsMechanical").set(WeaponType.Feedback, false);
    fields.get("targetsOrganic").set(WeaponType.Feedback, false);
    fields.get("targetsNonBuilding").set(WeaponType.Feedback, false);
    fields.get("targetsNonRobotic").set(WeaponType.Feedback, false);
    fields.get("targetsTerrain").set(WeaponType.Feedback, true);
    fields.get("targetsOrgOrMech").set(WeaponType.Feedback, false);
    fields.get("targetsOwn").set(WeaponType.Feedback, false);
  }

  private static void initializeWeaponType_Optical_Flare() throws Exception {
    Class<?> c = WeaponType.class;
    Map<String, Field> fields =
        Stream.of(c.getDeclaredFields())
            .collect(
                Collectors.toMap(
                    f -> f.getName(),
                    f -> {
                      f.setAccessible(true);
                      return f;
                    }));
    fields.get("iD").set(WeaponType.Optical_Flare, 107);
    fields.get("tech").set(WeaponType.Optical_Flare, TechType.Optical_Flare);
    fields.get("whatUses").set(WeaponType.Optical_Flare, UnitType.Terran_Medic);
    fields.get("damageAmount").set(WeaponType.Optical_Flare, 8);
    fields.get("damageBonus").set(WeaponType.Optical_Flare, 1);
    fields.get("damageCooldown").set(WeaponType.Optical_Flare, 22);
    fields.get("damageFactor").set(WeaponType.Optical_Flare, 1);
    fields.get("upgradeType").set(WeaponType.Optical_Flare, UpgradeType.Upgrade_60);
    fields.get("damageType").set(WeaponType.Optical_Flare, DamageType.Independent);
    fields.get("explosionType").set(WeaponType.Optical_Flare, ExplosionType.Optical_Flare);
    fields.get("minRange").set(WeaponType.Optical_Flare, 0);
    fields.get("maxRange").set(WeaponType.Optical_Flare, 288);
    fields.get("innerSplashRadius").set(WeaponType.Optical_Flare, 0);
    fields.get("medianSplashRadius").set(WeaponType.Optical_Flare, 0);
    fields.get("outerSplashRadius").set(WeaponType.Optical_Flare, 0);
    fields.get("targetsAir").set(WeaponType.Optical_Flare, false);
    fields.get("targetsGround").set(WeaponType.Optical_Flare, true);
    fields.get("targetsMechanical").set(WeaponType.Optical_Flare, false);
    fields.get("targetsOrganic").set(WeaponType.Optical_Flare, false);
    fields.get("targetsNonBuilding").set(WeaponType.Optical_Flare, false);
    fields.get("targetsNonRobotic").set(WeaponType.Optical_Flare, false);
    fields.get("targetsTerrain").set(WeaponType.Optical_Flare, false);
    fields.get("targetsOrgOrMech").set(WeaponType.Optical_Flare, false);
    fields.get("targetsOwn").set(WeaponType.Optical_Flare, false);
  }

  private static void initializeWeaponType_Maelstrom() throws Exception {
    Class<?> c = WeaponType.class;
    Map<String, Field> fields =
        Stream.of(c.getDeclaredFields())
            .collect(
                Collectors.toMap(
                    f -> f.getName(),
                    f -> {
                      f.setAccessible(true);
                      return f;
                    }));
    fields.get("iD").set(WeaponType.Maelstrom, 108);
    fields.get("tech").set(WeaponType.Maelstrom, TechType.Maelstrom);
    fields.get("whatUses").set(WeaponType.Maelstrom, UnitType.Protoss_Dark_Archon);
    fields.get("damageAmount").set(WeaponType.Maelstrom, 0);
    fields.get("damageBonus").set(WeaponType.Maelstrom, 1);
    fields.get("damageCooldown").set(WeaponType.Maelstrom, 1);
    fields.get("damageFactor").set(WeaponType.Maelstrom, 1);
    fields.get("upgradeType").set(WeaponType.Maelstrom, UpgradeType.Upgrade_60);
    fields.get("damageType").set(WeaponType.Maelstrom, DamageType.Independent);
    fields.get("explosionType").set(WeaponType.Maelstrom, ExplosionType.Maelstrom);
    fields.get("minRange").set(WeaponType.Maelstrom, 0);
    fields.get("maxRange").set(WeaponType.Maelstrom, 320);
    fields.get("innerSplashRadius").set(WeaponType.Maelstrom, 0);
    fields.get("medianSplashRadius").set(WeaponType.Maelstrom, 0);
    fields.get("outerSplashRadius").set(WeaponType.Maelstrom, 0);
    fields.get("targetsAir").set(WeaponType.Maelstrom, true);
    fields.get("targetsGround").set(WeaponType.Maelstrom, true);
    fields.get("targetsMechanical").set(WeaponType.Maelstrom, false);
    fields.get("targetsOrganic").set(WeaponType.Maelstrom, false);
    fields.get("targetsNonBuilding").set(WeaponType.Maelstrom, false);
    fields.get("targetsNonRobotic").set(WeaponType.Maelstrom, false);
    fields.get("targetsTerrain").set(WeaponType.Maelstrom, true);
    fields.get("targetsOrgOrMech").set(WeaponType.Maelstrom, false);
    fields.get("targetsOwn").set(WeaponType.Maelstrom, false);
  }

  private static void initializeWeaponType_Subterranean_Spines() throws Exception {
    Class<?> c = WeaponType.class;
    Map<String, Field> fields =
        Stream.of(c.getDeclaredFields())
            .collect(
                Collectors.toMap(
                    f -> f.getName(),
                    f -> {
                      f.setAccessible(true);
                      return f;
                    }));
    fields.get("iD").set(WeaponType.Subterranean_Spines, 109);
    fields.get("tech").set(WeaponType.Subterranean_Spines, TechType.None);
    fields.get("whatUses").set(WeaponType.Subterranean_Spines, UnitType.Zerg_Lurker);
    fields.get("damageAmount").set(WeaponType.Subterranean_Spines, 20);
    fields.get("damageBonus").set(WeaponType.Subterranean_Spines, 2);
    fields.get("damageCooldown").set(WeaponType.Subterranean_Spines, 37);
    fields.get("damageFactor").set(WeaponType.Subterranean_Spines, 1);
    fields.get("upgradeType").set(WeaponType.Subterranean_Spines, UpgradeType.Zerg_Missile_Attacks);
    fields.get("damageType").set(WeaponType.Subterranean_Spines, DamageType.Normal);
    fields.get("explosionType").set(WeaponType.Subterranean_Spines, ExplosionType.Enemy_Splash);
    fields.get("minRange").set(WeaponType.Subterranean_Spines, 0);
    fields.get("maxRange").set(WeaponType.Subterranean_Spines, 192);
    fields.get("innerSplashRadius").set(WeaponType.Subterranean_Spines, 20);
    fields.get("medianSplashRadius").set(WeaponType.Subterranean_Spines, 20);
    fields.get("outerSplashRadius").set(WeaponType.Subterranean_Spines, 20);
    fields.get("targetsAir").set(WeaponType.Subterranean_Spines, false);
    fields.get("targetsGround").set(WeaponType.Subterranean_Spines, true);
    fields.get("targetsMechanical").set(WeaponType.Subterranean_Spines, false);
    fields.get("targetsOrganic").set(WeaponType.Subterranean_Spines, false);
    fields.get("targetsNonBuilding").set(WeaponType.Subterranean_Spines, false);
    fields.get("targetsNonRobotic").set(WeaponType.Subterranean_Spines, false);
    fields.get("targetsTerrain").set(WeaponType.Subterranean_Spines, false);
    fields.get("targetsOrgOrMech").set(WeaponType.Subterranean_Spines, false);
    fields.get("targetsOwn").set(WeaponType.Subterranean_Spines, false);
  }

  private static void initializeWeaponType_Warp_Blades() throws Exception {
    Class<?> c = WeaponType.class;
    Map<String, Field> fields =
        Stream.of(c.getDeclaredFields())
            .collect(
                Collectors.toMap(
                    f -> f.getName(),
                    f -> {
                      f.setAccessible(true);
                      return f;
                    }));
    fields.get("iD").set(WeaponType.Warp_Blades, 111);
    fields.get("tech").set(WeaponType.Warp_Blades, TechType.None);
    fields.get("whatUses").set(WeaponType.Warp_Blades, UnitType.Protoss_Dark_Templar);
    fields.get("damageAmount").set(WeaponType.Warp_Blades, 40);
    fields.get("damageBonus").set(WeaponType.Warp_Blades, 3);
    fields.get("damageCooldown").set(WeaponType.Warp_Blades, 30);
    fields.get("damageFactor").set(WeaponType.Warp_Blades, 1);
    fields.get("upgradeType").set(WeaponType.Warp_Blades, UpgradeType.Protoss_Ground_Weapons);
    fields.get("damageType").set(WeaponType.Warp_Blades, DamageType.Normal);
    fields.get("explosionType").set(WeaponType.Warp_Blades, ExplosionType.Normal);
    fields.get("minRange").set(WeaponType.Warp_Blades, 0);
    fields.get("maxRange").set(WeaponType.Warp_Blades, 15);
    fields.get("innerSplashRadius").set(WeaponType.Warp_Blades, 0);
    fields.get("medianSplashRadius").set(WeaponType.Warp_Blades, 0);
    fields.get("outerSplashRadius").set(WeaponType.Warp_Blades, 0);
    fields.get("targetsAir").set(WeaponType.Warp_Blades, false);
    fields.get("targetsGround").set(WeaponType.Warp_Blades, true);
    fields.get("targetsMechanical").set(WeaponType.Warp_Blades, false);
    fields.get("targetsOrganic").set(WeaponType.Warp_Blades, false);
    fields.get("targetsNonBuilding").set(WeaponType.Warp_Blades, false);
    fields.get("targetsNonRobotic").set(WeaponType.Warp_Blades, false);
    fields.get("targetsTerrain").set(WeaponType.Warp_Blades, false);
    fields.get("targetsOrgOrMech").set(WeaponType.Warp_Blades, false);
    fields.get("targetsOwn").set(WeaponType.Warp_Blades, false);
  }

  private static void initializeWeaponType_C_10_Canister_Rifle_Samir_Duran() throws Exception {
    Class<?> c = WeaponType.class;
    Map<String, Field> fields =
        Stream.of(c.getDeclaredFields())
            .collect(
                Collectors.toMap(
                    f -> f.getName(),
                    f -> {
                      f.setAccessible(true);
                      return f;
                    }));
    fields.get("iD").set(WeaponType.C_10_Canister_Rifle_Samir_Duran, 112);
    fields.get("tech").set(WeaponType.C_10_Canister_Rifle_Samir_Duran, TechType.None);
    fields
        .get("whatUses")
        .set(WeaponType.C_10_Canister_Rifle_Samir_Duran, UnitType.Hero_Samir_Duran);
    fields.get("damageAmount").set(WeaponType.C_10_Canister_Rifle_Samir_Duran, 25);
    fields.get("damageBonus").set(WeaponType.C_10_Canister_Rifle_Samir_Duran, 1);
    fields.get("damageCooldown").set(WeaponType.C_10_Canister_Rifle_Samir_Duran, 22);
    fields.get("damageFactor").set(WeaponType.C_10_Canister_Rifle_Samir_Duran, 1);
    fields
        .get("upgradeType")
        .set(WeaponType.C_10_Canister_Rifle_Samir_Duran, UpgradeType.Terran_Infantry_Weapons);
    fields.get("damageType").set(WeaponType.C_10_Canister_Rifle_Samir_Duran, DamageType.Concussive);
    fields
        .get("explosionType")
        .set(WeaponType.C_10_Canister_Rifle_Samir_Duran, ExplosionType.Normal);
    fields.get("minRange").set(WeaponType.C_10_Canister_Rifle_Samir_Duran, 0);
    fields.get("maxRange").set(WeaponType.C_10_Canister_Rifle_Samir_Duran, 192);
    fields.get("innerSplashRadius").set(WeaponType.C_10_Canister_Rifle_Samir_Duran, 0);
    fields.get("medianSplashRadius").set(WeaponType.C_10_Canister_Rifle_Samir_Duran, 0);
    fields.get("outerSplashRadius").set(WeaponType.C_10_Canister_Rifle_Samir_Duran, 0);
    fields.get("targetsAir").set(WeaponType.C_10_Canister_Rifle_Samir_Duran, true);
    fields.get("targetsGround").set(WeaponType.C_10_Canister_Rifle_Samir_Duran, true);
    fields.get("targetsMechanical").set(WeaponType.C_10_Canister_Rifle_Samir_Duran, false);
    fields.get("targetsOrganic").set(WeaponType.C_10_Canister_Rifle_Samir_Duran, false);
    fields.get("targetsNonBuilding").set(WeaponType.C_10_Canister_Rifle_Samir_Duran, false);
    fields.get("targetsNonRobotic").set(WeaponType.C_10_Canister_Rifle_Samir_Duran, false);
    fields.get("targetsTerrain").set(WeaponType.C_10_Canister_Rifle_Samir_Duran, false);
    fields.get("targetsOrgOrMech").set(WeaponType.C_10_Canister_Rifle_Samir_Duran, false);
    fields.get("targetsOwn").set(WeaponType.C_10_Canister_Rifle_Samir_Duran, false);
  }

  private static void initializeWeaponType_C_10_Canister_Rifle_Infested_Duran() throws Exception {
    Class<?> c = WeaponType.class;
    Map<String, Field> fields =
        Stream.of(c.getDeclaredFields())
            .collect(
                Collectors.toMap(
                    f -> f.getName(),
                    f -> {
                      f.setAccessible(true);
                      return f;
                    }));
    fields.get("iD").set(WeaponType.C_10_Canister_Rifle_Infested_Duran, 113);
    fields.get("tech").set(WeaponType.C_10_Canister_Rifle_Infested_Duran, TechType.None);
    fields
        .get("whatUses")
        .set(WeaponType.C_10_Canister_Rifle_Infested_Duran, UnitType.Hero_Infested_Duran);
    fields.get("damageAmount").set(WeaponType.C_10_Canister_Rifle_Infested_Duran, 25);
    fields.get("damageBonus").set(WeaponType.C_10_Canister_Rifle_Infested_Duran, 1);
    fields.get("damageCooldown").set(WeaponType.C_10_Canister_Rifle_Infested_Duran, 22);
    fields.get("damageFactor").set(WeaponType.C_10_Canister_Rifle_Infested_Duran, 1);
    fields
        .get("upgradeType")
        .set(WeaponType.C_10_Canister_Rifle_Infested_Duran, UpgradeType.Terran_Infantry_Weapons);
    fields
        .get("damageType")
        .set(WeaponType.C_10_Canister_Rifle_Infested_Duran, DamageType.Concussive);
    fields
        .get("explosionType")
        .set(WeaponType.C_10_Canister_Rifle_Infested_Duran, ExplosionType.Normal);
    fields.get("minRange").set(WeaponType.C_10_Canister_Rifle_Infested_Duran, 0);
    fields.get("maxRange").set(WeaponType.C_10_Canister_Rifle_Infested_Duran, 192);
    fields.get("innerSplashRadius").set(WeaponType.C_10_Canister_Rifle_Infested_Duran, 0);
    fields.get("medianSplashRadius").set(WeaponType.C_10_Canister_Rifle_Infested_Duran, 0);
    fields.get("outerSplashRadius").set(WeaponType.C_10_Canister_Rifle_Infested_Duran, 0);
    fields.get("targetsAir").set(WeaponType.C_10_Canister_Rifle_Infested_Duran, true);
    fields.get("targetsGround").set(WeaponType.C_10_Canister_Rifle_Infested_Duran, true);
    fields.get("targetsMechanical").set(WeaponType.C_10_Canister_Rifle_Infested_Duran, false);
    fields.get("targetsOrganic").set(WeaponType.C_10_Canister_Rifle_Infested_Duran, false);
    fields.get("targetsNonBuilding").set(WeaponType.C_10_Canister_Rifle_Infested_Duran, false);
    fields.get("targetsNonRobotic").set(WeaponType.C_10_Canister_Rifle_Infested_Duran, false);
    fields.get("targetsTerrain").set(WeaponType.C_10_Canister_Rifle_Infested_Duran, false);
    fields.get("targetsOrgOrMech").set(WeaponType.C_10_Canister_Rifle_Infested_Duran, false);
    fields.get("targetsOwn").set(WeaponType.C_10_Canister_Rifle_Infested_Duran, false);
  }

  private static void initializeWeaponType_Dual_Photon_Blasters_Artanis() throws Exception {
    Class<?> c = WeaponType.class;
    Map<String, Field> fields =
        Stream.of(c.getDeclaredFields())
            .collect(
                Collectors.toMap(
                    f -> f.getName(),
                    f -> {
                      f.setAccessible(true);
                      return f;
                    }));
    fields.get("iD").set(WeaponType.Dual_Photon_Blasters_Artanis, 114);
    fields.get("tech").set(WeaponType.Dual_Photon_Blasters_Artanis, TechType.None);
    fields.get("whatUses").set(WeaponType.Dual_Photon_Blasters_Artanis, UnitType.Hero_Artanis);
    fields.get("damageAmount").set(WeaponType.Dual_Photon_Blasters_Artanis, 20);
    fields.get("damageBonus").set(WeaponType.Dual_Photon_Blasters_Artanis, 1);
    fields.get("damageCooldown").set(WeaponType.Dual_Photon_Blasters_Artanis, 30);
    fields.get("damageFactor").set(WeaponType.Dual_Photon_Blasters_Artanis, 1);
    fields
        .get("upgradeType")
        .set(WeaponType.Dual_Photon_Blasters_Artanis, UpgradeType.Protoss_Air_Weapons);
    fields.get("damageType").set(WeaponType.Dual_Photon_Blasters_Artanis, DamageType.Normal);
    fields.get("explosionType").set(WeaponType.Dual_Photon_Blasters_Artanis, ExplosionType.Normal);
    fields.get("minRange").set(WeaponType.Dual_Photon_Blasters_Artanis, 0);
    fields.get("maxRange").set(WeaponType.Dual_Photon_Blasters_Artanis, 128);
    fields.get("innerSplashRadius").set(WeaponType.Dual_Photon_Blasters_Artanis, 0);
    fields.get("medianSplashRadius").set(WeaponType.Dual_Photon_Blasters_Artanis, 0);
    fields.get("outerSplashRadius").set(WeaponType.Dual_Photon_Blasters_Artanis, 0);
    fields.get("targetsAir").set(WeaponType.Dual_Photon_Blasters_Artanis, false);
    fields.get("targetsGround").set(WeaponType.Dual_Photon_Blasters_Artanis, true);
    fields.get("targetsMechanical").set(WeaponType.Dual_Photon_Blasters_Artanis, false);
    fields.get("targetsOrganic").set(WeaponType.Dual_Photon_Blasters_Artanis, false);
    fields.get("targetsNonBuilding").set(WeaponType.Dual_Photon_Blasters_Artanis, false);
    fields.get("targetsNonRobotic").set(WeaponType.Dual_Photon_Blasters_Artanis, false);
    fields.get("targetsTerrain").set(WeaponType.Dual_Photon_Blasters_Artanis, false);
    fields.get("targetsOrgOrMech").set(WeaponType.Dual_Photon_Blasters_Artanis, false);
    fields.get("targetsOwn").set(WeaponType.Dual_Photon_Blasters_Artanis, false);
  }

  private static void initializeWeaponType_Anti_Matter_Missiles_Artanis() throws Exception {
    Class<?> c = WeaponType.class;
    Map<String, Field> fields =
        Stream.of(c.getDeclaredFields())
            .collect(
                Collectors.toMap(
                    f -> f.getName(),
                    f -> {
                      f.setAccessible(true);
                      return f;
                    }));
    fields.get("iD").set(WeaponType.Anti_Matter_Missiles_Artanis, 115);
    fields.get("tech").set(WeaponType.Anti_Matter_Missiles_Artanis, TechType.None);
    fields.get("whatUses").set(WeaponType.Anti_Matter_Missiles_Artanis, UnitType.Hero_Artanis);
    fields.get("damageAmount").set(WeaponType.Anti_Matter_Missiles_Artanis, 28);
    fields.get("damageBonus").set(WeaponType.Anti_Matter_Missiles_Artanis, 1);
    fields.get("damageCooldown").set(WeaponType.Anti_Matter_Missiles_Artanis, 22);
    fields.get("damageFactor").set(WeaponType.Anti_Matter_Missiles_Artanis, 2);
    fields
        .get("upgradeType")
        .set(WeaponType.Anti_Matter_Missiles_Artanis, UpgradeType.Protoss_Air_Weapons);
    fields.get("damageType").set(WeaponType.Anti_Matter_Missiles_Artanis, DamageType.Explosive);
    fields.get("explosionType").set(WeaponType.Anti_Matter_Missiles_Artanis, ExplosionType.Normal);
    fields.get("minRange").set(WeaponType.Anti_Matter_Missiles_Artanis, 0);
    fields.get("maxRange").set(WeaponType.Anti_Matter_Missiles_Artanis, 128);
    fields.get("innerSplashRadius").set(WeaponType.Anti_Matter_Missiles_Artanis, 0);
    fields.get("medianSplashRadius").set(WeaponType.Anti_Matter_Missiles_Artanis, 0);
    fields.get("outerSplashRadius").set(WeaponType.Anti_Matter_Missiles_Artanis, 0);
    fields.get("targetsAir").set(WeaponType.Anti_Matter_Missiles_Artanis, true);
    fields.get("targetsGround").set(WeaponType.Anti_Matter_Missiles_Artanis, false);
    fields.get("targetsMechanical").set(WeaponType.Anti_Matter_Missiles_Artanis, false);
    fields.get("targetsOrganic").set(WeaponType.Anti_Matter_Missiles_Artanis, false);
    fields.get("targetsNonBuilding").set(WeaponType.Anti_Matter_Missiles_Artanis, false);
    fields.get("targetsNonRobotic").set(WeaponType.Anti_Matter_Missiles_Artanis, false);
    fields.get("targetsTerrain").set(WeaponType.Anti_Matter_Missiles_Artanis, false);
    fields.get("targetsOrgOrMech").set(WeaponType.Anti_Matter_Missiles_Artanis, false);
    fields.get("targetsOwn").set(WeaponType.Anti_Matter_Missiles_Artanis, false);
  }

  private static void initializeWeaponType_C_10_Canister_Rifle_Alexei_Stukov() throws Exception {
    Class<?> c = WeaponType.class;
    Map<String, Field> fields =
        Stream.of(c.getDeclaredFields())
            .collect(
                Collectors.toMap(
                    f -> f.getName(),
                    f -> {
                      f.setAccessible(true);
                      return f;
                    }));
    fields.get("iD").set(WeaponType.C_10_Canister_Rifle_Alexei_Stukov, 116);
    fields.get("tech").set(WeaponType.C_10_Canister_Rifle_Alexei_Stukov, TechType.None);
    fields
        .get("whatUses")
        .set(WeaponType.C_10_Canister_Rifle_Alexei_Stukov, UnitType.Hero_Alexei_Stukov);
    fields.get("damageAmount").set(WeaponType.C_10_Canister_Rifle_Alexei_Stukov, 30);
    fields.get("damageBonus").set(WeaponType.C_10_Canister_Rifle_Alexei_Stukov, 1);
    fields.get("damageCooldown").set(WeaponType.C_10_Canister_Rifle_Alexei_Stukov, 22);
    fields.get("damageFactor").set(WeaponType.C_10_Canister_Rifle_Alexei_Stukov, 1);
    fields
        .get("upgradeType")
        .set(WeaponType.C_10_Canister_Rifle_Alexei_Stukov, UpgradeType.Terran_Infantry_Weapons);
    fields
        .get("damageType")
        .set(WeaponType.C_10_Canister_Rifle_Alexei_Stukov, DamageType.Concussive);
    fields
        .get("explosionType")
        .set(WeaponType.C_10_Canister_Rifle_Alexei_Stukov, ExplosionType.Normal);
    fields.get("minRange").set(WeaponType.C_10_Canister_Rifle_Alexei_Stukov, 0);
    fields.get("maxRange").set(WeaponType.C_10_Canister_Rifle_Alexei_Stukov, 192);
    fields.get("innerSplashRadius").set(WeaponType.C_10_Canister_Rifle_Alexei_Stukov, 0);
    fields.get("medianSplashRadius").set(WeaponType.C_10_Canister_Rifle_Alexei_Stukov, 0);
    fields.get("outerSplashRadius").set(WeaponType.C_10_Canister_Rifle_Alexei_Stukov, 0);
    fields.get("targetsAir").set(WeaponType.C_10_Canister_Rifle_Alexei_Stukov, true);
    fields.get("targetsGround").set(WeaponType.C_10_Canister_Rifle_Alexei_Stukov, true);
    fields.get("targetsMechanical").set(WeaponType.C_10_Canister_Rifle_Alexei_Stukov, false);
    fields.get("targetsOrganic").set(WeaponType.C_10_Canister_Rifle_Alexei_Stukov, false);
    fields.get("targetsNonBuilding").set(WeaponType.C_10_Canister_Rifle_Alexei_Stukov, false);
    fields.get("targetsNonRobotic").set(WeaponType.C_10_Canister_Rifle_Alexei_Stukov, false);
    fields.get("targetsTerrain").set(WeaponType.C_10_Canister_Rifle_Alexei_Stukov, false);
    fields.get("targetsOrgOrMech").set(WeaponType.C_10_Canister_Rifle_Alexei_Stukov, false);
    fields.get("targetsOwn").set(WeaponType.C_10_Canister_Rifle_Alexei_Stukov, false);
  }

  private static void initializeWeaponType_None() throws Exception {
    Class<?> c = WeaponType.class;
    Map<String, Field> fields =
        Stream.of(c.getDeclaredFields())
            .collect(
                Collectors.toMap(
                    f -> f.getName(),
                    f -> {
                      f.setAccessible(true);
                      return f;
                    }));
    fields.get("iD").set(WeaponType.None, 130);
    fields.get("tech").set(WeaponType.None, TechType.None);
    fields.get("whatUses").set(WeaponType.None, UnitType.None);
    fields.get("damageAmount").set(WeaponType.None, 0);
    fields.get("damageBonus").set(WeaponType.None, 0);
    fields.get("damageCooldown").set(WeaponType.None, 0);
    fields.get("damageFactor").set(WeaponType.None, 0);
    fields.get("upgradeType").set(WeaponType.None, UpgradeType.None);
    fields.get("damageType").set(WeaponType.None, DamageType.None);
    fields.get("explosionType").set(WeaponType.None, ExplosionType.None);
    fields.get("minRange").set(WeaponType.None, 0);
    fields.get("maxRange").set(WeaponType.None, 0);
    fields.get("innerSplashRadius").set(WeaponType.None, 0);
    fields.get("medianSplashRadius").set(WeaponType.None, 0);
    fields.get("outerSplashRadius").set(WeaponType.None, 0);
    fields.get("targetsAir").set(WeaponType.None, false);
    fields.get("targetsGround").set(WeaponType.None, false);
    fields.get("targetsMechanical").set(WeaponType.None, false);
    fields.get("targetsOrganic").set(WeaponType.None, false);
    fields.get("targetsNonBuilding").set(WeaponType.None, false);
    fields.get("targetsNonRobotic").set(WeaponType.None, false);
    fields.get("targetsTerrain").set(WeaponType.None, false);
    fields.get("targetsOrgOrMech").set(WeaponType.None, false);
    fields.get("targetsOwn").set(WeaponType.None, false);
  }

  private static void initializeWeaponType_Unknown() throws Exception {
    Class<?> c = WeaponType.class;
    Map<String, Field> fields =
        Stream.of(c.getDeclaredFields())
            .collect(
                Collectors.toMap(
                    f -> f.getName(),
                    f -> {
                      f.setAccessible(true);
                      return f;
                    }));
    fields.get("iD").set(WeaponType.Unknown, 131);
    fields.get("tech").set(WeaponType.Unknown, TechType.None);
    fields.get("whatUses").set(WeaponType.Unknown, UnitType.Unknown);
    fields.get("damageAmount").set(WeaponType.Unknown, 0);
    fields.get("damageBonus").set(WeaponType.Unknown, 0);
    fields.get("damageCooldown").set(WeaponType.Unknown, 0);
    fields.get("damageFactor").set(WeaponType.Unknown, 0);
    fields.get("upgradeType").set(WeaponType.Unknown, UpgradeType.Unknown);
    fields.get("damageType").set(WeaponType.Unknown, DamageType.Unknown);
    fields.get("explosionType").set(WeaponType.Unknown, ExplosionType.Unknown);
    fields.get("minRange").set(WeaponType.Unknown, 0);
    fields.get("maxRange").set(WeaponType.Unknown, 0);
    fields.get("innerSplashRadius").set(WeaponType.Unknown, 0);
    fields.get("medianSplashRadius").set(WeaponType.Unknown, 0);
    fields.get("outerSplashRadius").set(WeaponType.Unknown, 0);
    fields.get("targetsAir").set(WeaponType.Unknown, false);
    fields.get("targetsGround").set(WeaponType.Unknown, false);
    fields.get("targetsMechanical").set(WeaponType.Unknown, false);
    fields.get("targetsOrganic").set(WeaponType.Unknown, false);
    fields.get("targetsNonBuilding").set(WeaponType.Unknown, false);
    fields.get("targetsNonRobotic").set(WeaponType.Unknown, false);
    fields.get("targetsTerrain").set(WeaponType.Unknown, false);
    fields.get("targetsOrgOrMech").set(WeaponType.Unknown, false);
    fields.get("targetsOwn").set(WeaponType.Unknown, false);
  }

  private static void initializeWeaponType_MAX() throws Exception {
    Class<?> c = WeaponType.class;
    Map<String, Field> fields =
        Stream.of(c.getDeclaredFields())
            .collect(
                Collectors.toMap(
                    f -> f.getName(),
                    f -> {
                      f.setAccessible(true);
                      return f;
                    }));
    fields.get("iD").set(WeaponType.MAX, 132);
    fields.get("tech").set(WeaponType.MAX, null);
    fields.get("whatUses").set(WeaponType.MAX, null);
    fields.get("damageAmount").set(WeaponType.MAX, 0);
    fields.get("damageBonus").set(WeaponType.MAX, 0);
    fields.get("damageCooldown").set(WeaponType.MAX, 0);
    fields.get("damageFactor").set(WeaponType.MAX, 0);
    fields.get("upgradeType").set(WeaponType.MAX, null);
    fields.get("damageType").set(WeaponType.MAX, null);
    fields.get("explosionType").set(WeaponType.MAX, null);
    fields.get("minRange").set(WeaponType.MAX, 0);
    fields.get("maxRange").set(WeaponType.MAX, 0);
    fields.get("innerSplashRadius").set(WeaponType.MAX, 0);
    fields.get("medianSplashRadius").set(WeaponType.MAX, 0);
    fields.get("outerSplashRadius").set(WeaponType.MAX, 0);
    fields.get("targetsAir").set(WeaponType.MAX, false);
    fields.get("targetsGround").set(WeaponType.MAX, false);
    fields.get("targetsMechanical").set(WeaponType.MAX, false);
    fields.get("targetsOrganic").set(WeaponType.MAX, false);
    fields.get("targetsNonBuilding").set(WeaponType.MAX, false);
    fields.get("targetsNonRobotic").set(WeaponType.MAX, false);
    fields.get("targetsTerrain").set(WeaponType.MAX, false);
    fields.get("targetsOrgOrMech").set(WeaponType.MAX, false);
    fields.get("targetsOwn").set(WeaponType.MAX, false);
  }

  private static Map<?, ?> toMap(Object... element) {
    Map<Object, Object> map = new HashMap<>();
    for (int i = 0; i < element.length; i += 2) {
      map.put(element[i], element[i + 1]);
    }
    return map;
  }
}
