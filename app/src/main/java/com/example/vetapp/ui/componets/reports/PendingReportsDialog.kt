package com.example.vetapp.ui.componets.reports

import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable

@Composable
fun PendingReportsDialog (
    onConfirm: () -> Unit,
    onDismiss: () -> Unit
    ) {
        AlertDialog(
            onDismissRequest = { onDismiss() },
            title = { Text("Pending Reports") },
            text = { Text("Please confirm whether you sent the reports via email last time") },
            confirmButton = {
                Button(onClick = onConfirm) {
                    Text("Confirm")
                }
            },
            dismissButton = {
                Button(onClick = onDismiss) {
                    Text("Need to Resend")
                }
            }
        )
}