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

#include "BridgeData.h"

#ifdef _WIN32
#define _USE_MATH_DEFINES
#endif
#include <math.h>

const double BridgeData::RADIANS_TO_DEGREES = 180.0 / M_PI;
const double BridgeData::DECIMAL_PRESERVATION_SCALE = 100.0;
const int BridgeData::MISSING_UNIT_ID = -1;

BridgeData::BridgeData() : _index(0) {
  for (size_t i = 0; i < INT_BUF_SIZE; ++i) {
    intBuf[i] = 0;
  }
}

void BridgeData::reset() { _index = 0; }

void BridgeData::add(const int val) { intBuf[_index++] = val; }

void BridgeData::add(const size_t val) { add(int(val)); }

void BridgeData::add(const bool b) { add(b ? 1 : 0); }

int BridgeData::getIndex() const { return _index; }

void BridgeData::addFields(const BWAPI::TilePosition &tilePosition) {
  add(tilePosition.x);
  add(tilePosition.y);
}

void BridgeData::addFields(const BWAPI::WalkPosition &walkPosition) {
  add(walkPosition.x);
  add(walkPosition.y);
}

void BridgeData::addFields(const BWAPI::Position &position) {
  add(position.x);
  add(position.y);
}

void BridgeData::addId(const BWAPI::UnitType &unitType) { add(unitType.getID()); }

void BridgeData::addId(const BWAPI::Unit &unit) { add(unit ? unit->getID() : MISSING_UNIT_ID); }

void BridgeData::addId(const BWAPI::Player &player) { add(player ? player->getID() : MISSING_UNIT_ID); }

void BridgeData::addId(const BWAPI::Bullet &bullet) { add(bullet ? bullet->getID() : MISSING_UNIT_ID); }

void BridgeData::addId(const BWAPI::BulletType &bulletType) { add(bulletType.getID()); }

void BridgeData::addId(const BWAPI::UnitCommand &unitCommand) { add(unitCommand.getType().getID()); }

void BridgeData::addId(const BWAPI::TechType &techType) { add(techType.getID()); }

void BridgeData::addId(const BWAPI::UpgradeType &upgradeType) { add(upgradeType.getID()); }

void BridgeData::addId(const BWAPI::Order &order) { add(order.getID()); }

void BridgeData::addFields(const BWAPI::Bullet &bullet) {
  add(bullet->exists());
  add(toPreservedDouble(toPreservedBwapiAngle(bullet->getAngle())));
  addId(bullet);
  addId(bullet->getPlayer());
  addFields(bullet->getPosition());
  add(bullet->getRemoveTimer());
  addId(bullet->getSource());
  addId(bullet->getTarget());
  addFields(bullet->getTargetPosition());
  addId(bullet->getType());
  add(toPreservedDouble(bullet->getVelocityX()));
  add(toPreservedDouble(bullet->getVelocityY()));
  add(bullet->isVisible());
}

