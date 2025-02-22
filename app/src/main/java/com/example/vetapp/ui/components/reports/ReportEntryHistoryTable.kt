package com.example.vetapp.ui.components.reports

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.vetapp.Database.Entities.ReportEntry
import com.example.vetapp.Database.Entities.ReportTemplateField
import com.example.vetapp.viewmodels.ReportViewModel
import java.text.SimpleDateFormat
import java.util.Locale

@Composable
fun ReportEntryHistoryTable(reportTemplates : List<ReportTemplateField> = emptyList(), viewModel: ReportViewModel = hiltViewModel()) {
    val entries by viewModel.reportEntries.collectAsState()
    LaunchedEffect(reportTemplates) {
        reportTemplates.forEach { item ->
            viewModel.PopulateReportEntriesProperty(item.uid)
        }

    }
    if(entries.count() != 0){
        LazyColumn(
            modifier = Modifier.fillMaxSize()
        ) {
            item {
                LazyRow(
                    modifier = Modifier.fillMaxWidth()
                        .padding(16.dp),
                    horizontalArrangement = Arrangement.Center
                ) {
                    item{
                        Text(
                            "Timestamp",
                            style = TextStyle(
                                fontWeight = FontWeight.Bold,
                                textDecoration = TextDecoration.Underline,
                                fontSize = 18.sp
                            ),
                            textAlign = TextAlign.Center
                        )
                        reportTemplates.forEach { item ->
                            Text(
                                item.name,
                                style = TextStyle(
                                    fontWeight = FontWeight.Bold,
                                    textDecoration = TextDecoration.Underline,
                                    fontSize = 18.sp
                                ),
                                textAlign = TextAlign.Center
                            )
                        }
                    }
                    reportTemplates.forEach { item ->
                        items(entries[item.uid]!!) { entry ->
                            ReportHistoryTableItem(entry)
                        }
                    }

                }
            }
        }
        }

}

@Composable
fun ReportHistoryTableItem(entry : ReportEntry){
    Row( modifier = Modifier
        .fillMaxWidth()
        .border(1.dp, Color.Black),
        horizontalArrangement = Arrangement.Center) {
        Text(DisplayFormattedDate(entry.timestamp), modifier = Modifier.weight(2f), fontWeight = FontWeight.Normal, textAlign = TextAlign.Center)
        Text(entry.value, modifier = Modifier.weight(2f), fontWeight = FontWeight.Normal, textAlign = TextAlign.Center)
    }
}

fun DisplayFormattedDate(entryTimestamp: String) : String {
    val originalFormat = SimpleDateFormat("EEE MMM dd HH:mm:ss zzz yyyy", Locale.US)
    val targetFormat = SimpleDateFormat("EEEE MMM d yyyy", Locale.US)
    val date = originalFormat.parse(entryTimestamp)
    return targetFormat.format(date)
}

@Composable
@Preview(showBackground = true)
fun ReportEntryHistoryItemPreview(){
    val index = 1
    val entry = ReportEntry(0, "time", 1, 1, "Tue Feb 04 21:17:31 EST 2025")
    ReportHistoryTableItem(entry);
}


