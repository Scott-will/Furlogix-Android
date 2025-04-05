package com.furlogix.reports

import com.furlogix.Database.Entities.ReportTemplateField
import javax.inject.Inject

class ReportTemplateValidator @Inject constructor(
){

    suspend fun ValidateTemplate(template : ReportTemplateField) : com.furlogix.Result{
        if(template.name.isNullOrEmpty()){
            return com.furlogix.Result(false, "Template name is empty")
        }
        return com.furlogix.Result(true, "")
    }
}