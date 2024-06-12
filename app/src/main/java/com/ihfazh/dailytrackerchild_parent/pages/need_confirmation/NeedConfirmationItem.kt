package com.ihfazh.dailytrackerchild_parent.pages.need_confirmation

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.FilledIconButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage


data class NeedConfirmationState(
    val id: String,
    val name: String,
    val photo: String,
    val title: String,
    val submittedAt: String,
    val processing: Boolean = false
)

@Composable
fun NeedConfirmationItem(
    state: NeedConfirmationState,
    onConfirmClick: (id: String) -> Unit,
    onRejectClick: (id: String) -> Unit,
    modifier: Modifier = Modifier
){

    Column(
        modifier
            .fillMaxWidth()
            .shadow(1.dp, shape = RoundedCornerShape(8.dp))
            .background(MaterialTheme.colorScheme.background, shape = RoundedCornerShape(8.dp))
    ) {
        Box(){
            AsyncImage(state.photo, null,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(200.dp)
            )

            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(MaterialTheme.colorScheme.primaryContainer.copy(alpha = 0.4f))
                    .padding(16.dp, 8.dp)
                    .align(Alignment.BottomStart),
                verticalArrangement = Arrangement.spacedBy(4.dp)
            ) {
                Text(text = state.name, style = MaterialTheme.typography.titleSmall)
                Text(text = state.title, style = MaterialTheme.typography.titleLarge)
                Text(text = state.submittedAt, style = MaterialTheme.typography.bodySmall)
            }

        }

        if (state.processing){
            Column(
                Modifier.fillMaxWidth()
                    .padding(16.dp)
            ) {
                Text(text = "Processing....")
            }

        } else {
            Row(
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
            ) {
                FilledIconButton(
                    onClick = {onConfirmClick.invoke(state.id)},
                ) {
                    Icon(Icons.Default.Check, null)
                }

                FilledIconButton(
                    onClick = {onRejectClick.invoke(state.id)},
                    colors = IconButtonDefaults.filledIconButtonColors(
                        containerColor = MaterialTheme.colorScheme.error
                    )
                ) {
                    Icon(Icons.Default.Close, null)
                }
            }
        }
    }

}



@Composable
@Preview
fun NeedConfirmationItemPreview(){
    val states = listOf(
        NeedConfirmationState("1", "Sakinah","", "Sholat subuh", "10 Januari 2023"),
        NeedConfirmationState("1", "Fufu","", "Sholat subuh", "10 Januari 2023", true),
    )

    Column(
        Modifier.padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        states.map{
            NeedConfirmationItem(state = it, onConfirmClick = {}, onRejectClick = {})
        }

    }
}