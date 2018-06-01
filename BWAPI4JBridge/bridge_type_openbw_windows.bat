@echo off

echo Generating build scripts for OpenBWAPI4JBridge...
echo.

IF EXIST build_openbw_windows (
echo Deleting build_openbw_windows/ directory...
rmdir /s /q build_openbw_windows
)
mkdir build_openbw_windows
cd build_openbw_windows
cmake .. -DOPENBW=1 -DOPENBW_ENABLE_UI=1

echo.
echo Done.
echo.

IF EXIST OpenBWAPI4JBridge.sln (
echo A Visual Studio solution has been generated.
echo Open the solution and build the bridge with the "Release / Win32" configuration.
)

@pause

@echo on
