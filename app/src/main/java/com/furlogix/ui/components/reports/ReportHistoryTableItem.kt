package com.furlogix.ui.components.reports

import android.util.Log
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import java.text.SimpleDateFormat
import java.util.Locale

@Composable
fun ReportHistoryTableItem(timestamp : String, entry : List<String>){
    Log.d("ReportHistoryTableItem", "building entry")
    Row( modifier = Modifier
        .fillMaxWidth()
        .border(1.dp, Color.Black),
        horizontalArrangement = Arrangement.Center) {
        Text(DisplayFormattedDate(timestamp), modifier = Modifier.weight(2f), fontWeight = FontWeight.Normal, textAlign = TextAlign.Center)
        entry.forEach { item ->
            Text(item, modifier = Modifier.weight(2f), fontWeight = FontWeight.Normal, textAlign = TextAlign.Center)
        }
    }
}

fun DisplayFormattedDate(entryTimestamp: String) : String {
    val originalFormat = SimpleDateFormat("EEE MMM dd HH:mm:ss zzz yyyy", Locale.US)
    val targetFormat = SimpleDateFormat("EEEE MMM d yyyy", Locale.US)
    val date = originalFormat.parse(entryTimestamp)
    return targetFormat.format(date)
}



