package com.furlogix.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.MailOutline
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.furlogix.Database.Entities.Reports
import com.furlogix.ui.components.reports.read.ReportHistoryTableItem
import com.furlogix.ui.navigation.Screen
import com.furlogix.viewmodels.ReportTemplatesViewModels
import com.furlogix.viewmodels.ReportViewModel
import kotlinx.coroutines.delay

@Composable
fun ReportEntryHistoryScreen(navController: NavController, reportId: Int = 0,
                             reportViewModel: ReportViewModel = hiltViewModel(),
                             reportTemplateViewModels: ReportTemplatesViewModels = hiltViewModel()) {
    val currentReport = reportViewModel.currentReport.collectAsState()
    val templates = reportTemplateViewModels.reportTemplatesForCurrentReport.collectAsState()
    val entries by reportViewModel.reportEntries.collectAsState() // entries are collected as a map
    var showLoading by remember { mutableStateOf(true) }

    LaunchedEffect(reportId) {
        reportViewModel.populateCurrentReport(reportId)
        reportTemplateViewModels.populateReportTemplatesForCurrentReport(reportId)
        delay(3000)
        showLoading = false
    }
    templates.value.forEach { item ->
        reportViewModel.PopulateReportEntriesProperty(item.uid)
    }

    val groupedEntries = entries.flatMap { it.value }.groupBy { it.timestamp }

    Column{
        Spacer(modifier = Modifier.height(16.dp))
        Row(modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp),
            horizontalArrangement = Arrangement.spacedBy(16.dp)){
            Button(onClick =
            {navController.navigate(Screen.ReportEntry.route.replace("{reportId}", reportId.toString()))},
                modifier = Modifier.weight(1f)
                    .padding(top = 8.dp)){
                Text("Add Data", fontSize = 18.sp)
            }
            Button(onClick = { reportViewModel.gatherReportData(reportId) },
                modifier = Modifier.weight(1f)
                    .padding(top = 8.dp)) {
                Text("Send Reports", fontSize = 18.sp)
            }
        }
        Spacer(modifier = Modifier.fillMaxWidth().padding(10.dp))
        if (entries.isEmpty() && showLoading) {
            CircularProgressIndicator(modifier = Modifier.fillMaxSize()) // Show loading indicator
        } else {
            LazyColumn(modifier = Modifier.fillMaxSize()) {
                //last sent time
                if(currentReport.value != null){
                    item {
                        LastSentTimeDisplay(currentReport.value!!)
                    }
                }
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
                            val displayName = if (template.units.isNullOrBlank()) {
                                template.name
                            } else {
                                "${template.name} (${template.units})"
                            }

                            Text(
                                text = displayName,
                                modifier = Modifier.weight(1f),
                                style = TextStyle(fontWeight = FontWeight.Bold, color = Color.Black),
                                textAlign = TextAlign.Center
                            )
                        }


                    }
                }
                groupedEntries.forEach { (timestamp, entryList) ->
                    item{
                        ReportHistoryTableItem(timestamp,
                            entryList.map{it.value}, entryList.map{it.Id})
                    }
                }
            }
        }
    }
}

@Composable
fun LastSentTimeDisplay(currentReport : Reports){
    currentReport?.lastSentTime?.takeIf { it.isNotEmpty() }?.let { lastSent ->
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .padding(vertical = 4.dp, horizontal = 8.dp)
        ) {
            Icon(
                imageVector = Icons.Default.MailOutline,
                contentDescription = "Last Sent Time",
                tint = Color.Gray,
                modifier = Modifier.size(16.dp)
            )
            Spacer(modifier = Modifier.width(4.dp))
            Text(
                text = "Last sent: $lastSent",
                fontSize = 12.sp,
                color = Color.Gray,
                fontStyle = FontStyle.Italic
            )
        }
    }
}
