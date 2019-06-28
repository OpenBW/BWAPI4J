package mockdata.dataexporter;

import bwapi.Order;
import bwapi.Race;
import bwapi.TechType;
import bwapi.UnitType;
import bwapi.WeaponType;
import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

class TechTypes {
  static void initializeTechType() throws Exception {
    initializeTechType_Stim_Packs();
    initializeTechType_Lockdown();
    initializeTechType_EMP_Shockwave();
    initializeTechType_Spider_Mines();
    initializeTechType_Scanner_Sweep();
    initializeTechType_Tank_Siege_Mode();
    initializeTechType_Defensive_Matrix();
    initializeTechType_Irradiate();
    initializeTechType_Yamato_Gun();
    initializeTechType_Cloaking_Field();
    initializeTechType_Personnel_Cloaking();
    initializeTechType_Burrowing();
    initializeTechType_Infestation();
    initializeTechType_Spawn_Broodlings();
    initializeTechType_Dark_Swarm();
    initializeTechType_Plague();
    initializeTechType_Consume();
    initializeTechType_Ensnare();
    initializeTechType_Parasite();
    initializeTechType_Psionic_Storm();
    initializeTechType_Hallucination();
    initializeTechType_Recall();
    initializeTechType_Stasis_Field();
    initializeTechType_Archon_Warp();
    initializeTechType_Restoration();
    initializeTechType_Disruption_Web();
    initializeTechType_Unused_26();
    initializeTechType_Mind_Control();
    initializeTechType_Dark_Archon_Meld();
    initializeTechType_Feedback();
    initializeTechType_Optical_Flare();
    initializeTechType_Maelstrom();
    initializeTechType_Lurker_Aspect();
    initializeTechType_Unused_33();
    initializeTechType_Healing();
    initializeTechType_None();
    initializeTechType_Nuclear_Strike();
    initializeTechType_Unknown();
    initializeTechType_MAX();
  }

  private static void initializeTechType_Stim_Packs() throws Exception {
    Class<?> c = TechType.class;
    Map<String, Field> fields =
        Stream.of(c.getDeclaredFields())
            .collect(
                Collectors.toMap(
                    f -> f.getName(),
                    f -> {
                      f.setAccessible(true);
                      return f;
                    }));
    fields.get("iD").set(TechType.Stim_Packs, 0);
    fields.get("race").set(TechType.Stim_Packs, Race.Terran);
    fields.get("mineralPrice").set(TechType.Stim_Packs, 100);
    fields.get("gasPrice").set(TechType.Stim_Packs, 100);
    fields.get("researchTime").set(TechType.Stim_Packs, 1200);
    fields.get("energyCost").set(TechType.Stim_Packs, 0);
    fields.get("whatResearches").set(TechType.Stim_Packs, UnitType.Terran_Academy);
    fields.get("weaponType").set(TechType.Stim_Packs, WeaponType.None);
    fields.get("targetsUnit").set(TechType.Stim_Packs, false);
    fields.get("targetsPosition").set(TechType.Stim_Packs, false);
    fields.get("order").set(TechType.Stim_Packs, Order.None);
    fields.get("requiredUnit").set(TechType.Stim_Packs, UnitType.None);
  }

  private static void initializeTechType_Lockdown() throws Exception {
    Class<?> c = TechType.class;
    Map<String, Field> fields =
        Stream.of(c.getDeclaredFields())
            .collect(
                Collectors.toMap(
                    f -> f.getName(),
                    f -> {
                      f.setAccessible(true);
                      return f;
                    }));
    fields.get("iD").set(TechType.Lockdown, 1);
    fields.get("race").set(TechType.Lockdown, Race.Terran);
    fields.get("mineralPrice").set(TechType.Lockdown, 200);
    fields.get("gasPrice").set(TechType.Lockdown, 200);
    fields.get("researchTime").set(TechType.Lockdown, 1500);
    fields.get("energyCost").set(TechType.Lockdown, 100);
    fields.get("whatResearches").set(TechType.Lockdown, UnitType.Terran_Covert_Ops);
    fields.get("weaponType").set(TechType.Lockdown, WeaponType.Lockdown);
    fields.get("targetsUnit").set(TechType.Lockdown, true);
    fields.get("targetsPosition").set(TechType.Lockdown, false);
    fields.get("order").set(TechType.Lockdown, Order.CastLockdown);
    fields.get("requiredUnit").set(TechType.Lockdown, UnitType.None);
  }

  private static void initializeTechType_EMP_Shockwave() throws Exception {
    Class<?> c = TechType.class;
    Map<String, Field> fields =
        Stream.of(c.getDeclaredFields())
            .collect(
                Collectors.toMap(
                    f -> f.getName(),
                    f -> {
                      f.setAccessible(true);
                      return f;
                    }));
    fields.get("iD").set(TechType.EMP_Shockwave, 2);
    fields.get("race").set(TechType.EMP_Shockwave, Race.Terran);
    fields.get("mineralPrice").set(TechType.EMP_Shockwave, 200);
    fields.get("gasPrice").set(TechType.EMP_Shockwave, 200);
    fields.get("researchTime").set(TechType.EMP_Shockwave, 1800);
    fields.get("energyCost").set(TechType.EMP_Shockwave, 100);
    fields.get("whatResearches").set(TechType.EMP_Shockwave, UnitType.Terran_Science_Facility);
    fields.get("weaponType").set(TechType.EMP_Shockwave, WeaponType.EMP_Shockwave);
    fields.get("targetsUnit").set(TechType.EMP_Shockwave, true);
    fields.get("targetsPosition").set(TechType.EMP_Shockwave, true);
    fields.get("order").set(TechType.EMP_Shockwave, Order.CastEMPShockwave);
    fields.get("requiredUnit").set(TechType.EMP_Shockwave, UnitType.None);
  }

