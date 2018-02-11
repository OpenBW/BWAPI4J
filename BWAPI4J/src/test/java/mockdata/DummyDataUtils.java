package mockdata;

import org.apache.commons.compress.archivers.ArchiveEntry;
import org.apache.commons.compress.archivers.ArchiveInputStream;
import org.apache.commons.compress.archivers.tar.TarArchiveInputStream;
import org.apache.commons.compress.compressors.bzip2.BZip2CompressorInputStream;
import org.apache.commons.lang3.mutable.MutableInt;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.Assert;

import java.io.*;
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

    public static int[] populateIntegerArray(final String filename, final String regex) throws IOException {
        final MutableInt index = new MutableInt(0);
        InputStream inputStream = DummyDataUtils.class.getResourceAsStream("/mockdata/" + filename + ".tar.bz2");

        try (ArchiveInputStream tarIn = new TarArchiveInputStream(new BZip2CompressorInputStream(inputStream));
             BufferedReader buffer = new BufferedReader(new InputStreamReader(tarIn))) {
            ArchiveEntry nextEntry = tarIn.getNextEntry();
            Assert.assertNotNull(nextEntry);
            int[] read = buffer.lines()
                    .flatMap(line -> Stream.of(line.split(regex)))
                    .map(String::trim)
                    .mapToInt(Integer::parseInt)
                    .toArray();
            logger.debug("Read " + index + " values");
            return read;
        }
    }

    public static int[] parseIntegerArray(final String filename, final String regex) throws URISyntaxException, IOException {
        final MutableInt index = new MutableInt(0);
        final URI fileURI = DummyDataUtils.class.getResource(filename).toURI();
        final List<Integer> array = new ArrayList<>();
        final Stream<String> stream = Files.lines(Paths.get(fileURI));
        stream.forEach(l -> {
            for (final String s : l.split(regex)) {
                array.add(Integer.valueOf(s.trim()));
                index.increment();
            }
        });
        stream.close();

        logger.debug("Read " + index.intValue() + " values");

        final int[] ret = new int[array.size()];
        for (int i = 0; i < array.size(); ++i) {
            ret[i] = array.get(i);
        }

        return ret;
    }

}
