if (NOT SPDLOG_CMAKE_INCLUDE)
set(SPDLOG_CMAKE_INCLUDE 1)

if_unset_then_set(SPDLOG_INC_DIR ${BWAPI4JBRIDGE_EXT_DIR}/spdlog/include)
include_directories(${SPDLOG_INC_DIR})

endif()
