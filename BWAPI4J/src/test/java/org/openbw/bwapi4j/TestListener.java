package org.openbw.bwapi4j;

import bwapi.Flag;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import org.openbw.bwapi4j.type.Color;
import org.openbw.bwapi4j.type.Race;
import org.openbw.bwapi4j.type.UnitType;
import org.openbw.bwapi4j.unit.Unit;

public class TestListener implements BWEventListener {
  private static final int SCREEN_WIDTH =
      640; // only used for optimized shape drawing within the screen area
  private static final int SCREEN_HEIGHT =
      480; // only used for optimized shape drawing within the screen area
  private static final Position SCREEN_SIZE = new Position(SCREEN_WIDTH, SCREEN_HEIGHT);

  private BW bw; // main game object

  private Player self;
  private List<Unit> workers;

  /**
   * Tests if the specified position is within the current viewport area. Useful for ignoring shape
   * drawing calls if the specified position is off screen.
   */
  private boolean isOnScreen(final Position position) {
    final Position topLeftScreenPosition = bw.getInteractionHandler().getScreenPosition();
    final Position bottomRightScreenPosition = topLeftScreenPosition.add(SCREEN_SIZE);
    return (position.getX() >= topLeftScreenPosition.getX()
        && position.getY() >= topLeftScreenPosition.getY()
        && position.getX() < bottomRightScreenPosition.getX()
        && position.getY() < bottomRightScreenPosition.getY());
  }

  @Override
  public void onStart() {
    try {
      workers = new ArrayList<>();

      System.out.println("onStart");

      // Hello World!
      bw.getInteractionHandler().sendText("hello, world");

      // Print the map name.
      bw.getInteractionHandler()
          .printf(
              "The map is "
                  + bw.getBWMap().mapName()
                  + "! Size: "
                  + bw.getBWMap().mapWidth()
                  + "x"
                  + bw.getBWMap().mapHeight());

      // Enable the UserInput flag, which allows us to manually control units and type messages.
      bw.getInteractionHandler().enableFlag(Flag.UserInput);

      // Uncomment the following line and the bot will know about everything through the fog of war
      // (cheat).
      // bw.getInteractionHandler().enableCompleteMapInformation();

      self = bw.getInteractionHandler().self();

      // Compile list of workers.
      for (final Unit u : bw.getUnits(self)) {
        if (u.getType().isWorker()) {
          if (!workers.contains(u)) {
            workers.add(u);
          }
        }
      }

      /* Basic gamestart worker auto-mine */ {
        final List<Unit> unassignedMineralPatches = bw.getMinerals();
        final List<Unit> unassignedWorkers = new ArrayList<>(workers);
        unassignedMineralPatches.sort(
            new UnitDistanceComparator(self.getStartLocation().toPosition()));
        while (!unassignedWorkers.isEmpty() && !unassignedMineralPatches.isEmpty()) {
          final Unit unassignedWorker = unassignedWorkers.remove(0);
          final Unit unassignedMineralPatch = unassignedMineralPatches.remove(0);
          unassignedWorker.gather(unassignedMineralPatch);
        }
      }
    } catch (Exception e) {
      e.printStackTrace();
      System.exit(1);
    }
  }

  @Override
  public void onEnd(boolean isWinner) {
    System.out.println("onEnd: winner: " + isWinner);

    bw.exit();
  }

