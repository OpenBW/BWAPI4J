package bwem.map;

import org.apache.commons.lang3.mutable.MutableInt;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openbw.bwapi4j.TilePosition;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.stream.Stream;

public class OriginalBwapiData {

    private static final Logger logger = LogManager.getLogger();

    public final int[] walkabilityInfo_ORIGINAL;
    public final int[] groundInfo_ORIGINAL;
    public final int[] buildableInfo_ORIGINAL;
    public final TilePosition mapSize_FightingSpirit_ORIGINAL = new TilePosition(128, 128);
    public final TilePosition[] startLocations_FightingSpirit_ORIGINAL = {new TilePosition(117, 7), new TilePosition(7, 6), new TilePosition(7, 116), new TilePosition(117, 117)};

    public OriginalBwapiData() throws IOException, URISyntaxException {
        int width = 128;
        int height = 128;
        int walkWidth = width * 4;
        int walkHeight = height * 4;

        this.walkabilityInfo_ORIGINAL = new int[walkWidth * walkHeight];
        this.groundInfo_ORIGINAL = new int[width * height];
        this.buildableInfo_ORIGINAL = new int[width * height];

        final String filenameSuffix = "_FightingSpirit" + "_ORIGINAL.txt";
        populateIntegerArray("walkabilityInfo" + filenameSuffix, this.walkabilityInfo_ORIGINAL, " ");
        populateIntegerArray("groundInfo" + filenameSuffix, this.groundInfo_ORIGINAL, " ");
        populateIntegerArray("buildableInfo" + filenameSuffix, this.buildableInfo_ORIGINAL, " ");
    }

    private void populateIntegerArray(final String filename, final int[] array, final String regex) throws URISyntaxException, IOException {
        final MutableInt index = new MutableInt(0);
        URI fileURI = OriginalBwapiData.class.getResource(filename).toURI();
        Stream<String> stream = Files.lines(Paths.get(fileURI));
        stream.forEach(l -> {
            for (String s : l.split(regex)) {
                array[index.getAndIncrement()] = Integer.valueOf(s.trim());
            }
        });
        stream.close();
        logger.debug("Added " + index + " values");
    }

}
