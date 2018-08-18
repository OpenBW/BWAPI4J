package bwem;

public class OldMarkableClassA extends OldMarkable<OldMarkableClassA> {
  private final int id;

  public OldMarkableClassA(final int id) {
    this.id = id;
  }

  public int getId() {
    return this.id;
  }
}
