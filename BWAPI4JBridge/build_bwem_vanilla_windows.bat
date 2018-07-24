set DEV_CMD_BAT="..\\..\\..\\VsDevCmd.bat"
set TARGET_BUILD_DIR="externals\\bwem\\build"
set TARGET_BUILD_SOLUTION=BWEM.sln

IF NOT EXIST %TARGET_BUILD_DIR% (
mkdir %TARGET_BUILD_DIR%
)

cd %TARGET_BUILD_DIR%
cmake ..

IF EXIST %TARGET_BUILD_SOLUTION% (
%DEV_CMD_BAT%
MSBuild.exe %TARGET_BUILD_SOLUTION% /p:Configuration=Release
)
