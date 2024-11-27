package com.example.vetapp.ui.componets.reports

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.vetapp.Database.Entities.ReportTemplateField
import com.example.vetapp.ui.componets.common.NoDataAvailable
import com.example.vetapp.ui.navigation.Screen

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
                NoDataAvailable("Templates")
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
