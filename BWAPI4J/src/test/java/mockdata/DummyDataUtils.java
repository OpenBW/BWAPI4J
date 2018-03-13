package mockdata;

import org.apache.commons.compress.archivers.ArchiveEntry;
import org.apache.commons.compress.archivers.ArchiveInputStream;
import org.apache.commons.compress.archivers.tar.TarArchiveInputStream;
import org.apache.commons.compress.compressors.bzip2.BZip2CompressorInputStream;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.Assert;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

public final class DummyDataUtils {

    private static final Logger logger = LogManager.getLogger();

    private DummyDataUtils() {}

    private static String determineMapShortHash(final String mapHash) {
        final String mapShortHash;
        if (mapHash == null) {
            mapShortHash = "d2f5633cc4bb0fca13cd1250729d5530c82c7451".substring(0, 7);
        } else {
            mapShortHash = mapHash.substring(0, 7);
        }
        return mapShortHash;
    }

    private static ArchiveEntry getArchiveEntry(final ArchiveInputStream tarIn, final String startsWith) throws IOException {
        ArchiveEntry nextEntry;
        while ((nextEntry = tarIn.getNextEntry()) != null) {
            if (Paths.get(nextEntry.getName()).getFileName().toString().startsWith(startsWith)) {
                break;
            }
        }
        return nextEntry;
    }

    public static int[] readIntegerArrayFromArchiveFile(final String archiveFilename, final String mapHash, final String regex) throws IOException {
        final InputStream inputStream = DummyDataUtils.class.getResourceAsStream("/mockdata/" + archiveFilename + ".tar.bz2");

        final String mapShortHash = determineMapShortHash(mapHash);

        try (final ArchiveInputStream tarIn = new TarArchiveInputStream(new BZip2CompressorInputStream(inputStream));
                final BufferedReader buffer = new BufferedReader(new InputStreamReader(tarIn))) {

            final ArchiveEntry nextEntry = getArchiveEntry(tarIn, mapShortHash);
            Assert.assertNotNull(nextEntry);

            final int[] read = buffer.lines()
                    .flatMap(line -> Stream.of(line.split(regex)))
                    .map(String::trim)
                    .mapToInt(Integer::parseInt)
                    .toArray();
            logger.debug("Read " + read.length + " values from " + archiveFilename);
            return read;
        }
    }

    public static List<List<Integer>> readMultiLineIntegerArraysFromArchiveFile(final String archiveFilename, final String mapHash, final String regex) throws IOException {
        final InputStream inputStream = DummyDataUtils.class.getResourceAsStream("/mockdata/" + archiveFilename + ".tar.bz2");

        final String mapShortHash = determineMapShortHash(mapHash);

        try (final ArchiveInputStream tarIn = new TarArchiveInputStream(new BZip2CompressorInputStream(inputStream));
                final BufferedReader buffer = new BufferedReader(new InputStreamReader(tarIn))) {

            final ArchiveEntry nextEntry = getArchiveEntry(tarIn, mapShortHash);
            Assert.assertNotNull(nextEntry);

            final List<List<Integer>> data = new ArrayList<>();

            String line;
            while ((line = buffer.readLine()) != null) {
                if (line.isEmpty()) {
                    continue;
                }

                final String[] tokens = line.split(regex);
                final List<Integer> intTokens = new ArrayList<>();

                for (final String token : tokens) {
                    final String tokenTrimmed = token.trim();

                    if (tokenTrimmed.isEmpty()) {
                        continue;
                    }

                    int intToken = Integer.parseInt(tokenTrimmed);
                    intTokens.add(intToken);
                }

                data.add(intTokens);
            }

            int valuesReadCount = 0;
            for (final List<Integer> list : data) {
                valuesReadCount += list.size();
            }

            logger.debug("Read " + valuesReadCount + " values from " + archiveFilename);

            return data;
        }
    }

    public static List<List<String>> reacMultiLinesAsStringsFromArchiveFile(final String archiveFilename, final String mapHash, final String regex) throws IOException {
        final InputStream inputStream = DummyDataUtils.class.getResourceAsStream("/mockdata/" + archiveFilename + ".tar.bz2");

        final String mapShortHash = determineMapShortHash(mapHash);

        try (final ArchiveInputStream tarIn = new TarArchiveInputStream(new BZip2CompressorInputStream(inputStream));
             final BufferedReader buffer = new BufferedReader(new InputStreamReader(tarIn))) {

            final ArchiveEntry nextEntry = getArchiveEntry(tarIn, mapShortHash);
            Assert.assertNotNull(nextEntry);

            final List<List<String>> data = new ArrayList<>();

            String line;
            while ((line = buffer.readLine()) != null) {
                if (line.isEmpty()) {
                    continue;
                }

                final String[] tokens = line.split(regex);
                final List<String> strTokens = new ArrayList<>();

                for (final String token : tokens) {
                    final String tokenTrimmed = token.trim();

                    if (tokenTrimmed.isEmpty()) {
                        continue;
                    }

                    strTokens.add(tokenTrimmed);
                }

                data.add(strTokens);
            }

            int valuesReadCount = 0;
            for (final List<String> list : data) {
                valuesReadCount += list.size();
            }

            logger.debug("Read " + valuesReadCount + " values from " + archiveFilename);

            return data;
        }
    }

//    public static int[] parseIntegerArray(final String filename, final String regex) throws URISyntaxException, IOException {
//        final MutableInt index = new MutableInt(0);
//        final URI fileURI = DummyDataUtils.class.getResource(filename).toURI();
//        final List<Integer> array = new ArrayList<>();
//        final Stream<String> stream = Files.lines(Paths.get(fileURI));
//        stream.forEach(l -> {
//            for (final String s : l.split(regex)) {
//                array.add(Integer.valueOf(s.trim()));
//                index.increment();
//            }
//        });
//        stream.close();
//
//        logger.debug("Read " + index.intValue() + " values");
//
//        final int[] ret = new int[array.size()];
//        for (int i = 0; i < array.size(); ++i) {
//            ret[i] = array.get(i);
//        }
//
//        return ret;
//    }

}
