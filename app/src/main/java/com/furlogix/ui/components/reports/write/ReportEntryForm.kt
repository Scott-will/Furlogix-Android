package com.furlogix.ui.components.reports.write

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.furlogix.Database.Entities.ReportTemplateField
import com.furlogix.helpers.DateTimeHelper
import com.furlogix.ui.components.common.NoDataAvailable
import com.furlogix.ui.components.common.SimpleDateTimePicker
import com.furlogix.ui.components.reports.read.ReportEntry

@SuppressLint("NewApi")
@Composable
fun ReportEntryForm(
    reportName : String,
    fields : List<ReportTemplateField>,
    templateValueMap : MutableMap<Int, MutableState<String>>,
    timestamp : MutableState<String>) {
    Surface(
    shape = MaterialTheme.shapes.medium,
    tonalElevation = 8.dp,
    modifier = Modifier.fillMaxWidth()
    )
    {
        val dateTimeHelper = DateTimeHelper()
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
                fields.forEach { field ->
                    ReportEntry(
                        reportTemplateField = field,
                        text = templateValueMap[field.uid]!!
                    )
                }
                SimpleDateTimePicker { dateTime ->
                    if (dateTime != null) {
                        timestamp.value = dateTimeHelper.FormatDateTimeString(dateTime)
                    }
                }

                Text("Selected: ${timestamp.value ?: "No date selected"}", modifier = Modifier.padding(top = 8.dp))
                Spacer(modifier = Modifier.height(16.dp))
            }
        }
    }
}
