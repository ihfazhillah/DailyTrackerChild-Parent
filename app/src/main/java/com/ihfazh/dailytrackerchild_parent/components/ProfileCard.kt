package com.ihfazh.dailytrackerchild_parent.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import kotlinx.serialization.Serializable

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
    Card(modifier = modifier.clickable { onProfileClicked.invoke(profile) }) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .padding(12.dp)
        ) {
            Avatar(child, 100.dp)
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = profile.name,
                style = MaterialTheme.typography.displaySmall
            )

            Spacer(modifier = Modifier.height(32.dp))
            MyProgress(progress = profile.progress)
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
        0.3F
    )
    ProfileCard(profile = profile)
}