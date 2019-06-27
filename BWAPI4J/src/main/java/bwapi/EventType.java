package bwapi;

public enum EventType {
  MatchStart,
  MatchEnd,
  MatchFrame,
  MenuFrame,
  SendText,
  ReceiveText,
  PlayerLeft,
  NukeDetect,
  UnitDiscover,
  UnitEvade,
  UnitShow,
  UnitHide,
  UnitCreate,
  UnitDestroy,
  UnitMorph,
  UnitRenegade,
  SaveGame,
  UnitComplete,
  // TriggerAction,
  None;

  public static EventType withId(int id) {
    return values()[id];
  }
}
