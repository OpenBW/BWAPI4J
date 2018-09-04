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

#include "BridgeEnum.h"

#include <BWAPI.h>

#include "Logger.h"

void BridgeEnum::initialize(JNIEnv *env, const JavaRefs &javaRefs) {
  createUpgradeTypeEnum(env, javaRefs);
  createTechTypeEnum(env, javaRefs);
  createWeaponTypeEnum(env, javaRefs);
  createUnitTypeEnum(env, javaRefs);
}

void BridgeEnum::createUpgradeTypeEnum(JNIEnv *env, const JavaRefs &javaRefs) {
  LOGGER("Reading upgrade types...");

  for (BWAPI::UpgradeType upgradeType : BWAPI::UpgradeTypes::allUpgradeTypes()) {
    if (upgradeType.getName().empty()) {
      return;
    }
    jfieldID upgradeTypeField = env->GetStaticFieldID(javaRefs.upgradeTypeClass, upgradeType.getName().c_str(), "Lorg/openbw/bwapi4j/type/UpgradeType;");
    jobject CurrentUpgradeType = env->GetStaticObjectField(javaRefs.upgradeTypeClass, upgradeTypeField);

    // set int fields
    env->SetIntField(CurrentUpgradeType, env->GetFieldID(javaRefs.upgradeTypeClass, "id", "I"), upgradeType.getID());
    env->SetIntField(CurrentUpgradeType, env->GetFieldID(javaRefs.upgradeTypeClass, "mineralPriceFactor", "I"), upgradeType.mineralPriceFactor());
    env->SetIntField(CurrentUpgradeType, env->GetFieldID(javaRefs.upgradeTypeClass, "gasPriceFactor", "I"), upgradeType.gasPriceFactor());
    env->SetIntField(CurrentUpgradeType, env->GetFieldID(javaRefs.upgradeTypeClass, "upgradeTimeFactor", "I"), upgradeType.upgradeTimeFactor());
    env->SetIntField(CurrentUpgradeType, env->GetFieldID(javaRefs.upgradeTypeClass, "maxRepeats", "I"), upgradeType.maxRepeats());
    // set int[] fields
    jint *gasPrices = new jint[upgradeType.maxRepeats()];
    jint *mineralPrices = new jint[upgradeType.maxRepeats()];
    jint *upgradeTimes = new jint[upgradeType.maxRepeats()];
    jobject *whatsRequired = new jobject[upgradeType.maxRepeats()];

    for (int i = 0; i < upgradeType.maxRepeats(); i++) {
      gasPrices[i] = upgradeType.gasPrice(i + 1);
      mineralPrices[i] = upgradeType.mineralPrice(i + 1);
      upgradeTimes[i] = upgradeType.upgradeTime(i + 1);
      whatsRequired[i] = env->GetStaticObjectField(
          javaRefs.unitTypeClass,
          env->GetStaticFieldID(javaRefs.unitTypeClass, upgradeType.whatsRequired(i + 1).getName().c_str(), "Lorg/openbw/bwapi4j/type/UnitType;"));
    }

    jintArray gasPricesArray = env->NewIntArray(upgradeType.maxRepeats());
    env->SetIntArrayRegion(gasPricesArray, 0, upgradeType.maxRepeats(), gasPrices);
    env->SetObjectField(CurrentUpgradeType, env->GetFieldID(javaRefs.upgradeTypeClass, "gasPrices", "[I"), gasPricesArray);

    jintArray mineralPricesArray = env->NewIntArray(upgradeType.maxRepeats());
    env->SetIntArrayRegion(mineralPricesArray, 0, upgradeType.maxRepeats(), mineralPrices);
    env->SetObjectField(CurrentUpgradeType, env->GetFieldID(javaRefs.upgradeTypeClass, "mineralPrices", "[I"), mineralPricesArray);

    jintArray upgradeTimesArray = env->NewIntArray(upgradeType.maxRepeats());
    env->SetIntArrayRegion(upgradeTimesArray, 0, upgradeType.maxRepeats(), upgradeTimes);
    env->SetObjectField(CurrentUpgradeType, env->GetFieldID(javaRefs.upgradeTypeClass, "upgradeTimes", "[I"), upgradeTimesArray);

    jobjectArray objectArray = env->NewObjectArray(upgradeType.maxRepeats(), javaRefs.unitTypeClass, NULL);
    env->SetObjectField(CurrentUpgradeType, env->GetFieldID(javaRefs.upgradeTypeClass, "whatsRequired", "[Lorg/openbw/bwapi4j/type/UnitType;"), objectArray);
    for (int i = 0; i < upgradeType.maxRepeats(); i++) {
      env->SetObjectArrayElement(objectArray, i, whatsRequired[i]);
    }

    // read existing whatUses set and put UnitType entries
    for (auto const &use : upgradeType.whatUses()) {
      env->CallObjectMethod(CurrentUpgradeType, javaRefs.upgradeTypeClass_addUsingUnit, (jint)use.getID());
    }

    // set enum fields
    jobject race = env->GetStaticObjectField(javaRefs.raceClass,
                                             env->GetStaticFieldID(javaRefs.raceClass, upgradeType.getRace().c_str(), "Lorg/openbw/bwapi4j/type/Race;"));
    env->SetObjectField(CurrentUpgradeType, env->GetFieldID(javaRefs.upgradeTypeClass, "race", "Lorg/openbw/bwapi4j/type/Race;"), race);

    jobject whatUpgrades = env->GetStaticObjectField(
        javaRefs.unitTypeClass, env->GetStaticFieldID(javaRefs.unitTypeClass, upgradeType.whatUpgrades().c_str(), "Lorg/openbw/bwapi4j/type/UnitType;"));
    env->SetObjectField(CurrentUpgradeType, env->GetFieldID(javaRefs.upgradeTypeClass, "whatUpgrades", "Lorg/openbw/bwapi4j/type/UnitType;"), whatUpgrades);
  }

  LOGGER("Reading upgrade types... done");
}

