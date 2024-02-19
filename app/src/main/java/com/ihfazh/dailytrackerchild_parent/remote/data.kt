package com.ihfazh.dailytrackerchild_parent.remote

import com.ihfazh.dailytrackerchild_parent.components.ProfileItem
import com.ihfazh.dailytrackerchild_parent.components.Task
import kotlinx.serialization.Serializable

@Serializable
data class LoginBody(val username: String, val password: String)
@Serializable
data class LoginResponse(val token: String)
@Serializable
data class ChildrenResponse(val profiles: List<ProfileItem>)

@Serializable
data class TaskRemote(
    val id: String,
    val title : String,
    val status: String,
    val image: String? = null
)

@Serializable
data class TaskListResponse(val tasks: List<Task>)

@Serializable
data class TaskListFromRemoteResponse(val tasks: List<TaskRemote>)


@Serializable
data class MarkAsFinishedBody(val task_id: String)

@Serializable
data class MarkAsFinishedResponse(val task: TaskRemote)

@Serializable
data class ResetTaskBody(val task_id: String)

@Serializable
data class ResetTaskResponse(val task: TaskRemote)
