# BWAPI4J

This is a Java wrapper for [BWAPI 4.2.0](https://github.com/bwapi/bwapi/).
It is intended to replace older projects such as BWMirror and JNIBWAPI.

BWAPI4J is compatible with both the original BW on Windows as well as OpenBW on Windows or Linux.

This project consists of two main parts:

1. The Java project **BWAPI4J** containing all Java classes used by your bot.
2. The C++ project **BWAPI4JBridge** containing C++ code required for your compiled bot to interact with original BW or OpenBW.
  * Visual Studio 2017 project files to build the bridge for original BW on Windows.
  * CMake to generate the scripts to build the bridge for OpenBW on Windows or Linux.

*Note: This is a development version, and breaking changes can occur at any time.*

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

### Importing and building BWAPI4J using Eclipse and Gradle

Clone the repository:
````
git clone https://github.com/OpenBW/BWAPI4J.git
````

1. Import the project as follows:
  * In Eclipse, choose: `File -> Import... -> Git/Projects from Git -> Existing Local Repository`.
  * If BWAPI4J does not appear, choose `add...` and add `<your path>/git/bwapi4j` where <your path> is the path to your git directory.
  * Select `Import existing Eclipse projects` and click `Next`.
  * Select `BWAPI4J` and click `Finish`.
2. Copy `gradle.properties.sample` to `gradle.properties` and add the path to your JDK at the key `org.gradle.java.home=`
3. Execute the gradle target `distribution / assembleDist`.
  * `Windows -> Show View -> Other... -> Gradle/Gradle Tasks`.
  * In the view, click the `Refresh Tasks for All Projects` icon.
  * The BWAPI4J project should appear. Navigate to `distribution` and double-click on `assembleDist`.

### Building your own JAR using Gradle and adding it to an existing Maven project

````
git clone https://github.com/OpenBW/BWAPI4J.git
cd BWAPI4J/BWAPI4J/
./gradlew shadowJar sourcesJar javadocJar
mvn install:install-file \
    -Dfile=build/libs/BWAPI4J.jar \
    -Djavadoc=build/libs/BWAPI4J-javadoc.jar \
    -Dsources=build/libs/BWAPI4J-sources.jar \
    -DgroupId=org.openbw \
    -DartifactId=bwapi4j \
    -Dversion=0.1-SNAPSHOT  \
    -Dpackaging=jar
````

### Running the smoke test using Eclipse

Right-click the project and choose `Run as... -> JUnit Test`.

The smoke test starts a game and sends the first SCV to the first mineral patch it finds.

---

### BWAPI4JBridge (C++)

The bridge is broken into two parts:
- Original BW for Windows
  * Uses Visual Studio 2017 project files.
- OpenBW for Windows or Linux.
  * Uses CMake to generate Visual Studio project files on Windows or Makefiles on Linux.

*Note: All bridges are already built and located in the `BWAPI4JBridge/BWAPI4JBridge/Release/` directory. If one of the bridges is not working for you, please try compiling it yourself or submit a GitHub Issue.*

### Original BW bridge for Windows

#### Prerequisites
- Visual Studio 2017

#### Build Steps:
- Open the complete project solution file `BWAPI4JBridge/BWAPI4JBridge/VisualStudio/BWAPI4JBridge.sln`.
- Set the configuration to **Release / x86**.
- Build the **BWAPI4JBridge.dll** by right-clicking on the **BWAPI4JBridge** project in the project explorer and click **Rebuild**.

### OpenBW bridge for Windows or Linux

#### Prerequisites

- CMake 3.1 or higher
- g++ 5.x or higher (needs to support most C++14 features)

#### Generating the build scripts

From the root **BWAPI4J/** directory, copy and paste the following into a terminal:
````
cd BWAPI4JBridge/BWAPI4JBridge/ && \
    mkdir build/ && \
    cd build/ && \
    cmake .. -DOPENBW=1 -DOPENBW_ENABLE_UI=1
````
The above commands will generate the following Visual Studio project files on Windows:
````
BWAPI4JBridge/BWAPI4JBridge/build/ALL_BUILD.vcxproj
BWAPI4JBridge/BWAPI4JBridge/build/ALL_BUILD.vcxproj.filters
BWAPI4JBridge/BWAPI4JBridge/build/BWAPI4JBridge.sln
BWAPI4JBridge/BWAPI4JBridge/build/cmake_install.cmake
BWAPI4JBridge/BWAPI4JBridge/build/CMakeCache.txt
BWAPI4JBridge/BWAPI4JBridge/build/OpenBWAPI4JBridge.vcxproj
BWAPI4JBridge/BWAPI4JBridge/build/OpenBWAPI4JBridge.vcxproj.filters
BWAPI4JBridge/BWAPI4JBridge/build/ZERO_CHECK.vcxproj
BWAPI4JBridge/BWAPI4JBridge/build/ZERO_CHECK.vcxproj.filters
````

#### Building the bridge

* If using Windows:
  * Open the newly generated Visual Studio solution file.
  * Set the configuration to **Release / Win32**.
  * Right-click on **OpenBWAPI4JBridge** and click **Rebuild**.
* If using Linux:
  * Run the `make` command.

---

### Dependencies for running your bot

#### Original BW on Windows

* BW 1.16.1
* VC++ Redist 2017
* Windows 7 or higher
* Add the path of the following file to your PATH environment variable:
````
BWAPI4JBridge.dll
````
* You might also need to add the following BWTA2 dependencies:
````
libgmp-10.dll
libmpfr-4.dll
````

#### OpenBW

- BW does not need to be installed. Only the following original BW files are required ([available for free](https://www.battle.net/download/getInstallerForGame?os=win&locale=enUS&version=LIVE&gameProgram=STARCRAFT)). Copy them to your root project directory:
````
BrooDat.mpq
StarDat.mpq
Patch_rt.mpq
````
- At least one Melee map. For example, **Fighting Spirit** from the [SSCAIT map pack](https://sscaitournament.com/files/sscai_map_pack.zip).
- Copy `bwapi-data/bwapi.ini.sample` to `bwapi-data/bwapi.ini` and configure as usual. See [https://github.com/bwapi/bwapi/wiki/Configuration](https://github.com/bwapi/bwapi/wiki/Configuration)

##### OpenBW for Windows

* Add the path of the following file to your PATH environment variable:
````
OpenBWAPI4JBridge.dll
````

##### OpenBW for Linux

* Add the paths of the following files to your `LD_LIBRARY_PATH` environment variable or copy the files to your `/usr/lib/` or `/usr/local/lib/` directory.
````
libOpenBWAPI4JBridge.so
libOpenBWData.so
libBWAPILIB.so
libBWAPI.so
libBWTA2.so
````
*Note: For compiling OpenBW libraries, see the [OpenBW / BWAPI](https://github.com/openbw/bwapi) project. For compiling the BWTA2 library, see Adakite's [BWTA2 fork](https://github.com/adakitesystems/bwta2).*

<!--
#### Bridge: OpenBW on Windows and/or Linux

##### Prerequisites:

- Eclipse is optional. However, it is recommended, since it allows to develop both the Java project and the C++ project within the same IDE. Ensure Eclipse CDT installed.
- Visual Studio 2017 (on Windows).
- g++ compiler supporting C++14 (on Linux).
- CMake -->

<!-- You can choose to build the bridge using one of the two following ways: -->

<!-- #### 1. Building with Eclipse:
Import and build the project as follows:
  * In eclipse, choose: `File -> Import... -> Git/Projects from Git -> Existing Local Repository`.
  * If BWAPI4J does not appear, choose `add...` and add `<your path>/git/bwapi4j` where <your path> is the path to your git directory.
  * Select `Import existing Eclipse projects` and click `Next`.
  * Select `BWAPI4JBridge` and click `Finish`.
  * Add `<path to JDK>/include` and `<path to JDK>/include/linux` to the include section under `Project -> Properties -> C/C++ Build -> Settings -> Tool Settings -> GCC C++ Compiler -> Includes`
  * Click the `Build release` icon in the Eclipse menubar. -->
