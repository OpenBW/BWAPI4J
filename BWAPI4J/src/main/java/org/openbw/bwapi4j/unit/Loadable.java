package org.openbw.bwapi4j.unit;

public interface Loadable {

    boolean isLoaded();

    boolean load(MobileUnit target);

    /**
     * Loads target unit into this transporter.
     * @param target unit to load
     * @param queued true if command is queued
     * @return true is command successful, false else
     */
    boolean load(MobileUnit target, boolean queued);

    boolean unload(MobileUnit target);

    boolean unloadAll();

    boolean unloadAll(boolean queued);



//    // TODO implement space remaining for JNI and then subclasses
//    private int spaceRemaining;
//    int getSpaceRemaining();

//    // TODO implement units loaded for JNI and then subclasses
//    private List<Unit> loadedUnits;
//    List<PlayerUnit> getLoadedUnits();

}
