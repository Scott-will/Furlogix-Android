package com.example.vetapp.ui.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.vetapp.ui.components.reports.ReportEntryHistoryTable
import com.example.vetapp.viewmodels.ReportViewModel

@Composable
fun ReportEntryHistoryScreen(navController: NavController, reportTemplateId : Int = 0, viewModel: ReportViewModel = hiltViewModel()
) {
    val template = viewModel.reportTemplateFields.collectAsState().value.filter { it.uid == reportTemplateId }
    Column {
        template.firstOrNull() ?.let { Text(it.name, modifier = Modifier.align(Alignment.CenterHorizontally).padding(16.dp), style = TextStyle(fontSize = 30.sp, fontWeight = FontWeight.Bold)) }
        ReportEntryHistoryTable(reportTemplateId)
    }
}