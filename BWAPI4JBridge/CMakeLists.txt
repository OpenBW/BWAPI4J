################################################################################

cmake_minimum_required(VERSION 3.1)

if (OPENBW)
	set(PROJECT_NAME OpenBWAPI4JBridge)
else()
	set(PROJECT_NAME BWAPI4JBridge)
endif()

project(${PROJECT_NAME})

set(CMAKE_CXX_STANDARD 14)
set(CMAKE_CXX_STANDARD_REQUIRED ON)

if(NOT CMAKE_BUILD_TYPE)
    set(CMAKE_BUILD_TYPE Release)
endif()

if (BWAPI4JBRIDGE_ENABLE_LOGGER)
	add_definitions(-DBWAPI4JBRIDGE_ENABLE_LOGGER)
endif()

if (MSVC)
    add_definitions(-D_CRT_SECURE_NO_WARNINGS -DNOMINMAX -DWIN32 -D_WIN32 -D_WINDOWS -D_USRDLL)
	
	if (NOT OPENBW AND CMAKE_BUILD_TYPE MATCHES Release)
		set(CMAKE_SHARED_LINKER_FLAGS "${CMAKE_SHARED_LINKER_FLAGS} /LTCG")
	endif()

    set(CMAKE_RUNTIME_OUTPUT_DIRECTORY "..")
    set(CMAKE_ARCHIVE_OUTPUT_DIRECTORY "..")
    set(CMAKE_LIBRARY_OUTPUT_DIRECTORY "..")
else()
	set(CMAKE_CXX_FLAGS_DEBUG "${CMAKE_CXX_FLAGS} -g")
	set(CMAKE_CXX_FLAGS_RELEASE "${CMAKE_CXX_FLAGS} -O3")

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



################################################################################
# BWAPI4J (Java)
################################################################################

if_unset_then_set(BWAPI4J_DIR ${CMAKE_SOURCE_DIR}/../BWAPI4J)



################################################################################
# BWAPI4JBridge
################################################################################

if_unset_then_set(BWAPI4JBRIDGE_SRC_DIR ${CMAKE_SOURCE_DIR}/src)
if_unset_then_set(BWAPI4JBRIDGE_EXT_DIR ${CMAKE_SOURCE_DIR}/externals)

file(GLOB_RECURSE BWAPI4JBRIDGE_SRCS
    ${BWAPI4JBRIDGE_SRC_DIR}/*.h
    ${BWAPI4JBRIDGE_SRC_DIR}/*.cpp
)

add_library(${PROJECT_NAME} SHARED ${BWAPI4JBRIDGE_SRCS})

################################################################################



################################################################################
# spdlog
################################################################################

if_unset_then_set(SPDLOG_INC_DIR ${BWAPI4JBRIDGE_EXT_DIR}/spdlog/include)
include_directories(${SPDLOG_INC_DIR})

################################################################################



################################################################################
# JNI
################################################################################

if_unset_then_set(BWAPI4J_JNI_INC_DIR ${BWAPI4J_DIR}/src/native/include)
include_directories(${BWAPI4J_JNI_INC_DIR})

find_package(JNI REQUIRED)
include_directories(${JNI_INCLUDE_DIRS})

################################################################################



################################################################################
# BWTA
################################################################################

#if_unset_then_set(BWTA_INC_DIR ../BWTA/include)
#include_directories(${BWTA_INC_DIR})
#
#if (MSVC)
#	if_unset_then_set(BWTA_LIB_DIR ../BWTA/lib)
#
#	link_directories(${BWTA_LIB_DIR})
#
#	target_link_libraries(${PROJECT_NAME} ../${BWTA_LIB_DIR}/BWTA)
#else()
#	target_link_libraries(${PROJECT_NAME} BWTA2)
#endif()

################################################################################



################################################################################
# [Vanilla BW] / [OpenBW] / [BWAPI]
################################################################################

if (OPENBW)

    add_definitions(-DOPENBW)

    if (OPENBW_ENABLE_UI)
        add_definitions(-DOPENBW_ENABLE_UI)
    endif()

	if_unset_then_set(OPENBW_UPPER_DIR ${BWAPI4JBRIDGE_EXT_DIR}/openbw)
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
        if_unset_then_set(OPENBW_LIB_DIR ${OPENBW_BWAPI_DIR}/build/lib/Release)
	else()
		if_unset_then_set(OPENBW_LIB_DIR ${OPENBW_BWAPI_DIR}/build/lib)
    endif()
	
	find_library(OpenBWData_LIB NAMES OpenBWData PATHS ${OPENBW_LIB_DIR} NO_DEFAULT_PATH)
	find_library(BWAPI_LIB NAMES BWAPI PATHS ${OPENBW_LIB_DIR} NO_DEFAULT_PATH)
	find_library(BWAPILIB_LIB NAMES BWAPILIB PATHS ${OPENBW_LIB_DIR} NO_DEFAULT_PATH)
	
	target_link_libraries(${PROJECT_NAME} 
		${OpenBWData_LIB}
		${BWAPI_LIB}
		${BWAPILIB_LIB}
	)
	
	if (OPENBW_ENABLE_UI)
		find_library(openbw_ui_LIB NAMES openbw_ui PATHS ${OPENBW_LIB_DIR} NO_DEFAULT_PATH)
		target_link_libraries(${PROJECT_NAME} ${openbw_ui_LIB})
	endif()
	
else()

	if_unset_then_set(BWAPI_ROOT_DIR ${BWAPI4JBRIDGE_EXT_DIR}/bwapi)
	if_unset_then_set(BWAPI_DIR ${BWAPI_ROOT_DIR}/bwapi)
	if_unset_then_set(BWAPI_INC_DIR ${BWAPI_DIR}/include)
	if_unset_then_set(BWAPI_LIB_DIR ${BWAPI_DIR}/lib)
			
	include_directories(${BWAPI_INC_DIR})
	
	find_library(BWAPI_LIB NAMES BWAPI PATHS ${BWAPI_LIB_DIR} NO_DEFAULT_PATH)
	find_library(BWAPIClient_LIB NAMES BWAPIClient PATHS ${BWAPI_LIB_DIR} NO_DEFAULT_PATH)
	
	target_link_libraries(${PROJECT_NAME} 
		${BWAPI_LIB} 
		${BWAPIClient_LIB}
	)

endif()

################################################################################