  private static void initializeTechType_Spider_Mines() throws Exception {
    Class<?> c = TechType.class;
    Map<String, Field> fields =
        Stream.of(c.getDeclaredFields())
            .collect(
                Collectors.toMap(
                    f -> f.getName(),
                    f -> {
                      f.setAccessible(true);
                      return f;
                    }));
    fields.get("iD").set(TechType.Spider_Mines, 3);
    fields.get("race").set(TechType.Spider_Mines, Race.Terran);
    fields.get("mineralPrice").set(TechType.Spider_Mines, 100);
    fields.get("gasPrice").set(TechType.Spider_Mines, 100);
    fields.get("researchTime").set(TechType.Spider_Mines, 1200);
    fields.get("energyCost").set(TechType.Spider_Mines, 0);
    fields.get("whatResearches").set(TechType.Spider_Mines, UnitType.Terran_Machine_Shop);
    fields.get("weaponType").set(TechType.Spider_Mines, WeaponType.Spider_Mines);
    fields.get("targetsUnit").set(TechType.Spider_Mines, false);
    fields.get("targetsPosition").set(TechType.Spider_Mines, true);
    fields.get("order").set(TechType.Spider_Mines, Order.PlaceMine);
    fields.get("requiredUnit").set(TechType.Spider_Mines, UnitType.None);
  }

  private static void initializeTechType_Scanner_Sweep() throws Exception {
    Class<?> c = TechType.class;
    Map<String, Field> fields =
        Stream.of(c.getDeclaredFields())
            .collect(
                Collectors.toMap(
                    f -> f.getName(),
                    f -> {
                      f.setAccessible(true);
                      return f;
                    }));
    fields.get("iD").set(TechType.Scanner_Sweep, 4);
    fields.get("race").set(TechType.Scanner_Sweep, Race.Terran);
    fields.get("mineralPrice").set(TechType.Scanner_Sweep, 0);
    fields.get("gasPrice").set(TechType.Scanner_Sweep, 0);
    fields.get("researchTime").set(TechType.Scanner_Sweep, 0);
    fields.get("energyCost").set(TechType.Scanner_Sweep, 50);
    fields.get("whatResearches").set(TechType.Scanner_Sweep, UnitType.None);
    fields.get("weaponType").set(TechType.Scanner_Sweep, WeaponType.None);
    fields.get("targetsUnit").set(TechType.Scanner_Sweep, true);
    fields.get("targetsPosition").set(TechType.Scanner_Sweep, true);
    fields.get("order").set(TechType.Scanner_Sweep, Order.CastScannerSweep);
    fields.get("requiredUnit").set(TechType.Scanner_Sweep, UnitType.None);
  }

  private static void initializeTechType_Tank_Siege_Mode() throws Exception {
    Class<?> c = TechType.class;
    Map<String, Field> fields =
        Stream.of(c.getDeclaredFields())
            .collect(
                Collectors.toMap(
                    f -> f.getName(),
                    f -> {
                      f.setAccessible(true);
                      return f;
                    }));
    fields.get("iD").set(TechType.Tank_Siege_Mode, 5);
    fields.get("race").set(TechType.Tank_Siege_Mode, Race.Terran);
    fields.get("mineralPrice").set(TechType.Tank_Siege_Mode, 150);
    fields.get("gasPrice").set(TechType.Tank_Siege_Mode, 150);
    fields.get("researchTime").set(TechType.Tank_Siege_Mode, 1200);
    fields.get("energyCost").set(TechType.Tank_Siege_Mode, 0);
    fields.get("whatResearches").set(TechType.Tank_Siege_Mode, UnitType.Terran_Machine_Shop);
    fields.get("weaponType").set(TechType.Tank_Siege_Mode, WeaponType.None);
    fields.get("targetsUnit").set(TechType.Tank_Siege_Mode, false);
    fields.get("targetsPosition").set(TechType.Tank_Siege_Mode, false);
    fields.get("order").set(TechType.Tank_Siege_Mode, Order.None);
    fields.get("requiredUnit").set(TechType.Tank_Siege_Mode, UnitType.None);
  }

  private static void initializeTechType_Defensive_Matrix() throws Exception {
    Class<?> c = TechType.class;
    Map<String, Field> fields =
        Stream.of(c.getDeclaredFields())
            .collect(
                Collectors.toMap(
                    f -> f.getName(),
                    f -> {
                      f.setAccessible(true);
                      return f;
                    }));
    fields.get("iD").set(TechType.Defensive_Matrix, 6);
    fields.get("race").set(TechType.Defensive_Matrix, Race.Terran);
    fields.get("mineralPrice").set(TechType.Defensive_Matrix, 0);
    fields.get("gasPrice").set(TechType.Defensive_Matrix, 0);
    fields.get("researchTime").set(TechType.Defensive_Matrix, 0);
    fields.get("energyCost").set(TechType.Defensive_Matrix, 100);
    fields.get("whatResearches").set(TechType.Defensive_Matrix, UnitType.None);
    fields.get("weaponType").set(TechType.Defensive_Matrix, WeaponType.None);
    fields.get("targetsUnit").set(TechType.Defensive_Matrix, true);
    fields.get("targetsPosition").set(TechType.Defensive_Matrix, false);
    fields.get("order").set(TechType.Defensive_Matrix, Order.CastDefensiveMatrix);
    fields.get("requiredUnit").set(TechType.Defensive_Matrix, UnitType.None);
  }

  private static void initializeTechType_Irradiate() throws Exception {
    Class<?> c = TechType.class;
    Map<String, Field> fields =
        Stream.of(c.getDeclaredFields())
            .collect(
                Collectors.toMap(
                    f -> f.getName(),
                    f -> {
                      f.setAccessible(true);
                      return f;
                    }));
    fields.get("iD").set(TechType.Irradiate, 7);
    fields.get("race").set(TechType.Irradiate, Race.Terran);
    fields.get("mineralPrice").set(TechType.Irradiate, 200);
    fields.get("gasPrice").set(TechType.Irradiate, 200);
    fields.get("researchTime").set(TechType.Irradiate, 1200);
    fields.get("energyCost").set(TechType.Irradiate, 75);
    fields.get("whatResearches").set(TechType.Irradiate, UnitType.Terran_Science_Facility);
    fields.get("weaponType").set(TechType.Irradiate, WeaponType.Irradiate);
    fields.get("targetsUnit").set(TechType.Irradiate, true);
    fields.get("targetsPosition").set(TechType.Irradiate, false);
    fields.get("order").set(TechType.Irradiate, Order.CastIrradiate);
    fields.get("requiredUnit").set(TechType.Irradiate, UnitType.None);
  }

