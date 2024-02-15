package com.ihfazh.dailytrackerchild_parent.pages.task_list

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Refresh
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.ihfazh.dailytrackerchild_parent.components.Avatar
import com.ihfazh.dailytrackerchild_parent.components.Child
import com.ihfazh.dailytrackerchild_parent.components.Date
import com.ihfazh.dailytrackerchild_parent.components.DateItem
import com.ihfazh.dailytrackerchild_parent.components.ErrorMessage
import com.ihfazh.dailytrackerchild_parent.components.HijriDateItem
import com.ihfazh.dailytrackerchild_parent.components.MyProgress
import com.ihfazh.dailytrackerchild_parent.components.ProfileItem
import com.ihfazh.dailytrackerchild_parent.components.Task
import com.ihfazh.dailytrackerchild_parent.components.TaskCard
import com.ihfazh.dailytrackerchild_parent.components.TaskStatus
import com.ihfazh.dailytrackerchild_parent.components.onTaskFinish
import com.ihfazh.dailytrackerchild_parent.types.OnRetryClicked


private fun ProfileItem.toChild(): Child = Child(avatarUrl, name)

@Composable
fun TaskList(
    state: BaseState,
    modifier: Modifier = Modifier,
    onTaskFinish: onTaskFinish = {},
    onRetryClicked: OnRetryClicked = {},
    onProfileClicked: () -> Unit

){

    val user = state.profile.toChild()

    val date = state.dateItem

    val progress = if (state is Idle) {
        state.tasks.filter { it.status === TaskStatus.finished}.size.toFloat() / state.tasks.size
    } else {
        state.profile.progress
    }


    Surface {
        Column (
            modifier = modifier
        ){
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .clickable { onProfileClicked.invoke() }
            ) {
                Avatar(child = user)
                Spacer(modifier = Modifier.width(16.dp))
                Text(
                    text = user.name,
                    style = MaterialTheme.typography.headlineMedium
                )
            }

            Row {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center,
                    modifier = Modifier
                        .fillMaxHeight()
                        .padding(24.dp)
                ){

                    Date(
                        item = date,
                    )

                    Spacer(modifier = Modifier.height(40.dp))

                    MyProgress(progress = progress)
                }

                if (state is Idle){
                    LazyVerticalGrid(
                        columns = GridCells.Fixed(2),
                        contentPadding = PaddingValues(8.dp)
                    ){
                        items(state.tasks){task ->
                            TaskCard(
                                task = task,
                                modifier = Modifier
                                    .padding(12.dp),
                                onTaskFinishClick = onTaskFinish
                            )
                        }
                    }
                }

                if (state is Error){
                    Column (
                        verticalArrangement = Arrangement.Center,
                        horizontalAlignment = Alignment.CenterHorizontally,
                        modifier = Modifier
                            .fillMaxHeight()
                            .fillMaxWidth()
                    ){
                        ErrorMessage(errorMessage = state.error)

                        Spacer(modifier = Modifier.height(16.dp))

                        Button(onClick = onRetryClicked) {
                            Icon(
                                Icons.Rounded.Refresh,
                                null
                            )
                            Text(text = "Ulangi")
                        }

                    }
                }

                if (state is Loading){
                    Column (
                        verticalArrangement = Arrangement.Center,
                        horizontalAlignment = Alignment.CenterHorizontally,
                        modifier = Modifier
                            .fillMaxHeight()
                            .fillMaxWidth()
                    ){

                        CircularProgressIndicator(Modifier.height(100.dp).width(100.dp))

                    }

                }

            }
        }

    }
}


@Preview(widthDp = 1280)
@Composable
fun TaskListPreview(){
    val tasks = listOf<Task>(
        Task("1", "Sholat Subuh", TaskStatus.finished),
        Task("2", "Mengerjakan PR Ustadz", TaskStatus.pending),
        Task("3", "Dot ISA Pagi", TaskStatus.todo),
        Task("4", "Belajar Sama Amah Arini", TaskStatus.todo),
        Task("5", "Belajar Sama Amah Rufa", TaskStatus.todo),
        Task("6", "Dot Isa Sore", TaskStatus.todo),
        Task("6", "Trampolin 100 kali", TaskStatus.todo),
        Task("6", "Sapu sapu rumah", TaskStatus.todo),
        Task("6", "Sepedaan", TaskStatus.todo),
    )
    val date = DateItem(
        HijriDateItem(15, "Rajab", 1445),
        "27 Januari 2024"
    )

    TaskList(
        Loading(
            ProfileItem("1", "url", "hello", 0.2F),
            date
        ),
        onProfileClicked = {}
    )
}