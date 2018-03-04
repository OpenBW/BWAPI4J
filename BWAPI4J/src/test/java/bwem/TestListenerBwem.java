package bwem;

import bwem.example.MapPrinterExample;
import org.openbw.bwapi4j.BW;
import org.openbw.bwapi4j.BWEventListener;
import org.openbw.bwapi4j.Player;
import org.openbw.bwapi4j.Position;
import org.openbw.bwapi4j.type.Color;
import org.openbw.bwapi4j.type.UnitType;
import org.openbw.bwapi4j.unit.Unit;

public class TestListenerBwem implements BWEventListener {

    private BW bw;
    private BWEM bwem;

    @Override
    public void onStart() {
        try {
            System.out.println("onStart");

            // Hello World!
            this.bw.getInteractionHandler().sendText("hello, world");

            // Print the map name.
            this.bw.getInteractionHandler().printf("The map is " + this.bw.getBWMap().mapName() + "! Size: " + this.bw.getBWMap().mapWidth() + "x" + this.bw.getBWMap().mapHeight());

            // Enable the UserInput flag, which allows us to manually control units and type messages.
            this.bw.getInteractionHandler().enableUserInput();

            // Uncomment the following line and the bot will know about everything through the fog of war (cheat).
            //this.bw.getInteractionHandler().enableCompleteMapInformation();

            // Initialize BWEM.
            System.out.println("BWEM initialization started.");
            this.bwem = new BWEM(this.bw); // Instantiate the BWEM object.
            this.bwem.initialize(); // Initialize and pre-calculate internal data.
            this.bwem.getMap().enableAutomaticPathAnalysis(); // This option requires "bwem.getMap().onUnitDestroyed(unit);" in the "onUnitDestroy" callback.
            try {
                this.bwem.getMap().assignStartingLocationsToSuitableBases(); // Throws an exception on failure.
            } catch (final Exception e) {
                e.printStackTrace();
                if (this.bwem.getMap().getUnassignedStartingLocations().size() > 0) {
                    throw new IllegalStateException("Failed to find suitable bases for the following starting locations: " + this.bwem.getMap().getUnassignedStartingLocations().toString());
                }
            }
            System.out.println("BWEM initialization completed.");

            // BWEM's map printer example. Generates a "map.bmp" image file.
            this.bwem.getMap().getMapPrinter().initialize(this.bw, this.bwem.getMap());
            final MapPrinterExample example = new MapPrinterExample(this.bwem.getMap().getMapPrinter());
            example.printMap(this.bwem.getMap());
            example.pathExample(this.bwem.getMap());

            // Check if this is a replay
            if (this.bw.getInteractionHandler().isReplay()) {
                for (final Player player : this.bw.getAllPlayers()) {
                    // Only print the player if they are not an observer
                    if (!player.isObserver()) {
                        this.bw.getInteractionHandler().printf(player.getName() + ", playing as " + player.getRace());
                    }
                }
            } else {
                // Retrieve you and your enemy's races. enemy() will just return the first enemy.
                // If you wish to deal with multiple enemies then you must use enemies().
                this.bw.getInteractionHandler().printf("The matchup is " + this.bw.getInteractionHandler().self().getRace() + " vs " + this.bw.getInteractionHandler().enemy().getRace());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onEnd(boolean isWinner) {
        System.out.println("onEnd: winner: " + isWinner);
    }

    @Override
    public void onFrame() {
        try {
            // Display starting locations and possible base locations.
            for (final Base base : this.bwem.getMap().getBases()) {
                final boolean isStartingLocation = base.isStartingLocation();
                final Color highlightColor = isStartingLocation ? Color.GREEN : Color.YELLOW;
                final Position location = base.getLocation().toPosition();
                final Position resourceDepotSize = UnitType.Terran_Command_Center.tileSize().toPosition();
                this.bw.getMapDrawer().drawBoxMap(location.getX(), location.getY(), location.add(resourceDepotSize).getX(), location.add(resourceDepotSize).getY(), highlightColor);
            }

            // Display choke points.
            //TODO
        } catch (Exception e) {
            e.printStackTrace();
            System.exit(0);
        }
    }

    @Override
    public void onSendText(String text) {
        System.out.println("onSendText: " + text);

        this.bw.getInteractionHandler().sendText(text);
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

        if (this.bw.getBWMap().isValidPosition(target)) {
            this.bw.getInteractionHandler().sendText("Nuclear Launch Detected at " + target.toString());
        } else {
            this.bw.getInteractionHandler().sendText("Where's the nuke?");
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

        // BWEM's unit tracking for automatic path analysis.
        try {
            this.bwem.getMap().onUnitDestroyed(unit);
        } catch (final Exception e) {
            e.printStackTrace();
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
        final TestListenerBwem listener = new TestListenerBwem();

        final BW bw = new BW(listener);
        listener.bw = bw;

        bw.startGame();
    }
}