void BridgeEnum::createTechTypeEnum(JNIEnv *env, const JavaRefs &javaRefs) {
  LOGGER("Reading tech types...");

  for (BWAPI::TechType techType : BWAPI::TechTypes::allTechTypes()) {
    if (techType.getName().empty()) {
      return;
    }
    jfieldID techTypeField = env->GetStaticFieldID(javaRefs.techTypeClass, techType.getName().c_str(), "Lorg/openbw/bwapi4j/type/TechType;");
    jobject CurrentTechType = env->GetStaticObjectField(javaRefs.techTypeClass, techTypeField);

    // set int fields
    env->SetIntField(CurrentTechType, env->GetFieldID(javaRefs.techTypeClass, "id", "I"), techType.getID());
    env->SetIntField(CurrentTechType, env->GetFieldID(javaRefs.techTypeClass, "mineralPrice", "I"), techType.mineralPrice());
    env->SetIntField(CurrentTechType, env->GetFieldID(javaRefs.techTypeClass, "gasPrice", "I"), techType.gasPrice());
    env->SetIntField(CurrentTechType, env->GetFieldID(javaRefs.techTypeClass, "researchTime", "I"), techType.researchTime());
    env->SetIntField(CurrentTechType, env->GetFieldID(javaRefs.techTypeClass, "energyCost", "I"), techType.energyCost());
    // set boolean fields
    env->SetBooleanField(CurrentTechType, env->GetFieldID(javaRefs.techTypeClass, "targetsUnit", "Z"), techType.targetsUnit());
    env->SetBooleanField(CurrentTechType, env->GetFieldID(javaRefs.techTypeClass, "targetsPosition", "Z"), techType.targetsPosition());
    // set enum fields
    jobject race = env->GetStaticObjectField(javaRefs.raceClass,
                                             env->GetStaticFieldID(javaRefs.raceClass, techType.getRace().getName().c_str(), "Lorg/openbw/bwapi4j/type/Race;"));
    env->SetObjectField(CurrentTechType, env->GetFieldID(javaRefs.techTypeClass, "race", "Lorg/openbw/bwapi4j/type/Race;"), race);

    jobject weaponType = env->GetStaticObjectField(
        javaRefs.weaponTypeClass,
        env->GetStaticFieldID(javaRefs.weaponTypeClass, techType.getWeapon().getName().c_str(), "Lorg/openbw/bwapi4j/type/WeaponType;"));
    env->SetObjectField(CurrentTechType, env->GetFieldID(javaRefs.techTypeClass, "weaponType", "Lorg/openbw/bwapi4j/type/WeaponType;"), weaponType);

    jobject whatResearches = env->GetStaticObjectField(
        javaRefs.unitTypeClass,
        env->GetStaticFieldID(javaRefs.unitTypeClass, techType.whatResearches().getName().c_str(), "Lorg/openbw/bwapi4j/type/UnitType;"));
    env->SetObjectField(CurrentTechType, env->GetFieldID(javaRefs.techTypeClass, "whatResearches", "Lorg/openbw/bwapi4j/type/UnitType;"), whatResearches);

    jobject order = env->GetStaticObjectField(
        javaRefs.orderClass, env->GetStaticFieldID(javaRefs.orderClass, techType.getOrder().getName().c_str(), "Lorg/openbw/bwapi4j/type/Order;"));
    env->SetObjectField(CurrentTechType, env->GetFieldID(javaRefs.techTypeClass, "order", "Lorg/openbw/bwapi4j/type/Order;"), order);

    jobject requiredUnit = env->GetStaticObjectField(
        javaRefs.unitTypeClass, env->GetStaticFieldID(javaRefs.unitTypeClass, techType.requiredUnit().getName().c_str(), "Lorg/openbw/bwapi4j/type/UnitType;"));
    env->SetObjectField(CurrentTechType, env->GetFieldID(javaRefs.techTypeClass, "requiredUnit", "Lorg/openbw/bwapi4j/type/UnitType;"), requiredUnit);
  }

  LOGGER("Reading tech types... done");
}

