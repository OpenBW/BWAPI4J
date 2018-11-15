package bwem.unit;

import java.util.ArrayList;
import java.util.List;
import org.openbw.bwapi4j.Position;
import org.openbw.bwapi4j.TilePosition;
import org.openbw.bwapi4j.WalkPosition;
import org.openbw.bwapi4j.type.UnitType;
import org.openbw.bwapi4j.unit.Unit;

public class NeutralImpl implements Neutral {
  private final Unit bwapiUnit;
  protected Neutral nextStacked;
  private final List<WalkPosition> blockedAreas;

  protected NeutralImpl(final Unit unit) {
    this.bwapiUnit = unit;
    this.blockedAreas = new ArrayList<>();
  }

  public Resource IsResource() {
    return null;
  }

  public Mineral IsMineral() {
    return null;
  }

  public Geyser IsGeyser() {
    return null;
  }

  public StaticBuilding IsStaticBuilding() {
    return null;
  }

  public Unit Unit() {
    return bwapiUnit;
  }

  public UnitType Type() {
    return Unit().getType();
  }

  public Position Pos() {
    return Unit().getPosition();
  }

  public TilePosition TopLeft() {
    return Unit().getTilePosition();
  }

  public TilePosition BottomRight() {
    return TopLeft().add(Size());
  }

  public TilePosition Size() {
    return Type().tileSize();
  }

  public boolean Blocking() {
    return !blockedAreas.isEmpty();
  }

  public Neutral NextStacked() {
    return nextStacked;
  }

  public Neutral LastStacked() {
    Neutral top = this;
    while (top.NextStacked() != null) {
      top = top.NextStacked();
    }
    return top;
  }

  ////////////////////////////////////////////////////////////////////////////
  //	Details: The functions below are used by the BWEM's internals

  public void SetBlocking(final List<WalkPosition> blockedAreas) {
    if (blockedAreas != this.blockedAreas) {
      this.blockedAreas.clear();
      this.blockedAreas.addAll(blockedAreas);
    }
  }
}
