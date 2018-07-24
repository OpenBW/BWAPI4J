if (NOT SPDLOG_INC_CMAKE_INCLUDE)
set(SPDLOG_INC_CMAKE_INCLUDE 1)

include(${CMAKE_CURRENT_LIST_DIR}/func.cmake)

if_unset_then_set(SPDLOG_INC_DIR ${BWAPI4JBRIDGE_EXT_DIR}/spdlog/include)
include_directories(${SPDLOG_INC_DIR})

endif()
