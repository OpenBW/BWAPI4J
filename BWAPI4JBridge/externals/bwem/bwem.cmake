if (NOT BWEM_CMAKE_INCLUDE)
set(BWEM_CMAKE_INCLUDE 1)

include(${CMAKE_CURRENT_LIST_DIR}/../../func.cmake)
include(${CMAKE_CURRENT_LIST_DIR}/../../bwapi_inc.cmake)

if_unset_then_set(BWEM_ROOT_DIR ${CMAKE_CURRENT_LIST_DIR}/BWEM-community/BWEM)
if_unset_then_set(BWEM_INC_DIR ${BWEM_ROOT_DIR}/include)
if_unset_then_set(BWEM_SRC_DIR ${BWEM_ROOT_DIR}/src)

file(GLOB_RECURSE BWEM_SRCS
    ${BWEM_SRC_DIR}/*.cpp
)
add_library(${PROJECT_NAME} STATIC ${BWEM_SRCS})

include_directories(${BWEM_INC_DIR})

endif()
