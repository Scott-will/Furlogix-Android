package com.furlogix.ui.components.common

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp

@Composable
fun IconDisplayer(iconName: String) {
    val context = LocalContext.current
    val iconResId = remember(iconName) {
        context.resources.getIdentifier(iconName, "drawable", context.packageName)
    }

    if (iconResId != 0) {
        Image(
            painter = painterResource(id = iconResId),
            contentDescription = iconName,
            modifier = Modifier
                .size(48.dp)
                .background(Color.Transparent)

        )
    } else {
        Text("Icon not found")
    }
}
