package com.furlogix.ui.components.common

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.furlogix.R

@Composable
fun Header(titleText : String,
           subText : String) {
    Column(
        modifier = Modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // Animated header icon
        val scale by animateFloatAsState(
            targetValue = 1f,
            animationSpec = tween(1000),
            label = "headerScale"
        )

        Icon(
            imageVector = Icons.Default.Favorite,
            contentDescription = "Pets",
            tint = Color(0xFF6366F1),
            modifier = Modifier
                .size(48.dp)
                .scale(scale)
        )

        Spacer(modifier = Modifier.height(12.dp))

        Text(
            text = titleText,
            fontSize = 32.sp,
            fontWeight = FontWeight.Bold,
            color = Color(0xFF1E293B),
            textAlign = TextAlign.Center,
            fontFamily = FontFamily(Font(R.font.rubik_bold))
        )

        Text(
            text = subText,
            fontSize = 16.sp,
            color = Color(0xFF64748B),
            textAlign = TextAlign.Center,
            modifier = Modifier.padding(top = 4.dp)
        )
    }
}