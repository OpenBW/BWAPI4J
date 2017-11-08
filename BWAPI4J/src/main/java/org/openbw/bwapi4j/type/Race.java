package org.openbw.bwapi4j.type;

public enum Race {

    Zerg(0), Terran(1), Protoss(2), None(3), Unknown(4);

    private int id;
    private UnitType worker;
    private UnitType center;
    private UnitType refinery;
    private UnitType transport;
    private UnitType supplyProvider;

    private Race(int id) {
        this.id = id;
        switch(id) {
        case 0:
        	this.worker = UnitType.Zerg_Drone;
        	this.center = UnitType.Zerg_Hatchery;
        	this.refinery = UnitType.Zerg_Extractor;
        	this.transport = UnitType.Zerg_Overlord;
        	this.supplyProvider = UnitType.Zerg_Overlord;
        	break;
        case 1:
        	this.worker = UnitType.Terran_SCV;
	    	this.center = UnitType.Terran_Command_Center;
	    	this.refinery = UnitType.Terran_Refinery;
	    	this.transport = UnitType.Terran_Dropship;
	    	this.supplyProvider = UnitType.Terran_Supply_Depot;
	    	break;
	    case 2:
	    	this.worker = UnitType.Protoss_Probe;
	    	this.center = UnitType.Protoss_Nexus;
	    	this.refinery = UnitType.Protoss_Assimilator;
	    	this.transport = UnitType.Protoss_Shuttle;
	    	this.supplyProvider = UnitType.Protoss_Pylon;
	    	break;
        }
    }

    public int getId() {
        return this.id;
    }

    /**
     * Retrieves the default worker type for this Race. Note In Starcraft,
     * workers are the units that are used to construct structures. Returns
     * UnitType of the worker that this race uses.
     */
    public UnitType getWorker() {
        return this.worker;
    }

    /**
     * Retrieves the default resource center UnitType that is used to create
     * expansions for this Race. Note In Starcraft, the center is the very first
     * structure of the Race's technology tree. Also known as its base of
     * operations or resource depot. Returns UnitType of the center that this
     * race uses.
     */
    public UnitType getCenter() {
        return this.center;
    }

    /**
     * Retrieves the default structure UnitType for this Race that is used to
     * harvest gas from Vespene Geysers. Note In Starcraft, you must first
     * construct a structure over a Vespene Geyser in order to begin harvesting
     * Vespene Gas. Returns UnitType of the structure used to harvest gas.
     */
    public UnitType getRefinery() {
        return this.refinery;
    }

    /**
     * Retrieves the default transport UnitType for this race that is used to
     * transport ground units across the map. Note In Starcraft, transports will
     * allow you to carry ground units over unpassable terrain. Returns UnitType
     * for transportation.
     */
    public UnitType getTransport() {
        return this.transport;
    }

    /**
     * Retrieves the default supply provider UnitType for this race that is used
     * to construct units. Note In Starcraft, training, morphing, or warping in
     * units requires that the player has sufficient supply available for their
     * Race. Returns UnitType that provides the player with supply.
     */
    public UnitType getSupplyProvider() {
        return this.supplyProvider;
    }
}