void BridgeData::addFields(const BWAPI::Unit &unit) {
  add(unit->getID());
  add(unit->getReplayID());
  addId(unit->getPlayer());
  addId(unit->getType());
  addFields(unit->getPosition());
  addFields(unit->getTilePosition());
  add(toPreservedDouble(toPreservedBwapiAngle(unit->getAngle())));
  add(toPreservedDouble(unit->getVelocityX()));
  add(toPreservedDouble(unit->getVelocityY()));
  add(unit->getHitPoints());
  add(unit->getShields());
  add(unit->getEnergy());
  add(unit->getResources());
  add(unit->getResourceGroup());
  add(unit->getLastCommandFrame());
  addId(unit->getLastCommand());

  // getLastAttackingPlayer doesn't work as documented, have to check for "None" player
  const int lastAttackingPlayerId = (unit->getLastAttackingPlayer() && unit->getLastAttackingPlayer()->getType() != BWAPI::PlayerTypes::None)
                                        ? unit->getLastAttackingPlayer()->getID()
                                        : MISSING_UNIT_ID;
  add(lastAttackingPlayerId);

  addId(unit->getInitialType());
  addFields(unit->getInitialPosition());
  addFields(unit->getInitialTilePosition());
  add(unit->getInitialHitPoints());
  add(unit->getInitialResources());
  add(unit->getKillCount());
  add(unit->getAcidSporeCount());
  add(unit->getInterceptorCount());
  add(unit->getScarabCount());
  add(unit->getSpiderMineCount());
  add(unit->getGroundWeaponCooldown());
  add(unit->getAirWeaponCooldown());
  add(unit->getSpellCooldown());
  add(unit->getDefenseMatrixPoints());
  add(unit->getDefenseMatrixTimer());
  add(unit->getEnsnareTimer());
  add(unit->getIrradiateTimer());
  add(unit->getLockdownTimer());
  add(unit->getMaelstromTimer());
  add(unit->getOrderTimer());
  add(unit->getPlagueTimer());
  add(unit->getRemoveTimer());
  add(unit->getStasisTimer());
  add(unit->getStimTimer());
  addId(unit->getBuildType());

  const auto &trainingQueue = unit->getTrainingQueue();
  const auto trainingQueueSize = trainingQueue.size();
  add(trainingQueueSize);

  addId(unit->getTech());
  addId(unit->getUpgrade());
  add(unit->getRemainingBuildTime());
  add(unit->getRemainingTrainTime());
  add(unit->getRemainingResearchTime());
  add(unit->getRemainingUpgradeTime());
  addId(unit->getBuildUnit());
  addId(unit->getTarget());
  addFields(unit->getTargetPosition());
  addId(unit->getOrder());
  addId(unit->getOrderTarget());
  addId(unit->getSecondaryOrder());
  addFields(unit->getRallyPosition());
  addId(unit->getRallyUnit());
  addId(unit->getAddon());
  addId(unit->getNydusExit());
  addId(unit->getTransport());
  add(unit->getLoadedUnits().size());  // see separate getLoadedUnits method
  addId(unit->getCarrier());           // see getInterceptorCount and separate getInterceptors method
  addId(unit->getHatchery());
  add(unit->getLarva().size());  // see separate getLarva method
  addId(unit->getPowerUp());
  add(unit->exists());
  add(unit->hasNuke());
  add(unit->isAccelerating());
  add(unit->isAttacking());
  add(unit->isAttackFrame());
  add(unit->isBeingConstructed());
  add(unit->isBeingGathered());
  add(unit->isBeingHealed());
  add(unit->isBlind());
  add(unit->isBraking());
  add(unit->isBurrowed());
  add(unit->isCarryingGas());
  add(unit->isCarryingMinerals());
  add(unit->isCloaked());
  add(unit->isCompleted());
  add(unit->isConstructing());
  add(unit->isDefenseMatrixed());
  add(unit->isDetected());
  add(unit->isEnsnared());
  add(unit->isFollowing());
  add(unit->isGatheringGas());
  add(unit->isGatheringMinerals());
  add(unit->isHallucination());
  add(unit->isHoldingPosition());
  add(unit->isIdle());
  add(unit->isInterruptible());
  add(unit->isInvincible());
  add(unit->isIrradiated());
  add(unit->isLifted());
  add(unit->isLoaded());
  add(unit->isLockedDown());
  add(unit->isMaelstrommed());
  add(unit->isMorphing());
  add(unit->isMoving());
  add(unit->isParasited());
  add(unit->isPatrolling());
  add(unit->isPlagued());
  add(unit->isRepairing());
  add(unit->isSelected());
  add(unit->isSieged());
  add(unit->isStartingAttack());
  add(unit->isStasised());
  add(unit->isStimmed());
  add(unit->isStuck());
  add(unit->isTraining());
  add(unit->isUnderAttack());
  add(unit->isUnderDarkSwarm());
  add(unit->isUnderDisruptionWeb());
  add(unit->isUnderStorm());
  add(unit->isPowered());
  add(unit->isUpgrading());
  add(unit->isVisible());
  add(unit->isResearching());
  add(unit->isFlying());
  addFields(unit->getOrderTargetPosition);

  // TODO: Refactor and reduce duplicated code with loaded units.
  /* Training Queue */ {
    const size_t maxTrainingQueueSize = 5;
    for (size_t i = 0; i < trainingQueueSize; ++i) {
      const auto &unitType = trainingQueue[i];
      addId(unitType.getID);
    }
    const size_t remainingInQueue = maxTrainingQueueSize - trainingQueueSize;
    for (size_t i = 0; i < remainingInQueue; ++i) {
      add(MISSING_UNIT_ID);
    }
  }

  add(unit->getSpaceRemaining());

  // TODO: Refactor and reduce duplicated code with training queues.
  /* Loaded Units */ {
    const size_t maxLoadedUnitsCount = 8;

    const auto &loadedUnits = unit->getLoadedUnits();
    const size_t loadedUnitsCount = loadedUnits.size();

    for (const auto &loadedUnit : loadedUnits) {
      addId(loadedUnit);
    }

    const size_t unusedLoadedUnitSlots = maxLoadedUnitsCount - loadedUnitsCount;
    for (size_t i = 0; i < unusedLoadedUnitSlots; ++i) {
      add(MISSING_UNIT_ID);
    }
  }
}

double BridgeData::toDegrees(const double radians) { return radians * RADIANS_TO_DEGREES; }

// BWAPI 4.2.0:
// https://github.com/bwapi/bwapi/blob/59b14af21b3c881ce06af8b1ea1d63fa3c8b2df0/bwapi/BWAPI/Source/BWAPI/UnitUpdate.cpp#L206-L212
// https://github.com/bwapi/bwapi/blob/59b14af21b3c881ce06af8b1ea1d63fa3c8b2df0/bwapi/BWAPI/Source/BWAPI/BulletImpl.cpp#L93-L97
double BridgeData::toPreservedBwapiAngle(const double angle) { return (angle * 128.0 / M_PI); }

int BridgeData::toPreservedDouble(const double d) { return static_cast<int>(DECIMAL_PRESERVATION_SCALE * d); }
