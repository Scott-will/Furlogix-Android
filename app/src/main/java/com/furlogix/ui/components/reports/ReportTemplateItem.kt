package com.furlogix.ui.components.reports

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.furlogix.Database.Entities.ReportTemplateField
import com.furlogix.ui.components.common.BoxColourTheme
import com.furlogix.ui.components.common.DeleteButton
import com.furlogix.ui.components.common.DeleteWarning
import com.furlogix.ui.components.common.EditButton
import com.furlogix.ui.components.common.IconDisplayer


@Composable
fun ReportTemplateItem(data: ReportTemplateField,
                       onDeleteClick : (ReportTemplateField) -> Unit,
                       onUpdateClick : (ReportTemplateField) -> Unit,
                       onFavouriteClick : (ReportTemplateField) -> Unit,
                       index : Int = 0) {
    var showDialog by remember { mutableStateOf(false) }
    var showDeleteWarning by remember { mutableStateOf(false) }

    Surface(
        modifier = Modifier.size(140.dp),
        shape = RoundedCornerShape(12.dp),
        color = BoxColourTheme.GetColour(index)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            IconDisplayer(data.icon)
            Text(
                text = data.name,
                modifier = Modifier.fillMaxWidth(),
                fontWeight = FontWeight.Bold,
                textAlign = TextAlign.Center
            )
            Row(){
                EditButton { onUpdateClick(data); showDialog = true }
                DeleteButton {onDeleteClick(data); showDeleteWarning = true }
            }
        }
        if (showDialog) {
            AddReportTemplateDialog(
                onDismiss = { showDialog = false },
                onSave = { newItem ->
                    onUpdateClick(newItem)
                },
                currentLabel = data.name,
                selectedType = data.fieldType.toString(),
                reportId = data.reportId,
                update = true,
                reportField = data
            )
        }
        if(showDeleteWarning){
            DeleteWarning({showDeleteWarning = false}, {onDeleteClick(data); showDeleteWarning = false}, "Deleting this report template will delete all associated data")
        }
    }
}

@Composable
fun ReporttemplatesList(dataList: List<ReportTemplateField>,
                        onDeleteClick : (ReportTemplateField) -> Unit,
                        onUpdateClick : (ReportTemplateField) -> Unit,
                        onFavouriteClick : (ReportTemplateField) -> Unit) {
    Column(modifier = Modifier.padding(16.dp).fillMaxSize()) {
        dataList.chunked(2).forEach { pair ->
            Row(
                modifier = Modifier.fillMaxWidth()
                    .padding(bottom = 16.dp),
                horizontalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                pair.forEachIndexed { index, data ->
                    Box(modifier = Modifier.weight(1f)){
                        ReportTemplateItem(
                            data = data,
                            onUpdateClick = onUpdateClick,
                            onDeleteClick = onDeleteClick,
                            onFavouriteClick = onFavouriteClick,
                            index = dataList.indexOf(data)
                        )
                    }

                }

            }
        }
    }
}