package com.example.vetapp.reports

import android.widget.Toast
import com.example.vetapp.Database.Entities.ReportEntry
import com.example.vetapp.Database.Entities.ReportTemplateField
import com.example.vetapp.R
import com.example.vetapp.VetApplication
import com.example.vetapp.repositories.IReportTemplateRepository
import com.example.vetapp.repositories.IReportsRepository
import javax.inject.Inject

class ReportEntryValidator @Inject constructor(
    private val reportTemplateRepository : IReportTemplateRepository,
    private val reportRepository : IReportsRepository,
) {
    suspend fun ValidateEntry(entry : ReportEntry) : com.example.vetapp.Result{
        //validate type and value are okay
        try{
            var report = reportRepository.getReportById(entry.reportId)
        }
        catch(e : Exception){
            return com.example.vetapp.Result(false, "Report does not exist")
        }
        var template : ReportTemplateField
        try{
            template = reportTemplateRepository.GetTemplateById(entry.templateId)
        }
        catch(e : Exception){
            return com.example.vetapp.Result(false, "Report template does not exist")
        }
        if(entry.value.isEmpty())
        {
            return com.example.vetapp.Result(false, "Value is empty for ${template.name}")
        }
        if(!FieldTypeValidator.validateFieldTypeWithValue(template.fieldType, entry.value)){
            return com.example.vetapp.Result(false, "Value is not valid for ${template.name}")
        }

        return com.example.vetapp.Result(true, "Entry added successfully")
    }
}