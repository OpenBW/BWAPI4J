if (NOT BWEM_LIB_CMAKE_INCLUDE)
set(BWEM_LIB_CMAKE_INCLUDE 1)

include(${CMAKE_CURRENT_LIST_DIR}/../../func.cmake)
include(${CMAKE_CURRENT_LIST_DIR}/bwem_defs.cmake)

if (OPENBW)
	find_library(BWEM_LIB NAMES OpenBWEM PATHS ${BWEM_LIB_DIR} NO_DEFAULT_PATH)
else()
	find_library(BWEM_LIB NAMES BWEM PATHS ${BWEM_LIB_DIR} NO_DEFAULT_PATH)
endif()

target_link_libraries(${PROJECT_NAME} ${BWEM_LIB})

endif()
