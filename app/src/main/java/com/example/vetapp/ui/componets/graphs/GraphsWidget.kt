package com.example.vetapp.ui.componets.graphs

import android.util.Log
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.automirrored.filled.ArrowForward
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
    Column(horizontalAlignment = Alignment.CenterHorizontally) {

        Row(
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically
        ) {
            // Left Arrow
            IconButton(onClick = { previous() }) {
                Icon(imageVector = Icons.AutoMirrored.Default.ArrowBack, contentDescription = "Previous")
            }

            Spacer(modifier = Modifier.width(32.dp))

            // Right Arrow
            IconButton(onClick = { next() }) {
                Icon(imageVector = Icons.AutoMirrored.Default.ArrowForward, contentDescription = "Next")
            }
        }
        Box( modifier = Modifier
            .size(250.dp).align(Alignment.CenterHorizontally)){
            val template = favouriteReportTemplates.value[currentIndex];
            val data = favouriteReportTemplateData.value[template.uid]

            Log.d("tag", "${data?.size}")
            if (data != null && data.size != 0) {
                if(template.fieldType == FieldType.TEXT){
                    PieChart(data, template.name)
                }
                if(template.fieldType == FieldType.NUMBER){
                    LineGraph(data, template.name)
                }
                if(template.fieldType == FieldType.BOOLEAN){
                    BarGraph(data, template.name)
                }
            }
            else{
                //add data warning
            }

        }
    }

}

