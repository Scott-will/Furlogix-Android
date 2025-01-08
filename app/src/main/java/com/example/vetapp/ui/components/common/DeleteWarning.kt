package com.example.vetapp.ui.componets.common

import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

@Composable
fun DeleteWarning(onDismiss : () -> Unit, onConfirm : () -> Unit, msg : String){
    AlertDialog(
        onDismissRequest = onDismiss, // Dismiss the dialog
        title = {
            Text(text = "Deleting will delete addtional items", color = Color.White)
        },
        text = {
            Text(text = msg, color = Color.White)
        },
        confirmButton = {
            Button(onClick = onConfirm) {
                Text("OK", color = Color.White)
            }
        },
        dismissButton = {
            Button(onClick = onDismiss) {
                Text("Cancel", color = Color.White)
            }
        },
        containerColor = Color.Red, // Red background to indicate an error
        textContentColor = Color.White // White text color for good contrast
    )
}