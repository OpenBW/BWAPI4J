package org.openbw.bwapi4j.test;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import org.openbw.bwapi4j.type.Race;
import org.openbw.bwapi4j.type.UnitType;
import org.openbw.bwapi4j.type.UpgradeType;

class UpgradeTypes {

  static void initializeUpgradeType() throws Exception {
    initializeUpgradeType_Terran_Infantry_Armor();
    initializeUpgradeType_Terran_Vehicle_Plating();
    initializeUpgradeType_Terran_Ship_Plating();
    initializeUpgradeType_Zerg_Carapace();
    initializeUpgradeType_Zerg_Flyer_Carapace();
    initializeUpgradeType_Protoss_Ground_Armor();
    initializeUpgradeType_Protoss_Air_Armor();
    initializeUpgradeType_Terran_Infantry_Weapons();
    initializeUpgradeType_Terran_Vehicle_Weapons();
    initializeUpgradeType_Terran_Ship_Weapons();
    initializeUpgradeType_Zerg_Melee_Attacks();
    initializeUpgradeType_Zerg_Missile_Attacks();
    initializeUpgradeType_Zerg_Flyer_Attacks();
    initializeUpgradeType_Protoss_Ground_Weapons();
    initializeUpgradeType_Protoss_Air_Weapons();
    initializeUpgradeType_Protoss_Plasma_Shields();
    initializeUpgradeType_U_238_Shells();
    initializeUpgradeType_Ion_Thrusters();
    initializeUpgradeType_Titan_Reactor();
    initializeUpgradeType_Ocular_Implants();
    initializeUpgradeType_Moebius_Reactor();
    initializeUpgradeType_Apollo_Reactor();
    initializeUpgradeType_Colossus_Reactor();
    initializeUpgradeType_Ventral_Sacs();
    initializeUpgradeType_Antennae();
    initializeUpgradeType_Pneumatized_Carapace();
    initializeUpgradeType_Metabolic_Boost();
    initializeUpgradeType_Adrenal_Glands();
    initializeUpgradeType_Muscular_Augments();
    initializeUpgradeType_Grooved_Spines();
    initializeUpgradeType_Gamete_Meiosis();
    initializeUpgradeType_Metasynaptic_Node();
    initializeUpgradeType_Singularity_Charge();
    initializeUpgradeType_Leg_Enhancements();
    initializeUpgradeType_Scarab_Damage();
    initializeUpgradeType_Reaver_Capacity();
    initializeUpgradeType_Gravitic_Drive();
    initializeUpgradeType_Sensor_Array();
    initializeUpgradeType_Gravitic_Boosters();
    initializeUpgradeType_Khaydarin_Amulet();
    initializeUpgradeType_Apial_Sensors();
    initializeUpgradeType_Gravitic_Thrusters();
    initializeUpgradeType_Carrier_Capacity();
    initializeUpgradeType_Khaydarin_Core();
    initializeUpgradeType_Argus_Jewel();
    initializeUpgradeType_Argus_Talisman();
    initializeUpgradeType_Caduceus_Reactor();
    initializeUpgradeType_Chitinous_Plating();
    initializeUpgradeType_Anabolic_Synthesis();
    initializeUpgradeType_Charon_Boosters();
    initializeUpgradeType_Upgrade_60();
    initializeUpgradeType_None();
    initializeUpgradeType_Unknown();
    initializeUpgradeType_MAX();
  }

  private static void initializeUpgradeType_Terran_Infantry_Armor() throws Exception {
    Class<?> c = UpgradeType.class;
    Map<String, Field> fields =
        Stream.of(c.getDeclaredFields())
            .collect(
                Collectors.toMap(
                    f -> f.getName(),
                    f -> {
                      f.setAccessible(true);
                      return f;
                    }));
    fields.get("iD").set(UpgradeType.Terran_Infantry_Armor, 0);
    fields.get("race").set(UpgradeType.Terran_Infantry_Armor, Race.Terran);
    fields.get("mineralPrices").set(UpgradeType.Terran_Infantry_Armor, new int[] {100, 100, 175});
    fields.get("mineralPriceFactor").set(UpgradeType.Terran_Infantry_Armor, 75);
    fields.get("gasPrices").set(UpgradeType.Terran_Infantry_Armor, new int[] {100, 100, 175});
    fields.get("gasPriceFactor").set(UpgradeType.Terran_Infantry_Armor, 75);
    fields.get("upgradeTimes").set(UpgradeType.Terran_Infantry_Armor, new int[] {4000, 4000, 4480});
    fields.get("upgradeTimeFactor").set(UpgradeType.Terran_Infantry_Armor, 480);
    fields.get("maxRepeats").set(UpgradeType.Terran_Infantry_Armor, 3);
    fields
        .get("whatUpgrades")
        .set(UpgradeType.Terran_Infantry_Armor, UnitType.Terran_Engineering_Bay);
    fields
        .get("whatsRequired")
        .set(
            UpgradeType.Terran_Infantry_Armor,
            new UnitType[] {UnitType.None, UnitType.None, UnitType.Terran_Science_Facility});
    fields
        .get("whatUses")
        .set(
            UpgradeType.Terran_Infantry_Armor,
            new ArrayList(
                Arrays.asList(
                    UnitType.Terran_Firebat,
                    UnitType.Hero_Sarah_Kerrigan,
                    UnitType.Terran_Marine,
                    UnitType.Terran_Ghost,
                    UnitType.Terran_Civilian,
                    UnitType.Terran_SCV,
                    UnitType.Terran_Medic,
                    UnitType.Hero_Gui_Montag,
                    UnitType.Hero_Jim_Raynor_Marine,
                    UnitType.Hero_Samir_Duran,
                    UnitType.Hero_Alexei_Stukov)));
  }

  private static void initializeUpgradeType_Terran_Vehicle_Plating() throws Exception {
    Class<?> c = UpgradeType.class;
    Map<String, Field> fields =
        Stream.of(c.getDeclaredFields())
            .collect(
                Collectors.toMap(
                    f -> f.getName(),
                    f -> {
                      f.setAccessible(true);
                      return f;
                    }));
    fields.get("iD").set(UpgradeType.Terran_Vehicle_Plating, 1);
    fields.get("race").set(UpgradeType.Terran_Vehicle_Plating, Race.Terran);
    fields.get("mineralPrices").set(UpgradeType.Terran_Vehicle_Plating, new int[] {100, 100, 175});
    fields.get("mineralPriceFactor").set(UpgradeType.Terran_Vehicle_Plating, 75);
    fields.get("gasPrices").set(UpgradeType.Terran_Vehicle_Plating, new int[] {100, 100, 175});
    fields.get("gasPriceFactor").set(UpgradeType.Terran_Vehicle_Plating, 75);
    fields
        .get("upgradeTimes")
        .set(UpgradeType.Terran_Vehicle_Plating, new int[] {4000, 4000, 4480});
    fields.get("upgradeTimeFactor").set(UpgradeType.Terran_Vehicle_Plating, 480);
    fields.get("maxRepeats").set(UpgradeType.Terran_Vehicle_Plating, 3);
    fields.get("whatUpgrades").set(UpgradeType.Terran_Vehicle_Plating, UnitType.Terran_Armory);
    fields
        .get("whatsRequired")
        .set(
            UpgradeType.Terran_Vehicle_Plating,
            new UnitType[] {UnitType.None, UnitType.None, UnitType.Terran_Science_Facility});
    fields
        .get("whatUses")
        .set(
            UpgradeType.Terran_Vehicle_Plating,
            new ArrayList(
                Arrays.asList(
                    UnitType.Terran_Vulture,
                    UnitType.Hero_Jim_Raynor_Vulture,
                    UnitType.Terran_Goliath,
                    UnitType.Terran_Siege_Tank_Tank_Mode,
                    UnitType.Hero_Edmund_Duke_Siege_Mode,
                    UnitType.Hero_Alan_Schezar,
                    UnitType.Hero_Edmund_Duke_Tank_Mode,
                    UnitType.Terran_Siege_Tank_Siege_Mode)));
  }

  private static void initializeUpgradeType_Terran_Ship_Plating() throws Exception {
    Class<?> c = UpgradeType.class;
    Map<String, Field> fields =
        Stream.of(c.getDeclaredFields())
            .collect(
                Collectors.toMap(
                    f -> f.getName(),
                    f -> {
                      f.setAccessible(true);
                      return f;
                    }));
    fields.get("iD").set(UpgradeType.Terran_Ship_Plating, 2);
    fields.get("race").set(UpgradeType.Terran_Ship_Plating, Race.Terran);
    fields.get("mineralPrices").set(UpgradeType.Terran_Ship_Plating, new int[] {150, 150, 225});
    fields.get("mineralPriceFactor").set(UpgradeType.Terran_Ship_Plating, 75);
    fields.get("gasPrices").set(UpgradeType.Terran_Ship_Plating, new int[] {150, 150, 225});
    fields.get("gasPriceFactor").set(UpgradeType.Terran_Ship_Plating, 75);
    fields.get("upgradeTimes").set(UpgradeType.Terran_Ship_Plating, new int[] {4000, 4000, 4480});
    fields.get("upgradeTimeFactor").set(UpgradeType.Terran_Ship_Plating, 480);
    fields.get("maxRepeats").set(UpgradeType.Terran_Ship_Plating, 3);
    fields.get("whatUpgrades").set(UpgradeType.Terran_Ship_Plating, UnitType.Terran_Armory);
    fields
        .get("whatsRequired")
        .set(
            UpgradeType.Terran_Ship_Plating,
            new UnitType[] {UnitType.None, UnitType.None, UnitType.Terran_Science_Facility});
    fields
        .get("whatUses")
        .set(
            UpgradeType.Terran_Ship_Plating,
            new ArrayList(
                Arrays.asList(
                    UnitType.Terran_Wraith,
                    UnitType.Terran_Science_Vessel,
                    UnitType.Hero_Arcturus_Mengsk,
                    UnitType.Terran_Dropship,
                    UnitType.Hero_Hyperion,
                    UnitType.Terran_Battlecruiser,
                    UnitType.Hero_Norad_II,
                    UnitType.Hero_Tom_Kazansky,
                    UnitType.Hero_Magellan,
                    UnitType.Terran_Valkyrie,
                    UnitType.Hero_Gerard_DuGalle)));
  }

  private static void initializeUpgradeType_Zerg_Carapace() throws Exception {
    Class<?> c = UpgradeType.class;
    Map<String, Field> fields =
        Stream.of(c.getDeclaredFields())
            .collect(
                Collectors.toMap(
                    f -> f.getName(),
                    f -> {
                      f.setAccessible(true);
                      return f;
                    }));
    fields.get("iD").set(UpgradeType.Zerg_Carapace, 3);
    fields.get("race").set(UpgradeType.Zerg_Carapace, Race.Zerg);
    fields.get("mineralPrices").set(UpgradeType.Zerg_Carapace, new int[] {150, 150, 225});
    fields.get("mineralPriceFactor").set(UpgradeType.Zerg_Carapace, 75);
    fields.get("gasPrices").set(UpgradeType.Zerg_Carapace, new int[] {150, 150, 225});
    fields.get("gasPriceFactor").set(UpgradeType.Zerg_Carapace, 75);
    fields.get("upgradeTimes").set(UpgradeType.Zerg_Carapace, new int[] {4000, 4000, 4480});
    fields.get("upgradeTimeFactor").set(UpgradeType.Zerg_Carapace, 480);
    fields.get("maxRepeats").set(UpgradeType.Zerg_Carapace, 3);
    fields.get("whatUpgrades").set(UpgradeType.Zerg_Carapace, UnitType.Zerg_Evolution_Chamber);
    fields
        .get("whatsRequired")
        .set(
            UpgradeType.Zerg_Carapace,
            new UnitType[] {UnitType.None, UnitType.None, UnitType.Zerg_Lair});
    fields
        .get("whatUses")
        .set(
            UpgradeType.Zerg_Carapace,
            new ArrayList(
                Arrays.asList(
                    UnitType.Zerg_Larva,
                    UnitType.Zerg_Egg,
                    UnitType.Zerg_Zergling,
                    UnitType.Zerg_Defiler,
                    UnitType.Zerg_Hydralisk,
                    UnitType.Zerg_Lurker,
                    UnitType.Zerg_Ultralisk,
                    UnitType.Hero_Torrasque,
                    UnitType.Hero_Infested_Duran,
                    UnitType.Zerg_Broodling,
                    UnitType.Zerg_Drone,
                    UnitType.Zerg_Infested_Terran,
                    UnitType.Hero_Infested_Kerrigan,
                    UnitType.Hero_Unclean_One,
                    UnitType.Hero_Hunter_Killer,
                    UnitType.Hero_Devouring_One,
                    UnitType.Zerg_Cocoon,
                    UnitType.Zerg_Lurker_Egg)));
  }

