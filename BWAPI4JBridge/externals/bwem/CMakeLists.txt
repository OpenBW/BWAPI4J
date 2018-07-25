cmake_minimum_required(VERSION 3.1)

if (OPENBW)
	set(PROJECT_NAME OpenBWEM)
else()
	set(PROJECT_NAME BWEM)
endif()

project(${PROJECT_NAME})

include(${CMAKE_CURRENT_LIST_DIR}/../../flags.cmake)
include(${CMAKE_CURRENT_LIST_DIR}/bwem.cmake)
