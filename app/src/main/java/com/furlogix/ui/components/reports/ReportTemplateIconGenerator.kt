package com.furlogix.ui.components.reports

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.res.imageResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.furlogix.R

@Composable
fun ReportTemplateIconGenerator(text : String) {
    Box(modifier = Modifier.size(50.dp)){
        Image(bitmap = ImageBitmap.imageResource(R.drawable.water), contentDescription = null,
            modifier =
            Modifier.fillMaxSize()
                .align(Alignment.Center)
                .clip(CircleShape))
    }
}

@Composable
@Preview(showBackground = true)
fun ReportTemplateIconGeneratorPreview(){
    ReportTemplateIconGenerator("")
}