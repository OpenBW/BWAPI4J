![Build Status](https://travis-ci.com/OpenBW/BWAPI4J.svg?branch=master)

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

---

### BWAPI4J (Java)

#### Recommended Software

* Eclipse
* Gradle
* Git

Both Eclipse and Gradle are optional in theory. However, it is greatly recommended to use Gradle to build the project since it automates the build process and reduces it to executing a single Gradle target.
Eclipse is recommended because it works seamlessly with Gradle (using the Gradle Wrapper) and Git. Using Eclipse, both the Java project and the C++ project can be developed in the same IDE.

#### Required JDK Version

JDK 1.8 or later is required. 32-bit is required when using original BW. 64-bit is required when using OpenBW.

### Cloning the repository

````
git clone https://github.com/OpenBW/BWAPI4J.git
````

### Setting up Gradle

Copy `gradle.properties.sample` to `gradle.properties` and add the path to your JDK using the key `org.gradle.java.home=`

### Compiling a JAR using Eclipse and Gradle

1. Import the project as follows:
  * In Eclipse, choose: `File -> Import... -> Git/Projects from Git -> Existing Local Repository`.
  * If BWAPI4J does not appear, choose `add...` and add `<your path>/git/bwapi4j` where <your path> is the path to your git directory.
  * Select `Import existing Eclipse projects` and click `Next`.
  * Select `BWAPI4J` and click `Finish`.
2. Execute the gradle target `distribution / assembleDist`.
  * `Windows -> Show View -> Other... -> Gradle/Gradle Tasks`.
  * In the view, click the `Refresh Tasks for All Projects` icon.
  * The BWAPI4J project should appear. Navigate to `distribution` and double-click on `assembleDist`.

### Compiling a JAR using Gradle only

Navigate to the `BWAPI4J/BWAPI4J/` directory and run the following command:

````
./gradlew clean build sourcesJar javadocJar shadowJar
````

The resulting JARs will be found in the `BWAPI4J/BWAPI4J/build/libs/` directory.

### Adding the JAR to an existing Maven project

This step is optional. You may manually import the JAR from the previous step into any IDE.

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

### The Smoke Test

The smoke test will test your setup and display errors or exceptions if something went wrong.

#### Running the Smoke Test with Eclipse

Right-click the project and choose `Run as... -> JUnit Test`.

#### Running the Smoke Test with another IDE

Navigate to `BWAPI4J/BWAPI4J/src/test/java/org/openbw/bwapi4j/MainTest.java` and run the `smokeTest` manually.

---

### BWAPI4JBridge (C++)

The bridge is separated into three **types** that share most of the same code:

| Type Name | BW Version | Platform | Binary Name |
|-|-|-|-|
| vanilla | BW 1.16.1 | Windows | BWAPI4JBridge.dll |
| openbw | OpenBW | Windows | OpenBWAPI4JBridge.dll |
| openbw | OpenBW | Linux | libOpenBWAPI4JBridge.so |

*Note: All bridges are already built and located in the `BWAPI4JBridge/BWAPI4JBridge/Release/` directory. If one of the bridges is not working for you, please try compiling it yourself with the steps below or submit a GitHub Issue.*

### Bridge Type: Vanilla BW for Windows

This bridge is only required when running your bot with Vanilla BW on Windows.

#### Prerequisites
- Visual Studio 2017
- CMake 3.1 or higher

#### Generating the Visual Studio project files

- Run the batch file: `bridge_type_vanilla_windows.bat`

#### Build Steps

- Open `build_vanilla_windows/BWAPI4JBridge.sln`
- Set the configuration to **Release / Win32**.
- Build by right-clicking on the **BWAPI4JBridge** project in the project explorer and select **Rebuild**.

### Bridge Type: OpenBW for Windows

This bridge is only required when running your bot with OpenBW on Windows.

#### Prerequisites

- A compiled and working version of OpenBW (see the [OpenBW / BWAPI](https://github.com/openbw/bwapi) project)
- Visual Studio 2017
- CMake 3.1 or higher

#### Generating the Visual Studio project files

- Run the batch file: `bridge_type_openbw_windows.bat`

#### Build Steps

- Open `build_openbw_windows/OpenBWAPI4JBridge.sln`
- Set the configuration to **Release / Win32**.
- Build by right-clicking on the **OpenBWAPI4JBridge** project in the project explorer and select **Rebuild**.

### Bridge Type: OpenBW for Linux

This bridge is only required when running your bot with OpenBW on Linux.

#### Prerequisites

- A compiled and working version of OpenBW (see the [OpenBW / BWAPI](https://github.com/openbw/bwapi) project)
- A compiled BWTA2 library for OpenBW's BWAPI on Linux (see Adakite's [BWTA2 Linux fork](https://github.com/adakitesystems/bwta2))
- CMake 3.1 or higher
- g++ 5.x or higher (needs to support most C++14 features)

#### Generating the Makefiles and automatically building the bridge

- Run the bash script: `bridge_type_openbw_linux.sh`

---

### Dependencies for running a BWAPI4J bot

* The bridge should be accessible via a library path known by the system.
  * On Windows, the path to the bridge can be added to the `PATH` environment variable.
  * On Linux, the path to the bridge can added to the `LD_LIBRARY_PATH` environment variable.
* If the bridge is not found in a known library path, the bot will search the current working directory.

#### Original BW on Windows

* BW 1.16.1
* BWAPI 4.2.0
* VC++ Redist 2017
* Windows 7 or higher

#### OpenBW

- A compiled and working version of OpenBW.
- The original BW does not need to be installed. Only the following three BW files are required ([available for free](https://www.battle.net/download/getInstallerForGame?os=win&locale=enUS&version=LIVE&gameProgram=STARCRAFT)). Copy them to your root project directory: `BrooDat.mpq`, `StarDat.mpq`, `Patch_rt.mpq`
- At least one Melee map. For example, **Fighting Spirit** from the [SSCAIT map pack](https://sscaitournament.com/files/sscai_map_pack.zip).
- Copy `bwapi-data/bwapi.ini.sample` to `bwapi-data/bwapi.ini` and configure as usual. See [https://github.com/bwapi/bwapi/wiki/Configuration](https://github.com/bwapi/bwapi/wiki/Configuration)
- Set the `ai = ` variable in `bwapi.ini` to point to the BWAPI4J bridge module. E.g.
````
ai = ..\BWAPI4JBridge\BWAPI4JBridge\Release\OpenBWAPI4JBridge.dll
````
or
````
ai = ../BWAPI4JBridge/BWAPI4JBridge/Release/libOpenBWAPI4JBridge.so
````

##### OpenBW for Linux

* In addition to `libOpenBWAPI4JBridge.so`, add the paths of the following files to the `LD_LIBRARY_PATH` environment variable or copy them to a system library directory such as `/usr/lib/` or `/usr/local/lib/`
````
libOpenBWData.so
libBWAPILIB.so
libBWAPI.so
libBWTA2.so
````

### JVM options

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
