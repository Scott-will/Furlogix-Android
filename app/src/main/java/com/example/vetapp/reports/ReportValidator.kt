package com.example.vetapp.reports

import com.example.vetapp.Database.DAO.UserDao
import com.example.vetapp.Database.Entities.ReportTemplateField
import com.example.vetapp.Database.Entities.Reports
import com.example.vetapp.repositories.IReportEntryRepository
import com.example.vetapp.repositories.IReportTemplateRepository
import com.example.vetapp.repositories.IReportsRepository
import javax.inject.Inject

class ReportValidator () {

    suspend fun ValidateReport(report : Reports) : Boolean{
        if(report.name.isEmpty()){
            return false
        }
        return true
    }
}