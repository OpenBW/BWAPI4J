package mockdata;

import org.apache.commons.lang3.tuple.ImmutablePair;
import org.openbw.bwapi4j.Position;
import org.openbw.bwapi4j.WalkPosition;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class BWEM_CPPathSamples {

    public static class CPPSample {

        public final ImmutablePair<Position, Position> startAndEnd;
        public final List<WalkPosition> pathNodes;
        public final int pathLength;

        public CPPSample(final ImmutablePair<Position, Position> startAndEnd, final int length, final List<WalkPosition> nodes) {
            this.startAndEnd = startAndEnd;
            this.pathNodes = nodes;
            this.pathLength = length;
        }

    }

    public final List<CPPSample> samples;

    public BWEM_CPPathSamples(final String mapHash) throws IOException, URISyntaxException {
        this.samples = new ArrayList<>();

        final String filename = mapHash.substring(0, 7) + "_CPPathSamples_1_Node_Min_ORIGINAL.txt";

        final URI fileURI = DummyDataUtils.class.getResource("DummyBwemData" + File.separator + filename).toURI();

        final FileInputStream fis = new FileInputStream(Paths.get(fileURI).toString());
        final BufferedReader br = new BufferedReader(new InputStreamReader(fis, StandardCharsets.UTF_8));
        String line;
        while ((line = br.readLine()) != null) {
            if (line.isEmpty()) {
                continue;
            }

            final String[] tokens = line.trim().split(" ");
            final int[] vals = new int[tokens.length];
            for (int i = 0; i < tokens.length; ++i) {
                vals[i] = Integer.parseInt(tokens[i]);
            }

            int index = 0;

            final int x1 = vals[index++];
            final int y1 = vals[index++];
            final Position start = new Position(x1, y1);
            final int x2 = vals[index++];
            final int y2 = vals[index++];
            final Position end = new Position(x2, y2);
            final ImmutablePair<Position, Position> startAndEnd = new ImmutablePair<>(start, end);

            final int pathLength = vals[index++];

            final List<WalkPosition> pathNodes = new ArrayList<>();
            for (int i = index; i < vals.length; i += 2) {
                final int x = vals[i];
                final int y = vals[i + 1];
                final WalkPosition node = new WalkPosition(x, y);
                pathNodes.add(node);
            }

            final CPPSample sample = new CPPSample(startAndEnd, pathLength, pathNodes);
            this.samples.add(sample);
        }
        br.close();
        fis.close();
    }

}
