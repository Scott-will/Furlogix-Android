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
import com.example.vetapp.Database.Entities.Reports

@Composable
fun ReportItem(data: Reports, onClick: (Reports) -> Unit) {
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
                text = data.Name,
                modifier = Modifier.weight(1f), // Ensures text is left-aligned
                fontWeight = FontWeight.Bold // Optional: Makes the text bold
            )
        }
    }
}

@Composable
fun ReportsList(dataList: List<Reports>, onBoxClick: (Reports) -> Unit) {
    Column(modifier = Modifier.padding(16.dp)) {
        dataList.forEach { data ->
            ReportItem(data = data, onClick = onBoxClick)
        }
    }
}