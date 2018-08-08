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
  // read static data: UpgradeType
  LOGGER("reading upgrade types...");
  for (BWAPI::UpgradeType upgradeType : BWAPI::UpgradeTypes::allUpgradeTypes()) {
    if (upgradeType.getName().empty()) {
      return;
    }
    jfieldID upgradeTypeField = globalEnv->GetStaticFieldID(upgradeTypeClass, upgradeType.getName().c_str(), "Lorg/openbw/bwapi4j/type/UpgradeType;");
    jobject CurrentUpgradeType = globalEnv->GetStaticObjectField(upgradeTypeClass, upgradeTypeField);

    // set int fields
    globalEnv->SetIntField(CurrentUpgradeType, globalEnv->GetFieldID(upgradeTypeClass, "id", "I"), upgradeType.getID());
    globalEnv->SetIntField(CurrentUpgradeType, globalEnv->GetFieldID(upgradeTypeClass, "mineralPriceFactor", "I"), upgradeType.mineralPriceFactor());
    globalEnv->SetIntField(CurrentUpgradeType, globalEnv->GetFieldID(upgradeTypeClass, "gasPriceFactor", "I"), upgradeType.gasPriceFactor());
    globalEnv->SetIntField(CurrentUpgradeType, globalEnv->GetFieldID(upgradeTypeClass, "upgradeTimeFactor", "I"), upgradeType.upgradeTimeFactor());
    globalEnv->SetIntField(CurrentUpgradeType, globalEnv->GetFieldID(upgradeTypeClass, "maxRepeats", "I"), upgradeType.maxRepeats());
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
          unitTypeClass, globalEnv->GetStaticFieldID(unitTypeClass, upgradeType.whatsRequired(i + 1).getName().c_str(), "Lorg/openbw/bwapi4j/type/UnitType;"));
    }

    jintArray gasPricesArray = globalEnv->NewIntArray(upgradeType.maxRepeats());
    globalEnv->SetIntArrayRegion(gasPricesArray, 0, upgradeType.maxRepeats(), gasPrices);
    globalEnv->SetObjectField(CurrentUpgradeType, globalEnv->GetFieldID(upgradeTypeClass, "gasPrices", "[I"), gasPricesArray);

    jintArray mineralPricesArray = globalEnv->NewIntArray(upgradeType.maxRepeats());
    globalEnv->SetIntArrayRegion(mineralPricesArray, 0, upgradeType.maxRepeats(), mineralPrices);
    globalEnv->SetObjectField(CurrentUpgradeType, globalEnv->GetFieldID(upgradeTypeClass, "mineralPrices", "[I"), mineralPricesArray);

    jintArray upgradeTimesArray = globalEnv->NewIntArray(upgradeType.maxRepeats());
    globalEnv->SetIntArrayRegion(upgradeTimesArray, 0, upgradeType.maxRepeats(), upgradeTimes);
    globalEnv->SetObjectField(CurrentUpgradeType, globalEnv->GetFieldID(upgradeTypeClass, "upgradeTimes", "[I"), upgradeTimesArray);

    jobjectArray objectArray = globalEnv->NewObjectArray(upgradeType.maxRepeats(), unitTypeClass, NULL);
    globalEnv->SetObjectField(CurrentUpgradeType, globalEnv->GetFieldID(upgradeTypeClass, "whatsRequired", "[Lorg/openbw/bwapi4j/type/UnitType;"), objectArray);
    for (int i = 0; i < upgradeType.maxRepeats(); i++) {
      globalEnv->SetObjectArrayElement(objectArray, i, whatsRequired[i]);
    }

    // read existing whatUses set and put UnitType entries
    for (auto const &use : upgradeType.whatUses()) {
      globalEnv->CallObjectMethod(CurrentUpgradeType, upgradeTypeClass_addUsingUnit, (jint)use.getID());
    }

    // set enum fields
    jobject race =
        globalEnv->GetStaticObjectField(raceClass, globalEnv->GetStaticFieldID(raceClass, upgradeType.getRace().c_str(), "Lorg/openbw/bwapi4j/type/Race;"));
    globalEnv->SetObjectField(CurrentUpgradeType, globalEnv->GetFieldID(upgradeTypeClass, "race", "Lorg/openbw/bwapi4j/type/Race;"), race);

    jobject whatUpgrades = globalEnv->GetStaticObjectField(
        unitTypeClass, globalEnv->GetStaticFieldID(unitTypeClass, upgradeType.whatUpgrades().c_str(), "Lorg/openbw/bwapi4j/type/UnitType;"));
    globalEnv->SetObjectField(CurrentUpgradeType, globalEnv->GetFieldID(upgradeTypeClass, "whatUpgrades", "Lorg/openbw/bwapi4j/type/UnitType;"), whatUpgrades);
  }
  LOGGER("done");
}