  private static void initializeUpgradeType_Zerg_Flyer_Carapace() throws Exception {
    Class<?> c = UpgradeType.class;
    Map<String, Field> fields =
        Stream.of(c.getDeclaredFields())
            .collect(
                Collectors.toMap(
                    f -> f.getName(),
                    f -> {
                      f.setAccessible(true);
                      return f;
                    }));
    fields.get("iD").set(UpgradeType.Zerg_Flyer_Carapace, 4);
    fields.get("race").set(UpgradeType.Zerg_Flyer_Carapace, Race.Zerg);
    fields.get("mineralPrices").set(UpgradeType.Zerg_Flyer_Carapace, new int[] {150, 150, 225});
    fields.get("mineralPriceFactor").set(UpgradeType.Zerg_Flyer_Carapace, 75);
    fields.get("gasPrices").set(UpgradeType.Zerg_Flyer_Carapace, new int[] {150, 150, 225});
    fields.get("gasPriceFactor").set(UpgradeType.Zerg_Flyer_Carapace, 75);
    fields.get("upgradeTimes").set(UpgradeType.Zerg_Flyer_Carapace, new int[] {4000, 4000, 4480});
    fields.get("upgradeTimeFactor").set(UpgradeType.Zerg_Flyer_Carapace, 480);
    fields.get("maxRepeats").set(UpgradeType.Zerg_Flyer_Carapace, 3);
    fields.get("whatUpgrades").set(UpgradeType.Zerg_Flyer_Carapace, UnitType.Zerg_Spire);
    fields
        .get("whatsRequired")
        .set(
            UpgradeType.Zerg_Flyer_Carapace,
            new UnitType[] {UnitType.None, UnitType.None, UnitType.Zerg_Lair});
    fields
        .get("whatUses")
        .set(
            UpgradeType.Zerg_Flyer_Carapace,
            new ArrayList(
                Arrays.asList(
                    UnitType.Zerg_Overlord,
                    UnitType.Zerg_Mutalisk,
                    UnitType.Zerg_Guardian,
                    UnitType.Zerg_Queen,
                    UnitType.Hero_Kukulza_Mutalisk,
                    UnitType.Zerg_Scourge,
                    UnitType.Hero_Yggdrasill,
                    UnitType.Hero_Matriarch,
                    UnitType.Hero_Kukulza_Guardian,
                    UnitType.Zerg_Devourer)));
  }

  private static void initializeUpgradeType_Protoss_Ground_Armor() throws Exception {
    Class<?> c = UpgradeType.class;
    Map<String, Field> fields =
        Stream.of(c.getDeclaredFields())
            .collect(
                Collectors.toMap(
                    f -> f.getName(),
                    f -> {
                      f.setAccessible(true);
                      return f;
                    }));
    fields.get("iD").set(UpgradeType.Protoss_Ground_Armor, 5);
    fields.get("race").set(UpgradeType.Protoss_Ground_Armor, Race.Protoss);
    fields.get("mineralPrices").set(UpgradeType.Protoss_Ground_Armor, new int[] {100, 100, 175});
    fields.get("mineralPriceFactor").set(UpgradeType.Protoss_Ground_Armor, 75);
    fields.get("gasPrices").set(UpgradeType.Protoss_Ground_Armor, new int[] {100, 100, 175});
    fields.get("gasPriceFactor").set(UpgradeType.Protoss_Ground_Armor, 75);
    fields.get("upgradeTimes").set(UpgradeType.Protoss_Ground_Armor, new int[] {4000, 4000, 4480});
    fields.get("upgradeTimeFactor").set(UpgradeType.Protoss_Ground_Armor, 480);
    fields.get("maxRepeats").set(UpgradeType.Protoss_Ground_Armor, 3);
    fields.get("whatUpgrades").set(UpgradeType.Protoss_Ground_Armor, UnitType.Protoss_Forge);
    fields
        .get("whatsRequired")
        .set(
            UpgradeType.Protoss_Ground_Armor,
            new UnitType[] {UnitType.None, UnitType.None, UnitType.Protoss_Templar_Archives});
    fields
        .get("whatUses")
        .set(
            UpgradeType.Protoss_Ground_Armor,
            new ArrayList(
                Arrays.asList(
                    UnitType.Protoss_Dark_Templar,
                    UnitType.Protoss_Dark_Archon,
                    UnitType.Protoss_Probe,
                    UnitType.Protoss_Zealot,
                    UnitType.Hero_Dark_Templar,
                    UnitType.Protoss_Dragoon,
                    UnitType.Hero_Zeratul,
                    UnitType.Protoss_High_Templar,
                    UnitType.Protoss_Archon,
                    UnitType.Hero_Tassadar_Zeratul_Archon,
                    UnitType.Hero_Fenix_Zealot,
                    UnitType.Hero_Fenix_Dragoon,
                    UnitType.Hero_Tassadar,
                    UnitType.Hero_Warbringer,
                    UnitType.Protoss_Reaver,
                    UnitType.Hero_Aldaris)));
  }

  private static void initializeUpgradeType_Protoss_Air_Armor() throws Exception {
    Class<?> c = UpgradeType.class;
    Map<String, Field> fields =
        Stream.of(c.getDeclaredFields())
            .collect(
                Collectors.toMap(
                    f -> f.getName(),
                    f -> {
                      f.setAccessible(true);
                      return f;
                    }));
    fields.get("iD").set(UpgradeType.Protoss_Air_Armor, 6);
    fields.get("race").set(UpgradeType.Protoss_Air_Armor, Race.Protoss);
    fields.get("mineralPrices").set(UpgradeType.Protoss_Air_Armor, new int[] {150, 150, 225});
    fields.get("mineralPriceFactor").set(UpgradeType.Protoss_Air_Armor, 75);
    fields.get("gasPrices").set(UpgradeType.Protoss_Air_Armor, new int[] {150, 150, 225});
    fields.get("gasPriceFactor").set(UpgradeType.Protoss_Air_Armor, 75);
    fields.get("upgradeTimes").set(UpgradeType.Protoss_Air_Armor, new int[] {4000, 4000, 4480});
    fields.get("upgradeTimeFactor").set(UpgradeType.Protoss_Air_Armor, 480);
    fields.get("maxRepeats").set(UpgradeType.Protoss_Air_Armor, 3);
    fields
        .get("whatUpgrades")
        .set(UpgradeType.Protoss_Air_Armor, UnitType.Protoss_Cybernetics_Core);
    fields
        .get("whatsRequired")
        .set(
            UpgradeType.Protoss_Air_Armor,
            new UnitType[] {UnitType.None, UnitType.None, UnitType.Protoss_Fleet_Beacon});
    fields
        .get("whatUses")
        .set(
            UpgradeType.Protoss_Air_Armor,
            new ArrayList(
                Arrays.asList(
                    UnitType.Protoss_Observer,
                    UnitType.Protoss_Corsair,
                    UnitType.Protoss_Shuttle,
                    UnitType.Protoss_Scout,
                    UnitType.Protoss_Arbiter,
                    UnitType.Hero_Mojo,
                    UnitType.Protoss_Carrier,
                    UnitType.Protoss_Interceptor,
                    UnitType.Hero_Gantrithor,
                    UnitType.Hero_Danimoth,
                    UnitType.Hero_Artanis,
                    UnitType.Hero_Raszagal)));
  }

  private static void initializeUpgradeType_Terran_Infantry_Weapons() throws Exception {
    Class<?> c = UpgradeType.class;
    Map<String, Field> fields =
        Stream.of(c.getDeclaredFields())
            .collect(
                Collectors.toMap(
                    f -> f.getName(),
                    f -> {
                      f.setAccessible(true);
                      return f;
                    }));
    fields.get("iD").set(UpgradeType.Terran_Infantry_Weapons, 7);
    fields.get("race").set(UpgradeType.Terran_Infantry_Weapons, Race.Terran);
    fields.get("mineralPrices").set(UpgradeType.Terran_Infantry_Weapons, new int[] {100, 100, 175});
    fields.get("mineralPriceFactor").set(UpgradeType.Terran_Infantry_Weapons, 75);
    fields.get("gasPrices").set(UpgradeType.Terran_Infantry_Weapons, new int[] {100, 100, 175});
    fields.get("gasPriceFactor").set(UpgradeType.Terran_Infantry_Weapons, 75);
    fields
        .get("upgradeTimes")
        .set(UpgradeType.Terran_Infantry_Weapons, new int[] {4000, 4000, 4480});
    fields.get("upgradeTimeFactor").set(UpgradeType.Terran_Infantry_Weapons, 480);
    fields.get("maxRepeats").set(UpgradeType.Terran_Infantry_Weapons, 3);
    fields
        .get("whatUpgrades")
        .set(UpgradeType.Terran_Infantry_Weapons, UnitType.Terran_Engineering_Bay);
    fields
        .get("whatsRequired")
        .set(
            UpgradeType.Terran_Infantry_Weapons,
            new UnitType[] {UnitType.None, UnitType.None, UnitType.Terran_Science_Facility});
    fields
        .get("whatUses")
        .set(
            UpgradeType.Terran_Infantry_Weapons,
            new ArrayList(
                Arrays.asList(
                    UnitType.Terran_Firebat,
                    UnitType.Hero_Sarah_Kerrigan,
                    UnitType.Terran_Marine,
                    UnitType.Hero_Jim_Raynor_Marine,
                    UnitType.Terran_Ghost,
                    UnitType.Hero_Gui_Montag,
                    UnitType.Hero_Samir_Duran,
                    UnitType.Special_Wall_Flame_Trap,
                    UnitType.Special_Right_Wall_Flame_Trap,
                    UnitType.Hero_Alexei_Stukov,
                    UnitType.Hero_Infested_Duran)));
  }

  private static void initializeUpgradeType_Terran_Vehicle_Weapons() throws Exception {
    Class<?> c = UpgradeType.class;
    Map<String, Field> fields =
        Stream.of(c.getDeclaredFields())
            .collect(
                Collectors.toMap(
                    f -> f.getName(),
                    f -> {
                      f.setAccessible(true);
                      return f;
                    }));
    fields.get("iD").set(UpgradeType.Terran_Vehicle_Weapons, 8);
    fields.get("race").set(UpgradeType.Terran_Vehicle_Weapons, Race.Terran);
    fields.get("mineralPrices").set(UpgradeType.Terran_Vehicle_Weapons, new int[] {100, 100, 175});
    fields.get("mineralPriceFactor").set(UpgradeType.Terran_Vehicle_Weapons, 75);
    fields.get("gasPrices").set(UpgradeType.Terran_Vehicle_Weapons, new int[] {100, 100, 175});
    fields.get("gasPriceFactor").set(UpgradeType.Terran_Vehicle_Weapons, 75);
    fields
        .get("upgradeTimes")
        .set(UpgradeType.Terran_Vehicle_Weapons, new int[] {4000, 4000, 4480});
    fields.get("upgradeTimeFactor").set(UpgradeType.Terran_Vehicle_Weapons, 480);
    fields.get("maxRepeats").set(UpgradeType.Terran_Vehicle_Weapons, 3);
    fields.get("whatUpgrades").set(UpgradeType.Terran_Vehicle_Weapons, UnitType.Terran_Armory);
    fields
        .get("whatsRequired")
        .set(
            UpgradeType.Terran_Vehicle_Weapons,
            new UnitType[] {UnitType.None, UnitType.None, UnitType.Terran_Science_Facility});
    fields
        .get("whatUses")
        .set(
            UpgradeType.Terran_Vehicle_Weapons,
            new ArrayList(
                Arrays.asList(
                    UnitType.Terran_Vulture,
                    UnitType.Special_Floor_Missile_Trap,
                    UnitType.Terran_Goliath,
                    UnitType.Hero_Jim_Raynor_Vulture,
                    UnitType.Hero_Edmund_Duke_Siege_Mode,
                    UnitType.Special_Floor_Gun_Trap,
                    UnitType.Hero_Alan_Schezar,
                    UnitType.Terran_Siege_Tank_Tank_Mode,
                    UnitType.Terran_Siege_Tank_Siege_Mode,
                    UnitType.Hero_Edmund_Duke_Tank_Mode,
                    UnitType.Special_Wall_Missile_Trap,
                    UnitType.Special_Right_Wall_Missile_Trap)));
  }

