package com.example.vetapp.reports

import com.example.vetapp.Database.Entities.ReportTemplateField
import com.example.vetapp.repositories.IReportTemplateRepository
import com.example.vetapp.repositories.IReportsRepository
import javax.inject.Inject

class ReportTemplateValidator @Inject constructor(
){

    suspend fun ValidateTemplate(template : ReportTemplateField) : com.example.vetapp.Result{
        if(template.name.isNullOrEmpty()){
            return com.example.vetapp.Result(false, "Template name is empty")
        }
        return com.example.vetapp.Result(true, "")
    }
}