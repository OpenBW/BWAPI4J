package bwem;

import org.openbw.bwapi4j.WalkPosition;

public class BwemUtils {

    /* map.cpp:29 */
//    bool seaSide(WalkPosition p, const Map * pMap)
//    {
//        if (!pMap->GetMiniTile(p).Sea()) return false;
//
//        for (WalkPosition delta : {WalkPosition(0, -1), WalkPosition(-1, 0), WalkPosition(+1, 0), WalkPosition(0, +1)})
//            if (pMap->Valid(p + delta))
//                if (!pMap->GetMiniTile(p + delta, check_t::no_check).Sea())
//                    return true;
//
//        return false;
//    }
    /**
     * Tests if the specified position has any non-sea neighbors.
     *
     * @param p specified position
     * @param map specified map containing the specified position
     */
    public static boolean hasNonSeaNeighbor(WalkPosition p, Map map) {
        if (!map.getMiniTile(p).Sea()) {
            return false;
        }

        WalkPosition[] deltas = { new WalkPosition(0, -1), new WalkPosition(-1, 0), new WalkPosition(1, 0), new WalkPosition(0, 1) };
        for (WalkPosition delta : deltas) {
            WalkPosition pDelta = p.add(delta);
            if (map.isValid(pDelta) && !map.getMiniTile(pDelta).Sea()) {
                return true;
            }
        }

        return false;
    }

    public static int squaredNorm(int dx, int dy) {
        return (dx * dx + dy * dy);
    }

    public static double norm(int dx, int dy) {
        return Math.sqrt((double) squaredNorm(dx, dy));
    }

}
