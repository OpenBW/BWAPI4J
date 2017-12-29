package org.openbw.bwapi4j.unit;

import org.openbw.bwapi4j.type.WeaponType;

/**
 * Holds information for a ground/air weapon.
 */
public class Weapon {
    private WeaponType type;
    private int cooldown;

    public Weapon(WeaponType type, int cooldown) {
        this.type = type;
        this.cooldown = cooldown;
    }

    public int cooldown() {
        return cooldown;
    }

    public WeaponType type() {
        return type;
    }

    void update(WeaponType type, int cooldown) {
        this.type = type;
        this.cooldown = cooldown;
    }
}
