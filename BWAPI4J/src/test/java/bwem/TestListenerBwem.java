package bwem;

import bwem.example.MapPrinterExample;
import bwem.unit.Geyser;
import bwem.unit.Mineral;
import org.openbw.bwapi4j.BW;
import org.openbw.bwapi4j.BWEventListener;
import org.openbw.bwapi4j.Player;
import org.openbw.bwapi4j.Position;
import org.openbw.bwapi4j.TilePosition;
import org.openbw.bwapi4j.type.Color;
import org.openbw.bwapi4j.type.Race;
import org.openbw.bwapi4j.type.UnitType;
import org.openbw.bwapi4j.unit.CommandCenter;
import org.openbw.bwapi4j.unit.MineralPatch;
import org.openbw.bwapi4j.unit.PlayerUnit;
import org.openbw.bwapi4j.unit.Unit;
import org.openbw.bwapi4j.unit.Worker;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;

public class TestListenerBwem implements BWEventListener {

    private static final int SCREEN_WIDTH = 640; // only used for optimized shape drawing within the screen area
    private static final int SCREEN_HEIGHT = 480; // only used for optimized shape drawing within the screen area
    private static final Position SCREEN_SIZE = new Position(SCREEN_WIDTH, SCREEN_HEIGHT);

    private BW bw; // main game object
    private BWEM bwem; // main terrain analyzer object

    private Player self;
    private final List<Worker> workers = new ArrayList<>();

    /**
     * Tests if the specified position is within the current viewport area.
     * Useful for ignoring shape drawing calls if the specified position is off screen.
     */
    private boolean isOnScreen(final Position position) {
        final Position topLeftScreenPosition = bw.getInteractionHandler().getScreenPosition();
        final Position bottomRightScreenPosition = topLeftScreenPosition.add(SCREEN_SIZE);
        return (position.getX() >= topLeftScreenPosition.getX() && position.getY() >= topLeftScreenPosition.getY()
                && position.getX() < bottomRightScreenPosition.getX() && position.getY() < bottomRightScreenPosition.getY());
    }

