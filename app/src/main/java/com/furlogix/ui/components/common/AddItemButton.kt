package com.furlogix.ui.components.common

import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp

@Composable
fun AddItemButton(onClick: () -> Unit, localModifier: Modifier) {
    IconButton(
        onClick = onClick,
        modifier = localModifier
    ) {
        Icon(
            painter = painterResource(id = android.R.drawable.ic_input_add), // Grey plus sign (can use any other icon)
            contentDescription = "Add",
            tint = Color.White, // Icon color (white)
            modifier = Modifier.size(32.dp) // Size of the plus sign inside the button
        )
    }
}