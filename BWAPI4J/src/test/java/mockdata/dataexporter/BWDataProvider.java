package mockdata.dataexporter;

public final class BWDataProvider {
  private BWDataProvider() {}

  public static void injectValues() throws Exception {
    UnitTypes.initializeUnitType();
    UpgradeTypes.initializeUpgradeType();
    TechTypes.initializeTechType();
    WeaponTypes.initializeWeaponType();
  }
}
