set DEV_CMD_BAT="..\\..\\..\\VsDevCmd.bat"
set TARGET_BUILD_DIRECTORY="externals\\bwapi\\bwapi"
set TARGET_SOLUTION="BWAPI.sln"

cd %TARGET_BUILD_DIRECTORY%

IF EXIST %TARGET_SOLUTION% (
%DEV_CMD_BAT%
msbuild %TARGET_SOLUTION% /t:BWAPI;BWAPIClient /p:Configuration=Release_Pipeline
)
