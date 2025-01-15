package com.example.vetapp.ui.componets.graphs

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
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
