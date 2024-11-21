package com.example.vetapp.ui.componets.common

import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.runtime.Composable
import androidx.compose.ui.draw.clip

@Composable
fun EditButton(onClick: () -> Unit) {
    IconButton(
        onClick = onClick,
        modifier = Modifier
            .size(56.dp) // Size of the circle
            .clip(CircleShape) // Apply circular shape
    ) {
        Icon(
            imageVector = Icons.Default.Edit, // Pencil icon
            contentDescription = "Edit",
            modifier = Modifier.size(24.dp), // Icon size inside the circle
            tint = Color.White // Icon color
        )
    }
}