  private static void initializeTechType_Yamato_Gun() throws Exception {
    Class<?> c = TechType.class;
    Map<String, Field> fields =
        Stream.of(c.getDeclaredFields())
            .collect(
                Collectors.toMap(
                    f -> f.getName(),
                    f -> {
                      f.setAccessible(true);
                      return f;
                    }));
    fields.get("iD").set(TechType.Yamato_Gun, 8);
    fields.get("race").set(TechType.Yamato_Gun, Race.Terran);
    fields.get("mineralPrice").set(TechType.Yamato_Gun, 100);
    fields.get("gasPrice").set(TechType.Yamato_Gun, 100);
    fields.get("researchTime").set(TechType.Yamato_Gun, 1800);
    fields.get("energyCost").set(TechType.Yamato_Gun, 150);
    fields.get("whatResearches").set(TechType.Yamato_Gun, UnitType.Terran_Physics_Lab);
    fields.get("weaponType").set(TechType.Yamato_Gun, WeaponType.Yamato_Gun);
    fields.get("targetsUnit").set(TechType.Yamato_Gun, true);
    fields.get("targetsPosition").set(TechType.Yamato_Gun, false);
    fields.get("order").set(TechType.Yamato_Gun, Order.FireYamatoGun);
    fields.get("requiredUnit").set(TechType.Yamato_Gun, UnitType.None);
  }

  private static void initializeTechType_Cloaking_Field() throws Exception {
    Class<?> c = TechType.class;
    Map<String, Field> fields =
        Stream.of(c.getDeclaredFields())
            .collect(
                Collectors.toMap(
                    f -> f.getName(),
                    f -> {
                      f.setAccessible(true);
                      return f;
                    }));
    fields.get("iD").set(TechType.Cloaking_Field, 9);
    fields.get("race").set(TechType.Cloaking_Field, Race.Terran);
    fields.get("mineralPrice").set(TechType.Cloaking_Field, 150);
    fields.get("gasPrice").set(TechType.Cloaking_Field, 150);
    fields.get("researchTime").set(TechType.Cloaking_Field, 1500);
    fields.get("energyCost").set(TechType.Cloaking_Field, 25);
    fields.get("whatResearches").set(TechType.Cloaking_Field, UnitType.Terran_Control_Tower);
    fields.get("weaponType").set(TechType.Cloaking_Field, WeaponType.None);
    fields.get("targetsUnit").set(TechType.Cloaking_Field, false);
    fields.get("targetsPosition").set(TechType.Cloaking_Field, false);
    fields.get("order").set(TechType.Cloaking_Field, Order.None);
    fields.get("requiredUnit").set(TechType.Cloaking_Field, UnitType.None);
  }

  private static void initializeTechType_Personnel_Cloaking() throws Exception {
    Class<?> c = TechType.class;
    Map<String, Field> fields =
        Stream.of(c.getDeclaredFields())
            .collect(
                Collectors.toMap(
                    f -> f.getName(),
                    f -> {
                      f.setAccessible(true);
                      return f;
                    }));
    fields.get("iD").set(TechType.Personnel_Cloaking, 10);
    fields.get("race").set(TechType.Personnel_Cloaking, Race.Terran);
    fields.get("mineralPrice").set(TechType.Personnel_Cloaking, 100);
    fields.get("gasPrice").set(TechType.Personnel_Cloaking, 100);
    fields.get("researchTime").set(TechType.Personnel_Cloaking, 1200);
    fields.get("energyCost").set(TechType.Personnel_Cloaking, 25);
    fields.get("whatResearches").set(TechType.Personnel_Cloaking, UnitType.Terran_Covert_Ops);
    fields.get("weaponType").set(TechType.Personnel_Cloaking, WeaponType.None);
    fields.get("targetsUnit").set(TechType.Personnel_Cloaking, false);
    fields.get("targetsPosition").set(TechType.Personnel_Cloaking, false);
    fields.get("order").set(TechType.Personnel_Cloaking, Order.None);
    fields.get("requiredUnit").set(TechType.Personnel_Cloaking, UnitType.None);
  }

  private static void initializeTechType_Burrowing() throws Exception {
    Class<?> c = TechType.class;
    Map<String, Field> fields =
        Stream.of(c.getDeclaredFields())
            .collect(
                Collectors.toMap(
                    f -> f.getName(),
                    f -> {
                      f.setAccessible(true);
                      return f;
                    }));
    fields.get("iD").set(TechType.Burrowing, 11);
    fields.get("race").set(TechType.Burrowing, Race.Zerg);
    fields.get("mineralPrice").set(TechType.Burrowing, 100);
    fields.get("gasPrice").set(TechType.Burrowing, 100);
    fields.get("researchTime").set(TechType.Burrowing, 1200);
    fields.get("energyCost").set(TechType.Burrowing, 0);
    fields.get("whatResearches").set(TechType.Burrowing, UnitType.Zerg_Hatchery);
    fields.get("weaponType").set(TechType.Burrowing, WeaponType.None);
    fields.get("targetsUnit").set(TechType.Burrowing, false);
    fields.get("targetsPosition").set(TechType.Burrowing, false);
    fields.get("order").set(TechType.Burrowing, Order.None);
    fields.get("requiredUnit").set(TechType.Burrowing, UnitType.None);
  }

  private static void initializeTechType_Infestation() throws Exception {
    Class<?> c = TechType.class;
    Map<String, Field> fields =
        Stream.of(c.getDeclaredFields())
            .collect(
                Collectors.toMap(
                    f -> f.getName(),
                    f -> {
                      f.setAccessible(true);
                      return f;
                    }));
    fields.get("iD").set(TechType.Infestation, 12);
    fields.get("race").set(TechType.Infestation, Race.Zerg);
    fields.get("mineralPrice").set(TechType.Infestation, 0);
    fields.get("gasPrice").set(TechType.Infestation, 0);
    fields.get("researchTime").set(TechType.Infestation, 0);
    fields.get("energyCost").set(TechType.Infestation, 0);
    fields.get("whatResearches").set(TechType.Infestation, UnitType.None);
    fields.get("weaponType").set(TechType.Infestation, WeaponType.None);
    fields.get("targetsUnit").set(TechType.Infestation, true);
    fields.get("targetsPosition").set(TechType.Infestation, false);
    fields.get("order").set(TechType.Infestation, Order.CastInfestation);
    fields.get("requiredUnit").set(TechType.Infestation, UnitType.None);
  }

