package com.furlogix.ui.screens

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.List
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.FloatingActionButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.furlogix.Database.Entities.ReportTemplateField
import com.furlogix.reports.FieldType
import com.furlogix.ui.components.reports.read.ReporttemplatesList
import com.furlogix.ui.components.reports.write.AddReportTemplateDialog
import com.furlogix.viewmodels.ReportTemplatesViewModels
import com.furlogix.viewmodels.ReportViewModel

@Composable
fun EditReportScreen(
    navController: NavController,
    reportId: Int,
    reportViewModel: ReportViewModel = hiltViewModel(),
    reportTemplatesViewModel: ReportTemplatesViewModels = hiltViewModel()
) {
    var showDialog by remember { mutableStateOf(false) }
    var label by remember { mutableStateOf("") }
    var selectedType by remember { mutableStateOf(FieldType.entries.first().toString()) }
    val newTemplateList = remember { (mutableListOf<ReportTemplateField>()) }
    val existingTemplateList = reportTemplatesViewModel.reportTemplatesForCurrentReport.collectAsState()
    val reports by reportViewModel.reports.collectAsState()

    val report = remember(reports, reportId) {
        reports.firstOrNull { it.Id == reportId }
    }
    val reportNameCopy = remember { mutableStateOf<String?>(null) }

    LaunchedEffect(report) {
        if (report != null && report.name.isNotEmpty() && reportNameCopy.value == null) {
            reportNameCopy.value = report.name
        }
        reportTemplatesViewModel.populateReportTemplatesForCurrentReport(reportId)
    }

    val reportsTemplates = existingTemplateList.value + newTemplateList

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(
                Brush.verticalGradient(
                    colors = listOf(
                        Color(0xFFF8F9FF),
                        Color(0xFFEEF2FF),
                        Color(0xFFE0E7FF)
                    )
                )
            )
    ) {
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 20.dp),
            verticalArrangement = Arrangement.spacedBy(20.dp),
            contentPadding = PaddingValues(vertical = 24.dp)
        ) {
            // Header Section
            item {
                Column(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    val scale by animateFloatAsState(
                        targetValue = 1f,
                        animationSpec = tween(1000),
                        label = "headerScale"
                    )

                    Text(
                        text = "Edit Report",
                        fontSize = 32.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color(0xFF1E293B),
                        textAlign = TextAlign.Center,
                        modifier = Modifier.scale(scale)
                    )

                    Text(
                        text = "Customize your report template",
                        fontSize = 16.sp,
                        color = Color(0xFF64748B),
                        textAlign = TextAlign.Center,
                        modifier = Modifier.padding(top = 4.dp)
                    )
                }
            }

            // Action Buttons Card
            item {
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(24.dp),
                    elevation = CardDefaults.cardElevation(defaultElevation = 8.dp),
                    colors = CardDefaults.cardColors(containerColor = Color.White)
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(20.dp),
                        horizontalArrangement = Arrangement.spacedBy(12.dp)
                    ) {
                        // Add Template Button
                        Button(
                            onClick = { showDialog = true },
                            modifier = Modifier.weight(1f),
                            colors = ButtonDefaults.buttonColors(
                                containerColor = Color(0xFF667EEA)
                            ),
                            shape = RoundedCornerShape(16.dp),
                            contentPadding = PaddingValues(vertical = 16.dp)
                        ) {
                            Icon(
                                imageVector = Icons.Default.Add,
                                contentDescription = null,
                                modifier = Modifier.size(18.dp)
                            )
                            Spacer(modifier = Modifier.width(8.dp))
                            Text(
                                text = "Add Field",
                                fontSize = 14.sp,
                                fontWeight = FontWeight.SemiBold
                            )
                        }

                        // Save Button
                        Button(
                            onClick = {
                                newTemplateList.forEach { item ->
                                    reportTemplatesViewModel.insertReportTemplateField(item)
                                }
                                existingTemplateList.value.forEach { item ->
                                    reportTemplatesViewModel.updateReportTemplateField(item)
                                }
                                if (report != null) {
                                    report.name = reportNameCopy.value.toString()
                                    reportViewModel.updateReport(report)
                                }
                                reportTemplatesViewModel.populateReportTemplatesForCurrentReport(reportId)
                                navController.popBackStack()
                            },
                            modifier = Modifier.weight(1f),
                            colors = ButtonDefaults.buttonColors(
                                containerColor = Color(0xFF059669)
                            ),
                            shape = RoundedCornerShape(16.dp),
                            contentPadding = PaddingValues(vertical = 16.dp)
                        ) {
                            Icon(
                                imageVector = Icons.Default.Check,
                                contentDescription = null,
                                modifier = Modifier.size(18.dp)
                            )
                            Spacer(modifier = Modifier.width(8.dp))
                            Text(
                                text = "Save",
                                fontSize = 14.sp,
                                fontWeight = FontWeight.SemiBold
                            )
                        }
                    }
                }
            }

            // Report Name Card
            if (report != null) {
                item {
                    Card(
                        modifier = Modifier.fillMaxWidth(),
                        shape = RoundedCornerShape(24.dp),
                        elevation = CardDefaults.cardElevation(defaultElevation = 12.dp),
                        colors = CardDefaults.cardColors(containerColor = Color.White)
                    ) {
                        Column(
                            modifier = Modifier.padding(20.dp)
                        ) {
                            Row(
                                verticalAlignment = Alignment.CenterVertically,
                                modifier = Modifier.fillMaxWidth()
                            ) {
                                Icon(
                                    imageVector = Icons.Default.Edit,
                                    contentDescription = null,
                                    tint = Color(0xFF667EEA),
                                    modifier = Modifier.size(24.dp)
                                )
                                Spacer(modifier = Modifier.width(12.dp))
                                Text(
                                    text = "Report Name",
                                    fontSize = 20.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = Color(0xFF1E293B)
                                )
                            }

                            Spacer(modifier = Modifier.height(16.dp))

                            if (report.name.isEmpty()) {
                                Box(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .padding(vertical = 20.dp),
                                    contentAlignment = Alignment.Center
                                ) {
                                    CircularProgressIndicator(
                                        color = Color(0xFF667EEA),
                                        modifier = Modifier.size(32.dp)
                                    )
                                }
                            } else {
                                OutlinedTextField(
                                    value = reportNameCopy.value ?: "",
                                    onValueChange = { reportNameCopy.value = it },
                                    label = {
                                        Text(
                                            "Enter Report Name",
                                            color = Color(0xFF64748B)
                                        )
                                    },
                                    keyboardOptions = KeyboardOptions.Default.copy(
                                        keyboardType = KeyboardType.Text
                                    ),
                                    textStyle = TextStyle(
                                        fontSize = 16.sp,
                                        color = Color(0xFF1E293B)
                                    ),
                                    modifier = Modifier.fillMaxWidth(),
                                    shape = RoundedCornerShape(16.dp),
                                    colors = OutlinedTextFieldDefaults.colors(
                                        focusedBorderColor = Color(0xFF667EEA),
                                        unfocusedBorderColor = Color(0xFFE2E8F0),
                                        focusedLabelColor = Color(0xFF667EEA)
                                    )
                                )
                            }
                        }
                    }
                }
            }

            // Report Fields Card
            item {
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(24.dp),
                    elevation = CardDefaults.cardElevation(defaultElevation = 12.dp),
                    colors = CardDefaults.cardColors(containerColor = Color.White)
                ) {
                    Column(
                        modifier = Modifier.padding(20.dp)
                    ) {
                        if (reportsTemplates.isEmpty()) {
                            // Empty state
                            Column(
                                horizontalAlignment = Alignment.CenterHorizontally,
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(vertical = 40.dp)
                            ) {
                                Icon(
                                    imageVector = Icons.Default.List,
                                    contentDescription = null,
                                    tint = Color(0xFF94A3B8),
                                    modifier = Modifier.size(64.dp)
                                )

                                Spacer(modifier = Modifier.height(16.dp))

                                Text(
                                    text = "No Fields Yet",
                                    fontSize = 20.sp,
                                    fontWeight = FontWeight.SemiBold,
                                    color = Color(0xFF64748B)
                                )

                                Text(
                                    text = "Add fields to customize your report template",
                                    fontSize = 14.sp,
                                    color = Color(0xFF94A3B8),
                                    textAlign = TextAlign.Center,
                                    modifier = Modifier.padding(top = 4.dp)
                                )
                            }
                        } else {
                            // Fields header
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceBetween,
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Row(
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    Icon(
                                        imageVector = Icons.Default.List,
                                        contentDescription = null,
                                        tint = Color(0xFF667EEA),
                                        modifier = Modifier.size(24.dp)
                                    )
                                    Spacer(modifier = Modifier.width(12.dp))
                                    Text(
                                        text = "Report Fields",
                                        fontSize = 20.sp,
                                        fontWeight = FontWeight.Bold,
                                        color = Color(0xFF1E293B)
                                    )
                                }

                                Card(
                                    shape = RoundedCornerShape(12.dp),
                                    colors = CardDefaults.cardColors(
                                        containerColor = Color(0xFF667EEA).copy(alpha = 0.1f)
                                    )
                                ) {
                                    Text(
                                        text = "${reportsTemplates.size} ${if (reportsTemplates.size == 1) "Field" else "Fields"}",
                                        fontSize = 12.sp,
                                        fontWeight = FontWeight.Medium,
                                        color = Color(0xFF667EEA),
                                        modifier = Modifier.padding(horizontal = 12.dp, vertical = 6.dp)
                                    )
                                }
                            }

                            Spacer(modifier = Modifier.height(16.dp))

                            // Fields list
                            ReporttemplatesList(
                                reportsTemplates,
                                onDeleteClick = { item -> reportTemplatesViewModel.deleteReportTemplateField(item) },
                                onUpdateClick = { item -> reportTemplatesViewModel.updateReportTemplateField(item) }
                            )
                        }
                    }
                }
            }
        }

        // Modern Floating Action Button for quick add
        FloatingActionButton(
            onClick = { showDialog = true },
            modifier = Modifier
                .align(Alignment.BottomEnd)
                .padding(24.dp),
            containerColor = Color(0xFF667EEA),
            contentColor = Color.White,
            elevation = FloatingActionButtonDefaults.elevation(
                defaultElevation = 12.dp,
                pressedElevation = 16.dp
            )
        ) {
            Icon(
                imageVector = Icons.Default.Add,
                contentDescription = "Add Field",
                modifier = Modifier.size(24.dp)
            )
        }
    }

    // Add Report Template Dialog
    if (showDialog) {
        AddReportTemplateDialog(
            onDismiss = { showDialog = false },
            onSave = { newItem ->
                newTemplateList.add(newItem)
                label = ""
                selectedType = FieldType.entries.first().toString()
                showDialog = false
            },
            currentLabel = label,
            currentUnit = "",
            selectedType = selectedType,
            reportId = reportId
        )
    }
}