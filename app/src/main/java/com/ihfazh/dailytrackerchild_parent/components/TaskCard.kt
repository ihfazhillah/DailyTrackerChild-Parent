package com.ihfazh.dailytrackerchild_parent.components

import androidx.compose.animation.animateColorAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.DismissDirection
import androidx.compose.material3.DismissValue
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SwipeToDismiss
import androidx.compose.material3.Text
import androidx.compose.material3.rememberDismissState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import kotlinx.serialization.Serializable


enum class TaskStatus { todo, pending, finished, processing, error }


@Serializable
data class Task(
    val id: String,
    val title: String,
    val status: TaskStatus,
    val image: String? = null
)

typealias onTaskConfirm = (id: String) -> Unit


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TaskCard(
    task: Task,
    modifier: Modifier = Modifier,
    onTaskConfirmClick: onTaskConfirm = {},
) {

    val textStyle = if(task.status == TaskStatus.finished)
        MaterialTheme.typography.titleMedium.copy(textDecoration = TextDecoration.LineThrough)
    else
        MaterialTheme.typography.titleMedium


    if (task.status == TaskStatus.pending){
        val dismissState = rememberDismissState(
            confirmValueChange = {dismissValue ->
                if (dismissValue == DismissValue.DismissedToStart){
                    onTaskConfirmClick.invoke(task.id)
                }
                true
            }
        )
        SwipeToDismiss(
            modifier = modifier
                .clip(RoundedCornerShape(10.dp))
            ,
            state = dismissState,
            background = {
                val color by animateColorAsState(
                    when(dismissState.targetValue){
                        DismissValue.Default -> MaterialTheme.colorScheme.surfaceVariant
                        DismissValue.DismissedToEnd -> Color.Green
                        DismissValue.DismissedToStart -> Color.Red
                    }, label = "color"
                )
                Box(modifier = Modifier
                    .fillMaxSize()
                    .background(color))
            },

            dismissContent ={
                TaskCardInner(task, textStyle)
            },

            directions = setOf(DismissDirection.EndToStart)
        )
    } else {
        TaskCardInner(task, textStyle, modifier)
    }


}

@Composable
private fun TaskCardInner(
    task: Task,
    textStyle: TextStyle,
    modifier : Modifier = Modifier
) {
    Card(
        shape = RoundedCornerShape(10.dp),
        elevation = CardDefaults.cardElevation(5.dp, 10.dp),
        modifier = modifier
    ) {

        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp)
        ) {
            if (task.status === TaskStatus.pending) {
                Icon(Icons.Filled.Warning, contentDescription = "")
                Spacer(modifier = Modifier.width(8.dp))
            }

            if (task.status === TaskStatus.processing){
                CircularProgressIndicator(
                    modifier = Modifier.width(30.dp).height(30.dp)
                )
                Spacer(modifier = Modifier.width(8.dp))
            }


            AsyncImage(
                model = task.image,
                contentDescription = null,
                modifier = Modifier
                    .width(30.dp)
                    .height(30.dp)
            )

            Spacer(modifier = Modifier.width(8.dp))


            Text(
                text = task.title,
                style = textStyle,
            )

        }
    }
}


@Preview
@Composable
fun TaskCardPreview() {
    val tasks = listOf<Task>(
        Task("1", "Sholat Subuh pada waktunya", TaskStatus.finished),
        Task("3", "Dot Isa pagi", TaskStatus.processing),
        Task("2", "Mengerjakan PR Ustadz Abdul Lathif Yang Baik Hati", TaskStatus.pending),
        Task("3", "Dot Isa pagi", TaskStatus.error),
        Task("3", "Dot Isa pagi", TaskStatus.todo),
    )

    Column(
    ) {
        tasks.map { task ->
            TaskCard(
                task,
                Modifier.padding(8.dp, 8.dp)
            )
        }
    }

}