  private static void initializeUpgradeType_Terran_Ship_Weapons() throws Exception {
    Class<?> c = UpgradeType.class;
    Map<String, Field> fields =
        Stream.of(c.getDeclaredFields())
            .collect(
                Collectors.toMap(
                    f -> f.getName(),
                    f -> {
                      f.setAccessible(true);
                      return f;
                    }));
    fields.get("iD").set(UpgradeType.Terran_Ship_Weapons, 9);
    fields.get("race").set(UpgradeType.Terran_Ship_Weapons, Race.Terran);
    fields.get("mineralPrices").set(UpgradeType.Terran_Ship_Weapons, new int[] {100, 100, 150});
    fields.get("mineralPriceFactor").set(UpgradeType.Terran_Ship_Weapons, 50);
    fields.get("gasPrices").set(UpgradeType.Terran_Ship_Weapons, new int[] {100, 100, 150});
    fields.get("gasPriceFactor").set(UpgradeType.Terran_Ship_Weapons, 50);
    fields.get("upgradeTimes").set(UpgradeType.Terran_Ship_Weapons, new int[] {4000, 4000, 4480});
    fields.get("upgradeTimeFactor").set(UpgradeType.Terran_Ship_Weapons, 480);
    fields.get("maxRepeats").set(UpgradeType.Terran_Ship_Weapons, 3);
    fields.get("whatUpgrades").set(UpgradeType.Terran_Ship_Weapons, UnitType.Terran_Armory);
    fields
        .get("whatsRequired")
        .set(
            UpgradeType.Terran_Ship_Weapons,
            new UnitType[] {UnitType.None, UnitType.None, UnitType.Terran_Science_Facility});
    fields
        .get("whatUses")
        .set(
            UpgradeType.Terran_Ship_Weapons,
            new ArrayList(
                Arrays.asList(
                    UnitType.Terran_Wraith,
                    UnitType.Hero_Norad_II,
                    UnitType.Hero_Tom_Kazansky,
                    UnitType.Hero_Hyperion,
                    UnitType.Terran_Battlecruiser,
                    UnitType.Hero_Arcturus_Mengsk,
                    UnitType.Hero_Gerard_DuGalle,
                    UnitType.Terran_Valkyrie)));
  }

  private static void initializeUpgradeType_Zerg_Melee_Attacks() throws Exception {
    Class<?> c = UpgradeType.class;
    Map<String, Field> fields =
        Stream.of(c.getDeclaredFields())
            .collect(
                Collectors.toMap(
                    f -> f.getName(),
                    f -> {
                      f.setAccessible(true);
                      return f;
                    }));
    fields.get("iD").set(UpgradeType.Zerg_Melee_Attacks, 10);
    fields.get("race").set(UpgradeType.Zerg_Melee_Attacks, Race.Zerg);
    fields.get("mineralPrices").set(UpgradeType.Zerg_Melee_Attacks, new int[] {100, 100, 150});
    fields.get("mineralPriceFactor").set(UpgradeType.Zerg_Melee_Attacks, 50);
    fields.get("gasPrices").set(UpgradeType.Zerg_Melee_Attacks, new int[] {100, 100, 150});
    fields.get("gasPriceFactor").set(UpgradeType.Zerg_Melee_Attacks, 50);
    fields.get("upgradeTimes").set(UpgradeType.Zerg_Melee_Attacks, new int[] {4000, 4000, 4480});
    fields.get("upgradeTimeFactor").set(UpgradeType.Zerg_Melee_Attacks, 480);
    fields.get("maxRepeats").set(UpgradeType.Zerg_Melee_Attacks, 3);
    fields.get("whatUpgrades").set(UpgradeType.Zerg_Melee_Attacks, UnitType.Zerg_Evolution_Chamber);
    fields
        .get("whatsRequired")
        .set(
            UpgradeType.Zerg_Melee_Attacks,
            new UnitType[] {UnitType.None, UnitType.None, UnitType.Zerg_Lair});
    fields
        .get("whatUses")
        .set(
            UpgradeType.Zerg_Melee_Attacks,
            new ArrayList(
                Arrays.asList(
                    UnitType.Zerg_Zergling,
                    UnitType.Hero_Devouring_One,
                    UnitType.Hero_Infested_Kerrigan,
                    UnitType.Zerg_Ultralisk,
                    UnitType.Zerg_Broodling,
                    UnitType.Hero_Torrasque)));
  }

  private static void initializeUpgradeType_Zerg_Missile_Attacks() throws Exception {
    Class<?> c = UpgradeType.class;
    Map<String, Field> fields =
        Stream.of(c.getDeclaredFields())
            .collect(
                Collectors.toMap(
                    f -> f.getName(),
                    f -> {
                      f.setAccessible(true);
                      return f;
                    }));
    fields.get("iD").set(UpgradeType.Zerg_Missile_Attacks, 11);
    fields.get("race").set(UpgradeType.Zerg_Missile_Attacks, Race.Zerg);
    fields.get("mineralPrices").set(UpgradeType.Zerg_Missile_Attacks, new int[] {100, 100, 150});
    fields.get("mineralPriceFactor").set(UpgradeType.Zerg_Missile_Attacks, 50);
    fields.get("gasPrices").set(UpgradeType.Zerg_Missile_Attacks, new int[] {100, 100, 150});
    fields.get("gasPriceFactor").set(UpgradeType.Zerg_Missile_Attacks, 50);
    fields.get("upgradeTimes").set(UpgradeType.Zerg_Missile_Attacks, new int[] {4000, 4000, 4480});
    fields.get("upgradeTimeFactor").set(UpgradeType.Zerg_Missile_Attacks, 480);
    fields.get("maxRepeats").set(UpgradeType.Zerg_Missile_Attacks, 3);
    fields
        .get("whatUpgrades")
        .set(UpgradeType.Zerg_Missile_Attacks, UnitType.Zerg_Evolution_Chamber);
    fields
        .get("whatsRequired")
        .set(
            UpgradeType.Zerg_Missile_Attacks,
            new UnitType[] {UnitType.None, UnitType.None, UnitType.Zerg_Lair});
    fields
        .get("whatUses")
        .set(
            UpgradeType.Zerg_Missile_Attacks,
            new ArrayList(
                Arrays.asList(
                    UnitType.Zerg_Hydralisk, UnitType.Hero_Hunter_Killer, UnitType.Zerg_Lurker)));
  }

  private static void initializeUpgradeType_Zerg_Flyer_Attacks() throws Exception {
    Class<?> c = UpgradeType.class;
    Map<String, Field> fields =
        Stream.of(c.getDeclaredFields())
            .collect(
                Collectors.toMap(
                    f -> f.getName(),
                    f -> {
                      f.setAccessible(true);
                      return f;
                    }));
    fields.get("iD").set(UpgradeType.Zerg_Flyer_Attacks, 12);
    fields.get("race").set(UpgradeType.Zerg_Flyer_Attacks, Race.Zerg);
    fields.get("mineralPrices").set(UpgradeType.Zerg_Flyer_Attacks, new int[] {100, 100, 175});
    fields.get("mineralPriceFactor").set(UpgradeType.Zerg_Flyer_Attacks, 75);
    fields.get("gasPrices").set(UpgradeType.Zerg_Flyer_Attacks, new int[] {100, 100, 175});
    fields.get("gasPriceFactor").set(UpgradeType.Zerg_Flyer_Attacks, 75);
    fields.get("upgradeTimes").set(UpgradeType.Zerg_Flyer_Attacks, new int[] {4000, 4000, 4480});
    fields.get("upgradeTimeFactor").set(UpgradeType.Zerg_Flyer_Attacks, 480);
    fields.get("maxRepeats").set(UpgradeType.Zerg_Flyer_Attacks, 3);
    fields.get("whatUpgrades").set(UpgradeType.Zerg_Flyer_Attacks, UnitType.Zerg_Spire);
    fields
        .get("whatsRequired")
        .set(
            UpgradeType.Zerg_Flyer_Attacks,
            new UnitType[] {UnitType.None, UnitType.None, UnitType.Zerg_Lair});
    fields
        .get("whatUses")
        .set(
            UpgradeType.Zerg_Flyer_Attacks,
            new ArrayList(
                Arrays.asList(
                    UnitType.Zerg_Mutalisk,
                    UnitType.Hero_Kukulza_Mutalisk,
                    UnitType.Hero_Kukulza_Guardian,
                    UnitType.Zerg_Guardian,
                    UnitType.Zerg_Devourer)));
  }

  private static void initializeUpgradeType_Protoss_Ground_Weapons() throws Exception {
    Class<?> c = UpgradeType.class;
    Map<String, Field> fields =
        Stream.of(c.getDeclaredFields())
            .collect(
                Collectors.toMap(
                    f -> f.getName(),
                    f -> {
                      f.setAccessible(true);
                      return f;
                    }));
    fields.get("iD").set(UpgradeType.Protoss_Ground_Weapons, 13);
    fields.get("race").set(UpgradeType.Protoss_Ground_Weapons, Race.Protoss);
    fields.get("mineralPrices").set(UpgradeType.Protoss_Ground_Weapons, new int[] {100, 100, 150});
    fields.get("mineralPriceFactor").set(UpgradeType.Protoss_Ground_Weapons, 50);
    fields.get("gasPrices").set(UpgradeType.Protoss_Ground_Weapons, new int[] {100, 100, 150});
    fields.get("gasPriceFactor").set(UpgradeType.Protoss_Ground_Weapons, 50);
    fields
        .get("upgradeTimes")
        .set(UpgradeType.Protoss_Ground_Weapons, new int[] {4000, 4000, 4480});
    fields.get("upgradeTimeFactor").set(UpgradeType.Protoss_Ground_Weapons, 480);
    fields.get("maxRepeats").set(UpgradeType.Protoss_Ground_Weapons, 3);
    fields.get("whatUpgrades").set(UpgradeType.Protoss_Ground_Weapons, UnitType.Protoss_Forge);
    fields
        .get("whatsRequired")
        .set(
            UpgradeType.Protoss_Ground_Weapons,
            new UnitType[] {UnitType.None, UnitType.None, UnitType.Protoss_Templar_Archives});
    fields
        .get("whatUses")
        .set(
            UpgradeType.Protoss_Ground_Weapons,
            new ArrayList(
                Arrays.asList(
                    UnitType.Protoss_Zealot,
                    UnitType.Hero_Fenix_Zealot,
                    UnitType.Hero_Dark_Templar,
                    UnitType.Protoss_Dragoon,
                    UnitType.Hero_Fenix_Dragoon,
                    UnitType.Hero_Aldaris,
                    UnitType.Hero_Tassadar,
                    UnitType.Hero_Tassadar_Zeratul_Archon,
                    UnitType.Protoss_Archon,
                    UnitType.Hero_Zeratul,
                    UnitType.Protoss_Dark_Templar)));
  }

  private static void initializeUpgradeType_Protoss_Air_Weapons() throws Exception {
    Class<?> c = UpgradeType.class;
    Map<String, Field> fields =
        Stream.of(c.getDeclaredFields())
            .collect(
                Collectors.toMap(
                    f -> f.getName(),
                    f -> {
                      f.setAccessible(true);
                      return f;
                    }));
    fields.get("iD").set(UpgradeType.Protoss_Air_Weapons, 14);
    fields.get("race").set(UpgradeType.Protoss_Air_Weapons, Race.Protoss);
    fields.get("mineralPrices").set(UpgradeType.Protoss_Air_Weapons, new int[] {100, 100, 175});
    fields.get("mineralPriceFactor").set(UpgradeType.Protoss_Air_Weapons, 75);
    fields.get("gasPrices").set(UpgradeType.Protoss_Air_Weapons, new int[] {100, 100, 175});
    fields.get("gasPriceFactor").set(UpgradeType.Protoss_Air_Weapons, 75);
    fields.get("upgradeTimes").set(UpgradeType.Protoss_Air_Weapons, new int[] {4000, 4000, 4480});
    fields.get("upgradeTimeFactor").set(UpgradeType.Protoss_Air_Weapons, 480);
    fields.get("maxRepeats").set(UpgradeType.Protoss_Air_Weapons, 3);
    fields
        .get("whatUpgrades")
        .set(UpgradeType.Protoss_Air_Weapons, UnitType.Protoss_Cybernetics_Core);
    fields
        .get("whatsRequired")
        .set(
            UpgradeType.Protoss_Air_Weapons,
            new UnitType[] {UnitType.None, UnitType.None, UnitType.Protoss_Fleet_Beacon});
    fields
        .get("whatUses")
        .set(
            UpgradeType.Protoss_Air_Weapons,
            new ArrayList(
                Arrays.asList(
                    UnitType.Hero_Danimoth,
                    UnitType.Protoss_Scout,
                    UnitType.Hero_Artanis,
                    UnitType.Protoss_Carrier,
                    UnitType.Hero_Mojo,
                    UnitType.Protoss_Arbiter,
                    UnitType.Protoss_Interceptor,
                    UnitType.Protoss_Corsair)));
  }

