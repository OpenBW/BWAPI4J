package org.openbw.bwapi4j.type;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import org.junit.Assert;
import org.junit.Ignore;
import org.junit.Test;
import org.openbw.bwapi4j.BW;
import org.openbw.bwapi4j.BWEventListener;
import org.openbw.bwapi4j.Player;
import org.openbw.bwapi4j.Position;
import org.openbw.bwapi4j.TilePosition;
import org.openbw.bwapi4j.unit.Unit;

/** These tests were ported from the original BWAPI unitTypesTest.cpp */
public class UnitTypeTest implements BWEventListener {

  private BW bw;

  private <T> boolean isUnorderedListEqual(List<T> expected, List<T> actual) {
    if (actual.size() != expected.size()) {
      return false;
    } else {
      for (int i = 0; i < expected.size(); ++i) {
        if (!actual.contains(expected.get(i))) {
          System.out.println("Not found in actual list: " + expected.get(i).toString());
          return false;
        }
      }
      return true;
    }
  }

  private void Assert_getName(final String expectedName, final UnitType unitType) {
    Assert.assertEquals(expectedName, unitType.toString());
  }

  private void Assert_getRace(final Race expectedRace, final UnitType unitType) {
    Assert.assertEquals(expectedRace, unitType.getRace());
  }

  private void Assert_whatBuilds(final UnitType expectedUnitType, final UnitType unitType) {
    Assert.assertEquals(expectedUnitType, unitType.whatBuilds().getFirst());
  }

  private void Assert_requiredUnits(
      final List<UnitType> expectedUnitTypes, final UnitType unitType) {
    Assert.assertTrue(isUnorderedListEqual(expectedUnitTypes, unitType.requiredUnits()));
  }

  private void Assert_requiredTech(final TechType expectedTechType, final UnitType unitType) {
    Assert.assertEquals(expectedTechType, unitType.requiredTech());
  }

  private void Assert_cloakingTech(final TechType expectedTechType, final UnitType unitType) {
    Assert.assertEquals(expectedTechType, unitType.cloakingTech());
  }

  private void Assert_abilities(final List<TechType> expectedTechTypes, final UnitType unitType) {
    Assert.assertTrue(isUnorderedListEqual(expectedTechTypes, unitType.abilities()));
  }

  private void Assert_upgrades(
      final List<UpgradeType> expectedUpgradeTypes, final UnitType unitType) {
    Assert.assertTrue(
        "Expected: "
            + expectedUpgradeTypes.toString()
            + ", Actual: "
            + unitType.upgrades().toString(),
        isUnorderedListEqual(expectedUpgradeTypes, unitType.upgrades()));
  }

  private void Assert_armorUpgrade(final UpgradeType expectedUpgradeType, final UnitType unitType) {
    Assert.assertEquals(expectedUpgradeType, unitType.armorUpgrade());
  }

  private void Assert_maxHitPoints(final int expectedMaxHitPoints, final UnitType unitType) {
    Assert.assertEquals(expectedMaxHitPoints, unitType.maxHitPoints());
  }

  private void Assert_maxShields(final int expectedMaxShields, final UnitType unitType) {
    Assert.assertEquals(expectedMaxShields, unitType.maxShields());
  }

  private void Assert_maxEnergy(final int expectedMaxEnergy, final UnitType unitType) {
    Assert.assertEquals(expectedMaxEnergy, unitType.maxEnergy());
  }

  private void Assert_armor(final int expectedArmor, final UnitType unitType) {
    Assert.assertEquals(expectedArmor, unitType.armor());
  }

  private void Assert_mineralPrice(final int expectedMineralPrice, final UnitType unitType) {
    Assert.assertEquals(expectedMineralPrice, unitType.mineralPrice());
  }

  private void Assert_gasPrice(final int expectedGasPrice, final UnitType unitType) {
    Assert.assertEquals(expectedGasPrice, unitType.gasPrice());
  }

