package bwapi;

import java.util.ArrayList;
import java.util.List;
import org.openbw.bwapi4j.util.buffer.BwapiDataBuffer;
import org.openbw.bwapi4j.util.buffer.DataBuffer;

public class Force {
  private final int id;
  private final BW bw;

  Force(final int id, final BW bw) {
    this.id = id;
    this.bw = bw;
  }

  public int getID() {
    return id;
  }

  private native String getName_native(int forceId);

  public String getName() {
    return getName_native(getID());
  }

  private native int[] getPlayerIds_native(int forceId);

  public List<Player> getPlayers() {
    final DataBuffer playerIds = new DataBuffer(getPlayerIds_native(getID()));

    final List<Player> players = new ArrayList<>(playerIds.size());

    while (playerIds.hasNext()) {
      players.add(BwapiDataBuffer.getPlayerById(playerIds, bw));
    }

    return players;
  }

  @Override
  public boolean equals(final Object object) {
    if (this == object) {
      return true;
    } else if (object instanceof Force) {
      final Force that = (Force) object;
      return this.getID() == that.getID();
    } else {
      return false;
    }
  }

  @Override
  public int hashCode() {
    return getID();
  }
}
