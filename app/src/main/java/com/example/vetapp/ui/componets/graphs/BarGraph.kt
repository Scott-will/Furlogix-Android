package com.example.vetapp.ui.componets.graphs

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.vetapp.Database.Entities.ReportEntry

@Composable
fun BarGraph(entries : List<ReportEntry>, name : String ) {
    val entryDict = entries.groupBy { it.value }
    // Calculate max height based on the largest count
    val trueCount = entryDict[true.toString()]?.size
    val falseCount = entryDict[false.toString()]?.size
    if((trueCount == null) or (falseCount == null)){
        return
    }
    val maxCount = maxOf(trueCount!!, falseCount!!)
    val trueBarHeight = (trueCount / maxCount.toFloat()) * 200f // Adjust height scale as needed
    val falseBarHeight = (falseCount / maxCount.toFloat()) * 200f // Adjust height scale as needed
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Text( text = name,
            style = TextStyle(fontWeight = FontWeight.Bold),
            modifier = Modifier.padding(top = 8.dp)
        )
        Row(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.Bottom
        ) {
            // True bar
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Canvas(modifier = Modifier.size(50.dp, trueBarHeight.dp)) {
                    drawRect(
                        color = Color.Green,
                        size = size.copy(width = 50f) // You can adjust the width
                    )
                }
                Text(
                    text = "True",
                    style = TextStyle(fontWeight = FontWeight.Bold),
                    modifier = Modifier.padding(top = 8.dp)
                )
                Text(
                    text = "$trueCount",
                    style = TextStyle(fontWeight = FontWeight.Light),
                    modifier = Modifier.padding(top = 4.dp)
                )
            }

            // False bar
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Canvas(modifier = Modifier.size(50.dp, falseBarHeight.dp)) {
                    drawRect(
                        color = Color.Red,
                        size = size.copy(width = 50f) // You can adjust the width
                    )
                }
                Text(
                    text = "False",
                    style = TextStyle(fontWeight = FontWeight.Bold),
                    modifier = Modifier.padding(top = 8.dp)
                )
                Text(
                    text = "$falseCount",
                    style = TextStyle(fontWeight = FontWeight.Light),
                    modifier = Modifier.padding(top = 4.dp)
                )
            }
        }
    }
}


@Composable
@Preview(showBackground = true)
fun BarGraphPreview() {
    // Mock data for the bar graph
    val mockEntries = listOf(
        ReportEntry(value = true.toString(), templateId = 1, reportId = 1, timestamp = "Thu Jan 09 19:10:08 EST 2025"),
        ReportEntry(value = true.toString(), templateId = 1, reportId = 1, timestamp = "Sun Jan 12 19:10:08 EST 2025"),
        ReportEntry(value = false.toString(), templateId = 1, reportId = 1, timestamp = "Fri Jan 10 19:10:08 EST 2025"),
        ReportEntry(value = true.toString(), templateId = 1, reportId = 1, timestamp = "Fri Jan 10 15:10:08 EST 2025"),
        ReportEntry(value = false.toString(), templateId = 1, reportId = 1, timestamp = "Thu Jan 09 12:10:08 EST 2025"),
    )
    BarGraph(entries = mockEntries, name = "Sample bar graph")

}
