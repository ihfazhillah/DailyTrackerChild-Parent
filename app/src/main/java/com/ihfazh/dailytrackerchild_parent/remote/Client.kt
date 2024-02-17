package com.ihfazh.dailytrackerchild_parent.remote

import com.ihfazh.dailytrackerchild_parent.components.Task
import com.ihfazh.dailytrackerchild_parent.fp.Outcome
import com.ihfazh.dailytrackerchild_parent.fp.OutcomeError

interface Client {
    suspend fun login(body: LoginBody): Outcome<LoginResponse, OutcomeError>
    suspend fun getChildren(): Outcome<ChildrenResponse, OutcomeError>
    suspend fun getTaskList(id: String): Outcome<TaskListResponse, OutcomeError>

    suspend fun markTaskAsFinished(id: String): Outcome<Task, OutcomeError>
    suspend fun confirmTask(id: String): Outcome<Task, OutcomeError>
}