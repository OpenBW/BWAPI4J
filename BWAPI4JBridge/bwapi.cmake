if (NOT BWAPI_CMAKE_INCLUDE)
set(BWAPI_CMAKE_INCLUDE 1)

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

    if (MSVC)
        if_unset_then_set(OPENBW_LIB_DIR ${OPENBW_BWAPI_DIR}/build/lib/Release)
	else()
		if_unset_then_set(OPENBW_LIB_DIR ${OPENBW_BWAPI_DIR}/build/lib)
    endif()
	
	find_library(OpenBWData_LIB NAMES OpenBWData PATHS ${OPENBW_LIB_DIR} NO_DEFAULT_PATH)
	find_library(BWAPI_LIB NAMES BWAPI PATHS ${OPENBW_LIB_DIR} NO_DEFAULT_PATH)
	find_library(BWAPILIB_LIB NAMES BWAPILIB PATHS ${OPENBW_LIB_DIR} NO_DEFAULT_PATH)
	
	target_link_libraries(${PROJECT_NAME} 
		${OpenBWData_LIB}
		${BWAPI_LIB}
		${BWAPILIB_LIB}
	)
	
	if (OPENBW_ENABLE_UI)
		find_library(openbw_ui_LIB NAMES openbw_ui PATHS ${OPENBW_LIB_DIR} NO_DEFAULT_PATH)
		target_link_libraries(${PROJECT_NAME} ${openbw_ui_LIB})
	endif()
	
else()

	if_unset_then_set(BWAPI_ROOT_DIR ${BWAPI4JBRIDGE_EXT_DIR}/bwapi)
	if_unset_then_set(BWAPI_DIR ${BWAPI_ROOT_DIR}/bwapi)
	if_unset_then_set(BWAPI_INC_DIR ${BWAPI_DIR}/include)
	if_unset_then_set(BWAPI_LIB_DIR ${BWAPI_DIR}/lib)
			
	include_directories(${BWAPI_INC_DIR})
	
	find_library(BWAPI_LIB NAMES BWAPI PATHS ${BWAPI_LIB_DIR} NO_DEFAULT_PATH)
	find_library(BWAPIClient_LIB NAMES BWAPIClient PATHS ${BWAPI_LIB_DIR} NO_DEFAULT_PATH)
	
	target_link_libraries(${PROJECT_NAME} 
		${BWAPI_LIB} 
		${BWAPIClient_LIB}
	)

endif()

endif()
