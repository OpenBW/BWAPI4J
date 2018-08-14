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
#include <BWAPI/Client.h>
#include "Bridge.h"
#include "Logger.h"

void BridgeEnum::initialize() {
  createUpgradeTypeEnum();
  createTechTypeEnum();
  createWeaponTypeEnum();
  createUnitTypeEnum();
}

void BridgeEnum::createUpgradeTypeEnum() {
  LOGGER("Reading upgrade types...");

  for (BWAPI::UpgradeType upgradeType : BWAPI::UpgradeTypes::allUpgradeTypes()) {
    if (upgradeType.getName().empty()) {
      return;
    }
    jfieldID upgradeTypeField = globalEnv->GetStaticFieldID(javaRefs.upgradeTypeClass, upgradeType.getName().c_str(), "Lorg/openbw/bwapi4j/type/UpgradeType;");
    jobject CurrentUpgradeType = globalEnv->GetStaticObjectField(javaRefs.upgradeTypeClass, upgradeTypeField);

    // set int fields
    globalEnv->SetIntField(CurrentUpgradeType, globalEnv->GetFieldID(javaRefs.upgradeTypeClass, "id", "I"), upgradeType.getID());
    globalEnv->SetIntField(CurrentUpgradeType, globalEnv->GetFieldID(javaRefs.upgradeTypeClass, "mineralPriceFactor", "I"), upgradeType.mineralPriceFactor());
    globalEnv->SetIntField(CurrentUpgradeType, globalEnv->GetFieldID(javaRefs.upgradeTypeClass, "gasPriceFactor", "I"), upgradeType.gasPriceFactor());
    globalEnv->SetIntField(CurrentUpgradeType, globalEnv->GetFieldID(javaRefs.upgradeTypeClass, "upgradeTimeFactor", "I"), upgradeType.upgradeTimeFactor());
    globalEnv->SetIntField(CurrentUpgradeType, globalEnv->GetFieldID(javaRefs.upgradeTypeClass, "maxRepeats", "I"), upgradeType.maxRepeats());
    // set int[] fields
    jint *gasPrices = new jint[upgradeType.maxRepeats()];
    jint *mineralPrices = new jint[upgradeType.maxRepeats()];
    jint *upgradeTimes = new jint[upgradeType.maxRepeats()];
    jobject *whatsRequired = new jobject[upgradeType.maxRepeats()];

    for (int i = 0; i < upgradeType.maxRepeats(); i++) {
      gasPrices[i] = upgradeType.gasPrice(i + 1);
      mineralPrices[i] = upgradeType.mineralPrice(i + 1);
      upgradeTimes[i] = upgradeType.upgradeTime(i + 1);
      whatsRequired[i] = globalEnv->GetStaticObjectField(
          javaRefs.unitTypeClass,
          globalEnv->GetStaticFieldID(javaRefs.unitTypeClass, upgradeType.whatsRequired(i + 1).getName().c_str(), "Lorg/openbw/bwapi4j/type/UnitType;"));
    }

    jintArray gasPricesArray = globalEnv->NewIntArray(upgradeType.maxRepeats());
    globalEnv->SetIntArrayRegion(gasPricesArray, 0, upgradeType.maxRepeats(), gasPrices);
    globalEnv->SetObjectField(CurrentUpgradeType, globalEnv->GetFieldID(javaRefs.upgradeTypeClass, "gasPrices", "[I"), gasPricesArray);

    jintArray mineralPricesArray = globalEnv->NewIntArray(upgradeType.maxRepeats());
    globalEnv->SetIntArrayRegion(mineralPricesArray, 0, upgradeType.maxRepeats(), mineralPrices);
    globalEnv->SetObjectField(CurrentUpgradeType, globalEnv->GetFieldID(javaRefs.upgradeTypeClass, "mineralPrices", "[I"), mineralPricesArray);

    jintArray upgradeTimesArray = globalEnv->NewIntArray(upgradeType.maxRepeats());
    globalEnv->SetIntArrayRegion(upgradeTimesArray, 0, upgradeType.maxRepeats(), upgradeTimes);
    globalEnv->SetObjectField(CurrentUpgradeType, globalEnv->GetFieldID(javaRefs.upgradeTypeClass, "upgradeTimes", "[I"), upgradeTimesArray);

    jobjectArray objectArray = globalEnv->NewObjectArray(upgradeType.maxRepeats(), javaRefs.unitTypeClass, NULL);
    globalEnv->SetObjectField(CurrentUpgradeType, globalEnv->GetFieldID(javaRefs.upgradeTypeClass, "whatsRequired", "[Lorg/openbw/bwapi4j/type/UnitType;"),
                              objectArray);
    for (int i = 0; i < upgradeType.maxRepeats(); i++) {
      globalEnv->SetObjectArrayElement(objectArray, i, whatsRequired[i]);
    }

    // read existing whatUses set and put UnitType entries
    for (auto const &use : upgradeType.whatUses()) {
      globalEnv->CallObjectMethod(CurrentUpgradeType, javaRefs.upgradeTypeClass_addUsingUnit, (jint)use.getID());
    }

    // set enum fields
    jobject race = globalEnv->GetStaticObjectField(
        javaRefs.raceClass, globalEnv->GetStaticFieldID(javaRefs.raceClass, upgradeType.getRace().c_str(), "Lorg/openbw/bwapi4j/type/Race;"));
    globalEnv->SetObjectField(CurrentUpgradeType, globalEnv->GetFieldID(javaRefs.upgradeTypeClass, "race", "Lorg/openbw/bwapi4j/type/Race;"), race);

    jobject whatUpgrades = globalEnv->GetStaticObjectField(
        javaRefs.unitTypeClass, globalEnv->GetStaticFieldID(javaRefs.unitTypeClass, upgradeType.whatUpgrades().c_str(), "Lorg/openbw/bwapi4j/type/UnitType;"));
    globalEnv->SetObjectField(CurrentUpgradeType, globalEnv->GetFieldID(javaRefs.upgradeTypeClass, "whatUpgrades", "Lorg/openbw/bwapi4j/type/UnitType;"),
                              whatUpgrades);
  }

  LOGGER("Reading upgrade types... done");
}

