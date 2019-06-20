// Original work Copyright (c) 2015, 2017, Igor Dimitrijevic
// Modified work Copyright (c) 2017-2018 OpenBW Team

//////////////////////////////////////////////////////////////////////////
//
// This file is part of the BWEM Library.
// BWEM is free software, licensed under the MIT/X11 License.
// A copy of the license is provided with the library in the LICENSE file.
// Copyright (c) 2015, 2017, Igor Dimitrijevic
//
//////////////////////////////////////////////////////////////////////////

package bwem.unit;

import bwem.area.Area;
import bwem.map.Map;
import bwem.map.MapImpl;
import bwem.map.TerrainDataInitializer;
import bwem.tile.Tile;
import bwem.tile.TileImpl;
import java.util.ArrayList;
import java.util.List;

import bwem.util.Asserts;
import org.openbw.bwapi4j.Position;
import org.openbw.bwapi4j.TilePosition;
import org.openbw.bwapi4j.WalkPosition;
import org.openbw.bwapi4j.unit.Unit;

public abstract class NeutralImpl implements Neutral {
  private final Unit bwapiUnit;
  private final Position pos;
  private final TilePosition topLeft;
  private final TilePosition tileSize;
  private final Map map;
  private Neutral nextStacked = null;
  private List<WalkPosition> blockedAreas = new ArrayList<>();

  protected NeutralImpl(final Unit unit, final Map map) {
    this.bwapiUnit = unit;
    this.map = map;
    this.pos = unit.getInitialPosition();
    this.topLeft = unit.getInitialTilePosition();
    this.tileSize = unit.getType().tileSize();

    // TODO:
    //        if (u->getType() == Special_Right_Pit_Door) ++topLeft.x;

    putOnTiles();
  }

  // protected:
  //    Neutral::~Neutral()
  //    {
  //        try
  //        {
  //            removeFromTiles();
  //
  //            if (blocking())
  //                MapImpl::Get(getMap())->onBlockingNeutralDestroyed(this);
  //        }
  //        catch(...)
  //        {
  //            bwem_assert(false);
  //        }
  //    }
  public void simulateCPPObjectDestructor() {
    removeFromTiles();

    if (isBlocking()) {
      final Map map = getMap();
      if (map instanceof MapImpl) {
        ((MapImpl) map).onBlockingNeutralDestroyed(this);
      } else {
        throw new IllegalStateException(
            "expected class: "
                + MapImpl.class.getName()
                + ", actual class: "
                + map.getClass().getName());
      }
    }
  }

  @Override
  public Unit getUnit() {
    return this.bwapiUnit;
  }

  @Override
  public Position getCenter() {
    return this.pos;
  }

  @Override
  public TilePosition getTopLeft() {
    return this.topLeft;
  }

  @Override
  public TilePosition getBottomRight() {
    return this.topLeft.add(this.tileSize).subtract(new TilePosition(1, 1));
  }

  @Override
  public TilePosition getSize() {
    return this.tileSize;
  }

  @Override
  public boolean isBlocking() {
    return !this.blockedAreas.isEmpty();
  }

  @Override
  public List<Area> getBlockedAreas() {
    final List<Area> blockedAreas = new ArrayList<>();
    for (final WalkPosition w : this.blockedAreas) {
      final Area area = getMap().getArea(w);

      if (area != null) {
        blockedAreas.add(getMap().getArea(w));
      } else {
//        bwem_assert_plus(area, std::string("Walk position(") + my_to_string(w) + ") does not belongs to any area. Either it is non-walkable, or does not belong to any area.");
//        Asserts.bwem_assert(false, "WalkPosition " + w.toString() + " either does not belong to any area or it is unwalkable");
      }
    }
    return blockedAreas;
  }

  @Override
  public Neutral getNextStacked() {
    return this.nextStacked;
  }

  // Returns the last Neutral stacked over this Neutral, if ever.
  public Neutral getLastStacked() {
    Neutral topNeutral = this;
    while (topNeutral.getNextStacked() != null) {
      topNeutral = topNeutral.getNextStacked();
    }
    return topNeutral;
  }

  public void setBlocking(final List<WalkPosition> blockedAreas) {
    // bwem_assert(m_blockedAreas.empty() && !blockedAreas.empty());
    Asserts.bwem_assert(this.blockedAreas.isEmpty() && !blockedAreas.isEmpty());

    this.blockedAreas = blockedAreas;
  }

  public boolean isSameUnitTypeAs(Neutral neutral) {
    return this.getUnit().getClass().getName().equals(neutral.getUnit().getClass().getName());
  }

