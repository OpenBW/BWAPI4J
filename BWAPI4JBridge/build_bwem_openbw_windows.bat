set DEV_CMD_BAT="..\\..\\..\\VsDevCmd.bat"
set TARGET_BUILD_DIR="externals\\bwem\\build_openbw_windows"
set TARGET_BUILD_SOLUTION=OpenBWEM.sln

IF NOT EXIST %TARGET_BUILD_DIR% (
mkdir %TARGET_BUILD_DIR%
)

cd %TARGET_BUILD_DIR%
cmake .. -DOPENBW=1

IF EXIST %TARGET_BUILD_SOLUTION% (
%DEV_CMD_BAT%
MSBuild.exe %TARGET_BUILD_SOLUTION% /p:Configuration=Release
)