void BridgeEnum::createTechTypeEnum() {
  LOGGER("Reading tech types...");

  for (BWAPI::TechType techType : BWAPI::TechTypes::allTechTypes()) {
    if (techType.getName().empty()) {
      return;
    }
    jfieldID techTypeField = globalEnv->GetStaticFieldID(javaRefs.techTypeClass, techType.getName().c_str(), "Lorg/openbw/bwapi4j/type/TechType;");
    jobject CurrentTechType = globalEnv->GetStaticObjectField(javaRefs.techTypeClass, techTypeField);

    // set int fields
    globalEnv->SetIntField(CurrentTechType, globalEnv->GetFieldID(javaRefs.techTypeClass, "id", "I"), techType.getID());
    globalEnv->SetIntField(CurrentTechType, globalEnv->GetFieldID(javaRefs.techTypeClass, "mineralPrice", "I"), techType.mineralPrice());
    globalEnv->SetIntField(CurrentTechType, globalEnv->GetFieldID(javaRefs.techTypeClass, "gasPrice", "I"), techType.gasPrice());
    globalEnv->SetIntField(CurrentTechType, globalEnv->GetFieldID(javaRefs.techTypeClass, "researchTime", "I"), techType.researchTime());
    globalEnv->SetIntField(CurrentTechType, globalEnv->GetFieldID(javaRefs.techTypeClass, "energyCost", "I"), techType.energyCost());
    // set boolean fields
    globalEnv->SetBooleanField(CurrentTechType, globalEnv->GetFieldID(javaRefs.techTypeClass, "targetsUnit", "Z"), techType.targetsUnit());
    globalEnv->SetBooleanField(CurrentTechType, globalEnv->GetFieldID(javaRefs.techTypeClass, "targetsPosition", "Z"), techType.targetsPosition());
    // set enum fields
    jobject race = globalEnv->GetStaticObjectField(
        javaRefs.raceClass, globalEnv->GetStaticFieldID(javaRefs.raceClass, techType.getRace().getName().c_str(), "Lorg/openbw/bwapi4j/type/Race;"));
    globalEnv->SetObjectField(CurrentTechType, globalEnv->GetFieldID(javaRefs.techTypeClass, "race", "Lorg/openbw/bwapi4j/type/Race;"), race);

    jobject weaponType = globalEnv->GetStaticObjectField(
        javaRefs.weaponTypeClass,
        globalEnv->GetStaticFieldID(javaRefs.weaponTypeClass, techType.getWeapon().getName().c_str(), "Lorg/openbw/bwapi4j/type/WeaponType;"));
    globalEnv->SetObjectField(CurrentTechType, globalEnv->GetFieldID(javaRefs.techTypeClass, "weaponType", "Lorg/openbw/bwapi4j/type/WeaponType;"), weaponType);

    jobject whatResearches = globalEnv->GetStaticObjectField(
        javaRefs.unitTypeClass,
        globalEnv->GetStaticFieldID(javaRefs.unitTypeClass, techType.whatResearches().getName().c_str(), "Lorg/openbw/bwapi4j/type/UnitType;"));
    globalEnv->SetObjectField(CurrentTechType, globalEnv->GetFieldID(javaRefs.techTypeClass, "whatResearches", "Lorg/openbw/bwapi4j/type/UnitType;"),
                              whatResearches);

    jobject order = globalEnv->GetStaticObjectField(
        javaRefs.orderClass, globalEnv->GetStaticFieldID(javaRefs.orderClass, techType.getOrder().getName().c_str(), "Lorg/openbw/bwapi4j/type/Order;"));
    globalEnv->SetObjectField(CurrentTechType, globalEnv->GetFieldID(javaRefs.techTypeClass, "order", "Lorg/openbw/bwapi4j/type/Order;"), order);

    jobject requiredUnit = globalEnv->GetStaticObjectField(
        javaRefs.unitTypeClass,
        globalEnv->GetStaticFieldID(javaRefs.unitTypeClass, techType.requiredUnit().getName().c_str(), "Lorg/openbw/bwapi4j/type/UnitType;"));
    globalEnv->SetObjectField(CurrentTechType, globalEnv->GetFieldID(javaRefs.techTypeClass, "requiredUnit", "Lorg/openbw/bwapi4j/type/UnitType;"),
                              requiredUnit);
  }

  LOGGER("Reading tech types... done");
}