    @Override
    public void onStart() {
        try {
            System.out.println("onStart");

            // Hello World!
            bw.getInteractionHandler().sendText("hello, world");

            // Print the map name.
            bw.getInteractionHandler().printf("The map is " + bw.getBWMap().mapName() + "! Size: " + bw.getBWMap().mapWidth() + "x" + bw.getBWMap().mapHeight());

            // Enable the UserInput flag, which allows us to manually control units and type messages.
            bw.getInteractionHandler().enableUserInput();

            // Uncomment the following line and the bot will know about everything through the fog of war (cheat).
            //bw.getInteractionHandler().enableCompleteMapInformation();

            self = bw.getInteractionHandler().self();

            // Initialize BWEM.
            System.out.println("BWEM initialization started.");
            bwem = new BWEM(bw); // Instantiate the BWEM object.
            bwem.initialize(); // Initialize and pre-calculate internal data.
            bwem.getMap().enableAutomaticPathAnalysis(); // This option requires "bwem.getMap().onUnitDestroyed(unit);" in the "onUnitDestroy" callback.
            try {
                bwem.getMap().assignStartingLocationsToSuitableBases(); // Throws an exception on failure.
            } catch (final Exception e) {
                e.printStackTrace();
                if (bwem.getMap().getUnassignedStartingLocations().size() > 0) {
                    throw new IllegalStateException("Failed to find suitable bases for the following starting locations: " + bwem.getMap().getUnassignedStartingLocations().toString());
                }
            }
            System.out.println("BWEM initialization completed.");

            // BWEM's map printer example. Generates a "map.bmp" image file.
            bwem.getMap().getMapPrinter().initialize(bw, bwem.getMap());
            final MapPrinterExample example = new MapPrinterExample(bwem.getMap().getMapPrinter());
            example.printMap(bwem.getMap(), "bwem_example");
            example.pathExample(bwem.getMap(), "bwem_example");

            /* Print player info to console. */ {
                final StringBuilder sb = new StringBuilder("Players: ").append(System.lineSeparator());
                for (final Player player : bw.getAllPlayers()) {
                    sb.append("  ").append(player.getName()).append(", ID=").append(player.getId()).append(", race=").append(player.getRace()).append(System.lineSeparator());
                }
                System.out.println(sb.toString());
            }

            // Compile list of workers.
            for (final PlayerUnit u : bw.getUnits(self)) {
                if (u instanceof Worker) {
                    final Worker worker = (Worker) u;
                    if (!workers.contains(worker)) {
                        workers.add(worker);
                    }
                }
            }

            /* Basic gamestart worker auto-mine */ {
                final List<MineralPatch> unassignedMineralPatches = bw.getMineralPatches();
                final List<Worker> unassignedWorkers = new ArrayList<>(workers);
                unassignedMineralPatches.sort(new UnitDistanceComparator(self.getStartLocation().toPosition()));
                while (!unassignedWorkers.isEmpty() && !unassignedMineralPatches.isEmpty()) {
                    final Worker unassignedWorker = unassignedWorkers.remove(0);
                    final MineralPatch unassignedMineralPatch = unassignedMineralPatches.remove(0);
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
    }

    @Override
    public void onFrame() {
        try {
            // Send idle workers to mine at the closest mineral patch.
            for (final Worker worker : workers) {
                if (worker.isIdle()) {
                    MineralPatch closestMineralPatch = null;
                    for (final MineralPatch mineralPatch : bw.getMineralPatches()) {
                        if (closestMineralPatch == null) {
                            closestMineralPatch = mineralPatch;
                        } else {
                            if (mineralPatch.getPosition().getDistance(self.getStartLocation().toPosition())
                                    < closestMineralPatch.getPosition().getDistance(self.getStartLocation().toPosition())) {
                                closestMineralPatch = mineralPatch;
                            }
                        }
                    }
                    worker.gather(closestMineralPatch);
                }
            }

            /* Train an SCV at every Command Center. */ {
                if (self.getRace() == Race.Terran) {
                    for (final PlayerUnit u : bw.getUnits(self)) {
                        if (u instanceof CommandCenter) {
                            final CommandCenter commandCenter = (CommandCenter) u;
                            if (!commandCenter.isTraining()) {
                                commandCenter.trainWorker();
                            }
                        }
                    }
                }
            }

            /* Highlight starting locations and possible base locations. */ {
                for (final Base base : bwem.getMap().getBases()) {
                    final boolean isStartingLocation = base.isStartingLocation();
                    final Color highlightColor = isStartingLocation ? Color.GREEN : Color.YELLOW;
                    final Position baseLocation = base.getLocation().toPosition();
                    final Position resourceDepotSize = UnitType.Terran_Command_Center.tileSize().toPosition();
                    if (isOnScreen(baseLocation)) {
                        bw.getMapDrawer().drawBoxMap(baseLocation, baseLocation.add(resourceDepotSize), highlightColor);
                    }

                    /* Display minerals. */
                    for (final Mineral mineral : base.getMinerals()) {
                        if (isOnScreen(mineral.getCenter())) {
                            bw.getMapDrawer().drawLineMap(mineral.getCenter(), base.getCenter(), Color.CYAN);
                        }
                    }

                    /* Display geysers. */
                    for (final Geyser geyser : base.getGeysers()) {
                        if (isOnScreen(geyser.getCenter())) {
                            bw.getMapDrawer().drawLineMap(geyser.getCenter(), base.getCenter(), Color.GREEN);
                        }
                    }
                }
            }

            /* Highlight choke points. */ {
                final int chokePointRadius = 8;
                final Color chokePointColor = Color.RED;
                for (final ChokePoint chokePoint : bwem.getMap().getChokePoints()) {
                    final Position center = chokePoint.getCenter().toPosition();
                    if (isOnScreen(center)) {
                        bw.getMapDrawer().drawCircleMap(center, chokePointRadius, chokePointColor);
                        bw.getMapDrawer().drawLineMap(center.getX() - chokePointRadius, center.getY(), center.getX() + chokePointRadius, center.getY(), chokePointColor);
                        bw.getMapDrawer().drawLineMap(center.getX(), center.getY() - chokePointRadius, center.getX(), center.getY() + chokePointRadius, chokePointColor);
                    }
                }
            }

            /* Highlight workers. */ {
                for (final Worker worker : workers) {
                    final Position tileSize = new TilePosition(worker.tileWidth(), worker.tileHeight()).toPosition();
                    final Position topLeft = worker.getPosition().subtract(tileSize.divide(new Position(2, 2)));
                    final Position bottomRight = topLeft.add(tileSize);
                    if (isOnScreen(topLeft)) {
                        bw.getMapDrawer().drawBoxMap(topLeft, bottomRight, Color.BROWN);
                    }
                }
            }

            /* Draw mouse position debug info. */ {
                final Position screenPosition = bw.getInteractionHandler().getScreenPosition();
                final Position mousePosition = screenPosition.add(bw.getInteractionHandler().getMousePosition());
                final String mouseText = "T:" + mousePosition.toTilePosition().toString() + "\nW:" + mousePosition.toWalkPosition().toString() + "\nP:" + mousePosition.toString();
                bw.getMapDrawer().drawBoxMap(mousePosition.toTilePosition().toPosition(), mousePosition.toTilePosition().toPosition().add(new TilePosition(1, 1).toPosition()), Color.WHITE);
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

        // BWEM's unit tracking for automatic path analysis.
        try {
            bwem.getMap().onUnitDestroyed(unit);
        } catch (final Exception e) {
            e.printStackTrace();
        }

        final Optional<Worker> destroyedWorker = workers.stream()
                .filter(w -> w.equals(unit))
                .findAny();
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

        if (unit instanceof Worker) {
            final Worker worker = (Worker) unit;
            if (worker.getPlayer().equals(self) && !workers.contains(worker)) {
                workers.add(worker);
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
            return Integer.compare(u1.getPosition().getDistance(targetPosition), u2.getPosition().getDistance(targetPosition));
        }

    }

}
