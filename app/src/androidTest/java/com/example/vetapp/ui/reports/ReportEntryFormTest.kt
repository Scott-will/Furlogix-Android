package com.example.vetapp.ui.reports

import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithText
import com.example.vetapp.Database.Entities.ReportTemplateField
import com.example.vetapp.reports.FieldType
import com.example.vetapp.ui.componets.reports.ReportEntryForm
import org.junit.Rule
import org.junit.Test

class ReportEntryFormTest {

    @Composable
    fun ReportEntryFormTestContent(
        reportName : String,
        fields : List<ReportTemplateField>,
        templateValueMap : MutableMap<Int, MutableState<String>>){
        ReportEntryForm(reportName = reportName,
            fields = fields,
            templateValueMap = templateValueMap)
    }

    @get:Rule
    val composeTestRule = createComposeRule()

    @Test
    fun ReportEntryForm_DisplaysRightItems(){
        var fields = mutableListOf<ReportTemplateField>()
        var i = 0
        FieldType.values().forEach { type ->
            val reportTemplateField = ReportTemplateField(
                name = "Template Field Test ${type}",
                fieldType = type,
                reportId = 0,
                uid = i
            )
            i++
            fields.add(reportTemplateField)
        }

        val map: MutableMap<Int, MutableState<String>> = mutableMapOf(
            0 to mutableStateOf(""),
            1 to mutableStateOf(""),
            2 to mutableStateOf("")
        )

        composeTestRule.setContent {
            ReportEntryFormTestContent("Test", fields, map)
        }

        FieldType.values().forEach { type ->
            composeTestRule.onNodeWithText("Template Field Test ${type}").assertIsDisplayed()
        }
    }
}