package com.furlogix.ui.componets.reports

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.furlogix.Database.Entities.ReportTemplateField
import com.furlogix.ui.components.common.NoDataAvailable
import com.furlogix.ui.components.reports.ReportEntry

@Composable
fun ReportEntryForm(
    reportName : String,
    fields : List<ReportTemplateField>,
    templateValueMap : MutableMap<Int, MutableState<String>>) {
    Surface(
    shape = MaterialTheme.shapes.medium,
    tonalElevation = 8.dp,
    modifier = Modifier.fillMaxWidth()
    )
    {
        Column(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth()
        ) {
            Text(reportName)
            Spacer(modifier = Modifier.height(16.dp))
            if(fields.size == 0){
                NoDataAvailable("Templates", Modifier.fillMaxSize())
            }
            else{
                LazyColumn(modifier = Modifier.padding(16.dp)) {
                    items(fields.size) { item ->
                        ReportEntry(
                            fields[item],
                            templateValueMap[fields[item].uid]!! // Bind each item to its corresponding state
                        )
                    }
                }
            }

        }
    }
}
