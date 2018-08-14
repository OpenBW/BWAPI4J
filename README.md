### Build Status

| | Windows | Linux | Download |
|-|:-:|:-:|:-:|
| **master** | [![AppVeyor master](https://ci.appveyor.com/api/projects/status/uby2l601herytp47/branch/master?svg=true)](https://ci.appveyor.com/project/adakitesystems/bwapi4j/branch/master) | [![Travis CI master](https://travis-ci.com/OpenBW/BWAPI4J.svg?branch=master)](https://travis-ci.com/OpenBW/BWAPI4J) | [![download](https://img.shields.io/badge/master-v0.8.1b-blue.svg)](https://github.com/OpenBW/BWAPI4J/releases/tag/v0.8.1b) |
| **develop** | [![AppVeyor develop](https://ci.appveyor.com/api/projects/status/uby2l601herytp47/branch/develop?svg=true)](https://ci.appveyor.com/project/adakitesystems/bwapi4j/branch/develop) | [![Travis CI develop](https://travis-ci.com/OpenBW/BWAPI4J.svg?branch=develop)](https://travis-ci.com/OpenBW/BWAPI4J) | [![download](https://img.shields.io/badge/develop-v0.8.2b-orange.svg)](https://github.com/OpenBW/BWAPI4J/releases/tag/v0.8.1b) |

SHA-256 checksums for official version 0.8.1b:

```
ea8d6a7c07fc4b3d77feebf983bd5aee446392bfaab6ac4485c8059a39bd9391  BWAPI4J-0.8.1b.jar
af64af4659f1be307c2b697a3f975d4b5143bd5d207bb456d6b6c222f81da1d5  BWAPI4J-0.8.1b-javadoc.jar
6ed30ab5e8449100e382a24814bbf9e0d9878ebb30276d3cab0fffd7c379b7bc  BWAPI4J-0.8.1b-sources.jar
```

# BWAPI4J

This is a Java wrapper for [BWAPI 4.2.0](https://github.com/bwapi/bwapi/).
It is intended to replace older projects such as BWMirror and JNIBWAPI.

BWAPI4J is compatible with the official BW 1.16.1 on Windows as well as OpenBW on Windows or Linux.

This project consists of two main parts:

1. The Java project **BWAPI4J** containing all Java classes used by your bot.
2. The C++ project **BWAPI4JBridge** containing C++ code required for BWAPI4J to interact with BW.

Notes:
* This is a development version and breaking changes can occur at any time.
* To develop a BWAPI4J bot, only the Java project is required.
* If you want to compile the bridge, you will need a C++ development environment.

### Why BWAPI4J?

BWAPI4J was inspired by both bwmirror and jnibwapi. It strives to combine the advantages of both while eliminating some weaknesses.

The main advantages of BWAPI4J are:
* speed: BWAPI4J minimizes the amount of JNI calls and caches data likely to be queried every frame on the Java-side using a single large array copy
* ease of debugging: automatically generated code is hard to debug, especially if c++ pointers are cached Java-side and there is no way to look up the values from a Java debugger. BWAPI4J is coded manually and caches relevant game data Java-side, rather than just pointers.
* feature-completeness: other bridges either do not contain the full set of features or are prone to nasty bugs that lead to JVM crashes, in particular in conjunction with libraries such as BWTA. BWAPI4J aims to provide at least a rudimentary exception handling even for the c++ routines, such that bugs can be investigated

the fact that BWAPI4J is typed may or may not be seen as an advantage. Future version will provide a typed and a non-typed interface.

Last but not least: BWAPI4J is **fully compatible with OpenBW** and therefore can be run on Linux (natively, no Wine required) at much greater speeds than the original BW.

---

## BWAPI4J (Java)

### Required JDK Version

JDK 1.8 or later is required. 32-bit is required when using original BW. 64-bit is required when using OpenBW.

### Cloning the repository

````
git clone https://github.com/OpenBW/BWAPI4J.git
cd BWAPI4J
git submodule update --init --recursive
````

### Compiling the BWAPI4J library JAR using Gradle

*Note: This step will not build the bridge. See the BWAPI4JBridge section for more details.*

#### Gradle Setup

Copy `gradle.properties.sample` to `gradle.properties` and add the path to your JDK using the key `org.gradle.java.home=`

#### Build Steps

Navigate to the `BWAPI4J/BWAPI4J/` directory and run the following command:

````
./gradlew clean build sourcesJar javadocJar shadowJar
````

The resulting JARs will be found in the `BWAPI4J/BWAPI4J/build/libs/` directory which can be imported into your IDE to develop a BWAPI4J bot.

### Adding the JAR to an existing Maven project

This step is optional. You may manually import the JARs from the previous step into any IDE.

*Note: If a version number is appended to the JAR filename, you must also add it to the JAR filenames in following example command.*

````
mvn install:install-file \
    -Dfile=build/libs/BWAPI4J.jar \
    -Djavadoc=build/libs/BWAPI4J-javadoc.jar \
    -Dsources=build/libs/BWAPI4J-sources.jar \
    -DgroupId=org.openbw \
    -DartifactId=bwapi4j \
    -Dversion=0.1-SNAPSHOT  \
    -Dpackaging=jar
````

### Testing your development environment

#### The Smoke Test

The smoke test will test your current setup and display errors or exceptions if something went wrong. If the smoke test fails, usually you will have a difficult time developing and deploying your bot.

##### Running the Smoke Test with Eclipse

Right-click the project and choose `Run as... -> JUnit Test`.

##### Running the Smoke Test with another IDE

Navigate to `BWAPI4J/BWAPI4J/src/test/java/org/openbw/bwapi4j/MainTest.java` and run the `smokeTest` manually.

---

## JVM options

####  bwapi4j.extractDependencies

By default, BWAPI4J will extract the bridge dependencies to the current working directory. This can be overridden by specifying the value `false` to the JVM. E.g.:

````
java -Dbwapi4j.extractDependencies=false -jar MyBot.jar
````

#### bwapi4j.bridgeType

By default, BWAPI4J will use the Vanilla BW bridge on Windows and the OpenBWAPI4JBridge on Linux. This can be overridden by specifying `vanilla` or `openbw` to the JVM. E.g.:

````
java -Dbwapi4j.bridgeType=vanilla -jar MyBot.jar
````
or
````
java -Dbwapi4j.bridgeType=openbw -jar MyBot.jar
````
---

## Dependencies for running a BWAPI4J bot

* The bridge should be accessible via a library path known by the system.
  * On Windows, the path to the bridge can be added to the `PATH` environment variable.
  * On Linux, the path to the bridge can added to the `LD_LIBRARY_PATH` environment variable.
* If the bridge is not found in a known library path, the bot will search the current working directory.

### Original BW on Windows

* BW 1.16.1
* BWAPI 4.2.0
* VC++ Redist 2017
* Windows 7 or higher

### OpenBW

- A compiled and working version of OpenBW.
- The original BW does not need to be installed. Only the following three BW files are required ([available for free](https://www.battle.net/download/getInstallerForGame?os=win&locale=enUS&version=LIVE&gameProgram=STARCRAFT)). Copy them to your root project directory: `BrooDat.mpq`, `StarDat.mpq`, `Patch_rt.mpq`
- At least one Melee map. For example, **Fighting Spirit** from the [SSCAIT map pack](https://sscaitournament.com/files/sscai_map_pack.zip).
- Copy `bwapi-data/bwapi.ini.sample` to `bwapi-data/bwapi.ini` and configure as usual. See [https://github.com/bwapi/bwapi/wiki/Configuration](https://github.com/bwapi/bwapi/wiki/Configuration)
- Set the `ai = ` variable in `bwapi.ini` to point to the BWAPI4J bridge module. E.g.
````
ai = ..\BWAPI4JBridge\Release\OpenBWAPI4JBridge.dll
````
or
````
ai = ../BWAPI4JBridge/Release/libOpenBWAPI4JBridge.so
````

#### OpenBW for Linux

* In addition to `libOpenBWAPI4JBridge.so`, add the paths of the following files to the `LD_LIBRARY_PATH` environment variable or copy them to a system library directory such as `/usr/lib/` or `/usr/local/lib/`
````
libOpenBWData.so
libBWAPILIB.so
libBWAPI.so
````

---

## BWAPI4JBridge (C++)

The bridge is separated into three **types** that share most of the same code:

| Type | BW Version | Platform | Binary Name
|-|-|-|-|
| vanilla | BW 1.16.1 | Windows | BWAPI4JBridge.dll |
| openbw | OpenBW | Windows | OpenBWAPI4JBridge.dll |
| openbw | OpenBW | Linux | libOpenBWAPI4JBridge.so |

*Note: Binaries have been removed from this repository and will be shipped with official releases. If one of the bridges is not working for you, please try compiling it yourself with the steps below or submit a GitHub Issue.*

### Prerequisites

- CMake 3.1 or higher

##### Windows-specific

- Visual Studio 2017

##### Linux-specific

- g++ 5.x or higher (needs to support most C++14 features)
- A working version of OpenBW (will be compiled automatically with an OpenBW bridge)

### Gradle Tasks for building the bridges

The following table outlines the Gradle tasks used for building a specific bridge.

| Type | Platform | Gradle Task
|-|-|-|
| vanilla | Windows | buildVanillaBridgeForWindows |
| openbw | Windows | buildOpenBWBridgeForWindows |
| openbw | Linux | buildOpenBWBridgeForLinux |

Examples:
```
./gradlew buildVanillaBridgeForWindows
```
```
./gradlew buildOpenBWBridgeForWindows
```
```
./gradlew buildOpenBWBridgeForLinux
```

### Including the bridge with the BWAPI4J library JAR

The bridge needs to be built before it is packaged via `shadowJar`. Use one of the appropriate Gradle tasks to build the bridge, then use the following command:
```
./gradlew shadowJar
```
