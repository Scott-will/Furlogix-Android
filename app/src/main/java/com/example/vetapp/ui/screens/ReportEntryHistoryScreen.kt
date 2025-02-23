package com.example.vetapp.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.vetapp.ui.components.reports.ReportEntryHistoryTable
import com.example.vetapp.ui.components.reports.ReportHistoryTableItem
import com.example.vetapp.ui.navigation.Screen
import com.example.vetapp.viewmodels.ReportViewModel

@Composable
fun ReportEntryHistoryScreen(navController: NavController, reportId: Int = 0, viewModel: ReportViewModel = hiltViewModel()) {
    // Collect templates and entries from the ViewModel's StateFlows
    val templates = viewModel.reportTemplatesForCurrentReport.collectAsState()
    val entries by viewModel.reportEntries.collectAsState() // entries are collected as a map

    // Populate templates and report entries when the reportId changes
    LaunchedEffect(reportId) {
        viewModel.populateReportTemplatesForCurrentReport(reportId)
    }
    templates.value.forEach { item ->
        viewModel.PopulateReportEntriesProperty(item.uid)
    }

    // Recalculate the grouped entries whenever `entries` changes
    val groupedEntries = entries.flatMap { it.value } // Flatten entries into a list of MyEntry
        .groupBy { it.timestamp } // Group them by timestamp

    // Handle loading state when no entries are available
    if (entries.isEmpty()) {
        CircularProgressIndicator(modifier = Modifier.fillMaxSize()) // Show loading indicator
    } else {
        // Render the grouped entries in a LazyColumn
        LazyColumn(modifier = Modifier.fillMaxSize()) {
            item{
                Row(modifier = Modifier
                    .fillMaxWidth()
                    .background(Color.Gray),
                    horizontalArrangement = Arrangement.Center){
                    Text(
                        text = "Timestamp",
                        modifier = Modifier.weight(1f),
                        style = TextStyle(fontWeight = FontWeight.Bold, color = Color.Black),
                        textAlign = TextAlign.Center
                    )
                    templates.value.forEach { template ->
                        Text(
                            text = template.name,
                            modifier = Modifier.weight(1f),
                            style = TextStyle(fontWeight = FontWeight.Bold, color = Color.Black),
                            textAlign = TextAlign.Center
                        )
                    }


                }
            }
            groupedEntries.forEach { (timestamp, entryList) ->
                item{
                    ReportHistoryTableItem(timestamp, entryList.map{it.value})
                }
//                item {
//                    // Render timestamp as a header
//                    Text(
//                        text = "Timestamp: $timestamp",
//                        modifier = Modifier.padding(16.dp)
//                    )
//                }
//
//                item {
//                    // Render values for this timestamp in a row
//                    Row(modifier = Modifier.fillMaxWidth()) {
//                        entryList.forEach { entry ->
//                            Text(
//                                text = entry.value,
//                                modifier = Modifier.padding(8.dp),
//                            )
//                        }
//                    }
//                }
            }
        }
    }
}

//ReportEntryHistoryTable(templates.value)

//    Button(onClick = {navController.navigate(Screen.ReportEntry.route
//        .replace("{reportId}", "${reportId}"))}){
//        Text("Add Data")
//    }
//    Spacer(modifier = Modifier.height(16.dp))