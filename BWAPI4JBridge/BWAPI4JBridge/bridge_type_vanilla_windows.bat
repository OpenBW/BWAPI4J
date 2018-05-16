@echo off

echo Generating build scripts for BWAPI4JBridge...
echo.

IF EXIST build_vanilla_windows (
echo Deleting build_vanilla_windows/ directory...
rmdir /s /q build_vanilla_windows
)
mkdir build_vanilla_windows
cd build_vanilla_windows
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
