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

void BridgeEnum::initialize(JNIEnv *env) {
  _env = env;

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
    jfieldID upgradeTypeField = _env->GetStaticFieldID(javaRefs.upgradeTypeClass, upgradeType.getName().c_str(), "Lorg/openbw/bwapi4j/type/UpgradeType;");
    jobject CurrentUpgradeType = _env->GetStaticObjectField(javaRefs.upgradeTypeClass, upgradeTypeField);

    // set int fields
    _env->SetIntField(CurrentUpgradeType, _env->GetFieldID(javaRefs.upgradeTypeClass, "id", "I"), upgradeType.getID());
    _env->SetIntField(CurrentUpgradeType, _env->GetFieldID(javaRefs.upgradeTypeClass, "mineralPriceFactor", "I"), upgradeType.mineralPriceFactor());
    _env->SetIntField(CurrentUpgradeType, _env->GetFieldID(javaRefs.upgradeTypeClass, "gasPriceFactor", "I"), upgradeType.gasPriceFactor());
    _env->SetIntField(CurrentUpgradeType, _env->GetFieldID(javaRefs.upgradeTypeClass, "upgradeTimeFactor", "I"), upgradeType.upgradeTimeFactor());
    _env->SetIntField(CurrentUpgradeType, _env->GetFieldID(javaRefs.upgradeTypeClass, "maxRepeats", "I"), upgradeType.maxRepeats());
    // set int[] fields
    jint *gasPrices = new jint[upgradeType.maxRepeats()];
    jint *mineralPrices = new jint[upgradeType.maxRepeats()];
    jint *upgradeTimes = new jint[upgradeType.maxRepeats()];
    jobject *whatsRequired = new jobject[upgradeType.maxRepeats()];

    for (int i = 0; i < upgradeType.maxRepeats(); i++) {
      gasPrices[i] = upgradeType.gasPrice(i + 1);
      mineralPrices[i] = upgradeType.mineralPrice(i + 1);
      upgradeTimes[i] = upgradeType.upgradeTime(i + 1);
      whatsRequired[i] = _env->GetStaticObjectField(
          javaRefs.unitTypeClass,
          _env->GetStaticFieldID(javaRefs.unitTypeClass, upgradeType.whatsRequired(i + 1).getName().c_str(), "Lorg/openbw/bwapi4j/type/UnitType;"));
    }

    jintArray gasPricesArray = _env->NewIntArray(upgradeType.maxRepeats());
    _env->SetIntArrayRegion(gasPricesArray, 0, upgradeType.maxRepeats(), gasPrices);
    _env->SetObjectField(CurrentUpgradeType, _env->GetFieldID(javaRefs.upgradeTypeClass, "gasPrices", "[I"), gasPricesArray);

    jintArray mineralPricesArray = _env->NewIntArray(upgradeType.maxRepeats());
    _env->SetIntArrayRegion(mineralPricesArray, 0, upgradeType.maxRepeats(), mineralPrices);
    _env->SetObjectField(CurrentUpgradeType, _env->GetFieldID(javaRefs.upgradeTypeClass, "mineralPrices", "[I"), mineralPricesArray);

    jintArray upgradeTimesArray = _env->NewIntArray(upgradeType.maxRepeats());
    _env->SetIntArrayRegion(upgradeTimesArray, 0, upgradeType.maxRepeats(), upgradeTimes);
    _env->SetObjectField(CurrentUpgradeType, _env->GetFieldID(javaRefs.upgradeTypeClass, "upgradeTimes", "[I"), upgradeTimesArray);

    jobjectArray objectArray = _env->NewObjectArray(upgradeType.maxRepeats(), javaRefs.unitTypeClass, NULL);
    _env->SetObjectField(CurrentUpgradeType, _env->GetFieldID(javaRefs.upgradeTypeClass, "whatsRequired", "[Lorg/openbw/bwapi4j/type/UnitType;"), objectArray);
    for (int i = 0; i < upgradeType.maxRepeats(); i++) {
      _env->SetObjectArrayElement(objectArray, i, whatsRequired[i]);
    }

    // read existing whatUses set and put UnitType entries
    for (auto const &use : upgradeType.whatUses()) {
      _env->CallObjectMethod(CurrentUpgradeType, javaRefs.upgradeTypeClass_addUsingUnit, (jint)use.getID());
    }

    // set enum fields
    jobject race = _env->GetStaticObjectField(javaRefs.raceClass,
                                              _env->GetStaticFieldID(javaRefs.raceClass, upgradeType.getRace().c_str(), "Lorg/openbw/bwapi4j/type/Race;"));
    _env->SetObjectField(CurrentUpgradeType, _env->GetFieldID(javaRefs.upgradeTypeClass, "race", "Lorg/openbw/bwapi4j/type/Race;"), race);

    jobject whatUpgrades = _env->GetStaticObjectField(
        javaRefs.unitTypeClass, _env->GetStaticFieldID(javaRefs.unitTypeClass, upgradeType.whatUpgrades().c_str(), "Lorg/openbw/bwapi4j/type/UnitType;"));
    _env->SetObjectField(CurrentUpgradeType, _env->GetFieldID(javaRefs.upgradeTypeClass, "whatUpgrades", "Lorg/openbw/bwapi4j/type/UnitType;"), whatUpgrades);
  }

  LOGGER("Reading upgrade types... done");
}

