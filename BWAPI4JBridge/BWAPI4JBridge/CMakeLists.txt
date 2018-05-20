cmake_minimum_required(VERSION 3.1)

if(NOT CMAKE_BUILD_TYPE)
    set(CMAKE_BUILD_TYPE Release)
endif()

#set(CMAKE_CXX_FLAGS "-Wall -Wextra")
set(CMAKE_CXX_FLAGS_DEBUG "${CMAKE_CXX_FLAGS} -g")
set(CMAKE_CXX_FLAGS_RELEASE "${CMAKE_CXX_FLAGS} -O3")


if (OPENBW)
set(PROJECT_NAME OpenBWAPI4JBridge)
else()
set(PROJECT_NAME BWAPI4JBridge)
endif()

project(${PROJECT_NAME})

set(CMAKE_CXX_STANDARD 14)
set(CMAKE_CXX_STANDARD_REQUIRED ON)

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

file(GLOB_RECURSE BWAPI4JBRIDGE_SRCS
    ${BWAPI4JBRIDGE_SRC_DIR}/*.h
    ${BWAPI4JBRIDGE_SRC_DIR}/*.cpp
)

add_library(${PROJECT_NAME} SHARED ${BWAPI4JBRIDGE_SRCS})

################################################################################



################################################################################
# JNI
################################################################################

if_unset_then_set(JNI_INC_DIR ../JNI/include)

if_unset_then_set(BWAPI4J_JNI_INC_DIR ../../BWAPI4J/src/native/include)

include_directories(${JNI_INC_DIR} ${BWAPI4J_JNI_INC_DIR})
if (WIN32)
    include_directories(${JNI_INC_DIR}/win32)
else()
    include_directories(${JNI_INC_DIR}/linux)
endif()

################################################################################



################################################################################
# BWTA
################################################################################

if_unset_then_set(BWTA_INC_DIR ../BWTA/include)
include_directories(${BWTA_INC_DIR})

if (MSVC)
	if_unset_then_set(BWTA_LIB_DIR ../BWTA/lib)

	link_directories(${BWTA_LIB_DIR})

	target_link_libraries(${PROJECT_NAME} ../${BWTA_LIB_DIR}/BWTA)
else()
	target_link_libraries(${PROJECT_NAME} BWTA2)
endif()

################################################################################



################################################################################
# [Vanilla BW] / [OpenBW] / [BWAPI]
################################################################################

if (OPENBW)

    add_definitions(-DOPENBW)

    if (OPENBW_ENABLE_UI)
        add_definitions(-DOPENBW_ENABLE_UI)
    endif()

	if_unset_then_set(OPENBW_UPPER_DIR ../../..)
    if_unset_then_set(OPENBW_DIR ${OPENBW_UPPER_DIR}/openbw)
    if_unset_then_set(OPENBW_BWAPI_DIR ${OPENBW_UPPER_DIR}/bwapi)

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

        target_link_libraries(${PROJECT_NAME} 
            ${OPENBW_LIB_DIR}/OpenBWData
            ${OPENBW_LIB_DIR}/BWAPI
            ${OPENBW_LIB_DIR}/BWAPILIB
        )

        if (OPENBW_ENABLE_UI)
            target_link_libraries(${PROJECT_NAME} ${OPENBW_LIB_DIR}/openbw_ui)
        endif()
    else()
        target_link_libraries(${PROJECT_NAME} 
            OpenBWData
            BWAPI
            BWAPILIB
        )
    endif()
	
else()

	# Vanilla BW

	if_unset_then_set(BWAPI_DIR ../BWAPI)
	if_unset_then_set(BWAPI_INC_DIR ${BWAPI_DIR}/include)
	if_unset_then_set(BWAPI_LIB_DIR ${BWAPI_DIR}/lib)
	
	include_directories(${BWAPI_INC_DIR})
	
	link_directories(${BWAPI_LIB_DIR})
	
	target_link_libraries(${PROJECT_NAME} ../${BWAPI_LIB_DIR}/BWAPI)
	target_link_libraries(${PROJECT_NAME} ../${BWAPI_LIB_DIR}/BWAPIClient)

endif()

################################################################################
