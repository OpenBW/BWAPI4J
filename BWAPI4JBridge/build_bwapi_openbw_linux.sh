TARGET_BUILD_DIRECTORY="externals/openbw/bwapi/build"

if [ ! -d $TARGET_BUILD_DIRECTORY ]; then
    mkdir $TARGET_BUILD_DIRECTORY
fi

cd $TARGET_BUILD_DIRECTORY && \
    cmake .. -DCMAKE_BUILD_TYPE=Release -DOPENBW_DIR=../../openbw -DOPENBW_ENABLE_UI=1 && \
    make
