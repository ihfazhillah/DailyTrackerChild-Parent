package com.ihfazh.dailytrackerchild_parent.pages

import com.ihfazh.dailytrackerchild_parent.pages.child_picker.ChildState
import com.ihfazh.dailytrackerchild_parent.pages.login.LoginState
import com.ihfazh.dailytrackerchild_parent.pages.task_list.TaskListStates

sealed interface PageState
data class LoginPage(val state: LoginState): PageState
data class ChildPickerPage(val state: ChildState): PageState
data class TaskListPage(val state: TaskListStates): PageState