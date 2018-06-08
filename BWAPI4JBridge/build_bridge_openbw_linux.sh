#!/bin/bash

#if [ -f ../../../BWTA2/Makefile ]; then
#    cd ../../../BWTA2/ && \
#	make && \
#	cp -f Release/*.so ../BWAPI4J/BWAPI4JBridge/BWAPI4JBridge/Release/ && \
#	cd ../BWAPI4J/BWAPI4JBridge/BWAPI4JBridge/
#fi

TARGET_BUILD_DIRECTORY="build_openbw_linux"

if [ ! -d $TARGET_BUILD_DIRECTORY ]; then
    mkdir $TARGET_BUILD_DIRECTORY
fi

cd $TARGET_BUILD_DIRECTORY && \
	cmake .. -DOPENBW=1 -DOPENBW_ENABLE_UI=1 && \
	make
