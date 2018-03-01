package bwem.area;

import bwem.Base;
import bwem.ChokePoint;
import bwem.area.typedef.AreaId;
import bwem.area.typedef.GroupId;
import bwem.typedef.Altitude;
import bwem.unit.Geyser;
import bwem.unit.Mineral;
import org.openbw.bwapi4j.TilePosition;
import org.openbw.bwapi4j.WalkPosition;

import java.util.List;

/**
 * Areas are regions that BWEM automatically computes from Brood War's maps.<br/>
 * Areas aim at capturing relevant regions that can be walked, though they may contain small inner non walkable regions called lakes.<br/>
 * More formally:<br/>
 *  - An area consists in a set of 4-connected MiniTiles, which are either Terrain-MiniTiles or Lake-MiniTiles.<br/>
 *  - An Area is delimited by the side of the Map, by Water-MiniTiles, or by other Areas. In the latter case
 *    the adjoining Areas are called neighboring Areas, and each pair of such Areas defines at least one ChokePoint.<br/>
 * Like ChokePoints and Bases, the number and the addresses of Area instances remain unchanged.<br/>
 * To access Areas one can use their ids or their addresses with equivalent efficiency.<br/>
 *
 * Areas inherit utils::Markable, which provides marking ability.<br/>
 * Areas inherit utils::UserData, which provides free-to-use data.
 */
public interface Area {

    /**
     * - Unique id > 0 of this Area. Range = 1.. Map::Areas().size()<br/>
     * - this == Map::GetArea(id())<br/>
     * - id() == Map::GetMiniTile(w).AreaId() for each walkable MiniTile w in this Area.<br/>
     * - Area::ids are guaranteed to remain unchanged.
     */
    AreaId getId();

    /**
     * - Unique id > 0 of the group of Areas which are accessible from this Area.<br/>
     * - For each pair (a, b) of Areas: a->GroupId() == b->GroupId()  <==>  a->AccessibleFrom(b)<br/>
     * - A groupId uniquely identifies a maximum set of mutually accessible Areas, that is, in the absence of blocking ChokePoints, a continent.
     */
    GroupId getGroupId();

    /**
     * Returns the top left position of the bounding box of this area.
     */
    TilePosition getTopLeft();

    /**
     * Returns the bottom right position of the bounding box of this area.
     */
    TilePosition getBottomRight();

    /**
     * Returns the bounding box size of this area.
     * The size is equal to (bottom right) subtract (top left) plus (1).
     */
    TilePosition getBoundingBoxSize();

    /**
     * Returns the position of the MiniTile with the highest altitude value.
     */
    WalkPosition getWalkPositionWithHighestAltitude();

    /**
     * Returns the highest altitude observed in this area.
     */
    Altitude getHighestAltitude();

    /**
     * Returns the number of MiniTiles in this area. This most accurately defines the size of this area.
     */
    int getSize();

    /**
     * Returns the percentage of low ground tiles in this area.
     */
    int getLowGroundPercentage();

    /**
     * Returns the percentage of high ground tiles in this area.
     */
    int getHighGroundPercentage();

    /**
     * Returns the percentage of very high ground tiles in this area.
     */
    int getVeryHighGroundPercentage();

    /**
     * Returns the ChokePoints between this Area and the neighboring ones.<br/>
     * - Note: if there are no neighboring Areas, then an empty set is returned.<br/>
     * - Note: there may be more ChokePoints returned than the number of neighboring Areas, as there may be several ChokePoints between two Areas.
     *
     * @see #getChokePoints(Area)
     */
    List<ChokePoint> getChokePoints();

    /**
     * Returns the ChokePoints between this Area and the specified area.<br/>
     * - Assumes the specified area is a neighbor of this area, i.e. ChokePointsByArea().find(pArea) != ChokePointsByArea().end()<br/>
     * - Note: there is always at least one ChokePoint between two neighboring Areas.
     *
     * @param area the specified area
     */
    List<ChokePoint> getChokePoints(Area area);

    /**
     * Returns the ChokePoints of this Area grouped by neighboring Areas.
     * Note: if there are no neighboring Areas, than an empty set is returned.
     */
    java.util.Map<Area, List<ChokePoint>> getChokePointsByArea();

    /**
     * Returns the accessible neighboring Areas.<br/>
     * - The accessible neighboring Areas are a subset of the neighboring Areas (the neighboring Areas can be iterated using ChokePointsByArea()).<br/>
     * - Two neighboring Areas are accessible from each over if at least one the ChokePoints they share is not Blocked.
     *
     * @see ChokePoint#isBlocked()
     */
    List<Area> getAccessibleNeighbors();

    /**
     * Returns whether this Area is accessible from the specified area, that is, if they share the same GroupId().<br/>
     * - Note: accessibility is always symmetrical.<br/>
     * - Note: even if a and b are neighboring Areas,<br/>
     *       we can have: a->AccessibleFrom(b)<br/>
     *       and not:     contains(a->AccessibleNeighbors(), b)
     *
     * @param area the specified area
     * @see #getGroupId()
     */
    boolean isAccessibleFrom(Area area);

    /**
     * Returns the Minerals contained in this Area.<br/>
     */
    List<Mineral> getMinerals();

    /**
     * Returns the Geysers contained in this Area.
     */
    List<Geyser> getGeysers();

    /**
     * Returns the Bases contained in this Area.
     */
    List<Base> getBases();

}
