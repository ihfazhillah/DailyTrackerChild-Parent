package com.ihfazh.dailytrackerchild_parent.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import kotlin.math.roundToInt

@Composable
fun MyProgress(progress: Float) {
    var totalProgress = (progress * 100)
    if (totalProgress.isNaN()) {
        totalProgress = 0f
    }

    Column {
        Text(text = "Kemajuan Kamu", style = MaterialTheme.typography.labelLarge)
        Spacer(modifier = Modifier.height(8.dp))

        Row(verticalAlignment = Alignment.CenterVertically) {
            LinearProgressIndicator(
                progress = progress,
                modifier = Modifier
                    .height(16.dp)
            )

            Spacer(Modifier.width(16.dp))

            Text(text = "${totalProgress.roundToInt()}%", style = MaterialTheme.typography.labelLarge)
        }
    }
}


@Preview
@Composable
fun MyProgressPreview() {
    MyProgress((8.toFloat() / 10))
}