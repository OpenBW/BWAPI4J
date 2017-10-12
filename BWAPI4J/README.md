# BWAPI4J

This is a Java wrapper for BWAPI 4.2.0.
It is intended to replace the older projects bwmirror and JNIBWAPI.

BWAPI4J is compatible with both the original BW (on Windows) as well as OpenBW (on Linux)

The project consists of three parts:

 - The eclipse Java project "BWAPI4J" containing all Java classes
 - The VS2017 C++ project "BWAPI4JBridge" containing the C++ code required to interact with the original BW
 - The eclipse C++ project "OpenBWAPI4JBridge" containing the C++ code required to interact with OpenBW

This is a development version, and breaking changes can occur at any time.

### build & install

Clone the [BWAPI4J repository](https://github.com/OpenBW/BWAPI4J):

`git clone https://github.com/OpenBW/BWAPI4J`

#### "BWAPI4J" Java project

Prerequisites:

Both Eclipse and Gradle are optionial in theory. However, it is greatly recommended to use Gradle to build the project, since it automates the build process and reduces it to executing a single Gradle target.
Eclipse is recommended because it works seamlessly with Gradle (using the Gradle Wrapper) and Git and both the Java project and the C++ project can be developed in the same IDE.

 - JDK 1.8 (32bit for original BW and 64bit for OpenBW) or later installed
 - Eclipse installed (preferably [Eclipse CDT](https://www.eclipse.org/cdt/) with out-of-the-box support for C++)
 - Gradle, JUnit, and Git plugins for Eclipse
 - Alternatively to Eclipse / Eclipse Gradle plugin: [Gradle install](https://gradle.org/install/)
 - BrooDat.mpq, StarDat.mpq, BrooDat.mpq from the [original BW](https://www.battle.net/download/getInstallerForGame?os=win&locale=enUS&version=LIVE&gameProgram=STARCRAFT) (available for free)
 - At least one melee map. For example from the [SSCAIT map pack](https://sscaitournament.com/files/sscai_map_pack.zip)
 
 	Note: the original game does not have to be installed. Only the 3 files mentioned are required.
 
Build Steps:
1. Import the project as follows:

   * in eclipse, choose: `File -> Import... -> Git/Projects from Git -> Existing Local Repository`
   * if BWAPI4J does not appear, choose `"add..."` and add "<your path>/git/bwapi4j" where <your path> is the path to your git directory
   * Select `Import existing Eclipse projects` and click `Next`
   * Select `BWAPI4J` and click `Finish`
2. Copy `gradle.properties.sample` to `gradle.properties` and add the path to your JDK at the key `org.gradle.java.home=`
3. Execute the gradle target `distribution / assembleDist`

   * `Windows -> Show View -> Other... -> Gradle/Gradle Tasks`
   * In the view, click the `Refresh Tasks for All Projects` icon
   * The BWAPI4J project should appear. Navigate to `distribution` and double-click on `assembleDist`
   
Run the smoke test:

1. copy `BrooDat.mpq`, `StarDat.mpq`, and `BrooDat.mpq` to the root directory of the eclipse project
2. copy `bwapi-data/bwapi.ini.sample` to `bwapi-data/bwapi.ini`
3. Right-click the project and choose `Run as... -> JUnit Test`

The smoke test starts a game and sends the first SCV to the first mineral patch it finds.

#### "OpenBWAPI4JBridge" C++ project

Prerequisites:

Eclipse is optional. However, it is recommended, since it allows to develop both the Java project and the C++ project within the same IDE.

 - gcc compiler supporting c++14
 - Eclipse CDT installed
 
Build Steps:
1. Import the project as follows:

   * in eclipse, choose: `File -> Import... -> Git/Projects from Git -> Existing Local Repository`
   * if BWAPI4J does not appear, choose `"add..."` and add "<your path>/git/bwapi4j" where <your path> is the path to your git directory
   * Select `Import existing Eclipse projects` and click `Next`
   * Select `OpenBWAPI4JBridge` and click `Finish`
   * Add `<path to JDK>/include` and `<path to JDK>/include/linux` to the include section under `Project -> Properties -> C/C++ Build -> Settings -> Tool Settings -> GCC C++ Compiler -> Includes`
   * Click the `Build release` icon in the Eclipse menubar
   