void BridgeEnum::createWeaponTypeEnum(JNIEnv *env, const JavaRefs &javaRefs) {
  LOGGER("Reading weapon types...");

  for (BWAPI::WeaponType weaponType : BWAPI::WeaponTypes::allWeaponTypes()) {
    if (weaponType.getName().empty()) {
      return;
    }
    jfieldID typeField = env->GetStaticFieldID(javaRefs.weaponTypeClass, weaponType.getName().c_str(), "Lorg/openbw/bwapi4j/type/WeaponType;");
    jobject CurrentWeaponType = env->GetStaticObjectField(javaRefs.weaponTypeClass, typeField);

    // set int fields
    env->SetIntField(CurrentWeaponType, env->GetFieldID(javaRefs.weaponTypeClass, "id", "I"), weaponType.getID());
    env->SetIntField(CurrentWeaponType, env->GetFieldID(javaRefs.weaponTypeClass, "damageAmount", "I"), weaponType.damageAmount());
    env->SetIntField(CurrentWeaponType, env->GetFieldID(javaRefs.weaponTypeClass, "damageBonus", "I"), weaponType.damageBonus());
    env->SetIntField(CurrentWeaponType, env->GetFieldID(javaRefs.weaponTypeClass, "damageCooldown", "I"), weaponType.damageCooldown());
    env->SetIntField(CurrentWeaponType, env->GetFieldID(javaRefs.weaponTypeClass, "damageFactor", "I"), weaponType.damageFactor());
    env->SetIntField(CurrentWeaponType, env->GetFieldID(javaRefs.weaponTypeClass, "minRange", "I"), weaponType.minRange());
    env->SetIntField(CurrentWeaponType, env->GetFieldID(javaRefs.weaponTypeClass, "maxRange", "I"), weaponType.maxRange());
    env->SetIntField(CurrentWeaponType, env->GetFieldID(javaRefs.weaponTypeClass, "innerSplashRadius", "I"), weaponType.innerSplashRadius());
    env->SetIntField(CurrentWeaponType, env->GetFieldID(javaRefs.weaponTypeClass, "medianSplashRadius", "I"), weaponType.medianSplashRadius());
    env->SetIntField(CurrentWeaponType, env->GetFieldID(javaRefs.weaponTypeClass, "outerSplashRadius", "I"), weaponType.outerSplashRadius());
    // set boolean fields
    env->SetBooleanField(CurrentWeaponType, env->GetFieldID(javaRefs.weaponTypeClass, "targetsAir", "Z"), weaponType.targetsAir());
    env->SetBooleanField(CurrentWeaponType, env->GetFieldID(javaRefs.weaponTypeClass, "targetsGround", "Z"), weaponType.targetsGround());
    env->SetBooleanField(CurrentWeaponType, env->GetFieldID(javaRefs.weaponTypeClass, "targetsMechanical", "Z"), weaponType.targetsMechanical());
    env->SetBooleanField(CurrentWeaponType, env->GetFieldID(javaRefs.weaponTypeClass, "targetsOrganic", "Z"), weaponType.targetsOrganic());
    env->SetBooleanField(CurrentWeaponType, env->GetFieldID(javaRefs.weaponTypeClass, "targetsNonBuilding", "Z"), weaponType.targetsNonBuilding());
    env->SetBooleanField(CurrentWeaponType, env->GetFieldID(javaRefs.weaponTypeClass, "targetsNonRobotic", "Z"), weaponType.targetsNonRobotic());
    env->SetBooleanField(CurrentWeaponType, env->GetFieldID(javaRefs.weaponTypeClass, "targetsTerrain", "Z"), weaponType.targetsTerrain());
    env->SetBooleanField(CurrentWeaponType, env->GetFieldID(javaRefs.weaponTypeClass, "targetsOrgOrMech", "Z"), weaponType.targetsOrgOrMech());
    env->SetBooleanField(CurrentWeaponType, env->GetFieldID(javaRefs.weaponTypeClass, "targetsOwn", "Z"), weaponType.targetsOwn());

    // set enum fields
    jobject tech = env->GetStaticObjectField(
        javaRefs.techTypeClass, env->GetStaticFieldID(javaRefs.techTypeClass, weaponType.getTech().getName().c_str(), "Lorg/openbw/bwapi4j/type/TechType;"));
    env->SetObjectField(CurrentWeaponType, env->GetFieldID(javaRefs.weaponTypeClass, "tech", "Lorg/openbw/bwapi4j/type/TechType;"), tech);

    jobject whatUses = env->GetStaticObjectField(
        javaRefs.unitTypeClass, env->GetStaticFieldID(javaRefs.unitTypeClass, weaponType.whatUses().getName().c_str(), "Lorg/openbw/bwapi4j/type/UnitType;"));
    env->SetObjectField(CurrentWeaponType, env->GetFieldID(javaRefs.weaponTypeClass, "whatUses", "Lorg/openbw/bwapi4j/type/UnitType;"), whatUses);

    jobject upgradeType = env->GetStaticObjectField(
        javaRefs.upgradeTypeClass,
        env->GetStaticFieldID(javaRefs.upgradeTypeClass, weaponType.upgradeType().getName().c_str(), "Lorg/openbw/bwapi4j/type/UpgradeType;"));
    env->SetObjectField(CurrentWeaponType, env->GetFieldID(javaRefs.weaponTypeClass, "upgradeType", "Lorg/openbw/bwapi4j/type/UpgradeType;"), upgradeType);

    jobject damageType = env->GetStaticObjectField(
        javaRefs.damageTypeClass,
        env->GetStaticFieldID(javaRefs.damageTypeClass, weaponType.damageType().getName().c_str(), "Lorg/openbw/bwapi4j/type/DamageType;"));
    env->SetObjectField(CurrentWeaponType, env->GetFieldID(javaRefs.weaponTypeClass, "damageType", "Lorg/openbw/bwapi4j/type/DamageType;"), damageType);

    jobject explosionType = env->GetStaticObjectField(
        javaRefs.explosionTypeClass,
        env->GetStaticFieldID(javaRefs.explosionTypeClass, weaponType.explosionType().getName().c_str(), "Lorg/openbw/bwapi4j/type/ExplosionType;"));
    env->SetObjectField(CurrentWeaponType, env->GetFieldID(javaRefs.weaponTypeClass, "explosionType", "Lorg/openbw/bwapi4j/type/ExplosionType;"),
                        explosionType);
  }

  LOGGER("Reading weapon types... done");
}