  private static void initializeTechType_Spawn_Broodlings() throws Exception {
    Class<?> c = TechType.class;
    Map<String, Field> fields =
        Stream.of(c.getDeclaredFields())
            .collect(
                Collectors.toMap(
                    f -> f.getName(),
                    f -> {
                      f.setAccessible(true);
                      return f;
                    }));
    fields.get("iD").set(TechType.Spawn_Broodlings, 13);
    fields.get("race").set(TechType.Spawn_Broodlings, Race.Zerg);
    fields.get("mineralPrice").set(TechType.Spawn_Broodlings, 100);
    fields.get("gasPrice").set(TechType.Spawn_Broodlings, 100);
    fields.get("researchTime").set(TechType.Spawn_Broodlings, 1200);
    fields.get("energyCost").set(TechType.Spawn_Broodlings, 150);
    fields.get("whatResearches").set(TechType.Spawn_Broodlings, UnitType.Zerg_Queens_Nest);
    fields.get("weaponType").set(TechType.Spawn_Broodlings, WeaponType.Spawn_Broodlings);
    fields.get("targetsUnit").set(TechType.Spawn_Broodlings, true);
    fields.get("targetsPosition").set(TechType.Spawn_Broodlings, false);
    fields.get("order").set(TechType.Spawn_Broodlings, Order.CastSpawnBroodlings);
    fields.get("requiredUnit").set(TechType.Spawn_Broodlings, UnitType.None);
  }

  private static void initializeTechType_Dark_Swarm() throws Exception {
    Class<?> c = TechType.class;
    Map<String, Field> fields =
        Stream.of(c.getDeclaredFields())
            .collect(
                Collectors.toMap(
                    f -> f.getName(),
                    f -> {
                      f.setAccessible(true);
                      return f;
                    }));
    fields.get("iD").set(TechType.Dark_Swarm, 14);
    fields.get("race").set(TechType.Dark_Swarm, Race.Zerg);
    fields.get("mineralPrice").set(TechType.Dark_Swarm, 0);
    fields.get("gasPrice").set(TechType.Dark_Swarm, 0);
    fields.get("researchTime").set(TechType.Dark_Swarm, 0);
    fields.get("energyCost").set(TechType.Dark_Swarm, 100);
    fields.get("whatResearches").set(TechType.Dark_Swarm, UnitType.None);
    fields.get("weaponType").set(TechType.Dark_Swarm, WeaponType.Dark_Swarm);
    fields.get("targetsUnit").set(TechType.Dark_Swarm, true);
    fields.get("targetsPosition").set(TechType.Dark_Swarm, true);
    fields.get("order").set(TechType.Dark_Swarm, Order.CastDarkSwarm);
    fields.get("requiredUnit").set(TechType.Dark_Swarm, UnitType.None);
  }

  private static void initializeTechType_Plague() throws Exception {
    Class<?> c = TechType.class;
    Map<String, Field> fields =
        Stream.of(c.getDeclaredFields())
            .collect(
                Collectors.toMap(
                    f -> f.getName(),
                    f -> {
                      f.setAccessible(true);
                      return f;
                    }));
    fields.get("iD").set(TechType.Plague, 15);
    fields.get("race").set(TechType.Plague, Race.Zerg);
    fields.get("mineralPrice").set(TechType.Plague, 200);
    fields.get("gasPrice").set(TechType.Plague, 200);
    fields.get("researchTime").set(TechType.Plague, 1500);
    fields.get("energyCost").set(TechType.Plague, 150);
    fields.get("whatResearches").set(TechType.Plague, UnitType.Zerg_Defiler_Mound);
    fields.get("weaponType").set(TechType.Plague, WeaponType.Plague);
    fields.get("targetsUnit").set(TechType.Plague, true);
    fields.get("targetsPosition").set(TechType.Plague, true);
    fields.get("order").set(TechType.Plague, Order.CastPlague);
    fields.get("requiredUnit").set(TechType.Plague, UnitType.None);
  }

  private static void initializeTechType_Consume() throws Exception {
    Class<?> c = TechType.class;
    Map<String, Field> fields =
        Stream.of(c.getDeclaredFields())
            .collect(
                Collectors.toMap(
                    f -> f.getName(),
                    f -> {
                      f.setAccessible(true);
                      return f;
                    }));
    fields.get("iD").set(TechType.Consume, 16);
    fields.get("race").set(TechType.Consume, Race.Zerg);
    fields.get("mineralPrice").set(TechType.Consume, 100);
    fields.get("gasPrice").set(TechType.Consume, 100);
    fields.get("researchTime").set(TechType.Consume, 1500);
    fields.get("energyCost").set(TechType.Consume, 0);
    fields.get("whatResearches").set(TechType.Consume, UnitType.Zerg_Defiler_Mound);
    fields.get("weaponType").set(TechType.Consume, WeaponType.Consume);
    fields.get("targetsUnit").set(TechType.Consume, true);
    fields.get("targetsPosition").set(TechType.Consume, false);
    fields.get("order").set(TechType.Consume, Order.CastConsume);
    fields.get("requiredUnit").set(TechType.Consume, UnitType.None);
  }

  private static void initializeTechType_Ensnare() throws Exception {
    Class<?> c = TechType.class;
    Map<String, Field> fields =
        Stream.of(c.getDeclaredFields())
            .collect(
                Collectors.toMap(
                    f -> f.getName(),
                    f -> {
                      f.setAccessible(true);
                      return f;
                    }));
    fields.get("iD").set(TechType.Ensnare, 17);
    fields.get("race").set(TechType.Ensnare, Race.Zerg);
    fields.get("mineralPrice").set(TechType.Ensnare, 100);
    fields.get("gasPrice").set(TechType.Ensnare, 100);
    fields.get("researchTime").set(TechType.Ensnare, 1200);
    fields.get("energyCost").set(TechType.Ensnare, 75);
    fields.get("whatResearches").set(TechType.Ensnare, UnitType.Zerg_Queens_Nest);
    fields.get("weaponType").set(TechType.Ensnare, WeaponType.Ensnare);
    fields.get("targetsUnit").set(TechType.Ensnare, true);
    fields.get("targetsPosition").set(TechType.Ensnare, true);
    fields.get("order").set(TechType.Ensnare, Order.CastEnsnare);
    fields.get("requiredUnit").set(TechType.Ensnare, UnitType.None);
  }

