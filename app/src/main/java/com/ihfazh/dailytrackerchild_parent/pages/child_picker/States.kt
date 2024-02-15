package com.ihfazh.dailytrackerchild_parent.pages.child_picker

import com.ihfazh.dailytrackerchild_parent.components.ProfileItem

sealed interface ChildState

data object Loading: ChildState

data class Error(val error: String): ChildState
data class Idle(val profiles: List<ProfileItem>): ChildState