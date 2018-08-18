package org.openbw.bwapi4j.type;

import java.util.ArrayList;
import java.util.List;
import org.junit.Assert;
import org.junit.Test;

public class UpgradeTypeTest {
  @Test
  public void listContainsTest() {
    final List<UpgradeType> upgradeTypes = new ArrayList<>();

    upgradeTypes.add(UpgradeType.Terran_Infantry_Weapons);
    upgradeTypes.add(UpgradeType.Terran_Infantry_Armor);
    upgradeTypes.add(UpgradeType.U_238_Shells);

    Assert.assertTrue(upgradeTypes.contains(UpgradeType.Terran_Infantry_Weapons));
    Assert.assertTrue(upgradeTypes.contains(UpgradeType.Terran_Infantry_Armor));
    Assert.assertTrue(upgradeTypes.contains(UpgradeType.U_238_Shells));

    Assert.assertFalse(upgradeTypes.contains(UpgradeType.Zerg_Flyer_Attacks));
    Assert.assertFalse(upgradeTypes.contains(UpgradeType.Adrenal_Glands));
    Assert.assertFalse(upgradeTypes.contains(UpgradeType.Charon_Boosters));
  }
}
