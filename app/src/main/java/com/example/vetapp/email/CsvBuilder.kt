package com.example.vetapp.email

import android.content.Context
import android.net.Uri
import android.os.Build
import androidx.core.content.FileProvider
import com.example.vetapp.Database.Entities.ReportEntry
import com.example.vetapp.Database.Entities.ReportTemplateField
import com.example.vetapp.VetApp
import com.example.vetapp.VetApplication
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
        file.createNewFile()
        val writer = BufferedWriter(FileWriter(file))
        writer.write("\"\"\"")
        writer.write(templates.joinToString(",") { it.name })
        writer.write("\"\"\"")
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
        writer.close()
        return getUriForFile(file)
    }



    fun getUriForFile(file: File): Uri {
        return FileProvider.getUriForFile(VetApplication.applicationContext(), "com.example.vetapp.provider" , file)
    }
}