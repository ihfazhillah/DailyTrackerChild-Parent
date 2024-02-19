package com.ihfazh.dailytrackerchild_parent.pages.task_list

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier

@Composable
fun TaskListScreen(
    viewModel: TaskListViewModel,
    modifier: Modifier = Modifier,
    onBack: () -> Unit = {}
){

    val state = viewModel.state.collectAsState()

    TaskList(
        state = state.value,
        modifier = modifier,
        onRetryClicked = {viewModel.getTaskList()},
        onTaskConfirm = {viewModel.markTaskAsFinished(it)},
        onTaskTodo = {viewModel.markAsTodo(it)},
        onBackClicked = onBack
    )
}