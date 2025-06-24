package com.furlogix.ui.components.reports.read

import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
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
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.furlogix.ui.components.common.DeleteWarning
import com.furlogix.viewmodels.ReportEntryHistoryViewModel
import java.text.SimpleDateFormat
import java.util.Locale

@Composable
fun ReportHistoryTableItem(
    timestamp: String,
    entry: List<String>,
    reportEntryIds: List<Int>,
    reportEntryHistoryViewModel: ReportEntryHistoryViewModel = hiltViewModel()
) {
    var showMenu by remember { mutableStateOf(false) }
    var showDeleteDialog by remember { mutableStateOf(false) }

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp, horizontal = 8.dp),
        shape = RoundedCornerShape(8.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 12.dp, vertical = 8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            // Date column
            Text(
                text = DisplayFormattedDate(timestamp),
                modifier = Modifier
                    .weight(1f)
                    .padding(end = 4.dp),
                textAlign = TextAlign.Start,
                style = MaterialTheme.typography.bodyMedium,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )

            // Entry columns
            entry.forEach { item ->
                Text(
                    text = item,
                    modifier = Modifier
                        .weight(1f)
                        .padding(horizontal = 4.dp),
                    textAlign = TextAlign.Center,
                    style = MaterialTheme.typography.bodyMedium,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
            }

            // Dropdown menu
            Box(
                modifier = Modifier
                    .wrapContentWidth()
                    .padding(start = 4.dp),
                contentAlignment = Alignment.Center
            ) {
                IconButton(onClick = { showMenu = true }) {
                    Icon(Icons.Default.MoreVert, contentDescription = "Options")
                }

                DropdownMenu(
                    expanded = showMenu,
                    onDismissRequest = { showMenu = false }
                ) {
                    DropdownMenuItem(
                        text = { Text("Delete") },
                        onClick = {
                            showMenu = false
                            showDeleteDialog = true
                        }
                    )
                }
            }
        }
    }

    if(showDeleteDialog){
        DeleteWarning({showDeleteDialog = false},
            { showDeleteDialog = false
                DeleteReportEntries(reportEntryIds, reportEntryHistoryViewModel)
            },
            "This report entry will be lost forever")
    }

}

fun DeleteReportEntries(reportEntryIds: List<Int>, reportEntryHistoryViewModel: ReportEntryHistoryViewModel){
    reportEntryIds.forEach{ reportEntryId ->
        reportEntryHistoryViewModel.DeleteReportEntry(reportEntryId)
    }
}

fun DisplayFormattedDate(entryTimestamp: String) : String {
    val originalFormat = SimpleDateFormat("yyyy-MM-dd HH:mm", Locale.US)
    val targetFormat = SimpleDateFormat("yyyy-MM-dd HH:mm", Locale.US)
    val date = originalFormat.parse(entryTimestamp)
    return targetFormat.format(date)
}