  private static void initializeUpgradeType_Protoss_Plasma_Shields() throws Exception {
    Class<?> c = UpgradeType.class;
    Map<String, Field> fields =
        Stream.of(c.getDeclaredFields())
            .collect(
                Collectors.toMap(
                    f -> f.getName(),
                    f -> {
                      f.setAccessible(true);
                      return f;
                    }));
    fields.get("iD").set(UpgradeType.Protoss_Plasma_Shields, 15);
    fields.get("race").set(UpgradeType.Protoss_Plasma_Shields, Race.Protoss);
    fields.get("mineralPrices").set(UpgradeType.Protoss_Plasma_Shields, new int[] {200, 200, 300});
    fields.get("mineralPriceFactor").set(UpgradeType.Protoss_Plasma_Shields, 100);
    fields.get("gasPrices").set(UpgradeType.Protoss_Plasma_Shields, new int[] {200, 200, 300});
    fields.get("gasPriceFactor").set(UpgradeType.Protoss_Plasma_Shields, 100);
    fields
        .get("upgradeTimes")
        .set(UpgradeType.Protoss_Plasma_Shields, new int[] {4000, 4000, 4480});
    fields.get("upgradeTimeFactor").set(UpgradeType.Protoss_Plasma_Shields, 480);
    fields.get("maxRepeats").set(UpgradeType.Protoss_Plasma_Shields, 3);
    fields.get("whatUpgrades").set(UpgradeType.Protoss_Plasma_Shields, UnitType.Protoss_Forge);
    fields
        .get("whatsRequired")
        .set(
            UpgradeType.Protoss_Plasma_Shields,
            new UnitType[] {UnitType.None, UnitType.None, UnitType.Protoss_Cybernetics_Core});
    fields
        .get("whatUses")
        .set(
            UpgradeType.Protoss_Plasma_Shields,
            new ArrayList(
                Arrays.asList(
                    UnitType.Protoss_Archon,
                    UnitType.Protoss_Corsair,
                    UnitType.Protoss_Shuttle,
                    UnitType.Protoss_Dark_Templar,
                    UnitType.Protoss_Dark_Archon,
                    UnitType.Protoss_Probe,
                    UnitType.Protoss_Zealot,
                    UnitType.Protoss_Dragoon,
                    UnitType.Protoss_High_Templar,
                    UnitType.Protoss_Scout,
                    UnitType.Protoss_Arbiter,
                    UnitType.Protoss_Carrier,
                    UnitType.Protoss_Interceptor,
                    UnitType.Hero_Dark_Templar,
                    UnitType.Hero_Zeratul,
                    UnitType.Hero_Tassadar_Zeratul_Archon,
                    UnitType.Hero_Fenix_Zealot,
                    UnitType.Hero_Fenix_Dragoon,
                    UnitType.Hero_Tassadar,
                    UnitType.Hero_Mojo,
                    UnitType.Hero_Warbringer,
                    UnitType.Hero_Gantrithor,
                    UnitType.Protoss_Reaver,
                    UnitType.Protoss_Observer,
                    UnitType.Hero_Danimoth,
                    UnitType.Hero_Aldaris,
                    UnitType.Hero_Artanis,
                    UnitType.Hero_Raszagal)));
  }

  private static void initializeUpgradeType_U_238_Shells() throws Exception {
    Class<?> c = UpgradeType.class;
    Map<String, Field> fields =
        Stream.of(c.getDeclaredFields())
            .collect(
                Collectors.toMap(
                    f -> f.getName(),
                    f -> {
                      f.setAccessible(true);
                      return f;
                    }));
    fields.get("iD").set(UpgradeType.U_238_Shells, 16);
    fields.get("race").set(UpgradeType.U_238_Shells, Race.Terran);
    fields.get("mineralPrices").set(UpgradeType.U_238_Shells, new int[] {150});
    fields.get("mineralPriceFactor").set(UpgradeType.U_238_Shells, 0);
    fields.get("gasPrices").set(UpgradeType.U_238_Shells, new int[] {150});
    fields.get("gasPriceFactor").set(UpgradeType.U_238_Shells, 0);
    fields.get("upgradeTimes").set(UpgradeType.U_238_Shells, new int[] {1500});
    fields.get("upgradeTimeFactor").set(UpgradeType.U_238_Shells, 0);
    fields.get("maxRepeats").set(UpgradeType.U_238_Shells, 1);
    fields.get("whatUpgrades").set(UpgradeType.U_238_Shells, UnitType.Terran_Academy);
    fields.get("whatsRequired").set(UpgradeType.U_238_Shells, new UnitType[] {UnitType.None});
    fields
        .get("whatUses")
        .set(UpgradeType.U_238_Shells, new ArrayList(Arrays.asList(UnitType.Terran_Marine)));
  }

  private static void initializeUpgradeType_Ion_Thrusters() throws Exception {
    Class<?> c = UpgradeType.class;
    Map<String, Field> fields =
        Stream.of(c.getDeclaredFields())
            .collect(
                Collectors.toMap(
                    f -> f.getName(),
                    f -> {
                      f.setAccessible(true);
                      return f;
                    }));
    fields.get("iD").set(UpgradeType.Ion_Thrusters, 17);
    fields.get("race").set(UpgradeType.Ion_Thrusters, Race.Terran);
    fields.get("mineralPrices").set(UpgradeType.Ion_Thrusters, new int[] {100});
    fields.get("mineralPriceFactor").set(UpgradeType.Ion_Thrusters, 0);
    fields.get("gasPrices").set(UpgradeType.Ion_Thrusters, new int[] {100});
    fields.get("gasPriceFactor").set(UpgradeType.Ion_Thrusters, 0);
    fields.get("upgradeTimes").set(UpgradeType.Ion_Thrusters, new int[] {1500});
    fields.get("upgradeTimeFactor").set(UpgradeType.Ion_Thrusters, 0);
    fields.get("maxRepeats").set(UpgradeType.Ion_Thrusters, 1);
    fields.get("whatUpgrades").set(UpgradeType.Ion_Thrusters, UnitType.Terran_Machine_Shop);
    fields.get("whatsRequired").set(UpgradeType.Ion_Thrusters, new UnitType[] {UnitType.None});
    fields
        .get("whatUses")
        .set(UpgradeType.Ion_Thrusters, new ArrayList(Arrays.asList(UnitType.Terran_Vulture)));
  }

  private static void initializeUpgradeType_Titan_Reactor() throws Exception {
    Class<?> c = UpgradeType.class;
    Map<String, Field> fields =
        Stream.of(c.getDeclaredFields())
            .collect(
                Collectors.toMap(
                    f -> f.getName(),
                    f -> {
                      f.setAccessible(true);
                      return f;
                    }));
    fields.get("iD").set(UpgradeType.Titan_Reactor, 19);
    fields.get("race").set(UpgradeType.Titan_Reactor, Race.Terran);
    fields.get("mineralPrices").set(UpgradeType.Titan_Reactor, new int[] {150});
    fields.get("mineralPriceFactor").set(UpgradeType.Titan_Reactor, 0);
    fields.get("gasPrices").set(UpgradeType.Titan_Reactor, new int[] {150});
    fields.get("gasPriceFactor").set(UpgradeType.Titan_Reactor, 0);
    fields.get("upgradeTimes").set(UpgradeType.Titan_Reactor, new int[] {2500});
    fields.get("upgradeTimeFactor").set(UpgradeType.Titan_Reactor, 0);
    fields.get("maxRepeats").set(UpgradeType.Titan_Reactor, 1);
    fields.get("whatUpgrades").set(UpgradeType.Titan_Reactor, UnitType.Terran_Science_Facility);
    fields.get("whatsRequired").set(UpgradeType.Titan_Reactor, new UnitType[] {UnitType.None});
    fields
        .get("whatUses")
        .set(
            UpgradeType.Titan_Reactor,
            new ArrayList(Arrays.asList(UnitType.Terran_Science_Vessel)));
  }

  private static void initializeUpgradeType_Ocular_Implants() throws Exception {
    Class<?> c = UpgradeType.class;
    Map<String, Field> fields =
        Stream.of(c.getDeclaredFields())
            .collect(
                Collectors.toMap(
                    f -> f.getName(),
                    f -> {
                      f.setAccessible(true);
                      return f;
                    }));
    fields.get("iD").set(UpgradeType.Ocular_Implants, 20);
    fields.get("race").set(UpgradeType.Ocular_Implants, Race.Terran);
    fields.get("mineralPrices").set(UpgradeType.Ocular_Implants, new int[] {100});
    fields.get("mineralPriceFactor").set(UpgradeType.Ocular_Implants, 0);
    fields.get("gasPrices").set(UpgradeType.Ocular_Implants, new int[] {100});
    fields.get("gasPriceFactor").set(UpgradeType.Ocular_Implants, 0);
    fields.get("upgradeTimes").set(UpgradeType.Ocular_Implants, new int[] {2500});
    fields.get("upgradeTimeFactor").set(UpgradeType.Ocular_Implants, 0);
    fields.get("maxRepeats").set(UpgradeType.Ocular_Implants, 1);
    fields.get("whatUpgrades").set(UpgradeType.Ocular_Implants, UnitType.Terran_Covert_Ops);
    fields.get("whatsRequired").set(UpgradeType.Ocular_Implants, new UnitType[] {UnitType.None});
    fields
        .get("whatUses")
        .set(UpgradeType.Ocular_Implants, new ArrayList(Arrays.asList(UnitType.Terran_Ghost)));
  }

  private static void initializeUpgradeType_Moebius_Reactor() throws Exception {
    Class<?> c = UpgradeType.class;
    Map<String, Field> fields =
        Stream.of(c.getDeclaredFields())
            .collect(
                Collectors.toMap(
                    f -> f.getName(),
                    f -> {
                      f.setAccessible(true);
                      return f;
                    }));
    fields.get("iD").set(UpgradeType.Moebius_Reactor, 21);
    fields.get("race").set(UpgradeType.Moebius_Reactor, Race.Terran);
    fields.get("mineralPrices").set(UpgradeType.Moebius_Reactor, new int[] {150});
    fields.get("mineralPriceFactor").set(UpgradeType.Moebius_Reactor, 0);
    fields.get("gasPrices").set(UpgradeType.Moebius_Reactor, new int[] {150});
    fields.get("gasPriceFactor").set(UpgradeType.Moebius_Reactor, 0);
    fields.get("upgradeTimes").set(UpgradeType.Moebius_Reactor, new int[] {2500});
    fields.get("upgradeTimeFactor").set(UpgradeType.Moebius_Reactor, 0);
    fields.get("maxRepeats").set(UpgradeType.Moebius_Reactor, 1);
    fields.get("whatUpgrades").set(UpgradeType.Moebius_Reactor, UnitType.Terran_Covert_Ops);
    fields.get("whatsRequired").set(UpgradeType.Moebius_Reactor, new UnitType[] {UnitType.None});
    fields
        .get("whatUses")
        .set(UpgradeType.Moebius_Reactor, new ArrayList(Arrays.asList(UnitType.Terran_Ghost)));
  }

  private static void initializeUpgradeType_Apollo_Reactor() throws Exception {
    Class<?> c = UpgradeType.class;
    Map<String, Field> fields =
        Stream.of(c.getDeclaredFields())
            .collect(
                Collectors.toMap(
                    f -> f.getName(),
                    f -> {
                      f.setAccessible(true);
                      return f;
                    }));
    fields.get("iD").set(UpgradeType.Apollo_Reactor, 22);
    fields.get("race").set(UpgradeType.Apollo_Reactor, Race.Terran);
    fields.get("mineralPrices").set(UpgradeType.Apollo_Reactor, new int[] {200});
    fields.get("mineralPriceFactor").set(UpgradeType.Apollo_Reactor, 0);
    fields.get("gasPrices").set(UpgradeType.Apollo_Reactor, new int[] {200});
    fields.get("gasPriceFactor").set(UpgradeType.Apollo_Reactor, 0);
    fields.get("upgradeTimes").set(UpgradeType.Apollo_Reactor, new int[] {2500});
    fields.get("upgradeTimeFactor").set(UpgradeType.Apollo_Reactor, 0);
    fields.get("maxRepeats").set(UpgradeType.Apollo_Reactor, 1);
    fields.get("whatUpgrades").set(UpgradeType.Apollo_Reactor, UnitType.Terran_Control_Tower);
    fields.get("whatsRequired").set(UpgradeType.Apollo_Reactor, new UnitType[] {UnitType.None});
    fields
        .get("whatUses")
        .set(UpgradeType.Apollo_Reactor, new ArrayList(Arrays.asList(UnitType.Terran_Wraith)));
  }

