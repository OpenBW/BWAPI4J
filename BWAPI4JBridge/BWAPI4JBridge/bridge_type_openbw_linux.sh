#!/bin/bash

if [ -f ../../../BWTA2/Makefile ]; then
    cd ../../../BWTA2/ && \
	make clean && \
	make && \
	cp -f Release/*.so ../BWAPI4J/BWAPI4JBridge/BWAPI4JBridge/Release/ && \
	cd ../BWAPI4J/BWAPI4JBridge/BWAPI4JBridge/
fi

rm -rf build/ && \
	mkdir build/ && \
	cd build/ && \
	cmake .. -DOPENBW=1 -DOPENBW_ENABLE_UI=1 && \
	make
