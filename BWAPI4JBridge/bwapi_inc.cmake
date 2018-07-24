if (NOT BWAPI_INC_CMAKE_INCLUDE)
set(BWAPI_INC_CMAKE_INCLUDE 1)

include(${CMAKE_CURRENT_LIST_DIR}/func.cmake)
include(${CMAKE_CURRENT_LIST_DIR}/externals.cmake)

if (OPENBW)

    add_definitions(-DOPENBW)

    if (OPENBW_ENABLE_UI)
        add_definitions(-DOPENBW_ENABLE_UI)
    endif()

	if_unset_then_set(OPENBW_UPPER_DIR ${BWAPI4JBRIDGE_EXT_DIR}/openbw)
	if_unset_then_set(OPENBW_DIR ${OPENBW_UPPER_DIR}/openbw)
	if_unset_then_set(OPENBW_BWAPI_DIR ${OPENBW_UPPER_DIR}/bwapi)

    include_directories(
        ${OPENBW_DIR}
        ${OPENBW_BWAPI_DIR}/bwapi/OpenBWData
        ${OPENBW_BWAPI_DIR}/bwapi/include
        ${OPENBW_BWAPI_DIR}/bwapi/BWAPI/Source
        ${OPENBW_BWAPI_DIR}/bwapi/Util/Source
        ${OPENBW_BWAPI_DIR}/bwapi/BWAPICore
    )
		
else()

	if_unset_then_set(BWAPI_ROOT_DIR ${BWAPI4JBRIDGE_EXT_DIR}/bwapi)
	if_unset_then_set(BWAPI_DIR ${BWAPI_ROOT_DIR}/bwapi)
	if_unset_then_set(BWAPI_INC_DIR ${BWAPI_DIR}/include)
				
	include_directories(${BWAPI_INC_DIR})
	
endif()

endif()
