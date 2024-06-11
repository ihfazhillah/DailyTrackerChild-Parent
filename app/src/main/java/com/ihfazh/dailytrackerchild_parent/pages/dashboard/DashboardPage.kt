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
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.ihfazh.dailytrackerchild_parent.components.DateItem
import com.ihfazh.dailytrackerchild_parent.components.HijriDateItem
import kotlinx.coroutines.launch


data class DashboardNavigationItem(
    val title: String,
    val icon: ImageVector,
)

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun DashboardPage(
    modifier: Modifier = Modifier
){
    val pagerState = rememberPagerState(
        initialPage = 1,
    ) {
        2
    }

    val navItems = listOf(
        DashboardNavigationItem("Home", Icons.Default.Home),
        DashboardNavigationItem("Tasks", Icons.Default.List),
    )

    val coroutineScope = rememberCoroutineScope()

    Scaffold(
        bottomBar = {

            Box(modifier = modifier
                .fillMaxWidth()
                .background(MaterialTheme.colorScheme.primaryContainer)
                .shadow(5.dp, spotColor = Color.Transparent)
            ){

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.Absolute.SpaceAround
                ) {

                    navItems.mapIndexed { index, navItem ->
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
    ) {

        Column(
            Modifier
                .fillMaxSize()
                .padding(it)) {
            if (pagerState.currentPage == 0){
                Dashboard(state = DashboardState(DateItem(HijriDateItem(10, "dzulhijjah", 100), "")))
            } else {
                Column {
                    Text(text = "Not implemented")
                }
            }
        }

    }

}


@Preview
@Composable
fun DashboardPagePreview(){
    DashboardPage()

}