package org.openbw.bwapi4j;

import org.openbw.bwapi4j.type.UnitType;

/**
 * Contains all damage-related bwapi functionality.
 */
public final class DamageEvaluator {

	
	/* default */ DamageEvaluator() {
		
	}

	private native int getDamageFrom_native(int fromType, int toType, int fromPlayer);
    private native int getDamageFrom_native(int fromType, int toType);
    private native int getDamageFrom_native(int fromType, int toType, int fromPlayer, int toPlayer);
    private native int getDamageTo_native(int toType, int fromType, int toPlayer);
    private native int getDamageTo_native(int toType, int fromType);
    private native int getDamageTo_native(int toType, int fromType, int toPlayer, int fromPlayer);

	public int getDamageFrom(UnitType fromType, UnitType toType, Player fromPlayer, Player toPlayer) {
		
		return getDamageFrom_native(fromType.ordinal(), toType.ordinal(), fromPlayer.getId(), toPlayer.getId());
	}
	
	public int getDamageFrom(UnitType fromType, UnitType toType) {
		return getDamageFrom_native(fromType.ordinal(), toType.ordinal());
	}
	
	public int getDamageFrom(UnitType fromType, UnitType toType, Player fromPlayer) {
		return getDamageFrom_native(fromType.ordinal(), toType.ordinal(), fromPlayer.getId());
	}
	
	public int getDamageTo(UnitType toType, UnitType fromType) {
		return getDamageTo_native(toType.ordinal(), fromType.ordinal());
	}
	
	public int getDamageTo(UnitType toType, UnitType fromType, Player toPlayer) {
		return getDamageTo_native(toType.ordinal(), fromType.ordinal(), toPlayer.getId());
	}
	
	public int getDamageTo(UnitType toType, UnitType fromType, Player toPlayer, Player fromPlayer) {
		return getDamageTo_native(toType.ordinal(), fromType.ordinal(), toPlayer.getId(), fromPlayer.getId());
	}
}
