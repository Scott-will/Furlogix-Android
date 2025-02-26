package com.example.vetapp.ui.components.graphs

import android.graphics.Paint
import android.util.Log
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.graphics.drawscope.drawIntoCanvas
import androidx.compose.ui.graphics.nativeCanvas
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.vetapp.Database.Entities.ReportEntry

@Composable
fun PieChart(entries : List<ReportEntry>, name : String) {
    val entryDict = entries.groupBy { it.value }
    val total = entries.size
    val pieChartData = mutableListOf<PieChartData>()
    for(e in entryDict){
        pieChartData.add(PieChartData(e.key, e.value.size/total.toFloat(), ColourList.getNext()))
    }
    Log.d("PieChart", "size of dict ${entryDict.size}, size of list: ${entries.size}")
    Column(horizontalAlignment = Alignment.CenterHorizontally){
        Text( text = name,
            style = TextStyle(fontWeight = FontWeight.Bold),
            modifier = Modifier.padding(top = 8.dp)
        )
        Spacer(modifier = Modifier.width(32.dp))
        Canvas(modifier = Modifier
            .fillMaxSize()) {
            val size = size.minDimension
            val center = Offset(size / 2f, size / 2f)
            var startAngle = -90f

            pieChartData.forEach { segment ->
                val sweepAngle = 360f * (segment.percent)

                // Draw the slice
                drawPieSlice(center, size / 2, startAngle, sweepAngle, segment.color)
                val middleAngle = startAngle + sweepAngle / 2f
                val radius = size / 4f

                // Calculate the position of the text
                val textX = center.x + radius * kotlin.math.cos(Math.toRadians(middleAngle.toDouble())).toFloat()
                val textY = center.y + radius * kotlin.math.sin(Math.toRadians(middleAngle.toDouble())).toFloat()

                // Draw the text in the slice
                drawIntoCanvas { canvas ->
                    val text = segment.value
                    val paint = Paint().apply {
                        color = Color.Black.toArgb()
                        textAlign = Paint.Align.CENTER
                        textSize = 30f
                    }
                    canvas.nativeCanvas.drawText(text, textX, textY, paint)
                }
                startAngle += sweepAngle
            }
        }
    }


}

@Composable
@Preview(showBackground = true)
fun PieChartPreview() {
    // Mock data for the pie chart
    val mockEntries = listOf(
        ReportEntry(value = "Category 1", templateId = 1, reportId = 1, timestamp = ""),
        ReportEntry(value = "Category 1", templateId = 1, reportId = 1, timestamp = ""),
        ReportEntry(value = "Category 2", templateId = 1, reportId = 1, timestamp = ""),
        ReportEntry(value = "Category 3", templateId = 1, reportId = 1, timestamp = ""),
        ReportEntry(value = "Category 3", templateId = 1, reportId = 1, timestamp = ""),
        ReportEntry(value = "Category 3", templateId = 1, reportId = 1, timestamp = "")
    )
    PieChart(entries = mockEntries, name = "Sample Pie Chart")

}
    fun DrawScope.drawPieSlice(center: Offset, radius: Float, startAngle: Float, sweepAngle: Float, color: Color) {
    // Draw the pie slice
    val path = Path()
    path.moveTo(center.x, center.y) // Move to the center of the pie chart
    path.arcTo(
        rect = androidx.compose.ui.geometry.Rect(center.x - radius, center.y - radius, center.x + radius, center.y + radius),
        startAngleDegrees = startAngle,
        sweepAngleDegrees = sweepAngle,
        forceMoveTo = false
    )
    path.close()

    drawPath(path, color)
}


data class PieChartData(
    val value : String,
    val percent : Float,
    val color : Color
)

