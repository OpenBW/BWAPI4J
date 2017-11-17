package mockdata;

import java.io.IOException;
import java.net.URISyntaxException;

public class BWEM_FightingSpirit extends BWEM_DummyData {

    public BWEM_FightingSpirit() throws IOException, URISyntaxException {
        super();

        final int tileWidth = 128;
        final int tileHeight = 128;

        super.populateArrays("FightingSpirit", tileWidth, tileHeight);
    }

}
