package com.ihfazh.dailytrackerchild_parent.pages.home

import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.consumeWindowInsets
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults.topAppBarColors
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


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    modifier: Modifier = Modifier,
    onProfileClicked: OnProfileClicked,
    vm: HomeViewModel = viewModel(factory = HomeViewModel.Factory),
){

    val state = vm.state.collectAsState()

    Scaffold(
        topBar = {
            TopAppBar(colors = topAppBarColors(
                containerColor = MaterialTheme.colorScheme.primaryContainer,
                titleContentColor = MaterialTheme.colorScheme.primary
            ),
                title = {
                    Text(text = "Perkembangan Anak")
                }
            )
        }
    ) { it ->
        Home(
            state=state.value,
            modifier=modifier
                .padding(it)
            ,
            onRetryClicked = {vm.getChildren()},
            onChildClicked = onProfileClicked
        )
    }


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
