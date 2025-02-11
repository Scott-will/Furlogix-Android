package com.example.vetapp.ui.components.reports

import androidx.compose.foundation.clickable
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
import com.example.vetapp.Database.Entities.Reports
import com.example.vetapp.ui.components.common.BoxColourTheme
import com.example.vetapp.ui.components.common.DeleteButton
import com.example.vetapp.ui.components.common.DeleteWarning
import com.example.vetapp.ui.components.common.EditButton

@Composable
fun ReportItem(data: Reports,
               onClick: (Reports) -> Unit,
               onDeleteClick : (Reports) -> Unit,
               onUpdateClick : (Reports) -> Unit,
               editable: Boolean = true,
               index : Int = 0) {
    var showDialog by remember { mutableStateOf(false) }
    var showDeleteWarning by remember { mutableStateOf(false) }
    Surface(
        modifier = Modifier.size(140.dp).clickable(onClick = {onClick(data)}),
        shape = RoundedCornerShape(12.dp),
        color = BoxColourTheme.GetColour(index)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Text(
                text = data.name,
                modifier = Modifier.fillMaxWidth(),
                fontWeight = FontWeight.Bold ,
                textAlign = TextAlign.Center
            )
            //Put pet photo here
            if (editable) {
                Row(){
                    EditButton { showDialog = true }
                    DeleteButton { showDeleteWarning = true }
                    if (showDialog) {
                        AddReportDialog(
                            onDismiss = { showDialog = false },
                            onSave = { newItem ->
                                onUpdateClick(newItem)
                                showDialog = false
                            },
                            currentLabel = data.name,
                            report = data,
                            update = true
                        )
                    }
                }
            }
            if (showDeleteWarning) {
                DeleteWarning(
                    { showDeleteWarning = false },
                    {onDeleteClick(data)},
                    "Deleting this report will delete all associated templates and data"
                )
            }
        }
    }
}


@Composable
fun ReportsList(dataList: List<Reports>,
                onDeleteClick :  (Reports) ->Unit,
                onUpdateClick :  (Reports) ->Unit,
                onClick : (Reports) -> Unit,
                editable : Boolean = true) {
    Column(modifier = Modifier.padding(16.dp).fillMaxSize()) {
        dataList.chunked(2).forEach { pair ->
            Row(
                modifier = Modifier.fillMaxWidth()
                    .padding(bottom = 16.dp),
                horizontalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                pair.forEachIndexed { index, data ->
                    Box(modifier = Modifier.weight(1f)){
                        ReportItem(
                            data = data,
                            onClick = onClick,
                            onUpdateClick = onUpdateClick,
                            onDeleteClick = onDeleteClick,
                            editable = editable,
                            index = dataList.indexOf(data)
                        )
                    }

                }

            }
        }
    }
}