  private static void initializeTechType_Parasite() throws Exception {
    Class<?> c = TechType.class;
    Map<String, Field> fields =
        Stream.of(c.getDeclaredFields())
            .collect(
                Collectors.toMap(
                    f -> f.getName(),
                    f -> {
                      f.setAccessible(true);
                      return f;
                    }));
    fields.get("iD").set(TechType.Parasite, 18);
    fields.get("race").set(TechType.Parasite, Race.Zerg);
    fields.get("mineralPrice").set(TechType.Parasite, 0);
    fields.get("gasPrice").set(TechType.Parasite, 0);
    fields.get("researchTime").set(TechType.Parasite, 0);
    fields.get("energyCost").set(TechType.Parasite, 75);
    fields.get("whatResearches").set(TechType.Parasite, UnitType.None);
    fields.get("weaponType").set(TechType.Parasite, WeaponType.Parasite);
    fields.get("targetsUnit").set(TechType.Parasite, true);
    fields.get("targetsPosition").set(TechType.Parasite, false);
    fields.get("order").set(TechType.Parasite, Order.CastParasite);
    fields.get("requiredUnit").set(TechType.Parasite, UnitType.None);
  }

  private static void initializeTechType_Psionic_Storm() throws Exception {
    Class<?> c = TechType.class;
    Map<String, Field> fields =
        Stream.of(c.getDeclaredFields())
            .collect(
                Collectors.toMap(
                    f -> f.getName(),
                    f -> {
                      f.setAccessible(true);
                      return f;
                    }));
    fields.get("iD").set(TechType.Psionic_Storm, 19);
    fields.get("race").set(TechType.Psionic_Storm, Race.Protoss);
    fields.get("mineralPrice").set(TechType.Psionic_Storm, 200);
    fields.get("gasPrice").set(TechType.Psionic_Storm, 200);
    fields.get("researchTime").set(TechType.Psionic_Storm, 1800);
    fields.get("energyCost").set(TechType.Psionic_Storm, 75);
    fields.get("whatResearches").set(TechType.Psionic_Storm, UnitType.Protoss_Templar_Archives);
    fields.get("weaponType").set(TechType.Psionic_Storm, WeaponType.Psionic_Storm);
    fields.get("targetsUnit").set(TechType.Psionic_Storm, true);
    fields.get("targetsPosition").set(TechType.Psionic_Storm, true);
    fields.get("order").set(TechType.Psionic_Storm, Order.CastPsionicStorm);
    fields.get("requiredUnit").set(TechType.Psionic_Storm, UnitType.None);
  }

  private static void initializeTechType_Hallucination() throws Exception {
    Class<?> c = TechType.class;
    Map<String, Field> fields =
        Stream.of(c.getDeclaredFields())
            .collect(
                Collectors.toMap(
                    f -> f.getName(),
                    f -> {
                      f.setAccessible(true);
                      return f;
                    }));
    fields.get("iD").set(TechType.Hallucination, 20);
    fields.get("race").set(TechType.Hallucination, Race.Protoss);
    fields.get("mineralPrice").set(TechType.Hallucination, 150);
    fields.get("gasPrice").set(TechType.Hallucination, 150);
    fields.get("researchTime").set(TechType.Hallucination, 1200);
    fields.get("energyCost").set(TechType.Hallucination, 100);
    fields.get("whatResearches").set(TechType.Hallucination, UnitType.Protoss_Templar_Archives);
    fields.get("weaponType").set(TechType.Hallucination, WeaponType.None);
    fields.get("targetsUnit").set(TechType.Hallucination, true);
    fields.get("targetsPosition").set(TechType.Hallucination, false);
    fields.get("order").set(TechType.Hallucination, Order.CastHallucination);
    fields.get("requiredUnit").set(TechType.Hallucination, UnitType.None);
  }

  private static void initializeTechType_Recall() throws Exception {
    Class<?> c = TechType.class;
    Map<String, Field> fields =
        Stream.of(c.getDeclaredFields())
            .collect(
                Collectors.toMap(
                    f -> f.getName(),
                    f -> {
                      f.setAccessible(true);
                      return f;
                    }));
    fields.get("iD").set(TechType.Recall, 21);
    fields.get("race").set(TechType.Recall, Race.Protoss);
    fields.get("mineralPrice").set(TechType.Recall, 150);
    fields.get("gasPrice").set(TechType.Recall, 150);
    fields.get("researchTime").set(TechType.Recall, 1800);
    fields.get("energyCost").set(TechType.Recall, 150);
    fields.get("whatResearches").set(TechType.Recall, UnitType.Protoss_Arbiter_Tribunal);
    fields.get("weaponType").set(TechType.Recall, WeaponType.None);
    fields.get("targetsUnit").set(TechType.Recall, true);
    fields.get("targetsPosition").set(TechType.Recall, true);
    fields.get("order").set(TechType.Recall, Order.CastRecall);
    fields.get("requiredUnit").set(TechType.Recall, UnitType.None);
  }

  private static void initializeTechType_Stasis_Field() throws Exception {
    Class<?> c = TechType.class;
    Map<String, Field> fields =
        Stream.of(c.getDeclaredFields())
            .collect(
                Collectors.toMap(
                    f -> f.getName(),
                    f -> {
                      f.setAccessible(true);
                      return f;
                    }));
    fields.get("iD").set(TechType.Stasis_Field, 22);
    fields.get("race").set(TechType.Stasis_Field, Race.Protoss);
    fields.get("mineralPrice").set(TechType.Stasis_Field, 150);
    fields.get("gasPrice").set(TechType.Stasis_Field, 150);
    fields.get("researchTime").set(TechType.Stasis_Field, 1500);
    fields.get("energyCost").set(TechType.Stasis_Field, 100);
    fields.get("whatResearches").set(TechType.Stasis_Field, UnitType.Protoss_Arbiter_Tribunal);
    fields.get("weaponType").set(TechType.Stasis_Field, WeaponType.Stasis_Field);
    fields.get("targetsUnit").set(TechType.Stasis_Field, true);
    fields.get("targetsPosition").set(TechType.Stasis_Field, true);
    fields.get("order").set(TechType.Stasis_Field, Order.CastStasisField);
    fields.get("requiredUnit").set(TechType.Stasis_Field, UnitType.None);
  }

