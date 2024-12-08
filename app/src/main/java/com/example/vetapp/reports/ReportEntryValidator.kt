package com.example.vetapp.reports

import android.widget.Toast
import com.example.vetapp.Database.Entities.ReportEntry
import com.example.vetapp.Database.Entities.ReportTemplateField
import com.example.vetapp.VetApplication
import com.example.vetapp.repositories.IReportTemplateRepository
import com.example.vetapp.repositories.IReportsRepository
import javax.inject.Inject

class ReportEntryValidator @Inject constructor(
    private val reportTemplateRepository : IReportTemplateRepository,
    private val reportRepository : IReportsRepository,
) {
    suspend fun ValidateEntry(entry : ReportEntry) : Boolean{
        //validate type and value are okay
        try{
            var report = reportRepository.getReportById(entry.reportId)
        }
        catch(e : Exception){
            return false
        }
        var template : ReportTemplateField
        try{
            template = reportTemplateRepository.GetTemplateById(entry.templateId)
        }
        catch(e : Exception){
            Toast.makeText(VetApplication.applicationContext(), "Report Template doesn't exist for report entry", Toast.LENGTH_SHORT).show()
            return false
        }
        if(entry.value.isEmpty())
        {
            Toast.makeText(VetApplication.applicationContext(), "Value is empty", Toast.LENGTH_SHORT).show()
            return false
        }
        if(!FieldTypeValidator.validateFieldTypeWithValue(template.fieldType, entry.value)){
            Toast.makeText(VetApplication.applicationContext(), "Value entered is not valid according to the field type", Toast.LENGTH_SHORT).show()
            return false
        }

        return true
    }
}