package com.ihfazh.dailytrackerchild_parent.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage


data class ChildData(
    val id: Int,
    val photo: String,
    val name: String,
    val finishedCount: Int,
    val todoCount: Int,
    val pendingCount: Int,
    val udzurCount: Int
)

@Composable
fun ChildItem(data: ChildData, modifier: Modifier = Modifier, onClick: (ChildData) -> Unit = {}){
    Row(
        horizontalArrangement = Arrangement.spacedBy(16.dp),
        verticalAlignment = Alignment.CenterVertically,
        modifier = modifier
            .fillMaxWidth()
            .shadow(1.dp, shape = RoundedCornerShape(8.dp))
            .padding(24.dp)
    ) {

        AsyncImage(
            data.photo,
            null,
            modifier = Modifier
                .width(100.dp)
                .height(100.dp)
                .clip(CircleShape)
                .border(1.dp, MaterialTheme.colorScheme.primary, shape = CircleShape)
            ,
        )
        
        Column(verticalArrangement = Arrangement.spacedBy(4.dp)) {

            Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {

                Box(
                    modifier = Modifier
                        .clip(CircleShape)
                        .background(Color.Gray)
                        .width(30.dp)
                        .height(30.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Text(data.todoCount.toString(), color = Color.White)
                }

                Box(
                    modifier = Modifier
                        .clip(CircleShape)
                        .background(Color.Yellow)
                        .width(30.dp)
                        .height(30.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Text(data.pendingCount.toString())
                }

                Box(
                    modifier = Modifier
                        .clip(CircleShape)
                        .background(Color.Red)
                        .width(30.dp)
                        .height(30.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Text(data.udzurCount.toString(), color = Color.White)
                }

                Box(
                    modifier = Modifier
                        .clip(CircleShape)
                        .background(MaterialTheme.colorScheme.primary)
                        .width(30.dp)
                        .height(30.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Text(data.todoCount.toString(), color = MaterialTheme.colorScheme.onPrimary)
                }

            }

            Text(
                data.name,
                style = MaterialTheme.typography.displaySmall
            )
        }

    }

}


@Composable
@Preview
fun ChildItemPreview(){
    val children = listOf(
        ChildData(1, "https://png.pngtree.com/png-clipart/20220306/original/pngtree-faceless-hijab-vector-no-background-png-image_7420386.png", "Sakinah", 10, 20, 30, 1 ),
        ChildData(2, "https://png.pngtree.com/png-clipart/20220306/original/pngtree-faceless-hijab-vector-no-background-png-image_7420386.png", "Fukaihah", 10, 20, 30, 1 ),
        ChildData(2, "https://png.pngtree.com/png-clipart/20220306/original/pngtree-faceless-hijab-vector-no-background-png-image_7420386.png", "Mimi Miu Miu", 10, 20, 30, 1 ),
    )

    Column(verticalArrangement = Arrangement.spacedBy(16.dp)) {

        children.map {
            ChildItem(data = it)
        }

    }
}