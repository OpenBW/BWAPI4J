# Project: BWAPI4J-BWEM

### Abstract

Port the BWEM C++ library to Java for compatibility with BWAPI4J. Main priority is the following methods and their dependencies:
* `map.h:204:virtual const CPPath &	GetPath(const BWAPI::Position & a, const BWAPI::Position & b, int * pLength = nullptr) const = 0;`
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
* `BWEM::Map::Initialize()` code will be put into ~~the constructor `public Map(org.openbw.bwapi4j.BW)`~~`MapInitialize()`.
* ~~Parameterless constructor for `bwem.Map` has been publically disabled since the original `BWAPI::Broodwar` is required and a singleton~~. Instantiation, initialization, and access have been implemented to match `BWEM::Map::Instance()` and their counterparts.

### Modifications

#### Types

| Location | Access | Original | Current | C++ | Java | Description |
|-|-|-|-|-|-|-|
| `area.h:54` | public | `Area::id` | N/A | `int16_t` | `int` | this appears to be used everwhere in the code in place of `int16_t` instead of a dedicated `Area::id` type |
| `area.h:56` | public | `Area::groupId` | N/A | `int16_t` | `int` |  |
| `defs.h:54` | global | `altitude_t` | `Altitude` | `int16_t` | `int` | altitude type in pixels |

### Code Trace

###### `mapImpl.cpp:68`, current focus: `mapImpl.cpp:123:void MapImpl::LoadData()`

```
void MapImpl::Initialize() {
  ... // member initialization
  LoadData();
  ... // more initialization methods
}
```

### Class and Method Dependencies

`graph.cpp:392:const CPPath & Graph::GetPath`
* ???

`Area`
* Graph

`Graph`
* Area
* Chokepoint
* CPPath

`CPPath`
* ???
