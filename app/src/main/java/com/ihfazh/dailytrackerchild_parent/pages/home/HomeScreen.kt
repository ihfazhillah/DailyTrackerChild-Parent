package com.ihfazh.dailytrackerchild_parent.pages.home

import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.viewmodel.compose.viewModel
import com.ihfazh.dailytrackerchild_parent.components.OnProfileClicked


@Composable
fun HomeScreen(
    modifier: Modifier = Modifier,
    onProfileClicked: OnProfileClicked,
    vm: HomeViewModel = viewModel(factory = HomeViewModel.Factory),
){

    val state = vm.state.collectAsState()

    Home(
        state=state.value,
        modifier=modifier,
        onRetryClicked = {vm.getChildren()},
        onChildClicked = onProfileClicked
    )

    val lifecycleOwner = LocalLifecycleOwner.current

    DisposableEffect(lifecycleOwner){
        val observer = LifecycleEventObserver { source, event ->
            when(event){
                Lifecycle.Event.ON_RESUME -> vm.getChildren()
                else -> {
                }
            }
        }
        lifecycleOwner.lifecycle.addObserver(observer)

        onDispose {
            lifecycleOwner.lifecycle.removeObserver(observer)
        }
    }

}
