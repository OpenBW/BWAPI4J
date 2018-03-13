package mockdata;

import bwem.area.TempAreaInfo;
import bwem.area.typedef.AreaId;
import bwem.tile.MiniTile;
import bwem.tile.MiniTileImpl;
import bwem.typedef.Altitude;
import org.apache.commons.lang3.tuple.MutablePair;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.Assert;
import org.openbw.bwapi4j.WalkPosition;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public abstract class BWEM_DummyData {

    private static final Logger logger = LogManager.getLogger();

    public int[] miniTileAltitudes;
    public List<MutablePair<WalkPosition, Altitude>> deltasByAscendingAltitude;
    public List<MutablePair<WalkPosition, MiniTile>> miniTilesByDescendingAltitude;
    public List<TempAreaInfo> tempAreaList;

    protected BWEM_DummyData() {
        /* Do nothing. */
    }

    protected void populateArrays(final String mapName) throws IOException {
        this.deltasByAscendingAltitude = new ArrayList<>();
        this.miniTilesByDescendingAltitude = new ArrayList<>();
        this.tempAreaList = new ArrayList<>();

        final String filenameSuffix = "_" + mapName + "_ORIGINAL";
        this.miniTileAltitudes = DummyDataUtils.readIntegerArrayFromArchiveFile("MiniTile_Altitudes" + filenameSuffix, null, " "); //TODO: Don't pass null.

        {
            final int valuesPerGroup = 3;

            final int[] array = DummyDataUtils.readIntegerArrayFromArchiveFile("DeltasByAscendingAltitude_sorted" + filenameSuffix, null, " "); //TODO: Don't pass null.

            Assert.assertTrue("Dummy data file contains invalid number of integers: " + array.length, (array.length >= valuesPerGroup) && (array.length % valuesPerGroup == 0));

            for (int i = 0; i < array.length; i += valuesPerGroup) {
                final int x = array[i];
                final int y = array[i + 1];
                final WalkPosition w = new WalkPosition(x, y);

                final int altitude = array[i + 2];

                this.deltasByAscendingAltitude.add(new MutablePair<>(w, new Altitude(altitude)));
            }
        }

        {
            final int valuesPerGroup = 4;
            final int[] array = DummyDataUtils.readIntegerArrayFromArchiveFile("MiniTilesByDescendingAltitude_sorted" + filenameSuffix, null, " "); //TODO: Don't pass null.
            
            Assert.assertTrue("Dummy data file contains invalid number of integers: " + array.length, (array.length >= valuesPerGroup) && (array.length % valuesPerGroup == 0));

            for (int i = 0; i < array.length; i += valuesPerGroup) {
                final int x = array[i];
                final int y = array[i + 1];
                final WalkPosition w = new WalkPosition(x, y);

                final int areaId = array[i + 2];
                final int altitude = array[i + 3];
                final MiniTile miniTile = new MiniTileImpl();
                if (areaId != -1) {
                    ((MiniTileImpl) miniTile).setAreaId(new AreaId(areaId));
                }
                if (altitude != -1) {
                    ((MiniTileImpl) miniTile).setAltitude(new Altitude(altitude));
                }

                this.miniTilesByDescendingAltitude.add(new MutablePair<>(w, miniTile));
            }
        }

        {
            final int valuesPerGroup = 6;
            final int[] array = DummyDataUtils.readIntegerArrayFromArchiveFile("TempAreaList" + filenameSuffix, null, " "); //TODO: Don't pass null.

            Assert.assertTrue("Dummy data file contains invalid number of integers: " + array.length, (array.length >= valuesPerGroup) && (array.length % valuesPerGroup == 0));

            for (int i = 0; i < array.length; i += valuesPerGroup) {
                final boolean valid = (array[i] == 1);
                final int id = array[i + 1];
                final int x = array[i + 2];
                final int y = array[i + 3];
                final int highestAltitude = array[i + 4];
                final int size = array[i + 5];
                final TempAreaInfo tempAreaInfo = new TempAreaInfo(valid, new AreaId(id), new WalkPosition(x, y), new Altitude(highestAltitude), size);

                this.tempAreaList.add(tempAreaInfo);
            }
        }
    }

}
