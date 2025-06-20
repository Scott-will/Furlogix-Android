package com.furlogix.ui.components.reports.write

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.furlogix.R

@Composable
fun ReportTemplateIconSelector(
    selectedIconName: String?, // Expects a name like "food", "paw", etc.
    onIconSelected: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    var expanded by remember { mutableStateOf(false) }

    // Associate names with their drawable resources
    val iconMap = mapOf(
        "food" to R.drawable.food,
        "paw" to R.drawable.paw,
        "water" to R.drawable.water,
        "medicine" to R.drawable.medicine
    )

    val context = androidx.compose.ui.platform.LocalContext.current

    // This converts selectedIconName to the correct resource ID (for display)
    val selectedImageResId = iconMap[selectedIconName] ?: iconMap.values.first()

    Column(modifier = modifier.padding(16.dp)) {
        // Dropdown Trigger
        Box {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .clickable { expanded = true }
                    .background(Color.LightGray)
                    .padding(12.dp)
            ) {
                Image(
                    painter = painterResource(id = selectedImageResId),
                    contentDescription = "Selected Image",
                    modifier = Modifier.size(40.dp)
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text("Choose an Icon")
                Icon(
                    imageVector = Icons.Default.ArrowDropDown,
                    contentDescription = "Dropdown Icon"
                )
            }

            DropdownMenu(
                expanded = expanded,
                onDismissRequest = { expanded = false }
            ) {
                iconMap.forEach { (iconName, imageResId) ->
                    DropdownMenuItem(
                        onClick = {
                            onIconSelected(iconName) // Send name back to parent
                            expanded = false
                        },
                        text = {
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                Image(
                                    painter = painterResource(id = imageResId),
                                    contentDescription = iconName,
                                    modifier = Modifier.size(40.dp)
                                )
                                Spacer(modifier = Modifier.width(8.dp))
                                Text(iconName.replaceFirstChar { it.uppercase() })
                            }
                        }
                    )
                }
            }
        }

        Spacer(modifier = Modifier.height(24.dp))

        // Show big preview of selected icon
        Image(
            painter = painterResource(id = selectedImageResId),
            contentDescription = "Displayed Image",
            modifier = Modifier
                .fillMaxWidth()
                .height(200.dp)
                .clip(RoundedCornerShape(10.dp))
        )
    }
}
