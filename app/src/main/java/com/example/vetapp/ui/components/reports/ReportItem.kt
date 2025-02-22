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
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
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
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.example.vetapp.Database.Entities.Reports
import com.example.vetapp.R
import com.example.vetapp.ui.components.common.BoxColourTheme
import com.example.vetapp.ui.components.common.DeleteButton
import com.example.vetapp.ui.components.common.DeleteWarning
import com.example.vetapp.ui.components.common.EditButton

@Composable
fun ReportItem(data: Reports,
               onClick: (Reports) -> Unit,
               onEditClick: (Reports) -> Unit,
               onDeleteClick : (Reports) -> Unit,
               onSendClick : (Reports) -> Unit,
               editable: Boolean = true,
               index : Int = 0) {
    var showDialog by remember { mutableStateOf(false) }
    var showDeleteWarning by remember { mutableStateOf(false) }
    var expanded by remember { mutableStateOf(false) }
    Surface(
        modifier = Modifier.size(140.dp).clickable(onClick = {onClick(data)}),
        shape = RoundedCornerShape(12.dp),
        color = BoxColourTheme.GetColour(index)
    ) {
        Box{
            IconButton(
                onClick = { expanded = !expanded },
                modifier = Modifier
                    .padding(8.dp)
                    .align(Alignment.TopEnd)
            ) {
                Icon(
                    imageVector = Icons.Filled.MoreVert,
                    contentDescription = "More report options"
                )
            }

            DropdownMenu(
                expanded = expanded,
                onDismissRequest = { expanded = false },
            ) {
                DropdownMenuItem(onClick = {
                    onEditClick(data)
                    expanded = false
                }, text = {Text("Edit")},
                    leadingIcon = {Icon(
                        imageVector = Icons.Default.Edit,
                        contentDescription = R.string.edit_text.toString(),
                        modifier = Modifier.size(24.dp),
                        tint = Color(0xffdce21c)
                    )})
                DropdownMenuItem(onClick = {
                    showDeleteWarning = true
                    expanded = false
                }, text = {Text("Delete")},
                    leadingIcon = {
                        Icon(
                            imageVector = Icons.Default.Delete,
                            contentDescription = R.string.delete_text.toString(),
                            modifier = Modifier.size(24.dp),
                            tint = Color.Gray
                        )
                    })
                DropdownMenuItem(onClick = {
                    // Handle second action
                    expanded = false
                }, text = {Text("Send")},
                    leadingIcon = {
                        Icon(
                            imageVector = Icons.Default.Email,
                            contentDescription = "email",
                            modifier = Modifier.size(24.dp),
                            tint = Color.Gray
                        )
                    })
            }
        }
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
//            if (editable) {
//                Row(){
//
//
//                    if (showDialog) {
//                        AddReportDialog(
//                            onDismiss = { showDialog = false },
//                            onSave = { newItem ->
//                                onUpdateClick(newItem)
//                                showDialog = false
//                            },
//                            currentLabel = data.name,
//                            report = data,
//                            update = true,
//                        )
//                    }
//
//                }
//            }
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
                onSendClick :  (Reports) ->Unit,
                onDeleteClick :  (Reports) ->Unit,
                onEditClick :  (Reports) ->Unit,
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
                            onSendClick = onSendClick,
                            onDeleteClick = onDeleteClick,
                            onEditClick = onEditClick,
                            editable = editable,
                            index = dataList.indexOf(data)
                        )
                    }

                }

            }
        }
    }
}