package com.ihfazh.dailytrackerchild_parent.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Divider
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
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

typealias onTaskFinish = (id: String) -> Unit


@Composable
fun TaskCard(
    task: Task,
    modifier: Modifier = Modifier,
    onTaskFinishClick: onTaskFinish = {},
) {
    val statusColor = when (task.status) {
        TaskStatus.todo -> Color.Red
        TaskStatus.pending -> Color.Yellow
        TaskStatus.finished -> Color.Green
        TaskStatus.processing -> Color.Gray
        TaskStatus.error -> Color.Red
    }

    val iconResourceId = when (task.status) {
        TaskStatus.todo -> Icons.Default.FavoriteBorder
        TaskStatus.pending -> Icons.Default.Lock
        TaskStatus.finished -> Icons.Default.Favorite
        TaskStatus.processing -> Icons.Default.Info
        TaskStatus.error -> Icons.Default.Clear
    }

    Card(
        modifier = modifier,
        shape = RoundedCornerShape(10.dp),
        elevation = CardDefaults.cardElevation(5.dp, 10.dp)
    ) {

        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .fillMaxWidth()
                .padding(12.dp)
        ) {
            AsyncImage(
                model = task.image,
                contentDescription = null,
                modifier = Modifier
                    .width(80.dp)
                    .height(80.dp)
            )

            Spacer(modifier = Modifier.width(12.dp))

            Text(
                text = task.title,
                style = MaterialTheme.typography.displaySmall,
                modifier = Modifier
            )
        }


        Spacer(modifier = Modifier.height(40.dp))
        Divider(thickness = 1.dp)


        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier
                .padding(12.dp)
                .fillMaxWidth()
        ) {

            Box(
                modifier = Modifier
                    .size(50.dp)
                    .clip(CircleShape)
                    .background(statusColor)
                    .padding(8.dp)

            ) {
                Icon(
                    iconResourceId,
                    contentDescription = "Sudah Selesai",
                    modifier = Modifier
                        .padding(5.dp)
                )
            }

            if ((task.status == TaskStatus.todo) or (task.status == TaskStatus.error)) {
                ElevatedButton(
                    onClick = { onTaskFinishClick.invoke(task.id) },
                    modifier = Modifier

                ) {
                    Text(text = "Selesaikan")
                }
            }

            if (task.status == TaskStatus.processing) {
                CircularProgressIndicator(
                    Modifier
                        .width(50.dp)
                        .height(50.dp)
                )
            }
        }
    }
}


@Preview
@Composable
fun TaskCardPreview() {
    val tasks = listOf<Task>(
        Task("1", "Sholat Subuh pada waktunya", TaskStatus.finished),
        Task("3", "Dot Isa pagi", TaskStatus.processing),
        Task("2", "Mengerjakan PR Ustadz", TaskStatus.pending),
        Task("3", "Dot Isa pagi", TaskStatus.error),
        Task("3", "Dot Isa pagi", TaskStatus.todo),
    )

    Column(
    ) {
        tasks.map { task ->
            TaskCard(
                task,
                Modifier.padding(12.dp, 24.dp)
            )
        }
    }

}