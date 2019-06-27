package bwapi;

import java.util.ArrayList;
import java.util.List;

public class Force {
  private final int id;
  private final String name;
  private final List<Player> players;

  Force(final int id, final String name, final List<Player> players) {
    this.id = id;
    this.name = name;
    this.players = new ArrayList<>(players);
  }

  public int getID() {
    return id;
  }

  public String getName() {
    return name;
  }

  public List<Player> getPlayers() {
    return new ArrayList<>(players);
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
