package com.ihfazh.dailytrackerchild_parent.pages.home

import com.ihfazh.dailytrackerchild_parent.components.ProfileItem

sealed  class  ChildState(open val profiles: List<ProfileItem>)

data class Loading(override val profiles: List<ProfileItem> = listOf()): ChildState(profiles)

data class Error(val error: String, override val profiles: List<ProfileItem> = listOf()): ChildState(profiles)
data class Idle(override val profiles: List<ProfileItem>): ChildState(profiles)