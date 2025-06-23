package com.furlogix.ui.components.reports.read

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
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
import androidx.compose.ui.unit.sp
import com.furlogix.Database.Entities.Reports
import com.furlogix.ui.components.common.DeleteWarning
import com.furlogix.ui.theme.ClickableItemRed

@Composable
fun ReportItem(
    data: Reports,
    onClick: (Reports) -> Unit,
    onEditClick: (Reports) -> Unit,
    onDeleteClick: (Reports) -> Unit,
    onSendClick: (Reports) -> Unit
) {
    var showDeleteWarning by remember { mutableStateOf(false) }
    var expanded by remember { mutableStateOf(false) }

    Surface(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onClick(data) },
        shape = RoundedCornerShape(12.dp),
        color = ClickableItemRed,
        shadowElevation = 8.dp,
    ) {
        Box(
            modifier = Modifier
                .padding(16.dp)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .align(Alignment.CenterStart),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                Text(
                    text = data.name,
                    modifier = Modifier.fillMaxWidth(),
                    fontWeight = FontWeight.Bold,
                    textAlign = TextAlign.Center,
                    color = Color.Black,
                    fontSize = 20.sp
                )

                if (showDeleteWarning) {
                    Spacer(modifier = Modifier.height(8.dp))
                    DeleteWarning(
                        onDismiss = { showDeleteWarning = false },
                        onConfirm = { onDeleteClick(data) },
                        msg = "Deleting this report will delete all associated templates and data"
                    )
                }
            }

            IconButton(
                onClick = { expanded = true },
                modifier = Modifier
                    .align(Alignment.TopEnd)
            ) {
                Icon(
                    imageVector = Icons.Filled.MoreVert,
                    contentDescription = "More report options",
                    tint = Color.Black
                )
            }

            DropdownMenu(
                expanded = expanded,
                onDismissRequest = { expanded = false },
            ) {
                DropdownMenuItem(
                    onClick = {
                        onEditClick(data)
                        expanded = false
                    },
                    text = { Text("Edit Report") },
                    leadingIcon = {
                        Icon(
                            imageVector = Icons.Default.Edit,
                            contentDescription = "Edit",
                            tint = Color(0xffdce21c)
                        )
                    }
                )
                DropdownMenuItem(
                    onClick = {
                        showDeleteWarning = true
                        expanded = false
                    },
                    text = { Text("Delete Report") },
                    leadingIcon = {
                        Icon(
                            imageVector = Icons.Default.Delete,
                            contentDescription = "Delete",
                            tint = Color.Gray
                        )
                    }
                )
                DropdownMenuItem(
                    onClick = {
                        onSendClick(data)
                        expanded = false
                    },
                    text = { Text("Send Report") },
                    leadingIcon = {
                        Icon(
                            imageVector = Icons.Default.Email,
                            contentDescription = "Send",
                            tint = Color.Gray
                        )
                    }
                )
            }
        }
    }
}

@Composable
fun ReportsList(
    dataList: List<Reports>,
    onSendClick: (Reports) -> Unit,
    onDeleteClick: (Reports) -> Unit,
    onEditClick: (Reports) -> Unit,
    onClick: (Reports) -> Unit
) {
    Column(
        modifier = Modifier
            .padding(16.dp)
            .fillMaxSize(),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        dataList.forEach { data ->
            ReportItem(
                data = data,
                onClick = onClick,
                onSendClick = onSendClick,
                onDeleteClick = onDeleteClick,
                onEditClick = onEditClick
            )
        }
    }
}