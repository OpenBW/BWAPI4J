if_unset_then_set(BWEM_ROOT_DIR ${CMAKE_CURRENT_LIST_DIR}/BWEM-community/BWEM)
if_unset_then_set(BWEM_INC_DIR ${BWEM_ROOT_DIR}/include)
if_unset_then_set(BWEM_SRC_DIR ${BWEM_ROOT_DIR}/src)

file(GLOB_RECURSE BWEM_SRCS
    ${BWEM_SRC_DIR}/*.cpp
)
add_library(${PROJECT_NAME} STATIC ${BWEM_SRCS})

include_directories(${BWEM_INC_DIR})

if_unset_then_set(BWAPI4JBRIDGE_DIR ${CMAKE_CURRENT_LIST_DIR}/../..)
include_directories(${BWAPI4JBRIDGE_DIR}/externals/bwapi/bwapi/include)
