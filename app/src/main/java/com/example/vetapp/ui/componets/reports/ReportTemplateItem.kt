package com.example.vetapp.ui.componets.reports

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
import androidx.navigation.NavController
import com.example.vetapp.Database.Entities.ReportTemplateField
import com.example.vetapp.ui.componets.common.DeleteButton
import com.example.vetapp.ui.componets.common.EditButton
import com.example.vetapp.ui.navigation.Screen


@Composable
fun ReportTemplateItem(data: ReportTemplateField,
                       onDeleteClick : (ReportTemplateField) -> Unit,
                       onUpdateClick : (ReportTemplateField) -> Unit,
                       navController: NavController) {
    var showDialog by remember { mutableStateOf(false) }
    Surface(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 5.dp),
        shape = RoundedCornerShape(12.dp), // Rounded corners
        color = Color.LightGray // Light blue background
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
                .clickable{navController.navigate(Screen.ReportEntryHistory.route.replace("{reportTemplateId}", "${data.uid}"))}, //go to page to see all previous entries in table format
            verticalAlignment = Alignment.CenterVertically
        ) {
            // Left-aligned text
            Text(
                text = data.name,
                modifier = Modifier.weight(1f), // Ensures text is left-aligned
                fontWeight = FontWeight.Bold // Optional: Makes the text bold
            )
            Spacer(modifier = Modifier.width(8.dp))
            EditButton { showDialog = true }
            Spacer(modifier = Modifier.width(8.dp))
            DeleteButton {onDeleteClick(data)  }
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
    }
}

@Composable
fun ReporttemplatesList(dataList: List<ReportTemplateField>,
                        onDeleteClick : (ReportTemplateField) -> Unit,
                        onUpdateClick : (ReportTemplateField) -> Unit,
                        navController: NavController) {
    Column(modifier = Modifier.padding(16.dp)) {
        dataList.forEach { data ->
            ReportTemplateItem(data = data, onUpdateClick = onUpdateClick, onDeleteClick = onDeleteClick, navController = navController)
        }
    }
}