package com.example.vetapp.email

import android.content.Context
import android.net.Uri
import android.os.Build
import androidx.core.content.FileProvider
import com.example.vetapp.Database.Entities.ReportEntry
import com.example.vetapp.Database.Entities.ReportTemplateField
import java.io.BufferedWriter
import java.io.File
import java.io.FileWriter
import java.io.StringWriter
import java.util.Date

class CsvBuilder {

    fun buildCsv(context: Context, reportName : String, entries : List<ReportEntry>, templates : List<ReportTemplateField>) : Uri{
        val directory = context.filesDir // This is for internal storage
        val fileName = "${reportName}_${Date().toString()}.csv"
        val file = File(directory, fileName)
        val writer = BufferedWriter(FileWriter(file))
        writer.write("\"\"\"")
        templates.forEach { template ->
            writer.write("\"${template.name}\",")
        }
        writer.write("\"\"\"")
        writer.newLine()
        val reportMap: Map<String, List<ReportEntry>> = entries.groupBy { it.timestamp }

        reportMap.toSortedMap().forEach { (timestamp, reportList) ->
            templates.forEach { template ->
                writer.write("\"${reportList.find { entry -> entry.templateId == template.uid }}\"")
                if(template.uid != templates.last().uid){
                    writer.write(",")
                }
            }
            writer.newLine()
        }
        writer.flush()
        return getUriForFile(file)
    }



    fun getUriForFile(file: File): Uri {
        return Uri.fromFile(file)
    }
}