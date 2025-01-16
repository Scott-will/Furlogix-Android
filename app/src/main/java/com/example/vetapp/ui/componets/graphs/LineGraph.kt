package com.example.vetapp.ui.componets.graphs

import android.graphics.Paint
import android.util.Log
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.ui.Alignment
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.nativeCanvas
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
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
            Log.d("tag", "woops")
        }

    }
    lineGraphEntries.sortBy { it.date }

    val xMin = lineGraphEntries.minOf { it.date.time }
    val xMax = lineGraphEntries.maxOf { it.date.time }
    val yMin = lineGraphEntries.minOf { it.value }
    val yMax = lineGraphEntries.maxOf { it.value }
    Column(horizontalAlignment = Alignment.CenterHorizontally){
        Text( text = name,
            style = TextStyle(fontWeight = FontWeight.Bold),
            modifier = Modifier.padding(top = 8.dp)
        )

        Canvas(modifier = Modifier.fillMaxSize()) {
            val canvasWidth = size.width
            val canvasHeight = size.height

            val padding = 1f

            val mapX : (Float) -> Float = { x ->
                val normalizedX = (x - xMin) / (xMax - xMin)
                normalizedX * (canvasWidth - 2 * padding) + padding
            }

            val mapY: (Float) -> Float = { y ->
                val normalizedY = (y - yMin) / (yMax - yMin)
                canvasHeight - (normalizedY * (canvasHeight - 2 * padding) + padding)
            }

            // Draw the Y-axis
            drawLine(
                start = Offset(padding, 0f),
                end = Offset(padding, canvasHeight),
                color = Color.Black,
                strokeWidth = 10f
            )

            // Draw the X-axis
            drawLine(
                start = Offset(0f, canvasHeight - padding),
                end = Offset(canvasWidth, canvasHeight - padding),
                color = Color.Black,
                strokeWidth = 10f
            )

            // Draw Y-axis labels
            val yLabelInterval = (yMax - yMin) / 5
            for (i in 0..5) {
                val labelValue = yMin + i * yLabelInterval
                val yPos = mapY(labelValue)
                drawContext.canvas.nativeCanvas.drawText(
                    labelValue.toString(),
                    padding - 30f, // 30f for spacing left of Y-axis
                    yPos,
                    Paint().apply {
                        color = android.graphics.Color.BLACK
                        textSize = 30f
                        textAlign = Paint.Align.RIGHT
                    }
                )
            }

            // Draw the graph lines
            for (i in 1 until lineGraphEntries.size) {
                val prevPoint = lineGraphEntries[i - 1]
                val currentPoint = lineGraphEntries[i]

                val startX = mapX(prevPoint.date.time.toFloat())
                val startY = mapY(prevPoint.value)
                val endX = mapX(currentPoint.date.time.toFloat())
                val endY = mapY(currentPoint.value)

                Log.d("tag", "startx: ${startX} starty: ${startY} endx: ${endX} endy: ${endY}")
                drawLine(
                    start = androidx.compose.ui.geometry.Offset(startX, startY),
                    end = androidx.compose.ui.geometry.Offset(endX, endY),
                    color = Color.Blue,
                    strokeWidth = 10f
                )
            }

            // Draw the data points
            for (point in lineGraphEntries) {
                val x = mapX(point.date.time.toFloat())
                val y = mapY(point.value)

                drawCircle(
                    color = Color.Black,
                    radius = 16f,  // Adjust the radius to control the size of the dot
                    center = androidx.compose.ui.geometry.Offset(x, y)
                )
            }

            // Draw X-axis title (below the X-axis)
            drawContext.canvas.nativeCanvas.drawText(
                "Time", // This is the X-axis title
                canvasWidth / 2f,
                canvasHeight - padding + 50f, // 50f for space below X-axis labels
                Paint().apply {
                    color = android.graphics.Color.BLACK
                    textSize = 40f
                    textAlign = Paint.Align.CENTER
                }
            )
        }

    }

}

data class LineGraphEntry(
    val date: Date,
    val value : Float
)

@Composable
@Preview(showBackground = true)
fun LineGraphPreview() {
    // Mock data for the line graph
    val mockEntries = listOf(
        ReportEntry(value = "123", templateId = 1, reportId = 1, timestamp = "Thu Jan 09 19:10:08 EST 2025"),
        ReportEntry(value = "127", templateId = 1, reportId = 1, timestamp = "Sun Jan 12 19:10:08 EST 2025"),
        ReportEntry(value = "20", templateId = 1, reportId = 1, timestamp = "Fri Jan 10 19:10:08 EST 2025"),
        ReportEntry(value = "75", templateId = 1, reportId = 1, timestamp = "Fri Jan 10 15:10:08 EST 2025"),
        ReportEntry(value = "45", templateId = 1, reportId = 1, timestamp = "Thu Jan 09 12:10:08 EST 2025"),
    )
    LineGraph(entries = mockEntries, name = "Sample Pie Chart")

}