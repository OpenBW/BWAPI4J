package bwem.map;

import org.apache.commons.lang3.mutable.MutableInt;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.stream.Stream;

public class OriginalBwemData {

    public final int[] miniTileAltitudes_ORIGINAL;

    public OriginalBwemData() throws IOException, URISyntaxException {
        int width = 128;
        int height = 128;
        int walkWidth = width * 4;
        int walkHeight = height * 4;

        this.miniTileAltitudes_ORIGINAL = new int[walkWidth * walkHeight];

        final String filenameSuffix = "_FightingSpirit" + "_ORIGINAL.txt";
        populateIntegerArray("MiniTile_Altitudes" + filenameSuffix, this.miniTileAltitudes_ORIGINAL, " ");
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
        System.out.println("added " + index + " values.");
    }

}
