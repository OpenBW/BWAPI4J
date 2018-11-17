package bwem;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import org.openbw.bwapi4j.BW;
import org.openbw.bwapi4j.BWEventListener;
import org.openbw.bwapi4j.Player;
import org.openbw.bwapi4j.Position;
import org.openbw.bwapi4j.TilePosition;
import org.openbw.bwapi4j.WalkPosition;
import org.openbw.bwapi4j.type.Color;
import org.openbw.bwapi4j.type.Race;
import org.openbw.bwapi4j.type.UnitType;
import org.openbw.bwapi4j.unit.Unit;

public class TestListenerBwem implements BWEventListener {
  private static final int SCREEN_WIDTH =
      640; // only used for optimized shape drawing within the screen area
  private static final int SCREEN_HEIGHT =
      480; // only used for optimized shape drawing within the screen area
  private static final Position SCREEN_SIZE = new Position(SCREEN_WIDTH, SCREEN_HEIGHT);

  private BW bw; // main game object

  private BWEM bwem;
  private Map bwemMap;

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
      bw.getInteractionHandler().enableUserInput();

      // Uncomment the following line and the bot will know about everything through the fog of war
      // (cheat).
      // bw.getInteractionHandler().enableCompleteMapInformation();

      self = bw.getInteractionHandler().self();

      // Initialize BWEM.
      bwem = new BWEM();
      bwemMap = bwem.GetMap();
      bwemMap.Initialize(bw);
      bwemMap.FindBasesForStartingLocations();
      bwemMap.EnableAutomaticPathAnalysis();

      System.out.println("Tiles count: " + bwemMap.Tiles().size());
      System.out.println("MiniTiles count: " + bwemMap.MiniTiles().size());
      System.out.println("Minerals count: " + bwemMap.Minerals().size());
      System.out.println("Geysers count: " + bwemMap.Geysers().size());
      System.out.println("StaticBuilding count: " + bwemMap.StaticBuildings().size());

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
              if (!u.isTraining() && u.canTrain(UnitType.Terran_SCV)) {
                u.train(UnitType.Terran_SCV);
              }
            }
          }
        }
      }

      /* Highlight starting locations and possible base locations. */ {
        final Position resourceDepotSize = UnitType.Terran_Command_Center.tileSize().toPosition();
        for (final TilePosition tilePosition : bwemMap.StartingLocations()) {
          final Color highlightColor = Color.GREEN;
          final Position position = tilePosition.toPosition();
          if (isOnScreen(position)) {
            bw.getMapDrawer().drawBoxMap(position, position.add(resourceDepotSize), highlightColor);
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

      /* Draw path from our base to center of the map */ {
        final Position startingLocation = bw.self().getStartLocation().toPosition();
        final Position mapCenter = bwemMap.Center();
        final PathLength pathLength = new PathLength();

        final List<WalkPosition> path = bwemMap.GetPath(startingLocation, mapCenter, pathLength);

        final int radius = 5;
        final Color chokepointColor = Color.RED;
        final Color pathColor = Color.ORANGE;

        WalkPosition prev = startingLocation.toWalkPosition();
        bw.getMapDrawer().drawCircleMap(startingLocation, radius, chokepointColor);
        for (final WalkPosition chokepoint : path) {
          bw.getMapDrawer().drawLineMap(prev.toPosition(), chokepoint.toPosition(), pathColor);

          bw.getMapDrawer().drawCircleMap(chokepoint.toPosition(), radius, chokepointColor);

          prev = chokepoint;
        }
        bw.getMapDrawer().drawLineMap(prev.toPosition(), mapCenter, pathColor);
        bw.getMapDrawer().drawCircleMap(mapCenter, radius, chokepointColor);

        if (pathLength.getValue() > 0) {
          bw.getMapDrawer()
              .drawTextMap(mapCenter.getX() + 3, mapCenter.getY() + 3, "" + pathLength.getValue());
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

    bwemMap.onUnitDestroy(unit);

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
    final TestListenerBwem listener = new TestListenerBwem();

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