void BridgeEnum::createTechTypeEnum() {
  LOGGER("Reading tech types...");

  for (BWAPI::TechType techType : BWAPI::TechTypes::allTechTypes()) {
    if (techType.getName().empty()) {
      return;
    }
    jfieldID techTypeField = _env->GetStaticFieldID(javaRefs.techTypeClass, techType.getName().c_str(), "Lorg/openbw/bwapi4j/type/TechType;");
    jobject CurrentTechType = _env->GetStaticObjectField(javaRefs.techTypeClass, techTypeField);

    // set int fields
    _env->SetIntField(CurrentTechType, _env->GetFieldID(javaRefs.techTypeClass, "id", "I"), techType.getID());
    _env->SetIntField(CurrentTechType, _env->GetFieldID(javaRefs.techTypeClass, "mineralPrice", "I"), techType.mineralPrice());
    _env->SetIntField(CurrentTechType, _env->GetFieldID(javaRefs.techTypeClass, "gasPrice", "I"), techType.gasPrice());
    _env->SetIntField(CurrentTechType, _env->GetFieldID(javaRefs.techTypeClass, "researchTime", "I"), techType.researchTime());
    _env->SetIntField(CurrentTechType, _env->GetFieldID(javaRefs.techTypeClass, "energyCost", "I"), techType.energyCost());
    // set boolean fields
    _env->SetBooleanField(CurrentTechType, _env->GetFieldID(javaRefs.techTypeClass, "targetsUnit", "Z"), techType.targetsUnit());
    _env->SetBooleanField(CurrentTechType, _env->GetFieldID(javaRefs.techTypeClass, "targetsPosition", "Z"), techType.targetsPosition());
    // set enum fields
    jobject race = _env->GetStaticObjectField(
        javaRefs.raceClass, _env->GetStaticFieldID(javaRefs.raceClass, techType.getRace().getName().c_str(), "Lorg/openbw/bwapi4j/type/Race;"));
    _env->SetObjectField(CurrentTechType, _env->GetFieldID(javaRefs.techTypeClass, "race", "Lorg/openbw/bwapi4j/type/Race;"), race);

    jobject weaponType = _env->GetStaticObjectField(
        javaRefs.weaponTypeClass,
        _env->GetStaticFieldID(javaRefs.weaponTypeClass, techType.getWeapon().getName().c_str(), "Lorg/openbw/bwapi4j/type/WeaponType;"));
    _env->SetObjectField(CurrentTechType, _env->GetFieldID(javaRefs.techTypeClass, "weaponType", "Lorg/openbw/bwapi4j/type/WeaponType;"), weaponType);

    jobject whatResearches = _env->GetStaticObjectField(
        javaRefs.unitTypeClass,
        _env->GetStaticFieldID(javaRefs.unitTypeClass, techType.whatResearches().getName().c_str(), "Lorg/openbw/bwapi4j/type/UnitType;"));
    _env->SetObjectField(CurrentTechType, _env->GetFieldID(javaRefs.techTypeClass, "whatResearches", "Lorg/openbw/bwapi4j/type/UnitType;"), whatResearches);

    jobject order = _env->GetStaticObjectField(
        javaRefs.orderClass, _env->GetStaticFieldID(javaRefs.orderClass, techType.getOrder().getName().c_str(), "Lorg/openbw/bwapi4j/type/Order;"));
    _env->SetObjectField(CurrentTechType, _env->GetFieldID(javaRefs.techTypeClass, "order", "Lorg/openbw/bwapi4j/type/Order;"), order);

    jobject requiredUnit = _env->GetStaticObjectField(
        javaRefs.unitTypeClass,
        _env->GetStaticFieldID(javaRefs.unitTypeClass, techType.requiredUnit().getName().c_str(), "Lorg/openbw/bwapi4j/type/UnitType;"));
    _env->SetObjectField(CurrentTechType, _env->GetFieldID(javaRefs.techTypeClass, "requiredUnit", "Lorg/openbw/bwapi4j/type/UnitType;"), requiredUnit);
  }

  LOGGER("Reading tech types... done");
}

