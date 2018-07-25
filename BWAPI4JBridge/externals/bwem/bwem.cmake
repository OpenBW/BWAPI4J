if (NOT BWEM_CMAKE_INCLUDE)
set(BWEM_CMAKE_INCLUDE 1)

include(${CMAKE_CURRENT_LIST_DIR}/../../func.cmake)
include(${CMAKE_CURRENT_LIST_DIR}/../../bwapi_inc.cmake)
include(${CMAKE_CURRENT_LIST_DIR}/bwem_defs.cmake)

file(GLOB_RECURSE BWEM_SRCS
    ${BWEM_SRC_DIR}/*.cpp
)
add_library(${PROJECT_NAME} STATIC ${BWEM_SRCS})

include(${CMAKE_CURRENT_LIST_DIR}/bwem_inc.cmake)

endif()
