package mockdata;

import org.apache.commons.lang3.mutable.MutableInt;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

public final class DummyDataUtils {

    private static final Logger logger = LogManager.getLogger();

    private DummyDataUtils() {}

    public static void populateIntegerArray(final String filename, final int[] array, final String regex) throws URISyntaxException, IOException {
        final MutableInt index = new MutableInt(0);
        final URI fileURI = DummyDataUtils.class.getResource(filename).toURI();
        final Stream<String> stream = Files.lines(Paths.get(fileURI));
        stream.forEach(l -> {
            for (final String s : l.split(regex)) {
                array[index.getAndIncrement()] = Integer.valueOf(s.trim());
            }
        });
        stream.close();
        logger.debug("Added " + index + " values");
    }

    public static int[] parseIntegerArray(final String filename, final String regex) throws URISyntaxException, IOException {
        final MutableInt index = new MutableInt(0);
        final URI fileURI = DummyDataUtils.class.getResource(filename).toURI();
        final List<Integer> array = new ArrayList<>();
        final Stream<String> stream = Files.lines(Paths.get(fileURI));
        stream.forEach(l -> {
            for (final String s : l.split(regex)) {
                array.add(Integer.valueOf(s.trim()));
            }
        });
        stream.close();

        logger.debug("Read " + index + " values");

        final int[] ret = new int[array.size()];
        for (int i = 0; i < array.size(); ++i) {
            ret[i] = array.get(i);
        }

        return ret;
    }

}