void BridgeEnum::createTechTypeEnum() {
  // read static data: TechType
  LOGGER("reading tech types...");
  for (BWAPI::TechType techType : BWAPI::TechTypes::allTechTypes()) {
    if (techType.getName().empty()) {
      return;
    }
    jfieldID techTypeField = globalEnv->GetStaticFieldID(techTypeClass, techType.getName().c_str(), "Lorg/openbw/bwapi4j/type/TechType;");
    jobject CurrentTechType = globalEnv->GetStaticObjectField(techTypeClass, techTypeField);

    // set int fields
    globalEnv->SetIntField(CurrentTechType, globalEnv->GetFieldID(techTypeClass, "id", "I"), techType.getID());
    globalEnv->SetIntField(CurrentTechType, globalEnv->GetFieldID(techTypeClass, "mineralPrice", "I"), techType.mineralPrice());
    globalEnv->SetIntField(CurrentTechType, globalEnv->GetFieldID(techTypeClass, "gasPrice", "I"), techType.gasPrice());
    globalEnv->SetIntField(CurrentTechType, globalEnv->GetFieldID(techTypeClass, "researchTime", "I"), techType.researchTime());
    globalEnv->SetIntField(CurrentTechType, globalEnv->GetFieldID(techTypeClass, "energyCost", "I"), techType.energyCost());
    // set boolean fields
    globalEnv->SetBooleanField(CurrentTechType, globalEnv->GetFieldID(techTypeClass, "targetsUnit", "Z"), techType.targetsUnit());
    globalEnv->SetBooleanField(CurrentTechType, globalEnv->GetFieldID(techTypeClass, "targetsPosition", "Z"), techType.targetsPosition());
    // set enum fields
    jobject race = globalEnv->GetStaticObjectField(
        raceClass, globalEnv->GetStaticFieldID(raceClass, techType.getRace().getName().c_str(), "Lorg/openbw/bwapi4j/type/Race;"));
    globalEnv->SetObjectField(CurrentTechType, globalEnv->GetFieldID(techTypeClass, "race", "Lorg/openbw/bwapi4j/type/Race;"), race);

    jobject weaponType = globalEnv->GetStaticObjectField(
        weaponTypeClass, globalEnv->GetStaticFieldID(weaponTypeClass, techType.getWeapon().getName().c_str(), "Lorg/openbw/bwapi4j/type/WeaponType;"));
    globalEnv->SetObjectField(CurrentTechType, globalEnv->GetFieldID(techTypeClass, "weaponType", "Lorg/openbw/bwapi4j/type/WeaponType;"), weaponType);

    jobject whatResearches = globalEnv->GetStaticObjectField(
        unitTypeClass, globalEnv->GetStaticFieldID(unitTypeClass, techType.whatResearches().getName().c_str(), "Lorg/openbw/bwapi4j/type/UnitType;"));
    globalEnv->SetObjectField(CurrentTechType, globalEnv->GetFieldID(techTypeClass, "whatResearches", "Lorg/openbw/bwapi4j/type/UnitType;"), whatResearches);

    jobject order = globalEnv->GetStaticObjectField(
        orderClass, globalEnv->GetStaticFieldID(orderClass, techType.getOrder().getName().c_str(), "Lorg/openbw/bwapi4j/type/Order;"));
    globalEnv->SetObjectField(CurrentTechType, globalEnv->GetFieldID(techTypeClass, "order", "Lorg/openbw/bwapi4j/type/Order;"), order);

    jobject requiredUnit = globalEnv->GetStaticObjectField(
        unitTypeClass, globalEnv->GetStaticFieldID(unitTypeClass, techType.requiredUnit().getName().c_str(), "Lorg/openbw/bwapi4j/type/UnitType;"));
    globalEnv->SetObjectField(CurrentTechType, globalEnv->GetFieldID(techTypeClass, "requiredUnit", "Lorg/openbw/bwapi4j/type/UnitType;"), requiredUnit);
  }
  LOGGER("done");
}