  private static void initializeTechType_Archon_Warp() throws Exception {
    Class<?> c = TechType.class;
    Map<String, Field> fields =
        Stream.of(c.getDeclaredFields())
            .collect(
                Collectors.toMap(
                    f -> f.getName(),
                    f -> {
                      f.setAccessible(true);
                      return f;
                    }));
    fields.get("iD").set(TechType.Archon_Warp, 23);
    fields.get("race").set(TechType.Archon_Warp, Race.Protoss);
    fields.get("mineralPrice").set(TechType.Archon_Warp, 0);
    fields.get("gasPrice").set(TechType.Archon_Warp, 0);
    fields.get("researchTime").set(TechType.Archon_Warp, 0);
    fields.get("energyCost").set(TechType.Archon_Warp, 0);
    fields.get("whatResearches").set(TechType.Archon_Warp, UnitType.None);
    fields.get("weaponType").set(TechType.Archon_Warp, WeaponType.None);
    fields.get("targetsUnit").set(TechType.Archon_Warp, true);
    fields.get("targetsPosition").set(TechType.Archon_Warp, false);
    fields.get("order").set(TechType.Archon_Warp, Order.None);
    fields.get("requiredUnit").set(TechType.Archon_Warp, UnitType.None);
  }

  private static void initializeTechType_Restoration() throws Exception {
    Class<?> c = TechType.class;
    Map<String, Field> fields =
        Stream.of(c.getDeclaredFields())
            .collect(
                Collectors.toMap(
                    f -> f.getName(),
                    f -> {
                      f.setAccessible(true);
                      return f;
                    }));
    fields.get("iD").set(TechType.Restoration, 24);
    fields.get("race").set(TechType.Restoration, Race.Terran);
    fields.get("mineralPrice").set(TechType.Restoration, 100);
    fields.get("gasPrice").set(TechType.Restoration, 100);
    fields.get("researchTime").set(TechType.Restoration, 1200);
    fields.get("energyCost").set(TechType.Restoration, 50);
    fields.get("whatResearches").set(TechType.Restoration, UnitType.Terran_Academy);
    fields.get("weaponType").set(TechType.Restoration, WeaponType.Restoration);
    fields.get("targetsUnit").set(TechType.Restoration, true);
    fields.get("targetsPosition").set(TechType.Restoration, false);
    fields.get("order").set(TechType.Restoration, Order.CastRestoration);
    fields.get("requiredUnit").set(TechType.Restoration, UnitType.None);
  }

  private static void initializeTechType_Disruption_Web() throws Exception {
    Class<?> c = TechType.class;
    Map<String, Field> fields =
        Stream.of(c.getDeclaredFields())
            .collect(
                Collectors.toMap(
                    f -> f.getName(),
                    f -> {
                      f.setAccessible(true);
                      return f;
                    }));
    fields.get("iD").set(TechType.Disruption_Web, 25);
    fields.get("race").set(TechType.Disruption_Web, Race.Protoss);
    fields.get("mineralPrice").set(TechType.Disruption_Web, 200);
    fields.get("gasPrice").set(TechType.Disruption_Web, 200);
    fields.get("researchTime").set(TechType.Disruption_Web, 1200);
    fields.get("energyCost").set(TechType.Disruption_Web, 125);
    fields.get("whatResearches").set(TechType.Disruption_Web, UnitType.Protoss_Fleet_Beacon);
    fields.get("weaponType").set(TechType.Disruption_Web, WeaponType.Disruption_Web);
    fields.get("targetsUnit").set(TechType.Disruption_Web, true);
    fields.get("targetsPosition").set(TechType.Disruption_Web, true);
    fields.get("order").set(TechType.Disruption_Web, Order.CastDisruptionWeb);
    fields.get("requiredUnit").set(TechType.Disruption_Web, UnitType.None);
  }

  private static void initializeTechType_Unused_26() throws Exception {
    Class<?> c = TechType.class;
    Map<String, Field> fields =
        Stream.of(c.getDeclaredFields())
            .collect(
                Collectors.toMap(
                    f -> f.getName(),
                    f -> {
                      f.setAccessible(true);
                      return f;
                    }));
    fields.get("iD").set(TechType.Unused_26, 26);
    fields.get("race").set(TechType.Unused_26, null);
    fields.get("mineralPrice").set(TechType.Unused_26, 0);
    fields.get("gasPrice").set(TechType.Unused_26, 0);
    fields.get("researchTime").set(TechType.Unused_26, 0);
    fields.get("energyCost").set(TechType.Unused_26, 0);
    fields.get("whatResearches").set(TechType.Unused_26, null);
    fields.get("weaponType").set(TechType.Unused_26, null);
    fields.get("targetsUnit").set(TechType.Unused_26, false);
    fields.get("targetsPosition").set(TechType.Unused_26, false);
    fields.get("order").set(TechType.Unused_26, null);
    fields.get("requiredUnit").set(TechType.Unused_26, null);
  }

  private static void initializeTechType_Mind_Control() throws Exception {
    Class<?> c = TechType.class;
    Map<String, Field> fields =
        Stream.of(c.getDeclaredFields())
            .collect(
                Collectors.toMap(
                    f -> f.getName(),
                    f -> {
                      f.setAccessible(true);
                      return f;
                    }));
    fields.get("iD").set(TechType.Mind_Control, 27);
    fields.get("race").set(TechType.Mind_Control, Race.Protoss);
    fields.get("mineralPrice").set(TechType.Mind_Control, 200);
    fields.get("gasPrice").set(TechType.Mind_Control, 200);
    fields.get("researchTime").set(TechType.Mind_Control, 1800);
    fields.get("energyCost").set(TechType.Mind_Control, 150);
    fields.get("whatResearches").set(TechType.Mind_Control, UnitType.Protoss_Templar_Archives);
    fields.get("weaponType").set(TechType.Mind_Control, WeaponType.Mind_Control);
    fields.get("targetsUnit").set(TechType.Mind_Control, true);
    fields.get("targetsPosition").set(TechType.Mind_Control, false);
    fields.get("order").set(TechType.Mind_Control, Order.CastMindControl);
    fields.get("requiredUnit").set(TechType.Mind_Control, UnitType.None);
  }

  private static void initializeTechType_Dark_Archon_Meld() throws Exception {
    Class<?> c = TechType.class;
    Map<String, Field> fields =
        Stream.of(c.getDeclaredFields())
            .collect(
                Collectors.toMap(
                    f -> f.getName(),
                    f -> {
                      f.setAccessible(true);
                      return f;
                    }));
    fields.get("iD").set(TechType.Dark_Archon_Meld, 28);
    fields.get("race").set(TechType.Dark_Archon_Meld, Race.Protoss);
    fields.get("mineralPrice").set(TechType.Dark_Archon_Meld, 0);
    fields.get("gasPrice").set(TechType.Dark_Archon_Meld, 0);
    fields.get("researchTime").set(TechType.Dark_Archon_Meld, 0);
    fields.get("energyCost").set(TechType.Dark_Archon_Meld, 0);
    fields.get("whatResearches").set(TechType.Dark_Archon_Meld, UnitType.None);
    fields.get("weaponType").set(TechType.Dark_Archon_Meld, WeaponType.None);
    fields.get("targetsUnit").set(TechType.Dark_Archon_Meld, true);
    fields.get("targetsPosition").set(TechType.Dark_Archon_Meld, false);
    fields.get("order").set(TechType.Dark_Archon_Meld, Order.None);
    fields.get("requiredUnit").set(TechType.Dark_Archon_Meld, UnitType.None);
  }

