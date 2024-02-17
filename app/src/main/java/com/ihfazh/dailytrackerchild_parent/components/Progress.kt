package com.ihfazh.dailytrackerchild_parent.components

import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Composable
fun MyProgress(indicatorProgress: Float, modifier: Modifier = Modifier) {

    LinearProgressIndicator(
        progress = indicatorProgress,
        modifier = modifier
            .clip(RoundedCornerShape(24.dp))
    )
}


@Preview
@Composable
fun MyProgressPreview() {
    MyProgress((8.toFloat() / 10))
}