  private static void initializeUpgradeType_Colossus_Reactor() throws Exception {
    Class<?> c = UpgradeType.class;
    Map<String, Field> fields =
        Stream.of(c.getDeclaredFields())
            .collect(
                Collectors.toMap(
                    f -> f.getName(),
                    f -> {
                      f.setAccessible(true);
                      return f;
                    }));
    fields.get("iD").set(UpgradeType.Colossus_Reactor, 23);
    fields.get("race").set(UpgradeType.Colossus_Reactor, Race.Terran);
    fields.get("mineralPrices").set(UpgradeType.Colossus_Reactor, new int[] {150});
    fields.get("mineralPriceFactor").set(UpgradeType.Colossus_Reactor, 0);
    fields.get("gasPrices").set(UpgradeType.Colossus_Reactor, new int[] {150});
    fields.get("gasPriceFactor").set(UpgradeType.Colossus_Reactor, 0);
    fields.get("upgradeTimes").set(UpgradeType.Colossus_Reactor, new int[] {2500});
    fields.get("upgradeTimeFactor").set(UpgradeType.Colossus_Reactor, 0);
    fields.get("maxRepeats").set(UpgradeType.Colossus_Reactor, 1);
    fields.get("whatUpgrades").set(UpgradeType.Colossus_Reactor, UnitType.Terran_Physics_Lab);
    fields.get("whatsRequired").set(UpgradeType.Colossus_Reactor, new UnitType[] {UnitType.None});
    fields
        .get("whatUses")
        .set(
            UpgradeType.Colossus_Reactor,
            new ArrayList(Arrays.asList(UnitType.Terran_Battlecruiser)));
  }

  private static void initializeUpgradeType_Ventral_Sacs() throws Exception {
    Class<?> c = UpgradeType.class;
    Map<String, Field> fields =
        Stream.of(c.getDeclaredFields())
            .collect(
                Collectors.toMap(
                    f -> f.getName(),
                    f -> {
                      f.setAccessible(true);
                      return f;
                    }));
    fields.get("iD").set(UpgradeType.Ventral_Sacs, 24);
    fields.get("race").set(UpgradeType.Ventral_Sacs, Race.Zerg);
    fields.get("mineralPrices").set(UpgradeType.Ventral_Sacs, new int[] {200});
    fields.get("mineralPriceFactor").set(UpgradeType.Ventral_Sacs, 0);
    fields.get("gasPrices").set(UpgradeType.Ventral_Sacs, new int[] {200});
    fields.get("gasPriceFactor").set(UpgradeType.Ventral_Sacs, 0);
    fields.get("upgradeTimes").set(UpgradeType.Ventral_Sacs, new int[] {2400});
    fields.get("upgradeTimeFactor").set(UpgradeType.Ventral_Sacs, 0);
    fields.get("maxRepeats").set(UpgradeType.Ventral_Sacs, 1);
    fields.get("whatUpgrades").set(UpgradeType.Ventral_Sacs, UnitType.Zerg_Lair);
    fields.get("whatsRequired").set(UpgradeType.Ventral_Sacs, new UnitType[] {UnitType.None});
    fields
        .get("whatUses")
        .set(UpgradeType.Ventral_Sacs, new ArrayList(Arrays.asList(UnitType.Zerg_Overlord)));
  }

  private static void initializeUpgradeType_Antennae() throws Exception {
    Class<?> c = UpgradeType.class;
    Map<String, Field> fields =
        Stream.of(c.getDeclaredFields())
            .collect(
                Collectors.toMap(
                    f -> f.getName(),
                    f -> {
                      f.setAccessible(true);
                      return f;
                    }));
    fields.get("iD").set(UpgradeType.Antennae, 25);
    fields.get("race").set(UpgradeType.Antennae, Race.Zerg);
    fields.get("mineralPrices").set(UpgradeType.Antennae, new int[] {150});
    fields.get("mineralPriceFactor").set(UpgradeType.Antennae, 0);
    fields.get("gasPrices").set(UpgradeType.Antennae, new int[] {150});
    fields.get("gasPriceFactor").set(UpgradeType.Antennae, 0);
    fields.get("upgradeTimes").set(UpgradeType.Antennae, new int[] {2000});
    fields.get("upgradeTimeFactor").set(UpgradeType.Antennae, 0);
    fields.get("maxRepeats").set(UpgradeType.Antennae, 1);
    fields.get("whatUpgrades").set(UpgradeType.Antennae, UnitType.Zerg_Lair);
    fields.get("whatsRequired").set(UpgradeType.Antennae, new UnitType[] {UnitType.None});
    fields
        .get("whatUses")
        .set(UpgradeType.Antennae, new ArrayList(Arrays.asList(UnitType.Zerg_Overlord)));
  }

  private static void initializeUpgradeType_Pneumatized_Carapace() throws Exception {
    Class<?> c = UpgradeType.class;
    Map<String, Field> fields =
        Stream.of(c.getDeclaredFields())
            .collect(
                Collectors.toMap(
                    f -> f.getName(),
                    f -> {
                      f.setAccessible(true);
                      return f;
                    }));
    fields.get("iD").set(UpgradeType.Pneumatized_Carapace, 26);
    fields.get("race").set(UpgradeType.Pneumatized_Carapace, Race.Zerg);
    fields.get("mineralPrices").set(UpgradeType.Pneumatized_Carapace, new int[] {150});
    fields.get("mineralPriceFactor").set(UpgradeType.Pneumatized_Carapace, 0);
    fields.get("gasPrices").set(UpgradeType.Pneumatized_Carapace, new int[] {150});
    fields.get("gasPriceFactor").set(UpgradeType.Pneumatized_Carapace, 0);
    fields.get("upgradeTimes").set(UpgradeType.Pneumatized_Carapace, new int[] {2000});
    fields.get("upgradeTimeFactor").set(UpgradeType.Pneumatized_Carapace, 0);
    fields.get("maxRepeats").set(UpgradeType.Pneumatized_Carapace, 1);
    fields.get("whatUpgrades").set(UpgradeType.Pneumatized_Carapace, UnitType.Zerg_Lair);
    fields
        .get("whatsRequired")
        .set(UpgradeType.Pneumatized_Carapace, new UnitType[] {UnitType.None});
    fields
        .get("whatUses")
        .set(
            UpgradeType.Pneumatized_Carapace, new ArrayList(Arrays.asList(UnitType.Zerg_Overlord)));
  }

  private static void initializeUpgradeType_Metabolic_Boost() throws Exception {
    Class<?> c = UpgradeType.class;
    Map<String, Field> fields =
        Stream.of(c.getDeclaredFields())
            .collect(
                Collectors.toMap(
                    f -> f.getName(),
                    f -> {
                      f.setAccessible(true);
                      return f;
                    }));
    fields.get("iD").set(UpgradeType.Metabolic_Boost, 27);
    fields.get("race").set(UpgradeType.Metabolic_Boost, Race.Zerg);
    fields.get("mineralPrices").set(UpgradeType.Metabolic_Boost, new int[] {100});
    fields.get("mineralPriceFactor").set(UpgradeType.Metabolic_Boost, 0);
    fields.get("gasPrices").set(UpgradeType.Metabolic_Boost, new int[] {100});
    fields.get("gasPriceFactor").set(UpgradeType.Metabolic_Boost, 0);
    fields.get("upgradeTimes").set(UpgradeType.Metabolic_Boost, new int[] {1500});
    fields.get("upgradeTimeFactor").set(UpgradeType.Metabolic_Boost, 0);
    fields.get("maxRepeats").set(UpgradeType.Metabolic_Boost, 1);
    fields.get("whatUpgrades").set(UpgradeType.Metabolic_Boost, UnitType.Zerg_Spawning_Pool);
    fields.get("whatsRequired").set(UpgradeType.Metabolic_Boost, new UnitType[] {UnitType.None});
    fields
        .get("whatUses")
        .set(UpgradeType.Metabolic_Boost, new ArrayList(Arrays.asList(UnitType.Zerg_Zergling)));
  }

  private static void initializeUpgradeType_Adrenal_Glands() throws Exception {
    Class<?> c = UpgradeType.class;
    Map<String, Field> fields =
        Stream.of(c.getDeclaredFields())
            .collect(
                Collectors.toMap(
                    f -> f.getName(),
                    f -> {
                      f.setAccessible(true);
                      return f;
                    }));
    fields.get("iD").set(UpgradeType.Adrenal_Glands, 28);
    fields.get("race").set(UpgradeType.Adrenal_Glands, Race.Zerg);
    fields.get("mineralPrices").set(UpgradeType.Adrenal_Glands, new int[] {200});
    fields.get("mineralPriceFactor").set(UpgradeType.Adrenal_Glands, 0);
    fields.get("gasPrices").set(UpgradeType.Adrenal_Glands, new int[] {200});
    fields.get("gasPriceFactor").set(UpgradeType.Adrenal_Glands, 0);
    fields.get("upgradeTimes").set(UpgradeType.Adrenal_Glands, new int[] {1500});
    fields.get("upgradeTimeFactor").set(UpgradeType.Adrenal_Glands, 0);
    fields.get("maxRepeats").set(UpgradeType.Adrenal_Glands, 1);
    fields.get("whatUpgrades").set(UpgradeType.Adrenal_Glands, UnitType.Zerg_Spawning_Pool);
    fields.get("whatsRequired").set(UpgradeType.Adrenal_Glands, new UnitType[] {UnitType.None});
    fields
        .get("whatUses")
        .set(UpgradeType.Adrenal_Glands, new ArrayList(Arrays.asList(UnitType.Zerg_Zergling)));
  }

  private static void initializeUpgradeType_Muscular_Augments() throws Exception {
    Class<?> c = UpgradeType.class;
    Map<String, Field> fields =
        Stream.of(c.getDeclaredFields())
            .collect(
                Collectors.toMap(
                    f -> f.getName(),
                    f -> {
                      f.setAccessible(true);
                      return f;
                    }));
    fields.get("iD").set(UpgradeType.Muscular_Augments, 29);
    fields.get("race").set(UpgradeType.Muscular_Augments, Race.Zerg);
    fields.get("mineralPrices").set(UpgradeType.Muscular_Augments, new int[] {150});
    fields.get("mineralPriceFactor").set(UpgradeType.Muscular_Augments, 0);
    fields.get("gasPrices").set(UpgradeType.Muscular_Augments, new int[] {150});
    fields.get("gasPriceFactor").set(UpgradeType.Muscular_Augments, 0);
    fields.get("upgradeTimes").set(UpgradeType.Muscular_Augments, new int[] {1500});
    fields.get("upgradeTimeFactor").set(UpgradeType.Muscular_Augments, 0);
    fields.get("maxRepeats").set(UpgradeType.Muscular_Augments, 1);
    fields.get("whatUpgrades").set(UpgradeType.Muscular_Augments, UnitType.Zerg_Hydralisk_Den);
    fields.get("whatsRequired").set(UpgradeType.Muscular_Augments, new UnitType[] {UnitType.None});
    fields
        .get("whatUses")
        .set(UpgradeType.Muscular_Augments, new ArrayList(Arrays.asList(UnitType.Zerg_Hydralisk)));
  }

  private static void initializeUpgradeType_Grooved_Spines() throws Exception {
    Class<?> c = UpgradeType.class;
    Map<String, Field> fields =
        Stream.of(c.getDeclaredFields())
            .collect(
                Collectors.toMap(
                    f -> f.getName(),
                    f -> {
                      f.setAccessible(true);
                      return f;
                    }));
    fields.get("iD").set(UpgradeType.Grooved_Spines, 30);
    fields.get("race").set(UpgradeType.Grooved_Spines, Race.Zerg);
    fields.get("mineralPrices").set(UpgradeType.Grooved_Spines, new int[] {150});
    fields.get("mineralPriceFactor").set(UpgradeType.Grooved_Spines, 0);
    fields.get("gasPrices").set(UpgradeType.Grooved_Spines, new int[] {150});
    fields.get("gasPriceFactor").set(UpgradeType.Grooved_Spines, 0);
    fields.get("upgradeTimes").set(UpgradeType.Grooved_Spines, new int[] {1500});
    fields.get("upgradeTimeFactor").set(UpgradeType.Grooved_Spines, 0);
    fields.get("maxRepeats").set(UpgradeType.Grooved_Spines, 1);
    fields.get("whatUpgrades").set(UpgradeType.Grooved_Spines, UnitType.Zerg_Hydralisk_Den);
    fields.get("whatsRequired").set(UpgradeType.Grooved_Spines, new UnitType[] {UnitType.None});
    fields
        .get("whatUses")
        .set(UpgradeType.Grooved_Spines, new ArrayList(Arrays.asList(UnitType.Zerg_Hydralisk)));
  }