void BridgeEnum::createWeaponTypeEnum() {
  LOGGER("Reading weapon types...");

  for (BWAPI::WeaponType weaponType : BWAPI::WeaponTypes::allWeaponTypes()) {
    if (weaponType.getName().empty()) {
      return;
    }
    jfieldID typeField = _env->GetStaticFieldID(javaRefs.weaponTypeClass, weaponType.getName().c_str(), "Lorg/openbw/bwapi4j/type/WeaponType;");
    jobject CurrentWeaponType = _env->GetStaticObjectField(javaRefs.weaponTypeClass, typeField);

    // set int fields
    _env->SetIntField(CurrentWeaponType, _env->GetFieldID(javaRefs.weaponTypeClass, "id", "I"), weaponType.getID());
    _env->SetIntField(CurrentWeaponType, _env->GetFieldID(javaRefs.weaponTypeClass, "damageAmount", "I"), weaponType.damageAmount());
    _env->SetIntField(CurrentWeaponType, _env->GetFieldID(javaRefs.weaponTypeClass, "damageBonus", "I"), weaponType.damageBonus());
    _env->SetIntField(CurrentWeaponType, _env->GetFieldID(javaRefs.weaponTypeClass, "damageCooldown", "I"), weaponType.damageCooldown());
    _env->SetIntField(CurrentWeaponType, _env->GetFieldID(javaRefs.weaponTypeClass, "damageFactor", "I"), weaponType.damageFactor());
    _env->SetIntField(CurrentWeaponType, _env->GetFieldID(javaRefs.weaponTypeClass, "minRange", "I"), weaponType.minRange());
    _env->SetIntField(CurrentWeaponType, _env->GetFieldID(javaRefs.weaponTypeClass, "maxRange", "I"), weaponType.maxRange());
    _env->SetIntField(CurrentWeaponType, _env->GetFieldID(javaRefs.weaponTypeClass, "innerSplashRadius", "I"), weaponType.innerSplashRadius());
    _env->SetIntField(CurrentWeaponType, _env->GetFieldID(javaRefs.weaponTypeClass, "medianSplashRadius", "I"), weaponType.medianSplashRadius());
    _env->SetIntField(CurrentWeaponType, _env->GetFieldID(javaRefs.weaponTypeClass, "outerSplashRadius", "I"), weaponType.outerSplashRadius());
    // set boolean fields
    _env->SetBooleanField(CurrentWeaponType, _env->GetFieldID(javaRefs.weaponTypeClass, "targetsAir", "Z"), weaponType.targetsAir());
    _env->SetBooleanField(CurrentWeaponType, _env->GetFieldID(javaRefs.weaponTypeClass, "targetsGround", "Z"), weaponType.targetsGround());
    _env->SetBooleanField(CurrentWeaponType, _env->GetFieldID(javaRefs.weaponTypeClass, "targetsMechanical", "Z"), weaponType.targetsMechanical());
    _env->SetBooleanField(CurrentWeaponType, _env->GetFieldID(javaRefs.weaponTypeClass, "targetsOrganic", "Z"), weaponType.targetsOrganic());
    _env->SetBooleanField(CurrentWeaponType, _env->GetFieldID(javaRefs.weaponTypeClass, "targetsNonBuilding", "Z"), weaponType.targetsNonBuilding());
    _env->SetBooleanField(CurrentWeaponType, _env->GetFieldID(javaRefs.weaponTypeClass, "targetsNonRobotic", "Z"), weaponType.targetsNonRobotic());
    _env->SetBooleanField(CurrentWeaponType, _env->GetFieldID(javaRefs.weaponTypeClass, "targetsTerrain", "Z"), weaponType.targetsTerrain());
    _env->SetBooleanField(CurrentWeaponType, _env->GetFieldID(javaRefs.weaponTypeClass, "targetsOrgOrMech", "Z"), weaponType.targetsOrgOrMech());
    _env->SetBooleanField(CurrentWeaponType, _env->GetFieldID(javaRefs.weaponTypeClass, "targetsOwn", "Z"), weaponType.targetsOwn());

    // set enum fields
    jobject tech = _env->GetStaticObjectField(
        javaRefs.techTypeClass, _env->GetStaticFieldID(javaRefs.techTypeClass, weaponType.getTech().getName().c_str(), "Lorg/openbw/bwapi4j/type/TechType;"));
    _env->SetObjectField(CurrentWeaponType, _env->GetFieldID(javaRefs.weaponTypeClass, "tech", "Lorg/openbw/bwapi4j/type/TechType;"), tech);

    jobject whatUses = _env->GetStaticObjectField(
        javaRefs.unitTypeClass, _env->GetStaticFieldID(javaRefs.unitTypeClass, weaponType.whatUses().getName().c_str(), "Lorg/openbw/bwapi4j/type/UnitType;"));
    _env->SetObjectField(CurrentWeaponType, _env->GetFieldID(javaRefs.weaponTypeClass, "whatUses", "Lorg/openbw/bwapi4j/type/UnitType;"), whatUses);

    jobject upgradeType = _env->GetStaticObjectField(
        javaRefs.upgradeTypeClass,
        _env->GetStaticFieldID(javaRefs.upgradeTypeClass, weaponType.upgradeType().getName().c_str(), "Lorg/openbw/bwapi4j/type/UpgradeType;"));
    _env->SetObjectField(CurrentWeaponType, _env->GetFieldID(javaRefs.weaponTypeClass, "upgradeType", "Lorg/openbw/bwapi4j/type/UpgradeType;"), upgradeType);

    jobject damageType = _env->GetStaticObjectField(
        javaRefs.damageTypeClass,
        _env->GetStaticFieldID(javaRefs.damageTypeClass, weaponType.damageType().getName().c_str(), "Lorg/openbw/bwapi4j/type/DamageType;"));
    _env->SetObjectField(CurrentWeaponType, _env->GetFieldID(javaRefs.weaponTypeClass, "damageType", "Lorg/openbw/bwapi4j/type/DamageType;"), damageType);

    jobject explosionType = _env->GetStaticObjectField(
        javaRefs.explosionTypeClass,
        _env->GetStaticFieldID(javaRefs.explosionTypeClass, weaponType.explosionType().getName().c_str(), "Lorg/openbw/bwapi4j/type/ExplosionType;"));
    _env->SetObjectField(CurrentWeaponType, _env->GetFieldID(javaRefs.weaponTypeClass, "explosionType", "Lorg/openbw/bwapi4j/type/ExplosionType;"),
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

    jfieldID typeField = _env->GetStaticFieldID(javaRefs.unitTypeClass, unitType.getName().c_str(), "Lorg/openbw/bwapi4j/type/UnitType;");
    jobject CurrentUnitType = _env->GetStaticObjectField(javaRefs.unitTypeClass, typeField);

    // set int fields
    _env->SetIntField(CurrentUnitType, _env->GetFieldID(javaRefs.unitTypeClass, "id", "I"), unitType.getID());
    _env->SetIntField(CurrentUnitType, _env->GetFieldID(javaRefs.unitTypeClass, "maxHitPoints", "I"), unitType.maxHitPoints());
    _env->SetIntField(CurrentUnitType, _env->GetFieldID(javaRefs.unitTypeClass, "maxShields", "I"), unitType.maxShields());
    _env->SetIntField(CurrentUnitType, _env->GetFieldID(javaRefs.unitTypeClass, "maxEnergy", "I"), unitType.maxEnergy());
    _env->SetIntField(CurrentUnitType, _env->GetFieldID(javaRefs.unitTypeClass, "armor", "I"), unitType.armor());
    _env->SetIntField(CurrentUnitType, _env->GetFieldID(javaRefs.unitTypeClass, "mineralPrice", "I"), unitType.mineralPrice());
    _env->SetIntField(CurrentUnitType, _env->GetFieldID(javaRefs.unitTypeClass, "gasPrice", "I"), unitType.gasPrice());
    _env->SetIntField(CurrentUnitType, _env->GetFieldID(javaRefs.unitTypeClass, "buildTime", "I"), unitType.buildTime());
    _env->SetIntField(CurrentUnitType, _env->GetFieldID(javaRefs.unitTypeClass, "supplyRequired", "I"), unitType.supplyRequired());
    _env->SetIntField(CurrentUnitType, _env->GetFieldID(javaRefs.unitTypeClass, "supplyProvided", "I"), unitType.supplyProvided());
    _env->SetIntField(CurrentUnitType, _env->GetFieldID(javaRefs.unitTypeClass, "spaceRequired", "I"), unitType.spaceRequired());
    _env->SetIntField(CurrentUnitType, _env->GetFieldID(javaRefs.unitTypeClass, "spaceProvided", "I"), unitType.spaceProvided());
    _env->SetIntField(CurrentUnitType, _env->GetFieldID(javaRefs.unitTypeClass, "buildScore", "I"), unitType.buildScore());
    _env->SetIntField(CurrentUnitType, _env->GetFieldID(javaRefs.unitTypeClass, "destroyScore", "I"), unitType.destroyScore());
    _env->SetIntField(CurrentUnitType, _env->GetFieldID(javaRefs.unitTypeClass, "tileWidth", "I"), unitType.tileWidth());
    _env->SetIntField(CurrentUnitType, _env->GetFieldID(javaRefs.unitTypeClass, "tileHeight", "I"), unitType.tileHeight());
    _env->SetIntField(CurrentUnitType, _env->GetFieldID(javaRefs.unitTypeClass, "dimensionLeft", "I"), unitType.dimensionLeft());
    _env->SetIntField(CurrentUnitType, _env->GetFieldID(javaRefs.unitTypeClass, "dimensionUp", "I"), unitType.dimensionUp());
    _env->SetIntField(CurrentUnitType, _env->GetFieldID(javaRefs.unitTypeClass, "dimensionRight", "I"), unitType.dimensionRight());
    _env->SetIntField(CurrentUnitType, _env->GetFieldID(javaRefs.unitTypeClass, "dimensionDown", "I"), unitType.dimensionDown());
    _env->SetIntField(CurrentUnitType, _env->GetFieldID(javaRefs.unitTypeClass, "width", "I"), unitType.width());
    _env->SetIntField(CurrentUnitType, _env->GetFieldID(javaRefs.unitTypeClass, "height", "I"), unitType.height());
    _env->SetIntField(CurrentUnitType, _env->GetFieldID(javaRefs.unitTypeClass, "seekRange", "I"), unitType.seekRange());
    _env->SetIntField(CurrentUnitType, _env->GetFieldID(javaRefs.unitTypeClass, "sightRange", "I"), unitType.sightRange());
    _env->SetIntField(CurrentUnitType, _env->GetFieldID(javaRefs.unitTypeClass, "maxGroundHits", "I"), unitType.maxGroundHits());
    _env->SetIntField(CurrentUnitType, _env->GetFieldID(javaRefs.unitTypeClass, "maxAirHits", "I"), unitType.maxAirHits());
    _env->SetIntField(CurrentUnitType, _env->GetFieldID(javaRefs.unitTypeClass, "acceleration", "I"), unitType.acceleration());
    _env->SetIntField(CurrentUnitType, _env->GetFieldID(javaRefs.unitTypeClass, "haltDistance", "I"), unitType.haltDistance());
    _env->SetIntField(CurrentUnitType, _env->GetFieldID(javaRefs.unitTypeClass, "turnRadius", "I"), unitType.turnRadius());
    // set boolean fields
    _env->SetBooleanField(CurrentUnitType, _env->GetFieldID(javaRefs.unitTypeClass, "canProduce", "Z"), unitType.canProduce());
    _env->SetBooleanField(CurrentUnitType, _env->GetFieldID(javaRefs.unitTypeClass, "canAttack", "Z"), unitType.canAttack());
    _env->SetBooleanField(CurrentUnitType, _env->GetFieldID(javaRefs.unitTypeClass, "canMove", "Z"), unitType.canMove());
    _env->SetBooleanField(CurrentUnitType, _env->GetFieldID(javaRefs.unitTypeClass, "isFlyer", "Z"), unitType.isFlyer());
    _env->SetBooleanField(CurrentUnitType, _env->GetFieldID(javaRefs.unitTypeClass, "regeneratesHP", "Z"), unitType.regeneratesHP());
    _env->SetBooleanField(CurrentUnitType, _env->GetFieldID(javaRefs.unitTypeClass, "isSpellcaster", "Z"), unitType.isSpellcaster());
    _env->SetBooleanField(CurrentUnitType, _env->GetFieldID(javaRefs.unitTypeClass, "hasPermanentCloak", "Z"), unitType.hasPermanentCloak());
    _env->SetBooleanField(CurrentUnitType, _env->GetFieldID(javaRefs.unitTypeClass, "isInvincible", "Z"), unitType.isInvincible());
    _env->SetBooleanField(CurrentUnitType, _env->GetFieldID(javaRefs.unitTypeClass, "isOrganic", "Z"), unitType.isOrganic());
    _env->SetBooleanField(CurrentUnitType, _env->GetFieldID(javaRefs.unitTypeClass, "isMechanical", "Z"), unitType.isMechanical());
    _env->SetBooleanField(CurrentUnitType, _env->GetFieldID(javaRefs.unitTypeClass, "isRobotic", "Z"), unitType.isRobotic());
    _env->SetBooleanField(CurrentUnitType, _env->GetFieldID(javaRefs.unitTypeClass, "isMechanical", "Z"), unitType.isMechanical());
    _env->SetBooleanField(CurrentUnitType, _env->GetFieldID(javaRefs.unitTypeClass, "isDetector", "Z"), unitType.isDetector());
    _env->SetBooleanField(CurrentUnitType, _env->GetFieldID(javaRefs.unitTypeClass, "isResourceContainer", "Z"), unitType.isResourceContainer());
    _env->SetBooleanField(CurrentUnitType, _env->GetFieldID(javaRefs.unitTypeClass, "isResourceDepot", "Z"), unitType.isResourceDepot());
    _env->SetBooleanField(CurrentUnitType, _env->GetFieldID(javaRefs.unitTypeClass, "isRefinery", "Z"), unitType.isRefinery());
    _env->SetBooleanField(CurrentUnitType, _env->GetFieldID(javaRefs.unitTypeClass, "isWorker", "Z"), unitType.isWorker());
    _env->SetBooleanField(CurrentUnitType, _env->GetFieldID(javaRefs.unitTypeClass, "requiresPsi", "Z"), unitType.requiresPsi());
    _env->SetBooleanField(CurrentUnitType, _env->GetFieldID(javaRefs.unitTypeClass, "requiresCreep", "Z"), unitType.requiresCreep());
    _env->SetBooleanField(CurrentUnitType, _env->GetFieldID(javaRefs.unitTypeClass, "isTwoUnitsInOneEgg", "Z"), unitType.isTwoUnitsInOneEgg());
    _env->SetBooleanField(CurrentUnitType, _env->GetFieldID(javaRefs.unitTypeClass, "isBurrowable", "Z"), unitType.isBurrowable());
    _env->SetBooleanField(CurrentUnitType, _env->GetFieldID(javaRefs.unitTypeClass, "isCloakable", "Z"), unitType.isCloakable());
    _env->SetBooleanField(CurrentUnitType, _env->GetFieldID(javaRefs.unitTypeClass, "isBuilding", "Z"), unitType.isBuilding());
    _env->SetBooleanField(CurrentUnitType, _env->GetFieldID(javaRefs.unitTypeClass, "isAddon", "Z"), unitType.isAddon());
    _env->SetBooleanField(CurrentUnitType, _env->GetFieldID(javaRefs.unitTypeClass, "isFlyingBuilding", "Z"), unitType.isFlyingBuilding());
    _env->SetBooleanField(CurrentUnitType, _env->GetFieldID(javaRefs.unitTypeClass, "isNeutral", "Z"), unitType.isNeutral());
    _env->SetBooleanField(CurrentUnitType, _env->GetFieldID(javaRefs.unitTypeClass, "isHero", "Z"), unitType.isHero());
    _env->SetBooleanField(CurrentUnitType, _env->GetFieldID(javaRefs.unitTypeClass, "isPowerup", "Z"), unitType.isPowerup());
    _env->SetBooleanField(CurrentUnitType, _env->GetFieldID(javaRefs.unitTypeClass, "isBeacon", "Z"), unitType.isBeacon());
    _env->SetBooleanField(CurrentUnitType, _env->GetFieldID(javaRefs.unitTypeClass, "isFlagBeacon", "Z"), unitType.isFlagBeacon());
    _env->SetBooleanField(CurrentUnitType, _env->GetFieldID(javaRefs.unitTypeClass, "isSpecialBuilding", "Z"), unitType.isSpecialBuilding());
    _env->SetBooleanField(CurrentUnitType, _env->GetFieldID(javaRefs.unitTypeClass, "isSpell", "Z"), unitType.isSpell());
    _env->SetBooleanField(CurrentUnitType, _env->GetFieldID(javaRefs.unitTypeClass, "producesCreep", "Z"), unitType.producesCreep());
    _env->SetBooleanField(CurrentUnitType, _env->GetFieldID(javaRefs.unitTypeClass, "producesLarva", "Z"), unitType.producesLarva());
    _env->SetBooleanField(CurrentUnitType, _env->GetFieldID(javaRefs.unitTypeClass, "isMineralField", "Z"), unitType.isMineralField());
    _env->SetBooleanField(CurrentUnitType, _env->GetFieldID(javaRefs.unitTypeClass, "isCritter", "Z"), unitType.isCritter());
    _env->SetBooleanField(CurrentUnitType, _env->GetFieldID(javaRefs.unitTypeClass, "canBuildAddon", "Z"), unitType.canBuildAddon());
    // set double fields
    _env->SetDoubleField(CurrentUnitType, _env->GetFieldID(javaRefs.unitTypeClass, "topSpeed", "D"), unitType.topSpeed());
    // set enum values
    jobject race = _env->GetStaticObjectField(
        javaRefs.raceClass, _env->GetStaticFieldID(javaRefs.raceClass, unitType.getRace().getName().c_str(), "Lorg/openbw/bwapi4j/type/Race;"));
    _env->SetObjectField(CurrentUnitType, _env->GetFieldID(javaRefs.unitTypeClass, "race", "Lorg/openbw/bwapi4j/type/Race;"), race);

    jobject requiredTech = _env->GetStaticObjectField(
        javaRefs.techTypeClass,
        _env->GetStaticFieldID(javaRefs.techTypeClass, unitType.requiredTech().getName().c_str(), "Lorg/openbw/bwapi4j/type/TechType;"));
    _env->SetObjectField(CurrentUnitType, _env->GetFieldID(javaRefs.unitTypeClass, "requiredTech", "Lorg/openbw/bwapi4j/type/TechType;"), requiredTech);

    jobject cloakingTech = _env->GetStaticObjectField(
        javaRefs.techTypeClass,
        _env->GetStaticFieldID(javaRefs.techTypeClass, unitType.cloakingTech().getName().c_str(), "Lorg/openbw/bwapi4j/type/TechType;"));
    _env->SetObjectField(CurrentUnitType, _env->GetFieldID(javaRefs.unitTypeClass, "cloakingTech", "Lorg/openbw/bwapi4j/type/TechType;"), cloakingTech);

    jobject armorUpgrade = _env->GetStaticObjectField(
        javaRefs.upgradeTypeClass,
        _env->GetStaticFieldID(javaRefs.upgradeTypeClass, unitType.armorUpgrade().getName().c_str(), "Lorg/openbw/bwapi4j/type/UpgradeType;"));
    _env->SetObjectField(CurrentUnitType, _env->GetFieldID(javaRefs.unitTypeClass, "armorUpgrade", "Lorg/openbw/bwapi4j/type/UpgradeType;"), armorUpgrade);

    jobject size = _env->GetStaticObjectField(javaRefs.unitSizeTypeClass, _env->GetStaticFieldID(javaRefs.unitSizeTypeClass, unitType.size().getName().c_str(),
                                                                                                 "Lorg/openbw/bwapi4j/type/UnitSizeType;"));
    _env->SetObjectField(CurrentUnitType, _env->GetFieldID(javaRefs.unitTypeClass, "size", "Lorg/openbw/bwapi4j/type/UnitSizeType;"), size);

    jobject groundWeapon = _env->GetStaticObjectField(
        javaRefs.weaponTypeClass,
        _env->GetStaticFieldID(javaRefs.weaponTypeClass, unitType.groundWeapon().getName().c_str(), "Lorg/openbw/bwapi4j/type/WeaponType;"));
    _env->SetObjectField(CurrentUnitType, _env->GetFieldID(javaRefs.unitTypeClass, "groundWeapon", "Lorg/openbw/bwapi4j/type/WeaponType;"), groundWeapon);

    jobject airWeapon = _env->GetStaticObjectField(
        javaRefs.weaponTypeClass,
        _env->GetStaticFieldID(javaRefs.weaponTypeClass, unitType.airWeapon().getName().c_str(), "Lorg/openbw/bwapi4j/type/WeaponType;"));
    _env->SetObjectField(CurrentUnitType, _env->GetFieldID(javaRefs.unitTypeClass, "airWeapon", "Lorg/openbw/bwapi4j/type/WeaponType;"), airWeapon);

    // set complex values
    jobject upgradesList = _env->GetObjectField(CurrentUnitType, _env->GetFieldID(javaRefs.unitTypeClass, "upgrades", "Ljava/util/ArrayList;"));
    for (BWAPI::UpgradeType upgradeType : unitType.upgrades()) {
      jobject upgradesMemberType = _env->GetStaticObjectField(
          javaRefs.upgradeTypeClass, _env->GetStaticFieldID(javaRefs.upgradeTypeClass, upgradeType.getName().c_str(), "Lorg/openbw/bwapi4j/type/UpgradeType;"));
      _env->CallObjectMethod(upgradesList, javaRefs.arrayListClass_add, upgradesMemberType);
      _env->DeleteLocalRef(upgradesMemberType);
    }
    _env->DeleteLocalRef(upgradesList);

    jobject upgradesWhatList = _env->GetObjectField(CurrentUnitType, _env->GetFieldID(javaRefs.unitTypeClass, "upgradesWhat", "Ljava/util/ArrayList;"));
    for (BWAPI::UpgradeType upgradeType : unitType.upgradesWhat()) {
      jobject upgradesWhatMemberType = _env->GetStaticObjectField(
          javaRefs.upgradeTypeClass, _env->GetStaticFieldID(javaRefs.upgradeTypeClass, upgradeType.getName().c_str(), "Lorg/openbw/bwapi4j/type/UpgradeType;"));
      _env->CallObjectMethod(upgradesWhatList, javaRefs.arrayListClass_add, upgradesWhatMemberType);
      _env->DeleteLocalRef(upgradesWhatMemberType);
    }
    _env->DeleteLocalRef(upgradesWhatList);

    jobject abilitiesList = _env->GetObjectField(CurrentUnitType, _env->GetFieldID(javaRefs.unitTypeClass, "abilities", "Ljava/util/ArrayList;"));
    for (BWAPI::TechType techType : unitType.abilities()) {
      jobject abilitiesMemberType = _env->GetStaticObjectField(
          javaRefs.techTypeClass, _env->GetStaticFieldID(javaRefs.techTypeClass, techType.getName().c_str(), "Lorg/openbw/bwapi4j/type/TechType;"));
      _env->CallObjectMethod(abilitiesList, javaRefs.arrayListClass_add, abilitiesMemberType);
      _env->DeleteLocalRef(abilitiesMemberType);
    }
    _env->DeleteLocalRef(abilitiesList);

    jobject researchesWhatList = _env->GetObjectField(CurrentUnitType, _env->GetFieldID(javaRefs.unitTypeClass, "researchesWhat", "Ljava/util/ArrayList;"));
    for (BWAPI::TechType techType : unitType.researchesWhat()) {
      jobject researchesWhatMemberType = _env->GetStaticObjectField(
          javaRefs.techTypeClass, _env->GetStaticFieldID(javaRefs.techTypeClass, techType.getName().c_str(), "Lorg/openbw/bwapi4j/type/TechType;"));
      _env->CallObjectMethod(researchesWhatList, javaRefs.arrayListClass_add, researchesWhatMemberType);
      _env->DeleteLocalRef(researchesWhatMemberType);
    }
    _env->DeleteLocalRef(researchesWhatList);

    jobject buildsWhatList = _env->GetObjectField(CurrentUnitType, _env->GetFieldID(javaRefs.unitTypeClass, "buildsWhat", "Ljava/util/ArrayList;"));
    for (BWAPI::UnitType unitType : unitType.buildsWhat()) {
      jobject buildsWhatMemberType = _env->GetStaticObjectField(
          javaRefs.unitTypeClass, _env->GetStaticFieldID(javaRefs.unitTypeClass, unitType.getName().c_str(), "Lorg/openbw/bwapi4j/type/UnitType;"));
      _env->CallObjectMethod(buildsWhatList, javaRefs.arrayListClass_add, buildsWhatMemberType);
      _env->DeleteLocalRef(buildsWhatMemberType);
    }
    _env->DeleteLocalRef(buildsWhatList);

    // create a new Pair object and fill in UnitType,Integer
    jfieldID whatBuildsField =
        _env->GetStaticFieldID(javaRefs.unitTypeClass, unitType.whatBuilds().first.getName().c_str(), "Lorg/openbw/bwapi4j/type/UnitType;");
    jobject whatBuildsType = _env->GetStaticObjectField(javaRefs.unitTypeClass, whatBuildsField);

    jobject pairObject = _env->NewObject(javaRefs.pairClass, javaRefs.pairClassConstructor, whatBuildsType,
                                         _env->NewObject(javaRefs.integerClass, javaRefs.integerClassConstructor, unitType.whatBuilds().second));
    _env->SetObjectField(CurrentUnitType, _env->GetFieldID(javaRefs.unitTypeClass, "whatBuilds", "Lorg/openbw/bwapi4j/util/Pair;"), pairObject);

    // read existing requiredUnits map and put <UnitType,Integer> entries
    for (auto const &req : unitType.requiredUnits()) {
      _env->CallObjectMethod(CurrentUnitType, javaRefs.unitTypeClass_addRequiredUnit, (jint)req.first.getID(), (jint)req.second);
    }
    _env->DeleteLocalRef(CurrentUnitType);
  }

  LOGGER("Reading unit types... done");
}
