package com.furlogix.ui.components.common

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.sp

@Composable
fun TitleText(text : String, modifier : Modifier) {
    Text(
        text = text,
        style = MaterialTheme.typography.headlineMedium.copy(
            fontWeight = FontWeight.Bold,
            fontSize = 32.sp,
            letterSpacing = 1.5.sp,
            color = Color.DarkGray
        ),
        modifier = modifier
            .fillMaxWidth(),
        textAlign = TextAlign.Center
    )
}