  private static void initializeUpgradeType_Gamete_Meiosis() throws Exception {
    Class<?> c = UpgradeType.class;
    Map<String, Field> fields =
        Stream.of(c.getDeclaredFields())
            .collect(
                Collectors.toMap(
                    f -> f.getName(),
                    f -> {
                      f.setAccessible(true);
                      return f;
                    }));
    fields.get("iD").set(UpgradeType.Gamete_Meiosis, 31);
    fields.get("race").set(UpgradeType.Gamete_Meiosis, Race.Zerg);
    fields.get("mineralPrices").set(UpgradeType.Gamete_Meiosis, new int[] {150});
    fields.get("mineralPriceFactor").set(UpgradeType.Gamete_Meiosis, 0);
    fields.get("gasPrices").set(UpgradeType.Gamete_Meiosis, new int[] {150});
    fields.get("gasPriceFactor").set(UpgradeType.Gamete_Meiosis, 0);
    fields.get("upgradeTimes").set(UpgradeType.Gamete_Meiosis, new int[] {2500});
    fields.get("upgradeTimeFactor").set(UpgradeType.Gamete_Meiosis, 0);
    fields.get("maxRepeats").set(UpgradeType.Gamete_Meiosis, 1);
    fields.get("whatUpgrades").set(UpgradeType.Gamete_Meiosis, UnitType.Zerg_Queens_Nest);
    fields.get("whatsRequired").set(UpgradeType.Gamete_Meiosis, new UnitType[] {UnitType.None});
    fields
        .get("whatUses")
        .set(UpgradeType.Gamete_Meiosis, new ArrayList(Arrays.asList(UnitType.Zerg_Queen)));
  }

  private static void initializeUpgradeType_Metasynaptic_Node() throws Exception {
    Class<?> c = UpgradeType.class;
    Map<String, Field> fields =
        Stream.of(c.getDeclaredFields())
            .collect(
                Collectors.toMap(
                    f -> f.getName(),
                    f -> {
                      f.setAccessible(true);
                      return f;
                    }));
    fields.get("iD").set(UpgradeType.Metasynaptic_Node, 32);
    fields.get("race").set(UpgradeType.Metasynaptic_Node, Race.Zerg);
    fields.get("mineralPrices").set(UpgradeType.Metasynaptic_Node, new int[] {150});
    fields.get("mineralPriceFactor").set(UpgradeType.Metasynaptic_Node, 0);
    fields.get("gasPrices").set(UpgradeType.Metasynaptic_Node, new int[] {150});
    fields.get("gasPriceFactor").set(UpgradeType.Metasynaptic_Node, 0);
    fields.get("upgradeTimes").set(UpgradeType.Metasynaptic_Node, new int[] {2500});
    fields.get("upgradeTimeFactor").set(UpgradeType.Metasynaptic_Node, 0);
    fields.get("maxRepeats").set(UpgradeType.Metasynaptic_Node, 1);
    fields.get("whatUpgrades").set(UpgradeType.Metasynaptic_Node, UnitType.Zerg_Defiler_Mound);
    fields.get("whatsRequired").set(UpgradeType.Metasynaptic_Node, new UnitType[] {UnitType.None});
    fields
        .get("whatUses")
        .set(UpgradeType.Metasynaptic_Node, new ArrayList(Arrays.asList(UnitType.Zerg_Defiler)));
  }

  private static void initializeUpgradeType_Singularity_Charge() throws Exception {
    Class<?> c = UpgradeType.class;
    Map<String, Field> fields =
        Stream.of(c.getDeclaredFields())
            .collect(
                Collectors.toMap(
                    f -> f.getName(),
                    f -> {
                      f.setAccessible(true);
                      return f;
                    }));
    fields.get("iD").set(UpgradeType.Singularity_Charge, 33);
    fields.get("race").set(UpgradeType.Singularity_Charge, Race.Protoss);
    fields.get("mineralPrices").set(UpgradeType.Singularity_Charge, new int[] {150});
    fields.get("mineralPriceFactor").set(UpgradeType.Singularity_Charge, 0);
    fields.get("gasPrices").set(UpgradeType.Singularity_Charge, new int[] {150});
    fields.get("gasPriceFactor").set(UpgradeType.Singularity_Charge, 0);
    fields.get("upgradeTimes").set(UpgradeType.Singularity_Charge, new int[] {2500});
    fields.get("upgradeTimeFactor").set(UpgradeType.Singularity_Charge, 0);
    fields.get("maxRepeats").set(UpgradeType.Singularity_Charge, 1);
    fields
        .get("whatUpgrades")
        .set(UpgradeType.Singularity_Charge, UnitType.Protoss_Cybernetics_Core);
    fields.get("whatsRequired").set(UpgradeType.Singularity_Charge, new UnitType[] {UnitType.None});
    fields
        .get("whatUses")
        .set(
            UpgradeType.Singularity_Charge, new ArrayList(Arrays.asList(UnitType.Protoss_Dragoon)));
  }

  private static void initializeUpgradeType_Leg_Enhancements() throws Exception {
    Class<?> c = UpgradeType.class;
    Map<String, Field> fields =
        Stream.of(c.getDeclaredFields())
            .collect(
                Collectors.toMap(
                    f -> f.getName(),
                    f -> {
                      f.setAccessible(true);
                      return f;
                    }));
    fields.get("iD").set(UpgradeType.Leg_Enhancements, 34);
    fields.get("race").set(UpgradeType.Leg_Enhancements, Race.Protoss);
    fields.get("mineralPrices").set(UpgradeType.Leg_Enhancements, new int[] {150});
    fields.get("mineralPriceFactor").set(UpgradeType.Leg_Enhancements, 0);
    fields.get("gasPrices").set(UpgradeType.Leg_Enhancements, new int[] {150});
    fields.get("gasPriceFactor").set(UpgradeType.Leg_Enhancements, 0);
    fields.get("upgradeTimes").set(UpgradeType.Leg_Enhancements, new int[] {2000});
    fields.get("upgradeTimeFactor").set(UpgradeType.Leg_Enhancements, 0);
    fields.get("maxRepeats").set(UpgradeType.Leg_Enhancements, 1);
    fields.get("whatUpgrades").set(UpgradeType.Leg_Enhancements, UnitType.Protoss_Citadel_of_Adun);
    fields.get("whatsRequired").set(UpgradeType.Leg_Enhancements, new UnitType[] {UnitType.None});
    fields
        .get("whatUses")
        .set(UpgradeType.Leg_Enhancements, new ArrayList(Arrays.asList(UnitType.Protoss_Zealot)));
  }

  private static void initializeUpgradeType_Scarab_Damage() throws Exception {
    Class<?> c = UpgradeType.class;
    Map<String, Field> fields =
        Stream.of(c.getDeclaredFields())
            .collect(
                Collectors.toMap(
                    f -> f.getName(),
                    f -> {
                      f.setAccessible(true);
                      return f;
                    }));
    fields.get("iD").set(UpgradeType.Scarab_Damage, 35);
    fields.get("race").set(UpgradeType.Scarab_Damage, Race.Protoss);
    fields.get("mineralPrices").set(UpgradeType.Scarab_Damage, new int[] {200});
    fields.get("mineralPriceFactor").set(UpgradeType.Scarab_Damage, 0);
    fields.get("gasPrices").set(UpgradeType.Scarab_Damage, new int[] {200});
    fields.get("gasPriceFactor").set(UpgradeType.Scarab_Damage, 0);
    fields.get("upgradeTimes").set(UpgradeType.Scarab_Damage, new int[] {2500});
    fields.get("upgradeTimeFactor").set(UpgradeType.Scarab_Damage, 0);
    fields.get("maxRepeats").set(UpgradeType.Scarab_Damage, 1);
    fields
        .get("whatUpgrades")
        .set(UpgradeType.Scarab_Damage, UnitType.Protoss_Robotics_Support_Bay);
    fields.get("whatsRequired").set(UpgradeType.Scarab_Damage, new UnitType[] {UnitType.None});
    fields
        .get("whatUses")
        .set(UpgradeType.Scarab_Damage, new ArrayList(Arrays.asList(UnitType.Protoss_Reaver)));
  }

  private static void initializeUpgradeType_Reaver_Capacity() throws Exception {
    Class<?> c = UpgradeType.class;
    Map<String, Field> fields =
        Stream.of(c.getDeclaredFields())
            .collect(
                Collectors.toMap(
                    f -> f.getName(),
                    f -> {
                      f.setAccessible(true);
                      return f;
                    }));
    fields.get("iD").set(UpgradeType.Reaver_Capacity, 36);
    fields.get("race").set(UpgradeType.Reaver_Capacity, Race.Protoss);
    fields.get("mineralPrices").set(UpgradeType.Reaver_Capacity, new int[] {200});
    fields.get("mineralPriceFactor").set(UpgradeType.Reaver_Capacity, 0);
    fields.get("gasPrices").set(UpgradeType.Reaver_Capacity, new int[] {200});
    fields.get("gasPriceFactor").set(UpgradeType.Reaver_Capacity, 0);
    fields.get("upgradeTimes").set(UpgradeType.Reaver_Capacity, new int[] {2500});
    fields.get("upgradeTimeFactor").set(UpgradeType.Reaver_Capacity, 0);
    fields.get("maxRepeats").set(UpgradeType.Reaver_Capacity, 1);
    fields
        .get("whatUpgrades")
        .set(UpgradeType.Reaver_Capacity, UnitType.Protoss_Robotics_Support_Bay);
    fields.get("whatsRequired").set(UpgradeType.Reaver_Capacity, new UnitType[] {UnitType.None});
    fields
        .get("whatUses")
        .set(UpgradeType.Reaver_Capacity, new ArrayList(Arrays.asList(UnitType.Protoss_Reaver)));
  }

  private static void initializeUpgradeType_Gravitic_Drive() throws Exception {
    Class<?> c = UpgradeType.class;
    Map<String, Field> fields =
        Stream.of(c.getDeclaredFields())
            .collect(
                Collectors.toMap(
                    f -> f.getName(),
                    f -> {
                      f.setAccessible(true);
                      return f;
                    }));
    fields.get("iD").set(UpgradeType.Gravitic_Drive, 37);
    fields.get("race").set(UpgradeType.Gravitic_Drive, Race.Protoss);
    fields.get("mineralPrices").set(UpgradeType.Gravitic_Drive, new int[] {200});
    fields.get("mineralPriceFactor").set(UpgradeType.Gravitic_Drive, 0);
    fields.get("gasPrices").set(UpgradeType.Gravitic_Drive, new int[] {200});
    fields.get("gasPriceFactor").set(UpgradeType.Gravitic_Drive, 0);
    fields.get("upgradeTimes").set(UpgradeType.Gravitic_Drive, new int[] {2500});
    fields.get("upgradeTimeFactor").set(UpgradeType.Gravitic_Drive, 0);
    fields.get("maxRepeats").set(UpgradeType.Gravitic_Drive, 1);
    fields
        .get("whatUpgrades")
        .set(UpgradeType.Gravitic_Drive, UnitType.Protoss_Robotics_Support_Bay);
    fields.get("whatsRequired").set(UpgradeType.Gravitic_Drive, new UnitType[] {UnitType.None});
    fields
        .get("whatUses")
        .set(UpgradeType.Gravitic_Drive, new ArrayList(Arrays.asList(UnitType.Protoss_Shuttle)));
  }

  private static void initializeUpgradeType_Sensor_Array() throws Exception {
    Class<?> c = UpgradeType.class;
    Map<String, Field> fields =
        Stream.of(c.getDeclaredFields())
            .collect(
                Collectors.toMap(
                    f -> f.getName(),
                    f -> {
                      f.setAccessible(true);
                      return f;
                    }));
    fields.get("iD").set(UpgradeType.Sensor_Array, 38);
    fields.get("race").set(UpgradeType.Sensor_Array, Race.Protoss);
    fields.get("mineralPrices").set(UpgradeType.Sensor_Array, new int[] {150});
    fields.get("mineralPriceFactor").set(UpgradeType.Sensor_Array, 0);
    fields.get("gasPrices").set(UpgradeType.Sensor_Array, new int[] {150});
    fields.get("gasPriceFactor").set(UpgradeType.Sensor_Array, 0);
    fields.get("upgradeTimes").set(UpgradeType.Sensor_Array, new int[] {2000});
    fields.get("upgradeTimeFactor").set(UpgradeType.Sensor_Array, 0);
    fields.get("maxRepeats").set(UpgradeType.Sensor_Array, 1);
    fields.get("whatUpgrades").set(UpgradeType.Sensor_Array, UnitType.Protoss_Observatory);
    fields.get("whatsRequired").set(UpgradeType.Sensor_Array, new UnitType[] {UnitType.None});
    fields
        .get("whatUses")
        .set(UpgradeType.Sensor_Array, new ArrayList(Arrays.asList(UnitType.Protoss_Observer)));
  }

