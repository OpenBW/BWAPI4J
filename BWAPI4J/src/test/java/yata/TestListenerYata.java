package yata;

import bwem.util.Timer;
import java.awt.*;
import java.nio.file.Paths;
import java.util.List;
import org.openbw.bwapi4j.BW;
import org.openbw.bwapi4j.BWEventListener;
import org.openbw.bwapi4j.Player;
import org.openbw.bwapi4j.Position;
import org.openbw.bwapi4j.TilePosition;
import org.openbw.bwapi4j.unit.Unit;

public class TestListenerYata implements BWEventListener {

  private BW bw;
  private YATA yata;

  @Override
  public void onStart() {

    try {
      System.out.println("onStart");
      this.bw.getInteractionHandler().enableUserInput();

      System.out.println("YATA initialization started.");
      this.yata = new YATA(this.bw.getBWMap());

      System.out.println("YATA initialization complete.");

      final MapPrinter mapPrinter =
          new MapPrinter(
              new TilePosition(this.bw.getBWMap().mapWidth(), this.bw.getBWMap().mapHeight()));
      for (int y = 0; y < this.yata.getHeight(); ++y) {
        for (int x = 0; x < this.yata.getWidth(); ++x) {
          final Color color = this.yata.getNodes()[x][y].isWalkable() ? Color.GREEN : Color.BLACK;
          final TilePosition tilePosition = new TilePosition(x, y);
          mapPrinter.Point(tilePosition.toWalkPosition(), color);
        }
      }

      final TilePosition sourceTilePosition = this.bw.getBWMap().getStartPositions().get(0);
      final TilePosition targetTilePosition = this.bw.getBWMap().getStartPositions().get(1);
      //            final TilePosition sourceTilePosition = new TilePosition(65, 104);
      //            final TilePosition targetTilePosition = new TilePosition(89, 106);

      Timer timer = new Timer();
      final List<TilePosition> tilePath = this.yata.getPath(sourceTilePosition, targetTilePosition);
      System.out.println("getPath(): " + timer.elapsedMilliseconds() + " ms");
      timer.reset();

      for (final TilePosition tilePosition : tilePath) {
        mapPrinter.Point(tilePosition.toWalkPosition(), Color.WHITE);
      }

      mapPrinter.writeImageToFile(Paths.get("yata.png"));
    } catch (Exception e) {
      e.printStackTrace();
      System.exit(0);
    }
  }

  @Override
  public void onEnd(boolean isWinner) {
    System.out.println("onEnd: winner: " + isWinner);
  }

  @Override
  public void onFrame() {

    try {

      //        if (frame == 5) {
      //
      //            System.out.println(this.bwta.getBaseLocations().size() + " base locations
      // found.");
      //            for (BaseLocation base : this.bwta.getBaseLocations()) {
      //
      //                System.out.println("location at " + base.getPosition().getX() + ", " +
      // base.getPosition().getY());
      //            }
      //
      //            System.out.println(this.bwta.getChokepoints().size() + " chokepoints found.");
      //            for (Chokepoint choke : this.bwta.getChokepoints()) {
      //
      //                System.out.println("choke side 1: " + choke.getRegions().first + ", side 2:
      // " + choke.getRegions().second);
      //            }
      //
      //            System.out.println(this.bwta.getRegions().size() + " regions found.");
      //        }
      //
      //        if (bw.getInteractionHandler().isKeyPressed(Key.K_D)) {
      //            System.out.println("D");
      //        }
      //        for (Player player : bw.getAllPlayers()) {
      //            System.out.println("Player " + player.getName() + " has minerals " +
      // player.minerals());
      //        }
      //
      //        this.frame++;

    } catch (Exception e) {
      e.printStackTrace();
      System.exit(0);
    }
  }

  @Override
  public void onSendText(String text) {
    System.out.println("onSendText: " + text);
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
  }

  /**
   * Test method.
   *
   * @param args arguments
   */
  public static void main(String[] args) {

    TestListenerYata listener = new TestListenerYata();
    BW bw = new BW(listener);
    listener.bw = bw;

    bw.startGame();
  }
}
