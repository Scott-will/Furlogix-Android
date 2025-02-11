package com.example.vetapp.ui.components.reports

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.vetapp.Database.Entities.ReportTemplateField
import com.example.vetapp.ui.components.common.BoxColourTheme
import com.example.vetapp.ui.components.common.DeleteButton
import com.example.vetapp.ui.components.common.DeleteWarning
import com.example.vetapp.ui.components.common.EditButton
import com.example.vetapp.ui.componets.common.FavouriteButton
import com.example.vetapp.ui.navigation.Screen


@Composable
fun ReportTemplateItem(data: ReportTemplateField,
                       onDeleteClick : (ReportTemplateField) -> Unit,
                       onUpdateClick : (ReportTemplateField) -> Unit,
                       onFavouriteClick : (ReportTemplateField) -> Unit,
                       navController: NavController,
                       index : Int = 0) {
    var showDialog by remember { mutableStateOf(false) }
    var showDeleteWarning by remember { mutableStateOf(false) }

    Surface(
        modifier = Modifier.size(140.dp),
        shape = RoundedCornerShape(12.dp),
        color = BoxColourTheme.GetColour(index)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .clickable{navController.navigate(Screen.ReportEntryHistory.route.replace("{reportTemplateId}", "${data.uid}"))},
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Text(
                text = data.name,
                modifier = Modifier.fillMaxWidth(),
                fontWeight = FontWeight.Bold,
                textAlign = TextAlign.Center
            )
            //ReportTemplateIconGenerator("test")
            Row(){
                EditButton { showDialog = true }
                DeleteButton {showDeleteWarning = true  }
                FavouriteButton(onClick = { onFavouriteClick(data) }, isFavourite = data.favourite)
            }
        }
        if (showDialog) {
            AddReportTemplateDialog(
                onDismiss = { showDialog = false },
                onSave = { newItem ->
                    onUpdateClick(newItem)
                },
                currentLabel = data.name,
                selectedType = data.fieldType.toString(),
                reportId = data.reportId,
                update = true,
                reportField = data
            )
        }
        if(showDeleteWarning){
            DeleteWarning({showDeleteWarning = false}, {onDeleteClick(data)}, "Deleting this report template will delete all associated data")
        }
    }
}

@Composable
fun ReporttemplatesList(dataList: List<ReportTemplateField>,
                        onDeleteClick : (ReportTemplateField) -> Unit,
                        onUpdateClick : (ReportTemplateField) -> Unit,
                        onFavouriteClick : (ReportTemplateField) -> Unit,
                        navController: NavController) {
    Column(modifier = Modifier.padding(16.dp).fillMaxSize()) {
        dataList.chunked(2).forEach { pair ->
            Row(
                modifier = Modifier.fillMaxWidth()
                    .padding(bottom = 16.dp),
                horizontalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                pair.forEachIndexed { index, data ->
                    Box(modifier = Modifier.weight(1f)){
                        ReportTemplateItem(
                            data = data,
                            onUpdateClick = onUpdateClick,
                            onDeleteClick = onDeleteClick,
                            onFavouriteClick = onFavouriteClick,
                            navController = navController,
                            index = dataList.indexOf(data)
                        )
                    }

                }

            }
        }
    }
}