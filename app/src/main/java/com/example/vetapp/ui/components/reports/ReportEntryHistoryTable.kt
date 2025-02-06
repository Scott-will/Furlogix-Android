package com.example.vetapp.ui.components.reports

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.vetapp.Database.Entities.ReportEntry
import com.example.vetapp.ui.components.common.BoxColourTheme
import com.example.vetapp.viewmodels.ReportViewModel

@Composable
fun ReportEntryHistoryTable(reportTemplateId : Int = 0, viewModel: ReportViewModel = hiltViewModel()) {
    viewModel.PopulateReportEntriesProperty(reportTemplateId)
    val entries by viewModel.reportEntries.collectAsState()
    LazyColumn(
        modifier = Modifier.fillMaxSize()
    ) {
        item{
            Row(
                modifier = Modifier.fillMaxWidth()
                    .padding(16.dp),
                horizontalArrangement = Arrangement.Absolute.Center
            ){
                Text("Value", modifier = Modifier.weight(2f),
                    style = TextStyle(
                        fontWeight = FontWeight.Bold,
                    textDecoration = TextDecoration.Underline,
                    fontSize = 18.sp ))
                Text("Timestamp", modifier = Modifier.weight(2f),
                    style = TextStyle(
                        fontWeight = FontWeight.Bold,
                        textDecoration = TextDecoration.Underline,
                        fontSize = 18.sp ))
            }
        }
        items(entries){ entry ->
            ReportHistoryTableItem(entry, entries.indexOf(entry))
        }
    }
}

@Composable
fun ReportHistoryTableItem(entry : ReportEntry, index : Int){
    Row( modifier = Modifier
        .fillMaxWidth()
        .padding(16.dp)
        .background(BoxColourTheme.GetColour(index))
        .padding(8.dp),
        horizontalArrangement = Arrangement.Absolute.Center) {
        Text(entry.value, modifier = Modifier.weight(2f), fontWeight = FontWeight.Bold)
        Text(entry.timestamp, modifier = Modifier.weight(2f), fontWeight = FontWeight.Bold)
    }
}

@Composable
@Preview(showBackground = true)
fun ReportEntryHistoryItemPreview(){
    val index = 1
    val entry = ReportEntry(0, "abcd", 1, 1, "time")
    ReportHistoryTableItem(entry, index);
}


