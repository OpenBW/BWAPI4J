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
* This library uses `bwem_assert` approximately 106 times. `if` statements and exception throwing will replace assertions for now.
### Porting Notes

* Custom types (e.g. typedefs) in the C++ version will be turned into classes or enums.
* Pointers passed as method arguments will have a mutable object equivalent.

###### BWEM::Map
* This class is a singleton and an interface/abstract in the C++ version. The port will have a `Map` class as instantiable and extendable. `MapImpl` will extend `Map`.

###### Classes using interface `IWrappedInteger`
* This interface is mainly used when creating a class to replace a C++ typedef. E.g. [C++: `typedef int16_t altitude_t;`; Java: `Altitude`], [C++: `typedef int16_t id;`, Java: `Area.Id`]

###### Timer.java
* Included for completeness.

###### utils.h:238:UserData
* Included for completeness.
* Only provides integer manipulation in the port. The C++ version allows for void pointers.

### Modifications

#### Types

| Location | Scope | Original C++ | Java Port | C++ Type | Java Type | Description |
|-|-|-|-|-|-|-|
| `area.h:54` | public | `Area::id` | `Area.Id` | `int16_t` | `int` | This appears to be used everwhere in the code in place of `int16_t` instead of a dedicated `Area::id` type. |
| `area.h:56` | public | `Area::groupId` | `Area.GroupId` | `int16_t` | `int` | - |
| `defs.h:54` | global | `altitude_t` | `Altitude` | `int16_t` | `int` | altitude type in pixels |
| `mapImpl.cpp:293` | local method | `altitude_scale` | `MiniTile.SIZE_IN_PIXELS` | `altitude_t` | `int` | "8 provides a pixel definition for altitude_t, since altitudes are computed from miniTiles which are 8x8 pixels" |
| `cp.h:143` | public | `ChokePoint::index` | `Index` | `int` | `int` | - |

### Class and Method Dependencies

###### graph.cpp:392:const CPPath & Graph::GetPath
* `graph.h:136:Graph::GetNearestArea`
  * `graph.cpp:161:Graph::GetArea`
    * `graph.h:64:Graph::GetMap`
  * `map.h:252:Map::BreadthFirstSearch`

###### CPPath
* `typedef std::vector<const ChokePoint *> Path;`
* `typedef ChokePoint::Path CPPath;`
