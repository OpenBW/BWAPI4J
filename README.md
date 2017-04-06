# BWAPI4J
## BWAPI wrapper for Java

The repository consists of two projects:

- A Java (eclipse) project
- A c++ (Visual Studio 2017) project

The two projects interact in the following way:

- The Java project generates c++ header files to "BWAPI4J/src/native/include" to be imported by the c++ project
- The c++ projecte generates a DLL to "BWAPI4JBridge/Release" to be loaded in the Java project

# Instructions

Follow these instructions to build BWAPI4J yourself.

## Repository

Prerequisites: 
 - git installed.
 
Instructions:
 - Navigate to your local git directory
 - Check out the complete project using "git clone https://github.com/OpenBW/BWAPI4J.git" from your command line.

### BWAPI4J

Prerequisites:
 - Java SDK 1.8 installed
 - optionally: eclipse installed
 
Instructions
 - for eclipse: import the BWAPI4J project as an eclipse project
 - build the final distribution using the gradle task "distZip"
 - generate the c++ header files required by the BWAPI4JBridge project using the gradle task "generateJniHeaders"

### BWAPI4JBridge

Prerequisites:
 - Visual Studio 2017 installed
 
Instructions
 - Open the complete BWAPI4JBridge solution, including the BWAPI4JBridge project inside the solution.
 - Build the DLL to be used in the Java project via "Build - Rebuild BWAPI4JBridge" with the targets "Release" and "x86"
