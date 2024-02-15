package com.ihfazh.dailytrackerchild_parent.pages.task_list

import com.ihfazh.dailytrackerchild_parent.components.DateItem
import com.ihfazh.dailytrackerchild_parent.components.ProfileItem
import com.ihfazh.dailytrackerchild_parent.components.Task
import com.ihfazh.dailytrackerchild_parent.components.TaskStatus

sealed interface TaskListStates

open class BaseState(open val profile: ProfileItem, open val dateItem: DateItem): TaskListStates
data class Loading(override val profile: ProfileItem, override val dateItem: DateItem): BaseState(profile, dateItem)
data class Idle(override val profile: ProfileItem, override val dateItem: DateItem, val tasks: List<Task>): BaseState(profile, dateItem){
    fun updateTaskStatusById(id: String, status: TaskStatus) = copy(tasks = tasks.map{ task ->
        if (task.id == id) {
            task.copy(status = status)
        } else {
            task
        }
    })
}
data class Error(override val profile: ProfileItem, override val dateItem: DateItem, val error: String): BaseState(profile, dateItem)
