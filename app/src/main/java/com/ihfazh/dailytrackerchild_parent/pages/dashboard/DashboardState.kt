package com.ihfazh.dailytrackerchild_parent.pages.dashboard

import com.ihfazh.dailytrackerchild_parent.components.ChildData
import com.ihfazh.dailytrackerchild_parent.components.DateItem

class DashboardState(
    val date: DateItem,
    val needConfirmationCount: Int = 0,
    val children: List<ChildData> = listOf(),
    val error: String? = null,
    val loading: Boolean = false
)
