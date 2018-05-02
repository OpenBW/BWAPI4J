cmake_minimum_required(VERSION 3.1)
project(OpenBWAPI4JBridge)

set(CMAKE_CXX_STANDARD 14)
set(CMAKE_CXX_STANDARD_REQUIRED ON)

set(OPENBW)

if (MSVC)
    add_definitions(-D_CRT_SECURE_NO_WARNINGS -DNOMINMAX -DWIN32 -D_WINDOWS -D_USRDLL)

    set(CMAKE_RUNTIME_OUTPUT_DIRECTORY "..")
    set(CMAKE_ARCHIVE_OUTPUT_DIRECTORY "..")
    set(CMAKE_LIBRARY_OUTPUT_DIRECTORY "..")
else()
    set(CMAKE_RUNTIME_OUTPUT_DIRECTORY "${CMAKE_BINARY_DIR}/bin")
    set(CMAKE_ARCHIVE_OUTPUT_DIRECTORY "${CMAKE_BINARY_DIR}/../Release")
    set(CMAKE_LIBRARY_OUTPUT_DIRECTORY "${CMAKE_BINARY_DIR}/../Release")
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
else()
    message("Including Linux JNI headers.")
    include_directories(${JNI_INC_DIR}/linux)
endif()

add_library(OpenBWAPI4JBridge SHARED ${BWAPI4JBRIDGE_SRCS})

################################################################################



################################################################################
# BWTA
################################################################################

if_unset_then_set(BWTA_INC_DIR ../BWTA/include)
include_directories(${BWTA_INC_DIR})

if (MSVC)
    if_unset_then_set(BWTA_LIB_DIR ../BWTA/lib)

    link_directories(${BWTA_LIB_DIR})

    target_link_libraries(OpenBWAPI4JBridge ../${BWTA_LIB_DIR}/BWTA)
else()
    target_link_libraries(OpenBWAPI4JBridge BWTA2)
endif()

################################################################################



################################################################################
# OpenBW
################################################################################

if (OPENBW)
    add_definitions(-DOPENBW)
    message("C++ preprocessor macro enabled: OPENBW")

    if (OPENBW_ENABLE_UI)
        add_definitions(-DOPENBW_ENABLE_UI)
        message("C++ preprocessor macro enabled: OPENBW_ENABLE_UI")
    endif()

    if_unset_then_set(OPENBW_DIR ../../../openbw)

    if_unset_then_set(OPENBW_BWAPI_DIR ../../../bwapi)

    include_directories(
        ${OPENBW_DIR}
        ${OPENBW_BWAPI_DIR}/bwapi/OpenBWData
        ${OPENBW_BWAPI_DIR}/bwapi/include
        ${OPENBW_BWAPI_DIR}/bwapi/BWAPI/Source
        ${OPENBW_BWAPI_DIR}/bwapi/Util/Source
        ${OPENBW_BWAPI_DIR}/bwapi/BWAPICore
    )

    if (MSVC)
        if_unset_then_set(OPENBW_LIB_DIR ../${OPENBW_BWAPI_DIR}/build/lib/Release)

        link_directories(${OPENBW_LIB_DIR})

        target_link_libraries(OpenBWAPI4JBridge 
            ${OPENBW_LIB_DIR}/OpenBWData
            ${OPENBW_LIB_DIR}/BWAPI
            ${OPENBW_LIB_DIR}/BWAPILIB
        )

        if (OPENBW_ENABLE_UI)
            target_link_libraries(OpenBWAPI4JBridge ${OPENBW_LIB_DIR}/openbw_ui)
        endif()
    else()
        target_link_libraries(OpenBWAPI4JBridge 
            OpenBWData
            BWAPI
            BWAPILIB
        )
    endif()
endif()

################################################################################
