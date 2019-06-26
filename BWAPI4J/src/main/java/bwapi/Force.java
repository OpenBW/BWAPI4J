package bwapi;

import java.util.List;

public class Force {
  public int getID() {
    throw new UnsupportedOperationException("TODO"); // TODO
  }

  public String getName() {
    throw new UnsupportedOperationException("TODO"); // TODO
  }

  public List<Player> getPlayers() {
    throw new UnsupportedOperationException("TODO"); // TODO
  }

  @Override
  public boolean equals(final Object object) {
    if (object instanceof Force) {
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