  private void Assert_buildTime(final int expectedBuildTime, final UnitType unitType) {
    Assert.assertEquals(expectedBuildTime, unitType.buildTime());
  }

  private void Assert_supplyRequired(final int expectedSupplyRequired, final UnitType unitType) {
    Assert.assertEquals(expectedSupplyRequired, unitType.supplyRequired());
  }

  private void Assert_supplyProvided(final int expectedSupplyProvided, final UnitType unitType) {
    Assert.assertEquals(expectedSupplyProvided, unitType.supplyProvided());
  }

  private void Assert_spaceRequired(final int expectedSpaceRequired, final UnitType unitType) {
    Assert.assertEquals(expectedSpaceRequired, unitType.spaceRequired());
  }

  private void Assert_spaceProvided(final int expectedSpaceProvided, final UnitType unitType) {
    Assert.assertEquals(expectedSpaceProvided, unitType.spaceProvided());
  }

  private void Assert_buildScore(final int expectedBuildScore, final UnitType unitType) {
    Assert.assertEquals(expectedBuildScore, unitType.buildScore());
  }

  private void Assert_destroyScore(final int expectedDestroyScore, final UnitType unitType) {
    Assert.assertEquals(expectedDestroyScore, unitType.destroyScore());
  }

  private void Assert_size(final UnitSizeType expectedSize, final UnitType unitType) {
    Assert.assertEquals(expectedSize, unitType.size());
  }

  private void Assert_tileWidth(final int expectedTileWidth, final UnitType unitType) {
    Assert.assertEquals(expectedTileWidth, unitType.tileWidth());
  }

  private void Assert_tileHeight(final int expectedTileHeight, final UnitType unitType) {
    Assert.assertEquals(expectedTileHeight, unitType.tileHeight());
  }

  private void Assert_tileSize(final TilePosition expectedSize, final UnitType unitType) {
    Assert.assertEquals(expectedSize, unitType.tileSize());
  }

  private void Assert_dimensionLeft(final int expectedDimensionLeft, final UnitType unitType) {
    Assert.assertEquals(expectedDimensionLeft, unitType.dimensionLeft());
  }

  private void Assert_dimensionUp(final int expectedDimensionUp, final UnitType unitType) {
    Assert.assertEquals(expectedDimensionUp, unitType.dimensionUp());
  }

  private void Assert_dimensionRight(final int expectedDimensionRight, final UnitType unitType) {
    Assert.assertEquals(expectedDimensionRight, unitType.dimensionRight());
  }

  private void Assert_dimensionDown(final int expectedDimensionDown, final UnitType unitType) {
    Assert.assertEquals(expectedDimensionDown, unitType.dimensionDown());
  }

  private void Assert_width(final int expectedWidth, final UnitType unitType) {
    Assert.assertEquals(expectedWidth, unitType.width());
  }

  private void Assert_height(final int expectedHeight, final UnitType unitType) {
    Assert.assertEquals(expectedHeight, unitType.height());
  }

  private void Assert_seekRange(final int expectedSeekRange, final UnitType unitType) {
    Assert.assertEquals(expectedSeekRange, unitType.seekRange());
  }

  private void Assert_sightRange(final int expectedSightRange, final UnitType unitType) {
    Assert.assertEquals(expectedSightRange, unitType.sightRange());
  }

  private void Assert_groundWeapon(final WeaponType expectedWeaponType, final UnitType unitType) {
    Assert.assertEquals(expectedWeaponType, unitType.groundWeapon());
  }

  private void Assert_maxGroundHits(final int expectedMaxGroundHits, final UnitType unitType) {
    Assert.assertEquals(expectedMaxGroundHits, unitType.maxGroundHits());
  }

  private void Assert_airWeapon(final WeaponType expectedWeaponType, final UnitType unitType) {
    Assert.assertEquals(expectedWeaponType, unitType.airWeapon());
  }

