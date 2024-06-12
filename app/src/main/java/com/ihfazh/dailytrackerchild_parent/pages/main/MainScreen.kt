package com.ihfazh.dailytrackerchild_parent.pages.main

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
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp
import com.ihfazh.dailytrackerchild_parent.pages.dashboard.DashboardPage
import com.ihfazh.dailytrackerchild_parent.pages.dashboard.DashboardViewModel
import kotlinx.coroutines.launch

@Composable
fun NotImplemented(modifier: Modifier = Modifier){
    Column(modifier=modifier.fillMaxSize(), verticalArrangement = Arrangement.Center, horizontalAlignment = Alignment.CenterHorizontally){
        Text(text = "Not Implemented Yet", style = MaterialTheme.typography.displayMedium)
    }

}

data class DashboardNavigationItem(
    val title: String,
    val icon: ImageVector,
)

val NAV_ITEMS = listOf(
    DashboardNavigationItem("Home", Icons.Default.Home),
    DashboardNavigationItem("Tasks", Icons.Default.List),
)

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun BottomNavigation(pagerState: PagerState, modifier: Modifier = Modifier){
    val coroutineScope = rememberCoroutineScope()
    Box(modifier = modifier
        .fillMaxWidth()
        .background(MaterialTheme.colorScheme.primaryContainer)
        .shadow(5.dp, spotColor = Color.Transparent)
    ){

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.Absolute.SpaceAround
        ) {

            NAV_ITEMS.mapIndexed { index, navItem ->
                Column(
                    Modifier
                        .padding(8.dp)
                        .clickable {
                            coroutineScope.launch {
                                pagerState.scrollToPage(index)
                            }
                        }
                    ,
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {

                    if (index == pagerState.currentPage){
                        Icon(
                            navItem.icon,
                            navItem.title,
                            modifier = Modifier
                                .clip(RoundedCornerShape(16.dp))
                                .background(MaterialTheme.colorScheme.primary)
                                .padding(16.dp, 4.dp),
                            tint = MaterialTheme.colorScheme.onPrimary
                        )
                    } else {
                        Icon(navItem.icon, navItem.title,
                            modifier = Modifier
                                .padding(16.dp, 4.dp)
                        )
                    }
                    Text(text = navItem.title)

                }
            }

        }

    }
}



@OptIn(ExperimentalFoundationApi::class)
@Composable
fun MainScreen(
    dashboardViewModel: DashboardViewModel
){
    val pagerState = rememberPagerState(
        initialPage = 0,
    ) {
        NAV_ITEMS.size
    }


    Scaffold(
        bottomBar = {
            BottomNavigation(pagerState = pagerState)
        }
    ) {

        HorizontalPager(state = pagerState,
            modifier = Modifier
                .padding(it)
                .fillMaxSize()
            ) { page ->
            if (page == 0){
                Column(Modifier.fillMaxSize()) {
                    DashboardPage(
                        dashboardViewModel,
                        onChildClicked = {},
                        onNeedConfirmClicked = {},
                    )
                }
            } else {
                NotImplemented(Modifier.padding(it))
            }

        }

    }
}