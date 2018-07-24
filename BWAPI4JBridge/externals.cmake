if (NOT EXTERNALS_CMAKE_INCLUDE)
set(EXTERNALS_CMAKE_INCLUDE 1)

include(${CMAKE_CURRENT_LIST_DIR}/func.cmake)

if_unset_then_set(BWAPI4JBRIDGE_EXT_DIR ${CMAKE_CURRENT_LIST_DIR}/externals)

endif()
