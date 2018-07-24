if (NOT JNI_INC_CMAKE_INCLUDE)
set(JNI_INC_CMAKE_INCLUDE 1)

include(${CMAKE_CURRENT_LIST_DIR}/func.cmake)
include(${CMAKE_CURRENT_LIST_DIR}/bwapi4j.cmake)

if_unset_then_set(BWAPI4J_JNI_INC_DIR ${BWAPI4J_DIR}/src/native/include)
include_directories(${BWAPI4J_JNI_INC_DIR})

find_package(JNI REQUIRED)
include_directories(${JNI_INCLUDE_DIRS})

endif()
