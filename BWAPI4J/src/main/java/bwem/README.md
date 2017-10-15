# Project: BWAPI4J-BWEM

### Abstract

Port the BWEM C++ library to Java for compatibility with BWAPI4J. Main priority is the following methods and their dependencies:
* `graph.cpp:392:const CPPath & Graph::GetPath(const Position & a, const Position & b, int * pLength) const`

### Port Author

Adakite

### General Notes

* [BWEM - Getting Started](http://bwem.sourceforge.net/start.html)
* The user only needs to include the `BWEM\bwem.h` header file when using the library.
* BWEM recommends using `namespace { auto & theMap = BWEM::Map::Instance(); }` to create an alias for convenience. E.g. `theMap.Initialize();` instead of `BWEM::Map::Instance().Initialize();`.
> "The class BWEM::Map is the entry point for almost every thing in BWEM. The unique instance can be accessed using BWEM::Map::Instance(). For convenience, we define an alias for it : theMap, local to this file. We could as well use a reference or a pointer member of the class ExampleAIModule but, because the instance of Map is a global variable, this matters little."

> "Before we can use theMap, we need to initialize it. The right place to do this is in ExampleAIModule::onStart()."
* Default example code which should be placed in the `onStart` callback method:
```
theMap.Initialize();
theMap.EnableAutomaticPathAnalysis();
bool startingLocationsOK = theMap.FindBasesForStartingLocations();
assert(startingLocationsOK);
```
* This library uses `bwem_assert` approximately 106 times. ~~Throw custom exceptions instead?~~ Therefore, use Java asserts.
* ~~Use Java's `short` type in place of C++'s `int16_t`.~~

### Porting Notes

`BWEM::Map`
* This class is a singleton and an interface/abstract. For now, everything will be ported into `Map.java` as a ~~regular instantiatable class~~singleton.
* `BWEM::Map::Initialize()` code will be put into ~~the constructor `public Map(org.openbw.bwapi4j.BW)`~~`Map.initialize()`.
* ~~Parameterless constructor for `bwem.Map` has been publically disabled since the original `BWAPI::Broodwar` is required and a singleton~~. Instantiation, initialization, and access have been implemented to match `BWEM::Map::Instance()` and their counterparts.

### Modifications

#### Types

| Location | Scope | Original C++ | Java Port | C++ Type | Java Type | Description |
|-|-|-|-|-|-|-|
| `area.h:54` | public | `Area::id` | `Area.Id` | `int16_t` | `int` | This appears to be used everwhere in the code in place of `int16_t` instead of a dedicated `Area::id` type. |
| `area.h:56` | public | `Area::groupId` | `Area.GroupId` | `int16_t` | `int` | - |
| `defs.h:54` | global | `altitude_t` | `Altitude` | `int16_t` | `int` | altitude type in pixels |
| `mapImpl.cpp:293` | local method | `altitude_scale` | `MiniTile.SIZE_IN_PIXELS` | `altitude_t` | `int` | "8 provides a pixel definition for altitude_t, since altitudes are computed from miniTiles which are 8x8 pixels" |
| `cp.h:143` | public | `ChokePoint::index` | `Index` | `int` | `int` | - |

### Current Focus

-

### Class and Method Dependencies

`graph.cpp:392:const CPPath & Graph::GetPath`
* `graph.h:136:Graph::GetNearestArea`
  * `graph.cpp:161:Graph::GetArea`
    * `graph.h:64:Graph::GetMap`
  * `map.h:252:Map::BreadthFirstSearch`

`Graph`
* Area
* Chokepoint
* CPPath
* Neutral

`Area`
* altitude_t
* Base
* ChokePoint
* Geyser
* Graph
* Mineral

`CPPath`
* `typedef std::vector<const ChokePoint *> Path;`
* `typedef ChokePoint::Path CPPath;`

### Java Classes to Review
* [x] Altitude
* [ ] Area
  * [x] GroupId
  * [x] Id
  * [ ] TempInfo
* [x] Bits
* [ ] BWEM
* [x] CheckMode
* [ ] Chokepoint
* [ ] CPPath
* [ ] Graph
* [x] Index
* [x] IPred
* [x] IWrappedInteger
* [ ] Map
* [x] Markable
* [ ] MiniTile
* [x] PairGenericAltitudeComparator
* [x] StaticBuilding
* [ ] Tile
* [x] unit/Geyser
* [x] unit/Mineral
* [ ] unit/Neutral
* [x] unit/Resource
* [x] unit/StaticBuilding
* [x] UserData