void BridgeEnum::createWeaponTypeEnum() {
  // read static data: WeaponType
  LOGGER("reading weapon types...");
  for (BWAPI::WeaponType weaponType : BWAPI::WeaponTypes::allWeaponTypes()) {
    if (weaponType.getName().empty()) {
      return;
    }
    jfieldID typeField = globalEnv->GetStaticFieldID(weaponTypeClass, weaponType.getName().c_str(), "Lorg/openbw/bwapi4j/type/WeaponType;");
    jobject CurrentWeaponType = globalEnv->GetStaticObjectField(weaponTypeClass, typeField);

    // set int fields
    globalEnv->SetIntField(CurrentWeaponType, globalEnv->GetFieldID(weaponTypeClass, "id", "I"), weaponType.getID());
    globalEnv->SetIntField(CurrentWeaponType, globalEnv->GetFieldID(weaponTypeClass, "damageAmount", "I"), weaponType.damageAmount());
    globalEnv->SetIntField(CurrentWeaponType, globalEnv->GetFieldID(weaponTypeClass, "damageBonus", "I"), weaponType.damageBonus());
    globalEnv->SetIntField(CurrentWeaponType, globalEnv->GetFieldID(weaponTypeClass, "damageCooldown", "I"), weaponType.damageCooldown());
    globalEnv->SetIntField(CurrentWeaponType, globalEnv->GetFieldID(weaponTypeClass, "damageFactor", "I"), weaponType.damageFactor());
    globalEnv->SetIntField(CurrentWeaponType, globalEnv->GetFieldID(weaponTypeClass, "minRange", "I"), weaponType.minRange());
    globalEnv->SetIntField(CurrentWeaponType, globalEnv->GetFieldID(weaponTypeClass, "maxRange", "I"), weaponType.maxRange());
    globalEnv->SetIntField(CurrentWeaponType, globalEnv->GetFieldID(weaponTypeClass, "innerSplashRadius", "I"), weaponType.innerSplashRadius());
    globalEnv->SetIntField(CurrentWeaponType, globalEnv->GetFieldID(weaponTypeClass, "medianSplashRadius", "I"), weaponType.medianSplashRadius());
    globalEnv->SetIntField(CurrentWeaponType, globalEnv->GetFieldID(weaponTypeClass, "outerSplashRadius", "I"), weaponType.outerSplashRadius());
    // set boolean fields
    globalEnv->SetBooleanField(CurrentWeaponType, globalEnv->GetFieldID(weaponTypeClass, "targetsAir", "Z"), weaponType.targetsAir());
    globalEnv->SetBooleanField(CurrentWeaponType, globalEnv->GetFieldID(weaponTypeClass, "targetsGround", "Z"), weaponType.targetsGround());
    globalEnv->SetBooleanField(CurrentWeaponType, globalEnv->GetFieldID(weaponTypeClass, "targetsMechanical", "Z"), weaponType.targetsMechanical());
    globalEnv->SetBooleanField(CurrentWeaponType, globalEnv->GetFieldID(weaponTypeClass, "targetsOrganic", "Z"), weaponType.targetsOrganic());
    globalEnv->SetBooleanField(CurrentWeaponType, globalEnv->GetFieldID(weaponTypeClass, "targetsNonBuilding", "Z"), weaponType.targetsNonBuilding());
    globalEnv->SetBooleanField(CurrentWeaponType, globalEnv->GetFieldID(weaponTypeClass, "targetsNonRobotic", "Z"), weaponType.targetsNonRobotic());
    globalEnv->SetBooleanField(CurrentWeaponType, globalEnv->GetFieldID(weaponTypeClass, "targetsTerrain", "Z"), weaponType.targetsTerrain());
    globalEnv->SetBooleanField(CurrentWeaponType, globalEnv->GetFieldID(weaponTypeClass, "targetsOrgOrMech", "Z"), weaponType.targetsOrgOrMech());
    globalEnv->SetBooleanField(CurrentWeaponType, globalEnv->GetFieldID(weaponTypeClass, "targetsOwn", "Z"), weaponType.targetsOwn());

    // set enum fields
    jobject tech = globalEnv->GetStaticObjectField(
        techTypeClass, globalEnv->GetStaticFieldID(techTypeClass, weaponType.getTech().getName().c_str(), "Lorg/openbw/bwapi4j/type/TechType;"));
    globalEnv->SetObjectField(CurrentWeaponType, globalEnv->GetFieldID(weaponTypeClass, "tech", "Lorg/openbw/bwapi4j/type/TechType;"), tech);

    jobject whatUses = globalEnv->GetStaticObjectField(
        unitTypeClass, globalEnv->GetStaticFieldID(unitTypeClass, weaponType.whatUses().getName().c_str(), "Lorg/openbw/bwapi4j/type/UnitType;"));
    globalEnv->SetObjectField(CurrentWeaponType, globalEnv->GetFieldID(weaponTypeClass, "whatUses", "Lorg/openbw/bwapi4j/type/UnitType;"), whatUses);

    jobject upgradeType = globalEnv->GetStaticObjectField(
        upgradeTypeClass, globalEnv->GetStaticFieldID(upgradeTypeClass, weaponType.upgradeType().getName().c_str(), "Lorg/openbw/bwapi4j/type/UpgradeType;"));
    globalEnv->SetObjectField(CurrentWeaponType, globalEnv->GetFieldID(weaponTypeClass, "upgradeType", "Lorg/openbw/bwapi4j/type/UpgradeType;"), upgradeType);

    jobject damageType = globalEnv->GetStaticObjectField(
        damageTypeClass, globalEnv->GetStaticFieldID(damageTypeClass, weaponType.damageType().getName().c_str(), "Lorg/openbw/bwapi4j/type/DamageType;"));
    globalEnv->SetObjectField(CurrentWeaponType, globalEnv->GetFieldID(weaponTypeClass, "damageType", "Lorg/openbw/bwapi4j/type/DamageType;"), damageType);

    jobject explosionType = globalEnv->GetStaticObjectField(
        explosionTypeClass,
        globalEnv->GetStaticFieldID(explosionTypeClass, weaponType.explosionType().getName().c_str(), "Lorg/openbw/bwapi4j/type/ExplosionType;"));
    globalEnv->SetObjectField(CurrentWeaponType, globalEnv->GetFieldID(weaponTypeClass, "explosionType", "Lorg/openbw/bwapi4j/type/ExplosionType;"),
                              explosionType);
  }
  LOGGER("done");
}

