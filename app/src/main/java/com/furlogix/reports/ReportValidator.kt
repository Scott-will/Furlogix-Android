package com.furlogix.reports

import com.furlogix.Database.Entities.Reports
import com.furlogix.Result

class ReportValidator () {

    suspend fun ValidateReport(report : Reports) : Result{
        if(report.name.isEmpty()){
            return com.furlogix.Result(false, "Template name is empty")
        }
        return Result(true, "")
    }
}