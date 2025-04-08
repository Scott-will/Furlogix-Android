package com.furlogix.email

import android.content.Context
import android.net.Uri
import androidx.core.content.FileProvider
import com.furlogix.Database.Entities.ReportEntry
import com.furlogix.Database.Entities.ReportTemplateField
import com.furlogix.Furlogix
import java.io.BufferedWriter
import java.io.File
import java.io.FileWriter
import java.util.Date

class CsvBuilder {

    fun buildAndWriteCsv(context: Context, reportName : String, entries : List<ReportEntry>, templates : List<ReportTemplateField>) : Uri{
        val directory = context.filesDir // This is for internal storage
        val fileName = "${reportName}_${Date().toString()}.csv"
        val file = File(directory, fileName)
        file.createNewFile()
        val writer = BufferedWriter(FileWriter(file))
        this.buildCsv(writer, entries, templates)
        writer.close()
        return getUriForFile(file)
    }

    fun buildCsv( writer : BufferedWriter, entries : List<ReportEntry>, templates : List<ReportTemplateField>){
        writer.write(templates.joinToString(",") { it.name })
        writer.newLine()
        val reportMap: Map<String, List<ReportEntry>> = entries.groupBy { it.timestamp }

        reportMap.toSortedMap().forEach { (timestamp, reportList) ->
            templates.forEach { template ->
                var entry = reportList.find { entry -> entry.templateId == template.uid }
                writer.write("\"${entry?.value}\"")
                if(template.uid != templates.last().uid){
                    writer.write(",")
                }
            }
            writer.newLine()
        }
    }

    fun getUriForFile(file: File): Uri {
        return FileProvider.getUriForFile(Furlogix.applicationContext(), "com.example.vetapp.provider" , file)
    }
}