  private void Assert_maxAirHits(final int expectedMaxAirHits, final UnitType unitType) {
    Assert.assertEquals(expectedMaxAirHits, unitType.maxAirHits());
  }

  private void Assert_topSpeed(final double expectedTopSpeed, final UnitType unitType) {
    Assert.assertTrue(Double.compare(expectedTopSpeed, unitType.topSpeed()) == 0);
  }

  private void Assert_acceleration(final int expectedAcceleration, final UnitType unitType) {
    Assert.assertEquals(expectedAcceleration, unitType.acceleration());
  }

  private void Assert_haltDistance(final int expectedHaltDistance, final UnitType unitType) {
    Assert.assertEquals(expectedHaltDistance, unitType.haltDistance());
  }

  private void Assert_turnRadius(final int expectedTurnRadius, final UnitType unitType) {
    Assert.assertEquals(expectedTurnRadius, unitType.turnRadius());
  }

  private void Assert_canProduce(final boolean expectedCanProduce, final UnitType unitType) {
    Assert.assertEquals(expectedCanProduce, unitType.canProduce());
  }

  private void Assert_canAttack(final boolean expectedCanAttack, final UnitType unitType) {
    Assert.assertEquals(expectedCanAttack, unitType.canAttack());
  }

  private void Assert_canMove(final boolean expectedCanMove, final UnitType unitType) {
    Assert.assertEquals(expectedCanMove, unitType.canMove());
  }

  private void Assert_isFlyer(final boolean expectedIsFlyer, final UnitType unitType) {
    Assert.assertEquals(expectedIsFlyer, unitType.isFlyer());
  }

  private void Assert_regeneratesHP(final boolean expectedRegeneratesHP, final UnitType unitType) {
    Assert.assertEquals(expectedRegeneratesHP, unitType.regeneratesHP());
  }

  private void Assert_isSpellcaster(final boolean expectedIsSpellcaster, final UnitType unitType) {
    Assert.assertEquals(expectedIsSpellcaster, unitType.isSpellcaster());
  }

  private void Assert_hasPermanentCloak(
      final boolean expectedHasPermanentCloak, final UnitType unitType) {
    Assert.assertEquals(expectedHasPermanentCloak, unitType.hasPermanentCloak());
  }

  private void Assert_isInvincible(final boolean expectedIsInvincible, final UnitType unitType) {
    Assert.assertEquals(expectedIsInvincible, unitType.isInvincible());
  }

  private void Assert_isOrganic(final boolean expectedIsOrganic, final UnitType unitType) {
    Assert.assertEquals(expectedIsOrganic, unitType.isOrganic());
  }

  private void Assert_isMechanical(final boolean expectedIsMechanical, final UnitType unitType) {
    Assert.assertEquals(expectedIsMechanical, unitType.isMechanical());
  }

  private void Assert_isRobotic(final boolean expectedIsRobotic, final UnitType unitType) {
    Assert.assertEquals(expectedIsRobotic, unitType.isRobotic());
  }

  private void Assert_isDetector(final boolean expectedIsDetector, final UnitType unitType) {
    Assert.assertEquals(expectedIsDetector, unitType.isDetector());
  }

  private void Assert_isResourceContainer(
      final boolean expectedIsResourceContainer, final UnitType unitType) {
    Assert.assertEquals(expectedIsResourceContainer, unitType.isResourceContainer());
  }

  private void Assert_isResourceDepot(
      final boolean expectedIsResourceDepot, final UnitType unitType) {
    Assert.assertEquals(expectedIsResourceDepot, unitType.isResourceDepot());
  }

  private void Assert_isRefinery(final boolean expectedIsRefinery, final UnitType unitType) {
    Assert.assertEquals(expectedIsRefinery, unitType.isRefinery());
  }

  private void Assert_isWorker(final boolean expectedIsWorker, final UnitType unitType) {
    Assert.assertEquals(expectedIsWorker, unitType.isWorker());
  }