void BridgeEnum::createUnitTypeEnum() {
  LOGGER("reading unit types...");
  for (BWAPI::UnitType unitType : BWAPI::UnitTypes::allUnitTypes()) {
    if (unitType.getName().empty()) {
      return;
    }

    jfieldID typeField = globalEnv->GetStaticFieldID(unitTypeClass, unitType.getName().c_str(), "Lorg/openbw/bwapi4j/type/UnitType;");
    jobject CurrentUnitType = globalEnv->GetStaticObjectField(unitTypeClass, typeField);

    // set int fields
    globalEnv->SetIntField(CurrentUnitType, globalEnv->GetFieldID(unitTypeClass, "id", "I"), unitType.getID());
    globalEnv->SetIntField(CurrentUnitType, globalEnv->GetFieldID(unitTypeClass, "maxHitPoints", "I"), unitType.maxHitPoints());
    globalEnv->SetIntField(CurrentUnitType, globalEnv->GetFieldID(unitTypeClass, "maxShields", "I"), unitType.maxShields());
    globalEnv->SetIntField(CurrentUnitType, globalEnv->GetFieldID(unitTypeClass, "maxEnergy", "I"), unitType.maxEnergy());
    globalEnv->SetIntField(CurrentUnitType, globalEnv->GetFieldID(unitTypeClass, "armor", "I"), unitType.armor());
    globalEnv->SetIntField(CurrentUnitType, globalEnv->GetFieldID(unitTypeClass, "mineralPrice", "I"), unitType.mineralPrice());
    globalEnv->SetIntField(CurrentUnitType, globalEnv->GetFieldID(unitTypeClass, "gasPrice", "I"), unitType.gasPrice());
    globalEnv->SetIntField(CurrentUnitType, globalEnv->GetFieldID(unitTypeClass, "buildTime", "I"), unitType.buildTime());
    globalEnv->SetIntField(CurrentUnitType, globalEnv->GetFieldID(unitTypeClass, "supplyRequired", "I"), unitType.supplyRequired());
    globalEnv->SetIntField(CurrentUnitType, globalEnv->GetFieldID(unitTypeClass, "supplyProvided", "I"), unitType.supplyProvided());
    globalEnv->SetIntField(CurrentUnitType, globalEnv->GetFieldID(unitTypeClass, "spaceRequired", "I"), unitType.spaceRequired());
    globalEnv->SetIntField(CurrentUnitType, globalEnv->GetFieldID(unitTypeClass, "spaceProvided", "I"), unitType.spaceProvided());
    globalEnv->SetIntField(CurrentUnitType, globalEnv->GetFieldID(unitTypeClass, "buildScore", "I"), unitType.buildScore());
    globalEnv->SetIntField(CurrentUnitType, globalEnv->GetFieldID(unitTypeClass, "destroyScore", "I"), unitType.destroyScore());
    globalEnv->SetIntField(CurrentUnitType, globalEnv->GetFieldID(unitTypeClass, "tileWidth", "I"), unitType.tileWidth());
    globalEnv->SetIntField(CurrentUnitType, globalEnv->GetFieldID(unitTypeClass, "tileHeight", "I"), unitType.tileHeight());
    globalEnv->SetIntField(CurrentUnitType, globalEnv->GetFieldID(unitTypeClass, "dimensionLeft", "I"), unitType.dimensionLeft());
    globalEnv->SetIntField(CurrentUnitType, globalEnv->GetFieldID(unitTypeClass, "dimensionUp", "I"), unitType.dimensionUp());
    globalEnv->SetIntField(CurrentUnitType, globalEnv->GetFieldID(unitTypeClass, "dimensionRight", "I"), unitType.dimensionRight());
    globalEnv->SetIntField(CurrentUnitType, globalEnv->GetFieldID(unitTypeClass, "dimensionDown", "I"), unitType.dimensionDown());
    globalEnv->SetIntField(CurrentUnitType, globalEnv->GetFieldID(unitTypeClass, "width", "I"), unitType.width());
    globalEnv->SetIntField(CurrentUnitType, globalEnv->GetFieldID(unitTypeClass, "height", "I"), unitType.height());
    globalEnv->SetIntField(CurrentUnitType, globalEnv->GetFieldID(unitTypeClass, "seekRange", "I"), unitType.seekRange());
    globalEnv->SetIntField(CurrentUnitType, globalEnv->GetFieldID(unitTypeClass, "sightRange", "I"), unitType.sightRange());
    globalEnv->SetIntField(CurrentUnitType, globalEnv->GetFieldID(unitTypeClass, "maxGroundHits", "I"), unitType.maxGroundHits());
    globalEnv->SetIntField(CurrentUnitType, globalEnv->GetFieldID(unitTypeClass, "maxAirHits", "I"), unitType.maxAirHits());
    globalEnv->SetIntField(CurrentUnitType, globalEnv->GetFieldID(unitTypeClass, "acceleration", "I"), unitType.acceleration());
    globalEnv->SetIntField(CurrentUnitType, globalEnv->GetFieldID(unitTypeClass, "haltDistance", "I"), unitType.haltDistance());
    globalEnv->SetIntField(CurrentUnitType, globalEnv->GetFieldID(unitTypeClass, "turnRadius", "I"), unitType.turnRadius());
    // set boolean fields
    globalEnv->SetBooleanField(CurrentUnitType, globalEnv->GetFieldID(unitTypeClass, "canProduce", "Z"), unitType.canProduce());
    globalEnv->SetBooleanField(CurrentUnitType, globalEnv->GetFieldID(unitTypeClass, "canAttack", "Z"), unitType.canAttack());
    globalEnv->SetBooleanField(CurrentUnitType, globalEnv->GetFieldID(unitTypeClass, "canMove", "Z"), unitType.canMove());
    globalEnv->SetBooleanField(CurrentUnitType, globalEnv->GetFieldID(unitTypeClass, "isFlyer", "Z"), unitType.isFlyer());
    globalEnv->SetBooleanField(CurrentUnitType, globalEnv->GetFieldID(unitTypeClass, "regeneratesHP", "Z"), unitType.regeneratesHP());
    globalEnv->SetBooleanField(CurrentUnitType, globalEnv->GetFieldID(unitTypeClass, "isSpellcaster", "Z"), unitType.isSpellcaster());
    globalEnv->SetBooleanField(CurrentUnitType, globalEnv->GetFieldID(unitTypeClass, "hasPermanentCloak", "Z"), unitType.hasPermanentCloak());
    globalEnv->SetBooleanField(CurrentUnitType, globalEnv->GetFieldID(unitTypeClass, "isInvincible", "Z"), unitType.isInvincible());
    globalEnv->SetBooleanField(CurrentUnitType, globalEnv->GetFieldID(unitTypeClass, "isOrganic", "Z"), unitType.isOrganic());
    globalEnv->SetBooleanField(CurrentUnitType, globalEnv->GetFieldID(unitTypeClass, "isMechanical", "Z"), unitType.isMechanical());
    globalEnv->SetBooleanField(CurrentUnitType, globalEnv->GetFieldID(unitTypeClass, "isRobotic", "Z"), unitType.isRobotic());
    globalEnv->SetBooleanField(CurrentUnitType, globalEnv->GetFieldID(unitTypeClass, "isMechanical", "Z"), unitType.isMechanical());
    globalEnv->SetBooleanField(CurrentUnitType, globalEnv->GetFieldID(unitTypeClass, "isDetector", "Z"), unitType.isDetector());
    globalEnv->SetBooleanField(CurrentUnitType, globalEnv->GetFieldID(unitTypeClass, "isResourceContainer", "Z"), unitType.isResourceContainer());
    globalEnv->SetBooleanField(CurrentUnitType, globalEnv->GetFieldID(unitTypeClass, "isResourceDepot", "Z"), unitType.isResourceDepot());
    globalEnv->SetBooleanField(CurrentUnitType, globalEnv->GetFieldID(unitTypeClass, "isRefinery", "Z"), unitType.isRefinery());
    globalEnv->SetBooleanField(CurrentUnitType, globalEnv->GetFieldID(unitTypeClass, "isWorker", "Z"), unitType.isWorker());
    globalEnv->SetBooleanField(CurrentUnitType, globalEnv->GetFieldID(unitTypeClass, "requiresPsi", "Z"), unitType.requiresPsi());
    globalEnv->SetBooleanField(CurrentUnitType, globalEnv->GetFieldID(unitTypeClass, "requiresCreep", "Z"), unitType.requiresCreep());
    globalEnv->SetBooleanField(CurrentUnitType, globalEnv->GetFieldID(unitTypeClass, "isTwoUnitsInOneEgg", "Z"), unitType.isTwoUnitsInOneEgg());
    globalEnv->SetBooleanField(CurrentUnitType, globalEnv->GetFieldID(unitTypeClass, "isBurrowable", "Z"), unitType.isBurrowable());
    globalEnv->SetBooleanField(CurrentUnitType, globalEnv->GetFieldID(unitTypeClass, "isCloakable", "Z"), unitType.isCloakable());
    globalEnv->SetBooleanField(CurrentUnitType, globalEnv->GetFieldID(unitTypeClass, "isBuilding", "Z"), unitType.isBuilding());
    globalEnv->SetBooleanField(CurrentUnitType, globalEnv->GetFieldID(unitTypeClass, "isAddon", "Z"), unitType.isAddon());
    globalEnv->SetBooleanField(CurrentUnitType, globalEnv->GetFieldID(unitTypeClass, "isFlyingBuilding", "Z"), unitType.isFlyingBuilding());
    globalEnv->SetBooleanField(CurrentUnitType, globalEnv->GetFieldID(unitTypeClass, "isNeutral", "Z"), unitType.isNeutral());
    globalEnv->SetBooleanField(CurrentUnitType, globalEnv->GetFieldID(unitTypeClass, "isHero", "Z"), unitType.isHero());
    globalEnv->SetBooleanField(CurrentUnitType, globalEnv->GetFieldID(unitTypeClass, "isPowerup", "Z"), unitType.isPowerup());
    globalEnv->SetBooleanField(CurrentUnitType, globalEnv->GetFieldID(unitTypeClass, "isBeacon", "Z"), unitType.isBeacon());
    globalEnv->SetBooleanField(CurrentUnitType, globalEnv->GetFieldID(unitTypeClass, "isFlagBeacon", "Z"), unitType.isFlagBeacon());
    globalEnv->SetBooleanField(CurrentUnitType, globalEnv->GetFieldID(unitTypeClass, "isSpecialBuilding", "Z"), unitType.isSpecialBuilding());
    globalEnv->SetBooleanField(CurrentUnitType, globalEnv->GetFieldID(unitTypeClass, "isSpell", "Z"), unitType.isSpell());
    globalEnv->SetBooleanField(CurrentUnitType, globalEnv->GetFieldID(unitTypeClass, "producesCreep", "Z"), unitType.producesCreep());
    globalEnv->SetBooleanField(CurrentUnitType, globalEnv->GetFieldID(unitTypeClass, "producesLarva", "Z"), unitType.producesLarva());
    globalEnv->SetBooleanField(CurrentUnitType, globalEnv->GetFieldID(unitTypeClass, "isMineralField", "Z"), unitType.isMineralField());
    globalEnv->SetBooleanField(CurrentUnitType, globalEnv->GetFieldID(unitTypeClass, "isCritter", "Z"), unitType.isCritter());
    globalEnv->SetBooleanField(CurrentUnitType, globalEnv->GetFieldID(unitTypeClass, "canBuildAddon", "Z"), unitType.canBuildAddon());
    // set double fields
    globalEnv->SetDoubleField(CurrentUnitType, globalEnv->GetFieldID(unitTypeClass, "topSpeed", "D"), unitType.topSpeed());
    // set enum values
    jobject race = globalEnv->GetStaticObjectField(
        raceClass, globalEnv->GetStaticFieldID(raceClass, unitType.getRace().getName().c_str(), "Lorg/openbw/bwapi4j/type/Race;"));
    globalEnv->SetObjectField(CurrentUnitType, globalEnv->GetFieldID(unitTypeClass, "race", "Lorg/openbw/bwapi4j/type/Race;"), race);

    jobject requiredTech = globalEnv->GetStaticObjectField(
        techTypeClass, globalEnv->GetStaticFieldID(techTypeClass, unitType.requiredTech().getName().c_str(), "Lorg/openbw/bwapi4j/type/TechType;"));
    globalEnv->SetObjectField(CurrentUnitType, globalEnv->GetFieldID(unitTypeClass, "requiredTech", "Lorg/openbw/bwapi4j/type/TechType;"), requiredTech);

    jobject cloakingTech = globalEnv->GetStaticObjectField(
        techTypeClass, globalEnv->GetStaticFieldID(techTypeClass, unitType.cloakingTech().getName().c_str(), "Lorg/openbw/bwapi4j/type/TechType;"));
    globalEnv->SetObjectField(CurrentUnitType, globalEnv->GetFieldID(unitTypeClass, "cloakingTech", "Lorg/openbw/bwapi4j/type/TechType;"), cloakingTech);

    jobject armorUpgrade = globalEnv->GetStaticObjectField(
        upgradeTypeClass, globalEnv->GetStaticFieldID(upgradeTypeClass, unitType.armorUpgrade().getName().c_str(), "Lorg/openbw/bwapi4j/type/UpgradeType;"));
    globalEnv->SetObjectField(CurrentUnitType, globalEnv->GetFieldID(unitTypeClass, "armorUpgrade", "Lorg/openbw/bwapi4j/type/UpgradeType;"), armorUpgrade);

    jobject size = globalEnv->GetStaticObjectField(
        unitSizeTypeClass, globalEnv->GetStaticFieldID(unitSizeTypeClass, unitType.size().getName().c_str(), "Lorg/openbw/bwapi4j/type/UnitSizeType;"));
    globalEnv->SetObjectField(CurrentUnitType, globalEnv->GetFieldID(unitTypeClass, "size", "Lorg/openbw/bwapi4j/type/UnitSizeType;"), size);

    jobject groundWeapon = globalEnv->GetStaticObjectField(
        weaponTypeClass, globalEnv->GetStaticFieldID(weaponTypeClass, unitType.groundWeapon().getName().c_str(), "Lorg/openbw/bwapi4j/type/WeaponType;"));
    globalEnv->SetObjectField(CurrentUnitType, globalEnv->GetFieldID(unitTypeClass, "groundWeapon", "Lorg/openbw/bwapi4j/type/WeaponType;"), groundWeapon);

    jobject airWeapon = globalEnv->GetStaticObjectField(
        weaponTypeClass, globalEnv->GetStaticFieldID(weaponTypeClass, unitType.airWeapon().getName().c_str(), "Lorg/openbw/bwapi4j/type/WeaponType;"));
    globalEnv->SetObjectField(CurrentUnitType, globalEnv->GetFieldID(unitTypeClass, "airWeapon", "Lorg/openbw/bwapi4j/type/WeaponType;"), airWeapon);

    // set complex values
    jobject upgradesList = globalEnv->GetObjectField(CurrentUnitType, globalEnv->GetFieldID(unitTypeClass, "upgrades", "Ljava/util/ArrayList;"));
    for (BWAPI::UpgradeType upgradeType : unitType.upgrades()) {
      jobject upgradesMemberType = globalEnv->GetStaticObjectField(
          upgradeTypeClass, globalEnv->GetStaticFieldID(upgradeTypeClass, upgradeType.getName().c_str(), "Lorg/openbw/bwapi4j/type/UpgradeType;"));
      globalEnv->CallObjectMethod(upgradesList, arrayListClass_add, upgradesMemberType);
      globalEnv->DeleteLocalRef(upgradesMemberType);
    }
    globalEnv->DeleteLocalRef(upgradesList);

    jobject upgradesWhatList = globalEnv->GetObjectField(CurrentUnitType, globalEnv->GetFieldID(unitTypeClass, "upgradesWhat", "Ljava/util/ArrayList;"));
    for (BWAPI::UpgradeType upgradeType : unitType.upgradesWhat()) {
      jobject upgradesWhatMemberType = globalEnv->GetStaticObjectField(
          upgradeTypeClass, globalEnv->GetStaticFieldID(upgradeTypeClass, upgradeType.getName().c_str(), "Lorg/openbw/bwapi4j/type/UpgradeType;"));
      globalEnv->CallObjectMethod(upgradesWhatList, arrayListClass_add, upgradesWhatMemberType);
      globalEnv->DeleteLocalRef(upgradesWhatMemberType);
    }
    globalEnv->DeleteLocalRef(upgradesWhatList);

    jobject abilitiesList = globalEnv->GetObjectField(CurrentUnitType, globalEnv->GetFieldID(unitTypeClass, "abilities", "Ljava/util/ArrayList;"));
    for (BWAPI::TechType techType : unitType.abilities()) {
      jobject abilitiesMemberType = globalEnv->GetStaticObjectField(
          techTypeClass, globalEnv->GetStaticFieldID(techTypeClass, techType.getName().c_str(), "Lorg/openbw/bwapi4j/type/TechType;"));
      globalEnv->CallObjectMethod(abilitiesList, arrayListClass_add, abilitiesMemberType);
      globalEnv->DeleteLocalRef(abilitiesMemberType);
    }
    globalEnv->DeleteLocalRef(abilitiesList);

    jobject researchesWhatList = globalEnv->GetObjectField(CurrentUnitType, globalEnv->GetFieldID(unitTypeClass, "researchesWhat", "Ljava/util/ArrayList;"));
    for (BWAPI::TechType techType : unitType.researchesWhat()) {
      jobject researchesWhatMemberType = globalEnv->GetStaticObjectField(
          techTypeClass, globalEnv->GetStaticFieldID(techTypeClass, techType.getName().c_str(), "Lorg/openbw/bwapi4j/type/TechType;"));
      globalEnv->CallObjectMethod(researchesWhatList, arrayListClass_add, researchesWhatMemberType);
      globalEnv->DeleteLocalRef(researchesWhatMemberType);
    }
    globalEnv->DeleteLocalRef(researchesWhatList);

    jobject buildsWhatList = globalEnv->GetObjectField(CurrentUnitType, globalEnv->GetFieldID(unitTypeClass, "buildsWhat", "Ljava/util/ArrayList;"));
    for (BWAPI::UnitType unitType : unitType.buildsWhat()) {
      jobject buildsWhatMemberType = globalEnv->GetStaticObjectField(
          unitTypeClass, globalEnv->GetStaticFieldID(unitTypeClass, unitType.getName().c_str(), "Lorg/openbw/bwapi4j/type/UnitType;"));
      globalEnv->CallObjectMethod(buildsWhatList, arrayListClass_add, buildsWhatMemberType);
      globalEnv->DeleteLocalRef(buildsWhatMemberType);
    }
    globalEnv->DeleteLocalRef(buildsWhatList);

    // create a new Pair object and fill in UnitType,Integer
    jfieldID whatBuildsField = globalEnv->GetStaticFieldID(unitTypeClass, unitType.whatBuilds().first.getName().c_str(), "Lorg/openbw/bwapi4j/type/UnitType;");
    jobject whatBuildsType = globalEnv->GetStaticObjectField(unitTypeClass, whatBuildsField);

    jobject pairObject = globalEnv->NewObject(pairClass, pairClassConstructor, whatBuildsType, globalEnv->NewObject(integerClass, integerClassConstructor, unitType.whatBuilds().second));
    globalEnv->SetObjectField(CurrentUnitType, globalEnv->GetFieldID(unitTypeClass, "whatBuilds", "Lorg/openbw/bwapi4j/util/Pair;"), pairObject);

    // read existing requiredUnits map and put <UnitType,Integer> entries
    for (auto const &req : unitType.requiredUnits()) {
      globalEnv->CallObjectMethod(CurrentUnitType, unitTypeClass_addRequiredUnit, (jint)req.first.getID(), (jint)req.second);
    }
    globalEnv->DeleteLocalRef(CurrentUnitType);
  }
  LOGGER("done");
}
