package com.ihfazh.dailytrackerchild_parent.pages.dashboard

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowRight
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.ihfazh.dailytrackerchild_parent.components.ChildData
import com.ihfazh.dailytrackerchild_parent.components.ChildItem
import com.ihfazh.dailytrackerchild_parent.components.DateItem
import com.ihfazh.dailytrackerchild_parent.utils.DateProvider

@Composable
fun Dashboard(
    state: DashboardState,
    date: DateItem,
    modifier: Modifier = Modifier,
    onChildClicked: (ChildData) -> Unit = {},
    onNeedConfirmClicked: () -> Unit = {},
    onRetryClicked: () -> Unit = {}
){

    if (state.loading){
        LinearProgressIndicator(modifier = Modifier.fillMaxWidth())
    }


    Column(
        modifier = modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(16.dp)
        ,
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center
        ) {
            Text(
                "${date.hijriDateItem.date} ${date.hijriDateItem.month} ${date.hijriDateItem.year}",
                style = MaterialTheme.typography.titleLarge,
            )
        }

        if (state.error !== null){
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(8.dp))
                    .background(MaterialTheme.colorScheme.errorContainer)

                    .padding(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Text(text = state.error, color = MaterialTheme.colorScheme.error)
                Button(
                    onClick = {
                              onRetryClicked.invoke()
                    },
                    colors = ButtonDefaults.buttonColors(
                        containerColor = MaterialTheme.colorScheme.error
                    )

                ) {
                    Text(text = "Retry")
                }
            }
        }

        if (state.needConfirmationCount > 0){
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(MaterialTheme.colorScheme.primary)
                    .clickable {
                        onNeedConfirmClicked.invoke()
                    }
                    .padding(16.dp)
                ,
                horizontalArrangement = Arrangement.Center
            ) {
                Text(
                    text = "${state.needConfirmationCount} tugas butuh dikonfirmasi",
                    color = MaterialTheme.colorScheme.onPrimary,
                    style = MaterialTheme.typography.titleMedium
                )
                Icon(Icons.Default.KeyboardArrowRight, null, tint = MaterialTheme.colorScheme.onPrimary)
            }
        }


        Column(
            modifier = Modifier.fillMaxWidth(),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            state.children.map {
                ChildItem(data = it, onClick = onChildClicked)
            }
        }

    }

}



@Preview
@Composable
fun DashboardPreview(){
    val state = DashboardState(
        0,
        listOf(
            ChildData(1, "https://png.pngtree.com/png-clipart/20220306/original/pngtree-faceless-hijab-vector-no-background-png-image_7420386.png", "Sakinah", 10, 20, 30, 1 ),
            ChildData(2, "https://png.pngtree.com/png-clipart/20220306/original/pngtree-faceless-hijab-vector-no-background-png-image_7420386.png", "Fukaihah", 10, 20, 30, 1 ),
            ChildData(2, "https://png.pngtree.com/png-clipart/20220306/original/pngtree-faceless-hijab-vector-no-background-png-image_7420386.png", "Mimi Miu Miu", 10, 20, 30, 1 ),
        ),
        loading = false,
        error = "Something bad happens"
    )

    Dashboard(state, DateProvider().getDateItem())
}