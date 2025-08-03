package com.furlogix.ui.components.reports.read

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material.icons.filled.Send
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.furlogix.Database.Entities.Reports
import com.furlogix.ui.components.common.DeleteWarning

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

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onClick(data) },
        shape = RoundedCornerShape(16.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 8.dp),
        colors = CardDefaults.cardColors(
            containerColor = Color.White
        )
    ) {
        Box(
            modifier = Modifier.fillMaxWidth()
        ) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(
                        Brush.horizontalGradient(
                            colors = listOf(
                                Color(0xFF667EEA).copy(alpha = 0.05f),
                                Color(0xFF764BA2).copy(alpha = 0.1f)
                            )
                        )
                    )
            )

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(20.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Card(
                    modifier = Modifier.size(48.dp),
                    shape = CircleShape,
                    colors = CardDefaults.cardColors(
                        containerColor = Color(0xFF667EEA).copy(alpha = 0.1f)
                    )
                ) {
                    Box(
                        modifier = Modifier.fillMaxSize(),
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(
                            imageVector = Icons.Default.DateRange,
                            contentDescription = null,
                            tint = Color(0xFF667EEA),
                            modifier = Modifier.size(24.dp)
                        )
                    }
                }

                Spacer(modifier = Modifier.width(16.dp))

                Column(
                    modifier = Modifier.weight(1f)
                ) {
                    Text(
                        text = data.name,
                        fontWeight = FontWeight.Bold,
                        fontSize = 18.sp,
                        color = Color(0xFF1E293B),
                        maxLines = 2,
                        overflow = TextOverflow.Ellipsis
                    )

                    Spacer(modifier = Modifier.height(4.dp))

                    Row(
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Icon(
                            imageVector = Icons.Default.Info,
                            contentDescription = null,
                            tint = Color(0xFF64748B),
                            modifier = Modifier.size(14.dp)
                        )
                        Spacer(modifier = Modifier.width(4.dp))
                        Text(
                            text = "Tap to view details",
                            fontSize = 12.sp,
                            color = Color(0xFF64748B)
                        )
                    }
                }

                Box {
                    IconButton(
                        onClick = { expanded = true },
                        modifier = Modifier
                            .background(
                                Color(0xFF94A3B8).copy(alpha = 0.1f),
                                CircleShape
                            )
                    ) {
                        Icon(
                            imageVector = Icons.Filled.MoreVert,
                            contentDescription = "More report options",
                            tint = Color(0xFF64748B),
                            modifier = Modifier.size(20.dp)
                        )
                    }

                    DropdownMenu(
                        expanded = expanded,
                        onDismissRequest = { expanded = false },
                        modifier = Modifier.background(
                            Color.White,
                            RoundedCornerShape(12.dp)
                        )
                    ) {
                        DropdownMenuItem(
                            onClick = {
                                onEditClick(data)
                                expanded = false
                            },
                            text = {
                                Text(
                                    "Edit Report",
                                    color = Color(0xFF1E293B),
                                    fontWeight = FontWeight.Medium
                                )
                            },
                            leadingIcon = {
                                Icon(
                                    imageVector = Icons.Default.Edit,
                                    contentDescription = "Edit",
                                    tint = Color(0xFF059669),
                                    modifier = Modifier.size(18.dp)
                                )
                            }
                        )

                        DropdownMenuItem(
                            onClick = {
                                onSendClick(data)
                                expanded = false
                            },
                            text = {
                                Text(
                                    "Send Report",
                                    color = Color(0xFF1E293B),
                                    fontWeight = FontWeight.Medium
                                )
                            },
                            leadingIcon = {
                                Icon(
                                    imageVector = Icons.Default.Send,
                                    contentDescription = "Send",
                                    tint = Color(0xFF3B82F6),
                                    modifier = Modifier.size(18.dp)
                                )
                            }
                        )

                        Divider(
                            color = Color(0xFFE2E8F0),
                            modifier = Modifier.padding(horizontal = 8.dp)
                        )

                        DropdownMenuItem(
                            onClick = {
                                showDeleteWarning = true
                                expanded = false
                            },
                            text = {
                                Text(
                                    "Delete Report",
                                    color = Color(0xFFEF4444),
                                    fontWeight = FontWeight.Medium
                                )
                            },
                            leadingIcon = {
                                Icon(
                                    imageVector = Icons.Default.Delete,
                                    contentDescription = "Delete",
                                    tint = Color(0xFFEF4444),
                                    modifier = Modifier.size(18.dp)
                                )
                            }
                        )
                    }
                }
            }
        }
    }

    // Delete warning dialog
    if (showDeleteWarning) {
        DeleteWarning(
            onDismiss = { showDeleteWarning = false },
            onConfirm = {
                onDeleteClick(data)
                showDeleteWarning = false
            },
            msg = "Deleting this report will delete all associated templates and data"
        )
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
        modifier = Modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(12.dp)
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