  private void Assert_requiresPsi(final boolean expectedRequiresPsi, final UnitType unitType) {
    Assert.assertEquals(expectedRequiresPsi, unitType.requiresPsi());
  }

  private void Assert_requiresCreep(final boolean expectedRequiresCreep, final UnitType unitType) {
    Assert.assertEquals(expectedRequiresCreep, unitType.requiresCreep());
  }

  private void Assert_isTwoUnitsInOneEgg(
      final boolean expectedIsTwoUnitsInOneEgg, final UnitType unitType) {
    Assert.assertEquals(expectedIsTwoUnitsInOneEgg, unitType.isTwoUnitsInOneEgg());
  }

  private void Assert_isBurrowable(final boolean expectedIsBurrowable, final UnitType unitType) {
    Assert.assertEquals(expectedIsBurrowable, unitType.isBurrowable());
  }

  private void Assert_isCloakable(final boolean expectedIsCloakable, final UnitType unitType) {
    Assert.assertEquals(expectedIsCloakable, unitType.isCloakable());
  }

  private void Assert_isBuilding(final boolean expectedIsBuilding, final UnitType unitType) {
    Assert.assertEquals(expectedIsBuilding, unitType.isBuilding());
  }

  private void Assert_isAddon(final boolean expectedIsAddon, final UnitType unitType) {
    Assert.assertEquals(expectedIsAddon, unitType.isAddon());
  }

  private void Assert_isFlyingBuilding(
      final boolean expectedIsFlyingBuilding, final UnitType unitType) {
    Assert.assertEquals(expectedIsFlyingBuilding, unitType.isFlyingBuilding());
  }

  private void Assert_isNeutral(final boolean expectedIsNeutral, final UnitType unitType) {
    Assert.assertEquals(expectedIsNeutral, unitType.isNeutral());
  }

  private void Assert_isHero(final boolean expectedIsHero, final UnitType unitType) {
    Assert.assertEquals(expectedIsHero, unitType.isHero());
  }

  private void Assert_isPowerup(final boolean expectedIsPowerup, final UnitType unitType) {
    Assert.assertEquals(expectedIsPowerup, unitType.isPowerup());
  }

  private void Assert_isBeacon(final boolean expectedIsBeacon, final UnitType unitType) {
    Assert.assertEquals(expectedIsBeacon, unitType.isBeacon());
  }

  private void Assert_isFlagBeacon(final boolean expectedIsFlagBeacon, final UnitType unitType) {
    Assert.assertEquals(expectedIsFlagBeacon, unitType.isFlagBeacon());
  }

  private void Assert_isSpecialBuilding(
      final boolean expectedIsSpecialBuilding, final UnitType unitType) {
    Assert.assertEquals(expectedIsSpecialBuilding, unitType.isSpecialBuilding());
  }

  private void Assert_isSpell(final boolean expectedIsSpell, final UnitType unitType) {
    Assert.assertEquals(expectedIsSpell, unitType.isSpell());
  }

  private void Assert_producesCreep(final boolean expectedProducesCreep, final UnitType unitType) {
    Assert.assertEquals(expectedProducesCreep, unitType.producesCreep());
  }

  private void Assert_producesLarva(final boolean expectedProducesLarva, final UnitType unitType) {
    Assert.assertEquals(expectedProducesLarva, unitType.producesLarva());
  }

  private void Assert_isMineralField(
      final boolean expectedIsMineralField, final UnitType unitType) {
    Assert.assertEquals(expectedIsMineralField, unitType.isMineralField());
  }

  private void Assert_isCritter(final boolean expectedIsCritter, final UnitType unitType) {
    Assert.assertEquals(expectedIsCritter, unitType.isCritter());
  }

