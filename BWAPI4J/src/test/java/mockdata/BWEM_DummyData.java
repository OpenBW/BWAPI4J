package mockdata;

import bwem.area.TempAreaInfo;
import bwem.area.typedef.AreaId;
import bwem.tile.MiniTile;
import bwem.typedef.Altitude;
import org.apache.commons.lang3.mutable.MutableInt;
import org.apache.commons.lang3.tuple.MutablePair;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.Assert;
import org.openbw.bwapi4j.WalkPosition;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

public abstract class BWEM_DummyData {

    private static final Logger logger = LogManager.getLogger();

    public int[] miniTileAltitudes;
    public List<MutablePair<WalkPosition, Altitude>> deltasByAscendingAltitude;
    public List<MutablePair<WalkPosition, MiniTile>> miniTilesByDescendingAltitude;
    public List<TempAreaInfo> tempAreaList;

    protected BWEM_DummyData() {
        /* Do nothing. */
    }

    protected void populateArrays(final String mapName, final int mapTileWidth, final int mapTileHeight) throws IOException, URISyntaxException {
        final int mapWalkWidth = mapTileWidth * 4;
        final int mapWalkHeight = mapTileHeight * 4;

        this.miniTileAltitudes = new int[mapWalkWidth * mapWalkHeight];
        this.deltasByAscendingAltitude = new ArrayList<>();
        this.miniTilesByDescendingAltitude = new ArrayList<>();
        this.tempAreaList = new ArrayList<>();

        final String filenameSuffix = "_" + mapName + "_ORIGINAL.txt";
        DummyDataUtils.populateIntegerArray("MiniTile_Altitudes" + filenameSuffix, this.miniTileAltitudes, " ");

        {
            final int valuesPerGroup = 3;
            final String filename = "DeltasByAscendingAltitude_sorted" + filenameSuffix;
            final String regex = " ";
            final List<Integer> array = new ArrayList<>();
            final MutableInt index = new MutableInt(0);
            final URI fileURI = BWEM_DummyData.class.getResource(filename).toURI();
            final Stream<String> stream = Files.lines(Paths.get(fileURI));
            stream.forEach(l -> {
                for (final String s : l.split(regex)) {
                    array.add(index.getAndIncrement(), Integer.valueOf(s.trim()));
                }
            });
            stream.close();
            logger.debug("Added " + index + " values");

            Assert.assertTrue("Dummy data file contains invalid number of integers: " + array.size(), (array.size() >= valuesPerGroup) && (array.size() % valuesPerGroup == 0));

            for (int i = 0; i < array.size(); i += valuesPerGroup) {
                final int x = array.get(i);
                final int y = array.get(i + 1);
                final WalkPosition w = new WalkPosition(x, y);

                final int altitude = array.get(i + 2);

                this.deltasByAscendingAltitude.add(new MutablePair<>(w, new Altitude(altitude)));
            }
        }

        {
            final int valuesPerGroup = 4;
            final String filename = "MiniTilesByDescendingAltitude_sorted" + filenameSuffix;
            final String regex = " ";
            final List<Integer> array = new ArrayList<>();
            final MutableInt index = new MutableInt(0);
            final URI fileURI = BWEM_DummyData.class.getResource(filename).toURI();
            final Stream<String> stream = Files.lines(Paths.get(fileURI));
            stream.forEach(l -> {
                for (final String s : l.split(regex)) {
                    array.add(index.getAndIncrement(), Integer.valueOf(s.trim()));
                }
            });
            stream.close();
            logger.debug("Added " + index + " values");

            Assert.assertTrue("Dummy data file contains invalid number of integers: " + array.size(), (array.size() >= valuesPerGroup) && (array.size() % valuesPerGroup == 0));

            for (int i = 0; i < array.size(); i += valuesPerGroup) {
                final int x = array.get(i);
                final int y = array.get(i + 1);
                final WalkPosition w = new WalkPosition(x, y);

                final int areaId = array.get(i + 2);
                final int altitude = array.get(i + 3);
                final MiniTile miniTile = new MiniTile();
                if (areaId != -1) {
                    miniTile.SetAreaId(new AreaId(areaId));
                }
                if (altitude != -1) {
                    miniTile.SetAltitude(new Altitude(altitude));
                }

                this.miniTilesByDescendingAltitude.add(new MutablePair<>(w, miniTile));
            }
        }

        {
            final int valuesPerGroup = 6;
            final String filename = "TempAreaList" + filenameSuffix;
            final String regex = " ";
            final List<Integer> array = new ArrayList<>();
            final MutableInt index = new MutableInt(0);
            final URI fileURI = BWEM_DummyData.class.getResource(filename).toURI();
            final Stream<String> stream = Files.lines(Paths.get(fileURI));
            stream.forEach(l -> {
                for (final String s : l.split(regex)) {
                    array.add(index.getAndIncrement(), Integer.valueOf(s.trim()));
                }
            });
            stream.close();
            logger.debug("Added " + index + " values");

            Assert.assertTrue("Dummy data file contains invalid number of integers: " + array.size(), (array.size() >= valuesPerGroup) && (array.size() % valuesPerGroup == 0));

            for (int i = 0; i < array.size(); i += valuesPerGroup) {
                final boolean valid = (array.get(i) == 1);
                final int id = array.get(i + 1);
                final int x = array.get(i + 2);
                final int y = array.get(i + 3);
                final int highestAltitude = array.get(i + 4);
                final int size = array.get(i + 5);
                final TempAreaInfo tempAreaInfo = new TempAreaInfo(valid, new AreaId(id), new WalkPosition(x, y), new Altitude(highestAltitude), size);

                this.tempAreaList.add(tempAreaInfo);
            }
        }
    }

}
