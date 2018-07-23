cmake_minimum_required(VERSION 3.1)

if (OPENBW)
	set(PROJECT_NAME OpenBWAPI4JBridge)
else()
	set(PROJECT_NAME BWAPI4JBridge)
endif()

project(${PROJECT_NAME})

set(CMAKE_CXX_STANDARD 17)
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

    set(CMAKE_RUNTIME_OUTPUT_DIRECTORY "${CMAKE_BINARY_DIR}/..")
    set(CMAKE_ARCHIVE_OUTPUT_DIRECTORY "${CMAKE_BINARY_DIR}/..")
    set(CMAKE_LIBRARY_OUTPUT_DIRECTORY "${CMAKE_BINARY_DIR}/..")
else()
	set(CMAKE_CXX_FLAGS_DEBUG "${CMAKE_CXX_FLAGS} -g")
	set(CMAKE_CXX_FLAGS_RELEASE "${CMAKE_CXX_FLAGS} -O3")

    set(CMAKE_RUNTIME_OUTPUT_DIRECTORY "${CMAKE_BINARY_DIR}/../Release")
    set(CMAKE_ARCHIVE_OUTPUT_DIRECTORY "${CMAKE_BINARY_DIR}/../Release")
    set(CMAKE_LIBRARY_OUTPUT_DIRECTORY "${CMAKE_BINARY_DIR}/../Release")
endif()

function(if_unset_then_set var val)
    if (NOT ${var})
        set(${var} ${val} PARENT_SCOPE)
    endif()
endfunction()

include(${CMAKE_CURRENT_LIST_DIR}/bwapi4j.cmake)
include(${CMAKE_CURRENT_LIST_DIR}/externals.cmake)
include(${CMAKE_CURRENT_LIST_DIR}/bwapi4jbridge.cmake)
include(${CMAKE_CURRENT_LIST_DIR}/spdlog.cmake)
include(${CMAKE_CURRENT_LIST_DIR}/jni.cmake)
include(${CMAKE_CURRENT_LIST_DIR}/bwapi.cmake)
