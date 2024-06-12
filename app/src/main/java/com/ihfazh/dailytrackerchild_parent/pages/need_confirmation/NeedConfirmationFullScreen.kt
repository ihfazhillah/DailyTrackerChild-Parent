package com.ihfazh.dailytrackerchild_parent.pages.need_confirmation

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.paddingFromBaseline
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.FilledIconButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage


@Composable
fun NeedConfirmationFullScreen(
    state: NeedConfirmationState,
    onConfirmClick: (id: String) -> Unit,
    onRejectClick: (id: String) -> Unit,
    onBackClick: () -> Unit
){

    Box(modifier = Modifier.fillMaxSize()){
        AsyncImage(
            state.photo, null,
            modifier = Modifier.fillMaxSize()
        )

        // top bar
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
                .align(Alignment.TopCenter),
            horizontalArrangement = Arrangement.spacedBy(16.dp)
        ) {

            IconButton(onClick = {onBackClick.invoke()}) {
                Icon(Icons.Default.ArrowBack, null)
            }

            Column(
                verticalArrangement = Arrangement.spacedBy(4.dp),
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(text = state.title, style = MaterialTheme.typography.titleLarge)
                Text(text = state.name, style = MaterialTheme.typography.bodyLarge)
                Text(text = state.submittedAt, style = MaterialTheme.typography.bodySmall)
            }

        }

        if (state.processing){
            Column(
                Modifier.fillMaxWidth()
                    .padding(16.dp, 40.dp)
                    .align(Alignment.BottomStart)
            ) {
                Text(text = "Processing....")
            }

        } else {
            Row(
                horizontalArrangement = Arrangement.SpaceAround,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp, 40.dp)
                    .align(Alignment.BottomStart)
            ) {
                FilledIconButton(
                    onClick = { onConfirmClick.invoke(state.id) },
                ) {
                    Icon(Icons.Default.Check, null)
                }

                FilledIconButton(
                    onClick = { onRejectClick.invoke(state.id) },
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
fun NeedConfirmationFullScreenPreview(){
    val state = NeedConfirmationState("1", "Fufu","", "Sholat subuh", "10 Januari 2023", )

    NeedConfirmationFullScreen(state = state, onConfirmClick = {}, onRejectClick ={} ) {

    }
}
