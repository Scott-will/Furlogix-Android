package com.example.vetapp.ui.componets.reports

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.vetapp.Database.Entities.ReportEntry
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
                horizontalArrangement = Arrangement.SpaceBetween
            ){
                Text("Value", modifier = Modifier.weight(2f))
                Text("Timestamp", modifier = Modifier.weight(2f))
            }
        }
        items(entries){ entry ->
            Row( modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
                .background(Color.LightGray)
                .padding(8.dp),
                horizontalArrangement = Arrangement.SpaceBetween) {
                Text(entry.value, modifier = Modifier.weight(2f))
                Text(entry.timestamp, modifier = Modifier.weight(2f))
            }
        }
    }
}