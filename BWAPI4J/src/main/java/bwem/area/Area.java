/*
Status: Incomplete
*/

package bwem.area;

//////////////////////////////////////////////////////////////////////////////////////////////
//                                                                                          //
//                                  class Area
//                                                                                          //
//////////////////////////////////////////////////////////////////////////////////////////////
//
// Areas are regions that BWEM automatically computes from Brood War's maps
// Areas aim at capturing relevant regions that can be walked, though they may contain small inner non walkable regions called lakes.
// More formally:
//  - An area consists in a set of 4-connected MiniTiles, which are either Terrain-MiniTiles or Lake-MiniTiles.
//  - An Area is delimited by the side of the Map, by Water-MiniTiles, or by other Areas. In the latter case
//    the adjoining Areas are called neighbouring Areas, and each pair of such Areas defines at least one ChokePoint.
// Like ChokePoints and Bases, the number and the addresses of Area instances remain unchanged.
// To access Areas one can use their ids or their addresses with equivalent efficiency.
//
// Areas inherit utils::Markable, which provides marking ability
// Areas inherit utils::UserData, which provides free-to-use data.
//
//////////////////////////////////////////////////////////////////////////////////////////////

public class Area {





}
