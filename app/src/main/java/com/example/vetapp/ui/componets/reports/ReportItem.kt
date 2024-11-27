package com.example.vetapp.ui.componets.reports

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.material3.Surface
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.navigation.NavController
import com.example.vetapp.Database.Entities.ReportTemplateField
import com.example.vetapp.Database.Entities.Reports
import com.example.vetapp.ui.componets.common.DeleteButton
import com.example.vetapp.ui.componets.common.EditButton
import com.example.vetapp.ui.navigation.Screen

@Composable
fun ReportItem(data: Reports,
               onClick: (Reports) -> Unit,
               onDeleteClick : (Reports) -> Unit,
               onUpdateClick : (Reports) -> Unit) {
    var showDialog by remember { mutableStateOf(false) }
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
            EditButton { showDialog = true }
            Spacer(modifier = Modifier.width(8.dp))
            DeleteButton {onDeleteClick(data)  }
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
}

@Composable
fun ReportsList(dataList: List<Reports>,
                navController: NavController,
                onDeleteClick :  (Reports) ->Unit,
                onUpdateClick :  (Reports) ->Unit) {

    Column(modifier = Modifier.padding(16.dp)) {
        dataList.forEach { data ->
            ReportItem(data = data,
                onClick = {navController.navigate(Screen.ReportEntry.route.replace("{reportId}", "${data.Id}"))},
                onUpdateClick = onUpdateClick,
                onDeleteClick = onDeleteClick)
        }

    }
}