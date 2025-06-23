package com.furlogix.ui.help

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material.icons.filled.Send
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog

data class HelpPage(val title: String, val description: String, val icon: ImageVector, val imageRes: Int? = null)

@Composable
fun HelpWizard(
    onFinish: () -> Unit,
    modifier: Modifier = Modifier
) {
    val pages = listOf(
        HelpPage("Welcome", "Let's take a quick tour of the app.", Icons.Default.Info),
        HelpPage("Templates", "Customize your own report templates.", Icons.Default.Edit),
        HelpPage("Send Reports", "Log health updates and send them to your vet.", Icons.Default.Send),
        HelpPage("History", "Track your pet's progress over time.", Icons.Default.Favorite),
        HelpPage("Reminders", "Set reminders so you never miss a task.", Icons.Default.Notifications),
        HelpPage("You're all set!", "You can always find this help guide in the menu.", Icons.Default.Check)
    )

    var currentPage by rememberSaveable { mutableStateOf(0) }

    Dialog(
        onDismissRequest = onFinish
    ) {
        Surface(
            shape = RoundedCornerShape(16.dp),
            tonalElevation = 8.dp,
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            color = MaterialTheme.colorScheme.background
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(24.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.SpaceBetween
            ) {
                Spacer(modifier = Modifier.height(16.dp))

                Icon(
                    imageVector = pages[currentPage].icon,
                    contentDescription = null,
                    modifier = Modifier.size(64.dp),
                    tint = MaterialTheme.colorScheme.primary
                )

                Text(
                    text = pages[currentPage].title,
                    style = MaterialTheme.typography.headlineMedium,
                    modifier = Modifier.padding(top = 16.dp)
                )

                pages[currentPage].imageRes?.let { imageRes ->
                    Image(
                        painter = painterResource(id = imageRes),
                        contentDescription = null,
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(200.dp)
                            .clip(RoundedCornerShape(12.dp))
                            .background(Color.LightGray),
                        contentScale = ContentScale.Crop
                    )
                }


                Text(
                    text = pages[currentPage].description,
                    style = MaterialTheme.typography.bodyLarge,
                    modifier = Modifier
                        .padding(vertical = 16.dp)
                        .fillMaxWidth(),
                    textAlign = TextAlign.Center
                )

                Row(
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    if (currentPage > 0) {
                        TextButton(onClick = { currentPage-- }) {
                            Text("Back")
                        }
                    } else {
                        Spacer(modifier = Modifier.width(64.dp))
                    }

                    Row(horizontalArrangement = Arrangement.Center) {
                        repeat(pages.size) { index ->
                            Box(
                                modifier = Modifier
                                    .padding(4.dp)
                                    .size(10.dp)
                                    .clip(CircleShape)
                                    .background(if (index == currentPage) MaterialTheme.colorScheme.primary else Color.LightGray)
                            )
                        }
                    }

                    TextButton(
                        onClick = {
                            if (currentPage == pages.lastIndex) onFinish() else currentPage++
                        }
                    ) {
                        Text(if (currentPage == pages.lastIndex) "Finish" else "Next")
                    }
                }
            }
        }
    }
}
