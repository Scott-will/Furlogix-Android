package com.example.vetapp.repositories

import android.database.Observable
import androidx.lifecycle.LiveData
import com.example.vetapp.Database.Entities.ReportTemplateField
import com.example.vetapp.injectionModules.ReportsRepositoryModule
import dagger.Component

interface IReportTemplateRepository {
    fun ReportTemplateObservable() : LiveData<List<ReportTemplateField>>

    fun insertReportTemplateFields(reportTemplateFields : List<ReportTemplateField>)
    
    fun GetReportById(id : Int) : LiveData<List<ReportTemplateField>>
}