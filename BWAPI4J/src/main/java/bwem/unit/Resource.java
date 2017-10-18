/*
Status: Ready for use
*/

package bwem.unit;

import bwem.map.Map;
import org.openbw.bwapi4j.unit.Unit;

//////////////////////////////////////////////////////////////////////////////////////////////
//                                                                                          //
//                                  class Ressource
//                                                                                          //
//////////////////////////////////////////////////////////////////////////////////////////////
//
// A Ressource is either a Mineral or a Geyser
//
//////////////////////////////////////////////////////////////////////////////////////////////

public abstract class Resource extends Neutral {

    protected Resource(Unit u, Map pMap) {
        super(u, pMap);
    }

    // Returns the initial amount of ressources for this Ressource (same as Unit()->getInitialResources).
    public abstract int InitialAmount();

    // Returns the current amount of ressources for this Ressource (same as Unit()->getResources).
    public abstract int Amount();

}
