set DEV_CMD_BAT="..\\VsDevCmd.bat"
set TARGET_BUILD_DIR=build_openbw_windows
set TARGET_BUILD_SOLUTION=OpenBWAPI4JBridge.sln

IF NOT EXIST %TARGET_BUILD_DIR% (
mkdir %TARGET_BUILD_DIR%
)

cd %TARGET_BUILD_DIR%
cmake .. -DCMAKE_BUILD_TYPE=Release -DOPENBW=1 -DOPENBW_DIR=../externals/openbw/openbw -DOPENBW_ENABLE_UI=1

IF EXIST %TARGET_BUILD_SOLUTION% (
%DEV_CMD_BAT%
MSBuild.exe %TARGET_BUILD_SOLUTION% /p:Configuration=Release
)
