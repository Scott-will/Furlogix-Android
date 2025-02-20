package com.example.vetapp.ui.componets.common

import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.example.vetapp.R

@Composable
fun FavouriteButton(onClick: () -> Unit, isFavourite : Boolean) {
    IconButton(
        onClick = onClick,
        modifier = Modifier
            .size(56.dp)
            .clip(CircleShape)
    ) {
        Icon(
            imageVector = Icons.Default.Favorite,
            contentDescription = R.string.favourite_text.toString(),
            modifier = Modifier.size(24.dp),
            tint = if (isFavourite) Color.Red else Color.Gray
        )
    }
}