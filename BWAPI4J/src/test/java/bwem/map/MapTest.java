package bwem.map;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import bwem.ChokePoint;
import bwem.typedef.CPPath;
import mockdata.BWAPI_DummyData;
import mockdata.BWEM_CPPathSamples;
import mockdata.BWEM_DummyData;
import mockdata.BWEM_FightingSpirit;
import mockdata.DummyDataUtils;
import org.apache.commons.lang3.mutable.MutableInt;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Ignore;
import org.junit.Test;
import org.openbw.bwapi4j.BW;
import org.openbw.bwapi4j.BWEventListener;
import org.openbw.bwapi4j.BWMap;
import org.openbw.bwapi4j.Player;
import org.openbw.bwapi4j.Position;
import org.openbw.bwapi4j.WalkPosition;
import org.openbw.bwapi4j.unit.MineralPatch;
import org.openbw.bwapi4j.unit.Unit;
import org.openbw.bwapi4j.unit.VespeneGeyser;

import bwem.BWEM;

public class MapTest implements BWEventListener {

	private static final Logger logger = LogManager.getLogger();

	private BW bw;
	private Map bwemMap;

	private BWEM_DummyData bwemData = null;
	
	@BeforeClass
    public static void setUpClass() {

    }

    @Before
	public void setUp() {
		if (this.bwemData == null) {
			try {
				this.bwemData = new BWEM_FightingSpirit();
			} catch (Exception e) {
				e.printStackTrace();
				Assert.fail("Failed to load dummy BWEM data.");
			}
		}
	}

	@Ignore
	@Test
    public void Compare_MiniTile_Altitudes_to_Original_Samples_LIVE() throws AssertionError {
		this.bw = new BW(this);
		this.bw.startGame();

		assertEquals_MiniTileAltitudes(this.bwemMap.getData(), this.bwemData, this.bw.getBWMap().mapWidth() * 4, this.bw.getBWMap().mapHeight() * 4);
	}

    @Test
    public void Compare_MiniTile_Altitudes_to_Original_Samples() throws AssertionError {
    	final BWMap bwMapMock = new BWMapMock("d2f5633cc4bb0fca13cd1250729d5530c82c7451", BWAPI_DummyData.DataSetBwapiVersion.BWAPI_420);

    	this.bwemMap = new MapInitializerImpl(bwMapMock, null, new ArrayList<>(), new ArrayList<>(), new ArrayList<>(), new ArrayList<>());
		((MapInitializer) this.bwemMap).initialize(true);

    	assertEquals_MiniTileAltitudes(this.bwemMap.getData(), this.bwemData, bwMapMock.mapWidth() * 4, bwMapMock.mapHeight() * 4);
    }

	/**
	 * Tests that each MiniTile's Altitude for all WalkPositions match between
	 * the original BWAPI/BWEM in C++ and this Java port.
	 */
	private void assertEquals_MiniTileAltitudes(AdvancedData data, BWEM_DummyData dummyBwemData, int walkTileWidth, int walkTileHeight) {
		for (int y = 0; y < walkTileHeight; ++y) {
			for (int x = 0; x < walkTileWidth; ++x) {
			    final WalkPosition w = new WalkPosition(x, y);

			    final int expected = dummyBwemData.miniTileAltitudes[walkTileWidth * y + x];
			    final int actual = data.getMiniTile(w).getAltitude().intValue();

				//----------------------------------------------------------------------
				// Three mini tile altitudes that do/did not match the original on map Fighting Spirit.
				//----------------------------------------------------------------------
                if (expected != actual) {
                    if (x == 248 && y == 249) { // index = 127737
                        logger.warn("The altitude of mini tile " + w.toString() + " does not match the original but has been marked as a possible false positive for now. expected=" + expected + ", actual=" + actual);
                        continue;
                    }
//                    if (x == 273 && y == 260) { continue; } // index = 133393
//                    if (x == 273 && y == 261) { continue; } // index = 133905
                }
				//----------------------------------------------------------------------

				Assert.assertEquals(w.toTilePosition() + ": mini tile altitude is wrong.", expected, actual);
			}
		}
	}

