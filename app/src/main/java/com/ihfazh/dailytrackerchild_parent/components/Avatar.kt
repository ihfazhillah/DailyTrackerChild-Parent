package com.ihfazh.dailytrackerchild_parent.components

import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage


data class Child(
    val avatarUrl: String,
    val name: String
)

@Composable
fun Avatar(child: Child, width: Dp = 80.dp) {
    AsyncImage(
        model = child.avatarUrl,
        contentDescription = null,
        modifier = Modifier
            .height(width)
            .width(width)
            .clip(CircleShape)
    )
}


@Preview
@Composable
fun AvatarPreview() {
    Avatar(Child("https://ptetutorials.com/images/user-profile.png", "Sakinah"))
}