  private static void initializeTechType_Feedback() throws Exception {
    Class<?> c = TechType.class;
    Map<String, Field> fields =
        Stream.of(c.getDeclaredFields())
            .collect(
                Collectors.toMap(
                    f -> f.getName(),
                    f -> {
                      f.setAccessible(true);
                      return f;
                    }));
    fields.get("iD").set(TechType.Feedback, 29);
    fields.get("race").set(TechType.Feedback, Race.Protoss);
    fields.get("mineralPrice").set(TechType.Feedback, 100);
    fields.get("gasPrice").set(TechType.Feedback, 100);
    fields.get("researchTime").set(TechType.Feedback, 1800);
    fields.get("energyCost").set(TechType.Feedback, 50);
    fields.get("whatResearches").set(TechType.Feedback, UnitType.None);
    fields.get("weaponType").set(TechType.Feedback, WeaponType.Feedback);
    fields.get("targetsUnit").set(TechType.Feedback, true);
    fields.get("targetsPosition").set(TechType.Feedback, false);
    fields.get("order").set(TechType.Feedback, Order.CastFeedback);
    fields.get("requiredUnit").set(TechType.Feedback, UnitType.None);
  }

  private static void initializeTechType_Optical_Flare() throws Exception {
    Class<?> c = TechType.class;
    Map<String, Field> fields =
        Stream.of(c.getDeclaredFields())
            .collect(
                Collectors.toMap(
                    f -> f.getName(),
                    f -> {
                      f.setAccessible(true);
                      return f;
                    }));
    fields.get("iD").set(TechType.Optical_Flare, 30);
    fields.get("race").set(TechType.Optical_Flare, Race.Terran);
    fields.get("mineralPrice").set(TechType.Optical_Flare, 100);
    fields.get("gasPrice").set(TechType.Optical_Flare, 100);
    fields.get("researchTime").set(TechType.Optical_Flare, 1800);
    fields.get("energyCost").set(TechType.Optical_Flare, 75);
    fields.get("whatResearches").set(TechType.Optical_Flare, UnitType.Terran_Academy);
    fields.get("weaponType").set(TechType.Optical_Flare, WeaponType.Optical_Flare);
    fields.get("targetsUnit").set(TechType.Optical_Flare, true);
    fields.get("targetsPosition").set(TechType.Optical_Flare, false);
    fields.get("order").set(TechType.Optical_Flare, Order.CastOpticalFlare);
    fields.get("requiredUnit").set(TechType.Optical_Flare, UnitType.None);
  }

  private static void initializeTechType_Maelstrom() throws Exception {
    Class<?> c = TechType.class;
    Map<String, Field> fields =
        Stream.of(c.getDeclaredFields())
            .collect(
                Collectors.toMap(
                    f -> f.getName(),
                    f -> {
                      f.setAccessible(true);
                      return f;
                    }));
    fields.get("iD").set(TechType.Maelstrom, 31);
    fields.get("race").set(TechType.Maelstrom, Race.Protoss);
    fields.get("mineralPrice").set(TechType.Maelstrom, 100);
    fields.get("gasPrice").set(TechType.Maelstrom, 100);
    fields.get("researchTime").set(TechType.Maelstrom, 1500);
    fields.get("energyCost").set(TechType.Maelstrom, 100);
    fields.get("whatResearches").set(TechType.Maelstrom, UnitType.Protoss_Templar_Archives);
    fields.get("weaponType").set(TechType.Maelstrom, WeaponType.Maelstrom);
    fields.get("targetsUnit").set(TechType.Maelstrom, true);
    fields.get("targetsPosition").set(TechType.Maelstrom, true);
    fields.get("order").set(TechType.Maelstrom, Order.CastMaelstrom);
    fields.get("requiredUnit").set(TechType.Maelstrom, UnitType.None);
  }

  private static void initializeTechType_Lurker_Aspect() throws Exception {
    Class<?> c = TechType.class;
    Map<String, Field> fields =
        Stream.of(c.getDeclaredFields())
            .collect(
                Collectors.toMap(
                    f -> f.getName(),
                    f -> {
                      f.setAccessible(true);
                      return f;
                    }));
    fields.get("iD").set(TechType.Lurker_Aspect, 32);
    fields.get("race").set(TechType.Lurker_Aspect, Race.Zerg);
    fields.get("mineralPrice").set(TechType.Lurker_Aspect, 200);
    fields.get("gasPrice").set(TechType.Lurker_Aspect, 200);
    fields.get("researchTime").set(TechType.Lurker_Aspect, 1800);
    fields.get("energyCost").set(TechType.Lurker_Aspect, 0);
    fields.get("whatResearches").set(TechType.Lurker_Aspect, UnitType.Zerg_Hydralisk_Den);
    fields.get("weaponType").set(TechType.Lurker_Aspect, WeaponType.None);
    fields.get("targetsUnit").set(TechType.Lurker_Aspect, false);
    fields.get("targetsPosition").set(TechType.Lurker_Aspect, false);
    fields.get("order").set(TechType.Lurker_Aspect, Order.None);
    fields.get("requiredUnit").set(TechType.Lurker_Aspect, UnitType.Zerg_Lair);
  }

  private static void initializeTechType_Unused_33() throws Exception {
    Class<?> c = TechType.class;
    Map<String, Field> fields =
        Stream.of(c.getDeclaredFields())
            .collect(
                Collectors.toMap(
                    f -> f.getName(),
                    f -> {
                      f.setAccessible(true);
                      return f;
                    }));
    fields.get("iD").set(TechType.Unused_33, 33);
    fields.get("race").set(TechType.Unused_33, null);
    fields.get("mineralPrice").set(TechType.Unused_33, 0);
    fields.get("gasPrice").set(TechType.Unused_33, 0);
    fields.get("researchTime").set(TechType.Unused_33, 0);
    fields.get("energyCost").set(TechType.Unused_33, 0);
    fields.get("whatResearches").set(TechType.Unused_33, null);
    fields.get("weaponType").set(TechType.Unused_33, null);
    fields.get("targetsUnit").set(TechType.Unused_33, false);
    fields.get("targetsPosition").set(TechType.Unused_33, false);
    fields.get("order").set(TechType.Unused_33, null);
    fields.get("requiredUnit").set(TechType.Unused_33, null);
  }

