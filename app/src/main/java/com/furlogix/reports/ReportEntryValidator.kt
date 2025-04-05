package com.furlogix.reports

import com.furlogix.Database.Entities.ReportEntry
import com.furlogix.Database.Entities.ReportTemplateField
import com.furlogix.repositories.IReportTemplateRepository
import com.furlogix.repositories.IReportsRepository
import javax.inject.Inject

class ReportEntryValidator @Inject constructor(
    private val reportTemplateRepository : IReportTemplateRepository,
    private val reportRepository : IReportsRepository,
) {
    suspend fun ValidateEntry(entry : ReportEntry) : com.furlogix.Result{
        //validate type and value are okay
        try{
            var report = reportRepository.getReportById(entry.reportId)
        }
        catch(e : Exception){
            return com.furlogix.Result(false, "Report does not exist")
        }
        var template : ReportTemplateField
        try{
            template = reportTemplateRepository.GetTemplateById(entry.templateId)
        }
        catch(e : Exception){
            return com.furlogix.Result(false, "Report template does not exist")
        }
        if(entry.value.isEmpty())
        {
            return com.furlogix.Result(false, "Value is empty for ${template.name}")
        }
        if(!FieldTypeValidator.validateFieldTypeWithValue(template.fieldType, entry.value)){
            return com.furlogix.Result(false, "Value is not valid for ${template.name}")
        }

        return com.furlogix.Result(true, "Entry added successfully")
    }
}