package com.furlogix.ui.components.common

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp

@Composable
fun TitleText(text : String) {
    Text(text, fontWeight = FontWeight.Bold, fontSize = 32.sp, modifier = Modifier.fillMaxWidth())

}