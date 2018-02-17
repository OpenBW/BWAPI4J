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
}
