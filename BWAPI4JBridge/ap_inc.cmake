if (NOT AP_INC_CMAKE_INCLUDE)
set(JNI_AP_CMAKE_INCLUDE 1)

include(${CMAKE_CURRENT_LIST_DIR}/func.cmake)
include(${CMAKE_CURRENT_LIST_DIR}/bwapi4j.cmake)

if_unset_then_set(BWAPI4J_AP_INC_DIR ${BWAPI4J_DIR}/build/generated/source/apt/main)
include_directories(${BWAPI4J_AP_INC_DIR})

endif()