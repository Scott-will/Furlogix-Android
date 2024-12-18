package com.example.vetapp

import com.example.vetapp.Database.Entities.ReportEntry
import com.example.vetapp.Database.Entities.ReportTemplateField
import com.example.vetapp.email.CsvBuilder
import com.example.vetapp.reports.FieldType
import com.example.vetapp.ui.navigation.Screen
import junit.framework.TestCase.assertEquals
import org.junit.Test
import java.io.BufferedWriter
import java.io.StringWriter
import java.util.Calendar

class CsvBuilderTests {

    @Test
    fun TestCsvBuilderOneTemplate(){
        val expectedCsv = "test Template\r\n" +
                "\"test1\"\r\n" +
                "\"test2\"\r\n" +
                "\"test3\""
        val stringWriter = StringWriter()
        var writer = BufferedWriter(stringWriter)
        val entries = mutableListOf<ReportEntry>()


        for (i in 1..3) {
            // Adjust timestamp by adding i hours to the current time
            val timestamp = "Tue Jan 0${i} 22:18:15 EST 2024" // Convert the calendar time to string
            val entry = ReportEntry(i, "test$i", i, 1, timestamp)
            entries.add(entry)
        }
        val templates = mutableListOf<ReportTemplateField>()
        templates.add(ReportTemplateField(1, 1, FieldType.TEXT, "test Template"))
        val csvBuilder = CsvBuilder()
        csvBuilder.buildCsv(writer, entries, templates)
        writer.flush()
        val result = stringWriter.toString().trim()

        assertEquals(expectedCsv, result)
    }

    @Test
    fun TestCsvBuilderMultipleTemplates(){
        val expectedCsv = "test Template,test Template2\r\n" +
                "\"test1\",\"sample1\"\r\n" +
                "\"test2\",\"sample2\"\r\n" +
                "\"test3\",\"sample3\""
        val stringWriter = StringWriter()
        var writer = BufferedWriter(stringWriter)
        val entries = mutableListOf<ReportEntry>()


        for (i in 1..3) {
            // Adjust timestamp by adding i hours to the current time
            val timestamp = "Tue Jan 0${i} 22:18:15 EST 2024" // Convert the calendar time to string
            val entry = ReportEntry(i, "test$i", i, 1, timestamp)
            entries.add(entry)
        }

        for (i in 1..3) {
            // Adjust timestamp by adding i hours to the current time
            val timestamp = "Tue Jan 0${i} 22:18:15 EST 2024" // Convert the calendar time to string
            val entry = ReportEntry(i, "sample$i", i, 2, timestamp)
            entries.add(entry)
        }
        val templates = mutableListOf<ReportTemplateField>()
        templates.add(ReportTemplateField(1, 1, FieldType.TEXT, "test Template"))
        templates.add(ReportTemplateField(2, 1, FieldType.BOOLEAN, "test Template2"))
        val csvBuilder = CsvBuilder()
        csvBuilder.buildCsv(writer, entries, templates)
        writer.flush()
        val result = stringWriter.toString().trim()

        assertEquals(expectedCsv, result)
    }
}