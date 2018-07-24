TARGET_BUILD_DIRECTORY="externals/bwem/build"

if [ ! -d $TARGET_BUILD_DIRECTORY ]; then
    mkdir $TARGET_BUILD_DIRECTORY
fi

cd $TARGET_BUILD_DIRECTORY && \
    cmake .. -DOPENBW=1 && \
    make
