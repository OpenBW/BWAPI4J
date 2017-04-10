package org.openbw.bwapi4j;

import org.openbw.bwapi4j.unit.Unit;

public interface BWEventListener {

    public void onStart();

    public void onEnd(boolean isWinner);

    public void onFrame();

    public void onSendText(String text);

    public void onReceiveText(Player player, String text);

    public void onPlayerLeft(Player player);

    public void onNukeDetect(Position target);

    public void onUnitDiscover(Unit unit);

    public void onUnitEvade(Unit unit);

    public void onUnitShow(Unit unit);

    public void onUnitHide(Unit unit);

    public void onUnitCreate(Unit unit);

    public void onUnitDestroy(Unit unit);

    public void onUnitMorph(Unit unit);

    public void onUnitRenegade(Unit unit);

    public void onSaveGame(String gameName);

    public void onUnitComplete(Unit unit);
}
