package com.example.vetapp.ui.components.reports

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material3.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.runtime.Composable

@Composable
fun TooManyReportsWarning() {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        colors = CardDefaults.cardColors(containerColor = Color.Gray)
    ) {
        Row(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                imageVector = Icons.Filled.Warning,
                contentDescription = "Warning",
                tint = Color.Yellow,
                modifier = Modifier.padding(end = 8.dp)
            )
            Text(
                text = "The size of the reports database has grown too large. Please send your reports",
                style = MaterialTheme.typography.bodyMedium,
                color = Color.White
            )
        }
    }
}