void BridgeEnum::createUnitTypeEnum(JNIEnv *env, const JavaRefs &javaRefs) {
  LOGGER("Reading unit types...");

  for (BWAPI::UnitType unitType : BWAPI::UnitTypes::allUnitTypes()) {
    if (unitType.getName().empty()) {
      return;
    }

    jfieldID typeField = env->GetStaticFieldID(javaRefs.unitTypeClass, unitType.getName().c_str(), "Lorg/openbw/bwapi4j/type/UnitType;");
    jobject CurrentUnitType = env->GetStaticObjectField(javaRefs.unitTypeClass, typeField);

    // set int fields
    env->SetIntField(CurrentUnitType, env->GetFieldID(javaRefs.unitTypeClass, "id", "I"), unitType.getID());
    env->SetIntField(CurrentUnitType, env->GetFieldID(javaRefs.unitTypeClass, "maxHitPoints", "I"), unitType.maxHitPoints());
    env->SetIntField(CurrentUnitType, env->GetFieldID(javaRefs.unitTypeClass, "maxShields", "I"), unitType.maxShields());
    env->SetIntField(CurrentUnitType, env->GetFieldID(javaRefs.unitTypeClass, "maxEnergy", "I"), unitType.maxEnergy());
    env->SetIntField(CurrentUnitType, env->GetFieldID(javaRefs.unitTypeClass, "armor", "I"), unitType.armor());
    env->SetIntField(CurrentUnitType, env->GetFieldID(javaRefs.unitTypeClass, "mineralPrice", "I"), unitType.mineralPrice());
    env->SetIntField(CurrentUnitType, env->GetFieldID(javaRefs.unitTypeClass, "gasPrice", "I"), unitType.gasPrice());
    env->SetIntField(CurrentUnitType, env->GetFieldID(javaRefs.unitTypeClass, "buildTime", "I"), unitType.buildTime());
    env->SetIntField(CurrentUnitType, env->GetFieldID(javaRefs.unitTypeClass, "supplyRequired", "I"), unitType.supplyRequired());
    env->SetIntField(CurrentUnitType, env->GetFieldID(javaRefs.unitTypeClass, "supplyProvided", "I"), unitType.supplyProvided());
    env->SetIntField(CurrentUnitType, env->GetFieldID(javaRefs.unitTypeClass, "spaceRequired", "I"), unitType.spaceRequired());
    env->SetIntField(CurrentUnitType, env->GetFieldID(javaRefs.unitTypeClass, "spaceProvided", "I"), unitType.spaceProvided());
    env->SetIntField(CurrentUnitType, env->GetFieldID(javaRefs.unitTypeClass, "buildScore", "I"), unitType.buildScore());
    env->SetIntField(CurrentUnitType, env->GetFieldID(javaRefs.unitTypeClass, "destroyScore", "I"), unitType.destroyScore());
    env->SetIntField(CurrentUnitType, env->GetFieldID(javaRefs.unitTypeClass, "tileWidth", "I"), unitType.tileWidth());
    env->SetIntField(CurrentUnitType, env->GetFieldID(javaRefs.unitTypeClass, "tileHeight", "I"), unitType.tileHeight());
    env->SetIntField(CurrentUnitType, env->GetFieldID(javaRefs.unitTypeClass, "dimensionLeft", "I"), unitType.dimensionLeft());
    env->SetIntField(CurrentUnitType, env->GetFieldID(javaRefs.unitTypeClass, "dimensionUp", "I"), unitType.dimensionUp());
    env->SetIntField(CurrentUnitType, env->GetFieldID(javaRefs.unitTypeClass, "dimensionRight", "I"), unitType.dimensionRight());
    env->SetIntField(CurrentUnitType, env->GetFieldID(javaRefs.unitTypeClass, "dimensionDown", "I"), unitType.dimensionDown());
    env->SetIntField(CurrentUnitType, env->GetFieldID(javaRefs.unitTypeClass, "width", "I"), unitType.width());
    env->SetIntField(CurrentUnitType, env->GetFieldID(javaRefs.unitTypeClass, "height", "I"), unitType.height());
    env->SetIntField(CurrentUnitType, env->GetFieldID(javaRefs.unitTypeClass, "seekRange", "I"), unitType.seekRange());
    env->SetIntField(CurrentUnitType, env->GetFieldID(javaRefs.unitTypeClass, "sightRange", "I"), unitType.sightRange());
    env->SetIntField(CurrentUnitType, env->GetFieldID(javaRefs.unitTypeClass, "maxGroundHits", "I"), unitType.maxGroundHits());
    env->SetIntField(CurrentUnitType, env->GetFieldID(javaRefs.unitTypeClass, "maxAirHits", "I"), unitType.maxAirHits());
    env->SetIntField(CurrentUnitType, env->GetFieldID(javaRefs.unitTypeClass, "acceleration", "I"), unitType.acceleration());
    env->SetIntField(CurrentUnitType, env->GetFieldID(javaRefs.unitTypeClass, "haltDistance", "I"), unitType.haltDistance());
    env->SetIntField(CurrentUnitType, env->GetFieldID(javaRefs.unitTypeClass, "turnRadius", "I"), unitType.turnRadius());
    // set boolean fields
    env->SetBooleanField(CurrentUnitType, env->GetFieldID(javaRefs.unitTypeClass, "canProduce", "Z"), unitType.canProduce());
    env->SetBooleanField(CurrentUnitType, env->GetFieldID(javaRefs.unitTypeClass, "canAttack", "Z"), unitType.canAttack());
    env->SetBooleanField(CurrentUnitType, env->GetFieldID(javaRefs.unitTypeClass, "canMove", "Z"), unitType.canMove());
    env->SetBooleanField(CurrentUnitType, env->GetFieldID(javaRefs.unitTypeClass, "isFlyer", "Z"), unitType.isFlyer());
    env->SetBooleanField(CurrentUnitType, env->GetFieldID(javaRefs.unitTypeClass, "regeneratesHP", "Z"), unitType.regeneratesHP());
    env->SetBooleanField(CurrentUnitType, env->GetFieldID(javaRefs.unitTypeClass, "isSpellcaster", "Z"), unitType.isSpellcaster());
    env->SetBooleanField(CurrentUnitType, env->GetFieldID(javaRefs.unitTypeClass, "hasPermanentCloak", "Z"), unitType.hasPermanentCloak());
    env->SetBooleanField(CurrentUnitType, env->GetFieldID(javaRefs.unitTypeClass, "isInvincible", "Z"), unitType.isInvincible());
    env->SetBooleanField(CurrentUnitType, env->GetFieldID(javaRefs.unitTypeClass, "isOrganic", "Z"), unitType.isOrganic());
    env->SetBooleanField(CurrentUnitType, env->GetFieldID(javaRefs.unitTypeClass, "isMechanical", "Z"), unitType.isMechanical());
    env->SetBooleanField(CurrentUnitType, env->GetFieldID(javaRefs.unitTypeClass, "isRobotic", "Z"), unitType.isRobotic());
    env->SetBooleanField(CurrentUnitType, env->GetFieldID(javaRefs.unitTypeClass, "isMechanical", "Z"), unitType.isMechanical());
    env->SetBooleanField(CurrentUnitType, env->GetFieldID(javaRefs.unitTypeClass, "isDetector", "Z"), unitType.isDetector());
    env->SetBooleanField(CurrentUnitType, env->GetFieldID(javaRefs.unitTypeClass, "isResourceContainer", "Z"), unitType.isResourceContainer());
    env->SetBooleanField(CurrentUnitType, env->GetFieldID(javaRefs.unitTypeClass, "isResourceDepot", "Z"), unitType.isResourceDepot());
    env->SetBooleanField(CurrentUnitType, env->GetFieldID(javaRefs.unitTypeClass, "isRefinery", "Z"), unitType.isRefinery());
    env->SetBooleanField(CurrentUnitType, env->GetFieldID(javaRefs.unitTypeClass, "isWorker", "Z"), unitType.isWorker());
    env->SetBooleanField(CurrentUnitType, env->GetFieldID(javaRefs.unitTypeClass, "requiresPsi", "Z"), unitType.requiresPsi());
    env->SetBooleanField(CurrentUnitType, env->GetFieldID(javaRefs.unitTypeClass, "requiresCreep", "Z"), unitType.requiresCreep());
    env->SetBooleanField(CurrentUnitType, env->GetFieldID(javaRefs.unitTypeClass, "isTwoUnitsInOneEgg", "Z"), unitType.isTwoUnitsInOneEgg());
    env->SetBooleanField(CurrentUnitType, env->GetFieldID(javaRefs.unitTypeClass, "isBurrowable", "Z"), unitType.isBurrowable());
    env->SetBooleanField(CurrentUnitType, env->GetFieldID(javaRefs.unitTypeClass, "isCloakable", "Z"), unitType.isCloakable());
    env->SetBooleanField(CurrentUnitType, env->GetFieldID(javaRefs.unitTypeClass, "isBuilding", "Z"), unitType.isBuilding());
    env->SetBooleanField(CurrentUnitType, env->GetFieldID(javaRefs.unitTypeClass, "isAddon", "Z"), unitType.isAddon());
    env->SetBooleanField(CurrentUnitType, env->GetFieldID(javaRefs.unitTypeClass, "isFlyingBuilding", "Z"), unitType.isFlyingBuilding());
    env->SetBooleanField(CurrentUnitType, env->GetFieldID(javaRefs.unitTypeClass, "isNeutral", "Z"), unitType.isNeutral());
    env->SetBooleanField(CurrentUnitType, env->GetFieldID(javaRefs.unitTypeClass, "isHero", "Z"), unitType.isHero());
    env->SetBooleanField(CurrentUnitType, env->GetFieldID(javaRefs.unitTypeClass, "isPowerup", "Z"), unitType.isPowerup());
    env->SetBooleanField(CurrentUnitType, env->GetFieldID(javaRefs.unitTypeClass, "isBeacon", "Z"), unitType.isBeacon());
    env->SetBooleanField(CurrentUnitType, env->GetFieldID(javaRefs.unitTypeClass, "isFlagBeacon", "Z"), unitType.isFlagBeacon());
    env->SetBooleanField(CurrentUnitType, env->GetFieldID(javaRefs.unitTypeClass, "isSpecialBuilding", "Z"), unitType.isSpecialBuilding());
    env->SetBooleanField(CurrentUnitType, env->GetFieldID(javaRefs.unitTypeClass, "isSpell", "Z"), unitType.isSpell());
    env->SetBooleanField(CurrentUnitType, env->GetFieldID(javaRefs.unitTypeClass, "producesCreep", "Z"), unitType.producesCreep());
    env->SetBooleanField(CurrentUnitType, env->GetFieldID(javaRefs.unitTypeClass, "producesLarva", "Z"), unitType.producesLarva());
    env->SetBooleanField(CurrentUnitType, env->GetFieldID(javaRefs.unitTypeClass, "isMineralField", "Z"), unitType.isMineralField());
    env->SetBooleanField(CurrentUnitType, env->GetFieldID(javaRefs.unitTypeClass, "isCritter", "Z"), unitType.isCritter());
    env->SetBooleanField(CurrentUnitType, env->GetFieldID(javaRefs.unitTypeClass, "canBuildAddon", "Z"), unitType.canBuildAddon());
    // set double fields
    env->SetDoubleField(CurrentUnitType, env->GetFieldID(javaRefs.unitTypeClass, "topSpeed", "D"), unitType.topSpeed());
    // set enum values
    jobject race = env->GetStaticObjectField(javaRefs.raceClass,
                                             env->GetStaticFieldID(javaRefs.raceClass, unitType.getRace().getName().c_str(), "Lorg/openbw/bwapi4j/type/Race;"));
    env->SetObjectField(CurrentUnitType, env->GetFieldID(javaRefs.unitTypeClass, "race", "Lorg/openbw/bwapi4j/type/Race;"), race);

    jobject requiredTech = env->GetStaticObjectField(
        javaRefs.techTypeClass, env->GetStaticFieldID(javaRefs.techTypeClass, unitType.requiredTech().getName().c_str(), "Lorg/openbw/bwapi4j/type/TechType;"));
    env->SetObjectField(CurrentUnitType, env->GetFieldID(javaRefs.unitTypeClass, "requiredTech", "Lorg/openbw/bwapi4j/type/TechType;"), requiredTech);

    jobject cloakingTech = env->GetStaticObjectField(
        javaRefs.techTypeClass, env->GetStaticFieldID(javaRefs.techTypeClass, unitType.cloakingTech().getName().c_str(), "Lorg/openbw/bwapi4j/type/TechType;"));
    env->SetObjectField(CurrentUnitType, env->GetFieldID(javaRefs.unitTypeClass, "cloakingTech", "Lorg/openbw/bwapi4j/type/TechType;"), cloakingTech);

    jobject armorUpgrade = env->GetStaticObjectField(
        javaRefs.upgradeTypeClass,
        env->GetStaticFieldID(javaRefs.upgradeTypeClass, unitType.armorUpgrade().getName().c_str(), "Lorg/openbw/bwapi4j/type/UpgradeType;"));
    env->SetObjectField(CurrentUnitType, env->GetFieldID(javaRefs.unitTypeClass, "armorUpgrade", "Lorg/openbw/bwapi4j/type/UpgradeType;"), armorUpgrade);

    jobject size = env->GetStaticObjectField(javaRefs.unitSizeTypeClass, env->GetStaticFieldID(javaRefs.unitSizeTypeClass, unitType.size().getName().c_str(),
                                                                                               "Lorg/openbw/bwapi4j/type/UnitSizeType;"));
    env->SetObjectField(CurrentUnitType, env->GetFieldID(javaRefs.unitTypeClass, "size", "Lorg/openbw/bwapi4j/type/UnitSizeType;"), size);

    jobject groundWeapon = env->GetStaticObjectField(
        javaRefs.weaponTypeClass,
        env->GetStaticFieldID(javaRefs.weaponTypeClass, unitType.groundWeapon().getName().c_str(), "Lorg/openbw/bwapi4j/type/WeaponType;"));
    env->SetObjectField(CurrentUnitType, env->GetFieldID(javaRefs.unitTypeClass, "groundWeapon", "Lorg/openbw/bwapi4j/type/WeaponType;"), groundWeapon);

    jobject airWeapon = env->GetStaticObjectField(
        javaRefs.weaponTypeClass,
        env->GetStaticFieldID(javaRefs.weaponTypeClass, unitType.airWeapon().getName().c_str(), "Lorg/openbw/bwapi4j/type/WeaponType;"));
    env->SetObjectField(CurrentUnitType, env->GetFieldID(javaRefs.unitTypeClass, "airWeapon", "Lorg/openbw/bwapi4j/type/WeaponType;"), airWeapon);

    // set complex values
    jobject upgradesList = env->GetObjectField(CurrentUnitType, env->GetFieldID(javaRefs.unitTypeClass, "upgrades", "Ljava/util/List;"));
    for (BWAPI::UpgradeType upgradeType : unitType.upgrades()) {
      jobject upgradesMemberType = env->GetStaticObjectField(
          javaRefs.upgradeTypeClass, env->GetStaticFieldID(javaRefs.upgradeTypeClass, upgradeType.getName().c_str(), "Lorg/openbw/bwapi4j/type/UpgradeType;"));
      env->CallObjectMethod(upgradesList, javaRefs.listClass_add, upgradesMemberType);
      env->DeleteLocalRef(upgradesMemberType);
    }
    env->DeleteLocalRef(upgradesList);

    jobject upgradesWhatList = env->GetObjectField(CurrentUnitType, env->GetFieldID(javaRefs.unitTypeClass, "upgradesWhat", "Ljava/util/List;"));
    for (BWAPI::UpgradeType upgradeType : unitType.upgradesWhat()) {
      jobject upgradesWhatMemberType = env->GetStaticObjectField(
          javaRefs.upgradeTypeClass, env->GetStaticFieldID(javaRefs.upgradeTypeClass, upgradeType.getName().c_str(), "Lorg/openbw/bwapi4j/type/UpgradeType;"));
      env->CallObjectMethod(upgradesWhatList, javaRefs.listClass_add, upgradesWhatMemberType);
      env->DeleteLocalRef(upgradesWhatMemberType);
    }
    env->DeleteLocalRef(upgradesWhatList);

    jobject abilitiesList = env->GetObjectField(CurrentUnitType, env->GetFieldID(javaRefs.unitTypeClass, "abilities", "Ljava/util/List;"));
    for (BWAPI::TechType techType : unitType.abilities()) {
      jobject abilitiesMemberType = env->GetStaticObjectField(
          javaRefs.techTypeClass, env->GetStaticFieldID(javaRefs.techTypeClass, techType.getName().c_str(), "Lorg/openbw/bwapi4j/type/TechType;"));
      env->CallObjectMethod(abilitiesList, javaRefs.listClass_add, abilitiesMemberType);
      env->DeleteLocalRef(abilitiesMemberType);
    }
    env->DeleteLocalRef(abilitiesList);

    jobject researchesWhatList = env->GetObjectField(CurrentUnitType, env->GetFieldID(javaRefs.unitTypeClass, "researchesWhat", "Ljava/util/List;"));
    for (BWAPI::TechType techType : unitType.researchesWhat()) {
      jobject researchesWhatMemberType = env->GetStaticObjectField(
          javaRefs.techTypeClass, env->GetStaticFieldID(javaRefs.techTypeClass, techType.getName().c_str(), "Lorg/openbw/bwapi4j/type/TechType;"));
      env->CallObjectMethod(researchesWhatList, javaRefs.listClass_add, researchesWhatMemberType);
      env->DeleteLocalRef(researchesWhatMemberType);
    }
    env->DeleteLocalRef(researchesWhatList);

    jobject buildsWhatList = env->GetObjectField(CurrentUnitType, env->GetFieldID(javaRefs.unitTypeClass, "buildsWhat", "Ljava/util/List;"));
    for (BWAPI::UnitType unitType : unitType.buildsWhat()) {
      jobject buildsWhatMemberType = env->GetStaticObjectField(
          javaRefs.unitTypeClass, env->GetStaticFieldID(javaRefs.unitTypeClass, unitType.getName().c_str(), "Lorg/openbw/bwapi4j/type/UnitType;"));
      env->CallObjectMethod(buildsWhatList, javaRefs.listClass_add, buildsWhatMemberType);
      env->DeleteLocalRef(buildsWhatMemberType);
    }
    env->DeleteLocalRef(buildsWhatList);

    // create a new Pair object and fill in UnitType,Integer
    jfieldID whatBuildsField =
        env->GetStaticFieldID(javaRefs.unitTypeClass, unitType.whatBuilds().first.getName().c_str(), "Lorg/openbw/bwapi4j/type/UnitType;");
    jobject whatBuildsType = env->GetStaticObjectField(javaRefs.unitTypeClass, whatBuildsField);

    jobject pairObject = env->NewObject(javaRefs.pairClass, javaRefs.pairClassConstructor, whatBuildsType,
                                        env->NewObject(javaRefs.integerClass, javaRefs.integerClassConstructor, unitType.whatBuilds().second));
    env->SetObjectField(CurrentUnitType, env->GetFieldID(javaRefs.unitTypeClass, "whatBuilds", "Lorg/openbw/bwapi4j/util/Pair;"), pairObject);

    // read existing requiredUnits map and put <UnitType,Integer> entries
    for (auto const &req : unitType.requiredUnits()) {
      env->CallObjectMethod(CurrentUnitType, javaRefs.unitTypeClass_addRequiredUnit, (jint)req.first.getID(), (jint)req.second);
    }
    env->DeleteLocalRef(CurrentUnitType);
  }

  LOGGER("Reading unit types... done");
}
