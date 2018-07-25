if (NOT BWEM_INC_CMAKE_INCLUDE)
set(BWEM_INC_CMAKE_INCLUDE 1)

include(${CMAKE_CURRENT_LIST_DIR}/../../func.cmake)
include(${CMAKE_CURRENT_LIST_DIR}/bwem_defs.cmake)

include_directories(${BWEM_INC_DIR})

endif()
