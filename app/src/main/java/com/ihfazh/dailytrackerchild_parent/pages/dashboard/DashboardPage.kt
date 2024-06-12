package com.ihfazh.dailytrackerchild_parent.pages.dashboard

import android.graphics.drawable.Icon
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.PagerState
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.List
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import com.ihfazh.dailytrackerchild_parent.components.ChildData
import com.ihfazh.dailytrackerchild_parent.components.DateItem
import com.ihfazh.dailytrackerchild_parent.components.HijriDateItem
import com.ihfazh.dailytrackerchild_parent.utils.DateProvider
import kotlinx.coroutines.launch




@Composable
fun DashboardPage(
    dashboardViewModel: DashboardViewModel,
    modifier: Modifier = Modifier,
    onChildClicked: (ChildData) -> Unit,
    onNeedConfirmClicked: () -> Unit,
){

    val dashboardState = dashboardViewModel.state.collectAsState()
    Dashboard(
        state = dashboardState.value,
        date = dashboardViewModel.date,
        modifier = modifier,
        onChildClicked = onChildClicked,
        onNeedConfirmClicked = onNeedConfirmClicked,
        onRetryClicked = {
            dashboardViewModel.collectData()
        }
    )

    val lifecycleOwner = LocalLifecycleOwner.current

    DisposableEffect(lifecycleOwner){
        val observer = LifecycleEventObserver { source, event ->
            when(event){
                Lifecycle.Event.ON_RESUME -> dashboardViewModel.collectData()
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

