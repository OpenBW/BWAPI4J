cmake_minimum_required(VERSION 3.1)
project(BWAPI4JBridge)

set(CMAKE_CXX_STANDARD 14)
set(CMAKE_CXX_STANDARD_REQUIRED ON)

set(CMAKE_ARCHIVE_OUTPUT_DIRECTORY "${CMAKE_BINARY_DIR}/../Release")
set(CMAKE_LIBRARY_OUTPUT_DIRECTORY "${CMAKE_BINARY_DIR}/../Release")
set(CMAKE_RUNTIME_OUTPUT_DIRECTORY "${CMAKE_BINARY_DIR}/bin")

if (MSVC)
    add_definitions(-D_CRT_SECURE_NO_WARNINGS)
endif()

function(if_unset_then_set var val)
    if (NOT ${var})
        set(${var} ${val} PARENT_SCOPE)
    endif()
endfunction()



################################################################################
# BWAPI4JBridge
################################################################################

if_unset_then_set(BWAPI4JBRIDGE_SRC_DIR src)
message("BWAPI4JBridge source dir: ${BWAPI4JBRIDGE_SRC_DIR}")
set(BWAPI4JBRIDGE_SRCS 
    ${BWAPI4JBRIDGE_SRC_DIR}/Bridge.cpp
    ${BWAPI4JBRIDGE_SRC_DIR}/BridgeEnum.cpp
    ${BWAPI4JBRIDGE_SRC_DIR}/BridgeMap.cpp
    ${BWAPI4JBRIDGE_SRC_DIR}/BWTA.cpp
    ${BWAPI4JBRIDGE_SRC_DIR}/DamageEvaluator.cpp
    ${BWAPI4JBRIDGE_SRC_DIR}/InteractionHandler.cpp
    ${BWAPI4JBRIDGE_SRC_DIR}/MapDrawer.cpp
    ${BWAPI4JBRIDGE_SRC_DIR}/OpenBridgeModule.cpp
    ${BWAPI4JBRIDGE_SRC_DIR}/Unit.cpp
)

################################################################################



################################################################################
# JNI
################################################################################

if_unset_then_set(JNI_INC_DIR ../JNI/include)
message("JNI include dir: ${JNI_INC_DIR}")

if_unset_then_set(BWAPI4J_JNI_INC_DIR ../../BWAPI4J/src/native/include)
message("BWAPI4J JNI include dir: ${BWAPI4J_JNI_INC_DIR}")

include_directories(${JNI_INC_DIR} ${BWAPI4J_JNI_INC_DIR})
if (WIN32)
    message("Including WIN32 JNI headers.")
    include_directories(${JNI_INC_DIR}/win32)
    add_library(OpenBWAPI4JBridge STATIC ${BWAPI4JBRIDGE_SRCS})
else()
    message("Including Linux JNI headers.")
    include_directories(${JNI_INC_DIR}/linux)
    add_library(OpenBWAPI4JBridge SHARED ${BWAPI4JBRIDGE_SRCS})
endif()

################################################################################



################################################################################
# OpenBW
################################################################################

if (OPENBW)
    message("C++ preprocessor macro enabled: OPENBW")
    add_definitions(-DOPENBW)

    if_unset_then_set(OPENBW_DIR ../../../../openbw/openbw)

    if_unset_then_set(OPENBW_BWAPI_DIR ../../../../openbw/bwapi)

    include_directories(
        ${OPENBW_DIR}
        ${OPENBW_BWAPI_DIR}/bwapi/OpenBWData
        ${OPENBW_BWAPI_DIR}/bwapi/include
        ${OPENBW_BWAPI_DIR}/bwapi/BWAPI/Source
        ${OPENBW_BWAPI_DIR}/bwapi/Util/Source
        ${OPENBW_BWAPI_DIR}/bwapi/BWAPICore
    )
endif()

################################################################################



################################################################################
# BWTA
################################################################################

if_unset_then_set(BWTA_INC_DIR ../BWTA/include)
include_directories(${BWTA_INC_DIR})

################################################################################
