if (NOT JNI_CMAKE_INCLUDE)
set(JNI_CMAKE_INCLUDE 1)

if (NOT BWAPI4J_DIR)
	include(${CMAKE_CURRENT_LIST_DIR}/bwapi4j.cmake)
endif()

if_unset_then_set(BWAPI4J_JNI_INC_DIR ${BWAPI4J_DIR}/src/native/include)
include_directories(${BWAPI4J_JNI_INC_DIR})

find_package(JNI REQUIRED)
include_directories(${JNI_INCLUDE_DIRS})

endif()
