package org.openbw.bwapi4j.util;

import org.openbw.bwapi4j.InteractionHandler;
import org.openbw.bwapi4j.Player;
import org.openbw.bwapi4j.unit.PlayerUnit;
import org.openbw.bwapi4j.unit.Unit;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class GetUnitsFromPlayerCache {

    private final Map<Integer, List<PlayerUnit>> playerIdToUnitsMap;
    private final Map<Integer, Unit> units;
    private final InteractionHandler interactionHandler;
    private final Map<Integer, Integer> playerIdToLastFrameRefreshMap;
    private final int refreshPeriod;

    public GetUnitsFromPlayerCache(final Map<Integer, Unit> units, final InteractionHandler interactionHandler) {
        this.playerIdToUnitsMap = new HashMap<>();
        this.units = units;
        this.interactionHandler = interactionHandler;
        this.playerIdToLastFrameRefreshMap = new HashMap<>();
        this.refreshPeriod = 1;
    }

    private boolean canRefresh(final Player player) {
        final int playerId = player.getId();

        final List<PlayerUnit> units = this.playerIdToUnitsMap.get(playerId);
        if (units == null) {
            return true;
        }

        final Integer lastFrameRefresh = this.playerIdToLastFrameRefreshMap.get(playerId);
        if (lastFrameRefresh == null) {
            return true;
        }

        final int currentFrameCount = getFrameCount();

        return (lastFrameRefresh + this.refreshPeriod < currentFrameCount + 1);
    }

    private int getFrameCount() {
        return this.interactionHandler.getFrameCount();
    }

    public List<PlayerUnit> get(final Player player) {
        final int playerId = player.getId();

        if (canRefresh(player)) {
            this.playerIdToUnitsMap.put(playerId, this.units.values()
                    .stream()
                    .filter(u -> u instanceof PlayerUnit && ((PlayerUnit) u).getPlayer().equals(player))
                    .map(u -> (PlayerUnit) u).collect(Collectors.toList()));
            this.playerIdToLastFrameRefreshMap.put(playerId, getFrameCount());
        }

        return this.playerIdToUnitsMap.get(playerId);
    }

}
