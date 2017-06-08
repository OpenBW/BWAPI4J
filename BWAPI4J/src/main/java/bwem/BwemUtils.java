package bwem;

public class BwemUtils {

    public enum check_t {
        no_check,
        check
    }

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

}
