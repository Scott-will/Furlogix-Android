package com.furlogix.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.furlogix.ui.components.reports.ReportHistoryTableItem
import com.furlogix.ui.navigation.Screen
import com.furlogix.viewmodels.ReportViewModel
import kotlinx.coroutines.delay

@Composable
fun ReportEntryHistoryScreen(navController: NavController, reportId: Int = 0, viewModel: ReportViewModel = hiltViewModel()) {
    val templates = viewModel.reportTemplatesForCurrentReport.collectAsState()
    val entries by viewModel.reportEntries.collectAsState() // entries are collected as a map
    var showLoading by remember { mutableStateOf(true) }

    LaunchedEffect(reportId) {
        viewModel.populateReportTemplatesForCurrentReport(reportId)
        delay(3000)
        showLoading = false
    }
    templates.value.forEach { item ->
        viewModel.PopulateReportEntriesProperty(item.uid)
    }

    val groupedEntries = entries.flatMap { it.value }
        .groupBy { it.timestamp }

    Column{
        Button(onClick =
        {navController.navigate(Screen.ReportEntry.route.replace("{reportId}", reportId.toString()))}){
            Text("Add Data")
        }
        Spacer(modifier = Modifier.fillMaxWidth().padding(10.dp))
        if (entries.isEmpty() && showLoading) {
            CircularProgressIndicator(modifier = Modifier.fillMaxSize()) // Show loading indicator
        } else {
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
                }
            }
        }
    }

}