void BridgeEnum::createWeaponTypeEnum() {
  LOGGER("Reading weapon types...");

  for (BWAPI::WeaponType weaponType : BWAPI::WeaponTypes::allWeaponTypes()) {
    if (weaponType.getName().empty()) {
      return;
    }
    jfieldID typeField = globalEnv->GetStaticFieldID(javaRefs.weaponTypeClass, weaponType.getName().c_str(), "Lorg/openbw/bwapi4j/type/WeaponType;");
    jobject CurrentWeaponType = globalEnv->GetStaticObjectField(javaRefs.weaponTypeClass, typeField);

    // set int fields
    globalEnv->SetIntField(CurrentWeaponType, globalEnv->GetFieldID(javaRefs.weaponTypeClass, "id", "I"), weaponType.getID());
    globalEnv->SetIntField(CurrentWeaponType, globalEnv->GetFieldID(javaRefs.weaponTypeClass, "damageAmount", "I"), weaponType.damageAmount());
    globalEnv->SetIntField(CurrentWeaponType, globalEnv->GetFieldID(javaRefs.weaponTypeClass, "damageBonus", "I"), weaponType.damageBonus());
    globalEnv->SetIntField(CurrentWeaponType, globalEnv->GetFieldID(javaRefs.weaponTypeClass, "damageCooldown", "I"), weaponType.damageCooldown());
    globalEnv->SetIntField(CurrentWeaponType, globalEnv->GetFieldID(javaRefs.weaponTypeClass, "damageFactor", "I"), weaponType.damageFactor());
    globalEnv->SetIntField(CurrentWeaponType, globalEnv->GetFieldID(javaRefs.weaponTypeClass, "minRange", "I"), weaponType.minRange());
    globalEnv->SetIntField(CurrentWeaponType, globalEnv->GetFieldID(javaRefs.weaponTypeClass, "maxRange", "I"), weaponType.maxRange());
    globalEnv->SetIntField(CurrentWeaponType, globalEnv->GetFieldID(javaRefs.weaponTypeClass, "innerSplashRadius", "I"), weaponType.innerSplashRadius());
    globalEnv->SetIntField(CurrentWeaponType, globalEnv->GetFieldID(javaRefs.weaponTypeClass, "medianSplashRadius", "I"), weaponType.medianSplashRadius());
    globalEnv->SetIntField(CurrentWeaponType, globalEnv->GetFieldID(javaRefs.weaponTypeClass, "outerSplashRadius", "I"), weaponType.outerSplashRadius());
    // set boolean fields
    globalEnv->SetBooleanField(CurrentWeaponType, globalEnv->GetFieldID(javaRefs.weaponTypeClass, "targetsAir", "Z"), weaponType.targetsAir());
    globalEnv->SetBooleanField(CurrentWeaponType, globalEnv->GetFieldID(javaRefs.weaponTypeClass, "targetsGround", "Z"), weaponType.targetsGround());
    globalEnv->SetBooleanField(CurrentWeaponType, globalEnv->GetFieldID(javaRefs.weaponTypeClass, "targetsMechanical", "Z"), weaponType.targetsMechanical());
    globalEnv->SetBooleanField(CurrentWeaponType, globalEnv->GetFieldID(javaRefs.weaponTypeClass, "targetsOrganic", "Z"), weaponType.targetsOrganic());
    globalEnv->SetBooleanField(CurrentWeaponType, globalEnv->GetFieldID(javaRefs.weaponTypeClass, "targetsNonBuilding", "Z"), weaponType.targetsNonBuilding());
    globalEnv->SetBooleanField(CurrentWeaponType, globalEnv->GetFieldID(javaRefs.weaponTypeClass, "targetsNonRobotic", "Z"), weaponType.targetsNonRobotic());
    globalEnv->SetBooleanField(CurrentWeaponType, globalEnv->GetFieldID(javaRefs.weaponTypeClass, "targetsTerrain", "Z"), weaponType.targetsTerrain());
    globalEnv->SetBooleanField(CurrentWeaponType, globalEnv->GetFieldID(javaRefs.weaponTypeClass, "targetsOrgOrMech", "Z"), weaponType.targetsOrgOrMech());
    globalEnv->SetBooleanField(CurrentWeaponType, globalEnv->GetFieldID(javaRefs.weaponTypeClass, "targetsOwn", "Z"), weaponType.targetsOwn());

    // set enum fields
    jobject tech = globalEnv->GetStaticObjectField(
        javaRefs.techTypeClass,
        globalEnv->GetStaticFieldID(javaRefs.techTypeClass, weaponType.getTech().getName().c_str(), "Lorg/openbw/bwapi4j/type/TechType;"));
    globalEnv->SetObjectField(CurrentWeaponType, globalEnv->GetFieldID(javaRefs.weaponTypeClass, "tech", "Lorg/openbw/bwapi4j/type/TechType;"), tech);

    jobject whatUses = globalEnv->GetStaticObjectField(
        javaRefs.unitTypeClass,
        globalEnv->GetStaticFieldID(javaRefs.unitTypeClass, weaponType.whatUses().getName().c_str(), "Lorg/openbw/bwapi4j/type/UnitType;"));
    globalEnv->SetObjectField(CurrentWeaponType, globalEnv->GetFieldID(javaRefs.weaponTypeClass, "whatUses", "Lorg/openbw/bwapi4j/type/UnitType;"), whatUses);

    jobject upgradeType = globalEnv->GetStaticObjectField(
        javaRefs.upgradeTypeClass,
        globalEnv->GetStaticFieldID(javaRefs.upgradeTypeClass, weaponType.upgradeType().getName().c_str(), "Lorg/openbw/bwapi4j/type/UpgradeType;"));
    globalEnv->SetObjectField(CurrentWeaponType, globalEnv->GetFieldID(javaRefs.weaponTypeClass, "upgradeType", "Lorg/openbw/bwapi4j/type/UpgradeType;"),
                              upgradeType);

    jobject damageType = globalEnv->GetStaticObjectField(
        javaRefs.damageTypeClass,
        globalEnv->GetStaticFieldID(javaRefs.damageTypeClass, weaponType.damageType().getName().c_str(), "Lorg/openbw/bwapi4j/type/DamageType;"));
    globalEnv->SetObjectField(CurrentWeaponType, globalEnv->GetFieldID(javaRefs.weaponTypeClass, "damageType", "Lorg/openbw/bwapi4j/type/DamageType;"),
                              damageType);

    jobject explosionType = globalEnv->GetStaticObjectField(
        javaRefs.explosionTypeClass,
        globalEnv->GetStaticFieldID(javaRefs.explosionTypeClass, weaponType.explosionType().getName().c_str(), "Lorg/openbw/bwapi4j/type/ExplosionType;"));
    globalEnv->SetObjectField(CurrentWeaponType, globalEnv->GetFieldID(javaRefs.weaponTypeClass, "explosionType", "Lorg/openbw/bwapi4j/type/ExplosionType;"),
                              explosionType);
  }

  LOGGER("Reading weapon types... done");
}