  private void putOnTiles() {
    //        bwem_assert(!pNextStacked);
    Asserts.bwem_assert(getNextStacked() == null);

    for (int dy = 0; dy < getSize().getY(); ++dy)
      for (int dx = 0; dx < getSize().getX(); ++dx) {
        final Tile deltaTile =
            ((TerrainDataInitializer) getMap().getData())
                .getTile_(getTopLeft().add(new TilePosition(dx, dy)));
        if (deltaTile.getNeutral() == null) {
          ((TileImpl) deltaTile).addNeutral(this);
        } else {
          final Neutral topNeutral = deltaTile.getNeutral().getLastStacked();

          if (topNeutral == null || !getTopLeft().equals(topNeutral.getTopLeft()) || !getBottomRight().equals(topNeutral.getBottomRight())) {
            continue;
          }

//          bwem_assert(this != tile.GetNeutral());
          Asserts.bwem_assert(!this.equals(deltaTile.getNeutral()));

//          bwem_assert(this != pTop);
          Asserts.bwem_assert(!this.equals(topNeutral));

//          bwem_assert(!pTop->IsGeyser());
          Asserts.bwem_assert(!topNeutral.getClass().getName().equals(Geyser.class.getName()));

//          bwem_assert_plus(pTop->Type() == Type(), "stacked neutrals have different types: " + pTop->Type().getName() + " / " + Type().getName() + " at position " + my_to_string(pTop->TopLeft()));
          Asserts.bwem_assert(((NeutralImpl) topNeutral).isSameUnitTypeAs(this), "Stacked Neutral objects have different types: top="
              + topNeutral.getClass().getName()
              + ", this="
              + this.getClass().getName());

//          bwem_assert_plus(pTop->TopLeft() == TopLeft(), "stacked neutrals not aligned: " + my_to_string(pTop->TopLeft()) + " / " + my_to_string(TopLeft()) + ", type is " + pTop->Type().getName());
          Asserts.bwem_assert(topNeutral.getTopLeft().equals(getTopLeft()), "Stacked Neutral objects not aligned: top="
              + topNeutral.toString()
              + ", this="
              + getTopLeft().toString());

//          bwem_assert((dx == 0) && (dy == 0));
          Asserts.bwem_assert((dx == 0) && (dy == 0));

          ((NeutralImpl) topNeutral).nextStacked = this;
          return;
        }
      }
  }

  /**
   * Warning: Do not use this function outside of BWEM's internals. This method is designed to be
   * called from the "~Neutral" destructor in C++.
   */
  private void removeFromTiles() {
    for (int dy = 0; dy < getSize().getY(); ++dy)
      for (int dx = 0; dx < getSize().getX(); ++dx) {
        final Tile tile =
            ((TerrainDataInitializer) getMap().getData())
                .getTile_(getTopLeft().add(new TilePosition(dx, dy)));
        //            bwem_assert(tile.GetNeutral());
        Asserts.bwem_assert(tile.getNeutral() != null);

        if (tile.getNeutral().equals(this)) {
          ((TileImpl) tile).removeNeutral(this);
          if (this.nextStacked != null) {
            ((TileImpl) tile).addNeutral(this.nextStacked);
          }
        } else {
          Neutral prevStacked = tile.getNeutral();

          while (prevStacked != null && !this.equals(prevStacked.getNextStacked())) {
            prevStacked = prevStacked.getNextStacked();
          }

          // bwem_assert((dx == 0) && (dy == 0));
          Asserts.bwem_assert((dx == 0) && (dy == 0));

          if (prevStacked != null) {
            //                    bwem_assert(pPrevStacked->Type() == Type());
            Asserts.bwem_assert(((NeutralImpl) prevStacked).isSameUnitTypeAs(this));

            //                    bwem_assert(pPrevStacked->topLeft() == topLeft());
            Asserts.bwem_assert(prevStacked.getTopLeft().equals(getTopLeft()));

            ((NeutralImpl) prevStacked).nextStacked = nextStacked;
          }

          this.nextStacked = null;
          return;
        }
      }

    this.nextStacked = null;
  }

  protected Map getMap() {
    return this.map;
  }

  @Override
  public boolean equals(final Object object) {
    if (this == object) {
      return true;
    } else if (!(object instanceof Neutral)) {
      return false;
    } else {
      final Neutral that = (Neutral) object;
      return (getUnit().getId() == that.getUnit().getId());
    }
  }

  @Override
  public int hashCode() {
    return getUnit().hashCode();
  }
}
