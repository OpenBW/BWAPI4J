package org.openbw.bwapi4j.type;

import org.junit.Assert;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

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
