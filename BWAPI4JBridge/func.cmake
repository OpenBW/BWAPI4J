if (NOT FUNC_CMAKE_INCLUDE)
set(FUNC_CMAKE_INCLUDE 1)

function(if_unset_then_set var val)
	if (NOT ${var})
		set(${var} ${val} PARENT_SCOPE)
	endif()
endfunction()

endif()