  private void Assert_canBuildAddon(final boolean expectedCanBuildAddon, final UnitType unitType) {
    Assert.assertEquals(expectedCanBuildAddon, unitType.canBuildAddon());
  }
  //    private void Assert_buildsWhat(final List<UnitType> expectedUnitTypes, final UnitType
  // unitType) { Assert.assertTrue(isListEqual(expectedUnitTypes, unitType.buildsWhat())); } //TODO:
  // UnitType.buildsWhat() is currently not implemented.
  private void Assert_researchesWhat(
      final List<TechType> expectedTechTypes, final UnitType unitType) {
    Assert.assertTrue(isUnorderedListEqual(expectedTechTypes, unitType.researchesWhat()));
  }

  private void Assert_upgradesWhat(
      final List<UpgradeType> expectedUpgradeTypes, final UnitType unitType) {
    Assert.assertTrue(isUnorderedListEqual(expectedUpgradeTypes, unitType.upgradesWhat()));
  }

  private void terranTest() {
    {
      final UnitType unitType = UnitType.Terran_Marine;
      Assert_getName("Terran_Marine", unitType);
      Assert_getRace(Race.Terran, unitType);
      Assert_whatBuilds(UnitType.Terran_Barracks, unitType);
      Assert_requiredUnits(Arrays.asList(UnitType.Terran_Barracks), unitType);
      Assert_requiredTech(TechType.None, unitType);
      Assert_cloakingTech(TechType.None, unitType);
      Assert_abilities(Arrays.asList(TechType.Stim_Packs), unitType);
      Assert_upgrades(
          Arrays.asList(
              UpgradeType.Terran_Infantry_Armor,
              UpgradeType.U_238_Shells,
              UpgradeType.Terran_Infantry_Weapons),
          unitType);
      Assert_armorUpgrade(UpgradeType.Terran_Infantry_Armor, unitType);
      Assert_maxHitPoints(40, unitType);
      Assert_maxShields(0, unitType);
      Assert_maxEnergy(0, unitType);
      Assert_armor(0, unitType);
      Assert_mineralPrice(50, unitType);
      Assert_gasPrice(0, unitType);
      Assert_buildTime(360, unitType);
      Assert_supplyRequired(2, unitType);
      Assert_supplyProvided(0, unitType);
      Assert_spaceRequired(1, unitType);
      Assert_spaceProvided(0, unitType);
      Assert_buildScore(50, unitType);
      Assert_destroyScore(100, unitType);
      Assert_size(UnitSizeType.Small, unitType);
      Assert_tileWidth(1, unitType);
      Assert_tileHeight(1, unitType);
      Assert_tileSize(new TilePosition(1, 1), unitType);
      Assert.assertEquals(
          new TilePosition(unitType.tileWidth(), unitType.tileHeight()), unitType.tileSize());
      Assert_dimensionLeft(8, unitType);
      Assert_dimensionUp(9, unitType);
      Assert_dimensionRight(8, unitType);
      Assert_dimensionDown(10, unitType);
      Assert_width(17, unitType);
      Assert.assertEquals(
          unitType.dimensionLeft() + 1 + unitType.dimensionRight(), unitType.width());
      Assert_height(20, unitType);
      Assert.assertEquals(unitType.dimensionUp() + 1 + unitType.dimensionDown(), unitType.height());
      Assert_seekRange(0, unitType);
      Assert_sightRange(224, unitType);
      Assert_groundWeapon(WeaponType.Gauss_Rifle, unitType);
      Assert_maxGroundHits(1, unitType);
      Assert_airWeapon(WeaponType.Gauss_Rifle, unitType);
      Assert_maxAirHits(1, unitType);
      Assert_topSpeed(4.0, unitType);
      Assert_acceleration(1, unitType);
      Assert_haltDistance(1, unitType);
      Assert_turnRadius(40, unitType);
      Assert_canProduce(false, unitType);
      Assert_canAttack(true, unitType);
      Assert_canMove(true, unitType);
      Assert_isFlyer(false, unitType);
      Assert_regeneratesHP(false, unitType);
      Assert_isSpellcaster(false, unitType);
      Assert_hasPermanentCloak(false, unitType);
      Assert_isInvincible(false, unitType);
      Assert_isOrganic(true, unitType);
      Assert_isMechanical(false, unitType);
      Assert_isRobotic(false, unitType);
      Assert_isDetector(false, unitType);
      Assert_isResourceContainer(false, unitType);
      Assert_isResourceDepot(false, unitType);
      Assert_isRefinery(false, unitType);
      Assert_isWorker(false, unitType);
      Assert_requiresPsi(false, unitType);
      Assert_requiresCreep(false, unitType);
      Assert_isTwoUnitsInOneEgg(false, unitType);
      Assert_isBurrowable(false, unitType);
      Assert_isCloakable(false, unitType);
      Assert_isBuilding(false, unitType);
      Assert_isAddon(false, unitType);
      Assert_isFlyingBuilding(false, unitType);
      Assert_isNeutral(false, unitType);
      Assert_isHero(false, unitType);
      Assert_isPowerup(false, unitType);
      Assert_isBeacon(false, unitType);
      Assert_isFlagBeacon(false, unitType);
      Assert_isSpecialBuilding(false, unitType);
      Assert_isSpell(false, unitType);
      Assert_producesCreep(false, unitType);
      Assert_producesLarva(false, unitType);
      Assert_isMineralField(false, unitType);
      Assert_isCritter(false, unitType);
      Assert_canBuildAddon(false, unitType);
      //            AssertSetEquals({}, t.buildsWhat()); //TODO
      Assert_researchesWhat(new ArrayList<>(), unitType);
      Assert_upgradesWhat(new ArrayList<>(), unitType);
    }
  }

