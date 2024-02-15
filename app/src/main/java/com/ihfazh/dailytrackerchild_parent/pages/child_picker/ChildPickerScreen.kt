package com.ihfazh.dailytrackerchild_parent.pages.child_picker

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import com.ihfazh.dailytrackerchild_parent.components.OnProfileClicked


@Composable
fun ChildPickerScreen(
    modifier: Modifier = Modifier,
    onProfileClicked: OnProfileClicked,
    vm: ChildPickerViewModel = viewModel(factory = ChildPickerViewModel.Factory),
){

    val state = vm.state.collectAsState()

    ChildPicker(
        state=state.value,
        modifier=modifier,
        onRetryClicked = {vm.getChildren()},
        onChildClicked = onProfileClicked
    )

    LaunchedEffect(""){
        vm.getChildren()
    }

}
