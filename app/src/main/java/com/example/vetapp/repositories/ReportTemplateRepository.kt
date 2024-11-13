package com.example.vetapp.repositories

import android.database.Observable
import androidx.lifecycle.LiveData
import com.example.vetapp.Database.AppDatabase
import com.example.vetapp.Database.DAO.ReportTemplateDao
import com.example.vetapp.Database.Entities.ReportTemplateField
import com.example.vetapp.VetApplication

class ReportTemplateRepository() : IReportTemplateRepository {
    private val reportTemplateDao : ReportTemplateDao = AppDatabase.getDatabase(VetApplication.applicationContext()).reportTemplateDao()

    override fun ReportTemplateObservable() : LiveData<List<ReportTemplateField>> {
        return reportTemplateDao.getAll()
    }

    fun insertReportTemplateFields(reportTemplateFields : List<ReportTemplateField>){

    }

}