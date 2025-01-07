package com.example.vetapp.reports

import com.example.vetapp.Database.Entities.Reports
import com.example.vetapp.Result

class ReportValidator () {

    suspend fun ValidateReport(report : Reports) : Result{
        if(report.name.isEmpty()){
            return com.example.vetapp.Result(false, "Template name is empty")
        }
        return Result(true, "")
    }
}