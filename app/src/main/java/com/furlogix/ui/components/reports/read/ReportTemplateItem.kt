package com.furlogix.ui.components.reports.read

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
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
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.furlogix.Database.Entities.ReportTemplateField
import com.furlogix.R
import com.furlogix.ui.components.common.DeleteWarning
import com.furlogix.ui.components.common.IconDisplayer
import com.furlogix.ui.components.reports.write.AddReportTemplateDialog

@Composable
fun ReporttemplatesList(
    dataList: List<ReportTemplateField>,
    onDeleteClick: (ReportTemplateField) -> Unit,
    onUpdateClick: (ReportTemplateField) -> Unit
) {
    Column(
        modifier = Modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        dataList.chunked(2).forEach { pair ->
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                pair.forEach { data ->
                    Box(modifier = Modifier.weight(1f)) {
                        ReportTemplateItem(
                            data = data,
                            onUpdateClick = onUpdateClick,
                            onDeleteClick = onDeleteClick
                        )
                    }
                }
                // Fill empty space if odd number of items
                repeat(2 - pair.size) {
                    Spacer(modifier = Modifier.weight(1f))
                }
            }
        }
    }
}

@Composable
fun ReportTemplateItem(
    data: ReportTemplateField,
    onDeleteClick: (ReportTemplateField) -> Unit,
    onUpdateClick: (ReportTemplateField) -> Unit
) {
    var showDialog by remember { mutableStateOf(false) }
    var showDeleteWarning by remember { mutableStateOf(false) }

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .aspectRatio(1f),
        shape = RoundedCornerShape(20.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 8.dp),
        colors = CardDefaults.cardColors(
            containerColor = Color.White
        )
    ) {
        Box(
            modifier = Modifier.fillMaxSize()
        ) {
            // Background gradient
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(
                        Brush.verticalGradient(
                            colors = listOf(
                                Color(0xFF667EEA).copy(alpha = 0.05f),
                                Color(0xFF764BA2).copy(alpha = 0.1f)
                            )
                        )
                    )
            )

            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.SpaceBetween
            ) {
                // Icon section
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
                        IconDisplayer(
                            iconName = data.icon,
                        )
                    }
                }

                // Text section
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier.weight(1f)
                ) {
                    Text(
                        text = data.name,
                        fontWeight = FontWeight.Bold,
                        fontSize = 14.sp,
                        color = Color(0xFF1E293B),
                        textAlign = TextAlign.Center,
                        maxLines = 2,
                        overflow = TextOverflow.Ellipsis,
                        modifier = Modifier.padding(vertical = 8.dp)
                    )

                    // Field type chip
                    Card(
                        shape = RoundedCornerShape(12.dp),
                        colors = CardDefaults.cardColors(
                            containerColor = Color(0xFF667EEA).copy(alpha = 0.1f)
                        )
                    ) {
                        Text(
                            text = data.fieldType.name,
                            fontSize = 10.sp,
                            fontWeight = FontWeight.Medium,
                            color = Color(0xFF667EEA),
                            modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp)
                        )
                    }
                }

                // Action buttons
                Row(
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    // Edit button
                    IconButton(
                        onClick = {
                            onUpdateClick(data)
                            showDialog = true
                        },
                        modifier = Modifier
                            .weight(1f)
                            .background(
                                Color(0xFF059669).copy(alpha = 0.1f),
                                RoundedCornerShape(12.dp)
                            )
                    ) {
                        Icon(
                            imageVector = Icons.Default.Edit,
                            contentDescription = R.string.edit_text.toString(),
                            tint = Color(0xFF059669),
                            modifier = Modifier.size(18.dp)
                        )
                    }

                    // Delete button
                    IconButton(
                        onClick = { showDeleteWarning = true },
                        modifier = Modifier
                            .weight(1f)
                            .background(
                                Color(0xFFEF4444).copy(alpha = 0.1f),
                                RoundedCornerShape(12.dp)
                            )
                    ) {
                        Icon(
                            imageVector = Icons.Default.Delete,
                            contentDescription = R.string.delete_text.toString(),
                            tint = Color(0xFFEF4444),
                            modifier = Modifier.size(18.dp)
                        )
                    }
                }
            }
        }
    }

    // Dialogs
    if (showDialog) {
        AddReportTemplateDialog(
            onDismiss = { showDialog = false },
            onSave = { newItem ->
                onUpdateClick(newItem)
                showDialog = false
            },
            currentLabel = data.name,
            selectedType = data.fieldType.toString(),
            currentUnit = data.units,
            reportId = data.reportId,
            update = true,
            reportField = data
        )
    }

    if (showDeleteWarning) {
        DeleteWarning(
            onDismiss = { showDeleteWarning = false },
            onConfirm = {
                onDeleteClick(data)
                showDeleteWarning = false
            },
            msg = "Deleting this report template will delete all associated data"
        )
    }
}