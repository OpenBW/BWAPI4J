@echo off

echo Generating build scripts for BWAPI4JBridge...
echo.

IF EXIST build (
echo Deleting build/ directory...
rmdir /s /q build
)
mkdir build
cd build
cmake ..

echo.
echo Done.
echo.

IF EXIST BWAPI4JBridge.sln (
echo A Visual Studio solution has been generated.
echo Open the solution and build the bridge with the "Release / Win32" configuration.
)

@pause

@echo on