  private void zergTest() {
    // TODO
    throw new UnsupportedOperationException();
  }

  private void protossTest() {
    // TODO
    throw new UnsupportedOperationException();
  }

  @Ignore
  @Test
  public void unitTypeTest() {
    this.bw = new BW(this);
    this.bw.startGame();

    terranTest();
    //        zergTest();
    //        protossTest();
  }

  @Test
  public void listContainsTest() {
    final List<UnitType> unitTypes = new ArrayList<>();

    unitTypes.add(UnitType.Terran_Command_Center);
    unitTypes.add(UnitType.Terran_Marine);
    unitTypes.add(UnitType.Terran_SCV);

    Assert.assertTrue(unitTypes.contains(UnitType.Terran_Command_Center));
    Assert.assertTrue(unitTypes.contains(UnitType.Terran_Marine));
    Assert.assertTrue(unitTypes.contains(UnitType.Terran_SCV));

    Assert.assertFalse(unitTypes.contains(UnitType.Zerg_Overlord));
    Assert.assertFalse(unitTypes.contains(UnitType.Terran_Barracks));
    Assert.assertFalse(unitTypes.contains(UnitType.Protoss_Probe));
  }

  @Override
  public void onStart() {
    this.bw.exit();
    this.bw.getInteractionHandler().leaveGame();
  }

  @Override
  public void onEnd(boolean isWinner) {}

  @Override
  public void onFrame() {}

  @Override
  public void onSendText(String text) {}

  @Override
  public void onReceiveText(Player player, String text) {}

  @Override
  public void onPlayerLeft(Player player) {}

  @Override
  public void onNukeDetect(Position target) {}

  @Override
  public void onUnitDiscover(Unit unit) {}

  @Override
  public void onUnitEvade(Unit unit) {}

  @Override
  public void onUnitShow(Unit unit) {}

  @Override
  public void onUnitHide(Unit unit) {}

  @Override
  public void onUnitCreate(Unit unit) {}

  @Override
  public void onUnitDestroy(Unit unit) {}

  @Override
  public void onUnitMorph(Unit unit) {}

  @Override
  public void onUnitRenegade(Unit unit) {}

  @Override
  public void onSaveGame(String gameName) {}

  @Override
  public void onUnitComplete(Unit unit) {}
}