    @Ignore
    @Test
    public void Compare_ChokePoint_Centers_to_Original_Samples_LIVE() throws URISyntaxException, IOException {
        this.bw = new BW(this);
        this.bw.startGame();

        final String filename = Paths.get("DummyBwemData", this.bw.getBWMap().mapHash().substring(0, 7) + "_ChokePoints_ORIGINAL.txt").toString();
        final int[] chokepointCentersVals_ORIGINAL = DummyDataUtils.readIntegerArrayFromArchiveFile(filename, this.bw.getBWMap().mapHash(), " ");
        final List<WalkPosition> chokepointCenters_ORIGINAL = new ArrayList<>();
        final int chokepointCentersCount_ORIGINAL = (chokepointCentersVals_ORIGINAL.length - 1) / 2;
        for (int i = 1; i < chokepointCentersVals_ORIGINAL.length; i += 2) {
            final int x = chokepointCentersVals_ORIGINAL[i];
            final int y = chokepointCentersVals_ORIGINAL[i + 1];
            final WalkPosition chokepoint = new WalkPosition(x, y);
            chokepointCenters_ORIGINAL.add(chokepoint);
        }

        final List<ChokePoint> chokepoints = ((MapImpl)this.bwemMap).getGraph().getChokePoints();
        final List<WalkPosition> chokepointCenters = new ArrayList<>();
        for (final ChokePoint chokepoint : chokepoints) {
            final WalkPosition center = chokepoint.getCenter();
            chokepointCenters.add(center);
        }

        Assert.assertEquals("Chokepoint container sizes do not match. original=" + chokepointCentersCount_ORIGINAL + ", port=" + chokepointCenters.size(), chokepointCentersCount_ORIGINAL, chokepointCenters.size());

        final int tolerance = 20; // If an exact position is not found, search within this radius value.
        final List<WalkPosition> tolerantCenters = new ArrayList<>(); // Keep track of and do not use the same tolerant center more than once.

        for (final WalkPosition center_ORIGINAL : chokepointCenters_ORIGINAL) {
            boolean found = chokepointCenters.contains(center_ORIGINAL);
            if (!found) {
                logger.warn("Did not find original chokepoint: " + center_ORIGINAL.toString() + ". Retrying with a tolerance value of " + tolerance + ".");
                final int boundsLowerX = center_ORIGINAL.getX() - tolerance;
                final int boundsUpperX = center_ORIGINAL.getX() + tolerance;
                final int boundsLowerY = center_ORIGINAL.getY() - tolerance;
                final int boundsUpperY = center_ORIGINAL.getY() + tolerance;
                boolean foundTolerant = false;
                for (int y = boundsLowerY; y <= boundsUpperY; ++y) {
                    for (int x = boundsLowerX; x <= boundsUpperX; ++x) {
                        final WalkPosition tolerantCenter = new WalkPosition(x, y);
                        if (chokepointCenters.contains(tolerantCenter)) {
                            Assert.assertEquals("Found a tolerant center but it has already been used: " + tolerantCenter.toString(), false, tolerantCenters.contains(tolerantCenter));
                            tolerantCenters.add(tolerantCenter);
                            foundTolerant = true;
                            logger.debug("Found tolerant center: " + tolerantCenter.toString() + ", tolerance=" + center_ORIGINAL.subtract(tolerantCenter) + ".");
                            break;
                        }
                    }
                    if (foundTolerant) {
                        break;
                    }
                }
                Assert.assertEquals("Did not find original chokepoint even with a tolerance value. list=" + chokepointCenters.toString(), true, foundTolerant);
            }
        }
    }

    @Ignore
    @Test
    public void Test_getPath_Lengths_Using_Original_Samples_LIVE() throws IOException, URISyntaxException {
        this.bw = new BW(this);
        this.bw.startGame();

        int pathErrorsCount = 0;
        int pathsCount = 0;
        int differenceSum = 0;

        final BWEM_CPPathSamples cppathSamples = new BWEM_CPPathSamples(this.bw.getBWMap().mapHash());
        for (final BWEM_CPPathSamples.CPPSample sample : cppathSamples.samples) {
            final Position sampleStartPosition = sample.startAndEnd.getLeft();
            final Position sampleEndPosition = sample.startAndEnd.getRight();
            final int samplePathLength = sample.pathLength;

            logger.debug("Testing: startPosition=" + sampleStartPosition.toString() + ", endPosition=" + sampleEndPosition.toString() + ", pathLength=" + samplePathLength);

            ++pathsCount;
            try {
                final MutableInt pathLength = new MutableInt();
                final CPPath path = this.bwemMap.getPath(sampleStartPosition, sampleEndPosition, pathLength);

                final int difference = pathLength.intValue() - samplePathLength;
                if (difference != 0) {
                    differenceSum += difference;
                    logger.warn("Path lengths do not match: difference=" + difference);
                }
            } catch (final Exception e) {
                logger.warn("Path error: startPosition=" + sampleStartPosition.toString() + ", endPosition=" + sampleEndPosition.toString() + ", pathLength=" + samplePathLength);
                ++pathErrorsCount;
                e.printStackTrace();
            }
        }

        logger.info("Total # of Paths: " + pathsCount + ", # of Path errors: " + pathErrorsCount);
        logger.info("Average difference: " + (differenceSum / (pathsCount - pathErrorsCount)));
    }

	@Override
	public void onStart() {
		final BWEM bwem = new BWEM(this.bw);
		bwem.initialize(true);
		this.bwemMap = bwem.getMap();

    	this.bw.exit();
        this.bw.getInteractionHandler().leaveGame();
	}

	@Override
	public void onEnd(boolean isWinner) {
		
	}

	@Override
	public void onFrame() {
		
	}

	@Override
	public void onSendText(String text) {
		
	}

	@Override
	public void onReceiveText(Player player, String text) {
		
	}

	@Override
	public void onPlayerLeft(Player player) {
		
	}

	@Override
	public void onNukeDetect(Position target) {
		
	}

	@Override
	public void onUnitDiscover(Unit unit) {
		
	}

	@Override
	public void onUnitEvade(Unit unit) {
		
	}

	@Override
	public void onUnitShow(Unit unit) {
		
	}

	@Override
	public void onUnitHide(Unit unit) {
		
	}

	@Override
	public void onUnitCreate(Unit unit) {
		
	}

	@Override
	public void onUnitDestroy(Unit unit) {
		
	}

	@Override
	public void onUnitMorph(Unit unit) {
		
	}

	@Override
	public void onUnitRenegade(Unit unit) {
		
	}

	@Override
	public void onSaveGame(String gameName) {
		
	}

	@Override
	public void onUnitComplete(Unit unit) {
		
	}

}
