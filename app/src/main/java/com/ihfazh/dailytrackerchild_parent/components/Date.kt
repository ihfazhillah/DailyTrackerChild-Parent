package com.ihfazh.dailytrackerchild_parent.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

data class HijriDateItem(
    val date: Int,
    val month: String,
    val year: Int
)

data class DateItem(
    val hijriDateItem: HijriDateItem,
    val georgianDateString: String
)

@Composable
fun Date(item: DateItem, modifier: Modifier = Modifier) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier
    ) {

        Text(
            text = item.hijriDateItem.date.toString(),
            style = MaterialTheme.typography.displayMedium
        )
        Text(text = item.hijriDateItem.month, style = MaterialTheme.typography.displayLarge)
        Text(
            text = item.hijriDateItem.year.toString(),
            style = MaterialTheme.typography.displayMedium
        )

        Spacer(Modifier.height(24.dp))
        Text(text = item.georgianDateString, style = MaterialTheme.typography.bodyMedium)
    }
}


@Preview
@Composable
fun DatePreview() {
    Date(
        DateItem(
            HijriDateItem(24, "Rajab", 1445),
            "27 Januari 2024"
        )
    )
}