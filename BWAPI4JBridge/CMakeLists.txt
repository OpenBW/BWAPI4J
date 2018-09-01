cmake_minimum_required(VERSION 3.1)

if (OPENBW)
	set(PROJECT_NAME OpenBWAPI4JBridge)
else()
	set(PROJECT_NAME BWAPI4JBridge)
endif()

project(${PROJECT_NAME})

include(${CMAKE_CURRENT_LIST_DIR}/flags.cmake)

if (BWAPI4JBRIDGE_ENABLE_LOGGER)
	add_definitions(-DBWAPI4JBRIDGE_ENABLE_LOGGER)
endif()

include(${CMAKE_CURRENT_LIST_DIR}/func.cmake)
include(${CMAKE_CURRENT_LIST_DIR}/externals.cmake)
include(${CMAKE_CURRENT_LIST_DIR}/bwapi4j.cmake)
include(${CMAKE_CURRENT_LIST_DIR}/bwapi4jbridge.cmake)
include(${CMAKE_CURRENT_LIST_DIR}/spdlog_inc.cmake)
include(${CMAKE_CURRENT_LIST_DIR}/jni_inc.cmake)
include(${CMAKE_CURRENT_LIST_DIR}/ap_inc.cmake)
include(${CMAKE_CURRENT_LIST_DIR}/bwapi_inc.cmake)
include(${CMAKE_CURRENT_LIST_DIR}/bwapi_lib.cmake)
include(${CMAKE_CURRENT_LIST_DIR}/externals/bwem/bwem_inc.cmake)
include(${CMAKE_CURRENT_LIST_DIR}/externals/bwem/bwem_lib.cmake)
