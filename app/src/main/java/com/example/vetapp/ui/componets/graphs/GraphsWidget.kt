package com.example.vetapp.ui.componets.graphs

import android.util.Log
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.automirrored.filled.ArrowForward
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.vetapp.reports.FieldType
import com.example.vetapp.viewmodels.ReportViewModel

@Composable
fun GraphsWidget(reportViewModel: ReportViewModel = hiltViewModel()) {
    //favourite
    reportViewModel.PopulateFavouriteReportTemplates()
    reportViewModel.PopulateFavouriteReportTemplateData()
    var favouriteReportTemplates = reportViewModel.favouriteReportTemplates.collectAsState()
    var favouriteReportTemplateData = reportViewModel.favouriteReportTemplatesData.collectAsState()

    var currentIndex by remember { mutableStateOf(0) }

    fun next() {
        if (currentIndex < favouriteReportTemplates.value.size - 1) {
            currentIndex++
        }
    }

    fun previous() {
        if (currentIndex > 0) {
            currentIndex--
        }
    }
    Column(horizontalAlignment = Alignment.CenterHorizontally){
        for(template in favouriteReportTemplates.value){
            var data = favouriteReportTemplateData.value[template.uid]
            if(template.fieldType == FieldType.TEXT){

                if (data != null) {
                    Log.d("GRAPHSWIDGET", "Building Pie Chart")
                    PieChart(data, template.name)
                }
                else{
                    Log.d("GRAPHSWIDGET", "data is null")
                }
            }
            if(template.fieldType == FieldType.NUMBER){
                if (data != null) {
                    LineGraph(data, template.name)
                }
            }
            if(template.fieldType == FieldType.BOOLEAN){
                //bar graph
            }
        }
        Spacer(modifier = Modifier.height(16.dp))

        // Row containing the navigation arrows
        Row(horizontalArrangement = Arrangement.Center) {
            // Left Arrow
            IconButton(onClick = { previous() }) {
                Icon(imageVector = Icons.AutoMirrored.Default.ArrowBack, contentDescription = "Previous")
            }

            Spacer(modifier = Modifier.width(16.dp))

            // Right Arrow
            IconButton(onClick = { next() }) {
                Icon(imageVector = Icons.AutoMirrored.Default.ArrowForward, contentDescription = "Next")
            }
        }
    }
}

