package mockdata;

import org.openbw.bwapi4j.TilePosition;

import java.io.IOException;
import java.net.URISyntaxException;

public class BWAPI_FightingSpirit extends BWAPI_DummyData {

    public BWAPI_FightingSpirit() throws IOException, URISyntaxException {
        super();

        final int tileWidth = 128;
        final int tileHeight = 128;

        super.name = "FightingSpirit";
        super.hash = "mockHash";
        super.mapSize = new TilePosition(tileWidth, tileHeight);
        super.startingLocations = new TilePosition[]{new TilePosition(117, 7), new TilePosition(7, 6), new TilePosition(7, 116), new TilePosition(117, 117)};

        super.populateArrays("FightingSpirit", tileWidth, tileHeight);
    }

}
