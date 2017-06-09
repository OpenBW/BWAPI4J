package bwem;

import org.openbw.bwapi4j.WalkPosition;

public class BwemUtils {

    /**
     * Enum to satisfy its C++ counterpart:
     *   defs.h:62:enum class check_t {no_check, check};
     */
    public enum check_t {
        no_check,
        check
    }

    //TODO: What is the reason for this function?
    /* utils.h:38:template <class T> void unused(const T &) {} */
    public static <T> void unused(T obj) {
        /* Do nothing. */
    }

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
    public boolean seaSide(WalkPosition p, Map pMap) {
        if (!pMap.GetMiniTile(p).Sea()) {
            return false;
        }

        WalkPosition[] deltas = { new WalkPosition(0, -1), new WalkPosition(-1, 0), new WalkPosition(1, 0), new WalkPosition(0, 1) };
        for (WalkPosition delta : deltas) {
            WalkPosition pDelta = new WalkPosition(p.getX() + delta.getX(), p.getY() + delta.getY());
            if (pMap.Valid(pDelta)
                    && !pMap.GetMiniTile(pDelta, check_t.no_check).Sea()) {
                return true;
            }
        }

        return false;
    }

}
