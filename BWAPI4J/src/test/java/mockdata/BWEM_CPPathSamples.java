package mockdata;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import org.apache.commons.lang3.tuple.ImmutablePair;
import org.openbw.bwapi4j.Position;
import org.openbw.bwapi4j.WalkPosition;

public class BWEM_CPPathSamples {

  public static class CPPSample {

    public final ImmutablePair<Position, Position> startAndEnd;
    public final List<WalkPosition> pathNodes;
    public final int pathLength;

    public CPPSample(
        final ImmutablePair<Position, Position> startAndEnd,
        final int length,
        final List<WalkPosition> nodes) {
      this.startAndEnd = startAndEnd;
      this.pathNodes = nodes;
      this.pathLength = length;
    }
  }

  public final List<CPPSample> samples;

  public BWEM_CPPathSamples(final String mapHash) throws IOException {
    this.samples = new ArrayList<>();

    final List<List<Integer>> samples =
        DummyDataUtils.readMultiLineIntegerArraysFromArchiveFile(
            DummyDataUtils.compileBwemDataSetArchiveFilename(
                "CPPathSamples_1_Node_Min",
                BWAPI_DummyData.DataSetBwapiVersion.BWAPI_420.toString(),
                BWEM_DummyData.DataSetBwemVersion.BWEM_141.toString()),
            mapHash,
            " ");

    for (final List<Integer> sample : samples) {
      int index = 0;

      final int startX = sample.get(index++);
      final int startY = sample.get(index++);
      final Position start = new Position(startX, startY);

      final int endX = sample.get(index++);
      final int endY = sample.get(index++);
      final Position end = new Position(endX, endY);

      final ImmutablePair<Position, Position> startAndEnd = new ImmutablePair<>(start, end);

      final int pathLength = sample.get(index++);

      final List<WalkPosition> pathNodes = new ArrayList<>();
      for (int i = index; i < sample.size(); i += 2) {
        final int nodeX = sample.get(i);
        final int nodeY = sample.get(i + 1);
        final WalkPosition node = new WalkPosition(nodeX, nodeY);
        pathNodes.add(node);
      }

      this.samples.add(new CPPSample(startAndEnd, pathLength, pathNodes));
    }
  }
}