  @Override
  public void onFrame() {
    try {
      // Send idle workers to mine at the closest mineral patch.
      for (final Unit worker : workers) {
        if (worker.isIdle()) {
          Unit closestMineralPatch = null;
          for (final Unit mineralPatch : bw.getMinerals()) {
            if (closestMineralPatch == null) {
              closestMineralPatch = mineralPatch;
            } else {
              if (mineralPatch.getPosition().getDistance(self.getStartLocation().toPosition())
                  < closestMineralPatch
                      .getPosition()
                      .getDistance(self.getStartLocation().toPosition())) {
                closestMineralPatch = mineralPatch;
              }
            }
          }
          worker.gather(closestMineralPatch);
        }
      }

      /* Train an SCV at every Command Center. */ {
        if (self.getRace() == Race.Terran) {
          for (final Unit u : bw.getUnits(self)) {
            if (u.getType() == UnitType.Terran_Command_Center) {
              if (!u.isTraining()) {
                u.train(UnitType.Terran_SCV);
              }
            }
          }
        }
      }

      /* Highlight workers. */ {
        for (final Unit worker : workers) {
          final Position tileSize =
              new TilePosition(worker.getType().tileWidth(), worker.getType().tileHeight())
                  .toPosition();
          final Position topLeft =
              worker.getPosition().subtract(tileSize.divide(new Position(2, 2)));
          final Position bottomRight = topLeft.add(tileSize);
          if (isOnScreen(topLeft)) {
            bw.getMapDrawer().drawBoxMap(topLeft, bottomRight, Color.BROWN);
          }
        }
      }

      /* Draw mouse position debug info. */ {
        final Position screenPosition = bw.getInteractionHandler().getScreenPosition();
        final Position mousePosition =
            screenPosition.add(bw.getInteractionHandler().getMousePosition());
        final String mouseText =
            "T:"
                + mousePosition.toTilePosition().toString()
                + "\nW:"
                + mousePosition.toWalkPosition().toString()
                + "\nP:"
                + mousePosition.toString();
        bw.getMapDrawer()
            .drawBoxMap(
                mousePosition.toTilePosition().toPosition(),
                mousePosition
                    .toTilePosition()
                    .toPosition()
                    .add(new TilePosition(1, 1).toPosition()),
                Color.WHITE);
        bw.getMapDrawer().drawTextMap(mousePosition.add(new Position(20, -10)), mouseText);
      }
    } catch (Exception e) {
      e.printStackTrace();
      System.exit(0);
    }
  }

  @Override
  public void onSendText(String text) {
    System.out.println("onSendText: " + text);

    bw.getInteractionHandler().sendText(text);
  }

  @Override
  public void onReceiveText(Player player, String text) {
    System.out.println("onReceiveText: by " + player + ": " + text);
  }

  @Override
  public void onPlayerLeft(Player player) {
    System.out.println("onPlayerLeft: " + player);
  }

  @Override
  public void onNukeDetect(Position target) {
    System.out.println("onNukeDetect: " + target);

    if (bw.getBWMap().isValidPosition(target)) {
      bw.getInteractionHandler().sendText("Nuclear Launch Detected at " + target.toString());
    } else {
      bw.getInteractionHandler().sendText("Where's the nuke?");
    }
  }

  @Override
  public void onUnitDiscover(Unit unit) {
    System.out.println("onUnitDiscover: " + unit);
  }

  @Override
  public void onUnitEvade(Unit unit) {
    System.out.println("onUnitEvade: " + unit);
  }

  @Override
  public void onUnitShow(Unit unit) {
    System.out.println("onUnitShow: " + unit);
  }

  @Override
  public void onUnitHide(Unit unit) {
    System.out.println("onUnitHide: " + unit);
  }

  @Override
  public void onUnitCreate(Unit unit) {
    System.out.println("onUnitCreate: " + unit);
  }

  @Override
  public void onUnitDestroy(Unit unit) {
    System.out.println("onUnitDestroy: " + unit);

    final Optional<Unit> destroyedWorker = workers.stream().filter(w -> w.equals(unit)).findAny();
    destroyedWorker.ifPresent(workers::remove);
  }

  @Override
  public void onUnitMorph(Unit unit) {
    System.out.println("onUnitMorph: " + unit);
  }

  @Override
  public void onUnitRenegade(Unit unit) {
    System.out.println("onUnitRenegade: " + unit);
  }

  @Override
  public void onSaveGame(String gameName) {
    System.out.println("onSaveGame: " + gameName);
  }

  @Override
  public void onUnitComplete(Unit unit) {
    System.out.println("onUnitComplete: " + unit);

    if (unit.getType().isWorker()) {
      if (unit.getPlayer().equals(self) && !workers.contains(unit)) {
        workers.add(unit);
      }
    }
  }

  public static void main(String[] args) {
    final TestListener listener = new TestListener();

    final BW bw = new BW(listener);
    listener.bw = bw;

    bw.startGame();
  }

  private static class UnitDistanceComparator implements Comparator<Unit> {
    private final Position targetPosition;

    public UnitDistanceComparator(final Position targetPosition) {
      this.targetPosition = targetPosition;
    }

    @Override
    public int compare(final Unit u1, final Unit u2) {
      return Integer.compare(
          u1.getPosition().getDistance(targetPosition),
          u2.getPosition().getDistance(targetPosition));
    }
  }
}
