package com.furlogix.ui.components.reminders

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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.furlogix.Database.Entities.Reminder
import com.furlogix.ui.components.common.DeleteButton
import com.furlogix.ui.theme.ClickableItemRed

@Composable
fun ReminderItem(data: Reminder,
onDeleteClick : (Reminder) -> Unit) {
    Surface(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 5.dp),
        shape = RoundedCornerShape(12.dp),
        color = ClickableItemRed
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            // Left-aligned text
            Text(
                text = data.type,
                modifier = Modifier.weight(1f), // Ensures text is left-aligned
                fontWeight = FontWeight.Bold // Optional: Makes the text bold
            )
            Text(
                text = data.startTime,
                modifier = Modifier.weight(1f), // Ensures text is left-aligned
                fontWeight = FontWeight.Bold // Optional: Makes the text bold
            )
            Text(
                text = data.frequency,
                modifier = Modifier.weight(1f), // Ensures text is left-aligned
                fontWeight = FontWeight.Bold // Optional: Makes the text bold
            )
            Spacer(modifier = Modifier.width(8.dp))
            DeleteButton { onDeleteClick(data) }

        }
    }
}

@Composable
fun RemindersList(dataList : List<Reminder>,
                  onDeleteClick : (Reminder) -> Unit,
                  ) {
    Column(modifier = Modifier.padding(16.dp)) {
        dataList.forEach { data ->
            ReminderItem(
                data = data,
                onDeleteClick = onDeleteClick
            )
        }
    }
}