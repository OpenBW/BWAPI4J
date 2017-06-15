package bwem;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openbw.bwapi4j.BW;

public class Map {

    private static final Logger logger = LogManager.getLogger();

    private static final Map INSTANCE = new Map();

    private BW bw = null;

    public static Map Instance() {
        return INSTANCE;
    }

    public void initialize(BW bw) {
        this.bw = bw;
    }

}
