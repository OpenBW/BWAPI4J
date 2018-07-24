if (NOT FUNC_CMAKE_INCLUDE)
set(FUNC_CMAKE_INCLUDE 1)

# If the specified variable is not set, then set it to the specified value.
function(if_unset_then_set var val)
	if (NOT ${var})
		set(${var} ${val} PARENT_SCOPE)
	endif()
endfunction()

# Throw FATAL_ERROR if the specified directory does not exist.
#function(check_dir_exists dir)
#	if (NOT EXISTS ${dir} OR NOT IS_DIRECTORY ${dir})
#		message(FATAL_ERROR "Directory not found: ${dir}")
#	endif()
#endfunction()

# If the specified variable is not set, then set it to the specified directory and check if it exists.
#function(if_unset_then_dir var dir)
#	if (NOT ${var})
#		check_dir_exists(${dir})
#		set(${var} ${dir})
#	endif()
#endfunction()

endif()
