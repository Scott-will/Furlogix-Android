package com.example.vetapp.ui.components.common

import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.example.vetapp.R

@Composable
fun EditButton(onClick: () -> Unit) {
    IconButton(
        onClick = onClick,
        modifier = Modifier
            .size(56.dp)
            .clip(CircleShape),

    ) {
        Icon(
            imageVector = Icons.Default.Edit,
            contentDescription = R.string.edit_text.toString(),
            modifier = Modifier.size(24.dp),
            tint = Color(0xffdce21c)
        )
    }
}