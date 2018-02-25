package bwem;

import bwem.example.MapPrinterExample;
import org.openbw.bwapi4j.unit.Unit;

import bwta.BWTA;
import org.openbw.bwapi4j.BW;
import org.openbw.bwapi4j.BWEventListener;
import org.openbw.bwapi4j.Player;
import org.openbw.bwapi4j.Position;

public class TestListenerBwem implements BWEventListener {

    private BW bw;
    private BWTA bwta;
    private BWEM bwem;
//    private int frame;

    @Override
    public void onStart() {

        try {
            System.out.println("onStart");
            this.bw.getInteractionHandler().enableUserInput();

//            this.bwta = new BWTA();
//            this.bwta.analyze();
//            System.out.println("BWTA analysis done.");

            System.out.println("BWEM initialization started.");
            this.bwem = new BWEM(this.bw);
//            this.bwem.getMap().initialize();
            this.bwem.initialize();
            this.bwem.getMap().enableAutomaticPathAnalysis();
            final boolean startLocationsOK = this.bwem.getMap().findBasesForStartingLocations();
            if (!startLocationsOK) {
                System.out.println("startLocationsOK: FAIL");
                System.exit(0);
            }
            this.bwem.getMap().getMapPrinter().initialize(this.bw, this.bwem.getMap());
            MapPrinterExample example = new MapPrinterExample(this.bwem.getMap().getMapPrinter());
            example.printMap(this.bwem.getMap());
            example.pathExample(this.bwem.getMap());
            System.out.println("BWEM initialization completed.");

//            this.frame = 0;
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
//            System.out.println(this.bwta.getBaseLocations().size() + " base locations found.");
//            for (BaseLocation base : this.bwta.getBaseLocations()) {
//
//                System.out.println("location at " + base.getPosition().getX() + ", " + base.getPosition().getY());
//            }
//
//            System.out.println(this.bwta.getChokepoints().size() + " chokepoints found.");
//            for (Chokepoint choke : this.bwta.getChokepoints()) {
//
//                System.out.println("choke side 1: " + choke.getRegions().first + ", side 2: " + choke.getRegions().second);
//            }
//
//            System.out.println(this.bwta.getRegions().size() + " regions found.");
//        }
//
//        if (bw.getInteractionHandler().isKeyPressed(Key.K_D)) {
//            System.out.println("D");
//        }
//        for (Player player : bw.getAllPlayers()) {
//            System.out.println("Player " + player.getName() + " has minerals " + player.minerals());
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

        /* BWEM's unit tracking. */
        try {
            this.bwem.getMap().onUnitDestroyed(unit);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
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
     * @param args arguments
     */
    public static void main(String[] args) {

        TestListenerBwem listener = new TestListenerBwem();
        BW bw = new BW(listener);
        listener.bw = bw;

        bw.startGame();
    }
}
