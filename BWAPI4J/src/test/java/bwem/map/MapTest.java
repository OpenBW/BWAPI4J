package bwem.map;

import bwem.BWEM;
import bwem.ChokePoint;
import bwem.typedef.CPPath;
import mockdata.BWAPI_DummyData;
import mockdata.BWEM_CPPathSamples;
import mockdata.BWEM_DummyData;
import mockdata.DummyDataUtils;
import org.apache.commons.lang3.mutable.MutableInt;
import org.apache.commons.lang3.tuple.ImmutableTriple;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.Assert;
import org.junit.Ignore;
import org.junit.Test;
import org.openbw.bwapi4j.BW;
import org.openbw.bwapi4j.BWEventListener;
import org.openbw.bwapi4j.BWMap;
import org.openbw.bwapi4j.Player;
import org.openbw.bwapi4j.Position;
import org.openbw.bwapi4j.WalkPosition;
import org.openbw.bwapi4j.unit.Unit;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;

public class MapTest implements BWEventListener {

	private static final Logger logger = LogManager.getLogger();

	private BW bw;
	private Map bwemMap;

	@Ignore
	@Test
    public void Compare_MiniTile_Altitudes_to_Original_Samples_LIVE() throws AssertionError, IOException {
		this.bw = new BW(this);
		this.bw.startGame();
        final BWEM_DummyData bwemDummyData = new BWEM_DummyData(this.bw.getBWMap().mapHash(), BWAPI_DummyData.DataSetBwapiVersion.BWAPI_420.toString(), BWEM_DummyData.DataSetBwemVersion.BWEM_141.toString());

		assertEquals_MiniTileAltitudes(this.bwemMap.getData(), bwemDummyData, this.bw.getBWMap().mapWidth() * 4, this.bw.getBWMap().mapHeight() * 4);
	}

	@Ignore
    @Test
    public void Compare_MiniTile_Altitudes_to_Original_Samples() throws AssertionError, IOException {
    	for (final BWAPI_DummyData.MapHash mapHash : BWAPI_DummyData.MapHash.values()) {
    	    logger.debug("Compare_MiniTile_Altitudes_to_Original_Samples: " + mapHash.getMapName() + ": " + mapHash.getMapHash());

            final BWMap bwMapMock = new BWMapMock(mapHash.getMapHash(), BWAPI_DummyData.DataSetBwapiVersion.BWAPI_420.toString());

            final BWEM_DummyData bwemDummyData = new BWEM_DummyData(mapHash.getMapHash(), BWAPI_DummyData.DataSetBwapiVersion.BWAPI_420.toString(), BWEM_DummyData.DataSetBwemVersion.BWEM_141.toString());

            this.bwemMap = new MapInitializerImpl(bwMapMock, null, new ArrayList<>(), new ArrayList<>(), new ArrayList<>(), new ArrayList<>());
            ((MapInitializer) this.bwemMap).initialize(true);

            assertEquals_MiniTileAltitudes(this.bwemMap.getData(), bwemDummyData, bwMapMock.mapWidth() * 4, bwMapMock.mapHeight() * 4);
        }
    }

	/**
	 * Tests that each MiniTile's Altitude for all WalkPositions match between
	 * the original BWAPI/BWEM in C++ and this Java port.
	 */
	private void assertEquals_MiniTileAltitudes(AdvancedData data, BWEM_DummyData dummyBwemData, int walkTileWidth, int walkTileHeight) {
        final List<ImmutableTriple<WalkPosition, Integer, Integer>> wrongAltitudes = new ArrayList<>();

		for (int y = 0; y < walkTileHeight; ++y) {
			for (int x = 0; x < walkTileWidth; ++x) {
			    final WalkPosition w = new WalkPosition(x, y);

			    final int expected = dummyBwemData.getMiniTileAltitudes()[walkTileWidth * y + x];
			    final int actual = data.getMiniTile(w).getAltitude().intValue();

//				Assert.assertEquals(w + ": mini tile altitude is wrong.", expected, actual);
                if (expected != actual) {
                    wrongAltitudes.add(new ImmutableTriple<>(w, expected, actual));
                }
			}
		}

		for (final ImmutableTriple<WalkPosition, Integer, Integer> triple : wrongAltitudes) {
		    logger.warn("Wrong MiniTile altitude for WalkPosition: " + triple.getLeft().toString() + ", expected=" + triple.getMiddle() + ", actual=" + triple.getRight());
        }
	}

    @Ignore
    @Test
    public void Compare_ChokePoint_Centers_to_Original_Samples_LIVE() throws URISyntaxException, IOException {
        this.bw = new BW(this);
        this.bw.startGame();

        final int[] chokepointCentersVals_ORIGINAL = DummyDataUtils.readIntegerArrayFromArchiveFile(DummyDataUtils.compileBwemDataSetArchiveFilename("ChokePoints", BWAPI_DummyData.DataSetBwapiVersion.BWAPI_420.toString(), BWEM_DummyData.DataSetBwemVersion.BWEM_141.toString()), this.bw.getBWMap().mapHash(), " ");
        final List<WalkPosition> chokepointCenters_ORIGINAL = new ArrayList<>();

        final int valuesPerGroup = 6;
        for (int i = 0; i < chokepointCentersVals_ORIGINAL.length; i += valuesPerGroup) {
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

        Assert.assertEquals("Chokepoint container sizes do not match. original=" + chokepointCenters_ORIGINAL.size() + ", port=" + chokepointCenters.size(), chokepointCenters_ORIGINAL.size(), chokepointCenters.size());

        final int tolerance = 20; // If an exact position is not found, search within this radius value.
        final List<WalkPosition> tolerantCenters = new ArrayList<>(); // Keep track of and do not use the same tolerant center more than once.

        for (final WalkPosition center_ORIGINAL : chokepointCenters_ORIGINAL) {
            boolean found = chokepointCenters.contains(center_ORIGINAL);
            if (!found) {
                logger.warn("Did not find original chokepoint: " + center_ORIGINAL.toString() + ". Retrying with a max tolerance of " + tolerance + ".");
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
                            logger.debug("Found tolerant center for chokepoint: " + center_ORIGINAL.toString() + ", tolerant_center=" + tolerantCenter.toString() + ", tolerance=" + center_ORIGINAL.subtract(tolerantCenter) + ".");
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
            final int originalPathLength = sample.pathLength;

            logger.debug("Testing: startPosition=" + sampleStartPosition.toString() + ", endPosition=" + sampleEndPosition.toString() + ", originalPathLength=" + originalPathLength);

            ++pathsCount;
            try {
                final MutableInt portPathLength = new MutableInt();
                final CPPath path = this.bwemMap.getPath(sampleStartPosition, sampleEndPosition, portPathLength);

                final int difference = portPathLength.intValue() - originalPathLength;
                if (difference != 0) {
                    differenceSum += difference;
                    logger.warn("Path lengths do not match: originalPathLength=" + originalPathLength + ", portPathLength=" + portPathLength + ", difference=" + difference);
                }
            } catch (final Exception e) {
                logger.warn("Failed to find a path: path error: startPosition=" + sampleStartPosition.toString() + ", endPosition=" + sampleEndPosition.toString() + ", pathLength=" + originalPathLength);
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
