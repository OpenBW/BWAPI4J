if (NOT BWEM_DEFS_CMAKE_INCLUDE)
set(BWEM_DEFS_CMAKE_INCLUDE 1)

include(${CMAKE_CURRENT_LIST_DIR}/../../func.cmake)

if_unset_then_set(BWEM_ROOT_DIR ${CMAKE_CURRENT_LIST_DIR}/BWEM-community/BWEM)
if_unset_then_set(BWEM_INC_DIR ${BWEM_ROOT_DIR}/include)
if_unset_then_set(BWEM_SRC_DIR ${BWEM_ROOT_DIR}/src)
if_unset_then_set(BWEM_LIB_DIR ${BWEM_ROOT_DIR}/../../Release)

endif()
