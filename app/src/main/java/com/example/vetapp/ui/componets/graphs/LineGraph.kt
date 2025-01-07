package com.example.vetapp.ui.componets.graphs

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.foundation.Canvas
import androidx.compose.ui.graphics.Color
import com.example.vetapp.Database.Entities.ReportEntry
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

@Composable
fun LineGraph(entries : List<ReportEntry>, name : String) {
    val formatter = SimpleDateFormat("EEE MMM dd HH:mm:ss z yyyy", Locale.US)
    val lineGraphEntries = mutableListOf<LineGraphEntry>()
    for (entry in entries){
        try{
            lineGraphEntries.add(LineGraphEntry(formatter.parse(entry.timestamp), entry.value.toFloat()))
        }
        catch (e: Exception){
            //log and ignore
        }

    }
    val xMin = lineGraphEntries.minOf { it.date.time }
    val xMax = lineGraphEntries.minOf { it.date.time }
    val yMin = lineGraphEntries.minOf { it.value }
    val yMax = lineGraphEntries.maxOf { it.value }

    Canvas(modifier = Modifier.fillMaxSize()){
        val canvasWidth = size.width
        val canvasHeight = size.height

        val padding = 40f

        val mapX : (Float) -> Float = { x ->
            val normalizedX = (x-xMin)/(xMax - xMin)
        normalizedX * (canvasWidth - 2*padding) + padding
        }

        val mapY: (Float) -> Float = { y ->
            val normalizedY = (y - yMin) / (yMax - yMin)
            canvasHeight - (normalizedY * (canvasHeight - 2 * padding) + padding)
        }

        for (i in 1 until lineGraphEntries.size) {
            val prevPoint = lineGraphEntries[i - 1]
            val currentPoint = lineGraphEntries[i]

            val startX = mapX(prevPoint.date.time.toFloat())
            val startY = mapY(prevPoint.value)
            val endX = mapX(currentPoint.date.time.toFloat())
            val endY = mapY(currentPoint.value)

            drawLine(
                start = androidx.compose.ui.geometry.Offset(startX, startY),
                end = androidx.compose.ui.geometry.Offset(endX, endY),
                color = Color.Blue,
                strokeWidth = 2f
            )
        }

    }

}

data class LineGraphEntry(
    val date: Date,
    val value : Float
)