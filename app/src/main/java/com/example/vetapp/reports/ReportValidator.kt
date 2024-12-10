package com.example.vetapp.reports

import com.example.vetapp.Database.DAO.UserDao
import com.example.vetapp.Database.Entities.ReportTemplateField
import com.example.vetapp.Database.Entities.Reports
import com.example.vetapp.Result
import com.example.vetapp.repositories.IReportEntryRepository
import com.example.vetapp.repositories.IReportTemplateRepository
import com.example.vetapp.repositories.IReportsRepository
import javax.inject.Inject

class ReportValidator () {

    suspend fun ValidateReport(report : Reports) : Result{
        if(report.name.isEmpty()){
            return com.example.vetapp.Result(false, "Template name is empty")
        }
        return Result(true, "")
    }
}