  private static void initializeUpgradeType_Gravitic_Boosters() throws Exception {
    Class<?> c = UpgradeType.class;
    Map<String, Field> fields =
        Stream.of(c.getDeclaredFields())
            .collect(
                Collectors.toMap(
                    f -> f.getName(),
                    f -> {
                      f.setAccessible(true);
                      return f;
                    }));
    fields.get("iD").set(UpgradeType.Gravitic_Boosters, 39);
    fields.get("race").set(UpgradeType.Gravitic_Boosters, Race.Protoss);
    fields.get("mineralPrices").set(UpgradeType.Gravitic_Boosters, new int[] {150});
    fields.get("mineralPriceFactor").set(UpgradeType.Gravitic_Boosters, 0);
    fields.get("gasPrices").set(UpgradeType.Gravitic_Boosters, new int[] {150});
    fields.get("gasPriceFactor").set(UpgradeType.Gravitic_Boosters, 0);
    fields.get("upgradeTimes").set(UpgradeType.Gravitic_Boosters, new int[] {2000});
    fields.get("upgradeTimeFactor").set(UpgradeType.Gravitic_Boosters, 0);
    fields.get("maxRepeats").set(UpgradeType.Gravitic_Boosters, 1);
    fields.get("whatUpgrades").set(UpgradeType.Gravitic_Boosters, UnitType.Protoss_Observatory);
    fields.get("whatsRequired").set(UpgradeType.Gravitic_Boosters, new UnitType[] {UnitType.None});
    fields
        .get("whatUses")
        .set(
            UpgradeType.Gravitic_Boosters, new ArrayList(Arrays.asList(UnitType.Protoss_Observer)));
  }

  private static void initializeUpgradeType_Khaydarin_Amulet() throws Exception {
    Class<?> c = UpgradeType.class;
    Map<String, Field> fields =
        Stream.of(c.getDeclaredFields())
            .collect(
                Collectors.toMap(
                    f -> f.getName(),
                    f -> {
                      f.setAccessible(true);
                      return f;
                    }));
    fields.get("iD").set(UpgradeType.Khaydarin_Amulet, 40);
    fields.get("race").set(UpgradeType.Khaydarin_Amulet, Race.Protoss);
    fields.get("mineralPrices").set(UpgradeType.Khaydarin_Amulet, new int[] {150});
    fields.get("mineralPriceFactor").set(UpgradeType.Khaydarin_Amulet, 0);
    fields.get("gasPrices").set(UpgradeType.Khaydarin_Amulet, new int[] {150});
    fields.get("gasPriceFactor").set(UpgradeType.Khaydarin_Amulet, 0);
    fields.get("upgradeTimes").set(UpgradeType.Khaydarin_Amulet, new int[] {2500});
    fields.get("upgradeTimeFactor").set(UpgradeType.Khaydarin_Amulet, 0);
    fields.get("maxRepeats").set(UpgradeType.Khaydarin_Amulet, 1);
    fields.get("whatUpgrades").set(UpgradeType.Khaydarin_Amulet, UnitType.Protoss_Templar_Archives);
    fields.get("whatsRequired").set(UpgradeType.Khaydarin_Amulet, new UnitType[] {UnitType.None});
    fields
        .get("whatUses")
        .set(
            UpgradeType.Khaydarin_Amulet,
            new ArrayList(Arrays.asList(UnitType.Protoss_High_Templar)));
  }

  private static void initializeUpgradeType_Apial_Sensors() throws Exception {
    Class<?> c = UpgradeType.class;
    Map<String, Field> fields =
        Stream.of(c.getDeclaredFields())
            .collect(
                Collectors.toMap(
                    f -> f.getName(),
                    f -> {
                      f.setAccessible(true);
                      return f;
                    }));
    fields.get("iD").set(UpgradeType.Apial_Sensors, 41);
    fields.get("race").set(UpgradeType.Apial_Sensors, Race.Protoss);
    fields.get("mineralPrices").set(UpgradeType.Apial_Sensors, new int[] {100});
    fields.get("mineralPriceFactor").set(UpgradeType.Apial_Sensors, 0);
    fields.get("gasPrices").set(UpgradeType.Apial_Sensors, new int[] {100});
    fields.get("gasPriceFactor").set(UpgradeType.Apial_Sensors, 0);
    fields.get("upgradeTimes").set(UpgradeType.Apial_Sensors, new int[] {2500});
    fields.get("upgradeTimeFactor").set(UpgradeType.Apial_Sensors, 0);
    fields.get("maxRepeats").set(UpgradeType.Apial_Sensors, 1);
    fields.get("whatUpgrades").set(UpgradeType.Apial_Sensors, UnitType.Protoss_Fleet_Beacon);
    fields.get("whatsRequired").set(UpgradeType.Apial_Sensors, new UnitType[] {UnitType.None});
    fields
        .get("whatUses")
        .set(UpgradeType.Apial_Sensors, new ArrayList(Arrays.asList(UnitType.Protoss_Scout)));
  }

  private static void initializeUpgradeType_Gravitic_Thrusters() throws Exception {
    Class<?> c = UpgradeType.class;
    Map<String, Field> fields =
        Stream.of(c.getDeclaredFields())
            .collect(
                Collectors.toMap(
                    f -> f.getName(),
                    f -> {
                      f.setAccessible(true);
                      return f;
                    }));
    fields.get("iD").set(UpgradeType.Gravitic_Thrusters, 42);
    fields.get("race").set(UpgradeType.Gravitic_Thrusters, Race.Protoss);
    fields.get("mineralPrices").set(UpgradeType.Gravitic_Thrusters, new int[] {200});
    fields.get("mineralPriceFactor").set(UpgradeType.Gravitic_Thrusters, 0);
    fields.get("gasPrices").set(UpgradeType.Gravitic_Thrusters, new int[] {200});
    fields.get("gasPriceFactor").set(UpgradeType.Gravitic_Thrusters, 0);
    fields.get("upgradeTimes").set(UpgradeType.Gravitic_Thrusters, new int[] {2500});
    fields.get("upgradeTimeFactor").set(UpgradeType.Gravitic_Thrusters, 0);
    fields.get("maxRepeats").set(UpgradeType.Gravitic_Thrusters, 1);
    fields.get("whatUpgrades").set(UpgradeType.Gravitic_Thrusters, UnitType.Protoss_Fleet_Beacon);
    fields.get("whatsRequired").set(UpgradeType.Gravitic_Thrusters, new UnitType[] {UnitType.None});
    fields
        .get("whatUses")
        .set(UpgradeType.Gravitic_Thrusters, new ArrayList(Arrays.asList(UnitType.Protoss_Scout)));
  }

  private static void initializeUpgradeType_Carrier_Capacity() throws Exception {
    Class<?> c = UpgradeType.class;
    Map<String, Field> fields =
        Stream.of(c.getDeclaredFields())
            .collect(
                Collectors.toMap(
                    f -> f.getName(),
                    f -> {
                      f.setAccessible(true);
                      return f;
                    }));
    fields.get("iD").set(UpgradeType.Carrier_Capacity, 43);
    fields.get("race").set(UpgradeType.Carrier_Capacity, Race.Protoss);
    fields.get("mineralPrices").set(UpgradeType.Carrier_Capacity, new int[] {100});
    fields.get("mineralPriceFactor").set(UpgradeType.Carrier_Capacity, 0);
    fields.get("gasPrices").set(UpgradeType.Carrier_Capacity, new int[] {100});
    fields.get("gasPriceFactor").set(UpgradeType.Carrier_Capacity, 0);
    fields.get("upgradeTimes").set(UpgradeType.Carrier_Capacity, new int[] {1500});
    fields.get("upgradeTimeFactor").set(UpgradeType.Carrier_Capacity, 0);
    fields.get("maxRepeats").set(UpgradeType.Carrier_Capacity, 1);
    fields.get("whatUpgrades").set(UpgradeType.Carrier_Capacity, UnitType.Protoss_Fleet_Beacon);
    fields.get("whatsRequired").set(UpgradeType.Carrier_Capacity, new UnitType[] {UnitType.None});
    fields
        .get("whatUses")
        .set(UpgradeType.Carrier_Capacity, new ArrayList(Arrays.asList(UnitType.Protoss_Carrier)));
  }

  private static void initializeUpgradeType_Khaydarin_Core() throws Exception {
    Class<?> c = UpgradeType.class;
    Map<String, Field> fields =
        Stream.of(c.getDeclaredFields())
            .collect(
                Collectors.toMap(
                    f -> f.getName(),
                    f -> {
                      f.setAccessible(true);
                      return f;
                    }));
    fields.get("iD").set(UpgradeType.Khaydarin_Core, 44);
    fields.get("race").set(UpgradeType.Khaydarin_Core, Race.Protoss);
    fields.get("mineralPrices").set(UpgradeType.Khaydarin_Core, new int[] {150});
    fields.get("mineralPriceFactor").set(UpgradeType.Khaydarin_Core, 0);
    fields.get("gasPrices").set(UpgradeType.Khaydarin_Core, new int[] {150});
    fields.get("gasPriceFactor").set(UpgradeType.Khaydarin_Core, 0);
    fields.get("upgradeTimes").set(UpgradeType.Khaydarin_Core, new int[] {2500});
    fields.get("upgradeTimeFactor").set(UpgradeType.Khaydarin_Core, 0);
    fields.get("maxRepeats").set(UpgradeType.Khaydarin_Core, 1);
    fields.get("whatUpgrades").set(UpgradeType.Khaydarin_Core, UnitType.Protoss_Arbiter_Tribunal);
    fields.get("whatsRequired").set(UpgradeType.Khaydarin_Core, new UnitType[] {UnitType.None});
    fields
        .get("whatUses")
        .set(UpgradeType.Khaydarin_Core, new ArrayList(Arrays.asList(UnitType.Protoss_Arbiter)));
  }

  private static void initializeUpgradeType_Argus_Jewel() throws Exception {
    Class<?> c = UpgradeType.class;
    Map<String, Field> fields =
        Stream.of(c.getDeclaredFields())
            .collect(
                Collectors.toMap(
                    f -> f.getName(),
                    f -> {
                      f.setAccessible(true);
                      return f;
                    }));
    fields.get("iD").set(UpgradeType.Argus_Jewel, 47);
    fields.get("race").set(UpgradeType.Argus_Jewel, Race.Protoss);
    fields.get("mineralPrices").set(UpgradeType.Argus_Jewel, new int[] {100});
    fields.get("mineralPriceFactor").set(UpgradeType.Argus_Jewel, 0);
    fields.get("gasPrices").set(UpgradeType.Argus_Jewel, new int[] {100});
    fields.get("gasPriceFactor").set(UpgradeType.Argus_Jewel, 0);
    fields.get("upgradeTimes").set(UpgradeType.Argus_Jewel, new int[] {2500});
    fields.get("upgradeTimeFactor").set(UpgradeType.Argus_Jewel, 0);
    fields.get("maxRepeats").set(UpgradeType.Argus_Jewel, 1);
    fields.get("whatUpgrades").set(UpgradeType.Argus_Jewel, UnitType.Protoss_Fleet_Beacon);
    fields.get("whatsRequired").set(UpgradeType.Argus_Jewel, new UnitType[] {UnitType.None});
    fields
        .get("whatUses")
        .set(UpgradeType.Argus_Jewel, new ArrayList(Arrays.asList(UnitType.Protoss_Corsair)));
  }

  private static void initializeUpgradeType_Argus_Talisman() throws Exception {
    Class<?> c = UpgradeType.class;
    Map<String, Field> fields =
        Stream.of(c.getDeclaredFields())
            .collect(
                Collectors.toMap(
                    f -> f.getName(),
                    f -> {
                      f.setAccessible(true);
                      return f;
                    }));
    fields.get("iD").set(UpgradeType.Argus_Talisman, 49);
    fields.get("race").set(UpgradeType.Argus_Talisman, Race.Protoss);
    fields.get("mineralPrices").set(UpgradeType.Argus_Talisman, new int[] {150});
    fields.get("mineralPriceFactor").set(UpgradeType.Argus_Talisman, 0);
    fields.get("gasPrices").set(UpgradeType.Argus_Talisman, new int[] {150});
    fields.get("gasPriceFactor").set(UpgradeType.Argus_Talisman, 0);
    fields.get("upgradeTimes").set(UpgradeType.Argus_Talisman, new int[] {2500});
    fields.get("upgradeTimeFactor").set(UpgradeType.Argus_Talisman, 0);
    fields.get("maxRepeats").set(UpgradeType.Argus_Talisman, 1);
    fields.get("whatUpgrades").set(UpgradeType.Argus_Talisman, UnitType.Protoss_Templar_Archives);
    fields.get("whatsRequired").set(UpgradeType.Argus_Talisman, new UnitType[] {UnitType.None});
    fields
        .get("whatUses")
        .set(
            UpgradeType.Argus_Talisman, new ArrayList(Arrays.asList(UnitType.Protoss_Dark_Archon)));
  }

