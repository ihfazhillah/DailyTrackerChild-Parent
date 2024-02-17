package com.ihfazh.dailytrackerchild_parent.pages.task_list

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.rounded.Refresh
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.ihfazh.dailytrackerchild_parent.components.Child
import com.ihfazh.dailytrackerchild_parent.components.DateItem
import com.ihfazh.dailytrackerchild_parent.components.ErrorMessage
import com.ihfazh.dailytrackerchild_parent.components.HijriDateItem
import com.ihfazh.dailytrackerchild_parent.components.ProfileItem
import com.ihfazh.dailytrackerchild_parent.components.Task
import com.ihfazh.dailytrackerchild_parent.components.TaskCard
import com.ihfazh.dailytrackerchild_parent.components.TaskStatus
import com.ihfazh.dailytrackerchild_parent.components.onTaskConfirm
import com.ihfazh.dailytrackerchild_parent.types.OnRetryClicked


private fun ProfileItem.toChild(): Child = Child(avatarUrl, name)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TaskList(
    state: BaseState,
    modifier: Modifier = Modifier,
    onTaskConfirm: onTaskConfirm = {},
    onRetryClicked: OnRetryClicked = {},
    onBackClicked: () -> Unit = {}
){

    val user = state.profile.toChild()

    Scaffold(
        topBar = {
            TopAppBar(
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primaryContainer,
                    titleContentColor = MaterialTheme.colorScheme.primary
                ),
                title = {
                    Row{
                        AsyncImage(
                            model = user.avatarUrl,
                            contentDescription = null,
                            modifier = Modifier
                                .height(30.dp)
                                .width(30.dp)
                                .clip(CircleShape)
                        )
                        Spacer(Modifier.width(8.dp))
                        Text(text = user.name)
                    }
                },
                navigationIcon = {
                    IconButton(onClick = onBackClicked) {
                        Icon(Icons.Filled.ArrowBack, "backIcon")
                    }
                },
            )
        }
    ) { paddingValues ->
            Column(
                modifier = modifier
                    .padding(paddingValues)
            ) {

                if (state is Idle) {
                    LazyColumn(
                        modifier = Modifier.padding(16.dp)
                    ) {
                        items(state.tasks) { task ->
                            TaskCard(
                                task = task,
                                modifier = Modifier
                                    .padding(0.dp, 8.dp)
                                ,
                                onTaskConfirmClick = onTaskConfirm
                            )
                        }
                    }
                }


                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center,
                    modifier = Modifier
                        .fillMaxHeight()
                        .fillMaxWidth()
                ) {

                    if (state is Error) {
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

                    if (state is Loading) {

                        CircularProgressIndicator(
                            Modifier
                                .height(100.dp)
                                .width(100.dp)
                        )

                    }


                }
            }

        }


}


@Preview(device = "id:pixel")
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
        Idle(
            ProfileItem("1", "Ihfazhillah", "https://www.gstatic.com/devrel-devsite/prod/v5ba20c1e081870fd30b7c8ebfa8711369a575956c1f44323664285c05468c6a4/android/images/lockup.svg", 0.2F),
            tasks=tasks
        ),
//        onProfileClicked = {}
    )
}