void BridgeEnum::createUnitTypeEnum() {
  LOGGER("Reading unit types...");

  for (BWAPI::UnitType unitType : BWAPI::UnitTypes::allUnitTypes()) {
    if (unitType.getName().empty()) {
      return;
    }

    jfieldID typeField = globalEnv->GetStaticFieldID(javaRefs.unitTypeClass, unitType.getName().c_str(), "Lorg/openbw/bwapi4j/type/UnitType;");
    jobject CurrentUnitType = globalEnv->GetStaticObjectField(javaRefs.unitTypeClass, typeField);

    // set int fields
    globalEnv->SetIntField(CurrentUnitType, globalEnv->GetFieldID(javaRefs.unitTypeClass, "id", "I"), unitType.getID());
    globalEnv->SetIntField(CurrentUnitType, globalEnv->GetFieldID(javaRefs.unitTypeClass, "maxHitPoints", "I"), unitType.maxHitPoints());
    globalEnv->SetIntField(CurrentUnitType, globalEnv->GetFieldID(javaRefs.unitTypeClass, "maxShields", "I"), unitType.maxShields());
    globalEnv->SetIntField(CurrentUnitType, globalEnv->GetFieldID(javaRefs.unitTypeClass, "maxEnergy", "I"), unitType.maxEnergy());
    globalEnv->SetIntField(CurrentUnitType, globalEnv->GetFieldID(javaRefs.unitTypeClass, "armor", "I"), unitType.armor());
    globalEnv->SetIntField(CurrentUnitType, globalEnv->GetFieldID(javaRefs.unitTypeClass, "mineralPrice", "I"), unitType.mineralPrice());
    globalEnv->SetIntField(CurrentUnitType, globalEnv->GetFieldID(javaRefs.unitTypeClass, "gasPrice", "I"), unitType.gasPrice());
    globalEnv->SetIntField(CurrentUnitType, globalEnv->GetFieldID(javaRefs.unitTypeClass, "buildTime", "I"), unitType.buildTime());
    globalEnv->SetIntField(CurrentUnitType, globalEnv->GetFieldID(javaRefs.unitTypeClass, "supplyRequired", "I"), unitType.supplyRequired());
    globalEnv->SetIntField(CurrentUnitType, globalEnv->GetFieldID(javaRefs.unitTypeClass, "supplyProvided", "I"), unitType.supplyProvided());
    globalEnv->SetIntField(CurrentUnitType, globalEnv->GetFieldID(javaRefs.unitTypeClass, "spaceRequired", "I"), unitType.spaceRequired());
    globalEnv->SetIntField(CurrentUnitType, globalEnv->GetFieldID(javaRefs.unitTypeClass, "spaceProvided", "I"), unitType.spaceProvided());
    globalEnv->SetIntField(CurrentUnitType, globalEnv->GetFieldID(javaRefs.unitTypeClass, "buildScore", "I"), unitType.buildScore());
    globalEnv->SetIntField(CurrentUnitType, globalEnv->GetFieldID(javaRefs.unitTypeClass, "destroyScore", "I"), unitType.destroyScore());
    globalEnv->SetIntField(CurrentUnitType, globalEnv->GetFieldID(javaRefs.unitTypeClass, "tileWidth", "I"), unitType.tileWidth());
    globalEnv->SetIntField(CurrentUnitType, globalEnv->GetFieldID(javaRefs.unitTypeClass, "tileHeight", "I"), unitType.tileHeight());
    globalEnv->SetIntField(CurrentUnitType, globalEnv->GetFieldID(javaRefs.unitTypeClass, "dimensionLeft", "I"), unitType.dimensionLeft());
    globalEnv->SetIntField(CurrentUnitType, globalEnv->GetFieldID(javaRefs.unitTypeClass, "dimensionUp", "I"), unitType.dimensionUp());
    globalEnv->SetIntField(CurrentUnitType, globalEnv->GetFieldID(javaRefs.unitTypeClass, "dimensionRight", "I"), unitType.dimensionRight());
    globalEnv->SetIntField(CurrentUnitType, globalEnv->GetFieldID(javaRefs.unitTypeClass, "dimensionDown", "I"), unitType.dimensionDown());
    globalEnv->SetIntField(CurrentUnitType, globalEnv->GetFieldID(javaRefs.unitTypeClass, "width", "I"), unitType.width());
    globalEnv->SetIntField(CurrentUnitType, globalEnv->GetFieldID(javaRefs.unitTypeClass, "height", "I"), unitType.height());
    globalEnv->SetIntField(CurrentUnitType, globalEnv->GetFieldID(javaRefs.unitTypeClass, "seekRange", "I"), unitType.seekRange());
    globalEnv->SetIntField(CurrentUnitType, globalEnv->GetFieldID(javaRefs.unitTypeClass, "sightRange", "I"), unitType.sightRange());
    globalEnv->SetIntField(CurrentUnitType, globalEnv->GetFieldID(javaRefs.unitTypeClass, "maxGroundHits", "I"), unitType.maxGroundHits());
    globalEnv->SetIntField(CurrentUnitType, globalEnv->GetFieldID(javaRefs.unitTypeClass, "maxAirHits", "I"), unitType.maxAirHits());
    globalEnv->SetIntField(CurrentUnitType, globalEnv->GetFieldID(javaRefs.unitTypeClass, "acceleration", "I"), unitType.acceleration());
    globalEnv->SetIntField(CurrentUnitType, globalEnv->GetFieldID(javaRefs.unitTypeClass, "haltDistance", "I"), unitType.haltDistance());
    globalEnv->SetIntField(CurrentUnitType, globalEnv->GetFieldID(javaRefs.unitTypeClass, "turnRadius", "I"), unitType.turnRadius());
    // set boolean fields
    globalEnv->SetBooleanField(CurrentUnitType, globalEnv->GetFieldID(javaRefs.unitTypeClass, "canProduce", "Z"), unitType.canProduce());
    globalEnv->SetBooleanField(CurrentUnitType, globalEnv->GetFieldID(javaRefs.unitTypeClass, "canAttack", "Z"), unitType.canAttack());
    globalEnv->SetBooleanField(CurrentUnitType, globalEnv->GetFieldID(javaRefs.unitTypeClass, "canMove", "Z"), unitType.canMove());
    globalEnv->SetBooleanField(CurrentUnitType, globalEnv->GetFieldID(javaRefs.unitTypeClass, "isFlyer", "Z"), unitType.isFlyer());
    globalEnv->SetBooleanField(CurrentUnitType, globalEnv->GetFieldID(javaRefs.unitTypeClass, "regeneratesHP", "Z"), unitType.regeneratesHP());
    globalEnv->SetBooleanField(CurrentUnitType, globalEnv->GetFieldID(javaRefs.unitTypeClass, "isSpellcaster", "Z"), unitType.isSpellcaster());
    globalEnv->SetBooleanField(CurrentUnitType, globalEnv->GetFieldID(javaRefs.unitTypeClass, "hasPermanentCloak", "Z"), unitType.hasPermanentCloak());
    globalEnv->SetBooleanField(CurrentUnitType, globalEnv->GetFieldID(javaRefs.unitTypeClass, "isInvincible", "Z"), unitType.isInvincible());
    globalEnv->SetBooleanField(CurrentUnitType, globalEnv->GetFieldID(javaRefs.unitTypeClass, "isOrganic", "Z"), unitType.isOrganic());
    globalEnv->SetBooleanField(CurrentUnitType, globalEnv->GetFieldID(javaRefs.unitTypeClass, "isMechanical", "Z"), unitType.isMechanical());
    globalEnv->SetBooleanField(CurrentUnitType, globalEnv->GetFieldID(javaRefs.unitTypeClass, "isRobotic", "Z"), unitType.isRobotic());
    globalEnv->SetBooleanField(CurrentUnitType, globalEnv->GetFieldID(javaRefs.unitTypeClass, "isMechanical", "Z"), unitType.isMechanical());
    globalEnv->SetBooleanField(CurrentUnitType, globalEnv->GetFieldID(javaRefs.unitTypeClass, "isDetector", "Z"), unitType.isDetector());
    globalEnv->SetBooleanField(CurrentUnitType, globalEnv->GetFieldID(javaRefs.unitTypeClass, "isResourceContainer", "Z"), unitType.isResourceContainer());
    globalEnv->SetBooleanField(CurrentUnitType, globalEnv->GetFieldID(javaRefs.unitTypeClass, "isResourceDepot", "Z"), unitType.isResourceDepot());
    globalEnv->SetBooleanField(CurrentUnitType, globalEnv->GetFieldID(javaRefs.unitTypeClass, "isRefinery", "Z"), unitType.isRefinery());
    globalEnv->SetBooleanField(CurrentUnitType, globalEnv->GetFieldID(javaRefs.unitTypeClass, "isWorker", "Z"), unitType.isWorker());
    globalEnv->SetBooleanField(CurrentUnitType, globalEnv->GetFieldID(javaRefs.unitTypeClass, "requiresPsi", "Z"), unitType.requiresPsi());
    globalEnv->SetBooleanField(CurrentUnitType, globalEnv->GetFieldID(javaRefs.unitTypeClass, "requiresCreep", "Z"), unitType.requiresCreep());
    globalEnv->SetBooleanField(CurrentUnitType, globalEnv->GetFieldID(javaRefs.unitTypeClass, "isTwoUnitsInOneEgg", "Z"), unitType.isTwoUnitsInOneEgg());
    globalEnv->SetBooleanField(CurrentUnitType, globalEnv->GetFieldID(javaRefs.unitTypeClass, "isBurrowable", "Z"), unitType.isBurrowable());
    globalEnv->SetBooleanField(CurrentUnitType, globalEnv->GetFieldID(javaRefs.unitTypeClass, "isCloakable", "Z"), unitType.isCloakable());
    globalEnv->SetBooleanField(CurrentUnitType, globalEnv->GetFieldID(javaRefs.unitTypeClass, "isBuilding", "Z"), unitType.isBuilding());
    globalEnv->SetBooleanField(CurrentUnitType, globalEnv->GetFieldID(javaRefs.unitTypeClass, "isAddon", "Z"), unitType.isAddon());
    globalEnv->SetBooleanField(CurrentUnitType, globalEnv->GetFieldID(javaRefs.unitTypeClass, "isFlyingBuilding", "Z"), unitType.isFlyingBuilding());
    globalEnv->SetBooleanField(CurrentUnitType, globalEnv->GetFieldID(javaRefs.unitTypeClass, "isNeutral", "Z"), unitType.isNeutral());
    globalEnv->SetBooleanField(CurrentUnitType, globalEnv->GetFieldID(javaRefs.unitTypeClass, "isHero", "Z"), unitType.isHero());
    globalEnv->SetBooleanField(CurrentUnitType, globalEnv->GetFieldID(javaRefs.unitTypeClass, "isPowerup", "Z"), unitType.isPowerup());
    globalEnv->SetBooleanField(CurrentUnitType, globalEnv->GetFieldID(javaRefs.unitTypeClass, "isBeacon", "Z"), unitType.isBeacon());
    globalEnv->SetBooleanField(CurrentUnitType, globalEnv->GetFieldID(javaRefs.unitTypeClass, "isFlagBeacon", "Z"), unitType.isFlagBeacon());
    globalEnv->SetBooleanField(CurrentUnitType, globalEnv->GetFieldID(javaRefs.unitTypeClass, "isSpecialBuilding", "Z"), unitType.isSpecialBuilding());
    globalEnv->SetBooleanField(CurrentUnitType, globalEnv->GetFieldID(javaRefs.unitTypeClass, "isSpell", "Z"), unitType.isSpell());
    globalEnv->SetBooleanField(CurrentUnitType, globalEnv->GetFieldID(javaRefs.unitTypeClass, "producesCreep", "Z"), unitType.producesCreep());
    globalEnv->SetBooleanField(CurrentUnitType, globalEnv->GetFieldID(javaRefs.unitTypeClass, "producesLarva", "Z"), unitType.producesLarva());
    globalEnv->SetBooleanField(CurrentUnitType, globalEnv->GetFieldID(javaRefs.unitTypeClass, "isMineralField", "Z"), unitType.isMineralField());
    globalEnv->SetBooleanField(CurrentUnitType, globalEnv->GetFieldID(javaRefs.unitTypeClass, "isCritter", "Z"), unitType.isCritter());
    globalEnv->SetBooleanField(CurrentUnitType, globalEnv->GetFieldID(javaRefs.unitTypeClass, "canBuildAddon", "Z"), unitType.canBuildAddon());
    // set double fields
    globalEnv->SetDoubleField(CurrentUnitType, globalEnv->GetFieldID(javaRefs.unitTypeClass, "topSpeed", "D"), unitType.topSpeed());
    // set enum values
    jobject race = globalEnv->GetStaticObjectField(
        javaRefs.raceClass, globalEnv->GetStaticFieldID(javaRefs.raceClass, unitType.getRace().getName().c_str(), "Lorg/openbw/bwapi4j/type/Race;"));
    globalEnv->SetObjectField(CurrentUnitType, globalEnv->GetFieldID(javaRefs.unitTypeClass, "race", "Lorg/openbw/bwapi4j/type/Race;"), race);

    jobject requiredTech = globalEnv->GetStaticObjectField(
        javaRefs.techTypeClass,
        globalEnv->GetStaticFieldID(javaRefs.techTypeClass, unitType.requiredTech().getName().c_str(), "Lorg/openbw/bwapi4j/type/TechType;"));
    globalEnv->SetObjectField(CurrentUnitType, globalEnv->GetFieldID(javaRefs.unitTypeClass, "requiredTech", "Lorg/openbw/bwapi4j/type/TechType;"),
                              requiredTech);

    jobject cloakingTech = globalEnv->GetStaticObjectField(
        javaRefs.techTypeClass,
        globalEnv->GetStaticFieldID(javaRefs.techTypeClass, unitType.cloakingTech().getName().c_str(), "Lorg/openbw/bwapi4j/type/TechType;"));
    globalEnv->SetObjectField(CurrentUnitType, globalEnv->GetFieldID(javaRefs.unitTypeClass, "cloakingTech", "Lorg/openbw/bwapi4j/type/TechType;"),
                              cloakingTech);

    jobject armorUpgrade = globalEnv->GetStaticObjectField(
        javaRefs.upgradeTypeClass,
        globalEnv->GetStaticFieldID(javaRefs.upgradeTypeClass, unitType.armorUpgrade().getName().c_str(), "Lorg/openbw/bwapi4j/type/UpgradeType;"));
    globalEnv->SetObjectField(CurrentUnitType, globalEnv->GetFieldID(javaRefs.unitTypeClass, "armorUpgrade", "Lorg/openbw/bwapi4j/type/UpgradeType;"),
                              armorUpgrade);

    jobject size = globalEnv->GetStaticObjectField(
        javaRefs.unitSizeTypeClass,
        globalEnv->GetStaticFieldID(javaRefs.unitSizeTypeClass, unitType.size().getName().c_str(), "Lorg/openbw/bwapi4j/type/UnitSizeType;"));
    globalEnv->SetObjectField(CurrentUnitType, globalEnv->GetFieldID(javaRefs.unitTypeClass, "size", "Lorg/openbw/bwapi4j/type/UnitSizeType;"), size);

    jobject groundWeapon = globalEnv->GetStaticObjectField(
        javaRefs.weaponTypeClass,
        globalEnv->GetStaticFieldID(javaRefs.weaponTypeClass, unitType.groundWeapon().getName().c_str(), "Lorg/openbw/bwapi4j/type/WeaponType;"));
    globalEnv->SetObjectField(CurrentUnitType, globalEnv->GetFieldID(javaRefs.unitTypeClass, "groundWeapon", "Lorg/openbw/bwapi4j/type/WeaponType;"),
                              groundWeapon);

    jobject airWeapon = globalEnv->GetStaticObjectField(
        javaRefs.weaponTypeClass,
        globalEnv->GetStaticFieldID(javaRefs.weaponTypeClass, unitType.airWeapon().getName().c_str(), "Lorg/openbw/bwapi4j/type/WeaponType;"));
    globalEnv->SetObjectField(CurrentUnitType, globalEnv->GetFieldID(javaRefs.unitTypeClass, "airWeapon", "Lorg/openbw/bwapi4j/type/WeaponType;"), airWeapon);

    // set complex values
    jobject upgradesList = globalEnv->GetObjectField(CurrentUnitType, globalEnv->GetFieldID(javaRefs.unitTypeClass, "upgrades", "Ljava/util/ArrayList;"));
    for (BWAPI::UpgradeType upgradeType : unitType.upgrades()) {
      jobject upgradesMemberType = globalEnv->GetStaticObjectField(
          javaRefs.upgradeTypeClass,
          globalEnv->GetStaticFieldID(javaRefs.upgradeTypeClass, upgradeType.getName().c_str(), "Lorg/openbw/bwapi4j/type/UpgradeType;"));
      globalEnv->CallObjectMethod(upgradesList, javaRefs.arrayListClass_add, upgradesMemberType);
      globalEnv->DeleteLocalRef(upgradesMemberType);
    }
    globalEnv->DeleteLocalRef(upgradesList);

    jobject upgradesWhatList =
        globalEnv->GetObjectField(CurrentUnitType, globalEnv->GetFieldID(javaRefs.unitTypeClass, "upgradesWhat", "Ljava/util/ArrayList;"));
    for (BWAPI::UpgradeType upgradeType : unitType.upgradesWhat()) {
      jobject upgradesWhatMemberType = globalEnv->GetStaticObjectField(
          javaRefs.upgradeTypeClass,
          globalEnv->GetStaticFieldID(javaRefs.upgradeTypeClass, upgradeType.getName().c_str(), "Lorg/openbw/bwapi4j/type/UpgradeType;"));
      globalEnv->CallObjectMethod(upgradesWhatList, javaRefs.arrayListClass_add, upgradesWhatMemberType);
      globalEnv->DeleteLocalRef(upgradesWhatMemberType);
    }
    globalEnv->DeleteLocalRef(upgradesWhatList);

    jobject abilitiesList = globalEnv->GetObjectField(CurrentUnitType, globalEnv->GetFieldID(javaRefs.unitTypeClass, "abilities", "Ljava/util/ArrayList;"));
    for (BWAPI::TechType techType : unitType.abilities()) {
      jobject abilitiesMemberType = globalEnv->GetStaticObjectField(
          javaRefs.techTypeClass, globalEnv->GetStaticFieldID(javaRefs.techTypeClass, techType.getName().c_str(), "Lorg/openbw/bwapi4j/type/TechType;"));
      globalEnv->CallObjectMethod(abilitiesList, javaRefs.arrayListClass_add, abilitiesMemberType);
      globalEnv->DeleteLocalRef(abilitiesMemberType);
    }
    globalEnv->DeleteLocalRef(abilitiesList);

    jobject researchesWhatList =
        globalEnv->GetObjectField(CurrentUnitType, globalEnv->GetFieldID(javaRefs.unitTypeClass, "researchesWhat", "Ljava/util/ArrayList;"));
    for (BWAPI::TechType techType : unitType.researchesWhat()) {
      jobject researchesWhatMemberType = globalEnv->GetStaticObjectField(
          javaRefs.techTypeClass, globalEnv->GetStaticFieldID(javaRefs.techTypeClass, techType.getName().c_str(), "Lorg/openbw/bwapi4j/type/TechType;"));
      globalEnv->CallObjectMethod(researchesWhatList, javaRefs.arrayListClass_add, researchesWhatMemberType);
      globalEnv->DeleteLocalRef(researchesWhatMemberType);
    }
    globalEnv->DeleteLocalRef(researchesWhatList);

    jobject buildsWhatList = globalEnv->GetObjectField(CurrentUnitType, globalEnv->GetFieldID(javaRefs.unitTypeClass, "buildsWhat", "Ljava/util/ArrayList;"));
    for (BWAPI::UnitType unitType : unitType.buildsWhat()) {
      jobject buildsWhatMemberType = globalEnv->GetStaticObjectField(
          javaRefs.unitTypeClass, globalEnv->GetStaticFieldID(javaRefs.unitTypeClass, unitType.getName().c_str(), "Lorg/openbw/bwapi4j/type/UnitType;"));
      globalEnv->CallObjectMethod(buildsWhatList, javaRefs.arrayListClass_add, buildsWhatMemberType);
      globalEnv->DeleteLocalRef(buildsWhatMemberType);
    }
    globalEnv->DeleteLocalRef(buildsWhatList);

    // create a new Pair object and fill in UnitType,Integer
    jfieldID whatBuildsField =
        globalEnv->GetStaticFieldID(javaRefs.unitTypeClass, unitType.whatBuilds().first.getName().c_str(), "Lorg/openbw/bwapi4j/type/UnitType;");
    jobject whatBuildsType = globalEnv->GetStaticObjectField(javaRefs.unitTypeClass, whatBuildsField);

    jobject pairObject = globalEnv->NewObject(javaRefs.pairClass, javaRefs.pairClassConstructor, whatBuildsType,
                                              globalEnv->NewObject(javaRefs.integerClass, javaRefs.integerClassConstructor, unitType.whatBuilds().second));
    globalEnv->SetObjectField(CurrentUnitType, globalEnv->GetFieldID(javaRefs.unitTypeClass, "whatBuilds", "Lorg/openbw/bwapi4j/util/Pair;"), pairObject);

    // read existing requiredUnits map and put <UnitType,Integer> entries
    for (auto const &req : unitType.requiredUnits()) {
      globalEnv->CallObjectMethod(CurrentUnitType, javaRefs.unitTypeClass_addRequiredUnit, (jint)req.first.getID(), (jint)req.second);
    }
    globalEnv->DeleteLocalRef(CurrentUnitType);
  }

  LOGGER("Reading unit types... done");
}
