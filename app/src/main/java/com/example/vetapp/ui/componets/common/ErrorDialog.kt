package com.example.vetapp.ui.componets.common

import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

@Composable
fun ErrorDialog(onDismiss : () -> Unit, msg : String) {
    AlertDialog(
        onDismissRequest = onDismiss, // Dismiss the dialog
        title = {
            Text(text = msg, color = Color.White)
        },
        text = {
            Text(text = "Please try again later.", color = Color.White)
        },
        confirmButton = {
            Button(onClick = onDismiss) {
                Text("OK", color = Color.White)
            }
        },
        containerColor = Color.Red, // Red background to indicate an error
        textContentColor = Color.White // White text color for good contrast
    )
}