  private static void initializeUpgradeType_Caduceus_Reactor() throws Exception {
    Class<?> c = UpgradeType.class;
    Map<String, Field> fields =
        Stream.of(c.getDeclaredFields())
            .collect(
                Collectors.toMap(
                    f -> f.getName(),
                    f -> {
                      f.setAccessible(true);
                      return f;
                    }));
    fields.get("iD").set(UpgradeType.Caduceus_Reactor, 51);
    fields.get("race").set(UpgradeType.Caduceus_Reactor, Race.Terran);
    fields.get("mineralPrices").set(UpgradeType.Caduceus_Reactor, new int[] {150});
    fields.get("mineralPriceFactor").set(UpgradeType.Caduceus_Reactor, 0);
    fields.get("gasPrices").set(UpgradeType.Caduceus_Reactor, new int[] {150});
    fields.get("gasPriceFactor").set(UpgradeType.Caduceus_Reactor, 0);
    fields.get("upgradeTimes").set(UpgradeType.Caduceus_Reactor, new int[] {2500});
    fields.get("upgradeTimeFactor").set(UpgradeType.Caduceus_Reactor, 0);
    fields.get("maxRepeats").set(UpgradeType.Caduceus_Reactor, 1);
    fields.get("whatUpgrades").set(UpgradeType.Caduceus_Reactor, UnitType.Terran_Academy);
    fields.get("whatsRequired").set(UpgradeType.Caduceus_Reactor, new UnitType[] {UnitType.None});
    fields
        .get("whatUses")
        .set(UpgradeType.Caduceus_Reactor, new ArrayList(Arrays.asList(UnitType.Terran_Medic)));
  }

  private static void initializeUpgradeType_Chitinous_Plating() throws Exception {
    Class<?> c = UpgradeType.class;
    Map<String, Field> fields =
        Stream.of(c.getDeclaredFields())
            .collect(
                Collectors.toMap(
                    f -> f.getName(),
                    f -> {
                      f.setAccessible(true);
                      return f;
                    }));
    fields.get("iD").set(UpgradeType.Chitinous_Plating, 52);
    fields.get("race").set(UpgradeType.Chitinous_Plating, Race.Zerg);
    fields.get("mineralPrices").set(UpgradeType.Chitinous_Plating, new int[] {150});
    fields.get("mineralPriceFactor").set(UpgradeType.Chitinous_Plating, 0);
    fields.get("gasPrices").set(UpgradeType.Chitinous_Plating, new int[] {150});
    fields.get("gasPriceFactor").set(UpgradeType.Chitinous_Plating, 0);
    fields.get("upgradeTimes").set(UpgradeType.Chitinous_Plating, new int[] {2000});
    fields.get("upgradeTimeFactor").set(UpgradeType.Chitinous_Plating, 0);
    fields.get("maxRepeats").set(UpgradeType.Chitinous_Plating, 1);
    fields.get("whatUpgrades").set(UpgradeType.Chitinous_Plating, UnitType.Zerg_Ultralisk_Cavern);
    fields.get("whatsRequired").set(UpgradeType.Chitinous_Plating, new UnitType[] {UnitType.None});
    fields
        .get("whatUses")
        .set(UpgradeType.Chitinous_Plating, new ArrayList(Arrays.asList(UnitType.Zerg_Ultralisk)));
  }

  private static void initializeUpgradeType_Anabolic_Synthesis() throws Exception {
    Class<?> c = UpgradeType.class;
    Map<String, Field> fields =
        Stream.of(c.getDeclaredFields())
            .collect(
                Collectors.toMap(
                    f -> f.getName(),
                    f -> {
                      f.setAccessible(true);
                      return f;
                    }));
    fields.get("iD").set(UpgradeType.Anabolic_Synthesis, 53);
    fields.get("race").set(UpgradeType.Anabolic_Synthesis, Race.Zerg);
    fields.get("mineralPrices").set(UpgradeType.Anabolic_Synthesis, new int[] {200});
    fields.get("mineralPriceFactor").set(UpgradeType.Anabolic_Synthesis, 0);
    fields.get("gasPrices").set(UpgradeType.Anabolic_Synthesis, new int[] {200});
    fields.get("gasPriceFactor").set(UpgradeType.Anabolic_Synthesis, 0);
    fields.get("upgradeTimes").set(UpgradeType.Anabolic_Synthesis, new int[] {2000});
    fields.get("upgradeTimeFactor").set(UpgradeType.Anabolic_Synthesis, 0);
    fields.get("maxRepeats").set(UpgradeType.Anabolic_Synthesis, 1);
    fields.get("whatUpgrades").set(UpgradeType.Anabolic_Synthesis, UnitType.Zerg_Ultralisk_Cavern);
    fields.get("whatsRequired").set(UpgradeType.Anabolic_Synthesis, new UnitType[] {UnitType.None});
    fields
        .get("whatUses")
        .set(UpgradeType.Anabolic_Synthesis, new ArrayList(Arrays.asList(UnitType.Zerg_Ultralisk)));
  }

  private static void initializeUpgradeType_Charon_Boosters() throws Exception {
    Class<?> c = UpgradeType.class;
    Map<String, Field> fields =
        Stream.of(c.getDeclaredFields())
            .collect(
                Collectors.toMap(
                    f -> f.getName(),
                    f -> {
                      f.setAccessible(true);
                      return f;
                    }));
    fields.get("iD").set(UpgradeType.Charon_Boosters, 54);
    fields.get("race").set(UpgradeType.Charon_Boosters, Race.Terran);
    fields.get("mineralPrices").set(UpgradeType.Charon_Boosters, new int[] {100});
    fields.get("mineralPriceFactor").set(UpgradeType.Charon_Boosters, 0);
    fields.get("gasPrices").set(UpgradeType.Charon_Boosters, new int[] {100});
    fields.get("gasPriceFactor").set(UpgradeType.Charon_Boosters, 0);
    fields.get("upgradeTimes").set(UpgradeType.Charon_Boosters, new int[] {2000});
    fields.get("upgradeTimeFactor").set(UpgradeType.Charon_Boosters, 0);
    fields.get("maxRepeats").set(UpgradeType.Charon_Boosters, 1);
    fields.get("whatUpgrades").set(UpgradeType.Charon_Boosters, UnitType.Terran_Machine_Shop);
    fields.get("whatsRequired").set(UpgradeType.Charon_Boosters, new UnitType[] {UnitType.None});
    fields
        .get("whatUses")
        .set(UpgradeType.Charon_Boosters, new ArrayList(Arrays.asList(UnitType.Terran_Goliath)));
  }

  private static void initializeUpgradeType_Upgrade_60() throws Exception {
    Class<?> c = UpgradeType.class;
    Map<String, Field> fields =
        Stream.of(c.getDeclaredFields())
            .collect(
                Collectors.toMap(
                    f -> f.getName(),
                    f -> {
                      f.setAccessible(true);
                      return f;
                    }));
    fields.get("iD").set(UpgradeType.Upgrade_60, 60);
    fields.get("race").set(UpgradeType.Upgrade_60, null);
    fields.get("mineralPrices").set(UpgradeType.Upgrade_60, null);
    fields.get("mineralPriceFactor").set(UpgradeType.Upgrade_60, 0);
    fields.get("gasPrices").set(UpgradeType.Upgrade_60, null);
    fields.get("gasPriceFactor").set(UpgradeType.Upgrade_60, 0);
    fields.get("upgradeTimes").set(UpgradeType.Upgrade_60, null);
    fields.get("upgradeTimeFactor").set(UpgradeType.Upgrade_60, 0);
    fields.get("maxRepeats").set(UpgradeType.Upgrade_60, 0);
    fields.get("whatUpgrades").set(UpgradeType.Upgrade_60, null);
    fields.get("whatsRequired").set(UpgradeType.Upgrade_60, null);
    fields.get("whatUses").set(UpgradeType.Upgrade_60, new ArrayList(Arrays.asList()));
  }

  private static void initializeUpgradeType_None() throws Exception {
    Class<?> c = UpgradeType.class;
    Map<String, Field> fields =
        Stream.of(c.getDeclaredFields())
            .collect(
                Collectors.toMap(
                    f -> f.getName(),
                    f -> {
                      f.setAccessible(true);
                      return f;
                    }));
    fields.get("iD").set(UpgradeType.None, 61);
    fields.get("race").set(UpgradeType.None, Race.None);
    fields.get("mineralPrices").set(UpgradeType.None, new int[] {});
    fields.get("mineralPriceFactor").set(UpgradeType.None, 0);
    fields.get("gasPrices").set(UpgradeType.None, new int[] {});
    fields.get("gasPriceFactor").set(UpgradeType.None, 0);
    fields.get("upgradeTimes").set(UpgradeType.None, new int[] {});
    fields.get("upgradeTimeFactor").set(UpgradeType.None, 0);
    fields.get("maxRepeats").set(UpgradeType.None, 0);
    fields.get("whatUpgrades").set(UpgradeType.None, UnitType.None);
    fields.get("whatsRequired").set(UpgradeType.None, new UnitType[] {});
    fields.get("whatUses").set(UpgradeType.None, new ArrayList(Arrays.asList()));
  }

  private static void initializeUpgradeType_Unknown() throws Exception {
    Class<?> c = UpgradeType.class;
    Map<String, Field> fields =
        Stream.of(c.getDeclaredFields())
            .collect(
                Collectors.toMap(
                    f -> f.getName(),
                    f -> {
                      f.setAccessible(true);
                      return f;
                    }));
    fields.get("iD").set(UpgradeType.Unknown, 62);
    fields.get("race").set(UpgradeType.Unknown, Race.Unknown);
    fields.get("mineralPrices").set(UpgradeType.Unknown, new int[] {});
    fields.get("mineralPriceFactor").set(UpgradeType.Unknown, 0);
    fields.get("gasPrices").set(UpgradeType.Unknown, new int[] {});
    fields.get("gasPriceFactor").set(UpgradeType.Unknown, 0);
    fields.get("upgradeTimes").set(UpgradeType.Unknown, new int[] {});
    fields.get("upgradeTimeFactor").set(UpgradeType.Unknown, 0);
    fields.get("maxRepeats").set(UpgradeType.Unknown, 0);
    fields.get("whatUpgrades").set(UpgradeType.Unknown, UnitType.None);
    fields.get("whatsRequired").set(UpgradeType.Unknown, new UnitType[] {});
    fields.get("whatUses").set(UpgradeType.Unknown, new ArrayList(Arrays.asList()));
  }

  private static void initializeUpgradeType_MAX() throws Exception {
    Class<?> c = UpgradeType.class;
    Map<String, Field> fields =
        Stream.of(c.getDeclaredFields())
            .collect(
                Collectors.toMap(
                    f -> f.getName(),
                    f -> {
                      f.setAccessible(true);
                      return f;
                    }));
    fields.get("iD").set(UpgradeType.MAX, 63);
    fields.get("race").set(UpgradeType.MAX, null);
    fields.get("mineralPrices").set(UpgradeType.MAX, null);
    fields.get("mineralPriceFactor").set(UpgradeType.MAX, 0);
    fields.get("gasPrices").set(UpgradeType.MAX, null);
    fields.get("gasPriceFactor").set(UpgradeType.MAX, 0);
    fields.get("upgradeTimes").set(UpgradeType.MAX, null);
    fields.get("upgradeTimeFactor").set(UpgradeType.MAX, 0);
    fields.get("maxRepeats").set(UpgradeType.MAX, 0);
    fields.get("whatUpgrades").set(UpgradeType.MAX, null);
    fields.get("whatsRequired").set(UpgradeType.MAX, null);
    fields.get("whatUses").set(UpgradeType.MAX, new ArrayList(Arrays.asList()));
  }

  private static Map<?, ?> toMap(Object... element) {
    Map<Object, Object> map = new HashMap<>();
    for (int i = 0; i < element.length; i += 2) {
      map.put(element[i], element[i + 1]);
    }
    return map;
  }
}
