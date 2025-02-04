package com.example.vetapp.ui.components.reports

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.vetapp.Database.Entities.Reports
import com.example.vetapp.ui.components.common.DeleteButton
import com.example.vetapp.ui.components.common.DeleteWarning
import com.example.vetapp.ui.components.common.EditButton

@Composable
fun ReportItem(data: Reports,
               onClick: (Reports) -> Unit,
               onDeleteClick : (Reports) -> Unit,
               onUpdateClick : (Reports) -> Unit,
               editable: Boolean = true) {
    var showDialog by remember { mutableStateOf(false) }
    var showDeleteWarning by remember { mutableStateOf(false) }
    Surface(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 5.dp)
            .clickable { onClick(data) }, // Box is clickable
        shape = RoundedCornerShape(12.dp), // Rounded corners
        color = Color.LightGray // Light blue background
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            // Left-aligned text
            Text(
                text = data.name,
                modifier = Modifier.weight(1f), // Ensures text is left-aligned
                fontWeight = FontWeight.Bold // Optional: Makes the text bold
            )
            Spacer(modifier = Modifier.width(8.dp))
            if (editable) {
                EditButton { showDialog = true }
                Spacer(modifier = Modifier.width(8.dp))
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

    Column(modifier = Modifier.padding(16.dp)) {
        dataList.forEach { data ->
            ReportItem(data = data,
                onClick = onClick,
                onUpdateClick = onUpdateClick,
                onDeleteClick = onDeleteClick,
                editable = editable)
        }

    }
}