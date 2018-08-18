package bwem;

public class OldMarkableClassB extends OldMarkable<OldMarkableClassB> {
  private final int id;

  public OldMarkableClassB(final int id) {
    this.id = id;
  }

  public int getId() {
    return this.id;
  }
}
