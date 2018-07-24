if (NOT BWAPI_LIB_CMAKE_INCLUDE)
set(BWAPI_LIB_CMAKE_INCLUDE 1)

include(${CMAKE_CURRENT_LIST_DIR}/func.cmake)
include(${CMAKE_CURRENT_LIST_DIR}/bwapi_inc.cmake)

if (OPENBW)

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

	if_unset_then_set(BWAPI_LIB_DIR ${BWAPI_DIR}/lib)

	find_library(BWAPI_LIB NAMES BWAPI PATHS ${BWAPI_LIB_DIR} NO_DEFAULT_PATH)
	find_library(BWAPIClient_LIB NAMES BWAPIClient PATHS ${BWAPI_LIB_DIR} NO_DEFAULT_PATH)
	
	target_link_libraries(${PROJECT_NAME} 
		${BWAPI_LIB} 
		${BWAPIClient_LIB}
	)

endif()

endif()
