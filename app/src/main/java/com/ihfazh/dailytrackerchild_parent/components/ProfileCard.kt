package com.ihfazh.dailytrackerchild_parent.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import kotlinx.serialization.Serializable
import kotlin.math.roundToInt

/*
Mainly used for user picker
 */

@Serializable
data class ProfileItem(
    val id: String,
    val name: String,
    val avatarUrl: String,
    val progress: Float,
) {
    companion object
}


typealias OnProfileClicked = (profile: ProfileItem) -> Unit


@Composable
fun ProfileCard(
    profile: ProfileItem,
    modifier: Modifier = Modifier,
    onProfileClicked: OnProfileClicked = {}
) {
    val child = Child(profile.avatarUrl, profile.name)
    var totalProgress = (profile.progress * 100)
    if (totalProgress.isNaN()) {
        totalProgress = 0f
    }
    Card(modifier = modifier.clickable { onProfileClicked.invoke(profile) }) {
        Column(
            modifier = Modifier
                .padding(12.dp)
        ) {

            Row(
                verticalAlignment = Alignment.CenterVertically
            ){
                Text(
                    text = profile.name,
                    style = MaterialTheme.typography.titleSmall
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text(text = "${totalProgress.roundToInt()}%", style = MaterialTheme.typography.bodySmall)
            }

            Spacer(modifier = Modifier.height(8.dp))

            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                Avatar(child, 30.dp)
                Spacer(modifier = Modifier.width(8.dp))
                MyProgress(indicatorProgress = profile.progress, modifier = Modifier.height(20.dp))
            }

        }
    }

}


@Preview
@Composable
fun ProfileCardPreview() {
    val profile = ProfileItem(
        "hello",
        "Sakinah",
        "https://www.shutterstock.com/image-vector/man-faceless-avatar-260nw-1013409094.jpg",
        0.9F
    )
    ProfileCard(profile = profile)
}