  private static void initializeTechType_Healing() throws Exception {
    Class<?> c = TechType.class;
    Map<String, Field> fields =
        Stream.of(c.getDeclaredFields())
            .collect(
                Collectors.toMap(
                    f -> f.getName(),
                    f -> {
                      f.setAccessible(true);
                      return f;
                    }));
    fields.get("iD").set(TechType.Healing, 34);
    fields.get("race").set(TechType.Healing, Race.Terran);
    fields.get("mineralPrice").set(TechType.Healing, 0);
    fields.get("gasPrice").set(TechType.Healing, 0);
    fields.get("researchTime").set(TechType.Healing, 0);
    fields.get("energyCost").set(TechType.Healing, 1);
    fields.get("whatResearches").set(TechType.Healing, UnitType.None);
    fields.get("weaponType").set(TechType.Healing, WeaponType.None);
    fields.get("targetsUnit").set(TechType.Healing, true);
    fields.get("targetsPosition").set(TechType.Healing, true);
    fields.get("order").set(TechType.Healing, Order.MedicHeal);
    fields.get("requiredUnit").set(TechType.Healing, UnitType.None);
  }

  private static void initializeTechType_None() throws Exception {
    Class<?> c = TechType.class;
    Map<String, Field> fields =
        Stream.of(c.getDeclaredFields())
            .collect(
                Collectors.toMap(
                    f -> f.getName(),
                    f -> {
                      f.setAccessible(true);
                      return f;
                    }));
    fields.get("iD").set(TechType.None, 44);
    fields.get("race").set(TechType.None, Race.None);
    fields.get("mineralPrice").set(TechType.None, 0);
    fields.get("gasPrice").set(TechType.None, 0);
    fields.get("researchTime").set(TechType.None, 0);
    fields.get("energyCost").set(TechType.None, 0);
    fields.get("whatResearches").set(TechType.None, UnitType.None);
    fields.get("weaponType").set(TechType.None, WeaponType.None);
    fields.get("targetsUnit").set(TechType.None, false);
    fields.get("targetsPosition").set(TechType.None, false);
    fields.get("order").set(TechType.None, Order.None);
    fields.get("requiredUnit").set(TechType.None, UnitType.None);
  }

  private static void initializeTechType_Nuclear_Strike() throws Exception {
    Class<?> c = TechType.class;
    Map<String, Field> fields =
        Stream.of(c.getDeclaredFields())
            .collect(
                Collectors.toMap(
                    f -> f.getName(),
                    f -> {
                      f.setAccessible(true);
                      return f;
                    }));
    fields.get("iD").set(TechType.Nuclear_Strike, 45);
    fields.get("race").set(TechType.Nuclear_Strike, Race.Terran);
    fields.get("mineralPrice").set(TechType.Nuclear_Strike, 0);
    fields.get("gasPrice").set(TechType.Nuclear_Strike, 0);
    fields.get("researchTime").set(TechType.Nuclear_Strike, 0);
    fields.get("energyCost").set(TechType.Nuclear_Strike, 0);
    fields.get("whatResearches").set(TechType.Nuclear_Strike, UnitType.None);
    fields.get("weaponType").set(TechType.Nuclear_Strike, WeaponType.Nuclear_Strike);
    fields.get("targetsUnit").set(TechType.Nuclear_Strike, true);
    fields.get("targetsPosition").set(TechType.Nuclear_Strike, true);
    fields.get("order").set(TechType.Nuclear_Strike, Order.NukePaint);
    fields.get("requiredUnit").set(TechType.Nuclear_Strike, UnitType.None);
  }

  private static void initializeTechType_Unknown() throws Exception {
    Class<?> c = TechType.class;
    Map<String, Field> fields =
        Stream.of(c.getDeclaredFields())
            .collect(
                Collectors.toMap(
                    f -> f.getName(),
                    f -> {
                      f.setAccessible(true);
                      return f;
                    }));
    fields.get("iD").set(TechType.Unknown, 46);
    fields.get("race").set(TechType.Unknown, Race.Unknown);
    fields.get("mineralPrice").set(TechType.Unknown, 0);
    fields.get("gasPrice").set(TechType.Unknown, 0);
    fields.get("researchTime").set(TechType.Unknown, 0);
    fields.get("energyCost").set(TechType.Unknown, 0);
    fields.get("whatResearches").set(TechType.Unknown, UnitType.Unknown);
    fields.get("weaponType").set(TechType.Unknown, WeaponType.Unknown);
    fields.get("targetsUnit").set(TechType.Unknown, false);
    fields.get("targetsPosition").set(TechType.Unknown, false);
    fields.get("order").set(TechType.Unknown, Order.Unknown);
    fields.get("requiredUnit").set(TechType.Unknown, UnitType.None);
  }

  private static void initializeTechType_MAX() throws Exception {
    Class<?> c = TechType.class;
    Map<String, Field> fields =
        Stream.of(c.getDeclaredFields())
            .collect(
                Collectors.toMap(
                    f -> f.getName(),
                    f -> {
                      f.setAccessible(true);
                      return f;
                    }));
    fields.get("iD").set(TechType.MAX, 47);
    fields.get("race").set(TechType.MAX, null);
    fields.get("mineralPrice").set(TechType.MAX, 0);
    fields.get("gasPrice").set(TechType.MAX, 0);
    fields.get("researchTime").set(TechType.MAX, 0);
    fields.get("energyCost").set(TechType.MAX, 0);
    fields.get("whatResearches").set(TechType.MAX, null);
    fields.get("weaponType").set(TechType.MAX, null);
    fields.get("targetsUnit").set(TechType.MAX, false);
    fields.get("targetsPosition").set(TechType.MAX, false);
    fields.get("order").set(TechType.MAX, null);
    fields.get("requiredUnit").set(TechType.MAX, null);
  }

  private static Map<?, ?> toMap(Object... element) {
    Map<Object, Object> map = new HashMap<>();
    for (int i = 0; i < element.length; i += 2) {
      map.put(element[i], element[i + 1]);
    }
    return map;
  }
}
