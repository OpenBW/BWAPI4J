package bwem.map;

import bwem.typedef.Altitude;
import org.apache.commons.lang3.mutable.MutableInt;
import org.apache.commons.lang3.tuple.MutablePair;
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

public class OriginalBwemData {

    public final int[] miniTileAltitudes_ORIGINAL;
    public final List<MutablePair<WalkPosition, Altitude>> deltasByAscendingAltitude;

    public OriginalBwemData() throws IOException, URISyntaxException {
        int width = 128;
        int height = 128;
        int walkWidth = width * 4;
        int walkHeight = height * 4;

        this.miniTileAltitudes_ORIGINAL = new int[walkWidth * walkHeight];
        this.deltasByAscendingAltitude = new ArrayList<>();

        final String filenameSuffix = "_FightingSpirit" + "_ORIGINAL.txt";
        populateIntegerArray("MiniTile_Altitudes" + filenameSuffix, this.miniTileAltitudes_ORIGINAL, " ");

        {
            final String filename = "DeltasByAscendingAltitude_sorted" + filenameSuffix;
            final String regex = " ";
            final List<Integer> array = new ArrayList<>();
            final MutableInt index = new MutableInt(0);
            final URI fileURI = OriginalBwapiData.class.getResource(filename).toURI();
            final Stream<String> stream = Files.lines(Paths.get(fileURI));
            stream.forEach(l -> {
                for (final String s : l.split(regex)) {
                    array.add(index.getAndIncrement(), Integer.valueOf(s.trim()));
                }
            });
            stream.close();
            System.out.println("added " + index + " values.");

            Assert.assertTrue("dummy data file contains invalid number of integers: " + array.size(), (array.size() >= 3) && (array.size() % 3 == 0));

            for (int i = 0; i < array.size(); i += 3) {
                final int x = array.get(i);
                final int y = array.get(i + 1);
                final WalkPosition w = new WalkPosition(x, y);
                final int altitude = array.get(i + 2);
                this.deltasByAscendingAltitude.add(new MutablePair<>(w, new Altitude(altitude)));
            }
        }
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
